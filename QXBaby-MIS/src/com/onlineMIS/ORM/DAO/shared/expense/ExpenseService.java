package com.onlineMIS.ORM.DAO.shared.expense;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.onlineMIS.ORM.DAO.Response;
import com.onlineMIS.ORM.DAO.chainS.user.ChainStoreDaoImpl;
import com.onlineMIS.ORM.DAO.chainS.user.ChainStoreService;
import com.onlineMIS.ORM.DAO.chainS.user.ChainUserInforService;
import com.onlineMIS.ORM.entity.chainS.report.ChainReport;
import com.onlineMIS.ORM.entity.chainS.report.ChainSalesReport;
import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.ORM.entity.chainS.user.ChainUserInfor;
import com.onlineMIS.ORM.entity.shared.expense.Expense;
import com.onlineMIS.ORM.entity.shared.expense.ExpenseRptVO;
import com.onlineMIS.ORM.entity.shared.expense.ExpenseType;
import com.onlineMIS.ORM.entity.shared.expense.ExpenseVO;
import com.onlineMIS.action.shared.expense.ExpenseActionFormBean;
import com.onlineMIS.action.shared.expense.ExpenseActionUIBean;
import com.onlineMIS.common.Common_util;

@Service
public class ExpenseService {
	@Autowired
	private ExpenseDaoImpl expenseDaoImpl;
	
	@Autowired
	private ExpenseTypeDaoImpl expenseTypeDaoImpl;
	
	@Autowired
	private ChainStoreService chainStoreService;

	
	/**
	 * 修改expense
	 * @param expense
	 * @return
	 */
	public Response saveUpdateExpense(Expense expense){
		Response response = new Response();
		
		ChainStore belong = expense.getEntity();
		if (belong != null && belong.getChain_id() == 0)
			expense.setEntity(null);
		
		try {
			expense.setLastUpdateTime(Common_util.getToday());
		    expenseDaoImpl.saveOrUpdate(expense, true);
		} catch (Exception e){
			response.setFail(e.getMessage());
		}
		
		return response;
	}

	
	/**
	 * 获取所有的消费记录
	 * @param belong
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Response searchExpenseChain(ChainStore belongStore, java.sql.Date startDate1, java.sql.Date endDate1, Integer page, Integer rowPerPage){
		Response response = new Response();
		Map saleReport = new HashMap<String, Object>();
		
		java.util.Date startDate = Common_util.formStartDate(startDate1);
		java.util.Date endDate = Common_util.formEndDate(endDate1);
		
		/**
		 * 1。获取criteria
		 */
		int total = 0;
		if (page != null && rowPerPage != null){
			DetachedCriteria criteria = createGetExpenseCriteria(belongStore, startDate, endDate);
	        criteria.setProjection(Projections.rowCount());
			total = Common_util.getProjectionSingleValue(expenseDaoImpl.getByCriteriaProjection(criteria, true));
		}

		/**
		 * 2。查询子数据
		 */
		int firstRecord = Common_util.getFirstRecord(page, rowPerPage);
		
		DetachedCriteria selectCriteria = createGetExpenseCriteria(belongStore, startDate, endDate);
        List<Expense> resultList = expenseDaoImpl.getByCritera(selectCriteria, firstRecord, rowPerPage, true);
        List<ExpenseVO> resultVOList = new ArrayList<ExpenseVO>();
        
        for (Expense expense : resultList){
        	ExpenseVO expenseVO = new ExpenseVO(expense);
        	resultVOList.add(expenseVO);
        }
        
        /**
         * 2。查询汇总数据
         */
        ExpenseVO totalReport = new ExpenseVO();
        DetachedCriteria totalCriteria = createGetExpenseCriteria(belongStore, startDate, endDate);
        totalCriteria.add(Restrictions.ne("status", Expense.statusE.DELETED.getValue()));
		totalCriteria.setProjection(Projections.sum("amount"));
		double totalAmount = Common_util.getProjectionDoubleValue(expenseDaoImpl.getByCriteriaProjection(totalCriteria, true));
        totalReport.setEntity("合计 (除去删除记录)");
        totalReport.setAmount(totalAmount);

		List<ExpenseVO> footer = new ArrayList<ExpenseVO>();
		footer.add(totalReport);
		saleReport.put("footer", footer);
		saleReport.put("rows", resultVOList);
		saleReport.put("total", total);
		
		response.setReturnValue(saleReport);
		
		return response;
	}
	
	private DetachedCriteria createGetExpenseCriteria(ChainStore belong, java.util.Date startDate, java.util.Date endDate){
		DetachedCriteria criteria = DetachedCriteria.forClass(Expense.class);	
        if (belong == null || belong.getChain_id() == 0)
		   criteria.add(Restrictions.isNull("entity"));
        else if (belong.getChain_id() > 0) 
           criteria.add(Restrictions.eq("entity.chain_id", belong.getChain_id()));
        criteria.add(Restrictions.between("expenseDate", Common_util.formStartDate(startDate), Common_util.formEndDate(endDate)));
        
        
        return criteria;
	}

	/**
	 * 准备创建create expense chain的页面
	 * @param userInfor
	 * @param formBean
	 * @param uiBean
	 */
	public void prepareCreateExpenseChainUI(ChainUserInfor userInfor, ExpenseActionFormBean formBean,
			ExpenseActionUIBean uiBean) {
		if (!ChainUserInforService.isMgmtFromHQ(userInfor)){
			int chainId = userInfor.getMyChainStore().getChain_id();
			ChainStore chainStore = chainStoreService.getChainStoreByID(chainId);
			formBean.setChainStore(chainStore);
		} 

		formBean.getExpense().setExpenseDate(Common_util.getToday());
		
		uiBean.setExpenseTypes(expenseTypeDaoImpl.getExpenseTypes(ExpenseType.belongE.CHAIN.getType()));
		
	}

	/**
	 * 保存 chain的expense
	 * @param userInfor
	 * @param expense
	 */
	public Response saveExpenseChain(ChainUserInfor userInfor, ChainStore chainStore, Expense expense) {
		if (!ChainUserInforService.isMgmtFromHQ(userInfor)){
			expense.setEntity(userInfor.getMyChainStore());
		} else 
			expense.setEntity(chainStore);
		
		expense.setStatus(Expense.statusE.NORMAL.getValue());
		expense.setUserId(userInfor.getUser_id());
		expense.setUserName(userInfor.getName());
		
		return saveUpdateExpense(expense);
	}

	public void prepareSearchExpenseChainUI(ChainUserInfor userInfor, ExpenseActionFormBean formBean,
			ExpenseActionUIBean uiBean) {
		if (!ChainUserInforService.isMgmtFromHQ(userInfor)){
			int chainId = userInfor.getMyChainStore().getChain_id();
			ChainStore chainStore = chainStoreService.getChainStoreByID(chainId);
			formBean.setChainStore(chainStore);
		} else {
			ChainStore allChainStore = ChainStoreDaoImpl.getAllChainStoreObject();
			formBean.setChainStore(allChainStore);
		}

		formBean.setStartDate(Common_util.getToday());
		formBean.setEndDate(Common_util.getToday());
	}

	/**
	 * 删除某一条记录
	 * @param userInfor
	 * @param id
	 * @return
	 */
	public Response deleteExpenseChain(ChainUserInfor userInfor, int expenseId) {
		Response response = new Response();
	    Expense expense = expenseDaoImpl.get(expenseId, true);
	    if (expense == null){
	    	response.setFail("无法找到 id为 " + expenseId + " 的费用记录");
	    	return response;
	    } else {
			if (!ChainUserInforService.isMgmtFromHQ(userInfor)){
				int chainId = userInfor.getMyChainStore().getChain_id();
				int entityId = expense.getEntity().getChain_id();
				
				if (chainId != entityId){
			    	response.setFail("无法删除其他连锁店的费用记录  " + expenseId);
			    	return response;
				}
			} 
	    	
			expense.setLastUpdateTime(Common_util.getToday());
			expense.setUserId(userInfor.getUser_id());
			expense.setUserName(userInfor.getName());
	    	expense.setStatus(Expense.statusE.DELETED.getValue());
            return saveUpdateExpense(expense);
	    }
	}

	/**
	 * 获取消费汇总报表
	 * @param chain_id
	 * @param startDate
	 * @param endDate
	 * @param expenseRptLevel
	 * @return
	 */
	public Response getExpenseReportChain(ChainUserInfor userInfor, ChainStore belongStore, Date startDate1, Date endDate1, int expenseRptLevel) {
		Response response = new Response();
		java.util.Date startDate = Common_util.formStartDate(startDate1);
		java.util.Date endDate = Common_util.formEndDate(endDate1);
		
		DetachedCriteria selectCriteria = createGetExpenseCriteria(belongStore, startDate, endDate);
		selectCriteria.add(Restrictions.ne("status", Expense.statusE.DELETED.getValue()));
		
		List<ExpenseRptVO> expenseRptVOs = new ArrayList<ExpenseRptVO>();
		
		String expenseEleName = "";
		/**
		 * 获取最顶层费用 : 比如某个连锁店在某个时期总费用
		 */
		if (expenseRptLevel == 0){
			if (belongStore == null )
				expenseEleName = "";
			else if (belongStore.getChain_id() == ChainStoreDaoImpl.getAllChainStoreObject().getChain_id())
				expenseEleName = ChainStoreDaoImpl.getAllChainStoreObject().getChain_name();
			else {
				belongStore = chainStoreService.getChainStoreByID(belongStore.getChain_id());
				expenseEleName = belongStore.getChain_name();
			}

			selectCriteria.setProjection(Projections.sum("amount"));
			double amount = Common_util.getProjectionDoubleValue(expenseDaoImpl.getByCriteriaProjection(selectCriteria, true));

			
			ExpenseRptVO expenseRptVO = new ExpenseRptVO(expenseEleName, amount, 1, ExpenseRptVO.STATE_CLOSED);
            expenseRptVOs.add(expenseRptVO);
			
		/**
		 * 获取大类费用
		 */
		} else if (expenseRptLevel == 1) {
			ProjectionList projList = Projections.projectionList();
			projList.add(Projections.groupProperty("expenseType.id"));
			projList.add(Projections.sum("amount"));
			selectCriteria.setProjection(projList);
			List<Object> result = expenseDaoImpl.getByCriteriaProjection(selectCriteria, false);
			for (Object object : result)
				  if (object != null){
					Object[] recordResult = (Object[])object;
					int typeId = (Integer)recordResult[0];
					double amount =  (Double)recordResult[1];
					
					ExpenseType type = expenseTypeDaoImpl.get(typeId, true);
					if (type == null)
						continue;
					else 
						expenseEleName = type.getName();
					
					ExpenseRptVO expenseRptVO = new ExpenseRptVO(expenseEleName, amount, 1, ExpenseRptVO.STATE_OPEN);
		            expenseRptVOs.add(expenseRptVO);
				  } 
		/**
		 * 获取最小曾费用
		 */
		} else {
			
		}
		
		response.setReturnValue(expenseRptVOs);
		return response;
	}




}
