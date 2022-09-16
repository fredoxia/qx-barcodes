package com.onlineMIS.ORM.DAO.chainS.batchRpt;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javassist.expr.NewArray;

import org.apache.naming.java.javaURLContextFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hp.hpl.sparta.xpath.ThisNodeTest;
import com.onlineMIS.ORM.DAO.Response;
import com.onlineMIS.ORM.DAO.chainS.batchRpt.ChainBatchRptService.ChainCurrentSalesAnalysisComparator;
import com.onlineMIS.ORM.DAO.chainS.chainMgmt.QxbabyConfDaoImpl;
import com.onlineMIS.ORM.DAO.chainS.inventoryFlow.ChainInOutStockDaoImpl;
import com.onlineMIS.ORM.DAO.chainS.inventoryFlow.ChainTransferFlowAcctDaoImpl;
import com.onlineMIS.ORM.DAO.chainS.inventoryFlow.ChainTransferOrderDaoImpl;
import com.onlineMIS.ORM.DAO.chainS.report.ChainBatchRptRepositotyDaoImpl;
import com.onlineMIS.ORM.DAO.chainS.report.ChainMonthlyActiveNumDaoImpl;
import com.onlineMIS.ORM.DAO.chainS.report.ChainMonthlyHotBrandDaoImpl;
import com.onlineMIS.ORM.DAO.chainS.report.ChainMonthlyHotProductDaoImpl;
import com.onlineMIS.ORM.DAO.chainS.report.ChainWeeklyHotBrandDaoImpl;
import com.onlineMIS.ORM.DAO.chainS.report.ChainWeeklyHotProductDaoImpl;
import com.onlineMIS.ORM.DAO.chainS.report.ChainWeeklySalesDaoImpl;
import com.onlineMIS.ORM.DAO.chainS.sales.ChainDailySalesDaoImpl;
import com.onlineMIS.ORM.DAO.chainS.sales.ChainDailySalesImpactDaoImpl;
import com.onlineMIS.ORM.DAO.chainS.sales.ChainStoreSalesOrderDaoImpl;
import com.onlineMIS.ORM.DAO.chainS.sales.PurchaseService;
import com.onlineMIS.ORM.DAO.chainS.user.ChainStoreDaoImpl;
import com.onlineMIS.ORM.DAO.chainS.user.ChainStoreService;
import com.onlineMIS.ORM.DAO.headQ.barCodeGentor.BrandDaoImpl;
import com.onlineMIS.ORM.DAO.headQ.barCodeGentor.ProductBarcodeDaoImpl;
import com.onlineMIS.ORM.DAO.headQ.barCodeGentor.QuarterDaoImpl;
import com.onlineMIS.ORM.DAO.headQ.barCodeGentor.YearDaoImpl;
import com.onlineMIS.ORM.DAO.headQ.inventory.InventoryOrderDAOImpl;
import com.onlineMIS.ORM.entity.chainS.batchRpt.ChainCurrentSeasonProductAnalysisItem;
import com.onlineMIS.ORM.entity.chainS.batchRpt.ChainCurrentSeasonProductAnalysisRpt;
import com.onlineMIS.ORM.entity.chainS.batchRpt.ChainCurrentSeasonSalesAnalysisItem;
import com.onlineMIS.ORM.entity.chainS.batchRpt.ChainCurrentSeasonSalesAnalysisRpt;
import com.onlineMIS.ORM.entity.chainS.batchRpt.batchRptTemplate.ChainCurrentSeasonProductAnalysisTemplate;
import com.onlineMIS.ORM.entity.chainS.batchRpt.batchRptTemplate.ChainCurrentSeasonSalesAnalysisTemplate;
import com.onlineMIS.ORM.entity.chainS.chainMgmt.QxbabyConf;
import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainInOutStock;
import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainTransferOrderFlowAcct;
import com.onlineMIS.ORM.entity.chainS.report.ChainBatchRptRepositoty;


import com.onlineMIS.ORM.entity.chainS.report.ChainSalesVIPPercentageItem;
import com.onlineMIS.ORM.entity.chainS.report.ChainTransferAcctFlowItem;
import com.onlineMIS.ORM.entity.chainS.report.rptTemplate.ChainSalesReportVIPPercentageTemplate;
import com.onlineMIS.ORM.entity.chainS.report.rptTemplate.ChainTransferAcctFlowTemplate;
import com.onlineMIS.ORM.entity.chainS.sales.ChainStoreSalesOrder;
import com.onlineMIS.ORM.entity.chainS.sales.ChainStoreSalesOrderProduct;
import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.ORM.entity.chainS.vip.ChainVIPCard;
import com.onlineMIS.ORM.entity.chainS.vip.ChainVIPType;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Product;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.ProductBarcode;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Quarter;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Year;
import com.onlineMIS.ORM.entity.headQ.inventory.InventoryOrder;
import com.onlineMIS.ORM.entity.headQ.inventory.InventoryOrderProduct;
import com.onlineMIS.common.Common_util;
import com.onlineMIS.common.FileCompressionUtil;
import com.onlineMIS.common.FileOperation;
import com.onlineMIS.common.loggerLocal;
import com.opensymphony.xwork2.ActionContext;

@Service
public class ChainBatchRptService {
	@Autowired
	private ChainStoreService chainStoreService;
	
	@Autowired
	private ChainStoreDaoImpl chainStoreDaoImpl;
	
	@Autowired
	private ChainMonthlyActiveNumDaoImpl chainMonthlyActiveNumDaoImpl;
	
	@Autowired
	private BrandDaoImpl brandDaoImpl;
	
	@Autowired
	private YearDaoImpl yearDaoImpl;
	
	@Autowired
	private QuarterDaoImpl quarterDaoImpl;
	
	@Autowired
	private ProductBarcodeDaoImpl productBarcodeDaoImpl;
	
	@Autowired
	private InventoryOrderDAOImpl inventoryOrderDAOImpl;
	
	@Autowired
	private PurchaseService purchaseService;
	
	@Autowired
	private ChainBatchRptRepositotyDaoImpl chainBatchRptRepositotyDaoImpl;
	
	@Autowired
	private QxbabyConfDaoImpl qxbabyConfDaoImpl;
	
	@Autowired
	private ChainInOutStockDaoImpl chainInOutStockDaoImpl;
	
	@Autowired
	private ChainStoreSalesOrderDaoImpl chainStoreSalesOrderDaoImpl;
	
	@Autowired
	private ChainTransferFlowAcctDaoImpl chainTransferFlowAcctDaoImpl;
	
	@Autowired
	private ChainTransferOrderDaoImpl chainTransferOrderDaoImpl;

	private Calendar today = Calendar.getInstance();
	
	/**
	 * 运行每周的批量报表程序
	 * 1. 每周运行当季货品分析报表
	 */
    @Transactional
	public Response runWeeklyCurrentSeasonProductAnalysisRpt(){

		Response response = new Response();
		
		loggerLocal.infoB(new Date() + " 开始 *周* 当季产品分析报表 :  ChainBatchRptService.runWeeklyRptBatch()");
		
		QxbabyConf qxbabyConf = qxbabyConfDaoImpl.getConf();
		Year year = yearDaoImpl.get(qxbabyConf.getYearId(), true);
		Quarter quarter = quarterDaoImpl.get(qxbabyConf.getQuarterId(), true);
		
		java.sql.Date endDate = Common_util.getYestorday();
		java.sql.Date startDate = new java.sql.Date(Common_util.calcualteDate(endDate, -6).getTime());
		
		
		loggerLocal.infoB("当季配置 :" + year.getYear() + " - " + quarter.getQuarter_Name());
		String message = startDate + "," + year.getYear() + "," + quarter.getQuarter_Name();
		
		/**
		 * 1. 获取当季所有的productBarcode
		 */
		DetachedCriteria pbCriteria = DetachedCriteria.forClass(ProductBarcode.class);
		DetachedCriteria productCriteria = pbCriteria.createCriteria("product");
		productCriteria.add(Restrictions.eq("year.year_ID", year.getYear_ID()));
		productCriteria.add(Restrictions.eq("quarter.quarter_ID", quarter.getQuarter_ID()));
		productCriteria.add(Restrictions.isNull("chainStore.chain_id"));
		List<ProductBarcode> productBarcodes = productBarcodeDaoImpl.getByCritera(pbCriteria, false);
		loggerLocal.infoB("总计多少当季货品:" + productBarcodes.size());
		
//		Set<Integer> productBarcodeIds = new HashSet<Integer>();
//		Set<String> barcodes = new HashSet<String>();
		Map<Integer, ChainCurrentSeasonProductAnalysisItem> rptItemMap = new HashMap<Integer, ChainCurrentSeasonProductAnalysisItem>();
		int numOfBarcodes = 0;
		for (ProductBarcode pb: productBarcodes){
			numOfBarcodes++;
//			productBarcodeIds.add(pb.getId());
//			barcodes.add(pb.getBarcode());
			ChainCurrentSeasonProductAnalysisItem item = new ChainCurrentSeasonProductAnalysisItem(pb);
			rptItemMap.put(pb.getId(), item);
		}
		

		if (numOfBarcodes <= 0){
			loggerLocal.infoB("无法找到当季条码数据");
			response.setMessage(message + " 无法找到当季条码数据");
			return response;
		}
		
		/**
		 * 2. 获取上市日期
		 */	
		loggerLocal.infoB("产品分析 : 获取上市日期");
		String sqlDate= "select i.productBarcode.id, MIN(i.date) from ChainInOutStock i where i.productBarcode.id in (select id from ProductBarcode b where b.product.productId in (select productId from Product where year.year_ID =? and quarter.quarter_ID=?)) group by i.productBarcode.id";
		Object[] valuesDate = {year.getYear_ID(), quarter.getQuarter_ID()};

		try {
		     List<Object> marketDateObjects = chainInOutStockDaoImpl.executeHQLSelect(sqlDate, valuesDate, null, false);
		     if (marketDateObjects != null && marketDateObjects.size() > 0){					
				for (Object object : marketDateObjects)
				  if (object != null){
					Object[] recordResult = (Object[])object;
					Integer productId = (Integer)recordResult[0];
					Timestamp marketDate =  (Timestamp)recordResult[1];
					
					ChainCurrentSeasonProductAnalysisItem item = rptItemMap.get(productId);
					if (item != null)
						item.setMarketDate(new java.sql.Date(marketDate.getTime()));
				  } 
		     }
		     chainInOutStockDaoImpl.clearSession();
		} catch (Exception e){
			loggerLocal.errorB(e);
			loggerLocal.infoB("获取market date出现错误说");
		}
		
		/**
		 * 3. 获取周采购件数
		 */
		loggerLocal.infoB("产品分析 :计算周采购件数");
		Map<Integer, Integer> purchaseWeeklyMap = new HashMap<Integer, Integer>();
		Map<Integer, Integer> inDeliveryWeeklyMap = new HashMap<Integer, Integer>();
		try {
		    calculatePurchaseMap(startDate, endDate, purchaseWeeklyMap, inDeliveryWeeklyMap, year, quarter);
		} catch (Exception e){
			loggerLocal.errorB(e);
			loggerLocal.errorB("获取周采购件数出错");
		}
		
		/**
		 * 4. 获取累计采购件数
		 */
		loggerLocal.infoB("产品分析 :获取累计采购的日期组");
		List<List<java.sql.Date>> dateList = calculateAccumulatedDates(startDate, endDate);
		
		loggerLocal.infoB("产品分析 :计算累计采购件数");
		Map<Integer, Integer> purchaseAccumulatedMap = new HashMap<Integer, Integer>();
		Map<Integer, Integer> inDeliveryAccumulatedMap = new HashMap<Integer, Integer>();		
        for (List<java.sql.Date> dates : dateList){
        	loggerLocal.infoB(new Date() + " 获取累计采购件数 : " + dates.get(0) + ", " + dates.get(1));
			try {
			    calculatePurchaseMap(dates.get(0), dates.get(1), purchaseAccumulatedMap, inDeliveryAccumulatedMap, year, quarter);
			} catch (Exception e){
				loggerLocal.errorB("获取累计采购件数出错 : " + dates.get(0) + ", " + dates.get(1));
				loggerLocal.errorB(e);
			}
        }
		
		/**
		 * 5. 获取周销售件数
		 */
		loggerLocal.infoB("产品分析 :计算周销售件数");
		Map<Integer, Integer> salesWeeklyMap = new HashMap<Integer, Integer>();
		try {
			calculateSalesMap(startDate, endDate, salesWeeklyMap, year, quarter);
		} catch (Exception e){
			loggerLocal.errorB("获取周销售出错");
			loggerLocal.errorB(e);
			
		}	
		
		/**
		 * 6. 累计销售件数
		 */
		loggerLocal.infoB("产品分析 :计算累计销售件数");
		Map<Integer, Integer> salesAccumulatedMap = new HashMap<Integer, Integer>();
        for (List<java.sql.Date> dates : dateList){
        	loggerLocal.infoB(new Date() + " 获取累计销售件数 : " + dates.get(0) + ", " + dates.get(1));
			try {
				calculateSalesMap(dates.get(0), dates.get(1), salesAccumulatedMap, year, quarter);
			} catch (Exception e){
				loggerLocal.errorB("获取累计销售件数出错 : " + dates.get(0) + ", " + dates.get(1));
				loggerLocal.errorB(e);
				
			}	
        }
		
		/**
		 * 7. 获取店铺库存件数
		 */
		loggerLocal.infoB("产品分析 :计算产品库存件数");
		String sqlInventory = "select i.productBarcode.id, sum(i.quantity) from ChainInOutStock i where i.productBarcode.id in (select id from ProductBarcode b where b.product.productId in (select productId from Product where year.year_ID =? and quarter.quarter_ID=?)) group by i.productBarcode.id";
		Object[] values = {year.getYear_ID(), quarter.getQuarter_ID()};

		try {
			List<Object> inventoryObjects = chainInOutStockDaoImpl.executeHQLSelect(sqlInventory, values, null, false);
			if (inventoryObjects != null && inventoryObjects.size() > 0){					
				for (Object object : inventoryObjects)
				  if (object != null){
					Object[] recordResult = (Object[])object;
					Integer productId = (Integer)recordResult[0];
					Integer inventory =  ((Long)recordResult[1]).intValue();
					
					ChainCurrentSeasonProductAnalysisItem item = rptItemMap.get(productId);
					if (item != null)
						item.setCurrentInentory(inventory);
				  } 
		     }
		} catch (Exception e){
			loggerLocal.errorB(e);
			loggerLocal.infoB("获取inventory 出现错误");
		}
		
		/**
		 * 8. 计算map的数据
		 */
		loggerLocal.infoB("产品分析 :计算map数据");
		ChainCurrentSeasonProductAnalysisRpt rpt = new ChainCurrentSeasonProductAnalysisRpt(chainStoreDaoImpl.getAllChainStoreObject(), year, quarter, startDate, endDate);
		
		Iterator<Integer> keys = rptItemMap.keySet().iterator();
		while (keys.hasNext()){
			int key = keys.next();
			ChainCurrentSeasonProductAnalysisItem item = rptItemMap.get(key);
			try {
			if (item != null){
				Integer weeklyPurchase = purchaseWeeklyMap.get(key);
				if (weeklyPurchase != null)
					item.setPurchaseWeekly(weeklyPurchase);
				
				Integer accumulatedPurchase = purchaseAccumulatedMap.get(key);
				if (accumulatedPurchase != null)
					item.setPurchaseAccumulated(accumulatedPurchase);
				
				Integer weeklySales = salesWeeklyMap.get(key);
				if (weeklySales != null)
					item.setSalesWeekly(weeklySales);
				
				Integer accumulatedSales = salesAccumulatedMap.get(key);
				if (accumulatedSales != null)
					item.setSalesAccumulated(accumulatedSales);
				
				Integer accumulatedInDelivery = inDeliveryAccumulatedMap.get(key);
				if (accumulatedInDelivery != null)
					item.setQuantityInDelivery(accumulatedInDelivery);
				
				item.calculateRatio();
			}
			} catch (Exception e){
				loggerLocal.error("错误 :" + e.getMessage() + ", " + key);
				loggerLocal.errorB(e);
			}
		}
		
		loggerLocal.infoB("产品分析 :排序map数据");
		List<ChainCurrentSeasonProductAnalysisItem> rptItems = new ArrayList<ChainCurrentSeasonProductAnalysisItem>(rptItemMap.values());
		Collections.sort(rptItems, new ChainCurrentProductAnalysisComparator());
		
		rpt.setRptItems(rptItems);
		
		
		String webInf = this.getClass().getClassLoader().getResource("").getPath();
		String contextPath = webInf.substring(1, webInf.indexOf("classes")).replaceAll("%20", " ");  

		/**
		 * 9. 保存数据
		 */
		
		/**
		 * 9.1 准备数据
		 */
		
		ChainBatchRptRepositoty chainBatchRptRepositoty = new ChainBatchRptRepositoty();
		chainBatchRptRepositoty.setRptId(ChainBatchRptRepositoty.TYPE_WEEKLY_PRODUCT_ANALYSIS_RPT);;
		chainBatchRptRepositoty.setRptDate(endDate);
		chainBatchRptRepositoty.setRptName(year.getYear_ID(), quarter.getQuarter_ID());
		chainBatchRptRepositoty.setRptDes(startDate, endDate, quarter);
		
		ChainBatchRptRepositoty chainBatchRptRepositoty2 = chainBatchRptRepositotyDaoImpl.getUniqueRepository(chainBatchRptRepositoty.getRptId(), chainBatchRptRepositoty.getRptDate(), chainBatchRptRepositoty.getRptName());
		if (chainBatchRptRepositoty2 != null){
			chainBatchRptRepositoty.setId(chainBatchRptRepositoty2.getId());
			chainBatchRptRepositotyDaoImpl.evict(chainBatchRptRepositoty2);
		}
			
		
	   /**
	    * 9.2 所有连锁店的数据
	    */
	   Map<String, HSSFWorkbook> bookMap = new HashMap<String, HSSFWorkbook>();
		
	   try {
		   loggerLocal.infoB("产品分析 :准备zip文件");
		   ChainCurrentSeasonProductAnalysisTemplate rptTemplate = new ChainCurrentSeasonProductAnalysisTemplate(rpt, contextPath + "template\\");
		   HSSFWorkbook wholeChainWorkbook = rptTemplate.process();
		   String wholeChainWorkbookName = rpt.getChainStore().getChain_name() + Common_util.dateFormat.format(startDate) + ".xls";
		   
		   bookMap.put(wholeChainWorkbookName, wholeChainWorkbook);
	
	       String zipFilePath = chainBatchRptRepositoty.getRptPathByType() + "\\" + chainBatchRptRepositoty.getDownloadRptName();    
	
	       loggerLocal.infoB("产品分析 :zip文件名: " + zipFilePath);
	       FileCompressionUtil.zipWorkbooks(zipFilePath, bookMap);
	       
	       //如果打包出现错误回滚
	       loggerLocal.infoB("产品分析 :写入报表数据到数据库");
		   chainBatchRptRepositotyDaoImpl.saveOrUpdate(chainBatchRptRepositoty, true);
			
	   } catch (Exception e){
		   loggerLocal.error("发生严重错误 : ");
		   loggerLocal.errorB(e);
	   }
	   
		response.setMessage(message);
		loggerLocal.infoB("------" + startDate + " 报表成功生成----");
		return response;
	
	}
	
	private List<List<java.sql.Date>> calculateAccumulatedDates(
			java.sql.Date startDate, java.sql.Date endDate) {
		List<List<java.sql.Date>> dateList = new ArrayList<List<java.sql.Date>>();
		
		//30 周
		int totalDatesGroup = 30;
		for (int i = 0; i < totalDatesGroup; i++){
			List<java.sql.Date> dates = new ArrayList<java.sql.Date>();
			
			java.sql.Date startDate2 = new java.sql.Date(Common_util.calcualteDate(startDate, i *-7).getTime());
			java.sql.Date endDate2 = new java.sql.Date(Common_util.calcualteDate(endDate, i *-7).getTime());
			dates.add(startDate2);
			dates.add(endDate2);
			
			loggerLocal.infoB(i + " , " +startDate2 + " , " + endDate2);
			dateList.add(dates);
		}
		
		return dateList;
	}

	private void calculateSalesMap(java.sql.Date startDate, java.sql.Date endDate, Map<Integer, Integer> salesMap,  Year year, Quarter quarter){
		DetachedCriteria salesCriteria = DetachedCriteria.forClass(ChainStoreSalesOrder.class);
		salesCriteria.add(Restrictions.eq("status", ChainStoreSalesOrder.STATUS_COMPLETE));
		salesCriteria.add(Restrictions.between("orderDate", startDate, endDate));
		List<ChainStoreSalesOrder> salesOrders = chainStoreSalesOrderDaoImpl.getByCritera(salesCriteria, false);
		for (ChainStoreSalesOrder order : salesOrders){
			loggerLocal.infoB("" + startDate + " " + endDate + " : " + order.getId());
			Set<ChainStoreSalesOrderProduct> orderProducts = order.getProductSet();
			Iterator<ChainStoreSalesOrderProduct> orderProductIterator = orderProducts.iterator();
			
			while (orderProductIterator.hasNext()){
				ChainStoreSalesOrderProduct orderProduct = orderProductIterator.next();
				ProductBarcode pb = orderProduct.getProductBarcode();
				Product product = pb.getProduct();
				
				if (product.getYear().getYear_ID() == year.getYear_ID() && product.getQuarter().getQuarter_ID() == quarter.getQuarter_ID()){
					int quantity = orderProduct.getQuantity();
					int type = orderProduct.getType();
					int flag = 1;
					int pbId = pb.getId();
					
					if (type == ChainStoreSalesOrderProduct.RETURN_BACK)
						flag = -1;
					
					Integer salesQ = salesMap.get(pbId);
					if (salesQ != null)
						salesMap.put(pbId, salesQ + quantity * flag);
					else 
						salesMap.put(pbId, quantity * flag);
				}
			}
		}
		
		chainStoreSalesOrderDaoImpl.clearSession();
	}
	
	
	private void calculatePurchaseMap(java.sql.Date startDate, java.sql.Date endDate, Map<Integer, Integer> purchaseMap, Map<Integer, Integer> inDeliveryMap, Year year, Quarter quarter){
		Set<Integer> clientIds = chainStoreDaoImpl.getAllClientIds();
		DetachedCriteria purchaseCritiera = DetachedCriteria.forClass(InventoryOrder.class);
		purchaseCritiera.add(Restrictions.in("client_id", clientIds));
		purchaseCritiera.add(Restrictions.eq("order_Status", InventoryOrder.STATUS_ACCOUNT_COMPLETE));
		purchaseCritiera.add(Restrictions.between("order_EndTime", Common_util.formStartDate(startDate), Common_util.formEndDate(endDate)));
		List<InventoryOrder> purchaseOrders = inventoryOrderDAOImpl.getByCritera(purchaseCritiera, false);
		for (InventoryOrder order : purchaseOrders){
			int orderType = order.getOrder_type();
			int flag = 1;
			if (orderType == InventoryOrder.TYPE_SALES_RETURN_ORDER_W)
				flag = -1;
			
			int chainConfirmStatus = order.getChainConfirmStatus();
			
			Set<InventoryOrderProduct> orderProducts = order.getProduct_Set();
			Iterator<InventoryOrderProduct> orderProductIterator = orderProducts.iterator();
			
			while (orderProductIterator.hasNext()){
				InventoryOrderProduct orderProduct = orderProductIterator.next();
				ProductBarcode pb = orderProduct.getProductBarcode();
				Product product = pb.getProduct();
				
				if (product.getYear().getYear_ID() == year.getYear_ID() && product.getQuarter().getQuarter_ID() == quarter.getQuarter_ID()){
					int quantity = orderProduct.getQuantity();
					if (chainConfirmStatus == InventoryOrder.STATUS_CHAIN_NOT_CONFIRM){
						Integer orginalQ = inDeliveryMap.get(pb.getId());
						if (orginalQ != null)
							inDeliveryMap.put(pb.getId(), orginalQ + quantity * flag);
						else 
							inDeliveryMap.put(pb.getId(), quantity * flag);
					} 
					
					Integer orginalQ = purchaseMap.get(pb.getId());
					if (orginalQ != null)
						purchaseMap.put(pb.getId(), orginalQ + quantity * flag);
					else 
						purchaseMap.put(pb.getId(), quantity * flag);
				}
			}
		}
		
		inventoryOrderDAOImpl.clearSession();
	}
	
	@Transactional
	public Response runWeeklyCurrentSeasonSalesAnalysisRpt(){
	
		Response response = new Response();
		
		loggerLocal.infoB(new Date() + " 开始 *周* 当季销售分析报表 :  ChainBatchRptService.runWeeklyCurrentSeasonSalesAnalysisRpt()");
		
		QxbabyConf qxbabyConf = qxbabyConfDaoImpl.getConf();
		Year year = yearDaoImpl.get(qxbabyConf.getYearId(), true);
		Quarter quarter = quarterDaoImpl.get(qxbabyConf.getQuarterId(), true);
		
		java.sql.Date endDate = Common_util.getYestorday();
		java.sql.Date rptDate = endDate;
		java.sql.Date startDate = new java.sql.Date(Common_util.calcualteDate(endDate, -6).getTime());
		
		loggerLocal.infoB("当季配置 :" + year.getYear() + " - " + quarter.getQuarter_Name());
		String message = startDate + "," + year.getYear() + "," + quarter.getQuarter_Name();
		
		/**
		 * 1. 获取当季所有的连锁店
		 */
		int numOfStores = 0;
		List<ChainStore> stores =  chainStoreService.getActiveChainstoresWithOrder();
		
		
		loggerLocal.infoB("销售分析: 总计多少连锁店:" + stores.size());

		Map<Integer, ChainCurrentSeasonSalesAnalysisItem> rptItemMap = new HashMap<Integer, ChainCurrentSeasonSalesAnalysisItem>();

		for (ChainStore store: stores){
			numOfStores++;
			ChainCurrentSeasonSalesAnalysisItem item = new ChainCurrentSeasonSalesAnalysisItem(store);
			rptItemMap.put(store.getChain_id(), item);
		}
		if (numOfStores <= 0){
			loggerLocal.infoB("无法找到连锁店信息");
			response.setMessage(message + " 无法找到连锁店信息");
			return response;
		}
		
		/**
		 * 2. 采购数据
		 */
		loggerLocal.infoB("销售分析: 获取累计采购的日期组");
		List<List<java.sql.Date>> dateList = calculateAccumulatedDates(startDate, endDate);
		
		loggerLocal.infoB("销售分析: 计算累计采购件数");
		Map<Integer, Double> purchaseAccumulatedMap = new HashMap<Integer, Double>();
		Map<Integer, Double> inDeliveryAccumulatedMap = new HashMap<Integer, Double>();		
		Map<Integer, Double> returnAccumulatedMap = new HashMap<Integer, Double>();	
		Map<Integer, Integer> purchaseAccumulateQMap = new HashMap<Integer, Integer>();
		Map<Integer, Integer> inDeliveryAccumulatedQMap = new HashMap<Integer, Integer>();		
		Map<Integer, Integer> returnAccumulatedQMap = new HashMap<Integer, Integer>();	
        for (List<java.sql.Date> dates : dateList){
        	loggerLocal.infoB(new Date() + " 获取累计采购件数 : " + dates.get(0) + ", " + dates.get(1));
			try {
			    calculatePurchaseMapForSalesAnalysis(dates.get(0), dates.get(1), purchaseAccumulatedMap, inDeliveryAccumulatedMap, returnAccumulatedMap,purchaseAccumulateQMap, inDeliveryAccumulatedQMap, returnAccumulatedQMap,year, quarter);
			} catch (Exception e){
				e.printStackTrace();
				loggerLocal.errorB("获取累计采购件数出错 : " + dates.get(0) + ", " + dates.get(1));
				loggerLocal.errorB(e);
			}
        }
        
        
		/**
		 * 3. 获取店铺库存信息
		 */
		loggerLocal.infoB("销售分析: 计算店铺库存件数");
		String sqlInventory = "select i.clientId, sum(i.costTotal), sum(i.quantity) from ChainInOutStock i where i.productBarcode.id in (select id from ProductBarcode b where b.product.productId in (select productId from Product where year.year_ID =? and quarter.quarter_ID=? and chainStore.chain_id = null)) group by i.clientId";
		Object[] values = {year.getYear_ID(), quarter.getQuarter_ID()};
		
		try {
		     List<Object> inventoryObjects = chainInOutStockDaoImpl.executeHQLSelect(sqlInventory, values, null, false);
		     if (inventoryObjects != null && inventoryObjects.size() > 0){					
				for (Object object : inventoryObjects)
				  if (object != null){
					Object[] recordResult = (Object[])object;
					Integer clientId = (Integer)recordResult[0];
					Double inventory =  (Double)recordResult[1];
					Long inventoryQ =  (Long)recordResult[2];
					ChainStore store = chainStoreDaoImpl.getByClientId(clientId);
					
					if (store == null)
						continue;
					
					ChainCurrentSeasonSalesAnalysisItem item = rptItemMap.get(store.getChain_id());
					if (item != null) {
						item.setInventoryAmt(inventory);
						item.setInventoryQ(inventoryQ.intValue());
					}
				  } 
		     }
		     chainInOutStockDaoImpl.clearSession();
		} catch (Exception e){
			loggerLocal.errorB(e);
			loggerLocal.infoB("获取inventory 出现错误");
		}
		
		/**
		 * 4. 获取店铺销售信息
		 */
		loggerLocal.infoB("销售分析: 计算累计销售件数");
		Map<Integer, Double> salesAccumulatedMap = new HashMap<Integer, Double>();
		Map<Integer, Integer> salesAccumulatedQMap = new HashMap<Integer, Integer>();
		Map<Integer, Double> salesAccumulatedCostMap = new HashMap<Integer, Double>();
        for (List<java.sql.Date> dates : dateList){
        	loggerLocal.infoB(new Date() + " 获取累计销售件数 : " + dates.get(0) + ", " + dates.get(1));
			try {
				calculateSalesMapForSalesAnalysis(dates.get(0), dates.get(1), salesAccumulatedMap,salesAccumulatedQMap, salesAccumulatedCostMap, year, quarter);
			} catch (Exception e){
				e.printStackTrace();
				loggerLocal.errorB("获取累计销售件数出错 : " + dates.get(0) + ", " + dates.get(1));
				loggerLocal.errorB(e);
				
			}	
        }
        
		/**
		 * 5. 计算map的数据
		 */
        loggerLocal.infoB("销售分析 :计算map数据");
		ChainCurrentSeasonSalesAnalysisRpt rpt = new ChainCurrentSeasonSalesAnalysisRpt(year, quarter, startDate, endDate);
		
		Iterator<Integer> keys = rptItemMap.keySet().iterator();
		while (keys.hasNext()){
			int key = keys.next();
			ChainCurrentSeasonSalesAnalysisItem item = rptItemMap.get(key);
			try {
				if (item != null){
					ChainStore store = chainStoreDaoImpl.get(key, true);
					int clientId = store.getClient_id();
					
					Double weeklyPurchase = purchaseAccumulatedMap.get(clientId);
					if (weeklyPurchase != null)
						item.setPurchaseAmt(weeklyPurchase);
					
					Double weeklyReturn = returnAccumulatedMap.get(clientId);
					if (weeklyReturn != null)
						item.setReturnAmt(weeklyReturn);
					
					Double salesAmt = salesAccumulatedMap.get(clientId);
					if (salesAmt != null)
						item.setSalesAmt(salesAmt);
					
					Double salesCost = salesAccumulatedCostMap.get(clientId);
					if (salesCost != null)
						item.setSalesCost(salesCost);
					
					Double inDeliveryAmt = inDeliveryAccumulatedMap.get(clientId);
					if (inDeliveryAmt != null)
						item.setInDeliveryAmt(inDeliveryAmt);
					
					Integer weeklyPurchaseQ = purchaseAccumulateQMap.get(clientId);
					if (weeklyPurchaseQ != null)
						item.setPurchaseQ(weeklyPurchaseQ);
					
					Integer weeklyReturnQ = returnAccumulatedQMap.get(clientId);
					if (weeklyReturnQ != null)
						item.setReturnQ(weeklyReturnQ);
					
					Integer salesQ = salesAccumulatedQMap.get(clientId);
					if (salesQ != null)
						item.setSalesQ(salesQ);
					
					Integer inDeliveryQ = inDeliveryAccumulatedQMap.get(clientId);
					if (inDeliveryQ != null)
						item.setInDeliveryQ(inDeliveryQ);
					
					item.calculateRatio();
				}
			} catch (Exception e){
				loggerLocal.error("错误 :" + e.getMessage() + ", " + key);
				loggerLocal.errorB(e);
			}
		}
		loggerLocal.infoB("销售分析 :排序map数据");
		List<ChainCurrentSeasonSalesAnalysisItem> rptItems = new ArrayList<ChainCurrentSeasonSalesAnalysisItem>(rptItemMap.values());
		Collections.sort(rptItems, new ChainCurrentSalesAnalysisComparator());
		
		rpt.setRptItems(rptItems);
		
		String webInf = this.getClass().getClassLoader().getResource("").getPath();
		String contextPath = webInf.substring(1, webInf.indexOf("classes")).replaceAll("%20", " ");   

		
		/**
		 * 9.1 准备数据
		 */
		ChainBatchRptRepositoty chainBatchRptRepositoty = new ChainBatchRptRepositoty();
		chainBatchRptRepositoty.setRptId(ChainBatchRptRepositoty.TYPE_ACCU_SALES_AWEEKLY_NALYSIS_RPT);
		chainBatchRptRepositoty.setRptDate(rptDate);
		chainBatchRptRepositoty.setRptName(year.getYear_ID(), quarter.getQuarter_ID());
		chainBatchRptRepositoty.setRptDes(startDate, endDate, quarter);
		
		ChainBatchRptRepositoty chainBatchRptRepositoty2 = chainBatchRptRepositotyDaoImpl.getUniqueRepository(chainBatchRptRepositoty.getRptId(), chainBatchRptRepositoty.getRptDate(), chainBatchRptRepositoty.getRptName());
		if (chainBatchRptRepositoty2 != null){
			chainBatchRptRepositoty.setId(chainBatchRptRepositoty2.getId());
			chainBatchRptRepositotyDaoImpl.evict(chainBatchRptRepositoty2);
		}
		
	   /**
	    * 9.2 所有连锁店的数据
	    */

	   loggerLocal.infoB("销售分析: 准备excel文件");
	   Map<String, HSSFWorkbook> bookMap = new HashMap<String, HSSFWorkbook>();
		
	   try {
		   ChainCurrentSeasonSalesAnalysisTemplate rptTemplate = new ChainCurrentSeasonSalesAnalysisTemplate(rpt, contextPath + "template\\");
		   HSSFWorkbook wholeChainWorkbook = rptTemplate.process();
		   String wholeChainWorkbookName = "所有连锁店报表" + Common_util.dateFormat.format(rptDate) + ".xls";
		   bookMap.put(wholeChainWorkbookName, wholeChainWorkbook);
		   
		   //如果打包出现错误回滚
		   loggerLocal.infoB("销售分析: 写入报表数据到数据库");
		   chainBatchRptRepositotyDaoImpl.saveOrUpdate(chainBatchRptRepositoty, true);
		   String filePath = chainBatchRptRepositoty.getRptPathByType() + "\\" + chainBatchRptRepositoty.getDownloadRptName();    
	
	       loggerLocal.infoB("销售分析: zip文件名: " + filePath);
	       FileCompressionUtil.zipWorkbooks(filePath, bookMap);
	       
		   loggerLocal.infoB("------" + startDate + " 报表成功生成----");
		   response.setMessage(message);
	   } catch (Exception e){
		   loggerLocal.error(e);
		   response.setFail(e.getMessage());
		   loggerLocal.infoB("------" + startDate + " 报表生成失败----");
	   }

	   return response;
	}
	
	private void calculateSalesMapForSalesAnalysis(java.sql.Date startDate,
			java.sql.Date endDate, Map<Integer, Double> salesAccumulatedMap,Map<Integer, Integer> salesAccumulatedQMap,Map<Integer, Double> salesAccumulatedCostMap,
			Year year, Quarter quarter) {
		DetachedCriteria salesCriteria = DetachedCriteria.forClass(ChainStoreSalesOrder.class);
		salesCriteria.add(Restrictions.eq("status", ChainStoreSalesOrder.STATUS_COMPLETE));
		salesCriteria.add(Restrictions.between("orderDate", startDate, endDate));
		List<ChainStoreSalesOrder> salesOrders = chainStoreSalesOrderDaoImpl.getByCritera(salesCriteria, false);
		for (ChainStoreSalesOrder order : salesOrders){
//			loggerLocal.infoB("" + startDate + " " + endDate + " : " + order.getId());
			Set<ChainStoreSalesOrderProduct> orderProducts = order.getProductSet();
			Iterator<ChainStoreSalesOrderProduct> orderProductIterator = orderProducts.iterator();
			
			double salesCost = 0;
			int salesQ = 0;
			double salesAmt = 0;
			int clientId = 0;
			ChainStore store = order.getChainStore();
			if (store == null)
				continue;
			else {
			    store = chainStoreDaoImpl.get(store.getChain_id(), true);
			    clientId = store.getClient_id();
			    //如果是子连锁店就过略掉
			    if (store.getParentStore() != null)
			    	continue;
			}
			
//			if (clientId == 2011)
//				System.out.println("ggg");
			
			while (orderProductIterator.hasNext()){
				ChainStoreSalesOrderProduct orderProduct = orderProductIterator.next();
				ProductBarcode pb = orderProduct.getProductBarcode();
				Product product = pb.getProduct();

				
				if (product.getChainStore() == null && product.getYear().getYear_ID() == year.getYear_ID() && product.getQuarter().getQuarter_ID() == quarter.getQuarter_ID()){
					
					int quantity = orderProduct.getQuantity();
					int type = orderProduct.getType();
					int flag = 1;
					
					if (type == ChainStoreSalesOrderProduct.RETURN_BACK)
						flag = -1;
					else if (type == ChainStoreSalesOrderProduct.FREE)
						flag = 0;
					
					salesCost += orderProduct.getCostPrice() * quantity * flag;
					salesQ += quantity * flag;
					salesAmt += orderProduct.getDiscountRate() * orderProduct.getRetailPrice() * quantity * flag;
				}
			}
			
			Double originalSalesAmt = salesAccumulatedMap.get(clientId);
			if (originalSalesAmt == null)
				originalSalesAmt = new Double(0);
			salesAccumulatedMap.put(clientId, originalSalesAmt + salesAmt);
			
			Double originalCost = salesAccumulatedCostMap.get(clientId);
			if (originalCost == null)
				originalCost = new Double(0);
			salesAccumulatedCostMap.put(clientId, originalCost + salesCost);
			
			Integer originalQ = salesAccumulatedQMap.get(clientId);
			if (originalQ == null)
				originalQ = new Integer(0);
			salesAccumulatedQMap.put(clientId, originalQ + salesQ);
		}
		chainStoreSalesOrderDaoImpl.clearSession();
	}

	private void calculatePurchaseMapForSalesAnalysis(java.sql.Date startDate,
			java.sql.Date endDate, Map<Integer, Double> purchaseAccumulatedMap,
			Map<Integer, Double> inDeliveryAccumulatedMap,Map<Integer, Double> returnAccumulatedMap,Map<Integer, Integer> purchaseAccumulatedQMap,
			Map<Integer, Integer> inDeliveryAccumulatedQMap,Map<Integer, Integer> returnAccumulatedQMap, Year year,
			Quarter quarter) {
		Set<Integer> clientIds = chainStoreDaoImpl.getAllClientIds();
		DetachedCriteria purchaseCritiera = DetachedCriteria.forClass(InventoryOrder.class);
		purchaseCritiera.add(Restrictions.in("client_id", clientIds));
		purchaseCritiera.add(Restrictions.eq("order_Status", InventoryOrder.STATUS_ACCOUNT_COMPLETE));
		purchaseCritiera.add(Restrictions.between("order_EndTime", Common_util.formStartDate(startDate), Common_util.formEndDate(endDate)));
		List<InventoryOrder> purchaseOrders = inventoryOrderDAOImpl.getByCritera(purchaseCritiera, false);
		
		Double purchaseAccumulated = null;
		Double returnAccumulated = null;
		Double inDeliveryAccumulated = null;
		Integer purchaseAccumulatedQ = null;
		Integer returnAccumulatedQ = null;
		Integer inDeliveryAccumulatedQ = null;
		
		for (InventoryOrder order : purchaseOrders){
			int clientId = order.getClient_id();
			double purchaseAmt = 0;
			double returnAmt = 0;
			double inDeliveryAmt = 0;
			int purchaseQ = 0;
			int returnQ = 0;
			int inDeliveryQ = 0;			

			int chainConfirmStatus = order.getChainConfirmStatus();
			
			Set<InventoryOrderProduct> orderProducts = order.getProduct_Set();
			Iterator<InventoryOrderProduct> orderProductIterator = orderProducts.iterator();
			
			while (orderProductIterator.hasNext()){
				InventoryOrderProduct orderProduct = orderProductIterator.next();
				ProductBarcode pb = orderProduct.getProductBarcode();
				Product product = pb.getProduct();
				
				if (product.getYear().getYear_ID() == year.getYear_ID() && product.getQuarter().getQuarter_ID() == quarter.getQuarter_ID() && (product.getChainStore() == null)){
					int quantity = orderProduct.getQuantity();
					if (order.getOrder_type() == InventoryOrder.TYPE_SALES_ORDER_W){
						if (chainConfirmStatus == InventoryOrder.STATUS_CHAIN_NOT_CONFIRM){
							inDeliveryAmt += orderProduct.getWholeSalePrice() * quantity;
							inDeliveryQ += quantity;
						}
						purchaseAmt += orderProduct.getWholeSalePrice() * quantity;
						purchaseQ += quantity;
					} else {
						if (chainConfirmStatus == InventoryOrder.STATUS_CHAIN_NOT_CONFIRM){
							inDeliveryAmt -= orderProduct.getWholeSalePrice() * quantity;
							inDeliveryQ -= quantity;
						}
						returnAmt += orderProduct.getWholeSalePrice() * quantity;
						returnQ += quantity;
					}
				}
			}
			
			purchaseAccumulated = purchaseAccumulatedMap.get(clientId);
			if (purchaseAccumulated != null || purchaseAmt != 0){
				if (purchaseAccumulated == null)
					purchaseAccumulated = new Double(0);
				purchaseAccumulatedMap.put(clientId, purchaseAccumulated + purchaseAmt);
			}
			
			returnAccumulated = returnAccumulatedMap.get(clientId);
			if (returnAccumulated != null || returnAmt != 0){
				if (returnAccumulated == null)
					returnAccumulated = new Double(0);
				returnAccumulatedMap.put(clientId, returnAccumulated + returnAmt);
			}
			
			inDeliveryAccumulated = inDeliveryAccumulatedMap.get(clientId);
			if (inDeliveryAccumulated != null || inDeliveryAmt != 0){
				if (inDeliveryAccumulated == null)
					inDeliveryAccumulated = new Double(0);
				inDeliveryAccumulatedMap.put(clientId, inDeliveryAccumulated + inDeliveryAmt);
			}
			
			purchaseAccumulatedQ = purchaseAccumulatedQMap.get(clientId);
			if (purchaseAccumulatedQ != null || purchaseQ != 0){
				if (purchaseAccumulatedQ == null)
					purchaseAccumulatedQ = new Integer(0);
				purchaseAccumulatedQMap.put(clientId, purchaseAccumulatedQ + purchaseQ);
			}
			
			returnAccumulatedQ = returnAccumulatedQMap.get(clientId);
			if (returnAccumulatedQ != null || returnQ != 0){
				if (returnAccumulatedQ == null)
					returnAccumulatedQ = new Integer(0);
				returnAccumulatedQMap.put(clientId, returnAccumulatedQ + returnQ);
			}
			
			inDeliveryAccumulatedQ = inDeliveryAccumulatedQMap.get(clientId);
			if (inDeliveryAccumulatedQ != null || inDeliveryQ != 0){
				if (inDeliveryAccumulatedQ == null)
					inDeliveryAccumulatedQ = new Integer(0);
				inDeliveryAccumulatedQMap.put(clientId, inDeliveryAccumulatedQ + inDeliveryQ);
			}
		}
		
		inventoryOrderDAOImpl.clearSession();
	}
	
	public void runDailySalesVIPPercentage(){
		Date rptDate = Common_util.getYestorday();
		
		final String SPLIT = "@";
		loggerLocal.infoB(new Date() + " 开始 *日* 连锁店销售VIP占比报表 :  ChainBatchRptService.runDailySalesVIPPercentage()");
		
		/**
		 * example : ChainStore 1
		 * NetSales : 1<@>N
		 * VIP1 : 1<@>V1
		 * VIP2 : 1<@>V2
		 * VIP3 : 1<@>V3
		 */
		Map<String, Double> valueMap = new HashMap<String, Double>();
		
		Date startDate = Common_util.formStartDate(rptDate);
		Date endDate = Common_util.formEndDate(rptDate);
		
		DetachedCriteria salesCriteria = DetachedCriteria.forClass(ChainStoreSalesOrder.class);
		salesCriteria.add(Restrictions.eq("status", ChainStoreSalesOrder.STATUS_COMPLETE));
		salesCriteria.add(Restrictions.between("orderDate", startDate, endDate));
		List<ChainStoreSalesOrder> salesOrders = chainStoreSalesOrderDaoImpl.getByCritera(salesCriteria, false);
		
		Double totalSalesAmt = null;
		Double vipSalesAmt = null;
		for (ChainStoreSalesOrder order : salesOrders){
			ChainStore chainStore = order.getChainStore();
			int chainId = chainStore.getChain_id();
			ChainVIPCard vipCard = order.getVipCard();
			double netSales = order.getNetAmount() - order.getNetAmountR();
			
			//1. 计算总销售
			String SALES_AMT_KEY = chainId + SPLIT + "N";
			totalSalesAmt = valueMap.get(SALES_AMT_KEY);
			if (totalSalesAmt == null)
				totalSalesAmt = new Double(0);
			valueMap.put(SALES_AMT_KEY, totalSalesAmt + netSales);
			
			//2. 查vip
			if (vipCard != null){
				int VIPType = vipCard.getVipType().getId();
				String SALES_VIP_KEY = chainId + SPLIT + "V" + VIPType;
				
				vipSalesAmt = valueMap.get(SALES_VIP_KEY);
				if (vipSalesAmt == null)
					vipSalesAmt  = new Double(0);
				valueMap.put(SALES_VIP_KEY, vipSalesAmt + netSales);
			}
			
		}
		
		List<ChainSalesVIPPercentageItem> percentageItems = new ArrayList<ChainSalesVIPPercentageItem>();
		
		//准备连锁店数据
		List<ChainStore> chainStores = chainStoreService.getActiveChainstoresWithOrder();
		for (ChainStore chainStore : chainStores){
			int chainId = chainStore.getChain_id();
			String SALES_AMT_KEY = chainId + SPLIT + "N";
			String SALES_VIP1_KEY = chainId + SPLIT + "V" + ChainVIPType.VIP1_ID;
			String SALES_VIP2_KEY = chainId + SPLIT + "V" + ChainVIPType.VIP2_ID;
			String SALES_VIP3_KEY = chainId + SPLIT + "V" + ChainVIPType.VIP3_ID;
			
			ChainSalesVIPPercentageItem item = new ChainSalesVIPPercentageItem();
			
			Double netSales = valueMap.get(SALES_AMT_KEY);
			Double vip1Sales = valueMap.get(SALES_VIP1_KEY);
			Double vip2Sales = valueMap.get(SALES_VIP2_KEY);
			Double vip3Sales = valueMap.get(SALES_VIP3_KEY);
			
			if (netSales == null)
				netSales = new Double(0);
			if (vip1Sales == null)
				vip1Sales = new Double(0);
			if (vip2Sales == null)
				vip2Sales = new Double(0);
			if (vip3Sales == null)
				vip3Sales = new Double(0);
			
			item.setChainStore(chainStore);
			item.setNetSales(netSales.intValue());
			item.setVip1NetSales(vip1Sales.intValue());
			item.setVip2NetSales(vip2Sales.intValue());
			item.setVip3NetSales(vip3Sales.intValue());
			
			if (netSales == 0){
				item.setVip1NetSalesPercentage(0);
				item.setVip2NetSalesPercentage(0);
				item.setVip3NetSalesPercentage(0);
			} else {
				if (vip1Sales >0)
				    item.setVip1NetSalesPercentage(vip1Sales / netSales);
				if (vip2Sales >0)
					item.setVip2NetSalesPercentage(vip2Sales / netSales);
				if (vip3Sales >0)
					item.setVip3NetSalesPercentage(vip3Sales / netSales);
			}
			
			percentageItems.add(item);
		}
		
		String webInf = this.getClass().getClassLoader().getResource("").getPath();
		String contextPath = webInf.substring(1, webInf.indexOf("classes")).replaceAll("%20", " ");  

		ChainSalesReportVIPPercentageTemplate percentageTemplate = null;
		try {
			percentageTemplate = new ChainSalesReportVIPPercentageTemplate(percentageItems, contextPath, rptDate);
			HSSFWorkbook workbook = percentageTemplate.process();
			
			String rptPath = ChainSalesReportVIPPercentageTemplate.getFilePath(rptDate);
			File fileOuputFile = new File(rptPath);
			FileOutputStream fileOutputStream = new FileOutputStream(fileOuputFile);
			workbook.write(fileOutputStream);
			fileOutputStream.close();
		} catch (Exception e) {
			loggerLocal.error("生成vip报表出现错误 : " + rptDate);
			loggerLocal.errorB(e);
			e.printStackTrace();
			return;
		}
	}

	class ChainCurrentProductAnalysisComparator implements Comparator<ChainCurrentSeasonProductAnalysisItem> {

		@Override
		public int compare(ChainCurrentSeasonProductAnalysisItem o1,
				ChainCurrentSeasonProductAnalysisItem o2) {
			return o2.getSalesWeekly() - o1.getSalesWeekly();
			
		}
	}
	
	class ChainCurrentSalesAnalysisComparator implements Comparator<ChainCurrentSeasonSalesAnalysisItem> {

		@Override
		public int compare(ChainCurrentSeasonSalesAnalysisItem o1,
				ChainCurrentSeasonSalesAnalysisItem o2) {
			double result = o2.getSalesAmt() - o1.getSalesAmt();
			if (result > 0)
				return 1;
			else if (result < 0)
				return -1;
			else 
				return 0;
			
		}
	}
	
	/**
	 * 每个月16号凌晨运行前 1-15号之间的 transfer-order flow acct
	 * 
	 * 每个月1号运行前16号到月底的pre-order flow acct
	 */
	public void runBiweeklyTransferOrderFlowAcctCalculation(){
		Date today = Common_util.getToday();
		
		loggerLocal.infoB(today + " 开始 运行调货单的流水  :  ChainBatchRptService.runBiweeklyTransferOrderFlowAcctCalculation()");
		int date = today.getDate();
		
		Date startDate = null;
		Date endDate = Common_util.getYestorday();
		
		Calendar thisMonth = Calendar.getInstance();
		if (date == 1){
			int lastMonthInt = thisMonth.get(Calendar.MONTH);
			thisMonth.set(Calendar.MONTH, lastMonthInt - 1);
			thisMonth.set(Calendar.DATE, 16);
			
			startDate = new Date(thisMonth.getTimeInMillis());
		} else if (date == 16){
			thisMonth.set(Calendar.DATE, 1);
			startDate = new Date(thisMonth.getTimeInMillis());
		}
		
		startDate = Common_util.formStartDate(startDate);
		endDate = Common_util.formEndDate(endDate);
		
		Object[] values = {startDate, endDate};
		String hql = "SELECT acctChainStoreId, sum(totalQuantity), sum(totalWholeSalesPrice), sum(totalSalesPrice), sum(flowAcctAmt), sum(transportationFee) FROM ChainTransferOrderFlowAcct WHERE orderId>0 AND acctFlowDate BETWEEN ? AND ? GROUP BY acctChainStoreId";
		
		Map<Integer, ChainTransferOrderFlowAcct> flowAcctMap = new HashMap<Integer, ChainTransferOrderFlowAcct>();
		
		String comment = Common_util.dateFormat.format(startDate) +" 至 " + Common_util.dateFormat.format(endDate) +" 调货费用总计";
		List<Object> resultValues = chainTransferFlowAcctDaoImpl.executeHQLSelect(hql, values, null, false);
		
		if (resultValues != null){
			for (Object record : resultValues ){
				Object[] records = (Object[])record;
				int chainId = Common_util.getInt(records[0]);
				int quantity = Common_util.getInt(records[1]);
				double wholePrice = Common_util.getDouble(records[2]);
				double salesPrice = Common_util.getDouble(records[3]);
				double acctFlow = Common_util.getDouble(records[4]);
				double transportationFee = Common_util.getDouble(records[5]);
				
				ChainTransferOrderFlowAcct flowAcct = new ChainTransferOrderFlowAcct();
				flowAcct.setAcctChainStoreId(chainId);
				flowAcct.setAcctFlowDate(today);
				flowAcct.setFromChainStore(comment);
				flowAcct.setTotalQuantity(quantity);
//				flowAcct.setTotalWholeSalesPrice(wholePrice);
//				flowAcct.setTotalSalesPrice(salesPrice);
				flowAcct.setFlowAcctAmt(acctFlow);
				flowAcct.setTransportationFee(transportationFee);
				
				flowAcctMap.put(chainId, flowAcct);
			}
		}
		
		String getMinOrderId = "SELECT MIN(orderId) FROM ChainTransferOrderFlowAcct";
		List<Object> objectResult = chainTransferFlowAcctDaoImpl.executeHQLSelect(getMinOrderId, null, null, false);
		int minOrderId = 0;
		if (objectResult != null){
			minOrderId = (Integer)objectResult.get(0);
		} 
		if (minOrderId > 0)
			minOrderId *= -1;
		
		List<ChainStore> allParentsStores = chainStoreDaoImpl.getAllParentStores();
		for (ChainStore store : allParentsStores){
			minOrderId--;
			int id = store.getChain_id();
			ChainTransferOrderFlowAcct flowAcct = flowAcctMap.get(id);
			if (flowAcct == null){
				flowAcct = new ChainTransferOrderFlowAcct();
				flowAcct.setAcctChainStoreId(id);
				flowAcct.setAcctFlowDate(today);
				flowAcct.setFromChainStore(comment);
				flowAcctMap.put(id, flowAcct);
			} 
			flowAcct.setOrderId(minOrderId);

			chainTransferFlowAcctDaoImpl.saveOrUpdate(flowAcct, false);
		}
		
		List<ChainTransferAcctFlowItem> acctFlowItems = new ArrayList<ChainTransferAcctFlowItem>();
		Iterator<ChainTransferOrderFlowAcct> chainIterator = flowAcctMap.values().iterator();
		while (chainIterator.hasNext()){
			ChainTransferOrderFlowAcct flowAcct = chainIterator.next();
			if (flowAcct.getFlowAcctAmt() == 0)
				continue;
			
			int chainId = flowAcct.getAcctChainStoreId();
			ChainStore store = chainStoreDaoImpl.get(chainId, true);
			
			ChainTransferAcctFlowItem item = new ChainTransferAcctFlowItem(store.getChain_name() + " " +store.getOwner_name(), flowAcct.getFlowAcctAmt());
			acctFlowItems.add(item);
		}
		
		String fromToDate = Common_util.dateFormat.format(startDate) +" 至 " + Common_util.dateFormat.format(endDate);
		
		String webInf = this.getClass().getClassLoader().getResource("").getPath();
		String contextPath = webInf.substring(1, webInf.indexOf("classes")).replaceAll("%20", " ");  
		ChainTransferAcctFlowTemplate acctFlowTemplate = null;
		try {
			acctFlowTemplate = new ChainTransferAcctFlowTemplate(acctFlowItems, contextPath, today, fromToDate);
			
			HSSFWorkbook workbook = acctFlowTemplate.process();
			
			ChainBatchRptRepositoty rptRepository = new ChainBatchRptRepositoty();
			rptRepository.setRptId(ChainBatchRptRepositoty.TYPE_CHAIN_TRANSFER_ACCT_FLOW_RPT);
			rptRepository.setRptDate(new java.sql.Date(today.getTime()));
			
			String rptPath = rptRepository.getRptPathByType() + rptRepository.getDownloadRptName();
			
			File fileOuputFile = new File(rptPath);
			FileOutputStream fileOutputStream = new FileOutputStream(fileOuputFile);
			workbook.write(fileOutputStream);
			fileOutputStream.close();
			
			ChainBatchRptRepositoty chainBatchRptRepositotyOld = chainBatchRptRepositotyDaoImpl.getUniqueRepository(ChainBatchRptRepositoty.TYPE_CHAIN_TRANSFER_ACCT_FLOW_RPT, new java.sql.Date(today.getTime()), "调货");
			if (chainBatchRptRepositotyOld == null){
				ChainBatchRptRepositoty chainBatchRptRepositoty = new ChainBatchRptRepositoty();
				chainBatchRptRepositoty.setRptId(ChainBatchRptRepositoty.TYPE_CHAIN_TRANSFER_ACCT_FLOW_RPT);
				chainBatchRptRepositoty.setRptDate(new java.sql.Date(today.getTime()));
				chainBatchRptRepositoty.setRptName("调货");
				chainBatchRptRepositoty.setRptDes(fromToDate);
				chainBatchRptRepositotyDaoImpl.saveOrUpdate(chainBatchRptRepositoty, true);
			}
			
		} catch (Exception e) {
			loggerLocal.error("生成调货流水报表出现错误 : " + today);
			loggerLocal.errorB(e);
			e.printStackTrace();
			return;
		}
	}
}




