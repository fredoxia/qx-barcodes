package com.onlineMIS.ORM.DAO.headQ.finance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onlineMIS.ORM.DAO.Response;
import com.onlineMIS.ORM.DAO.chainS.user.ChainStoreDaoImpl;
import com.onlineMIS.ORM.DAO.chainS.user.ChainStoreService;
import com.onlineMIS.ORM.DAO.chainS.user.ChainUserInforService;
import com.onlineMIS.ORM.DAO.headQ.inventory.InventoryOrderDAOImpl;
import com.onlineMIS.ORM.entity.base.Pager;
import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainInOutStock;
import com.onlineMIS.ORM.entity.chainS.sales.ChainStoreSalesOrder;
import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.ORM.entity.chainS.user.ChainUserInfor;
import com.onlineMIS.ORM.entity.headQ.finance.ChainAcctFlow;
import com.onlineMIS.ORM.entity.headQ.finance.ChainAcctFlowReportItem;
import com.onlineMIS.ORM.entity.headQ.finance.ChainFinanceTrace;
import com.onlineMIS.ORM.entity.headQ.finance.FinanceBill;
import com.onlineMIS.ORM.entity.headQ.finance.FinanceBillItem;
import com.onlineMIS.ORM.entity.headQ.finance.FinanceCategory;
import com.onlineMIS.ORM.entity.headQ.inventory.InventoryOrder;
import com.onlineMIS.ORM.entity.headQ.user.UserInfor;
import com.onlineMIS.action.headQ.finance.FinanceActionFormBean;
import com.onlineMIS.action.headQ.finance.FinanceActionUIBean;
import com.onlineMIS.common.Common_util;
import com.onlineMIS.sorter.ChainAcctFlowReportItemSort;

@Service
public class FinanceService {
	@Autowired
	private FinanceBillImpl financeBillImpl;
	
	@Autowired
	private ChainAcctFlowDaoImpl chainAcctFlowDaoImpl;
	
	@Autowired
	private FinanceCategoryImpl financeCategoryImpl;
	
	@Autowired
	private ChainStoreDaoImpl chainStoreDaoImpl;
	
	@Autowired
	private ChainStoreService chainStoreService;
	
	@Autowired
	private ChainFinanceTraceImpl chainFinanceTraceImpl;
	
	@Autowired
	private InventoryOrderDAOImpl inventoryOrderDAOImpl;
	
	/**
	 * to prepare the bean when create the finance bill
	 * @param formBean
	 * @param uiBean
	 */
	public void prepareCreateBill(FinanceActionFormBean formBean, FinanceActionUIBean uiBean){
		FinanceBill financeBill = formBean.getFinanceBill();
		initializeFianceBill(financeBill);
		
//		List<ChainStore> chainStores = chainStoreDaoImpl.getAll(true);
//		uiBean.setChainStores(chainStores);

	}
	
	/**
	 * to prepare the bean when edit the finance bill
	 * @param uiBean
	 */
	public void prepareEditBill(FinanceActionFormBean formBean, ChainStore chainStore){
        formBean.setChainStore(chainStore);
	}
	
	/**
	 * initialize the finance bill
	 */
	private void initializeFianceBill(FinanceBill financeBill){
		List<FinanceBillItem> billItems = new ArrayList<FinanceBillItem>();
		List<FinanceCategory> allCategories = financeCategoryImpl.getAll(true);
		if (allCategories != null){
			for (int i = 0; i < allCategories.size(); i++){
				FinanceBillItem billItem = new FinanceBillItem();
				billItem.setFinanceCategory(allCategories.get(i));
				billItems.add(billItem);
			}
			financeBill.setFinanceBillItemList(billItems);
		}

		financeBill.setBillDate(new java.sql.Date(new Date().getTime()));
	}

	/**
	 * save the bill to draft
	 * @param financeBill
	 */
	public Response saveFHQToDraft(FinanceBill financeBill) {
		Response response = new Response();
		int billId = financeBill.getId();
		FinanceBill originalBill = financeBillImpl.get(billId, true);
		
		if (originalBill == null || originalBill.getStatus() == FinanceBill.STATUS_DRAFT){
			financeBillImpl.evict(originalBill);
			
			financeBill.setStatus(FinanceBill.STATUS_DRAFT);
			financeBill.setCreateDate(new Date());
			
			financeBill.putListToSet();
			
			financeBillImpl.saveOrUpdate(financeBill, true);
			
			response.setQuickValue(Response.SUCCESS, "单据成功保存草稿");
		} else {
			response.setReturnCode(Response.ERROR);
			response.setMessage("保存草稿出现错误");
		}
		
		return response;
	}

	/**
	 * 单据过账
	 * @param financeBill
	 */
	@Transactional
	public Response postFHQBill(FinanceBill financeBill) {
		Response response = new Response();
		int billId = financeBill.getId();
		FinanceBill originalBill = financeBillImpl.get(billId, true);
		
		if (originalBill == null || originalBill.getStatus() == FinanceBill.STATUS_DRAFT){
			financeBillImpl.evict(originalBill);
			// finanace bill validation
			if (validateFHQBill(financeBill, response) == false)
				return response;
			
			//1. change the bill status
			financeBill.setStatus(FinanceBill.STATUS_COMPLETE);
			financeBill.setCreateDate(new Date());
			financeBill.putListToSet();
			financeBillImpl.saveOrUpdate(financeBill, true);
			
			//2.0 updat the finance trace
			updateFinanceTrace(financeBill, false);
			
			//3.0 update the acct flow
			updateChainAcctFlow(financeBill, false);
			
			response.setReturnCode(Response.SUCCESS);
			response.setMessage("单据成功过账");
		} else {
			response.setReturnCode(Response.ERROR);
			response.setMessage("单据过账出现错误");
		}
		
		return response;
	}

	/**
	 * 1. 如果是使用预付款的单据,保证不能超过现在存在的预付款
	 * @param financeBill
	 * @param response
	 * @return
	 */
	private boolean validateFHQBill(FinanceBill financeBill, Response response) {
		int chainId = financeBill.getChainStore().getChain_id();
		if (chainId == 0){
	    	response.setFail("请选择一个连锁店再继续操作");
	    	return false;
		}
		
		List<FinanceBillItem> items = financeBill.getFinanceBillItemList();
		for (FinanceBillItem item: items){
			int itemType = item.getFinanceCategory().getType();
			double amount = item.getTotal();
			if (itemType == FinanceCategory.PREPAY_ACCT_TYPE && amount >0){
				double prepaySum = chainFinanceTraceImpl.getSumOfFinanceCategory(FinanceCategory.PREPAY_ACCT_TYPE, chainId);
			    if (amount > prepaySum){
			    	response.setQuickValue(Response.FAIL, "预付款超过以前总和");
			    	return false;
			    }
			}
		}
		
		return true;
	}

	/***
	 * to delete the FHQ Bill
	 * @param financeBill
	 */
	public Response deleteFHQBill(FinanceBill financeBill) {
		Response response = new Response();
		int billId = financeBill.getId();
		FinanceBill originalBill = financeBillImpl.get(billId, true);
		
		if (originalBill != null && originalBill.getStatus() == FinanceBill.STATUS_DRAFT){
			originalBill.setStatus(FinanceBill.STATUS_DELETED);
			originalBill.setCreateDate(new Date());
		
		    financeBillImpl.saveOrUpdate(originalBill, true);
		    
		    response.setReturnCode(Response.SUCCESS);
		} else {
			response.setReturnCode(Response.ERROR);
			response.setMessage("删除单据出错");
		}
		
		return response;
	}
	
	/**
	 * service to cancel the bill 
	 * @param financeBill
	 */
	@Transactional
	public Response cancelFHQBill(FinanceBill financeBill) {
		Response response = new Response();
		int billId = financeBill.getId();
		FinanceBill originalBill = financeBillImpl.get(billId, true);
		
		if (originalBill == null || originalBill.getStatus() != FinanceBill.STATUS_COMPLETE){
			response.setReturnCode(Response.ERROR);
			response.setMessage("单据不存在或者尚未过账无法红冲");
		} else {
			//1.0 change the bill status 
			originalBill.setStatus(FinanceBill.STATUS_CANCEL);
			//originalBill.setCreateDate(new Date());	
			financeBillImpl.update(originalBill, true);

			//2.0 updat the finance trace
			updateFinanceTrace(originalBill, true);

			//3.0 update the acct flow
			updateChainAcctFlow(originalBill, true);

			response.setReturnCode(Response.SUCCESS);
		}
		
		return response;
	}

	/**
	 * function to search the finance bills based on the search criteria
	 * @param formBean
	 * @return
	 */
	public List<FinanceBill> searchFHQBills(FinanceActionFormBean formBean) {
		boolean cached = false;
		Pager pager = formBean.getPager();

		if (pager.getTotalPage() == 0){
		    DetachedCriteria criteria = buildSearchFHQBills(formBean);
			criteria.setProjection(Projections.rowCount());
			int totalRecord = Common_util.getProjectionSingleValue(financeBillImpl.getByCriteriaProjection(criteria, false));
			pager.initialize(totalRecord);
		} else {
			pager.calFirstResult();
			cached = true;
		}
		
		DetachedCriteria criteria2 = buildSearchFHQBills(formBean);
		criteria2.addOrder(Order.asc("chainStore.chain_id"));
		criteria2.addOrder(Order.asc("billDate"));
		
		return financeBillImpl.getByCritera(criteria2, pager.getFirstResult(), pager.getRecordPerPage(), cached);
	}
	
	private DetachedCriteria buildSearchFHQBills(FinanceActionFormBean formBean){
        DetachedCriteria criteria = DetachedCriteria.forClass(FinanceBill.class);
        FinanceBill financeBill = formBean.getFinanceBill();
		
		//1. get the status
		int status = financeBill.getStatus();
		if (status != Common_util.ALL_RECORD)
			criteria.add(Restrictions.eq("status", status));
		else 
			criteria.add(Restrictions.ne("status", FinanceBill.STATUS_DELETED));
		
		//2. get the category
		int type = financeBill.getType();
		if (type != Common_util.ALL_RECORD)
			criteria.add(Restrictions.eq("type", type));
		
		
		//2. get the chain
		int chain_id = formBean.getChainStore().getChain_id();
		if (chain_id != Common_util.ALL_RECORD)
			criteria.add(Restrictions.eq("chainStore.chain_id", chain_id));
		
		//4. set the date
		Date startDate = Common_util.formStartDate(formBean.getSearchStartTime());
		Date endDate = Common_util.formEndDate(formBean.getSearchEndTime());
		criteria.add(Restrictions.between("createDate", startDate, endDate));
		
		return criteria;
	}
	
	/**
	 * to update the finance trace
	 * @param bill
	 * @param isCancel
	 */
	private void updateFinanceTrace(FinanceBill bill, boolean isCancel){
		int chainId = bill.getChainStore().getChain_id();
		String billId = String.valueOf(bill.getId());
		int offset = isCancel ? -1 : 1;
		billId= isCancel ? "C" + billId : billId;
		
		int billType = bill.getType();
		
		//if it is 总部的付款单需要*-1
		if (billType == FinanceBill.FINANCE_PAID_HQ || billType == FinanceBill.FINANCE_INCREASE_HQ)
			offset *= -1;
		
		double totalAmt = 0;
		//1. to update the bill items
		for (FinanceBillItem billItem : bill.getFinanceBillItemSet()){
			int categoryId = billItem.getFinanceCategory().getId();
			int type = financeCategoryImpl.get(categoryId, true).getType();
			double amount = billItem.getTotal();
			
			if (amount != 0){
				totalAmt += amount;
				//1.1 if it is 预付款需要减
				if (type == FinanceCategory.PREPAY_ACCT_TYPE)
					offset *= -1;
				
			    ChainFinanceTrace financeTrace = new ChainFinanceTrace(chainId, type, billId, amount * offset, new java.sql.Date(bill.getCreateDate().getTime()));
			    chainFinanceTraceImpl.save(financeTrace, false);
			} 
		}
		
		//2. if it is prepaid bill, need insert a prepaid amount
		if (billType == FinanceBill.FINANCE_PREINCOME_HQ){
		    ChainFinanceTrace financeTrace = new ChainFinanceTrace(chainId, FinanceCategory.PREPAY_ACCT_TYPE, billId, totalAmt * offset , new java.sql.Date(bill.getCreateDate().getTime()));
		    chainFinanceTraceImpl.save(financeTrace, false);
		}
		
	}
	
	private void updateChainAcctFlow(FinanceBill bill, boolean isCancel){
		int chainId = bill.getChainStore().getChain_id();
		
		ChainStore chainStore = chainStoreDaoImpl.get(chainId, true);
		
		if (chainStore !=  null){
			int billType = bill.getType();
			int offset = 1;
			
			//1. check the finance bill type
			if (billType == FinanceBill.FINANCE_INCOME_HQ || billType == FinanceBill.FINANCE_DECREASE_HQ){
				offset *= -1;
			} else if (billType == FinanceBill.FINANCE_PAID_HQ || billType == FinanceBill.FINANCE_INCREASE_HQ){
				offset *= 1;
			} else 
				return ;
			
			//2. check is cancel
			if (isCancel)
				offset *= -1;
			
			//3. calculate the pre-acct and postAcctAmt
    		double initialAcctAmt = chainStore.getInitialAcctAmt();
    		double acctAmtFlow = chainAcctFlowDaoImpl.getAccumulateAcctFlow(chainId);
    		double preAcctAmt = Common_util.getDecimalDouble(initialAcctAmt + acctAmtFlow);
    		
    		//4. 设计折扣
    		double invoiceTotal = bill.getInvoiceTotal();
			if (billType == FinanceBill.FINANCE_INCREASE_HQ || billType == FinanceBill.FINANCE_PAID_HQ) {
				invoiceTotal -= bill.getInvoiceDiscount();
			} else if (billType == FinanceBill.FINANCE_DECREASE_HQ || billType == FinanceBill.FINANCE_INCOME_HQ){
				invoiceTotal += bill.getInvoiceDiscount();
			}
			double netAmt = offset * invoiceTotal;
			double postAcctAmt = Common_util.getDecimalDouble(preAcctAmt + netAmt);
    		
    		//4.update the finance bill's pre and post 
    		if (!isCancel){
    		   bill.setPreAcctAmt(preAcctAmt);
    		   bill.setPostAcctAmt(postAcctAmt);
    		   financeBillImpl.update(bill, true);
    		}
			
			ChainAcctFlow chainAcctFlow = new ChainAcctFlow(chainId, netAmt, "F," + bill.getId() + "," + isCancel, new java.sql.Date(bill.getCreateDate().getTime()));
			chainAcctFlowDaoImpl.save(chainAcctFlow, true);
		}
		
	}

	/**
	 * to get the finance bill by id
	 * @param id
	 * @return
	 */
	@Transactional
	public FinanceBill getFinanceBillById(int id) {
		FinanceBill financeBill = financeBillImpl.get(id, true);
		
		financeBillImpl.initialize(financeBill);
		
		financeBill.putSetToList();
		
		return financeBill;
	}

	/**
	 * 获取连锁店当前欠款 和 预付款
	 * @param chainId
	 * @return
	 */
	public Response getChainCurrentFinance(int chainId) {
		Response response = new Response();
		
		ChainStore chainStore = chainStoreDaoImpl.get(chainId, true);
		
		//1. Get the current finance
		double initialAcct = chainStore.getInitialAcctAmt();
		double currentFinance = initialAcct + chainAcctFlowDaoImpl.getAccumulateAcctFlow(chainId);
		currentFinance = Common_util.roundDouble(currentFinance, 2);
		
		//2. get the accumulated prepaid
		double prepaid = chainFinanceTraceImpl.getSumOfFinanceCategory(FinanceCategory.PREPAY_ACCT_TYPE, chainId);
		prepaid = Common_util.roundDouble(prepaid, 2);
		
		response.setReturnCode(Response.SUCCESS);
		
		Map<String, Double> financeDataMap = new HashMap<String, Double>();
		financeDataMap.put("cf", currentFinance);
		financeDataMap.put("pp", prepaid);
		
		response.setReturnValue(financeDataMap);
		
		return response;
	}

	/**
	 * 
	 * @param startDate
	 * @param endDate
	 * @param chainId
	 * @return
	 */
	public Response searchAcctFlow(Date startDate, Date endDate, int chainId, boolean isChain) {
		Response response = new Response();
		List<ChainAcctFlowReportItem> rptItems = new ArrayList<ChainAcctFlowReportItem>();
		
		if (chainId != -1 && chainId != 0){
			/**
			 * 1. 搜索所有采购单据
			 */
			DetachedCriteria criteria = DetachedCriteria.forClass(InventoryOrder.class,"order");
			criteria.add(Restrictions.eq("order.order_Status", InventoryOrder.STATUS_ACCOUNT_COMPLETE));
			
			if (chainId != Common_util.ALL_RECORD){
				ChainStore chainStore = chainStoreDaoImpl.get(chainId, true);
				int clientid = chainStore.getClient_id();
				criteria.add(Restrictions.eq("order.client_id", clientid));
			} else {
				criteria.add(Restrictions.in("order.client_id", getAllChainClientId()));
			}
			startDate  = Common_util.formStartDate(startDate);
			endDate = Common_util.formEndDate(endDate);
			criteria.add(Restrictions.between("order.order_EndTime",startDate,endDate));
			//criteria.addOrder(Order.asc("order.order_StartTime"));
			List<InventoryOrder> purchaseOrders = inventoryOrderDAOImpl.getByCritera(criteria, true);
	
			/**
			 * 2. 搜索所有财物单据
			 */
	        DetachedCriteria criteria2 = DetachedCriteria.forClass(FinanceBill.class);
			criteria2.add(Restrictions.eq("status", FinanceBill.STATUS_COMPLETE));
			if (chainId != Common_util.ALL_RECORD){
				criteria2.add(Restrictions.eq("chainStore.chain_id", chainId));
			} else {
				criteria.add(Restrictions.in("chainStore.chain_id", getAllChainId()));
			}
			criteria2.add(Restrictions.between("createDate", startDate, endDate));
			List<FinanceBill> financeBills = financeBillImpl.getByCritera(criteria2, true);
			
	
			constructChainAcctFlowRptItems(purchaseOrders, financeBills, rptItems, chainId, isChain, startDate);
		}
		
		Map data = new HashMap<String, List>();
		data.put("rows", rptItems);
		response.setReturnValue(data);
		response.setReturnCode(Response.SUCCESS);
		
		return response;
	}
	
	/**
	 * 构建账户流水
	 * @param purchaseOrders
	 * @param financeBills
	 * @param rptItems
	 */
	private void constructChainAcctFlowRptItems(List<InventoryOrder> purchaseOrders, List<FinanceBill> financeBills, List<ChainAcctFlowReportItem> rptItems, int chainId, boolean isChain, Date startDate){
		//1. 货品采购单
		if (purchaseOrders != null && purchaseOrders.size() >0){
			for (InventoryOrder order : purchaseOrders){
				int clientId = order.getClient_id();
				ChainStore chainStore = chainStoreDaoImpl.getByClientId(clientId);
				String orderType = "";
				if (isChain)
					orderType = order.getOrder_type_chain();
				else 
					orderType = order.getOrder_type_ws();
				
				String acctFlowType ="";
				if (order.getOrder_type() == InventoryOrder.TYPE_SALES_ORDER_W)
					acctFlowType = ChainAcctFlowReportItem.ACCT_FLOW_TYPE_INCREASE;
				else if (order.getOrder_type() == InventoryOrder.TYPE_SALES_RETURN_ORDER_W)
					acctFlowType = ChainAcctFlowReportItem.ACCT_FLOW_TYPE_DECREASE;
				
				ChainAcctFlowReportItem acctFlowItem = new ChainAcctFlowReportItem(chainStore, order.getOrder_EndTime(), orderType, ChainAcctFlowReportItem.ITEM_TYPE_PURCHASE,acctFlowType, order.getTotalQuantity(), order.getTotalWholePrice() - order.getTotalDiscount(), order.getOrder_ID(),order.getComment(), 0,0);
				rptItems.add(acctFlowItem);
			}
		}
		
		//2. 财务单据
		if (financeBills != null && financeBills.size() > 0){
			for (FinanceBill bill : financeBills){
				ChainStore chainStore = bill.getChainStore();
				String billTypeName = "";
				if (isChain)
					billTypeName = bill.getTypeChainS();
				else 
					billTypeName = bill.getTypeHQS();
				
				int billType = bill.getType();
				String acctFlowType = "";
				double invoiceTotal = bill.getInvoiceTotal();
				if (billType == FinanceBill.FINANCE_INCREASE_HQ || billType == FinanceBill.FINANCE_PAID_HQ) {
					acctFlowType = ChainAcctFlowReportItem.ACCT_FLOW_TYPE_INCREASE;
					invoiceTotal -= bill.getInvoiceDiscount();
				} else if (billType == FinanceBill.FINANCE_DECREASE_HQ || billType == FinanceBill.FINANCE_INCOME_HQ){
					acctFlowType = ChainAcctFlowReportItem.ACCT_FLOW_TYPE_DECREASE;
					invoiceTotal += bill.getInvoiceDiscount();
				}
				
				ChainAcctFlowReportItem acctFlowItem = new ChainAcctFlowReportItem(chainStore, bill.getCreateDate(), billTypeName, ChainAcctFlowReportItem.ITEM_TYPE_FINANCE, acctFlowType, 0, invoiceTotal, bill.getId(), bill.getComment(), 0,0);
				rptItems.add(acctFlowItem);
			}
		}
		
		//3. 排序一下
		Collections.sort(rptItems, new ChainAcctFlowReportItemSort());
		
		//4. 获取前面的累计欠款
		Date firstDate = null;
		double acctBefore  = 0;
		if (rptItems.size() > 0){
			firstDate = rptItems.get(0).getDate();
		} else 
			firstDate = startDate;
			
		
		if (firstDate != null)
		    acctBefore = chainAcctFlowDaoImpl.getAccumulateAcctFlowBefore(chainId, firstDate);
		
		//5. 准备数据
		ChainStore chainStore = chainStoreDaoImpl.get(chainId, true);
		if (chainStore != null)
		     acctBefore += chainStore.getInitialAcctAmt();
		
		double acctAfter = acctBefore;
		for (ChainAcctFlowReportItem rptItem: rptItems){
			double thisAmt = rptItem.getAmount();
			String acctFlowType = rptItem.getAcctFlowType();
			
			if (acctFlowType.equalsIgnoreCase(ChainAcctFlowReportItem.ACCT_FLOW_TYPE_INCREASE)){
				acctAfter += thisAmt;
				rptItem.setAcctIncrease(thisAmt);
				rptItem.setPostAcct(acctAfter);
			} else if (acctFlowType.equalsIgnoreCase(ChainAcctFlowReportItem.ACCT_FLOW_TYPE_DECREASE)){
				acctAfter = acctAfter - thisAmt;
				rptItem.setAcctDecrease(thisAmt);
				rptItem.setPostAcct(acctAfter);
			} else {
				rptItem.setPostAcct(acctAfter);
			}

		}
		
		//6.0 设置首
		ChainAcctFlowReportItem firstItem = new ChainAcctFlowReportItem();
		firstItem.setDate(startDate);
		firstItem.setPostAcct(acctBefore);
		rptItems.add(0, firstItem);
	}
	
	
	private Set<Integer> getAllChainId(){
		Set<Integer> chainIds = new HashSet<Integer>();
		List<ChainStore> stores = chainStoreService.getAvailableClientChainstores();
		for (ChainStore chainStore : stores)
			chainIds.add(chainStore.getChain_id());
		
		return chainIds;
	}
	
	private Set<Integer> getAllChainClientId(){
		Set<Integer> clientIds = new HashSet<Integer>();
		List<ChainStore> stores = chainStoreService.getAvailableClientChainstores();
		for (ChainStore chainStore : stores)
			clientIds.add(chainStore.getClient_id());
		
		return clientIds;
	}

}
