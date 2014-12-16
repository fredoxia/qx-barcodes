package com.onlineMIS.ORM.DAO.chainS.batchRpt;


import java.io.FileOutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onlineMIS.ORM.DAO.chainS.chainMgmt.QxbabyConfDaoImpl;
import com.onlineMIS.ORM.DAO.chainS.inventoryFlow.ChainInOutStockDaoImpl;
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
import com.onlineMIS.ORM.entity.chainS.batchRpt.ChainCurrentSeasonSalesAnalysisItem;
import com.onlineMIS.ORM.entity.chainS.batchRpt.ChainCurrentSeasonSalesAnalysisRpt;
import com.onlineMIS.ORM.entity.chainS.batchRpt.batchRptTemplate.ChainCurrentSeasonSalesAnalysisTemplate;
import com.onlineMIS.ORM.entity.chainS.chainMgmt.QxbabyConf;
import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainInOutStock;
import com.onlineMIS.ORM.entity.chainS.report.ChainBatchRptRepositoty;
import com.onlineMIS.ORM.entity.chainS.sales.ChainStoreSalesOrder;
import com.onlineMIS.ORM.entity.chainS.sales.ChainStoreSalesOrderProduct;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Product;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.ProductBarcode;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Quarter;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Year;
import com.onlineMIS.ORM.entity.headQ.inventory.InventoryOrder;
import com.onlineMIS.ORM.entity.headQ.inventory.InventoryOrderProduct;
import com.onlineMIS.common.Common_util;
import com.onlineMIS.common.FileCompressionUtil;
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

	private Calendar today = Calendar.getInstance();
	
	/**
	 * 运行每周的批量报表程序
	 * 1. 每周运行当季货品分析报表
	 */
	@Transactional
	public void runWeeklyRptBatch(){
		loggerLocal.infoB(new Date() + " 开始 *周* 当季货品销售分析报表 :  ChainBatchRptService.runWeeklyRptBatch()");
		
		QxbabyConf qxbabyConf = qxbabyConfDaoImpl.getConf();
		Year year = yearDaoImpl.get(qxbabyConf.getYearId(), true);
		Quarter quarter = quarterDaoImpl.get(qxbabyConf.getQuarterId(), true);
		
		List<java.sql.Date> lastWeekDays = Common_util.getLastWeekDays();
		java.sql.Date startDate = lastWeekDays.get(0);
		java.sql.Date endDate = lastWeekDays.get(6);
		
		loggerLocal.infoB("当季配置 :" + year.getYear() + " - " + quarter.getQuarter_Name());
		
		/**
		 * 1. 获取当季所有的productBarcode
		 */
		DetachedCriteria pbCriteria = DetachedCriteria.forClass(ProductBarcode.class);
		DetachedCriteria productCriteria = pbCriteria.createCriteria("product");
		productCriteria.add(Restrictions.eq("year.year_ID", year.getYear_ID()));
		productCriteria.add(Restrictions.eq("quarter.quarter_ID", quarter.getQuarter_ID()));
		List<ProductBarcode> productBarcodes = productBarcodeDaoImpl.getByCritera(pbCriteria, false);
		loggerLocal.infoB("总计多少当季货品:" + productBarcodes.size());
		
//		Set<Integer> productBarcodeIds = new HashSet<Integer>();
//		Set<String> barcodes = new HashSet<String>();
		Map<Integer, ChainCurrentSeasonSalesAnalysisItem> rptItemMap = new HashMap<Integer, ChainCurrentSeasonSalesAnalysisItem>();
		int numOfBarcodes = 0;
		for (ProductBarcode pb: productBarcodes){
			numOfBarcodes++;
//			productBarcodeIds.add(pb.getId());
//			barcodes.add(pb.getBarcode());
			ChainCurrentSeasonSalesAnalysisItem item = new ChainCurrentSeasonSalesAnalysisItem(pb);
			rptItemMap.put(pb.getId(), item);
		}
		

		if (numOfBarcodes <= 0){
			loggerLocal.infoB("无法找到当季条码数据");
			return;
		}
		
		/**
		 * 2. 获取上市日期
		 */	
		DetachedCriteria ioCriteria = DetachedCriteria.forClass(ChainInOutStock.class);
		DetachedCriteria pbIoCriteria = ioCriteria.createCriteria("productBarcode");
		DetachedCriteria productIoCriteria = pbIoCriteria.createCriteria("product");
		productIoCriteria.add(Restrictions.eq("year.year_ID", year.getYear_ID()));
		productIoCriteria.add(Restrictions.eq("quarter.quarter_ID", quarter.getQuarter_ID()));
		
		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.property("productBarcode.id"));
		projectionList.add(Projections.min("date"));
		ioCriteria.setProjection(projectionList);
		
		try {
		     List<Object> marketDateObjects = chainInOutStockDaoImpl.getByCriteriaProjection(ioCriteria, false);
		     if (marketDateObjects != null && marketDateObjects.size() > 0){					
				for (Object object : marketDateObjects)
				  if (object != null){
					Object[] recordResult = (Object[])object;
					Integer productId = (Integer)recordResult[0];
					Timestamp marketDate =  (Timestamp)recordResult[1];
					
					ChainCurrentSeasonSalesAnalysisItem item = rptItemMap.get(productId);
					if (item != null)
						item.setMarketDate(new java.sql.Date(marketDate.getTime()));
				  } 
		     }
		} catch (Exception e){
			loggerLocal.errorB(e);
			loggerLocal.infoB("获取market date出现错误说");
		}
		
		/**
		 * 3. 获取周采购件数
		 */
		loggerLocal.infoB("计算周采购件数");
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
		loggerLocal.infoB("计算累计采购件数");
		Map<Integer, Integer> purchaseAccumulatedMap = new HashMap<Integer, Integer>();
		Map<Integer, Integer> inDeliveryAccumulatedMap = new HashMap<Integer, Integer>();		
		java.sql.Date startDate4Month = new java.sql.Date(Common_util.calcualteDate(Common_util.getToday(), -120).getTime());
		loggerLocal.infoB("累计日期 : " + startDate4Month);
		try {
		    calculatePurchaseMap(startDate4Month, endDate, purchaseAccumulatedMap, inDeliveryAccumulatedMap, year, quarter);
		} catch (Exception e){
			loggerLocal.errorB(e);
			loggerLocal.errorB("获取累计采购件数出错");
		}	
		
		/**
		 * 5. 获取周销售件数
		 */
		loggerLocal.infoB("计算周销售件数");
		Map<Integer, Integer> salesWeeklyMap = new HashMap<Integer, Integer>();
		try {
		calculateSalesMap(startDate4Month, endDate, salesWeeklyMap, year, quarter);
		} catch (Exception e){
			loggerLocal.errorB(e);
			loggerLocal.errorB("获取周销售出错");
		}	
		
		/**
		 * 6. 累计销售件数
		 */
		loggerLocal.infoB("计算累计销售件数");
		Map<Integer, Integer> salesAccumulatedMap = new HashMap<Integer, Integer>();
		try {
			calculateSalesMap(startDate4Month, endDate, salesAccumulatedMap, year, quarter);
		} catch (Exception e){
			loggerLocal.errorB(e);
			loggerLocal.errorB("获取累计销售件数出错");
		}	
		
		/**
		 * 7. 获取店铺库存件数
		 */
		loggerLocal.infoB("计算店铺库存件数");
		DetachedCriteria ioInventoryCriteria = DetachedCriteria.forClass(ChainInOutStock.class);
		DetachedCriteria pbIoInventoryCriteria = ioInventoryCriteria.createCriteria("productBarcode");
		DetachedCriteria productIoInventoryCriteria = pbIoInventoryCriteria.createCriteria("product");
		productIoInventoryCriteria.add(Restrictions.eq("year.year_ID", year.getYear_ID()));
		productIoInventoryCriteria.add(Restrictions.eq("quarter.quarter_ID", quarter.getQuarter_ID()));
		
		ProjectionList projectionInventoryList = Projections.projectionList();
		projectionInventoryList.add(Projections.property("productBarcode.id"));
		projectionInventoryList.add(Projections.sum("quantity"));
		ioInventoryCriteria.setProjection(projectionInventoryList);
		try {
		     List<Object> inventoryObjects = chainInOutStockDaoImpl.getByCriteriaProjection(ioInventoryCriteria, false);
		     if (inventoryObjects != null && inventoryObjects.size() > 0){					
				for (Object object : inventoryObjects)
				  if (object != null){
					Object[] recordResult = (Object[])object;
					Integer productId = (Integer)recordResult[0];
					Integer inventory =  (Integer)recordResult[1];
					
					ChainCurrentSeasonSalesAnalysisItem item = rptItemMap.get(productId);
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
		ChainCurrentSeasonSalesAnalysisRpt rpt = new ChainCurrentSeasonSalesAnalysisRpt(chainStoreDaoImpl.getAllChainStoreObject(), year, quarter, startDate);
		
		Iterator<Integer> keys = rptItemMap.keySet().iterator();
		while (keys.hasNext()){
			int key = keys.next();
			ChainCurrentSeasonSalesAnalysisItem item = rptItemMap.get(key);
			if (item != null){
				Integer weeklyPurchase = purchaseWeeklyMap.get(key);
				if (weeklyPurchase != null)
					item.setPurchaseWeekly(weeklyPurchase);
				
				Integer accumulatedPurchase = purchaseWeeklyMap.get(key);
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
		}
		
		List<ChainCurrentSeasonSalesAnalysisItem> rptItems = new ArrayList<ChainCurrentSeasonSalesAnalysisItem>(rptItemMap.values());
		rpt.setRptItems(rptItems);
		

		
		HttpServletRequest request = (HttpServletRequest)ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);   
		String contextPath= request.getRealPath("/");

		/**
		 * 9. 保存数据
		 */
		
		/**
		 * 9.1 准备数据
		 */
		
		ChainBatchRptRepositoty chainBatchRptRepositoty = new ChainBatchRptRepositoty();
		chainBatchRptRepositoty.setRptId(ChainBatchRptRepositoty.TYPE_WEEKLY_SALES_ANALYSIS_RPT);;
		chainBatchRptRepositoty.setRptDate(startDate);
		chainBatchRptRepositoty.setRptDes(startDate, endDate);
		
	   /**
	    * 9.2 所有连锁店的数据
	    */
	   Map<String, HSSFWorkbook> bookMap = new HashMap<String, HSSFWorkbook>();
		
	   try {
		   loggerLocal.infoB("准备zip文件");
		   ChainCurrentSeasonSalesAnalysisTemplate rptTemplate = new ChainCurrentSeasonSalesAnalysisTemplate(rpt, contextPath + "WEB-INF\\template\\");
		   HSSFWorkbook wholeChainWorkbook = rptTemplate.process();
		   String wholeChainWorkbookName = rpt.getChainStore().getChain_name() + ".xls";
		   
		   bookMap.put(wholeChainWorkbookName, wholeChainWorkbook);
	
	       String zipFilePath = chainBatchRptRepositoty.getRptPathByType() + "\\" + chainBatchRptRepositoty.getDownloadRptName();    
	
	       loggerLocal.infoB("zip文件名: " + zipFilePath);
	       FileCompressionUtil.zipWorkbooks(zipFilePath, bookMap);
	       
	       //如果打包出现错误回滚
	       loggerLocal.infoB("写入报表数据到数据库");
		   chainBatchRptRepositotyDaoImpl.saveOrUpdate(chainBatchRptRepositoty, true);
			
	   } catch (Exception e){
		   loggerLocal.error("发生严重错误 : ");
		   loggerLocal.errorB(e);
	   }
	
	}
	
	private void calculateSalesMap(java.sql.Date startDate, java.sql.Date endDate, Map<Integer, Integer> salesMap,  Year year, Quarter quarter){
		DetachedCriteria salesCriteria = DetachedCriteria.forClass(ChainStoreSalesOrder.class);
		salesCriteria.add(Restrictions.eq("status", ChainStoreSalesOrder.STATUS_COMPLETE));
		salesCriteria.add(Restrictions.between("orderDate", startDate, endDate));
		List<ChainStoreSalesOrder> salesOrders = chainStoreSalesOrderDaoImpl.getByCritera(salesCriteria, false);
		for (ChainStoreSalesOrder order : salesOrders){
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
	}
	
	
	private void calculatePurchaseMap(java.sql.Date startDate, java.sql.Date endDate, Map<Integer, Integer> purchaseMap, Map<Integer, Integer> inDeliveryMap, Year year, Quarter quarter){
		Set<Integer> clientIds = chainStoreDaoImpl.getAllClientIds();
		DetachedCriteria purchaseCritiera = DetachedCriteria.forClass(InventoryOrder.class);
		purchaseCritiera.add(Restrictions.in("client_id", clientIds));
		purchaseCritiera.add(Restrictions.eq("order_Status", InventoryOrder.STATUS_ACCOUNT_COMPLETE));
		purchaseCritiera.add(Restrictions.between("order_EndTime", startDate, endDate));
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
					} else {
						Integer orginalQ = purchaseMap.get(pb.getId());
						if (orginalQ != null)
							purchaseMap.put(pb.getId(), orginalQ + quantity * flag);
						else 
							purchaseMap.put(pb.getId(), quantity * flag);
					}
				}
			}
		}
	}
}


