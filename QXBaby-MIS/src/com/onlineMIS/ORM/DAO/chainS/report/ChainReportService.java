package com.onlineMIS.ORM.DAO.chainS.report;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.naming.java.javaURLContextFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onlineMIS.ORM.DAO.Response;
import com.onlineMIS.ORM.DAO.chainS.inventoryFlow.ChainInOutStockDaoImpl;
import com.onlineMIS.ORM.DAO.chainS.inventoryFlow.ChainInventoryFlowOrderService;
import com.onlineMIS.ORM.DAO.chainS.report.ChainReportService.ChainSalesStatisticComparator;
import com.onlineMIS.ORM.DAO.chainS.sales.ChainDailySalesDaoImpl;
import com.onlineMIS.ORM.DAO.chainS.sales.ChainStoreSalesOrderDaoImpl;
import com.onlineMIS.ORM.DAO.chainS.user.ChainStoreDaoImpl;
import com.onlineMIS.ORM.DAO.chainS.user.ChainStoreService;
import com.onlineMIS.ORM.DAO.chainS.user.ChainUserInforDaoImpl;
import com.onlineMIS.ORM.DAO.chainS.user.ChainUserInforService;
import com.onlineMIS.ORM.DAO.chainS.vip.ChainVIPCardImpl;
import com.onlineMIS.ORM.DAO.chainS.vip.ChainVIPPrepaidImpl;
import com.onlineMIS.ORM.DAO.headQ.barCodeGentor.BrandDaoImpl;
import com.onlineMIS.ORM.DAO.headQ.barCodeGentor.ProductBarcodeDaoImpl;
import com.onlineMIS.ORM.DAO.headQ.barCodeGentor.ProductDaoImpl;
import com.onlineMIS.ORM.DAO.headQ.barCodeGentor.QuarterDaoImpl;
import com.onlineMIS.ORM.DAO.headQ.barCodeGentor.YearDaoImpl;
import com.onlineMIS.ORM.DAO.headQ.finance.ChainFinanceTraceImpl;
import com.onlineMIS.ORM.DAO.headQ.finance.FinanceCategoryImpl;
import com.onlineMIS.ORM.DAO.headQ.inventory.InventoryOrderDAOImpl;
import com.onlineMIS.ORM.DAO.headQ.inventory.InventoryOrderProductDAOImpl;
import com.onlineMIS.ORM.entity.base.Pager;
import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainInOutStock;
import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainInventoryItemVO;
import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainInventoryReportTemplate;
import com.onlineMIS.ORM.entity.chainS.report.ChainAllInOneReportItemVO;
import com.onlineMIS.ORM.entity.chainS.report.ChainBatchRptRepositoty;
import com.onlineMIS.ORM.entity.chainS.report.ChainDailySalesAnalysis;
import com.onlineMIS.ORM.entity.chainS.report.ChainFinanceReport;
import com.onlineMIS.ORM.entity.chainS.report.ChainFinanceReportItem;
import com.onlineMIS.ORM.entity.chainS.report.ChainPurchaseReport;
import com.onlineMIS.ORM.entity.chainS.report.ChainPurchaseStatisReportItem;
import com.onlineMIS.ORM.entity.chainS.report.ChainPurchaseStatisticReportItemVO;
import com.onlineMIS.ORM.entity.chainS.report.ChainReport;
import com.onlineMIS.ORM.entity.chainS.report.ChainReportItemVO;
import com.onlineMIS.ORM.entity.chainS.report.ChainSalesReport;
import com.onlineMIS.ORM.entity.chainS.report.ChainSalesStatisReportItem;
import com.onlineMIS.ORM.entity.chainS.report.ChainSalesStatisticReportItemVO;
import com.onlineMIS.ORM.entity.chainS.report.ChainWMRank;
import com.onlineMIS.ORM.entity.chainS.report.ChainWeeklySales;
import com.onlineMIS.ORM.entity.chainS.report.VIPReportVO;
import com.onlineMIS.ORM.entity.chainS.report.rptTemplate.ChainPurchaseStatisticsReportTemplate;
import com.onlineMIS.ORM.entity.chainS.report.rptTemplate.ChainSalesReportTemplate;
import com.onlineMIS.ORM.entity.chainS.report.rptTemplate.ChainSalesReportVIPPercentageTemplate;
import com.onlineMIS.ORM.entity.chainS.report.rptTemplate.ChainSalesStatisticsReportDetailTemplate;
import com.onlineMIS.ORM.entity.chainS.report.rptTemplate.ChainSalesStatisticsReportTemplate;
import com.onlineMIS.ORM.entity.chainS.report.rptTemplate.ChainVIPConsumptionRptTemplate;
import com.onlineMIS.ORM.entity.chainS.sales.ChainDailySales;
import com.onlineMIS.ORM.entity.chainS.sales.ChainStoreSalesOrder;
import com.onlineMIS.ORM.entity.chainS.sales.ChainStoreSalesOrderProduct;
import com.onlineMIS.ORM.entity.chainS.sales.PurchaseOrderTemplate;
import com.onlineMIS.ORM.entity.chainS.user.ChainRoleType;
import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.ORM.entity.chainS.user.ChainUserInfor;
import com.onlineMIS.ORM.entity.chainS.vip.ChainVIPCard;
import com.onlineMIS.ORM.entity.chainS.vip.ChainVIPPrepaidFlow;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Brand;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Category;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Color;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Product;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.ProductBarcode;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Quarter;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Year;
import com.onlineMIS.ORM.entity.headQ.finance.FinanceCategory;
import com.onlineMIS.ORM.entity.headQ.inventory.InventoryOrder;
import com.onlineMIS.action.chainS.report.ChainReportActionFormBean;
import com.onlineMIS.action.chainS.report.ChainReportActionUIBean;
import com.onlineMIS.common.Common_util;
import com.onlineMIS.common.ExcelUtil;
import com.onlineMIS.common.loggerLocal;
import com.onlineMIS.filter.SystemParm;
import com.onlineMIS.sorter.ChainInventoryReportSort;
import com.onlineMIS.sorter.ChainStatisticReportItemVOSorter;

@Service
public class ChainReportService {
	private final List<Integer> noDisplaySet = new ArrayList<Integer>(Arrays.asList(213,275,288));
	@Autowired
	private ChainStoreService chainStoreService;
	
	@Autowired
	private ChainInOutStockDaoImpl chainInOutStockDaoImpl;
	
	@Autowired
	private ChainStoreSalesOrderDaoImpl chainSalesOrderDaoImpl;
	
	@Autowired
	private InventoryOrderDAOImpl inventoryOrderDAOImpl;
	
	@Autowired
	private InventoryOrderProductDAOImpl inventoryOrderProductDAOImpl;
	
	@Autowired
	private ChainFinanceTraceImpl chainFinanceTraceImpl;
	
	@Autowired
	private FinanceCategoryImpl financeCategoryImpl;
	
	@Autowired
	private ChainUserInforDaoImpl chainUserInforDaoImpl;
	
	@Autowired
	private YearDaoImpl yearDaoImpl;
	
	@Autowired
	private ChainStoreDaoImpl chainStoreDaoImpl;
	
	@Autowired
	private QuarterDaoImpl quarterDaoImpl;
	
	@Autowired
	private BrandDaoImpl brandDaoImpl;
	
	@Autowired
	private ProductBarcodeDaoImpl productBarcodeDaoImpl;
	
	@Autowired
	private ChainWeeklySalesDaoImpl chainWeeklySalesDaoImpl;
	
	@Autowired
	private ProductDaoImpl productDaoImpl;
	
	@Autowired
	private ChainVIPCardImpl chainVIPCardImpl;
	
	@Autowired
	private ChainVIPPrepaidImpl chainVIPPrepaidImpl;
	
	@Autowired
	private ChainBatchRptRepositotyDaoImpl chainAutoRptRepositoryDaoImpl;
	
	@Autowired
	private ChainDailySalesDaoImpl chainDailySalesDaoImpl;
	@Autowired
	private ChainInventoryFlowOrderService chainInventoryFlowOrderService;
	/**
	 * to prepare the generate the report UI
	 * @param uiBean
	 * @param loginUser
	 */
	public void prepareGenReportUI(ChainReportActionFormBean formBean,ChainReportActionUIBean uiBean, ChainUserInfor loginUser){
		if (!ChainUserInforService.isMgmtFromHQ(loginUser)){
			ChainStore chainStore = chainStoreService.getChainStoreByID(loginUser.getMyChainStore().getChain_id());
			formBean.setChainStore(chainStore);
		} 
		
	}
	
	/**
	 * 准备按照员工分类的销售报表
	 * @param formBean
	 * @param uiBean
	 * @param userInfor
	 */
	public void prepareGenSalesReportBySalerUI(
			ChainReportActionFormBean formBean, ChainReportActionUIBean uiBean,
			ChainUserInfor loginUser) {
//		uiBean.setChainStores(chainStoreService.getChainStoreList(loginUser));
		
		if (!ChainUserInforService.isMgmtFromHQ(loginUser)){
			ChainStore chainStore = chainStoreService.getChainStoreByID(loginUser.getMyChainStore().getChain_id());
			formBean.setChainStore(chainStore);
		}
	}

	/**
	 * to generate the report
	 * @param formBean
	 */
	public Response generateChainReport(ChainReportActionFormBean formBean) {
		Response response = new Response();
		
		int chainId = formBean.getChainStore().getChain_id();
		int salerId = formBean.getSaler().getUser_id();
		Date startDate = Common_util.formStartDate(formBean.getStartDate());
		Date endDate = Common_util.formEndDate(formBean.getEndDate());
		int reportType = formBean.getReportType();
		
		ChainReport report = new ChainReport();
		report.setChainStore(formBean.getChainStore());
		
		/**
		 * 销售报表, 采购报表，财务报表
		 * type 1 : 直接表达出来，比如只求某个连锁店
		 *      2   ： 求销售的总数
		 */
		if (reportType == ChainReport.TYPE_SALES_REPORT){
			
			ChainSalesReport salesReport = generateSalesTotal(chainId, salerId, startDate, endDate, false);
			response.setReturnValue(salesReport);
			response.setAction(1);
		} else if (reportType == ChainReport.TYPE_PURCHASE_REPORT){
			int clientId = chainStoreService.getChainStoreByID(chainId).getClient_id();
			report = generatePurchaseReport(clientId, startDate, endDate);
			response.setReturnValue(report);
			response.setAction(1);
		} else if (reportType == ChainReport.TYPE_FINANCE_REPORT){
			report = generateFinanceReport(chainId, startDate, endDate);
			response.setReturnValue(report);
			response.setAction(1);
		}
		
		return response;
	}

	/**
	 * to generate the 财务报表
	 * @param chainId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public ChainReport generateFinanceReport(int chainId, Date startDate,
			Date endDate) {
		Object[] values = new Object[]{chainId, startDate, endDate};
		String hql = "select categoryId, sum(amount) from ChainFinanceTrace where chainId =? and date between ? and ? group by categoryId";
		List<ChainFinanceReportItem> reportItems = new ArrayList<ChainFinanceReportItem>();
		
		List<Object> reportObj =  chainFinanceTraceImpl.executeHQLSelect(hql, values,null, false);
		if (reportObj != null){
			Map<Integer, FinanceCategory> categoryMap = financeCategoryImpl.getFinanceCategoryMap();
			
			for (Object reportItemObj: reportObj){
				Object[] reportItemObjArray = (Object[])reportItemObj;
				int typeId = Common_util.getInt(reportItemObjArray[0]);
				double amount = Common_util.getDouble(reportItemObjArray[1]);
				
				FinanceCategory category = categoryMap.get(typeId);
				if (category != null){
					ChainFinanceReportItem reportItem = new ChainFinanceReportItem(category, amount);
					reportItems.add(reportItem);
				}
			}
		}
		
		ChainFinanceReport financeReport = new ChainFinanceReport();
		financeReport.setReportItems(reportItems);
		financeReport.setType(ChainReport.TYPE_FINANCE_REPORT);
		
		return financeReport;
	}

	/**
	 * to generate the purchase report 采购报表
	 * @param clientId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	private ChainReport generatePurchaseReport(int clientId, Date startDate,
			Date endDate) {
		Object[] valuesPurchase = new Object[]{startDate, endDate, InventoryOrder.STATUS_ACCOUNT_COMPLETE,clientId, InventoryOrder.TYPE_SALES_ORDER_W};
		Object[] valuesReturn = new Object[]{startDate, endDate, InventoryOrder.STATUS_ACCOUNT_COMPLETE,clientId, InventoryOrder.TYPE_SALES_RETURN_ORDER_W};
		
		String hql_purchase = "select sum(totalQuantity), sum(totalWholePrice) from InventoryOrder where order_EndTime between ? and ? and order_Status = ? and client_id=? and order_type =?";
		String hql_return = "select sum(totalQuantity), sum(totalWholePrice) from InventoryOrder where order_EndTime between ? and ? and order_Status = ? and client_id=? and order_type =?";
		
		Object[] purchase =  (Object[])inventoryOrderDAOImpl.executeHQLSelect(hql_purchase, valuesPurchase,null, false).get(0);
		Object[] returns = (Object[])inventoryOrderDAOImpl.executeHQLSelect(hql_return, valuesReturn,null, false).get(0);
		
		int purchaseQ = Common_util.getInt(purchase[0]);
		int returnQ = Common_util.getInt(returns[0]);
		double purchaseP = Common_util.getDouble(purchase[1]);
		double returnP = Common_util.getDouble(returns[1]);
		
		ChainPurchaseReport purchaseReport = new ChainPurchaseReport(ChainReport.TYPE_PURCHASE_REPORT, purchaseQ, returnQ, purchaseP, returnP);

		return purchaseReport;
	}
	
	/**
	 * 连锁店总部获取信息
	 * @param formBean
	 * @return
	 */
	public Response generateSalesReport(ChainReportActionFormBean formBean, Integer page, Integer rowPerPage, String sortName, String sortOrder) {
		Response response = new Response();
		
		int chainId = formBean.getChainStore().getChain_id();
		Date startDate = Common_util.formStartDate(formBean.getStartDate());
		Date endDate = Common_util.formEndDate(formBean.getEndDate());
		int salerId = Common_util.ALL_RECORD;
		
		ChainReport report = new ChainReport();
		report.setChainStore(formBean.getChainStore());
		
		Map data = new HashMap<String, Object>();
		
		try {
			generateSalesReportByHQ(data, chainId, salerId, startDate, endDate, page, rowPerPage, sortName, sortOrder);
			response.setReturnCode(Response.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			response.setReturnCode(Response.FAIL);
		}
		response.setReturnValue(data);
		
		return response;
	}
	
	/**
	 * 获取sales report的总数,或者连锁店获取自己的总数
	 * @param chainId
	 * @param salerId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	private ChainSalesReport generateSalesTotal(int chainId, int salerId, Date startDate, Date endDate, boolean skipTotalPrepaid){
		/**1. to get the information from the sales order and exchange order
		 * 收入, 销售额， 销售总量，退货额，退货总量,,
		 */
		Object[] value_sale = new Object[]{startDate, endDate, ChainStoreSalesOrder.STATUS_COMPLETE};
		String chainCriteria = "";
		String chainCriteriaPrepaid = "";
		
		if (chainId == Common_util.ALL_RECORD) {
			String noDisplayStore = "(" + ChainStore.CHAIN_ID_TEST_ID;
			for (int i = 0; i < noDisplaySet.size();i++){
				noDisplayStore += "," +noDisplaySet.get(i);
			}
			noDisplayStore += ")";
			
			
			chainCriteria = " chainStore.chain_id not in " + noDisplayStore;
			chainCriteriaPrepaid = " chainStore.chain_id not in " + noDisplayStore;
		} else {
			chainCriteria = " chainStore.chain_id = " + chainId;
			chainCriteriaPrepaid = " c.chainStore.chain_id = " + chainId;
		}
		
		
		if (salerId != Common_util.ALL_RECORD){
			chainCriteria += " AND saler.user_id = " + salerId;
			chainCriteriaPrepaid += " AND c.operator.user_id = " + salerId;
		}

		String hql_sale = "select sum(totalQuantity), sum(netAmount),  sum(totalQuantityR), " +
				"sum(netAmountR), sum(totalCost), sum(totalQuantityF) ,sum(totalCostF), sum(discountAmount), " +
				"sum(coupon), sum (cardAmount), sum(cashAmount - returnAmount), sum(vipScore), sum(totalAmount - netAmount), sum(qxQuantity), sum(qxAmount), sum(qxCost), sum(myQuantity), sum(myAmount), sum(myCost), sum(chainPrepaidAmt), sum(wechatAmount), sum(alipayAmount) from ChainStoreSalesOrder where orderDate between ? and ? and status = ? and " + chainCriteria;

		Object[] sales = (Object[])chainSalesOrderDaoImpl.executeHQLSelect(hql_sale, value_sale,null, true).get(0);

		int totalQ = Common_util.getInt(sales[0]);
		double netAmt = Common_util.getDouble(sales[1]);
		int totalQR = Common_util.getInt(sales[2]);
		double netAmtR = Common_util.getDouble(sales[3]);
		double totalCost = Common_util.getDouble(sales[4]);
		int totalQF = Common_util.getInt(sales[5]);
		double totalCostF = Common_util.getDouble(sales[6]);
		double discountAmt = Common_util.getDouble(sales[7]);
		double coupon = Common_util.getDouble(sales[8]);
		double cardAmt = Common_util.getDouble(sales[9]);
		double cashAmt = Common_util.getDouble(sales[10]);
		double vipScoreAmt = Common_util.getDouble(sales[11]);
		double totalSalesDiscountAmt = Common_util.getDouble(sales[12]);
		int qxQuantity = Common_util.getInt(sales[13]);
		double qxAmount = Common_util.getDouble(sales[14]);
		double qxCost = Common_util.getDouble(sales[15]);
		int myQuantity = Common_util.getInt(sales[16]);
		double myAmount = Common_util.getDouble(sales[17]);
		double myCost = Common_util.getDouble(sales[18]);
		double vipPrepaidAmt = Common_util.getDouble(sales[19]);
		double wechatAmt = Common_util.getDouble(sales[20]);
		double alipayAmt = Common_util.getDouble(sales[21]);
		
		//2. 计算vip的数量
		String hql_sale_vipString = "SELECT SUM(totalQuantity - totalQuantityR), SUM(netAmount - netAmountR)   FROM ChainStoreSalesOrder WHERE orderDate between ? and ? and status = ? AND vipCard IS NOT NULL AND " + chainCriteria; 
		Object[] salesVip = (Object[])chainSalesOrderDaoImpl.executeHQLSelect(hql_sale_vipString, value_sale,null, true).get(0);
		int vipQ = Common_util.getInt(salesVip[0]);
		double vipAmt = Common_util.getDouble(salesVip[1]);
		
		//3。 计算 vip prepaid desposit
		double vipPrepaidDepositCash = 0;
		double vipPrepaidDepositCard = 0;
		double vipPrepaidDepositAlipay = 0;
		double vipPrepaidDepositWechat = 0;
		
		String hql = "SELECT c.depositType, sum(amount) FROM ChainVIPPrepaidFlow c WHERE c.operationType = ? AND c.status="+ ChainVIPPrepaidFlow.STATUS_SUCCESS +" AND "+ chainCriteriaPrepaid +" AND c.dateD BETWEEN ? AND ? GROUP BY c.depositType";
	    Object[] values = new Object[]{ChainVIPPrepaidFlow.OPERATION_TYPE_DEPOSIT, startDate, endDate };
	    List<Object> prepaid = chainVIPPrepaidImpl.executeHQLSelect(hql, values,null, true);
	    
	    if (prepaid != null && prepaid.size() > 0)
		  for (Object object: prepaid){
			  Object[] object2 = (Object[])object;
			  if (object2[0] == null || object2[1] == null)
				  continue;
			  String depositType = object2[0].toString();
			  double amount = Common_util.getDouble(object2[1]);

			  if (depositType.equalsIgnoreCase(ChainVIPPrepaidFlow.DEPOSIT_TYPE_CARD))
				  vipPrepaidDepositCard += amount;
			  else if (depositType.equalsIgnoreCase(ChainVIPPrepaidFlow.DEPOSIT_TYPE_CASH))
				  vipPrepaidDepositCash += amount;
			  else if (depositType.equalsIgnoreCase(ChainVIPPrepaidFlow.DEPOSIT_TYPE_ALIPAY))
				  vipPrepaidDepositAlipay += amount;
			  else if (depositType.equalsIgnoreCase(ChainVIPPrepaidFlow.DEPOSIT_TYPE_WECHAT))
				  vipPrepaidDepositWechat += amount;			  
		   }
	    
	    //4. 计算累计还有多少预存款
	    double prepaidTotal = 0;
	    if (!skipTotalPrepaid){
	    	String hqlPreaidSum = "SELECT sum(calculatedAmt) FROM ChainVIPPrepaidFlow c WHERE c.status="+ ChainVIPPrepaidFlow.STATUS_SUCCESS + " AND " + chainCriteriaPrepaid;
	    	List<Object> prepaidTotalObj = chainVIPPrepaidImpl.executeHQLSelect(hqlPreaidSum, null, null, true);
			for (Object record : prepaidTotalObj){
				prepaidTotal = Common_util.getDouble(record);
			}
	    }

		
		ChainStore chainStore = new ChainStore();
		chainStore.setChain_id(chainId);
		ChainSalesReport chainReport = new ChainSalesReport(ChainReport.TYPE_SALES_REPORT, totalQ, totalQR,
				totalQF, netAmt,totalSalesDiscountAmt, netAmtR,totalCost, totalCostF, discountAmt,
				coupon, cardAmt, cashAmt, vipScoreAmt, qxQuantity,qxAmount, qxCost, myQuantity, myAmount, myCost,vipQ, vipAmt,vipPrepaidAmt,vipPrepaidDepositCash, vipPrepaidDepositCard,vipPrepaidDepositAlipay, vipPrepaidDepositWechat,prepaidTotal, wechatAmt, alipayAmt);
		chainReport.setChainStore(chainStore);
		
		return chainReport;
	}
	
	/**
	 * 计算sale report 分页
	 * @param chainId
	 * @param salerId
	 * @param startDate
	 * @param endDate
	 * @param pager
	 */
	private int calculateSaleReportCount(int chainId, Object[] value_sale){
		//2.1 计算pager
		String criteria = "";
		if (chainId == Common_util.ALL_RECORD)
			criteria = " FROM ChainStoreSalesOrder WHERE orderDate BETWEEN ? AND ? AND status = ?";
		else 
			criteria = "FROM ChainStoreSalesOrder WHERE orderDate BETWEEN ? AND ? AND status = ? AND chainStore.chain_id = " + chainId;

		String criteria2 = "SELECT COUNT(DISTINCT chainStore.chain_id) " + criteria;
		    
	    return chainSalesOrderDaoImpl.executeHQLCount(criteria2, value_sale, true);
	}


	/**
	 * to generate the sales report 销售报表
	 * @param chainId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	private void generateSalesReportByHQ(Map saleReport, int chainId, int salerId, Date startDate,
			Date endDate, Integer page, Integer rowPerPage, String sortName, String sortOrder) {
		/**
		 * 1. 获取total
		 */
		Object[] value_sale = new Object[]{startDate, endDate, ChainStoreSalesOrder.STATUS_COMPLETE};
		ChainSalesReport totalReport = generateSalesTotal(chainId, salerId, startDate, endDate, true);
		
		/**
		 * 2. 实现分页,如果是搜索所有连锁店
		 */
		int total = 0;
		if (page != null && rowPerPage != null)
			total = calculateSaleReportCount(chainId, value_sale);
		
		/**
		 * 获取数据列表
		 */
		List<Integer> chainIdList = new ArrayList<Integer>(); 
		List<ChainSalesReport> reports = new ArrayList<ChainSalesReport>();
		String criteria = "";
		if (chainId == Common_util.ALL_RECORD)
			criteria = " FROM ChainStoreSalesOrder WHERE orderDate BETWEEN ? AND ? AND status = ?";
		else 
			criteria = "FROM ChainStoreSalesOrder WHERE orderDate BETWEEN ? AND ? AND status = ? AND chainStore.chain_id = " + chainId;

		criteria += " GROUP BY chainStore.chain_id ";
		
		String orderBy = generateSalesOrderBy(sortName, sortOrder);
		
		criteria += orderBy;
		
		String hql_sale2 = "SELECT sum(totalQuantity), sum(netAmount), sum(totalQuantityR), " +
				"sum(netAmountR), sum(totalCost), sum(totalQuantityF) ,sum(totalCostF), sum(discountAmount), " +
				"sum(coupon), sum (cardAmount), sum(cashAmount - returnAmount), sum(vipScore), chainStore.chain_id, sum(totalAmount - netAmount), sum(qxQuantity), sum(qxAmount), sum(qxCost), sum(myQuantity), sum(myAmount), sum(myCost),sum(chainPrepaidAmt), sum(wechatAmount), sum(alipayAmount)  " + criteria;

		Integer[] pagerArray = null;
		if (page != null && rowPerPage != null)
		      pagerArray = new Integer[]{Common_util.getFirstRecord(page, rowPerPage), rowPerPage};
		
		List<Object> sales2 = (List<Object>)chainSalesOrderDaoImpl.executeHQLSelect(hql_sale2, value_sale,pagerArray, true);

		for (Object resultObject : sales2){
			Object[] sales3 = (Object[])resultObject;
			int totalQ2 = Common_util.getInt(sales3[0]);
			double netAmt2 = Common_util.getDouble(sales3[1]);
			int totalQR2 = Common_util.getInt(sales3[2]);
			double netAmtR2 = Common_util.getDouble(sales3[3]);
			double totalCost2 = Common_util.getDouble(sales3[4]);
			int totalQF2 = Common_util.getInt(sales3[5]);
			double totalCostF2 = Common_util.getDouble(sales3[6]);
			double discountAmt2 = Common_util.getDouble(sales3[7]);
			double coupon2 = Common_util.getDouble(sales3[8]);
			double cardAmt2 = Common_util.getDouble(sales3[9]);
			double cashAmt2 = Common_util.getDouble(sales3[10]);
			double vipScoreAmt2 = Common_util.getDouble(sales3[11]);
			int chainStoreId =  Common_util.getInt(sales3[12]);
			chainIdList.add(chainStoreId);
			
			double totalSalesDiscountAmt2 = Common_util.getDouble(sales3[13]);
			int qxQuantity = Common_util.getInt(sales3[14]);
			double qxAmount = Common_util.getDouble(sales3[15]);
			double qxCost = Common_util.getDouble(sales3[16]);
			int myQuantity = Common_util.getInt(sales3[17]);
			double myAmount = Common_util.getDouble(sales3[18]);
			double myCost = Common_util.getDouble(sales3[19]);
			double vipPrepaidAmt = Common_util.getDouble(sales3[20]);
			double wechatAmt =  Common_util.getDouble(sales3[21]);
			double alipayAmt =  Common_util.getDouble(sales3[22]);
			
			ChainStore store = chainStoreDaoImpl.get(chainStoreId, true);
			
			ChainSalesReport chainReport2 = new ChainSalesReport(ChainReport.TYPE_SALES_REPORT, totalQ2, totalQR2,
					totalQF2, netAmt2,totalSalesDiscountAmt2, netAmtR2,totalCost2, totalCostF2, discountAmt2,
					coupon2, cardAmt2, cashAmt2, vipScoreAmt2, qxQuantity,qxAmount, qxCost, myQuantity, myAmount, myCost, 0, 0,vipPrepaidAmt,0,0,0,0,0,store,wechatAmt, alipayAmt);
			reports.add(chainReport2);
		}
		
		//4. 将prepaid deposit 计算出来
		Map<Integer, Double> prepaidMap = new HashMap<Integer, Double>();
		String chainCriteriaPrepaid = "";
		
		if (chainIdList.size() > 0){
			chainCriteriaPrepaid = " AND c.chainStore.chain_id IN (";
			for (int i = 0; i < chainIdList.size(); i++){
				if (i == chainIdList.size() -1)
					chainCriteriaPrepaid += chainIdList.get(i) + ")";
				else 
					chainCriteriaPrepaid += chainIdList.get(i) + ",";
			}
		}
		String hql = "SELECT c.chainStore.chain_id, sum(amount) FROM ChainVIPPrepaidFlow c WHERE c.operationType = ? AND c.status = "+ ChainVIPPrepaidFlow.STATUS_SUCCESS+" "+ chainCriteriaPrepaid +" AND c.dateD BETWEEN ? AND ? GROUP BY c.chainStore.chain_id";
	    Object[] values = new Object[]{ChainVIPPrepaidFlow.OPERATION_TYPE_DEPOSIT,startDate, endDate };
	    List<Object> prepaid = chainVIPPrepaidImpl.executeHQLSelect(hql, values,null, true);
	    
	    if (prepaid != null && prepaid.size() > 0)
		  for (Object object: prepaid){
			  Object[] object2 = (Object[])object;
			  if (object2[0] == null || object2[1] == null)
				  continue;
			  int chainIdPrepaid = Common_util.getInt(object2[0]);
			  Double amount = Common_util.getDouble(object2[1]);

			  prepaidMap.put(chainIdPrepaid, amount);
		   }
	    
	    //5. 把预付金放进去
	    int removeId = -1;
	    for (int i = 0; i < reports.size(); i++){
	    	ChainSalesReport rpt = reports.get(i);
	    	int chainIdRpt = rpt.getChainStore().getChain_id();
	    	
	    	//剔除乐至喜乐仓,巴中鞋子帐户
	    	if (noDisplaySet.contains(chainIdRpt)){
	    		removeId = i;
	    	}
	    	
	    	Double prepaidAmt = prepaidMap.get(chainIdRpt);
	    	if (prepaidAmt == null)
	    		rpt.setVipPrepaidDepositCash(0);
	    	else
	    		rpt.setVipPrepaidDepositCash(prepaidAmt);
	    }
	    
	    if (removeId != -1)
	    	reports.remove(removeId);

		List<ChainSalesReport> footer = new ArrayList<ChainSalesReport>();
		footer.add(totalReport);
		saleReport.put("footer", footer);
		saleReport.put("rows", reports);
		saleReport.put("total", total);
	}
	
	/**
	 * 生成销售分析报表
	 * @param formBean
	 * @param page
	 * @param rows
	 * @param sort
	 * @param order
	 * @return
	 */
	public Response generateSalesAnalysisReport(
			ChainReportActionFormBean formBean, int page, int rows,
			String sort, String order,ChainUserInfor userInfor) {
		Response response = new Response();
		/**
		 * 如果是非连锁店经理，只能在6点以后查询
		 */
//		if (!ChainUserInforService.isMgmtFromHQ(userInfor)){
//			Date currentDate = new Date();
//            int hr = currentDate.getHours();
//			if (hr < 18 && hr >9){
//				response.setReturnCode(Response.FAIL);
//				response.setMessage("因为数据收集和计算等原因，普通用户只能在晚上六点以后使用此功能");
//				return response;
//			}
//		}
//		
		
		int chainId = formBean.getChainStore().getChain_id();
		Date startDate = Common_util.formStartDate(formBean.getStartDate());
		Date endDate = Common_util.formEndDate(formBean.getEndDate());
		
		/**
		 * 1. 获取连锁店total
		 */
		Object[] value_sale = new Object[]{startDate, endDate, ChainStoreSalesOrder.STATUS_COMPLETE};
		ChainDailySalesAnalysis totalReport = generateSalesAnalysisTotal(chainId, value_sale);
		
		
		List<ChainDailySalesAnalysis> footer = new ArrayList<ChainDailySalesAnalysis>();
		footer.add(totalReport);
		
		List<ChainDailySalesAnalysis> reports = generateSalesAnalysis(chainId, value_sale);
		
		Map saleReport = new HashMap();
		saleReport.put("footer", footer);
		saleReport.put("rows", reports);
		saleReport.put("total", new Integer(1));
		
		
		
		response.setReturnValue(saleReport);
		response.setReturnCode(Response.SUCCESS);
		return response;
	}

	/**
	 * 生成连锁店销售分析报表的总计
	 * @param chainId
	 * @param value_sale
	 * @return
	 */
	private ChainDailySalesAnalysis generateSalesAnalysisTotal(int chainId,
			Object[] value_sale) {
		String chainCriteria = "";
		//1. 创建parameters
		String chainOneQuantityCritiera = "";
		chainOneQuantityCritiera = " chainStore.chain_id <> " + ChainStore.CHAIN_ID_TEST_ID + " AND totalQuantityA = 1 ";
		chainCriteria = " chainStore.chain_id <> " + ChainStore.CHAIN_ID_TEST_ID + " AND totalQuantityA > 0 AND totalQuantityA < " + Common_util.SALES_ANALYSIS_BAD_ORDER;

		String hql_sale_analysis = "select avg(totalQuantityA), max(totalQuantityA),  sum(netAmountA)/sum(totalQuantityA), count(id), chainStore.chain_id from ChainStoreSalesOrder where orderDate between ? and ? and status = ? and " + chainCriteria;
		String hql_sale_analysis_one = "select count(id), chainStore.chain_id from ChainStoreSalesOrder where orderDate between ? and ? and status = ? and " + chainOneQuantityCritiera;
		
		//2. 第一轮select
		List<Object> salesAnalysis = (List<Object>)chainSalesOrderDaoImpl.executeHQLSelect(hql_sale_analysis, value_sale,null, true);

		ChainStore store = ChainStoreDaoImpl.getAllChainStoreObject();
		store.setChain_name("所有店平均水平");
		ChainDailySalesAnalysis dailySalesAnalysis = new ChainDailySalesAnalysis(store, null);
		
		for (Object resultObject : salesAnalysis){
			Object[] sales = (Object[])resultObject;
			double liandai = Common_util.getDouble(sales[0]);
			int largestSalesQ = Common_util.getInt(sales[1]);		
			double kedan = Common_util.getDouble(sales[2]);
			int countArray = Common_util.getInt(sales[3]);
			
			if (countArray < Common_util.SALES_ANALYSIS_BAD_STORE)
				continue;

			dailySalesAnalysis.setKeDanAvg(kedan);
			dailySalesAnalysis.setLargestSalesQ(largestSalesQ);
			dailySalesAnalysis.setLianDaiRatio(liandai);
			
			//3. 第二轮select
			List<Object> salesAnalysis2 = (List<Object>)chainSalesOrderDaoImpl.executeHQLSelect(hql_sale_analysis_one, value_sale,null, true);
			for (Object resultObject2 : salesAnalysis2){
				Object[] sales2 = (Object[])resultObject2;
				int oneQCountArray = Common_util.getInt(sales2[0]);

				if (countArray != 0)
				    dailySalesAnalysis.setKeChiOrderRatio((double)oneQCountArray/countArray);
			}
		}
		
		return dailySalesAnalysis;
	}
	
	private List<ChainDailySalesAnalysis> generateSalesAnalysis(int chainId, Object[] value_sale){
		String chainCriteria = "";
		//单件量
		String chainOneQuantityCritiera = "";
		if (chainId == Common_util.ALL_RECORD){
			chainOneQuantityCritiera = " chainStore.chain_id <> " + ChainStore.CHAIN_ID_TEST_ID + " AND totalQuantityA = 1  GROUP BY chainStore.chain_id ";
			chainCriteria = " chainStore.chain_id <> " + ChainStore.CHAIN_ID_TEST_ID + " AND  totalQuantityA > 0  AND totalQuantityA < " + Common_util.SALES_ANALYSIS_BAD_ORDER +" GROUP BY chainStore.chain_id ORDER BY avg(totalQuantityA) DESC";
		} else {
			chainOneQuantityCritiera = " chainStore.chain_id = " + chainId + " AND totalQuantityA = 1";
			chainCriteria = " chainStore.chain_id = " + chainId + " AND totalQuantityA > 0  AND totalQuantityA < " + Common_util.SALES_ANALYSIS_BAD_ORDER;
		}
		

		String hql_sale_analysis = "select avg(totalQuantityA), max(totalQuantityA),  sum(netAmountA)/sum(totalQuantityA) , count(id), chainStore.chain_id from ChainStoreSalesOrder  where orderDate between ? and ? and status = ? and " + chainCriteria;
		String hql_sale_analysis_one = "select count(id), chainStore.chain_id from ChainStoreSalesOrder where orderDate between ? and ? and status = ? and " + chainOneQuantityCritiera;
		
		/**
		 * 1. 生成除开可耻率的数据
		 */
		List<Object> salesAnalysis = (List<Object>)chainSalesOrderDaoImpl.executeHQLSelect(hql_sale_analysis, value_sale,null, true);

		
		Map<Integer, ChainDailySalesAnalysis> salesAnalysisMap = new HashMap<Integer, ChainDailySalesAnalysis>();
		Map<Integer, Integer> orderNumMap= new HashMap<Integer, Integer>();
		int rank = 0;

		for (Object resultObject : salesAnalysis){
			rank++;
			Object[] sales = (Object[])resultObject;
			

			double liandai = Common_util.getDouble(sales[0]);
			int largestSalesQ = Common_util.getInt(sales[1]);		
			double kedan = Common_util.getDouble(sales[2]);
			int countArray = Common_util.getInt(sales[3]);
			int chainIdArray = Common_util.getInt(sales[4]);
			
			if (countArray < Common_util.SALES_ANALYSIS_BAD_STORE)
				continue;
			
			ChainStore store = chainStoreDaoImpl.get(chainIdArray, true);
			ChainDailySalesAnalysis dailySalesAnalysis = new ChainDailySalesAnalysis(store, null);
			

			dailySalesAnalysis.setKeDanAvg(kedan);
			dailySalesAnalysis.setLargestSalesQ(largestSalesQ);
			dailySalesAnalysis.setLianDaiRatio(liandai);
			dailySalesAnalysis.setRank(rank);
			dailySalesAnalysis.setKeChiOrderRatio(0);

			salesAnalysisMap.put(chainIdArray, dailySalesAnalysis);
			
			orderNumMap.put(chainIdArray, countArray);
		}
		
		/**
		 * 2. 生成可耻率数据
		 */
		List<Object> salesAnalysis2 = (List<Object>)chainSalesOrderDaoImpl.executeHQLSelect(hql_sale_analysis_one, value_sale,null, true);
		for (Object resultObject2 : salesAnalysis2){
			Object[] sales2 = (Object[])resultObject2;
			int oneQCountArray = Common_util.getInt(sales2[0]);
			int chainIdArray = Common_util.getInt(sales2[1]);

			ChainDailySalesAnalysis chainDailySalesAnalysis = salesAnalysisMap.get(chainIdArray);
			if (chainDailySalesAnalysis != null){
				int countArray = orderNumMap.get(chainIdArray);
				if (countArray != 0){
					chainDailySalesAnalysis.setKeChiOrderRatio((double)oneQCountArray/countArray);
					salesAnalysisMap.put(chainIdArray, chainDailySalesAnalysis);
				}
			}
		}
		
		List<ChainDailySalesAnalysis> salesAnalysises = new ArrayList<ChainDailySalesAnalysis>(salesAnalysisMap.values());
		
		Collections.sort(salesAnalysises, new Comparator<ChainDailySalesAnalysis>() {
			@Override
			public int compare(ChainDailySalesAnalysis arg0,
					ChainDailySalesAnalysis arg1) {
				return arg0.getRank() - arg1.getRank();
			}	
		});
		
		return salesAnalysises;
	}

	/**
	 * 创建order by
	 * @param sortName
	 * @param sortOrder
	 * @return
	 */
	private String generateSalesOrderBy(String sortName, String sortOrder) {
		String orderBy = " ORDER BY sum(netAmount - netAmountR) desc";
		if (sortName != null && sortOrder != null){
			if (sortName.equals("saleQuantitySum"))
				orderBy = " ORDER BY sum(totalQuantity) " + sortOrder;
			else if (sortName.equals("returnQuantitySum"))
				orderBy = " ORDER BY sum(totalQuantityR) " + sortOrder;
			else if (sortName.equals("netQuantitySum"))
				orderBy = " ORDER BY sum(totalQuantity - totalQuantityR) " + sortOrder;
			else if (sortName.equals("salesAmtSum"))
				orderBy = " ORDER BY sum(netAmount) " + sortOrder;
			else if (sortName.equals("returnAmtSum"))
				orderBy = " ORDER BY sum(netAmountR) " + sortOrder;
			else if (sortName.equals("netAmtSum"))
				orderBy = " ORDER BY sum(netAmount - netAmountR) " + sortOrder;
			else if (sortName.equals("netProfit"))
				orderBy = " ORDER BY sum(netAmount - netAmountR - totalCost - totalCostF - discountAmount) " + sortOrder;
			else if (sortName.equals("receiveAmtSum"))
				orderBy = " ORDER BY sum(cardAmount + cashAmount - returnAmount + alipayAmount + wechatAmount) " + sortOrder;
			else if (sortName.equals("qxAmount"))
				orderBy = " ORDER BY sum(qxAmount) " + sortOrder;
		}
		return orderBy;
	}

	/**
	 * 准备销售统计报表的UI element
	 * @param formBean
	 * @param uiBean
	 */
	public void prepareSalesStatisticReportUIBean(
			ChainReportActionFormBean formBean, ChainReportActionUIBean uiBean, ChainUserInfor userInfor) {
		//1. 检查chain store
		checkChainStoreUsers(formBean, uiBean, userInfor);
		
		//2. 构建year list
		List<Year> years = yearDaoImpl.getAll(true);
		uiBean.setYears(years);
		
		//3. 构建quarter list
		List<Quarter> quarters = quarterDaoImpl.getAll(true);
		uiBean.setQuarters(quarters);	
	}
	

	
	/**
	 * 准备采购统计报表ui
	 * @param formBean
	 * @param uiBean
	 * @param userInfor
	 */
	public void preparePurchaseStatisticReportUIBean(
			ChainReportActionFormBean formBean, ChainReportActionUIBean uiBean,
			ChainUserInfor userInfor) {
		//1. 检查chain store
		if (!ChainUserInforService.isMgmtFromHQ(userInfor)){
			int chainId = userInfor.getMyChainStore().getChain_id();
			ChainStore chainStore = chainStoreService.getChainStoreByID(chainId);
			formBean.setChainStore(chainStore);
		} else {
			ChainStore allChainStore = ChainStoreDaoImpl.getAllChainStoreObject();
			formBean.setChainStore(allChainStore);
		}
		
		//2. 构建year list
		List<Year> years = yearDaoImpl.getAll(true);
		uiBean.setYears(years);
		
		//3. 构建quarter list
		List<Quarter> quarters = quarterDaoImpl.getAll(true);
		uiBean.setQuarters(quarters);	
		
	}

	/**
	 * 准备销售报表的ui
	 * @param formBean
	 * @param uiBean
	 * @param userInfor
	 */
	public void prepareGenSalesReportUI(ChainReportActionFormBean formBean, ChainReportActionUIBean uiBean,
			ChainUserInfor userInfor) {
		//1. 检查chain store
		checkChainStoreUsers(formBean, uiBean, userInfor);
		
	}
	
	private void checkChainStoreUsers(ChainReportActionFormBean formBean, ChainReportActionUIBean uiBean,
			ChainUserInfor userInfor){
		if (!ChainUserInforService.isMgmtFromHQ(userInfor)){
			int chainId = userInfor.getMyChainStore().getChain_id();
			ChainStore chainStore = chainStoreService.getChainStoreByID(chainId);
			formBean.setChainStore(chainStore);
			
			List<ChainUserInfor> salers = new ArrayList<ChainUserInfor>();
			salers.addAll(chainUserInforDaoImpl.getActiveChainUsersByChainStore(chainId));
			uiBean.setChainSalers(salers);
		} else {
			ChainStore allChainStore = ChainStoreDaoImpl.getAllChainStoreObject();
			formBean.setChainStore(allChainStore);
		}
	}

	

	/**
	 * 本业务的方法，对于chainId=全部，需要另外处理
	 * @param chainId
	 * @return
	 */
	private ChainStore getThisChainStore(int chainId){
		ChainStore chainStore = new ChainStore();
		if (chainId == Common_util.ALL_RECORD)
			chainStore = chainStoreDaoImpl.getAllChainStoreObject();
		else 
			chainStore = chainStoreDaoImpl.get(chainId, true);
		return chainStore;
	}
	
	/**
	 * 本业务的方法，对于userId=全部，需要另外处理
	 * @param chainId
	 * @return
	 */	
	private ChainUserInfor getThisChainUserInfor(int userId){
		ChainUserInfor saler = new ChainUserInfor();

		if (userId == Common_util.ALL_RECORD)
			saler = chainUserInforDaoImpl.getAllChainUserObject();
		else 
			saler = chainUserInforDaoImpl.get(userId, true);
		
		return saler;
	}


	
	/**
	 * 获取登录排名信息
	 * @return
	 */
	@Transactional
	public Response getRank(ChainUserInfor userInfor){
		Response response = new Response();
		
		if (userInfor.getMyChainStore() == null || userInfor.getMyChainStore().getChain_id() == Common_util.ALL_RECORD){
			response.setFail("");
			return response;
		}
		
		int myChainId = userInfor.getMyChainStore().getChain_id();
		
		
		/**
		 * 我前周排名
		 */
		List<java.sql.Date> lastWeekDays = Common_util.getLastWeekDays();
		ChainWeeklySales weeklySale = chainWeeklySalesDaoImpl.getWeeklyRankById(lastWeekDays.get(0), myChainId);
		ChainWMRank weeklyRank = new ChainWMRank();
		if (weeklySale != null) {
			int salesQ = weeklySale.getNetSalesQuantity();
			int salesAmt = (int)weeklySale.getNetSalesAmount();
			int rank = weeklySale.getRank();
			String salesQS = String.valueOf(salesQ);
			String salesAmtS = String.valueOf(salesAmt);
	
			weeklyRank.setRank(rank);
			weeklyRank.setSaleQ(salesQS);
			weeklyRank.setSaleAmt(salesAmtS);
	
			weeklyRank.setStartDate(lastWeekDays.get(0));
			weeklyRank.setEndDate(lastWeekDays.get(6));
			weeklyRank.setWeekly(true);
		}
		
		/**
		 * 获取我日名次
		 */
		java.sql.Date lastDate = Common_util.getDate(-3);
		DetachedCriteria criteria2 = DetachedCriteria.forClass(ChainDailySales.class);
		criteria2.add(Restrictions.gt("reportDate", lastDate));
		criteria2.add(Restrictions.le("reportDate", Common_util.getToday()));
		criteria2.add(Restrictions.eq("chainStore.chain_id", myChainId));
			
		List<ChainDailySales> dailySales = chainDailySalesDaoImpl.getByCritera(criteria2, true);
		List<ChainWMRank> dailyRanks = new ArrayList<ChainWMRank>();
		
		if (dailySales != null && dailySales.size()>0){
			for (ChainDailySales dailySales2 : dailySales){
				ChainWMRank dailyRank = new ChainWMRank();

				int salesQ = dailySales2.getSalesQuantity();
				int salesAmt = (int)dailySales2.getSalesAmount();
				int rank = dailySales2.getRank();
				java.sql.Date date = dailySales2.getReportDate();

				dailyRank.setRank(rank);
				dailyRank.setSaleQ(String.valueOf(salesQ));
				dailyRank.setSaleAmt(String.valueOf(salesAmt));
				dailyRank.setRank(rank);
				dailyRank.setStartDate(date);
				
				dailyRanks.add(dailyRank);
			}
		}

		
		List<Object> returnValue = new ArrayList<Object>();
		returnValue.add(weeklyRank);
		returnValue.add(dailyRanks);
		
		response.setReturnValue(returnValue);
		response.setReturnCode(Response.SUCCESS);
		
		return response;
		
	}
	
	/**
	 * 
	 * @return
	 */
	private String constructChainSQL(String prefix,List<Object> value_sale){
		List<ChainStore> stores = chainStoreService.getAvailableClientChainstores();
		prefix += " IN (";
		if (stores != null && stores.size() > 0){
			for (int i = 0; i < stores.size();i++){
				ChainStore chainStore = stores.get(i);
				if (i == stores.size()-1)
				    prefix += chainStore.getClient_id() + ")";
				else 
					prefix += chainStore.getClient_id() + ",";
			}
			return prefix;
		} else 
			return null;
	}

	/**
	 * 创建销售报表到excel
	 * @param dataMap
	 * @param string
	 * @return
	 */
	public Map<String, Object> generateSalesRptExcel(Map dataMap, Map lastYearMap,String path, Date startDate, Date endDate) {
		Map<String,Object> returnMap=new HashMap<String, Object>();   

		ByteArrayInputStream byteArrayInputStream;   
		try {     
			HSSFWorkbook wb = null;   
			
			//to get the order information from database
			List<ChainSalesReport> rptEles = (List<ChainSalesReport>)dataMap.get("rows");
			List<ChainSalesReport> footer =  (List<ChainSalesReport>)dataMap.get("footer");
			
			ChainSalesReportTemplate rptTemplate = new ChainSalesReportTemplate(rptEles, lastYearMap, footer.get(0), startDate, endDate, path);
			wb = rptTemplate.process();

			ByteArrayOutputStream os = new ByteArrayOutputStream();   
			try {   
			    wb.write(os);   
			} catch (IOException e) {   
				loggerLocal.error(e);
		    }   
		    byte[] content = os.toByteArray();   
		    byteArrayInputStream = new ByteArrayInputStream(content);   
		    returnMap.put("stream", byteArrayInputStream);   
         
		    return returnMap;   
		 } catch (Exception ex) {   
			 loggerLocal.error(ex);
		 }   
		return null;   
	}

	/**
	 * 准备销售分析报表页面
	 * @param formBean
	 */
	public void prepareSalesAnalysisReportUI(ChainReportActionFormBean formBean) {
		formBean.setChainStore(chainStoreDaoImpl.getAllChainStoreObject());
		
	}

	/**
	 * 获取按照销售人员排名的销售报表
	 * @param formBean
	 * @param page
	 * @param rows
	 * @param sort
	 * @param order
	 * @return
	 */
	public Response generateSalesReportBySaler(
			ChainReportActionFormBean formBean, int page, int rows,
			String sort, String order, ChainUserInfor loginUser) {
		Response response = new Response();
		
		int chainId = formBean.getChainStore().getChain_id();
		Date startDate = Common_util.formStartDate(formBean.getStartDate());
		Date endDate = Common_util.formEndDate(formBean.getEndDate());
		
		ChainReport report = new ChainReport();
		report.setChainStore(formBean.getChainStore());
		
		Map saleReport = new HashMap<String, Object>();
		
		try {
			generateSalesReportBySaler(saleReport, chainId, startDate, endDate, page, rows, sort, order, loginUser);
			response.setReturnCode(Response.SUCCESS);
		} catch (Exception e) {
			response.setReturnCode(Response.FAIL);
		}
		response.setReturnValue(saleReport);
		
		return response;
	}

	private void generateSalesReportBySaler(Map saleReport, int chainId,
			Date startDate, Date endDate, Integer page, Integer rowPerPage, String sort,
			String order, ChainUserInfor loginUser) {
		/**
		 * 1. 获取total
		 */
		
		ChainSalesReport totalReport = generateSalesTotal(chainId, Common_util.ALL_RECORD, startDate, endDate, true);
		if (!ChainUserInforService.isMgmtFromHQ(loginUser) && loginUser.getRoleType().getChainRoleTypeId() != ChainRoleType.CHAIN_OWNER){
			totalReport.setFreeCostSum(0);
			totalReport.setNetProfit(0);
			totalReport.setNetSaleCostSum(0);
		}
		
		/**
		 * 2. 实现分页,如果是搜索所有连锁店
		 */
		Object[] value_sale = new Object[]{startDate, endDate, ChainStoreSalesOrder.STATUS_COMPLETE};
		int total = 0;
		if (page != null && rowPerPage != null)
			total = calculateSaleReportBySalerCount(chainId, value_sale);
		
		/**
		 * 获取数据列表
		 */
		List<ChainSalesReport> reports = new ArrayList<ChainSalesReport>();
		String criteria = "";
		if (chainId == Common_util.ALL_RECORD)
			criteria = " FROM ChainStoreSalesOrder WHERE orderDate BETWEEN ? AND ? AND status = ?";
		else 
			criteria = "FROM ChainStoreSalesOrder WHERE orderDate BETWEEN ? AND ? AND status = ? AND chainStore.chain_id = " + chainId;

		criteria += " GROUP BY  saler.user_id ";
		
		String orderBy = generateSalesOrderBy(sort, order);
		
		criteria += orderBy;
		
		String hql_sale2 = "SELECT sum(totalQuantity), sum(netAmount), sum(totalQuantityR), " +
				"sum(netAmountR), sum(totalCost), sum(totalQuantityF) ,sum(totalCostF), sum(discountAmount), " +
				"sum(coupon), sum (cardAmount), sum(cashAmount - returnAmount), sum(vipScore), chainStore.chain_id, sum(totalAmount - netAmount),  saler.user_id, sum(qxQuantity), sum(qxAmount), sum(qxCost), sum(myQuantity), sum(myAmount), sum(myCost),sum(chainPrepaidAmt), sum(wechatAmount), sum(alipayAmount)   " + criteria;

		Integer[] pagerArray = null;
		if (page != null && rowPerPage != null)
		      pagerArray = new Integer[]{Common_util.getFirstRecord(page, rowPerPage), rowPerPage};
		
		List<Object> sales2 = (List<Object>)chainSalesOrderDaoImpl.executeHQLSelect(hql_sale2, value_sale,pagerArray, true);

		for (Object resultObject : sales2){
			Object[] sales3 = (Object[])resultObject;
			int totalQ2 = Common_util.getInt(sales3[0]);
			double netAmt2 = Common_util.getDouble(sales3[1]);
			int totalQR2 = Common_util.getInt(sales3[2]);
			double netAmtR2 = Common_util.getDouble(sales3[3]);
			double totalCost2 = Common_util.getDouble(sales3[4]);
			int totalQF2 = Common_util.getInt(sales3[5]);
			double totalCostF2 = Common_util.getDouble(sales3[6]);
			double discountAmt2 = Common_util.getDouble(sales3[7]);
			double coupon2 = Common_util.getDouble(sales3[8]);
			double cardAmt2 = Common_util.getDouble(sales3[9]);
			double cashAmt2 = Common_util.getDouble(sales3[10]);
			double vipScoreAmt2 = Common_util.getDouble(sales3[11]);
			int chainStoreId =  Common_util.getInt(sales3[12]);
			double totalSalesDiscountAmt2 = Common_util.getDouble(sales3[13]);
			int salerId = Common_util.getInt(sales3[14]);
			int qxQuantity = Common_util.getInt(sales3[15]);
			double qxAmount = Common_util.getDouble(sales3[16]);
			double qxCost = Common_util.getDouble(sales3[17]);
			int myQuantity = Common_util.getInt(sales3[18]);
			double myAmount = Common_util.getDouble(sales3[19]);
			double myCost = Common_util.getDouble(sales3[20]);	
			double vipPrepaidAmt = Common_util.getDouble(sales3[21]);
			double wechatAmt =  Common_util.getDouble(sales3[22]);
			double alipayAmt =  Common_util.getDouble(sales3[23]);

			ChainStore store = chainStoreDaoImpl.get(chainStoreId, true);
			ChainUserInfor user = chainUserInforDaoImpl.get(salerId, true);
			
			ChainSalesReport chainReport2 = new ChainSalesReport(ChainReport.TYPE_SALES_REPORT, totalQ2, totalQR2,
					totalQF2, netAmt2,totalSalesDiscountAmt2, netAmtR2,totalCost2, totalCostF2, discountAmt2,
					coupon2, cardAmt2, cashAmt2, vipScoreAmt2, qxQuantity,qxAmount, qxCost, myQuantity, myAmount, myCost,0 ,0,vipPrepaidAmt,0,0,0,0,0,store,user, wechatAmt, alipayAmt);
			
			if (!ChainUserInforService.isMgmtFromHQ(loginUser) && loginUser.getRoleType().getChainRoleTypeId() != ChainRoleType.CHAIN_OWNER){
				chainReport2.setFreeCostSum(0);
				chainReport2.setNetProfit(0);
				chainReport2.setNetSaleCostSum(0);
			}
			
			reports.add(chainReport2);
		}

		List<ChainSalesReport> footer = new ArrayList<ChainSalesReport>();
		footer.add(totalReport);
		saleReport.put("footer", footer);
		saleReport.put("rows", reports);
		saleReport.put("total", total);
		
	}

	private int calculateSaleReportBySalerCount(int chainId, Object[] value_sale) {
		//2.1 计算pager
		String criteria = "";
		if (chainId == Common_util.ALL_RECORD)
			criteria = " FROM ChainStoreSalesOrder WHERE orderDate BETWEEN ? AND ? AND status = ?";
		else 
			criteria = "FROM ChainStoreSalesOrder WHERE orderDate BETWEEN ? AND ? AND status = ? AND chainStore.chain_id = " + chainId;

		String criteria2 = "SELECT COUNT(DISTINCT saler.user_id) " + criteria;
		    
	    return chainSalesOrderDaoImpl.executeHQLCount(criteria2, value_sale, true);
	}

	/**
	 * 获取销售统计报表的信息
	 * @param parentId
	 * @param chain_id
	 * @param year_ID
	 * @param quarter_ID
	 * @param brand_ID
	 * @param userInfor
	 * @return
	 */
	public Response getSalesStatisticReptEles(int parentId,Date startDate, Date endDate, int chainId, int yearId, int quarterId, int brandId, int salerId,
			ChainUserInfor userInfor) {
		Response response = new Response();
		
		List<Object> value_sale = new ArrayList<Object>();
		value_sale.add(ChainStoreSalesOrder.STATUS_COMPLETE);
		value_sale.add(startDate);
		value_sale.add(endDate);

		
		boolean showCost = userInfor.containFunction("purchaseAction!seeCost");
		ChainStore chainStore = this.getThisChainStore(chainId);
		ChainUserInfor saler = this.getThisChainUserInfor(salerId);
		
		List<ChainSalesStatisticReportItemVO> reportItems = new ArrayList<ChainSalesStatisticReportItemVO>();
		String name = "";
		
		String whereClause = "";
		if (chainId != Common_util.ALL_RECORD){
			whereClause += " AND csp.chainSalesOrder.chainStore.chain_id = " + chainId;
		} else { 
			whereClause += " AND csp.chainSalesOrder.chainStore.chain_id <>  " +ChainStore.CHAIN_ID_TEST_ID;
		}
		
		if (salerId != Common_util.ALL_RECORD){
			whereClause += " AND csp.chainSalesOrder.saler.user_id = " + salerId;
		}	
	 
		
		if (parentId == 0){
			//@2. 根节点
			String criteria = "SELECT SUM(quantity), SUM(retailPrice * discountRate * quantity), SUM(costPrice * quantity), SUM(retailPrice * (1 - discountRate) * quantity), csp.type FROM ChainStoreSalesOrderProduct csp WHERE csp.chainSalesOrder.status = ? AND csp.chainSalesOrder.orderDate BETWEEN ? AND ? "+ whereClause + " GROUP BY csp.type";
			name = chainStore.getChain_name() + " " + saler.getName();
			
			List<Object> values = chainSalesOrderDaoImpl.executeHQLSelect(criteria, value_sale.toArray(), null, true);
			
			ChainSalesStatisticReportItemVO rootItem = new ChainSalesStatisticReportItemVO(name, 1, chainId, yearId, quarterId, brandId,0, showCost, ChainSalesStatisticReportItemVO.STATE_CLOSED);
			
			if (values != null){
				for (Object record : values ){
					Object[] records = (Object[])record;
					int quantity = Common_util.getInt(records[0]);
					double sales = Common_util.getDouble(records[1]);
					double cost = Common_util.getDouble(records[2]);
					double salesDiscount = Common_util.getDouble(records[3]);
					int type = Common_util.getInt(records[4]);
					
					rootItem.putValue(quantity, type, sales, cost, salesDiscount);
				}
				
				rootItem.reCalculate();
			}
			
			reportItems.add(rootItem);
		} else if (yearId == 0){
			//@2. 展开所有年份的库存信息
			String criteria = "SELECT SUM(quantity), SUM(retailPrice * discountRate * quantity), SUM(costPrice * quantity), SUM(retailPrice * (1 - discountRate) * quantity), csp.productBarcode.product.year.year_ID,  csp.type FROM ChainStoreSalesOrderProduct csp WHERE csp.chainSalesOrder.status = ? AND csp.chainSalesOrder.orderDate BETWEEN ? AND ?  "+ whereClause + " GROUP BY csp.productBarcode.product.year.year_ID, csp.type";
			List<Object> values = chainSalesOrderDaoImpl.executeHQLSelect(criteria, value_sale.toArray(), null, true);
			
			
			if (values != null){
				Map<Integer, ChainSalesStatisticReportItemVO> dataMap = new HashMap<Integer, ChainSalesStatisticReportItemVO>();
				for (Object record : values ){
					Object[] records = (Object[])record;
					int quantity = Common_util.getInt(records[0]);
					double sales = Common_util.getDouble(records[1]);
					double cost = Common_util.getDouble(records[2]);
					double salesDiscount = Common_util.getDouble(records[3]);
					int yearIdDB = Common_util.getInt(records[4]);
					int type = Common_util.getInt(records[5]);
					
					ChainSalesStatisticReportItemVO levelFour = dataMap.get(yearIdDB);
					if (levelFour != null){
						levelFour.putValue(quantity, type, sales, cost, salesDiscount);
					} else {
						Year year = yearDaoImpl.get(yearIdDB, true);
						
						name = year.getYear() + "年";
						
						levelFour = new ChainSalesStatisticReportItemVO(name, parentId, chainId, yearIdDB, quarterId, brandId,0, showCost, ChainSalesStatisticReportItemVO.STATE_CLOSED);

						levelFour.putValue(quantity, type, sales, cost, salesDiscount);
					}
					
					dataMap.put(yearIdDB, levelFour);
				}
				
				List<Integer> yearIds = new ArrayList<Integer>(dataMap.keySet());
				Collections.sort(yearIds);
				
				//1. 把基本对象放入
				for (Integer id : yearIds){
					ChainSalesStatisticReportItemVO levelFourItem = dataMap.get(id);
					levelFourItem.reCalculate();

					reportItems.add(levelFourItem);
				}	
			}
			
			
		} else if (quarterId == 0){
			//@2. 展开所有季的库存信息
			String criteria = "SELECT SUM(quantity), SUM(retailPrice * discountRate * quantity), SUM(costPrice * quantity), SUM(retailPrice * (1 - discountRate) * quantity), csp.productBarcode.product.quarter.quarter_ID,  csp.type FROM ChainStoreSalesOrderProduct csp WHERE csp.chainSalesOrder.status = ? AND csp.chainSalesOrder.orderDate BETWEEN ? AND ? AND csp.productBarcode.product.year.year_ID = " + yearId + whereClause + " GROUP BY csp.productBarcode.product.quarter.quarter_ID, csp.type";
			List<Object> values = chainSalesOrderDaoImpl.executeHQLSelect(criteria, value_sale.toArray(), null, true);
			
			
			if (values != null){
				Map<Integer, ChainSalesStatisticReportItemVO> dataMap = new HashMap<Integer, ChainSalesStatisticReportItemVO>();
				for (Object record : values ){
					Object[] records = (Object[])record;
					int quantity = Common_util.getInt(records[0]);
					double sales = Common_util.getDouble(records[1]);
					double cost = Common_util.getDouble(records[2]);
					double salesDiscount = Common_util.getDouble(records[3]);
					int quarterIdDB = Common_util.getInt(records[4]);
					int type = Common_util.getInt(records[5]);
					
					ChainSalesStatisticReportItemVO levelFour = dataMap.get(quarterIdDB);
					if (levelFour != null){
						levelFour.putValue(quantity, type, sales, cost, salesDiscount);
					} else {
						Year year = yearDaoImpl.get(yearId, true);
						Quarter quarter = quarterDaoImpl.get(quarterIdDB, true);
						name = year.getYear() + "年" + quarter.getQuarter_Name();
						
						levelFour = new ChainSalesStatisticReportItemVO(name, parentId, chainId, yearId, quarterIdDB, brandId,0, showCost, ChainSalesStatisticReportItemVO.STATE_CLOSED);

						levelFour.putValue(quantity, type, sales, cost, salesDiscount);
					}
					
					dataMap.put(quarterIdDB, levelFour);
				}
				
				List<Integer> quarterIds = new ArrayList<Integer>(dataMap.keySet());
				Collections.sort(quarterIds);
				
				//1. 把基本对象放入
				for (Integer id : quarterIds){
					ChainSalesStatisticReportItemVO levelFourItem = dataMap.get(id);
					levelFourItem.reCalculate();

					reportItems.add(levelFourItem);
				}	
			}
		} else if (brandId == 0){
			//@2. 展开所有品牌的库存信息
			String criteria = "SELECT SUM(quantity), SUM(retailPrice * discountRate * quantity), SUM(costPrice * quantity), SUM(retailPrice * (1 - discountRate) * quantity), csp.productBarcode.product.brand.brand_ID,  csp.type FROM ChainStoreSalesOrderProduct csp WHERE csp.chainSalesOrder.status = ? AND csp.chainSalesOrder.orderDate BETWEEN ? AND ? AND csp.productBarcode.product.year.year_ID = " + yearId + " AND csp.productBarcode.product.quarter.quarter_ID = " + quarterId + whereClause + " GROUP BY csp.productBarcode.product.brand.brand_ID, csp.type";
			List<Object> values = chainSalesOrderDaoImpl.executeHQLSelect(criteria, value_sale.toArray(), null, true);
			
			
			if (values != null){
				Map<Integer, ChainSalesStatisticReportItemVO> dataMap = new HashMap<Integer, ChainSalesStatisticReportItemVO>();
				for (Object record : values ){
					Object[] records = (Object[])record;
					int quantity = Common_util.getInt(records[0]);
					double sales = Common_util.getDouble(records[1]);
					double cost = Common_util.getDouble(records[2]);
					double salesDiscount = Common_util.getDouble(records[3]);
					int brandIdDB = Common_util.getInt(records[4]);
					int type = Common_util.getInt(records[5]);
					
					ChainSalesStatisticReportItemVO levelFour = dataMap.get(brandIdDB);
					if (levelFour != null){
						levelFour.putValue(quantity, type, sales, cost, salesDiscount);
					} else {
						Brand brand = brandDaoImpl.get(brandIdDB, true);
						boolean isChain = false;
						if (brand.getChainStore() != null && brand.getChainStore().getChain_id() !=0)
							isChain = true;
						String pinyin = brand.getPinyin();
						if (!StringUtils.isEmpty(pinyin)){
							name = pinyin.substring(0, 1) + " ";
						}
						
						 name += brand.getBrand_Name();
						
						levelFour = new ChainSalesStatisticReportItemVO(name, parentId, chainId, yearId, quarterId, brandIdDB,0, showCost, ChainSalesStatisticReportItemVO.STATE_CLOSED);
						levelFour.setIsChain(isChain);
						levelFour.putValue(quantity, type, sales, cost, salesDiscount);
					}
					
					dataMap.put(brandIdDB, levelFour);
				}
				
				List<Integer> brandIds = new ArrayList<Integer>(dataMap.keySet());
				Collections.sort(brandIds);
				
				//1. 把基本对象放入
				for (Integer id : brandIds){
					ChainSalesStatisticReportItemVO levelFourItem = dataMap.get(id);
					levelFourItem.reCalculate();

					reportItems.add(levelFourItem);
				}	
			}
		} else if (brandId != 0) {
			//@2. 展开所有品牌的库存信息
			String criteria = "SELECT SUM(quantity), SUM(retailPrice * discountRate * quantity), SUM(costPrice * quantity), SUM(retailPrice * (1 - discountRate) * quantity), csp.productBarcode.id,  csp.type FROM ChainStoreSalesOrderProduct csp WHERE csp.chainSalesOrder.status = ? AND csp.chainSalesOrder.orderDate BETWEEN ? AND ? AND csp.productBarcode.product.year.year_ID = " + yearId + " AND csp.productBarcode.product.quarter.quarter_ID = " + quarterId + " AND csp.productBarcode.product.brand.brand_ID = " + brandId + whereClause + " GROUP BY csp.productBarcode.id, csp.type";
			List<Object> values = chainSalesOrderDaoImpl.executeHQLSelect(criteria, value_sale.toArray(), null, true);
			
			
			if (values != null){
				Map<Integer, ChainSalesStatisticReportItemVO> dataMap = new HashMap<Integer, ChainSalesStatisticReportItemVO>();
				for (Object record : values ){
					Object[] records = (Object[])record;
					int quantity = Common_util.getInt(records[0]);
					double sales = Common_util.getDouble(records[1]);
					double cost = Common_util.getDouble(records[2]);
					double salesDiscount = Common_util.getDouble(records[3]);
					int pbId = Common_util.getInt(records[4]);
					int type = Common_util.getInt(records[5]);
					
					ChainSalesStatisticReportItemVO levelFour = dataMap.get(pbId);
					if (levelFour != null){
						levelFour.putValue(quantity, type, sales, cost, salesDiscount);
					} else {
						ProductBarcode pb = productBarcodeDaoImpl.get(pbId, true);
						String barcode = pb.getBarcode();
						Color color = pb.getColor();
						String colorName = "";
						if (color != null)
							colorName = color.getName();

						Category category = pb.getProduct().getCategory();
						name = Common_util.cutProductCode(pb.getProduct().getProductCode()) + colorName + " " +  category.getCategory_Name();
						
						boolean isChain = false;
						if (pb.getChainStore() != null && pb.getChainStore().getChain_id() !=0)
							isChain = true;

						levelFour = new ChainSalesStatisticReportItemVO(name, parentId, chainId, yearId, quarterId, brandId,pbId, showCost, ChainSalesStatisticReportItemVO.STATE_OPEN);
						levelFour.putValue(quantity, type, sales, cost, salesDiscount);
						levelFour.setIsChain(isChain);
						levelFour.setBarcode(barcode);
					}
					
					dataMap.put(pbId, levelFour);
				}
				
				List<Integer> pbIds = new ArrayList<Integer>(dataMap.keySet());
				Collections.sort(pbIds);
				
				//1. 把基本对象放入
				for (Integer id : pbIds){
					ChainSalesStatisticReportItemVO levelFourItem = dataMap.get(id);
					levelFourItem.reCalculate();

					reportItems.add(levelFourItem);
				}	
			}
		}
		Collections.sort(reportItems, new ChainStatisticReportItemVOSorter());
	    response.setReturnValue(reportItems);
	    return response;
	}
	
	/**
	 * 获取
	 * @param chainStore
	 * @param startDate
	 * @param endDate
	 * @param year
	 * @param quarter
	 * @param brand
	 * @param loginUserInfor
	 * @param string
	 * @return
	 */
	public Response generateChainPurchaseStatisticExcelReport(int parentId,
			int chainId, java.sql.Date startDate,
			java.sql.Date endDate, int yearId, int quarterId, int brandId,
			ChainUserInfor userInfor, String filePath) {
		
	    Response response = new Response();	

		
		List<Object> value_sale = new ArrayList<Object>();
		value_sale.add(InventoryOrder.STATUS_ACCOUNT_COMPLETE);
		value_sale.add(Common_util.formStartDate(startDate));
		value_sale.add(Common_util.formEndDate(endDate));

		
		boolean showCost = userInfor.containFunction("purchaseAction!seeCost");
		
		ChainStore chainStore = this.getThisChainStore(chainId);
		
		List<ChainPurchaseStatisReportItem> reportItems = new ArrayList<ChainPurchaseStatisReportItem>();
		String name = "";
		
		StringBuffer whereClause = new StringBuffer(" AND p.productBarcode.product.category.category_ID !=" + SystemParm.getQXCategory());
		if (chainId != Common_util.ALL_RECORD){
			whereClause.append(" AND p.order.client_id = " + chainStore.getClient_id());
		} else { 
			//ChainStore testChainStore = chainStoreDaoImpl.get(ChainStore.CHAIN_ID_TEST_ID, true);
			
			whereClause.append(" AND p.order.client_id <> " + ChainStore.CLIENT_ID_TEST_ID);
		}
		
		if (yearId != 0){
			whereClause.append(" AND p.productBarcode.product.year.year_ID = " + yearId);
		}
		
		if (quarterId != 0){
			whereClause.append(" AND p.productBarcode.product.quarter.quarter_ID = " + quarterId);
		}
		
		if (brandId != 0){
			whereClause.append(" AND p.productBarcode.product.brand.brand_ID = " + brandId);
		}
			
		//@2. 展开所有品牌的库存信息
		StringBuffer sql = new StringBuffer("SELECT SUM(quantity), SUM(wholeSalePrice * quantity), p.productBarcode.id, p.order.order_type FROM InventoryOrderProduct p " + 
		         " WHERE p.order.order_Status = ? AND p.order.order_EndTime BETWEEN ? AND ? ");

		sql.append(whereClause);
		sql.append(" GROUP BY p.productBarcode.id, p.order.order_type");
		
		List<Object> values = inventoryOrderProductDAOImpl.executeHQLSelect(sql.toString(), value_sale.toArray(), null, true);
		
		Map<Integer, ChainPurchaseStatisticReportItemVO> dataMap = new HashMap<Integer, ChainPurchaseStatisticReportItemVO>();
		for (Object record : values ){
			Object[] records = (Object[])record;
			int quantity = Common_util.getInt(records[0]);
			double amount = Common_util.getDouble(records[1]);
			int pbId = Common_util.getInt(records[2]);
			int type = Common_util.getInt(records[3]);
			
			ChainPurchaseStatisticReportItemVO levelOneItem = dataMap.get(pbId);
			if (levelOneItem != null){
				levelOneItem.putValue(type, quantity, amount);
			} else {
				ProductBarcode pb = productBarcodeDaoImpl.get(pbId, true);
				Color color = pb.getColor();
				String colorName = "";
				if (color != null)
					colorName = color.getName();
				
				Category category = pb.getProduct().getCategory();
				
				name = Common_util.cutProductCode(pb.getProduct().getProductCode()) + colorName  + " " +  category.getCategory_Name();
				
				levelOneItem = new ChainPurchaseStatisticReportItemVO(name, parentId,chainId, yearId, quarterId, brandId,pbId, showCost, ChainPurchaseStatisticReportItemVO.STATE_OPEN);
				levelOneItem.putValue(type, quantity, amount);
				
				dataMap.put(pbId, levelOneItem);
			}
		}
		
		List<Integer> pbKey = new ArrayList<Integer>(dataMap.keySet());
		ChainPurchaseStatisReportItem totalItem = new ChainPurchaseStatisReportItem();
		
		//1. 把基本对象放入
		//2. 计算总数
		for (Integer id : pbKey){
			ChainPurchaseStatisticReportItemVO levelOneItem = dataMap.get(id);
			levelOneItem.reCalculate();
			
            int pbId = levelOneItem.getPbId();
            ProductBarcode pb = productBarcodeDaoImpl.get(pbId, true);
            
			ChainPurchaseStatisReportItem reportItem = new ChainPurchaseStatisReportItem(levelOneItem, chainStore, startDate, endDate, pb);
			totalItem.add(reportItem);
			reportItems.add(reportItem);
		}

		
		/**
		 * @2. 准备excel报表
		 */

		try {
			chainStore = this.getThisChainStore(chainStore.getChain_id());

			ChainPurchaseStatisticsReportTemplate chainPurchaseStatisticsReportTemplate = new ChainPurchaseStatisticsReportTemplate(reportItems,totalItem, chainStore, filePath, showCost, startDate, endDate);
			HSSFWorkbook wb = chainPurchaseStatisticsReportTemplate.process();
			
			ByteArrayInputStream byteArrayInputStream = ExcelUtil.convertExcelToInputStream(wb);
			
			response.setReturnValue(byteArrayInputStream);
			response.setReturnCode(Response.SUCCESS);
		} catch (IOException e){
			response.setReturnCode(Response.FAIL);
		}
		return response;
	}

	/**
	 * 获取
	 * @param chainStore
	 * @param startDate
	 * @param endDate
	 * @param year
	 * @param quarter
	 * @param brand
	 * @param loginUserInfor
	 * @param string
	 * @return
	 */
	public Response generateChainSalesStatisticExcelReport(int parentId,
			int chainId, int salerId, java.sql.Date startDate,
			java.sql.Date endDate, int yearId, int quarterId, int brandId,
			ChainUserInfor loginUserInfor, String filePath) {
		
	    Response response = new Response();	
	
		List<Object> value_sale = new ArrayList<Object>();
		value_sale.add(ChainStoreSalesOrder.STATUS_COMPLETE);
		value_sale.add(startDate);
		value_sale.add(endDate);
		
		boolean showCost = loginUserInfor.containFunction("purchaseAction!seeCost");

		//1. part one is to calculate the summary
		String whereClause = "";
		if (chainId != Common_util.ALL_RECORD){
			whereClause += " AND csp.chainSalesOrder.chainStore.chain_id = " + chainId;
		} else { 
			whereClause += " AND csp.chainSalesOrder.chainStore.chain_id <>  " +ChainStore.CHAIN_ID_TEST_ID;
		}
		
		if (salerId != Common_util.ALL_RECORD){
			whereClause += " AND csp.chainSalesOrder.saler.user_id = " + salerId;
		}	
		
		String criteria = "SELECT SUM(quantity), SUM(retailPrice * discountRate * quantity), SUM(costPrice * quantity), SUM(retailPrice * (1 - discountRate) * quantity), csp.productBarcode.id,  csp.type FROM ChainStoreSalesOrderProduct csp WHERE csp.chainSalesOrder.status = ? AND csp.chainSalesOrder.orderDate BETWEEN ? AND ? ";
		
		if (yearId != 0){
			whereClause += " AND csp.productBarcode.product.year.year_ID = " + yearId;
		}
		
		if (quarterId != 0){
			whereClause += " AND csp.productBarcode.product.quarter.quarter_ID = " + quarterId;
		}
		
		if (brandId != 0){
			whereClause += " AND csp.productBarcode.product.brand.brand_ID = " + brandId;
		}
		
		criteria += whereClause ;
		String criteiraSummary = criteria + " GROUP BY csp.productBarcode.id, csp.type";
		
		List<Object> values = chainSalesOrderDaoImpl.executeHQLSelect(criteiraSummary, value_sale.toArray(), null, true);
		
		Map<String, ChainSalesStatisticReportItemVO> dataMap = new HashMap<String, ChainSalesStatisticReportItemVO>();
		
		if (values != null){
			for (Object record : values ){
				Object[] records = (Object[])record;
				int quantity = Common_util.getInt(records[0]);
				double sales = Common_util.getDouble(records[1]);
				double cost = Common_util.getDouble(records[2]);
				double salesDiscount = Common_util.getDouble(records[3]);
				int pbId = Common_util.getInt(records[4]);
				int type = Common_util.getInt(records[5]);
				
				ChainSalesStatisticReportItemVO levelFour = dataMap.get(String.valueOf(pbId));
				if (levelFour != null){
					levelFour.putValue(quantity, type, sales, cost, salesDiscount);
				} else {
				    String name = "";
					ProductBarcode pb = productBarcodeDaoImpl.get(pbId, true);
					
					boolean isChain = false;
					if (pb.getChainStore() != null && pb.getChainStore().getChain_id() !=0)
						isChain = true;

					levelFour = new ChainSalesStatisticReportItemVO(name, parentId, chainId, yearId, quarterId, brandId,pbId, showCost, ChainSalesStatisticReportItemVO.STATE_OPEN);
					levelFour.putValue(quantity, type, sales, cost, salesDiscount);
					levelFour.setIsChain(isChain);
				}
				
				dataMap.put(String.valueOf(pbId), levelFour);
			}
		}
			
		List<ChainSalesStatisReportItem> reportItems = new ArrayList<ChainSalesStatisReportItem>();
		ChainSalesStatisReportItem totalItem = new ChainSalesStatisReportItem();
		List<ChainSalesStatisticReportItemVO> reportItemVOs = new ArrayList<ChainSalesStatisticReportItemVO>(dataMap.values());
		
		ChainStore chainStore = this.getThisChainStore(chainId);
		ChainUserInfor saler = this.getThisChainUserInfor(salerId);
		
		for (ChainSalesStatisticReportItemVO vo : reportItemVOs){
            int pbId = vo.getPbId();
            ProductBarcode pb = productBarcodeDaoImpl.get(pbId, true);
            
            vo.reCalculate();
            
			ChainSalesStatisReportItem reportItem = new ChainSalesStatisReportItem(vo, chainStore, saler, startDate, endDate, pb);
			totalItem.add(reportItem);
			reportItems.add(reportItem);
		}
		
		
		/**
		 * @3. 准备excel报表
		 */

		try {
			chainStore = this.getThisChainStore(chainStore.getChain_id());
			if (saler.getUser_id() != Common_util.ALL_RECORD)
				saler = chainUserInforDaoImpl.get(saler.getUser_id(), true);
			ChainSalesStatisticsReportTemplate chainSalesStatisticsReportTemplate = new ChainSalesStatisticsReportTemplate(reportItems,totalItem, chainStore, filePath, showCost, saler, startDate, endDate);
			HSSFWorkbook wb = chainSalesStatisticsReportTemplate.process();
			
			//
			ByteArrayInputStream byteArrayInputStream = ExcelUtil.convertExcelToInputStream(wb);
			
			response.setReturnValue(byteArrayInputStream);
			response.setReturnCode(Response.SUCCESS);
		} catch (IOException e){
			response.setReturnCode(Response.FAIL);
		}
		return response;
	}
	
	/**
	 * 获取
	 * @param chainStore
	 * @param startDate
	 * @param endDate
	 * @param year
	 * @param quarter
	 * @param brand
	 * @param loginUserInfor
	 * @param string
	 * @return
	 */
	public Response generateChainSalesStatisticExcelReportDetail(int parentId,
			int chainId, int salerId, java.sql.Date startDate,
			java.sql.Date endDate, int yearId, int quarterId, int brandId,
			ChainUserInfor loginUserInfor, String filePath) {
		
	    Response response = new Response();	
	
		List<Object> value_sale = new ArrayList<Object>();
		value_sale.add(ChainStoreSalesOrder.STATUS_COMPLETE);
		value_sale.add(startDate);
		value_sale.add(endDate);
		
		boolean showCost = loginUserInfor.containFunction("purchaseAction!seeCost");

		//1. part one is to calculate the summary
		String whereClause = "";
		if (chainId != Common_util.ALL_RECORD){
			whereClause += " AND csp.chainSalesOrder.chainStore.chain_id = " + chainId;
		} else { 
			whereClause += " AND csp.chainSalesOrder.chainStore.chain_id <>  " +ChainStore.CHAIN_ID_TEST_ID;
		}
		
		if (salerId != Common_util.ALL_RECORD){
			whereClause += " AND csp.chainSalesOrder.saler.user_id = " + salerId;
		}	
		
		String criteria = "SELECT SUM(quantity), SUM(retailPrice * discountRate * quantity), SUM(costPrice * quantity), SUM(retailPrice * (1 - discountRate) * quantity), csp.productBarcode.id,  csp.type FROM ChainStoreSalesOrderProduct csp WHERE csp.chainSalesOrder.status = ? AND csp.chainSalesOrder.orderDate BETWEEN ? AND ? ";
		
		if (yearId != 0){
			whereClause += " AND csp.productBarcode.product.year.year_ID = " + yearId;
		}
		
		if (quarterId != 0){
			whereClause += " AND csp.productBarcode.product.quarter.quarter_ID = " + quarterId;
		}
		
		if (brandId != 0){
			whereClause += " AND csp.productBarcode.product.brand.brand_ID = " + brandId;
		}


		Map<String, ChainSalesStatisticReportItemVO> dataMap = new HashMap<String, ChainSalesStatisticReportItemVO>();


		List<ChainSalesStatisReportItem> reportItemsDetail = new ArrayList<ChainSalesStatisReportItem>();
		ChainSalesStatisReportItem totalItem = new ChainSalesStatisReportItem();

		ChainStore chainStore = this.getThisChainStore(chainId);
		ChainUserInfor saler = this.getThisChainUserInfor(salerId);

		
		//2. part two is to calculate the detail elements
		criteria = "SELECT SUM(quantity), SUM(retailPrice * discountRate * quantity), SUM(costPrice * quantity), SUM(retailPrice * (1 - discountRate) * quantity), csp.productBarcode.id,  csp.type, csp.chainSalesOrder.chainStore.chain_id, csp.chainSalesOrder.orderDate FROM ChainStoreSalesOrderProduct csp WHERE csp.chainSalesOrder.status = ? AND csp.chainSalesOrder.orderDate BETWEEN ? AND ? ";
		criteria += whereClause ;
		String criteriaDetail = criteria + " GROUP BY csp.chainSalesOrder.chainStore.chain_id, csp.chainSalesOrder.orderDate, csp.productBarcode.id, csp.type";
		
		List<Object> values = chainSalesOrderDaoImpl.executeHQLSelect(criteriaDetail, value_sale.toArray(), null, true);

		dataMap = new HashMap<String, ChainSalesStatisticReportItemVO>();
		if (values != null){
			for (Object record : values ){
				Object[] records = (Object[])record;
				int quantity = Common_util.getInt(records[0]);
				double sales = Common_util.getDouble(records[1]);
				double cost = Common_util.getDouble(records[2]);
				double salesDiscount = Common_util.getDouble(records[3]);
				int pbId = Common_util.getInt(records[4]);
				int type = Common_util.getInt(records[5]);
				int chainIdDB = Common_util.getInt(records[6]);
				Date date = Common_util.getDate(records[7]);
				
				String key = formatKey(chainIdDB, date, pbId);
				
				//System.out.println(key);
				
				ChainSalesStatisticReportItemVO levelFour = dataMap.get(key);
				if (levelFour != null){
					levelFour.putValue(quantity, type, sales, cost, salesDiscount);
				} else {
				    String name = "";
					ProductBarcode pb = productBarcodeDaoImpl.get(pbId, true);
					
					boolean isChain = false;
					if (pb.getChainStore() != null && pb.getChainStore().getChain_id() !=0)
						isChain = true;

					levelFour = new ChainSalesStatisticReportItemVO(name, parentId, chainIdDB, yearId, quarterId, brandId,pbId, showCost, ChainSalesStatisticReportItemVO.STATE_OPEN);

					levelFour.setDate(Common_util.dateFormat.format(date));
					
					levelFour.putValue(quantity, type, sales, cost, salesDiscount);
					levelFour.setIsChain(isChain);
				}
				
				dataMap.put(key, levelFour);
			}
		}
			
		List<ChainSalesStatisticReportItemVO> reportItemVOs2 = new ArrayList<ChainSalesStatisticReportItemVO>(dataMap.values());

		
		for (ChainSalesStatisticReportItemVO vo : reportItemVOs2){
            int pbId = vo.getPbId();
            ProductBarcode pb = productBarcodeDaoImpl.get(pbId, true);
            
            vo.reCalculate();
            
            ChainStore store = chainStoreService.getChainStoreByID(vo.getChainId());
            
			ChainSalesStatisReportItem reportItem = new ChainSalesStatisReportItem(vo, store, saler, startDate, endDate, pb);
			//totalItem.add(reportItem);
			reportItemsDetail.add(reportItem);
		}
		
		/**
		 * @3. 准备excel报表
		 */

		try {
			chainStore = this.getThisChainStore(chainStore.getChain_id());
			if (saler.getUser_id() != Common_util.ALL_RECORD)
				saler = chainUserInforDaoImpl.get(saler.getUser_id(), true);
			ChainSalesStatisticsReportDetailTemplate chainSalesStatisticsReportTemplate = new ChainSalesStatisticsReportDetailTemplate(reportItemsDetail,totalItem, chainStore, filePath, showCost, saler, startDate, endDate);
			HSSFWorkbook wb = chainSalesStatisticsReportTemplate.process();
			
			//
			ByteArrayInputStream byteArrayInputStream = ExcelUtil.convertExcelToInputStream(wb);
			
			response.setReturnValue(byteArrayInputStream);
			response.setReturnCode(Response.SUCCESS);
		} catch (IOException e){
			response.setReturnCode(Response.FAIL);
		}
		return response;
	}
	
	private String formatKey(int chainId, Date date, Integer pbId){
		return chainId+"@"+date.toString()+"@"+pbId;
	}

	class ChainSalesStatisticComparator implements Comparator<ChainSalesStatisReportItem> {

		@Override
		public int compare(ChainSalesStatisReportItem o1,
				ChainSalesStatisReportItem o2) {
			Product p1 = o1.getProductBarcode().getProduct();
			Product p2 = o2.getProductBarcode().getProduct();
			
			if (p1.getYear().getYear_ID() == p2.getYear().getYear_ID()){
				if (p1.getQuarter().getQuarter_ID() == p2.getQuarter().getQuarter_ID()){
					if (p1.getBrand().getBrand_ID() == p2.getBrand().getBrand_ID()){
						return p1.getProductCode().compareTo(p2.getProductCode());
					} else 
						return p1.getBrand().getBrand_ID() - p2.getBrand().getBrand_ID();
				} else 
					return p1.getQuarter().getQuarter_ID() - p2.getQuarter().getQuarter_ID();
			} else
			    return p1.getYear().getYear_ID() - p2.getYear().getYear_ID();
		}
	}
	public void prepareVIPConsumpReportUI(ChainReportActionFormBean formBean,
			ChainReportActionUIBean uiBean, ChainUserInfor userInfor) {
		if (!ChainUserInforService.isMgmtFromHQ(userInfor)){
			int chainId = userInfor.getMyChainStore().getChain_id();
			ChainStore chainStore = chainStoreService.getChainStoreByID(chainId);
			formBean.setChainStore(chainStore);
		} else {
			ChainStore allChainStore = ChainStoreDaoImpl.getAllChainStoreObject();
			formBean.setChainStore(allChainStore);
		}
		
	}

	@Transactional
	public Response generateVIPConsumpReport(
			ChainReportActionFormBean formBean, Integer page, Integer rowPerPage,
			String sort, String order) {
		Response response = new Response();
		Date startDate = formBean.getStartDate();
		Date endDate = formBean.getEndDate();
		int chainId = formBean.getChainStore().getChain_id();
		Map saleReport = new HashMap();
		
		/**
		 * 1. 获取total
		 */
		Object[] value_sale = new Object[]{startDate, endDate, ChainStoreSalesOrder.STATUS_COMPLETE};

		String chainCriteria = "";
		if (chainId == Common_util.ALL_RECORD)
			chainCriteria = " chainStore.chain_id <> " + ChainStore.CHAIN_ID_TEST_ID;
		else 
			chainCriteria = " chainStore.chain_id = " + chainId;

		String hql_sale = "select sum(totalQuantity), sum(netAmount),  sum(totalQuantityR), " +
				"sum(netAmountR), sum(totalQuantityF), sum(discountAmount), " +
				"sum(coupon), sum (cardAmount + cashAmount + wechatAmount + alipayAmount - returnAmount), sum(chainPrepaidAmt), count(id) from ChainStoreSalesOrder where vipCard IS NOT NULL and  orderDate between ? and ? and status = ? and " + chainCriteria;

		Object[] sales = (Object[])chainSalesOrderDaoImpl.executeHQLSelect(hql_sale, value_sale,null, true).get(0);

		int saleQ = Common_util.getInt(sales[0]);
		double saleAmt = Common_util.getDouble(sales[1]);
		int returnQ = Common_util.getInt(sales[2]);
		double returnAmt = Common_util.getDouble(sales[3]);
		int freeQ = Common_util.getInt(sales[4]);
		double discountAmt = Common_util.getDouble(sales[5]);
		double coupon = Common_util.getDouble(sales[6]);
		double receiveAmt = Common_util.getDouble(sales[7]);
		double prepaidAmt = Common_util.getDouble(sales[8]);
		int countSum  = Common_util.getInt(sales[9]);
				
		VIPReportVO totalVO = new VIPReportVO(saleQ, returnQ, freeQ, saleAmt, returnAmt, coupon, receiveAmt, discountAmt, null,prepaidAmt, countSum);
		
		//添加一个dummy的chainStore
		ChainVIPCard vip = new ChainVIPCard();
		vip.setIssueChainStore(new ChainStore());
		totalVO.setVip(vip);
		
		
		/**
		 * 2. 实现分页,如果是搜索所有连锁店
		 */
		int total = 0;
		if (page != null && rowPerPage != null)
			total = calculateVIPConsumpReportTotalRecord(chainId, value_sale);
		
		/**
		 * 获取数据列表
		 */
		List<VIPReportVO> reports = new ArrayList<VIPReportVO>();
		String criteria = "";
		if (chainId == Common_util.ALL_RECORD)
			criteria = " FROM ChainStoreSalesOrder WHERE vipCard IS NOT NULL and  orderDate BETWEEN ? AND ? AND status = ?";
		else 
			criteria = "FROM ChainStoreSalesOrder WHERE vipCard IS NOT NULL and  orderDate BETWEEN ? AND ? AND status = ? AND chainStore.chain_id = " + chainId;

		criteria += " GROUP BY  vipCard.id ";
	
		String orderBy = generateVIPConsumpOrderBy();
		criteria += orderBy;
		
		String hql_sale2 = "select sum(totalQuantity), sum(netAmount),  sum(totalQuantityR), " +
				"sum(netAmountR), sum(totalQuantityF), sum(discountAmount), " +
				"sum(coupon), sum (cardAmount + cashAmount + wechatAmount + alipayAmount - returnAmount), sum(chainPrepaidAmt), vipCard.id, count(id) " + criteria;

		Integer[] pagerArray = null;
		if (page != null && rowPerPage != null)
		      pagerArray = new Integer[]{Common_util.getFirstRecord(page, rowPerPage), rowPerPage};
		
		List<Object> sales2 = (List<Object>)chainSalesOrderDaoImpl.executeHQLSelect(hql_sale2, value_sale,pagerArray, true);

		for (Object resultObject : sales2){
			Object[] sales3 = (Object[])resultObject;
			int saleQ2 = Common_util.getInt(sales3[0]);
			double saleAmt2 = Common_util.getDouble(sales3[1]);
			int returnQ2 = Common_util.getInt(sales3[2]);
			double returnAmt2 = Common_util.getDouble(sales3[3]);
			int freeQ2 = Common_util.getInt(sales3[4]);
			double discountAmt2 = Common_util.getDouble(sales3[5]);
			double coupon2 = Common_util.getDouble(sales3[6]);
			double receiveAmt2 = Common_util.getDouble(sales3[7]);
			double prepaidAmt2 = Common_util.getDouble(sales3[8]);
			int vipId = Common_util.getInt(sales3[9]);
			int countSum2  = Common_util.getInt(sales3[10]);
					
			ChainVIPCard vipCard = chainVIPCardImpl.get(vipId, true);

			VIPReportVO rptVo = new VIPReportVO(saleQ2, returnQ2, freeQ2, saleAmt2, returnAmt2, coupon2, receiveAmt2, discountAmt2, vipCard, prepaidAmt2, countSum2);
			
			reports.add(rptVo);
		}

		List<VIPReportVO> footer = new ArrayList<VIPReportVO>();
		footer.add(totalVO);
		saleReport.put("footer", footer);
		saleReport.put("rows", reports);
		saleReport.put("total", total);
		
		response.setReturnValue(saleReport);
		return response;
	}

	private String generateVIPConsumpOrderBy() {
		String orderBy = " ORDER BY sum(netAmount - netAmountR) desc";

		return orderBy;
	}

	private int calculateVIPConsumpReportTotalRecord(int chainId,
			Object[] value_sale) {
		//2.1 计算pager
		String criteria = "";
		if (chainId == Common_util.ALL_RECORD)
			criteria = " FROM ChainStoreSalesOrder WHERE vipCard IS NOT NULL and  orderDate BETWEEN ? AND ? AND status = ?";
		else 
			criteria = "FROM ChainStoreSalesOrder WHERE vipCard IS NOT NULL and  orderDate BETWEEN ? AND ? AND status = ? AND chainStore.chain_id = " + chainId;

		String criteria2 = "SELECT COUNT(DISTINCT vipCard.id) " + criteria;
		    
	    return chainSalesOrderDaoImpl.executeHQLCount(criteria2, value_sale, true);
	}
	
	/**
	 * 下载vip消费报表
	 * @param formBean
	 * @return
	 */
	public Response downloadVIPConsumpReport(ChainReportActionFormBean formBean,String excelPath) {
		Response response = new Response();
		Date startDate = formBean.getStartDate();
		Date endDate = formBean.getEndDate();
		int chainId = formBean.getChainStore().getChain_id();
		
		List<VIPReportVO> reports = new ArrayList<VIPReportVO>();
		String criteria = "";
		if (chainId == Common_util.ALL_RECORD)
			criteria = " FROM ChainStoreSalesOrder WHERE vipCard IS NOT NULL and  orderDate BETWEEN ? AND ? AND status = ?";
		else 
			criteria = "FROM ChainStoreSalesOrder WHERE vipCard IS NOT NULL and  orderDate BETWEEN ? AND ? AND status = ? AND chainStore.chain_id = " + chainId;

		criteria += " GROUP BY  vipCard.id ";
	
		String orderBy = generateVIPConsumpOrderBy();
		criteria += orderBy;
		
		String hql_sale2 = "select sum(totalQuantity), sum(netAmount),  sum(totalQuantityR), " +
				"sum(netAmountR), sum(totalQuantityF), sum(discountAmount), " +
				"sum(coupon), sum (cardAmount + cashAmount + wechatAmount + alipayAmount - returnAmount), sum(chainPrepaidAmt), vipCard.id, count(id) " + criteria;

		Object[] value_sale = new Object[]{startDate, endDate, ChainStoreSalesOrder.STATUS_COMPLETE};
		List<Object> sales2 = (List<Object>)chainSalesOrderDaoImpl.executeHQLSelect(hql_sale2, value_sale,null, true);

		for (Object resultObject : sales2){
			Object[] sales3 = (Object[])resultObject;
			int saleQ2 = Common_util.getInt(sales3[0]);
			double saleAmt2 = Common_util.getDouble(sales3[1]);
			int returnQ2 = Common_util.getInt(sales3[2]);
			double returnAmt2 = Common_util.getDouble(sales3[3]);
			int freeQ2 = Common_util.getInt(sales3[4]);
			double discountAmt2 = Common_util.getDouble(sales3[5]);
			double coupon2 = Common_util.getDouble(sales3[6]);
			double receiveAmt2 = Common_util.getDouble(sales3[7]);
			double prepaidAmt2 = Common_util.getDouble(sales3[8]);
			int vipId = Common_util.getInt(sales3[9]);
			int countSum2  = Common_util.getInt(sales3[10]);
			
			ChainVIPCard vipCard = chainVIPCardImpl.get(vipId, true);

			VIPReportVO rptVo = new VIPReportVO(saleQ2, returnQ2, freeQ2, saleAmt2, returnAmt2, coupon2, receiveAmt2, discountAmt2, vipCard, prepaidAmt2,countSum2);
			
			reports.add(rptVo);
		}
		
		//2. 准备excel 报表
		try {
		    ChainVIPConsumptionRptTemplate rptTemplate = new ChainVIPConsumptionRptTemplate(reports, excelPath, startDate, endDate);
			HSSFWorkbook wb = rptTemplate.process();
			
			ByteArrayInputStream byteArrayInputStream = ExcelUtil.convertExcelToInputStream(wb);
			
			response.setReturnValue(byteArrayInputStream);
			response.setReturnCode(Response.SUCCESS);
		} catch (IOException e){
			e.printStackTrace();
			response.setFail(e.getMessage());
		}
		return response;
	}

	/**
	 * 准备 report repository 数据
	 * @param formBean
	 * @param uiBean
	 */
	public void prepareChainRptRepositoryUI(ChainReportActionFormBean formBean,
			ChainReportActionUIBean uiBean) {
		
		Map<Integer, List<ChainBatchRptRepositoty>> dataMap = chainAutoRptRepositoryDaoImpl.getRptRepositoryDateMap();
		Iterator<Integer> ids = dataMap.keySet().iterator();
		
		while (ids.hasNext()){
			int id = ids.next();
			switch (id) {
				case ChainBatchRptRepositoty.TYPE_WEEKLY_PRODUCT_ANALYSIS_RPT:
				    uiBean.setCurrentSalesDates(dataMap.get(id));
					break;
				case ChainBatchRptRepositoty.TYPE_ACCU_SALES_AWEEKLY_NALYSIS_RPT:
				    uiBean.setAccumulatedSalesDates(dataMap.get(id));
					break;
				case ChainBatchRptRepositoty.TYPE_CHAIN_TRANSFER_ACCT_FLOW_RPT:
				    uiBean.setChainTransferAcctFlowDates(dataMap.get(id));
					break;					
				default:
					break;
			}
		}
	}

	/**
	 * 下载batch report
	 * @param rptRepository
	 * @param userInfor
	 * @return
	 */
	public Response loadBatchRptRepository(
			ChainBatchRptRepositoty rptRepository, ChainUserInfor userInfor) {
		Response response = new Response();
		Map<String, Object> result = new HashMap<String, Object>();
		
		int rptId = rptRepository.getId();
		
		rptRepository = chainAutoRptRepositoryDaoImpl.get(rptId, true);
		if (rptRepository == null){
			response.setFail("无法找到报表");
		} else {
			int typeId = rptRepository.getRptId();
			if (typeId == ChainBatchRptRepositoty.TYPE_ACCU_SALES_AWEEKLY_NALYSIS_RPT || typeId == ChainBatchRptRepositoty.TYPE_WEEKLY_PRODUCT_ANALYSIS_RPT)
				result.put("Type", "zip");
			else 
				result.put("Type", "excel");
			
			File file = new File(rptRepository.getRptPathByType() + rptRepository.getDownloadRptName());
			InputStream inputStream = null;
			try {
				inputStream = new FileInputStream(file);
				result.put("download", inputStream);
				result.put("name", rptRepository.getDownloadRptName());
				response.setReturnValue(result);
			} catch (FileNotFoundException e) {
				response.setFail("无法找到报表");
			}
		}

		return response;
	}

	/**
	 * 获取vip销售占比分析报表
	 * @param rptDate
	 * @return
	 */
	public Response getVIPSalesAnalysisRpt(java.sql.Date rptDate) {
		Response response = new Response();
		
		String filePath = ChainSalesReportVIPPercentageTemplate.getFilePath(rptDate);
		
		File vipRpt = new File(filePath);
		if (!vipRpt.exists())
			response.setFail("无法找到当前VIP分析报表");
		else {
			response.setReturnValue(vipRpt);
		}
		return response;
	}

	/**
	 * 根据今年日期获取去年的销售数据
	 * @param formBean
	 * @return
	 */
	public Response generateLastYearSalesReport(
			ChainReportActionFormBean formBean) {
		Response response = new Response();
		
		int chainId = formBean.getChainStore().getChain_id();
		Date startDate = Common_util.formStartDate(Common_util.getDateOfLastYear(formBean.getStartDate()));
		Date endDate = Common_util.formEndDate(Common_util.getDateOfLastYear(formBean.getEndDate()));
		int salerId = Common_util.ALL_RECORD;
		
		Object[] value_sale = new Object[]{startDate, endDate, ChainStoreSalesOrder.STATUS_COMPLETE};
		
		ChainReport report = new ChainReport();
		report.setChainStore(formBean.getChainStore());
		
		Map qxDataMap = new HashMap<Integer, Double>();

		try {
			String criteria = " FROM ChainStoreSalesOrder WHERE orderDate BETWEEN ? AND ? AND status = ? GROUP BY chainStore.chain_id ";
			
			String hql_sale2 = "SELECT chainStore.chain_id, sum(qxQuantity), sum(qxAmount) " + criteria;

			List<Object> sales2 = (List<Object>)chainSalesOrderDaoImpl.executeHQLSelect(hql_sale2, value_sale,null, true);

			double totalAmt = 0;
			for (Object resultObject : sales2){
				Object[] sales3 = (Object[])resultObject;
				int chainStoreId =  Common_util.getInt(sales3[0]);
			
				int qxQuantity = Common_util.getInt(sales3[1]);
				double qxAmount = Common_util.getDouble(sales3[2]);
				totalAmt += qxAmount;
				
				qxDataMap.put(chainStoreId, qxAmount);
			}
			
			qxDataMap.put(Common_util.ALL_RECORD, totalAmt);
		} catch (Exception e) {
			e.printStackTrace();
			response.setReturnCode(Response.FAIL);
		}
		response.setReturnValue(qxDataMap);
		
		return response;
	}

	/**
	 * 获取采购统计信息的方法
	 * @param parentId
	 * @param startDate
	 * @param endDate
	 * @param chain_id
	 * @param year_ID
	 * @param quarter_ID
	 * @param brand_ID
	 * @param userInfor
	 * @return
	 */
	public Response getPurchaseStatisticReptEles(int parentId, java.sql.Date startDate, java.sql.Date endDate,
			int chainId, int yearId, int quarterId, int brandId, ChainUserInfor userInfor) {
		Response response = new Response();
		
		List<Object> value_sale = new ArrayList<Object>();
		value_sale.add(InventoryOrder.STATUS_ACCOUNT_COMPLETE);
		value_sale.add(Common_util.formStartDate(startDate));
		value_sale.add(Common_util.formEndDate(endDate));

		
		boolean showCost = userInfor.containFunction("purchaseAction!seeCost");
		
		ChainStore chainStore = this.getThisChainStore(chainId);
		
		List<ChainPurchaseStatisticReportItemVO> reportItems = new ArrayList<ChainPurchaseStatisticReportItemVO>();
		String name = "";
		
		String whereClause = " AND p.productBarcode.product.category.category_ID!=" + SystemParm.getQXCategory();
		if (chainId != Common_util.ALL_RECORD){
			whereClause += " AND p.order.client_id = " + chainStore.getClient_id();
		} else { 
			//ChainStore testChainStore = chainStoreDaoImpl.get(ChainStore.CHAIN_ID_TEST_ID, true);
			
			whereClause += " AND p.order.client_id != " + ChainStore.CLIENT_ID_TEST_ID;
		}

		if (parentId == 0){
			//@2. 根节点
			StringBuffer sql = new StringBuffer("SELECT SUM(quantity), SUM(wholeSalePrice * quantity), p.order.order_type   FROM InventoryOrderProduct p " + 
			         " WHERE p.order.order_Status = ? AND p.order.order_EndTime BETWEEN ? AND ?");

			sql.append(whereClause);
			sql.append(" GROUP BY p.order.order_type");
		
			List<Object> values = inventoryOrderProductDAOImpl.executeHQLSelect(sql.toString(), value_sale.toArray(), null, true);

			name = chainStore.getChain_name();
			ChainPurchaseStatisticReportItemVO rootItem = new ChainPurchaseStatisticReportItemVO(name, 1, chainId, yearId, quarterId, brandId,0, showCost, ChainPurchaseStatisticReportItemVO.STATE_CLOSED);
			
			if (values != null){
				for (Object record : values ){
					Object[] records = (Object[])record;
					int quantity = Common_util.getInt(records[0]);
					double amount = Common_util.getDouble(records[1]);
					int type = Common_util.getInt(records[2]);
					
					rootItem.putValue(type, quantity, amount);
				}
				
				rootItem.reCalculate();
			}
			
			reportItems.add(rootItem);
		} else if (yearId == 0){
			//@2. 展开所有年份的库存信息
			StringBuffer sql = new StringBuffer("SELECT SUM(quantity), SUM(wholeSalePrice * quantity), p.productBarcode.product.year.year_ID, p.order.order_type   FROM InventoryOrderProduct p " + 
			         " WHERE p.order.order_Status = ? AND p.order.order_EndTime BETWEEN ? AND ?");

			sql.append(whereClause);
			sql.append(" GROUP BY p.productBarcode.product.year.year_ID, p.order.order_type");
			
			List<Object> values = chainSalesOrderDaoImpl.executeHQLSelect(sql.toString(), value_sale.toArray(), null, true);
			
			Map<Integer, ChainPurchaseStatisticReportItemVO> dataMap = new HashMap<Integer, ChainPurchaseStatisticReportItemVO>();
			for (Object record : values ){
				Object[] records = (Object[])record;
				int quantity = Common_util.getInt(records[0]);
				double amount = Common_util.getDouble(records[1]);
				int yearIdDB = Common_util.getInt(records[2]);
				int type = Common_util.getInt(records[3]);
				
				ChainPurchaseStatisticReportItemVO levelOneItem = dataMap.get(yearIdDB);
				if (levelOneItem != null){
					levelOneItem.putValue(type, quantity, amount);
				} else {
					Year year = yearDaoImpl.get(yearIdDB, true);
					name = year.getYear() + "年";
					
					levelOneItem = new ChainPurchaseStatisticReportItemVO(name, parentId, chainId, yearIdDB, quarterId, brandId,0, showCost, ChainPurchaseStatisticReportItemVO.STATE_CLOSED);
					levelOneItem.putValue(type, quantity, amount);
					
					dataMap.put(yearIdDB, levelOneItem);
				}
			}
			
			List<Integer> yearKey = new ArrayList<Integer>(dataMap.keySet());
			Collections.sort(yearKey);
			
			//1. 把基本对象放入
			//2. 计算总数
			for (Integer id : yearKey){
				ChainPurchaseStatisticReportItemVO levelOneItem = dataMap.get(id);
				levelOneItem.reCalculate();

				reportItems.add(levelOneItem);
			}		
			
		} else if (quarterId == 0){
			//@2. 展开所有季的库存信息
			StringBuffer sql = new StringBuffer("SELECT SUM(quantity), SUM(wholeSalePrice * quantity), p.productBarcode.product.quarter.quarter_ID, p.order.order_type   FROM InventoryOrderProduct p " + 
			         " WHERE p.order.order_Status = ? AND p.order.order_EndTime BETWEEN ? AND ? AND p.productBarcode.product.year.year_ID =" + yearId);

			sql.append(whereClause);
			sql.append(" GROUP BY p.productBarcode.product.quarter.quarter_ID, p.order.order_type");
			
			List<Object> values = chainSalesOrderDaoImpl.executeHQLSelect(sql.toString(), value_sale.toArray(), null, true);
			
			Map<Integer, ChainPurchaseStatisticReportItemVO> dataMap = new HashMap<Integer, ChainPurchaseStatisticReportItemVO>();
			for (Object record : values ){
				Object[] records = (Object[])record;
				int quantity = Common_util.getInt(records[0]);
				double amount = Common_util.getDouble(records[1]);
				int quarterIdDB = Common_util.getInt(records[2]);
				int type = Common_util.getInt(records[3]);
				
				ChainPurchaseStatisticReportItemVO levelOneItem = dataMap.get(quarterIdDB);
				if (levelOneItem != null){
					levelOneItem.putValue(type, quantity, amount);
				} else {
					Year year = yearDaoImpl.get(yearId, true);
					Quarter quarter = quarterDaoImpl.get(quarterIdDB, true);
					name = year.getYear() + "年 " + quarter.getQuarter_Name();
					
					levelOneItem = new ChainPurchaseStatisticReportItemVO(name, parentId, chainId, yearId, quarterIdDB, brandId,0, showCost, ChainPurchaseStatisticReportItemVO.STATE_CLOSED);
					levelOneItem.putValue(type, quantity, amount);
					
					dataMap.put(quarterIdDB, levelOneItem);
				}
			}
			
			List<Integer> quarterKey = new ArrayList<Integer>(dataMap.keySet());
			Collections.sort(quarterKey);
			
			//1. 把基本对象放入
			//2. 计算总数
			for (Integer id : quarterKey){
				ChainPurchaseStatisticReportItemVO levelOneItem = dataMap.get(id);
				levelOneItem.reCalculate();

				reportItems.add(levelOneItem);
			}		
		} else if (brandId == 0){
			//@2. 展开所有品牌的库存信息
			StringBuffer sql = new StringBuffer("SELECT SUM(quantity), SUM(wholeSalePrice * quantity), p.productBarcode.product.brand.brand_ID, p.order.order_type FROM InventoryOrderProduct p " + 
			         " WHERE p.order.order_Status = ? AND p.order.order_EndTime BETWEEN ? AND ? AND p.productBarcode.product.year.year_ID =" + yearId + " AND p.productBarcode.product.quarter.quarter_ID =" + quarterId);

			sql.append(whereClause);
			sql.append(" GROUP BY p.productBarcode.product.brand.brand_ID, p.order.order_type");
			
			List<Object> values = chainSalesOrderDaoImpl.executeHQLSelect(sql.toString(), value_sale.toArray(), null, true);
			
			Map<Integer, ChainPurchaseStatisticReportItemVO> dataMap = new HashMap<Integer, ChainPurchaseStatisticReportItemVO>();
			for (Object record : values ){
				Object[] records = (Object[])record;
				int quantity = Common_util.getInt(records[0]);
				double amount = Common_util.getDouble(records[1]);
				int brandIdDB = Common_util.getInt(records[2]);
				int type = Common_util.getInt(records[3]);
				
				ChainPurchaseStatisticReportItemVO levelOneItem = dataMap.get(brandIdDB);
				if (levelOneItem != null){
					levelOneItem.putValue(type, quantity, amount);
				} else {
					Brand brand = brandDaoImpl.get(brandIdDB, true);
					String pinyin = brand.getPinyin();
					if (!StringUtils.isEmpty(pinyin)){
						name = pinyin.substring(0, 1) + " ";
					}
					
					 name += brand.getBrand_Name();
					
					levelOneItem = new ChainPurchaseStatisticReportItemVO(name, parentId,chainId, yearId, quarterId, brandIdDB,0, showCost, ChainPurchaseStatisticReportItemVO.STATE_CLOSED);
					levelOneItem.putValue(type, quantity, amount);
					
					dataMap.put(brandIdDB, levelOneItem);
				}
			}
			
			List<Integer> brandKey = new ArrayList<Integer>(dataMap.keySet());
			
			//1. 把基本对象放入
			//2. 计算总数
			for (Integer id : brandKey){
				ChainPurchaseStatisticReportItemVO levelOneItem = dataMap.get(id);
				levelOneItem.reCalculate();

				reportItems.add(levelOneItem);
			}		
		} else if (brandId != 0) {
			//@2. 展开所有品牌的库存信息
			StringBuffer sql = new StringBuffer("SELECT SUM(quantity), SUM(wholeSalePrice * quantity), p.productBarcode.id, p.order.order_type FROM InventoryOrderProduct p " + 
			         " WHERE p.order.order_Status = ? AND p.order.order_EndTime BETWEEN ? AND ? AND p.productBarcode.product.year.year_ID =" + yearId + " AND p.productBarcode.product.quarter.quarter_ID =" + quarterId+ " AND p.productBarcode.product.brand.brand_ID =" + brandId);

			sql.append(whereClause);
			sql.append(" GROUP BY p.productBarcode.id, p.order.order_type");
			
			List<Object> values = chainSalesOrderDaoImpl.executeHQLSelect(sql.toString(), value_sale.toArray(), null, true);
			
			Map<Integer, ChainPurchaseStatisticReportItemVO> dataMap = new HashMap<Integer, ChainPurchaseStatisticReportItemVO>();
			for (Object record : values ){
				Object[] records = (Object[])record;
				int quantity = Common_util.getInt(records[0]);
				double amount = Common_util.getDouble(records[1]);
				int pbId = Common_util.getInt(records[2]);
				int type = Common_util.getInt(records[3]);
				
				ChainPurchaseStatisticReportItemVO levelOneItem = dataMap.get(pbId);
				if (levelOneItem != null){
					levelOneItem.putValue(type, quantity, amount);
				} else {
					ProductBarcode pb = productBarcodeDaoImpl.get(pbId, true);
					Color color = pb.getColor();
					String colorName = "";
					if (color != null)
						colorName = color.getName();
					
					Category category = pb.getProduct().getCategory();
					
					name = Common_util.cutProductCode(pb.getProduct().getProductCode()) + colorName  + " " +  category.getCategory_Name();
					
					levelOneItem = new ChainPurchaseStatisticReportItemVO(name, parentId,chainId, yearId, quarterId, brandId,pbId, showCost, ChainPurchaseStatisticReportItemVO.STATE_OPEN);
					levelOneItem.putValue(type, quantity, amount);
					
					dataMap.put(pbId, levelOneItem);
				}
			}
			
			List<Integer> pbKey = new ArrayList<Integer>(dataMap.keySet());
			
			//1. 把基本对象放入
			//2. 计算总数
			for (Integer id : pbKey){
				ChainPurchaseStatisticReportItemVO levelOneItem = dataMap.get(id);
				levelOneItem.reCalculate();

				reportItems.add(levelOneItem);
			}
		}
		Collections.sort(reportItems, new ChainStatisticReportItemVOSorter());
	    response.setReturnValue(reportItems);
	    return response;
	}

	/**
	 * 获取all in one statis 信息
	 * @param parentId
	 * @param startDate
	 * @param endDate
	 * @param chain_id
	 * @param year_ID
	 * @param quarter_ID
	 * @param brand_ID
	 * @return
	 */
	@Transactional
	public Response getAllInOneReptEles(int parentId, java.sql.Date startDate, java.sql.Date endDate, int chainId,
			int yearId, int quarterId, int brandId, ChainUserInfor userInfor) {
	    Response response = new Response();
		
		List<ChainAllInOneReportItemVO> reportItems = new ArrayList<ChainAllInOneReportItemVO>();
		List<ChainSalesStatisticReportItemVO> salesStatisticReportItemVOs = new ArrayList<ChainSalesStatisticReportItemVO>();
		List<ChainPurchaseStatisticReportItemVO> purchaseStatisticReportItemVOs = new ArrayList<ChainPurchaseStatisticReportItemVO>();
		List<ChainInventoryItemVO> chainInventoryItemVOs = new ArrayList<ChainInventoryItemVO>();
		
		Map<String,ChainSalesStatisticReportItemVO> salesStatisticReportItemMap = new HashMap<String,ChainSalesStatisticReportItemVO>();
		Map<String,ChainPurchaseStatisticReportItemVO> purchaseStatisticReportItemMap = new HashMap<String,ChainPurchaseStatisticReportItemVO>();
		Map<String,ChainInventoryItemVO> chainInventoryItemMap = new HashMap<String,ChainInventoryItemVO>();
		Set<String> keys = new HashSet<String>();
		
		String name = "";

		
		Response salesResponse = getSalesStatisticReptEles(parentId, startDate, endDate, chainId, yearId, quarterId, brandId, Common_util.ALL_RECORD, userInfor);
		Response purchaseResponse = getPurchaseStatisticReptEles(parentId, startDate, endDate, chainId, yearId, quarterId, brandId, userInfor);
		Response inventoryResponse = chainInventoryFlowOrderService.getChainInventory(parentId, chainId, yearId, quarterId, brandId, userInfor, true);
		
		if (salesResponse.getReturnValue() != null){
			salesStatisticReportItemVOs = (List<ChainSalesStatisticReportItemVO>) salesResponse.getReturnValue();
		}
		if (purchaseResponse.getReturnValue() != null){
			purchaseStatisticReportItemVOs = (List<ChainPurchaseStatisticReportItemVO>) purchaseResponse.getReturnValue();
		}
		if (inventoryResponse.getReturnValue() != null){
			chainInventoryItemVOs = (List<ChainInventoryItemVO>) inventoryResponse.getReturnValue();
		}
		
		for (ChainSalesStatisticReportItemVO itemVO : salesStatisticReportItemVOs){
			keys.add(itemVO.generateKeys());
			salesStatisticReportItemMap.put(itemVO.generateKeys(), itemVO);
		}
		for (ChainInventoryItemVO itemVO : chainInventoryItemVOs){
			keys.add(itemVO.generateKeys());
			chainInventoryItemMap.put(itemVO.generateKeys(), itemVO);
		}
		for (ChainPurchaseStatisticReportItemVO itemVO : purchaseStatisticReportItemVOs){
			keys.add(itemVO.generateKeys());
			purchaseStatisticReportItemMap.put(itemVO.generateKeys(), itemVO);
		}
		
		List<String> keyList = new ArrayList<String>();
		keyList.addAll(keys);
		Collections.sort(keyList);
		
		
		
		for (String key : keyList){
			ChainSalesStatisticReportItemVO sales =  salesStatisticReportItemMap.get(key);
			ChainPurchaseStatisticReportItemVO purchase = purchaseStatisticReportItemMap.get(key);
			ChainInventoryItemVO inventory = chainInventoryItemMap.get(key);
			ChainAllInOneReportItemVO reportItem = null; 
			if (parentId == 0){
				
				ChainStore chainStore = this.getThisChainStore(chainId);
				name = chainStore.getChain_name();
				
				reportItem = new ChainAllInOneReportItemVO(name, 1, chainId, yearId, quarterId, brandId,0, ChainReportItemVO.STATE_CLOSED, sales, purchase, inventory);
			} else if (yearId == 0){
				int yearDB = get(key, "yearId");
				
				Year year = yearDaoImpl.get(yearDB, true);
				name = year.getYear() + "年";
				
				reportItem = new ChainAllInOneReportItemVO(name, parentId, chainId, yearDB, quarterId, brandId,0, ChainReportItemVO.STATE_CLOSED, sales, purchase, inventory);
			} else if (quarterId == 0){
				int quarterDB = get(key, "quarterId");
				
				Year year = yearDaoImpl.get(yearId, true);
				Quarter quarter = quarterDaoImpl.get(quarterDB, true);
				name = year.getYear() + "年 " + quarter.getQuarter_Name();
				
				reportItem = new ChainAllInOneReportItemVO(name, parentId, chainId, yearId, quarterDB, brandId,0, ChainReportItemVO.STATE_CLOSED, sales, purchase, inventory);	
			} else if (brandId == 0){
				int brandDB = get(key, "brandId");
				
				Brand brand = brandDaoImpl.get(brandDB, true);
				String pinyin = brand.getPinyin();
				if (!StringUtils.isEmpty(pinyin)){
					name = pinyin.substring(0, 1) + " ";
				}
				name += brand.getBrand_Name();
				
				boolean isChain = false;
				if (brand.getChainStore() != null && brand.getChainStore().getChain_id() !=0)
					isChain = true;
				
				reportItem = new ChainAllInOneReportItemVO(name, parentId, chainId, yearId, quarterId, brandDB,0, ChainReportItemVO.STATE_CLOSED, sales, purchase, inventory);	
			    reportItem.setIsChain(isChain);
			} else if (brandId != 0) {
				int pbId = get(key, "pbId");
				ProductBarcode pb = productBarcodeDaoImpl.get(pbId, true);
				
				if (pb == null)
					System.out.println(pbId +" " +  key);
				
				
				Color color = pb.getColor();
				String colorName = "";
				if (color != null)
					colorName = color.getName();
				
				boolean isChain = false;
				if (pb.getChainStore() != null && pb.getChainStore().getChain_id() !=0)
					isChain = true;

				Category category = pb.getProduct().getCategory();
				name = Common_util.cutProductCode(pb.getProduct().getProductCode()) + colorName+ " " +  category.getCategory_Name();
				reportItem = new ChainAllInOneReportItemVO(name, parentId, chainId, yearId, quarterId, brandId,pbId, ChainReportItemVO.STATE_OPEN, sales, purchase, inventory);
				reportItem.setIsChain(isChain);
			}
			
			reportItems.add(reportItem);
		}
		Collections.sort(reportItems, new ChainStatisticReportItemVOSorter());
		
		response.setReturnValue(reportItems);
		
		return response;
	}

	public int get(String key, String item){
		if (item.equals("parentId"))
			return Integer.parseInt(key.split(ChainReportItemVO.spliter)[0]);
		else if (item.equals("chainId"))
			return Integer.parseInt(key.split(ChainReportItemVO.spliter)[1]);
		else if (item.equals("yearId"))
			return Integer.parseInt(key.split(ChainReportItemVO.spliter)[2]);
		else if (item.equals("quarterId"))
			return Integer.parseInt(key.split(ChainReportItemVO.spliter)[3]);
		else if (item.equals("brandId"))
			return Integer.parseInt(key.split(ChainReportItemVO.spliter)[4]);
		else if (item.equals("pbId"))
			return Integer.parseInt(key.split(ChainReportItemVO.spliter)[5]);		
		return -1;
	}

}
