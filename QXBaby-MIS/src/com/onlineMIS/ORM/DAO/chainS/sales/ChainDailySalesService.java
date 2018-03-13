package com.onlineMIS.ORM.DAO.chainS.sales;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onlineMIS.ORM.DAO.Response;
import com.onlineMIS.ORM.DAO.chainS.inventoryFlow.ChainInventoryFlowOrderService;
import com.onlineMIS.ORM.DAO.chainS.report.ChainMonthlyActiveNumDaoImpl;
import com.onlineMIS.ORM.DAO.chainS.report.ChainMonthlyHotBrandDaoImpl;
import com.onlineMIS.ORM.DAO.chainS.report.ChainMonthlyHotProductDaoImpl;
import com.onlineMIS.ORM.DAO.chainS.report.ChainWeeklyHotBrandDaoImpl;
import com.onlineMIS.ORM.DAO.chainS.report.ChainWeeklyHotProductDaoImpl;
import com.onlineMIS.ORM.DAO.chainS.report.ChainWeeklySalesDaoImpl;
import com.onlineMIS.ORM.DAO.chainS.user.ChainStoreDaoImpl;
import com.onlineMIS.ORM.DAO.chainS.user.ChainStoreService;
import com.onlineMIS.ORM.DAO.chainS.user.ChainUserInforService;
import com.onlineMIS.ORM.DAO.headQ.barCodeGentor.BrandDaoImpl;
import com.onlineMIS.ORM.DAO.headQ.barCodeGentor.ProductBarcodeDaoImpl;
import com.onlineMIS.ORM.DAO.headQ.barCodeGentor.YearDaoImpl;
import com.onlineMIS.ORM.DAO.headQ.inventory.InventoryOrderDAOImpl;
import com.onlineMIS.ORM.DAO.headQ.inventory.InventoryService;
import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainInOutStock;
import com.onlineMIS.ORM.entity.chainS.report.ChainMonthlyActiveNum;
import com.onlineMIS.ORM.entity.chainS.report.ChainMonthlyHotBrand;
import com.onlineMIS.ORM.entity.chainS.report.ChainMonthlyHotProduct;
import com.onlineMIS.ORM.entity.chainS.report.ChainWeeklyHotBrand;
import com.onlineMIS.ORM.entity.chainS.report.ChainWeeklyHotProduct;
import com.onlineMIS.ORM.entity.chainS.report.ChainWeeklySales;
import com.onlineMIS.ORM.entity.chainS.sales.ChainDailySales;
import com.onlineMIS.ORM.entity.chainS.sales.ChainDailySalesImpact;
import com.onlineMIS.ORM.entity.chainS.sales.ChainStoreSalesOrder;
import com.onlineMIS.ORM.entity.chainS.sales.ChainStoreSalesOrderProduct;
import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.ORM.entity.chainS.user.ChainUserInfor;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Brand;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.ProductBarcode;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Year;
import com.onlineMIS.ORM.entity.headQ.inventory.InventoryOrder;
import com.onlineMIS.action.chainS.charts.ChainSalesChartFormBean;
import com.onlineMIS.action.chainS.charts.ChainSalesChartUIBean;
import com.onlineMIS.common.Common_util;
import com.onlineMIS.common.loggerLocal;
import com.onlineMIS.filter.SystemParm;
import com.onlineMIS.sorter.ChainDailySalesSorter;
import com.onlineMIS.sorter.SortYear;

@Service
public class ChainDailySalesService{
	@Autowired
	private ChainStoreService chainStoreService;
	
	@Autowired
	private ChainDailySalesDaoImpl chainDailySalesDaoImpl;
	
	@Autowired
	private ChainStoreSalesOrderDaoImpl chainSalesOrderDaoImpl;
	
	@Autowired
	private ChainStoreDaoImpl chainStoreDaoImpl;
	
	@Autowired
	private ChainDailySalesImpactDaoImpl chainDailySalesImpactDaoImpl;
	
	@Autowired
	private ChainWeeklySalesDaoImpl chainWeeklySalesDaoImpl;
	
	@Autowired
	private ChainWeeklyHotBrandDaoImpl chainWeeklyHotBrandDaoImpl;
	
	@Autowired
	private ChainWeeklyHotProductDaoImpl chainWeeklyHotProductDaoImpl;
	
	@Autowired	
	private ChainMonthlyHotBrandDaoImpl chainMonthlyHotBrandDaoImpl;
	
	@Autowired	
	private ChainMonthlyHotProductDaoImpl chainMonthlyHotProductDaoImpl;
	
	@Autowired
	private ChainMonthlyActiveNumDaoImpl chainMonthlyActiveNumDaoImpl;
	
	@Autowired
	private BrandDaoImpl brandDaoImpl;
	
	@Autowired
	private YearDaoImpl yearDaoImpl;
	
	@Autowired
	protected ChainInventoryFlowOrderService flowOrderService;
	
	@Autowired
	private ProductBarcodeDaoImpl productBarcodeDaoImpl;
	
	@Autowired
	private InventoryOrderDAOImpl inventoryOrderDAOImpl;
	
	@Autowired
	private PurchaseService purchaseService;


	private Calendar today = Calendar.getInstance();

	/**
	 * 每天晚上运行这个功能去插入每天连锁店的销售，等信息到daily sales 表
	 * 1. 这个每天凌晨1点运行前一天数据
	 * 2. 修正每天修改之前单据所产生的
	 * @return
	 */
	public void runDailyBatch(){
		Thread current = Thread.currentThread();  
		String threadId = String.valueOf(current.getId());
		
		String dailyBatchSelect = "SELECT sum(totalQuantity), sum(netAmount), sum(totalQuantityR), " +
				"sum(netAmountR), sum(totalCost), sum(totalQuantityF) ,sum(totalCostF), sum(discountAmount), " +
				"sum(coupon), sum (cardAmount), sum(cashAmount - returnAmount), sum(vipScore), chainStore.chain_id, sum(netAmount - netAmountR), sum(qxQuantity), sum(qxAmount), sum(qxCost), sum(myQuantity), sum(myAmount), sum(myCost), sum(wechatAmount), sum(alipayAmount)  from ChainStoreSalesOrder where orderDate = ? and status = ?";
		/**
		 * @1. 运行当天的销售情况
		 */
		loggerLocal.infoB(new Date() + " " + threadId + " 开始Batch job :  ChainDailySalesService.runDailyBatch()");
		
		java.sql.Date yestorday = Common_util.getYestorday();

		Object[] value_sale = new Object[]{yestorday,ChainStoreSalesOrder.STATUS_COMPLETE};
		
		String hql_sale = dailyBatchSelect + " and chainStore.chain_id <> " + ChainStore.CHAIN_ID_TEST_ID + " GROUP BY chainStore.chain_id";

		List<Object> sales2 = (List<Object>)chainSalesOrderDaoImpl.executeHQLSelect(hql_sale, value_sale,null, true);

		Set<Integer> chainIdSet = new HashSet<Integer>();
		int i  = 0;
		Collections.sort(sales2, new ChainDailySalesSorter());
		for (i = 0; i < sales2.size(); i++){
			Object resultObject = sales2.get(i);
			Object[] sales3 = (Object[])resultObject;
		    dailyReportMappingAndSave(sales3, 9999,  threadId, yestorday, i+1);
		    if (sales3 != null){
		    	int chainId =  Common_util.getInt(sales3[12]);
		    	chainIdSet.add(chainId);
		    }
		}
		List<ChainStore> availableChainStores = chainStoreService.getAvailableParentChainstores();
		for (ChainStore store: availableChainStores){
			//需要补充数据
			if (!chainIdSet.contains(store.getChain_id())){
				loggerLocal.infoB(threadId + " 开始补充连锁店信息 : " + store.getChain_id());
				Object[] emptyObjects = new Object[]{0,0,0,0,0,0,0,0,0,0,0,0,store.getChain_id(),0,0,0,0,0,0};
				dailyReportMappingAndSave(emptyObjects, store.getChain_id(),  threadId, yestorday,++i);
			}
		}
		
		/**
		 * @2. 修正每天销售更改
		 */
		List<ChainDailySalesImpact> chainDailySalesImpacts = chainDailySalesImpactDaoImpl.getAll(false);
		for (ChainDailySalesImpact chainDailySalesImpact: chainDailySalesImpacts){
			int chainId = chainDailySalesImpact.getChainStore().getChain_id();
			java.sql.Date reportDate = chainDailySalesImpact.getReportDate();
			
			loggerLocal.infoB(new Date() + " " + threadId + " 开始Batch job 修正:  ChainDailySalesService.runDailyBatch() " + reportDate + "," +chainId);
			
			String dailyAmendSelect = dailyBatchSelect + " AND chainStore.chain_id =?  GROUP BY chainStore.chain_id";
			Object[] value_sale_amend = new Object[]{reportDate,ChainStoreSalesOrder.STATUS_COMPLETE, chainId};
			List<Object> sales3 = (List<Object>)chainSalesOrderDaoImpl.executeHQLSelect(dailyAmendSelect, value_sale_amend,null, true);
			Object[] sales4 = null;
			if (sales3 != null && sales3.size() >0){
				sales4 = (Object[])sales3.get(0);				 
			}
			
			DetachedCriteria criteria = DetachedCriteria.forClass(ChainDailySales.class);
			criteria.add(Restrictions.eq("reportDate", reportDate));
			criteria.add(Restrictions.eq("chainStore.chain_id", chainId));
			List<ChainDailySales> value = chainDailySalesDaoImpl.getByCritera(criteria, true);
			
			int rank = 9999;
			if (value != null && value.size() >0)
				rank = value.get(0).getRank();
			
	        boolean success = dailyReportMappingAndSave(sales4,chainId, threadId, reportDate, rank);

	        if (success){
	    	   loggerLocal.infoB(new Date() + " " + threadId + " 修正成功:  ChainDailySalesService.runDailyBatch() " + reportDate + "," +chainId);
				
	    	   try {
	    	       chainDailySalesImpactDaoImpl.delete(chainDailySalesImpact,false);
	    	   } catch (Exception e) {
	    		   loggerLocal.infoB(new Date() + " " + threadId + " 删除修正失败:  " + reportDate + "," +chainId);
				   loggerLocal.errorB(e);
	    	   }
	        } else 
	    	   loggerLocal.infoB(new Date() + " " + threadId + " 修正失败:  ChainDailySalesService.runDailyBatch() " + reportDate + "," +chainId);

		}
		
		loggerLocal.infoB(new Date() +" " + threadId + "  完成 Batch job ：  ChainDailySalesService.runDailyBatch()");
	}
	

	
	/**
	 * 做Daily report mapping 的功能
	 * @param sales3
	 * @return
	 */
	private boolean dailyReportMappingAndSave(Object[] sales3, int chainId, String threadId, java.sql.Date reportDate, int rank){
		ChainDailySales dailySales = null;
		
		if (sales3 != null) {
			int totalQ = Common_util.getInt(sales3[0]);
			double netAmt = Common_util.getDouble(sales3[1]);
			int totalQR = Common_util.getInt(sales3[2]);
			double netAmtR = Common_util.getDouble(sales3[3]);
			double totalCost = Common_util.getDouble(sales3[4]);
			int totalQF = Common_util.getInt(sales3[5]);
			double totalCostF = Common_util.getDouble(sales3[6]);
			double discountAmt = Common_util.getDouble(sales3[7]);
			double coupon = Common_util.getDouble(sales3[8]);
			double cardAmt = Common_util.getDouble(sales3[9]);
			double cashAmt = Common_util.getDouble(sales3[10]);
			double vipScoreAmt = Common_util.getDouble(sales3[11]);
			chainId =  Common_util.getInt(sales3[12]);
			int qxQ = Common_util.getInt(sales3[14]);
			double qxAmount = Common_util.getDouble(sales3[15]);
			double qxCost = Common_util.getDouble(sales3[16]);
			int myQ = Common_util.getInt(sales3[17]);
			double myAmount = Common_util.getDouble(sales3[18]);
			double myCost = Common_util.getDouble(sales3[19]);		
			double wechatAmt = Common_util.getDouble(sales3[20]);	
			double alipayAmt = Common_util.getDouble(sales3[21]);	
			ChainStore store = chainStoreDaoImpl.get(chainId, true);
			
			if (store == null)
				return false;
			
			dailySales = new ChainDailySales(store, reportDate, totalQ, totalQR, totalQ - totalQR, totalQF, netAmt, netAmtR, netAmt - netAmtR, totalCost, 
			    		   netAmt - netAmtR - totalCost, totalCostF, discountAmt, coupon, vipScoreAmt, cardAmt, cashAmt, rank, qxQ, qxAmount, qxCost, myQ, myAmount, myCost, wechatAmt, alipayAmt);
		} else {
			ChainStore store = chainStoreDaoImpl.get(chainId, true);
			
			if (store == null)
				return false;
			
			dailySales = new ChainDailySales(store, reportDate);
		}

	    try {
	    	loggerLocal.infoB(threadId + " runDailyBatch 开始保存 : " + chainId);
	        chainDailySalesDaoImpl.saveOrUpdate(dailySales, false);
		} catch (Exception e) {
			loggerLocal.error(threadId + " runDailyBatch 保存 : " + chainId + " 失败!");
			loggerLocal.errorB(e);
			return false;
		}

		loggerLocal.infoB(threadId + " runDailyBatch 成功保存 : " + chainId);
		return true;
	}
	
	/**
	 * 每天运行一次去检查n天前的单子，看看是否需要系统自动过账
	 */
	public void runDailyInventoryOrderCheck(){
		int interval = Integer.parseInt(SystemParm.getParm("SYSTEM_INVENTORY_CONFIRM_DAYS"));
		Date today = new Date();
		Date exceptionDate = null;
		
		loggerLocal.infoB("DailyOrderCheck: =========== 开始检查n天前未确认单据 ================== " + today.toString());
		try {
			exceptionDate = Common_util.dateFormat.parse(SystemParm.getParm("CHAIN_INVENTORY_CONFIRM_EXCEPTION_DATE"));
		} catch (ParseException e) {
			loggerLocal.errorB(e);
			return;
		}
		
		//1. 验证today是否是在excetionDate 之前
		if (today.before(exceptionDate)){
			loggerLocal.infoB("DailyOrderCheck: 今天日期在避免注入日期之前");
			return ;
		} else {
			
			Date startDate = Common_util.calcualteDate(today, -interval);
			startDate = Common_util.formStartDate(startDate);
			
			Date endDate = Common_util.calcualteDate(today, -interval + 20);
			endDate = Common_util.formEndDate(endDate);
			
			//1.1 如果是4月5好，就要特别处理一些
			Date specialDate = null;
			try {
			    specialDate = Common_util.dateFormat.parse("2017-04-05");
			    
			    if (today.getYear() == specialDate.getYear() && today.getMonth()==specialDate.getMonth() && today.getDate() == specialDate.getDate()){
			    	startDate = Common_util.dateFormat.parse("2016-01-01");
			    }
			} catch (ParseException e) {
				loggerLocal.errorB(e);
				return;
			}
			
			loggerLocal.infoB("DailyOrderCheck: 查找之前单据，日期   " + startDate.toString() + " 到  " + endDate.toString());
			
			DetachedCriteria criteria = DetachedCriteria.forClass(InventoryOrder.class,"order");
			criteria.add(Restrictions.eq("order.order_Status", InventoryOrder.STATUS_ACCOUNT_COMPLETE));
			criteria.add(Restrictions.between("order.order_EndTime", startDate, endDate));
			criteria.add(Restrictions.or(Restrictions.eq("order.chainConfirmStatus", InventoryOrder.STATUS_CHAIN_NOT_CONFIRM), Restrictions.eq("order.chainConfirmStatus", InventoryOrder.STATUS_CHAIN_PRODUCT_INCORRECT)));
			
			List<InventoryOrder> orders = inventoryOrderDAOImpl.search(criteria);
			loggerLocal.infoB("DailyOrderCheck: 单据总数 : " + orders.size());
			
			for (InventoryOrder order : orders){
				int clientId = order.getClient_id();
				if (clientId < 0 || order.getChainConfirmStatus() == InventoryOrder.STATUS_CHAIN_CONFIRM || order.getChainConfirmStatus() == InventoryOrder.STATUS_SYSTEM_CONFIRM)
					continue;
				else {
					ChainStore chainStore = chainStoreDaoImpl.getByClientId(clientId);
					if (chainStore != null && order.getOrder_EndTime().after(chainStore.getActiveDate())){
                        loggerLocal.infoB("更新单据 :" + order.getOrder_ID() + "," + order.getClient_name());
                        try {
                        	purchaseService.systemUpdateChainInventoryStatus(order.getOrder_ID());
                        	loggerLocal.infoB("完成更新单据 :" + order.getOrder_ID() + "," + order.getClient_name());
                        } catch (Exception e){
                        	loggerLocal.infoB("错误更新单据 :" + order.getOrder_ID() + "," + order.getClient_name());
                        	loggerLocal.errorB(e);
                        }
					}
				}
			}
		}
		
		loggerLocal.infoB("DailyOrderCheck: 单据检查完成");
		
		
	}
	
	/**
	 * 每天运行一次去获取最新的产品信息，生成一个txt文件供下载
	 */
	public void runDailyGenBarcode(){
		loggerLocal.infoB(new Date() + " 开始Batch job :  ChainDailySalesService.runDailyGenBarcode()");

		try {
			InputStream fileStream = flowOrderService.genBarcodeFile();
			
			FileOutputStream fileOutput = new FileOutputStream(new File(SystemParm.getParm("BARCODE_DOWNLOAD_PATH")));
			
			int cha = 0;
			while ((cha = fileStream.read()) != -1){
				fileOutput.write(cha);
			}
			fileOutput.close();  
			fileStream.close();  

		} catch (FileNotFoundException e) {
			loggerLocal.error("runDailyGenBarcode 失败:" + e.getMessage());
		} catch (IOException e) {
			loggerLocal.error("runDailyGenBarcode 失败:" + e.getMessage());
		}

	}
	
	/**
	 * 每周一早上运行上周一周的销售前几名连锁店
	 */
	public void runWeeklyRank(){
		loggerLocal.infoB(new Date() + " 开始 weekly Batch job排名 :  ChainDailySalesService.runWeeklyRank()");
		List<java.sql.Date> lastWeekDays = Common_util.getLastWeekDays();
		java.sql.Date startDate = lastWeekDays.get(0);
		java.sql.Date endDate = lastWeekDays.get(6);
		
		DetachedCriteria criteria = DetachedCriteria.forClass(ChainDailySales.class);
		ProjectionList projList = Projections.projectionList();
		projList.add(Projections.groupProperty("chainStore.chain_id"));
		projList.add(Projections.sum("salesQuantity"));
		projList.add(Projections.sum("returnQuantity"));
		projList.add(Projections.sum("netSalesQuantity"));
		projList.add(Projections.sum("freeQuantity"));
		projList.add(Projections.sum("salesAmount"));
		projList.add(Projections.sum("returnAmount"));
		projList.add(Projections.sum("netSalesAmount").as("sumNetAmount"));
		projList.add(Projections.sum("qxQuantity"));
		projList.add(Projections.sum("qxAmount"));
		projList.add(Projections.sum("myQuantity"));
		projList.add(Projections.sum("myAmount"));
		criteria.setProjection(projList);
		criteria.add(Restrictions.between("reportDate", startDate, endDate));
		criteria.addOrder(Order.desc("sumNetAmount"));
		
		List<Object> result = chainDailySalesDaoImpl.getByCriteriaProjection(criteria,  false);
		for (int i = 0; i < result.size(); i++){
			  Object object = result.get(i);
			  if (object != null){
				 Object[] recordResult = (Object[])object;
				 int chainId = Common_util.getInt(recordResult[0]);
				 int salesQ = Common_util.getInt(recordResult[1]);
				 int returnQ = Common_util.getInt(recordResult[2]);
				 int netQ = Common_util.getInt(recordResult[3]);
				 int freeQ = Common_util.getInt(recordResult[4]);
				 double salesAmt = Common_util.getDouble(recordResult[5]);
				 double returnAmt = Common_util.getDouble(recordResult[6]);
				 double netAmt = Common_util.getDouble(recordResult[7]);
				 int qxQ = Common_util.getInt(recordResult[8]);
				 double qxAmount = Common_util.getDouble(recordResult[9]);
				 int myQ = Common_util.getInt(recordResult[10]);
				 double myAmount = Common_util.getDouble(recordResult[11]);
				 
				 int rank = i +1 ;
				 loggerLocal.infoB(new Date() + " 开始排名 : "+ rank +", " + chainId +", " + netQ +", " +netAmt);
				 
				 ChainStore chainStore = new ChainStore();
				 chainStore.setChain_id(chainId);
				 
				 ChainWeeklySales weeklyRank = new ChainWeeklySales(chainStore, startDate, salesQ, returnQ, netQ, freeQ, salesAmt, returnAmt, netAmt, rank, qxQ, qxAmount, myQ, myAmount);
				 try {
				     chainWeeklySalesDaoImpl.saveOrUpdate(weeklyRank, false);
				 } catch (Exception e) {
					e.printStackTrace();
				}
			  }
		} 
		
		loggerLocal.infoB(new Date() + " 完成 weekly Batch job排名 :  ChainDailySalesService.runWeeklyRank()");
	}
	
	/**
	 * 每周一早上运行上周的热销品牌和热销款式
	 * 
	 */
//	public void runWeeklyHotBrandProduct(){
//		loggerLocal.infoB(new Date() + " 开始 *周* 热销品牌 Batch job :  ChainDailySalesService.runWeeklyHotBrandProduct()");
//		
//		List<java.sql.Date> lastWeekDays = Common_util.getLastWeekDays();
//		java.sql.Date startDate = lastWeekDays.get(0);
//		java.sql.Date endDate = lastWeekDays.get(6);
//		
//		int numOfActiveChain = chainStoreService.getNumOfActiveChainStore();
//		
//		/**
//		 * 1. 查找所有连锁店热销品牌,
//		 */
//		loggerLocal.infoB(new Date() + " 查找所有连锁店 *周* 热销品牌");
//		Object[] value_sale = new Object[]{ChainStoreSalesOrder.STATUS_COMPLETE,startDate,endDate};
//		
//		String selectHotBrand = "SELECT SUM(quantity), productBarcode.product.brand.brand_ID, type FROM ChainStoreSalesOrderProduct sp WHERE sp.chainSalesOrder.status =? AND sp.chainSalesOrder.orderDate BETWEEN ? AND ? GROUP BY productBarcode.product.brand.brand_ID, type";
//		List<Object> sales2 = (List<Object>)chainSalesOrderDaoImpl.executeHQLSelect(selectHotBrand, value_sale,null, false);
//
//		Map<Integer, Integer> brandMap = putListToChainMap(sales2);
//
//		List<Map.Entry<Integer, Integer>> hotBrands = new ArrayList<Map.Entry<Integer,Integer>>(brandMap.entrySet());
//		Collections.sort(hotBrands, new Comparator<Map.Entry<Integer,Integer>>(){ 
//			   public int compare(Map.Entry<Integer,Integer> mapping1,Map.Entry<Integer,Integer> mapping2){
//			     return mapping2.getValue() - mapping1.getValue(); 
//			   } 
//			  }); 
//		List<Brand> hotBrandList = new ArrayList<Brand>();
//		for (int i = 0; i < hotBrands.size() && i < Common_util.HOT_BRAND_NUM; i++){
//			Map.Entry<Integer, Integer> hotBrand = hotBrands.get(i);
//			int brandId = hotBrand.getKey();
//			int sumQ = hotBrand.getValue();
//			
//			ChainStore allStore = new ChainStore();
//			allStore.setChain_id(Common_util.ALL_RECORD);
//			
//			Brand brand = new Brand();
//			brand.setBrand_ID(brandId);
//			
//			hotBrandList.add(brand);
//			
//			double avgQ = ((double)sumQ)/numOfActiveChain;
//			ChainWeeklyHotBrand chainWeeklyHotBrand = new ChainWeeklyHotBrand(startDate, allStore, brand, i+1, avgQ);
//			chainWeeklyHotBrandDaoImpl.saveOrUpdate(chainWeeklyHotBrand, false);
//		}
//		
//		loggerLocal.infoB(new Date() + "  *周* 热销品牌数量 : " + hotBrandList.size());
//		
//		if (hotBrandList.size() == 0)
//			return ;
//		
//		String brands = "(";
//		for (Brand brand: hotBrandList)
//			brands += brand.getBrand_ID() + ",";
//		brands += hotBrandList.get(0).getBrand_ID() + ")";
//		
//		/**
//		 * 2. 把每个连锁店分品牌查询出来
//		 */
//		loggerLocal.infoB(new Date() + " 查找每个连锁店的 *周* 热销品牌销售");
//		List<ChainStore> noDisabledChainStores = chainStoreService.getAvailableParentChainstores();
//		for (ChainStore chainStore : noDisabledChainStores){
//			Object[] value_saleChain = new Object[]{ChainStoreSalesOrder.STATUS_COMPLETE,startDate,endDate, chainStore.getChain_id()};
//			
//			String selectHotBrandChain = "SELECT SUM(quantity), productBarcode.product.brand.brand_ID, type FROM ChainStoreSalesOrderProduct sp WHERE sp.chainSalesOrder.status =? AND sp.chainSalesOrder.orderDate BETWEEN ? AND ? AND sp.chainSalesOrder.chainStore.chain_id=? AND productBarcode.product.brand.brand_ID IN " + brands +" GROUP BY productBarcode.product.brand.brand_ID, type";
//			List<Object> salesChain = (List<Object>)chainSalesOrderDaoImpl.executeHQLSelect(selectHotBrandChain, value_saleChain,null, false);
//
//			Map<Integer, Integer> brandMapChain = putListToChainMap(salesChain);
//			for (Brand brand: hotBrandList){
//				int brandId = brand.getBrand_ID();
//				int salesQ = 0;
//				Integer salesQObj = brandMapChain.get(brandId);
//				if (salesQObj != null)
//					salesQ = salesQObj.intValue();
//				
//				ChainWeeklyHotBrand chainWeeklyHotBrand = new ChainWeeklyHotBrand(startDate, chainStore, brand, 0, salesQ);
//				chainWeeklyHotBrandDaoImpl.saveOrUpdate(chainWeeklyHotBrand, false);
//				loggerLocal.infoB(new Date() + " 查找每个连锁店的 *周* 热销品牌销售 " + chainStore.getChain_id() + "," + brandId + "," + salesQ);
//			}
//			
//		}
//		
//		/**
//		 * 3. 把每个牌子下面的hot产品查出来
//		 *    以及每个连锁店的hot产品销量
//		 */
//		loggerLocal.infoB(new Date() + " 把每个牌子下面的 *周* hot产品查出来");
//		for (Brand brand : hotBrandList){
//			/**
//			 * 3.1 每个牌子的hot产品
//			 */
//			int brandId = brand.getBrand_ID();
//			Object[] valueByBrand = new Object[]{ChainStoreSalesOrder.STATUS_COMPLETE,startDate,endDate, brandId};
//			
//			String selectHotProduct = "SELECT SUM(quantity), productBarcode.id, type FROM ChainStoreSalesOrderProduct sp WHERE sp.chainSalesOrder.status =? AND sp.chainSalesOrder.orderDate BETWEEN ? AND ? AND productBarcode.product.brand.brand_ID =? GROUP BY productBarcode.id, type";
//			List<Object> salesProduct = (List<Object>)chainSalesOrderDaoImpl.executeHQLSelect(selectHotProduct, valueByBrand,null, false);
//
//			Map<Integer, Integer> productMap = putListToChainMap(salesProduct);
//
//			List<Map.Entry<Integer, Integer>> hotProducts = new ArrayList<Map.Entry<Integer,Integer>>(productMap.entrySet());
//			Collections.sort(hotProducts, new Comparator<Map.Entry<Integer,Integer>>(){ 
//				   public int compare(Map.Entry<Integer,Integer> mapping1,Map.Entry<Integer,Integer> mapping2){
//				     return mapping2.getValue() - mapping1.getValue(); 
//				   } 
//				  });
//			
//			List<ProductBarcode> hotProduct = new ArrayList<ProductBarcode>();
//			for (int i = 0; i < hotProducts.size() && i < Common_util.HOT_PRODUCT_NUM; i++){
//				Map.Entry<Integer, Integer> hotProductEntry = hotProducts.get(i);
//				int productId = hotProductEntry.getKey();
//				int sumQ = hotProductEntry.getValue();
//				
//				ChainStore allStore = new ChainStore();
//				allStore.setChain_id(Common_util.ALL_RECORD);
//				
//				ProductBarcode productBarcode = new ProductBarcode();
//				productBarcode.setId(productId);
//				
//				hotProduct.add(productBarcode);
//				
//				double avgQ = ((double)sumQ)/numOfActiveChain;
//				ChainWeeklyHotProduct chainWeeklyHotProduct = new ChainWeeklyHotProduct(startDate, allStore, brandId, productBarcode, i+1, avgQ);
//				chainWeeklyHotProductDaoImpl.saveOrUpdate(chainWeeklyHotProduct, false);
//			}
//			
//			loggerLocal.infoB(new Date() + " 每个牌子下面的 *周* hot产品数量 : " + brandId + "," + hotProduct.size());
//			/**
//			 * 3.2 每个连锁店的Hot product销量
//			 */
//			if (hotProduct.size() == 0)
//				return ;
//			
//			String products = "(";
//			for (ProductBarcode productBarcode: hotProduct)
//				products += productBarcode.getId() + ",";
//			products += hotProduct.get(0).getId() + ")";
//			
//			for (ChainStore chainStore : noDisabledChainStores){
//				Object[] value_saleChain = new Object[]{ChainStoreSalesOrder.STATUS_COMPLETE,startDate,endDate, chainStore.getChain_id()};
//				
//				String selectHotProductChain = "SELECT SUM(quantity), productBarcode.id, type FROM ChainStoreSalesOrderProduct sp WHERE sp.chainSalesOrder.status =? AND sp.chainSalesOrder.orderDate BETWEEN ? AND ? AND sp.chainSalesOrder.chainStore.chain_id=? AND productBarcode.id IN " + products +" GROUP BY productBarcode.id, type";
//				List<Object> salesProductChain = (List<Object>)chainSalesOrderDaoImpl.executeHQLSelect(selectHotProductChain, value_saleChain,null, false);
//
//				Map<Integer, Integer> productMapChain = putListToChainMap(salesProductChain);
//				for (ProductBarcode product: hotProduct){
//					int productId = product.getId();
//					int salesQ = 0;
//					Integer salesQObj = productMapChain.get(productId);
//					if (salesQObj != null)
//						salesQ = salesQObj.intValue();
//					
//					ChainWeeklyHotProduct chainWeeklyHotProduct = new ChainWeeklyHotProduct(startDate, chainStore, brandId, product, 0, salesQ);
//					chainWeeklyHotProductDaoImpl.saveOrUpdate(chainWeeklyHotProduct, false);
//					
//					loggerLocal.infoB(new Date() + " 每个连锁店下面的 *周* hot产品销量 : " + brandId + "," + productId + "," + salesQ);
//				}
//				
//			}
//		}
//		
//		loggerLocal.infoB(new Date() + " 完成  *周* 热销品牌 Batch job :  ChainDailySalesService.runWeeklyHotBrandProduct()");
//		
//	}
	
	/**
	 * 所有连锁店的数据的map
	 * @return
	 */
	private Map<Integer, Integer> putListToChainMap(List<Object> sales2){
		Map<Integer, Integer> brandMap = new HashMap<Integer, Integer>();
		for (Object resultObject : sales2){
			Object[] obj = (Object[])resultObject;
			int totalQ = Common_util.getInt(obj[0]);
			int id = Common_util.getInt(obj[1]);
			int type = Common_util.getInt(obj[2]);
			Integer q = brandMap.get(id);

			if (q == null){
				q = 0;
			}
			if (type == ChainStoreSalesOrderProduct.RETURN_BACK)
				totalQ *= -1;
//			else if (type == ChainStoreSalesOrderProduct.FREE)
//				totalQ *= 0;
			
			brandMap.put(id, totalQ + q);
		}
		
		return brandMap;
	}
	
	/**
	 * 每个月第一天运行当月热销品牌，和当月的active chain
	 */
//	public void runMonthlyHotBrandProduct(){
//        today = Calendar.getInstance();
//        today.add(Calendar.MONTH, -1);
//        today.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天  
//		java.sql.Date startDate = new java.sql.Date(today.getTime().getTime());
//		
//		today.set(Calendar.DAY_OF_MONTH, today.getActualMaximum(Calendar.DAY_OF_MONTH));
//		java.sql.Date endDate = new java.sql.Date(today.getTime().getTime());
//		
//		loggerLocal.infoB(new Date() + ": 开始 " +  startDate +  " *月* 热销品牌 Batch job :  ChainDailySalesService.runMonthlyHotBrandProduct()");
//				
//		int numOfActiveChain = chainStoreService.getNumOfActiveChainStore();
//		
//		ChainMonthlyActiveNum monthlyActiveNum = new ChainMonthlyActiveNum(startDate, numOfActiveChain);
//		chainMonthlyActiveNumDaoImpl.saveOrUpdate(monthlyActiveNum, true);
//		
//		/**
//		 * 1. 查找所有连锁店热销品牌,
//		 */
//		loggerLocal.infoB(new Date() + " 查找所有连锁店 " +  startDate +  "月* 热销品牌");
//		Object[] value_sale = new Object[]{ChainStoreSalesOrder.STATUS_COMPLETE,startDate,endDate};
//		
//		String selectHotBrand = "SELECT SUM(quantity), productBarcode.product.brand.brand_ID, type FROM ChainStoreSalesOrderProduct sp WHERE sp.chainSalesOrder.status =? AND sp.chainSalesOrder.orderDate BETWEEN ? AND ? GROUP BY productBarcode.product.brand.brand_ID, type";
//		List<Object> sales2 = (List<Object>)chainSalesOrderDaoImpl.executeHQLSelect(selectHotBrand, value_sale,null, false);
//
//		Map<Integer, Integer> brandMap = putListToChainMap(sales2);
//
//		List<Map.Entry<Integer, Integer>> hotBrands = new ArrayList<Map.Entry<Integer,Integer>>(brandMap.entrySet());
//		Collections.sort(hotBrands, new Comparator<Map.Entry<Integer,Integer>>(){ 
//			   public int compare(Map.Entry<Integer,Integer> mapping1,Map.Entry<Integer,Integer> mapping2){
//			     return mapping2.getValue() - mapping1.getValue(); 
//			   } 
//			  }); 
//		List<Brand> hotBrandList = new ArrayList<Brand>();
//		for (int i = 0; i < hotBrands.size() && i < Common_util.HOT_BRAND_NUM; i++){
//			Map.Entry<Integer, Integer> hotBrand = hotBrands.get(i);
//			int brandId = hotBrand.getKey();
//			int sumQ = hotBrand.getValue();
//			
//			ChainStore allStore = new ChainStore();
//			allStore.setChain_id(Common_util.ALL_RECORD);
//			
//			Brand brand = new Brand();
//			brand.setBrand_ID(brandId);
//			
//			hotBrandList.add(brand);
//
//			ChainMonthlyHotBrand chainWeeklyHotBrand = new ChainMonthlyHotBrand(startDate, allStore, brand, i+1, sumQ);
//			chainMonthlyHotBrandDaoImpl.saveOrUpdate(chainWeeklyHotBrand, false);
//		}
//		
//		loggerLocal.infoB(new Date() + "  " +  startDate +  " 月* 热销品牌数量 : " + hotBrandList.size());
//		
//		if (hotBrandList.size() == 0)
//			return ;
//		
//		String brands = "(";
//		for (Brand brand: hotBrandList)
//			brands += brand.getBrand_ID() + ",";
//		brands += hotBrandList.get(0).getBrand_ID() + ")";
//		
//		/**
//		 * 2. 把每个连锁店分品牌查询出来
//		 */
//		loggerLocal.infoB(new Date() + " 查找每个连锁店的 " +  startDate +  " 月* 热销品牌销售");
//		List<ChainStore> noDisabledChainStores = chainStoreService.getAvailableParentChainstores();
//		for (ChainStore chainStore : noDisabledChainStores){
//			Object[] value_saleChain = new Object[]{ChainStoreSalesOrder.STATUS_COMPLETE,startDate,endDate, chainStore.getChain_id()};
//			
//			String selectHotBrandChain = "SELECT SUM(quantity), productBarcode.product.brand.brand_ID, type FROM ChainStoreSalesOrderProduct sp WHERE sp.chainSalesOrder.status =? AND sp.chainSalesOrder.orderDate BETWEEN ? AND ? AND sp.chainSalesOrder.chainStore.chain_id=? AND productBarcode.product.brand.brand_ID IN " + brands +" GROUP BY productBarcode.product.brand.brand_ID, type";
//			List<Object> salesChain = (List<Object>)chainSalesOrderDaoImpl.executeHQLSelect(selectHotBrandChain, value_saleChain,null, false);
//
//			Map<Integer, Integer> brandMapChain = putListToChainMap(salesChain);
//			for (Brand brand: hotBrandList){
//				int brandId = brand.getBrand_ID();
//				int salesQ = 0;
//				Integer salesQObj = brandMapChain.get(brandId);
//				if (salesQObj != null)
//					salesQ = salesQObj.intValue();
//				
//				ChainMonthlyHotBrand chainWeeklyHotBrand = new ChainMonthlyHotBrand(startDate, chainStore, brand, 0, salesQ);
//				chainMonthlyHotBrandDaoImpl.saveOrUpdate(chainWeeklyHotBrand, false);
//				loggerLocal.infoB(new Date() + " 查找每个连锁店的  " +  startDate +  " 月* 热销品牌销售 " + chainStore.getChain_id() + "," + brandId + "," + salesQ);
//			}
//			
//		}
//		
//		/**
//		 * 3. 把每个牌子下面的hot产品查出来
//		 *    以及每个连锁店的hot产品销量
//		 */
//		loggerLocal.infoB(new Date() + " 把每个牌子下面的  " +  startDate +  " 月* hot产品查出来");
//		for (Brand brand : hotBrandList){
//			/**
//			 * 3.1 每个牌子的hot产品
//			 */
//			int brandId = brand.getBrand_ID();
//			Object[] valueByBrand = new Object[]{ChainStoreSalesOrder.STATUS_COMPLETE,startDate,endDate, brandId};
//			
//			String selectHotProduct = "SELECT SUM(quantity), productBarcode.id, type FROM ChainStoreSalesOrderProduct sp WHERE sp.chainSalesOrder.status =? AND sp.chainSalesOrder.orderDate BETWEEN ? AND ? AND productBarcode.product.brand.brand_ID =? GROUP BY productBarcode.id, type";
//			List<Object> salesProduct = (List<Object>)chainSalesOrderDaoImpl.executeHQLSelect(selectHotProduct, valueByBrand,null, false);
//
//			Map<Integer, Integer> productMap = putListToChainMap(salesProduct);
//
//			List<Map.Entry<Integer, Integer>> hotProducts = new ArrayList<Map.Entry<Integer,Integer>>(productMap.entrySet());
//			Collections.sort(hotProducts, new Comparator<Map.Entry<Integer,Integer>>(){ 
//				   public int compare(Map.Entry<Integer,Integer> mapping1,Map.Entry<Integer,Integer> mapping2){
//				     return mapping2.getValue() - mapping1.getValue(); 
//				   } 
//				  });
//			
//			List<ProductBarcode> hotProduct = new ArrayList<ProductBarcode>();
//			for (int i = 0; i < hotProducts.size() && i < Common_util.MONTHLY_HOT_PRODUCT_NUM; i++){
//				Map.Entry<Integer, Integer> hotProductEntry = hotProducts.get(i);
//				int productId = hotProductEntry.getKey();
//				int sumQ = hotProductEntry.getValue();
//				
//				ChainStore allStore = new ChainStore();
//				allStore.setChain_id(Common_util.ALL_RECORD);
//				
//				ProductBarcode productBarcode = new ProductBarcode();
//				productBarcode.setId(productId);
//				
//				hotProduct.add(productBarcode);
//
//				ChainMonthlyHotProduct chainWeeklyHotProduct = new ChainMonthlyHotProduct(startDate, allStore, brandId, productBarcode, i+1, sumQ);
//				chainMonthlyHotProductDaoImpl.saveOrUpdate(chainWeeklyHotProduct, false);
//			}
//			
//			loggerLocal.infoB(new Date() + " 每个牌子下面的  " +  startDate +  " 月* hot产品数量 : " + brandId + "," + hotProduct.size());
//			/**
//			 * 3.2 每个连锁店的Hot product销量
//			 */
//			if (hotProduct.size() == 0)
//				return ;
//			
//			String products = "(";
//			for (ProductBarcode productBarcode: hotProduct)
//				products += productBarcode.getId() + ",";
//			products += hotProduct.get(0).getId() + ")";
//			
//			for (ChainStore chainStore : noDisabledChainStores){
//				Object[] value_saleChain = new Object[]{ChainStoreSalesOrder.STATUS_COMPLETE,startDate,endDate, chainStore.getChain_id()};
//				
//				String selectHotProductChain = "SELECT SUM(quantity), productBarcode.id, type FROM ChainStoreSalesOrderProduct sp WHERE sp.chainSalesOrder.status =? AND sp.chainSalesOrder.orderDate BETWEEN ? AND ? AND sp.chainSalesOrder.chainStore.chain_id=? AND productBarcode.id IN " + products +" GROUP BY productBarcode.id, type";
//				List<Object> salesProductChain = (List<Object>)chainSalesOrderDaoImpl.executeHQLSelect(selectHotProductChain, value_saleChain,null, false);
//
//				Map<Integer, Integer> productMapChain = putListToChainMap(salesProductChain);
//				for (ProductBarcode product: hotProduct){
//					int productId = product.getId();
//					int salesQ = 0;
//					Integer salesQObj = productMapChain.get(productId);
//					if (salesQObj != null)
//						salesQ = salesQObj.intValue();
//					
//					if (salesQ != 0){
//						ChainMonthlyHotProduct chainWeeklyHotProduct = new ChainMonthlyHotProduct(startDate, chainStore, brandId, product, 0, salesQ);
//						chainMonthlyHotProductDaoImpl.saveOrUpdate(chainWeeklyHotProduct, false);
//					}
//					
//					loggerLocal.infoB(new Date() + " 每个连锁店下面的  " +  startDate +  " 月* hot产品销量 : " + brandId + "," + productId + "," + salesQ);
//				}
//				
//			}
//		}
//		
//		loggerLocal.infoB(new Date() + " 完成  " +  startDate +  " 月* 热销品牌 Batch job :  ChainDailySalesService.runMonthlyHotBrandProduct()");
//				
//	}
	
	/**
	 * 补充前几个月的月热销品牌
	 */
//	public void runMontlyDummyHotBrandProduct(){
//		for (int i = 0; i < 1; i++){
//			Calendar date2 = Calendar.getInstance();
//			date2.add(Calendar.MONTH, -1*i);
//			runMonthlyHotBrandProduct();
//		}
//	}
	
	/**
	 * 补齐之前排名
	 * 从8-2
	 */
	public void runDailyDummyBatch(){

		Thread current = Thread.currentThread();  
		String threadId = String.valueOf(current.getId());
		
		loggerLocal.infoB(new Date() +" " + threadId + "  开始  ：  ChainDailySalesService.runDailyDummyBatch()");
		
		Calendar start = Calendar.getInstance();
		start.set(2015, 3, 21);
		
		Calendar end = Calendar.getInstance();
        
		int offset = (int)((end.getTimeInMillis()-start.getTimeInMillis())/(1000*3600*24));
		loggerLocal.infoB(threadId + " offset : " + offset);
		
		for (int j = 0; j < offset ; j++){
			start.add(Calendar.DAY_OF_MONTH, 1);
			java.sql.Date yestorday = new java.sql.Date(start.getTimeInMillis());
			
			String dailyBatchSelect = "SELECT sum(totalQuantity), sum(netAmount), sum(totalQuantityR), " +
					"sum(netAmountR), sum(totalCost), sum(totalQuantityF) ,sum(totalCostF), sum(discountAmount), " +
					"sum(coupon), sum (cardAmount), sum(cashAmount - returnAmount), sum(vipScore), chainStore.chain_id, sum(netAmount - netAmountR)  from ChainStoreSalesOrder where orderDate = ? and status = ?";
			/**
			 * @1. 运行当天的销售情况
			 */
			loggerLocal.infoB(new Date() + " " + threadId + " 开始Batch job :  ChainDailySalesService.runDailyBatch()");

			Object[] value_sale = new Object[]{yestorday,ChainStoreSalesOrder.STATUS_COMPLETE};
			
			String hql_sale = dailyBatchSelect + " and chainStore.chain_id <> " + ChainStore.CHAIN_ID_TEST_ID + " GROUP BY chainStore.chain_id";

			List<Object> sales2 = (List<Object>)chainSalesOrderDaoImpl.executeHQLSelect(hql_sale, value_sale,null, true);

			Set<Integer> chainIdSet = new HashSet<Integer>();
			int i  = 0;
			Collections.sort(sales2, new ChainDailySalesSorter());
			for (i = 0; i < sales2.size(); i++){
				Object resultObject = sales2.get(i);
				Object[] sales3 = (Object[])resultObject;
			    dailyReportMappingAndSave(sales3, 9999,  threadId, yestorday, i+1);
			    if (sales3 != null){
			    	int chainId =  Common_util.getInt(sales3[12]);
			    	chainIdSet.add(chainId);
			    }
			}
			List<ChainStore> availableChainStores = chainStoreService.getAvailableParentChainstores();
			for (ChainStore store: availableChainStores){
				//需要补充数据
				if (!chainIdSet.contains(store.getChain_id())){
					loggerLocal.infoB(threadId + " 开始补充连锁店信息 : " + store.getChain_id());
					Object[] emptyObjects = new Object[]{0,0,0,0,0,0,0,0,0,0,0,0,store.getChain_id()};
					dailyReportMappingAndSave(emptyObjects, store.getChain_id(),  threadId, yestorday,++i);
				}
			}

			/*
			DetachedCriteria criteria = DetachedCriteria.forClass(ChainDailySales.class);
			criteria.add(Restrictions.eq("reportDate", day));
			criteria.addOrder(Order.desc("netSalesAmount"));

			List<ChainDailySales> sales2 = chainDailySalesDaoImpl.getByCritera(criteria, false);

			for (int j = 0; j < sales2.size(); j++){
				ChainDailySales resultObject = sales2.get(j);
				resultObject.setRank(j+1);
				
				chainDailySalesDaoImpl.saveOrUpdate(resultObject, false);
				
				loggerLocal.infoB(threadId + "runDailyDummyBatch update : " + day + "," + resultObject.getChainStore().getChain_id() + "," + j);
			}*/
		}
		
		loggerLocal.infoB(new Date() +" " + threadId + " 完成 Batch job ：  ChainDailySalesService.runDailyDummyBatch()");
	}
	

	public void prepareGenChartUI(ChainSalesChartUIBean uiBean, ChainSalesChartFormBean formBean, ChainUserInfor loginUser) {
		List<ChainStore> stores = chainStoreService.getChainStoreList(loginUser);
		uiBean.setChainStores(stores);
		
		formBean.setStartDate(Common_util.getLastMonthDay());
		
		formBean.setEndDate(Common_util.getYestorday());
	}

	public void prepareGenWeeklyHotBrandUI(ChainSalesChartUIBean uiBean,
			ChainSalesChartFormBean formBean, ChainUserInfor userInfor) {
		List<ChainStore> stores = chainStoreService.getChainStoreList(userInfor);
		uiBean.setChainStores(stores);
		
		if (!ChainUserInforService.isMgmtFromHQ(userInfor)){
			int chainId = userInfor.getMyChainStore().getChain_id();
			ChainStore chainStore = chainStoreService.getChainStoreByID(chainId);
			formBean.setChainStore(chainStore);
		} else {
			ChainStore allChainStore = ChainStoreDaoImpl.getAllChainStoreObject();
			formBean.setChainStore(allChainStore);
		}
		
		formBean.setStartDate(new java.sql.Date(Common_util.getLastMonday().getTime()));
		

	}
	
	public void prepareGenMonthlyHotBrandUI(ChainSalesChartUIBean uiBean,
			ChainSalesChartFormBean formBean, ChainUserInfor userInfor) {
		List<ChainStore> stores = chainStoreService.getChainStoreList(userInfor);
		uiBean.setChainStores(stores);
		
		if (!ChainUserInforService.isMgmtFromHQ(userInfor)){
			int chainId = userInfor.getMyChainStore().getChain_id();
			ChainStore chainStore = chainStoreService.getChainStoreByID(chainId);
			formBean.setChainStore(chainStore);
		} else {
			ChainStore allChainStore = ChainStoreDaoImpl.getAllChainStoreObject();
			formBean.setChainStore(allChainStore);
		}
		
		List<Integer> yearList = new ArrayList<Integer>();
		List<Integer> monthList = new ArrayList<Integer>();
		
		List<Year> years = yearDaoImpl.getAll(true);
		Collections.sort(years, new SortYear());
		for (Year year: years){
			int yearInt = new Integer(year.getYear());
			if (yearInt >= 2014)
			  yearList.add(yearInt);
		}
		
		for (int i = 1; i <= 12; i++)
			monthList.add(new Integer(i));
		
		uiBean.setReportMonthList(monthList);
		uiBean.setReportYearList(yearList);
		
	}

	/**
	 * 获取当周热销的品牌
	 * @param startDate
	 * @param chain_id
	 * @return
	 */
	public List<ChainWeeklyHotBrand> genWeeklyHotBrands(java.sql.Date startDate, int chain_id) {

		java.sql.Date monday = Common_util.getMonday(startDate);
		/**
		 * 1. 获取热销品牌，的连锁店平均
		 */
		DetachedCriteria allCriteria = DetachedCriteria.forClass(ChainWeeklyHotBrand.class);
		allCriteria.add(Restrictions.eq("reportDate", monday));
		allCriteria.add(Restrictions.eq("chainStore.chain_id", Common_util.ALL_RECORD));
		allCriteria.addOrder(Order.asc("rank"));
		List<ChainWeeklyHotBrand> hotBrands = chainWeeklyHotBrandDaoImpl.getByCritera(allCriteria, true);
		
		/**
		 * 2. 获取本连锁店的销售情况
		 */
		if (chain_id != Common_util.ALL_RECORD){
			DetachedCriteria chainCriteria = DetachedCriteria.forClass(ChainWeeklyHotBrand.class);
			chainCriteria.add(Restrictions.eq("reportDate", monday));
			chainCriteria.add(Restrictions.eq("chainStore.chain_id", chain_id));
			List<ChainWeeklyHotBrand> chainHotBrands = chainWeeklyHotBrandDaoImpl.getByCritera(chainCriteria, true);
			
			/**
			 * 2.1 组成map
			 */
			Map<Integer, ChainWeeklyHotBrand> hotBrandMap = new HashMap<Integer, ChainWeeklyHotBrand>();
			for (ChainWeeklyHotBrand hotBrand : chainHotBrands){
				hotBrandMap.put(hotBrand.getBrand().getBrand_ID(), hotBrand);
			}
			
			/**
			 * 2.2 把连锁店的sale放进去
			 */
			for (ChainWeeklyHotBrand hotBrand : hotBrands){
				ChainWeeklyHotBrand chainBrandQ = hotBrandMap.get(hotBrand.getBrand().getBrand_ID());
				if (chainBrandQ != null){
					hotBrand.setMySalesQuantity(chainBrandQ.getSalesQuantity());
				}
			}
			
		}
		return hotBrands;
	}

	/**
	 * 生成某个品牌下热销的产品
	 * @param chain_id
	 * @param brandId
	 * @param startDate
	 * @return
	 */
	@Transactional
	public Response genWeeklyHotProductInBrand(int chain_id, int brandId,
			java.sql.Date startDate) {
		Response response = new Response();
		
		java.sql.Date monday = Common_util.getMonday(startDate);
		/**
		 * 1. 获取热销品牌，的连锁店平均
		 */
		DetachedCriteria allCriteria = DetachedCriteria.forClass(ChainWeeklyHotProduct.class);
		allCriteria.add(Restrictions.eq("reportDate", monday));
		allCriteria.add(Restrictions.eq("chainStore.chain_id", Common_util.ALL_RECORD));
		allCriteria.add(Restrictions.eq("brandId", brandId));
		allCriteria.addOrder(Order.asc("rank"));
		List<ChainWeeklyHotProduct> hotProducts = chainWeeklyHotProductDaoImpl.getByCritera(allCriteria, true);
		
		/**
		 * 2. 获取本连锁店的销售情况
		 */
		if (chain_id != Common_util.ALL_RECORD){
			DetachedCriteria chainCriteria = DetachedCriteria.forClass(ChainWeeklyHotProduct.class);
			chainCriteria.add(Restrictions.eq("reportDate", monday));
			chainCriteria.add(Restrictions.eq("chainStore.chain_id", chain_id));
			List<ChainWeeklyHotProduct> chainHotProducts = chainWeeklyHotProductDaoImpl.getByCritera(chainCriteria, true);
			
			/**
			 * 2.1 组成map
			 */
			Map<Integer, ChainWeeklyHotProduct> hotProductMap = new HashMap<Integer, ChainWeeklyHotProduct>();
			for (ChainWeeklyHotProduct hotProduct : chainHotProducts){
				hotProductMap.put(hotProduct.getProductBarcode().getId(), hotProduct);
			}
			
			/**
			 * 2.2 把连锁店的sale放进去
			 */
			for (ChainWeeklyHotProduct hotProduct : hotProducts){
				ChainWeeklyHotProduct chainProduct = hotProductMap.get(hotProduct.getProductBarcode().getId());
				if (chainProduct != null){
					hotProduct.setMySalesQuantity(chainProduct.getSalesQuantity());
				}
			}
			
		}

		response.setReturnValue(hotProducts);
		response.setReturnCode(Response.SUCCESS);
		
		return response;
	}
	
	/**
	 * 生成monthlyhot brand
	 * @param reportYear
	 * @param reportMonths
	 * @param chain_id
	 * @return
	 */
	public List<ChainMonthlyHotBrand> genMonthlyHotBrands(int reportYear,
			List<Integer> reportMonths, int chain_id) {
		List<ChainMonthlyHotBrand> hotBrands = new ArrayList<ChainMonthlyHotBrand>();
		
		if (reportYear == 0 || reportMonths == null || reportMonths.size() == 0)
			 return hotBrands;
		else {
			Set<java.sql.Date> reportDates = new HashSet<java.sql.Date>();
			for (Integer month : reportMonths){
		         Calendar date=Calendar.getInstance(); 
		         date.set(reportYear, month - 1, 1);
		         reportDates.add(new java.sql.Date(date.getTimeInMillis()));
			}
			Map<Integer, ChainMonthlyHotBrand> hotBrandMap = new HashMap<Integer, ChainMonthlyHotBrand>();
			/**
			 * 1. 获取热销品牌，的连锁店总量
			 */
			DetachedCriteria allCriteria = DetachedCriteria.forClass(ChainMonthlyHotBrand.class);
			
			allCriteria.add(Restrictions.in("reportDate", reportDates));
			allCriteria.add(Restrictions.eq("chainStore.chain_id", Common_util.ALL_RECORD));
			
			ProjectionList projList = Projections.projectionList();
			projList.add(Projections.groupProperty("brand.brand_ID"));
			projList.add(Projections.sum("salesQuantity"), "sum");
			allCriteria.setProjection(projList);
			allCriteria.addOrder(Order.desc("sum"));
			
			List<Object> result = chainMonthlyHotBrandDaoImpl.getByCriteriaProjection(allCriteria, true);
			//1. to put the result to stock map
			for (int i = 0; i < result.size() ; i++){
			  Object object = result.get(i);
			  if (object != null){
				Object[] recordResult = (Object[])object;
				Integer brandId = (Integer)recordResult[0];
				Double sum =  (Double)recordResult[1];
				Brand brand = brandDaoImpl.get(brandId, true);
				ChainMonthlyHotBrand hotBrand = new ChainMonthlyHotBrand(null, null, brand, i+1, sum);
				hotBrandMap.put(brandId, hotBrand);
			  } 
			}
			
			/**
			 * 2. 获取连锁店的销售
			 */
			if (chain_id != Common_util.ALL_RECORD){
				DetachedCriteria criteria = DetachedCriteria.forClass(ChainMonthlyHotBrand.class);
				
				criteria.add(Restrictions.in("reportDate", reportDates));
				criteria.add(Restrictions.eq("chainStore.chain_id", chain_id));
				
				ProjectionList projList2 = Projections.projectionList();
				projList2.add(Projections.groupProperty("brand.brand_ID"));
				projList2.add(Projections.sum("salesQuantity"), "sum");
				criteria.setProjection(projList2);
				
				List<Object> result2 = chainMonthlyHotBrandDaoImpl.getByCriteriaProjection(criteria, true);
				//1. to put the result to stock map
				for (int i = 0; i < result2.size() ; i++){
				  Object object = result2.get(i);
				  if (object != null){
					Object[] recordResult = (Object[])object;
					Integer brandId = (Integer)recordResult[0];
					Double sum =  (Double)recordResult[1];
					
					ChainMonthlyHotBrand hotBrand = hotBrandMap.get(brandId);
					if (hotBrand == null ){
						loggerLocal.error("Erorr: 在计算monthly hot brand出现错误 :" + brandId);
						continue;
					} else 
						hotBrand.setMySalesQuantity(sum);
					
				  } 
				}
			}
			
			hotBrands = new ArrayList<ChainMonthlyHotBrand>(hotBrandMap.values());
			Collections.sort(hotBrands, new Comparator<ChainMonthlyHotBrand>() {

				@Override
				public int compare(ChainMonthlyHotBrand o1,
						ChainMonthlyHotBrand o2) {
					int rank1 = o1.getRank();
					int rank2 = o2.getRank();
					
					return rank1 - rank2;
				}
			});
			return hotBrands;
		}
	}
	
	/**
	 * 获取每月某个品牌的热销product
	 * @param chain_id
	 * @param brandId
	 * @param reportYear
	 * @param months
	 * @return
	 */
	public Response genMonthlyHotProductInBrand(int chain_id, int brandId,
			int reportYear, List<Integer> reportMonths) {
		Response response = new Response();
		
		Set<java.sql.Date> reportDates = new HashSet<java.sql.Date>();
		for (Integer month : reportMonths){
	         Calendar date=Calendar.getInstance(); 
	         date.set(reportYear, month - 1, 1);
	         reportDates.add(new java.sql.Date(date.getTimeInMillis()));
		}
		
		List<ChainMonthlyHotProduct> hotProducts = new ArrayList<ChainMonthlyHotProduct>();
		Map<Integer, ChainMonthlyHotProduct> hotProductMap = new HashMap<Integer, ChainMonthlyHotProduct>();
		
		/**
		 * 1. 获取热销品牌，的连锁店平均
		 */
		DetachedCriteria allCriteria = DetachedCriteria.forClass(ChainMonthlyHotProduct.class);
		allCriteria.add(Restrictions.in("reportDate", reportDates));
		allCriteria.add(Restrictions.eq("chainStore.chain_id", Common_util.ALL_RECORD));
		allCriteria.add(Restrictions.eq("brandId", brandId));
		
		ProjectionList projList = Projections.projectionList();
		projList.add(Projections.groupProperty("productBarcode.id"));
		projList.add(Projections.sum("salesQuantity"), "sum");
		allCriteria.setProjection(projList);
		allCriteria.addOrder(Order.desc("sum"));
		
		List<Object> result = chainMonthlyHotBrandDaoImpl.getByCriteriaProjection(allCriteria, true);
		
		//1. to put the result to stock map
		for (int i = 0; i < result.size() ; i++){
		  Object object = result.get(i);
		  if (object != null){
			Object[] recordResult = (Object[])object;
			Integer pbId = (Integer)recordResult[0];
			Double sum =  (Double)recordResult[1];
			ProductBarcode pb = productBarcodeDaoImpl.get(pbId, true);
			ChainMonthlyHotProduct hotProduct = new ChainMonthlyHotProduct(null, null, brandId, pb, i+1, sum);
			hotProductMap.put(pbId, hotProduct);
		  } 
		}
		
		/**
		 * 2. 获取本连锁店的销售情况
		 */
		if (chain_id != Common_util.ALL_RECORD){
			DetachedCriteria criteria = DetachedCriteria.forClass(ChainMonthlyHotProduct.class);
			criteria.add(Restrictions.in("reportDate", reportDates));
			criteria.add(Restrictions.eq("chainStore.chain_id", chain_id));
			criteria.add(Restrictions.eq("brandId", brandId));
			
			ProjectionList projList2 = Projections.projectionList();
			projList2.add(Projections.groupProperty("productBarcode.id"));
			projList2.add(Projections.sum("salesQuantity"), "sum");
			criteria.setProjection(projList2);
			criteria.addOrder(Order.desc("sum"));
			
			List<Object> result2 = chainMonthlyHotBrandDaoImpl.getByCriteriaProjection(criteria, true);
			
			//1. to put the result to stock map
			for (int i = 0; i < result2.size() ; i++){
			  Object object = result2.get(i);
			  if (object != null){
				Object[] recordResult = (Object[])object;
				Integer pbId = (Integer)recordResult[0];
				Double sum =  (Double)recordResult[1];
				
				ChainMonthlyHotProduct hotProduct = hotProductMap.get(pbId);
				if (hotProduct == null ){
					loggerLocal.error("Erorr: 在计算monthly hot brand出现错误 :" + pbId);
					continue;
				} else 
					hotProduct.setMySalesQuantity(sum);
			  } 
			}
		}
		
		hotProducts = new ArrayList<ChainMonthlyHotProduct>(hotProductMap.values());
		Collections.sort(hotProducts, new Comparator<ChainMonthlyHotProduct>() {

			@Override
			public int compare(ChainMonthlyHotProduct o1,
					ChainMonthlyHotProduct o2) {
				int rank1 = o1.getRank();
				int rank2 = o2.getRank();
				
				return rank1 - rank2;
			}
		});

		response.setReturnValue(hotProducts);
		response.setReturnCode(Response.SUCCESS);
		
		return response;
	}

	/**
	 * 生成 daily sales report
	 * @param chainId
	 * @param start
	 * @param end
	 * @return
	 */
	public Response generateSalesDailyReport(int chainId, java.sql.Date start,
			java.sql.Date end) {
		DecimalFormat df = new DecimalFormat("#.0");
		Response response = new Response();
		
		ChainStore store = chainStoreService.getChainStoreByID(chainId);
		
		Date yestorday = Common_util.getYestorday();
		if (end.after(yestorday))
			end = new java.sql.Date(yestorday.getTime());
		
		//1. 获取连锁店的销售
		DetachedCriteria chainCriteria = DetachedCriteria.forClass(ChainDailySales.class);
		chainCriteria.add(Restrictions.eq("chainStore.chain_id", chainId));
		chainCriteria.add(Restrictions.between("reportDate", start, end));
		chainCriteria.addOrder(Order.asc("reportDate"));
		Map<java.sql.Date, ChainDailySales> chainDailySalesMap = new HashMap<java.sql.Date, ChainDailySales>();
		List<ChainDailySales> chainDailySales = chainDailySalesDaoImpl.getByCritera(chainCriteria, true);
		List<Object> dailySalesObj = new ArrayList<Object>();
		for (ChainDailySales dailySales : chainDailySales){
			chainDailySalesMap.put(dailySales.getReportDate(), dailySales);
		}
		
		//2. 获取连锁店平均销售
		Object[] value_sale = new Object[]{start, end};
		
		String hql_sale = "SELECT avg(netSalesAmount), reportDate from ChainDailySales where reportDate between ? and ?  GROUP BY reportDate";
		
		List<Object> sales2 = (List<Object>)chainDailySalesDaoImpl.executeHQLSelect(hql_sale, value_sale,null, true);
		List<Object> avgSalesObj = new ArrayList<Object>();
		Map<java.sql.Date, Double> chainAvgDailySalesMap = new HashMap<java.sql.Date, Double>();
		
		for (Object resultObject : sales2){
			Object[] sales3 = (Object[])resultObject;
			double sales = Common_util.getDouble(sales3[0]);
			java.sql.Date reportDate = Common_util.getDate(sales3[1]);
			chainAvgDailySalesMap.put(reportDate, sales);
		}
		
		//3. 获取前五名连锁店平均销售
		Object[] value_sale2 = new Object[]{start, end, Common_util.DAILY_TOP_AVG_SALES_NUM };
		String hql_sale2 = "SELECT avg(netSalesAmount), reportDate from ChainDailySales where reportDate between ? and ? AND rank <?  GROUP BY reportDate";
		
		List<Object> sales3 = (List<Object>)chainDailySalesDaoImpl.executeHQLSelect(hql_sale2, value_sale2,null, true);
		List<Object> toAvgObjects = new ArrayList<Object>();
		Map<java.sql.Date, Double> chainAvgDailySalesMap2 = new HashMap<java.sql.Date, Double>();

		for (Object resultObject : sales3){
			
			Object[] sales4 = (Object[])resultObject;
			double sales = Common_util.getDouble(sales4[0]);
			java.sql.Date reportDate = Common_util.getDate(sales4[1]);
			chainAvgDailySalesMap2.put(reportDate, sales);
		}
		
		//3. 获取time series
		ChartReportVO chainReportVO = new ChartReportVO();
		List<java.sql.Date> timeSeries = Common_util.getDateBetween(start, end);
		List<String> timeSeriesString = new ArrayList<String>();
		int numOfPoint = 10;
		int totalTimeSeries = timeSeries.size();
		int month = 0;
		int date = 0;
		if (totalTimeSeries <= numOfPoint){
			chainReportVO.setTicketInterval(1);
		} else {
			int interval = (totalTimeSeries + numOfPoint) / numOfPoint;
			chainReportVO.setTicketInterval(interval);
		}
		
		for (int i = 0; i < timeSeries.size(); i++){
			java.sql.Date timeSerie = timeSeries.get(i);
			month = timeSerie.getMonth()+1;
			date = timeSerie.getDate();
			timeSeriesString.add(month + "月" + date +"日");
			Double avg = chainAvgDailySalesMap.get(timeSerie);
			if (avg == null)
				avgSalesObj.add(new Double(0));
			else 
				avgSalesObj.add(Common_util.formatDouble(avg,df));
			
			ChainDailySales dailySales = chainDailySalesMap.get(timeSerie);
			if (dailySales == null)
				dailySalesObj.add(new Double(0));
			else 
				dailySalesObj.add(Common_util.formatDouble(dailySales.getNetSalesAmount(), df));
			
			Double avg2 = chainAvgDailySalesMap2.get(timeSerie);
			if (avg2 == null)
				toAvgObjects.add(new Double(0));
			else 
				toAvgObjects.add(Common_util.formatDouble(avg2,df));
		}

		
		chainReportVO.setChartName("连锁店日销售走势图");
		chainReportVO.setYserisName("日销售额");
		chainReportVO.setXcategory(timeSeriesString);
		
		ChartReportSeries dailySeries = new ChartReportSeries();
		dailySeries.setName(store.getChain_name());
		dailySeries.setValue(dailySalesObj);
		
		ChartReportSeries avgSeries = new ChartReportSeries();
		avgSeries.setName("连锁店平均销售");
		avgSeries.setValue(avgSalesObj);	
		
		ChartReportSeries topAvgSeries = new ChartReportSeries();
		topAvgSeries.setName("每日销售前" + Common_util.DAILY_TOP_AVG_SALES_NUM +"名平均数");
		topAvgSeries.setValue(toAvgObjects);	
		
		List<ChartReportSeries> dataSeries = new ArrayList<ChartReportSeries>();
		dataSeries.add(dailySeries);
		dataSeries.add(avgSeries);
		dataSeries.add(topAvgSeries);
		chainReportVO.setSeriesList(dataSeries);
		
		response.setReturnValue(chainReportVO);
		response.setReturnCode(Response.SUCCESS);
		
		return response;
	}
}
