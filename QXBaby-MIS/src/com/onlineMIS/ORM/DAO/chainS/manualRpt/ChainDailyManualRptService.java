package com.onlineMIS.ORM.DAO.chainS.manualRpt;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.chainsaw.Main;
import org.apache.naming.java.javaURLContextFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onlineMIS.ORM.DAO.Response;
import com.onlineMIS.ORM.DAO.chainS.chainMgmt.QxbabyConfDaoImpl;
import com.onlineMIS.ORM.DAO.chainS.user.ChainStoreDaoImpl;
import com.onlineMIS.ORM.DAO.chainS.user.ChainUserInforService;
import com.onlineMIS.ORM.DAO.headQ.barCodeGentor.QuarterDaoImpl;
import com.onlineMIS.ORM.DAO.headQ.barCodeGentor.YearDaoImpl;
import com.onlineMIS.ORM.entity.chainS.chainMgmt.QxbabyConf;
import com.onlineMIS.ORM.entity.chainS.manualRpt.ChainDailyManualReportAnalysisItem;
import com.onlineMIS.ORM.entity.chainS.manualRpt.ChainDailyManualRpt;
import com.onlineMIS.ORM.entity.chainS.manualRpt.ChainDailyManualRptConf;
import com.onlineMIS.ORM.entity.chainS.sales.ChainStoreSalesOrder;
import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.ORM.entity.chainS.user.ChainUserInfor;
import com.onlineMIS.ORM.entity.headQ.SQLServer.ClientsMS;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Quarter;
import com.onlineMIS.action.chainS.manualRpt.ChainDailyManualRptActionFormBean;
import com.onlineMIS.action.chainS.manualRpt.ChainDailyManualRptActionUIBean;
import com.onlineMIS.common.Common_util;

@Service
public class ChainDailyManualRptService {

	@Autowired
	private ChainStoreDaoImpl chainStoreDaoImpl;
	
	@Autowired
	private ChainDailyManualRptConfDaoImpl chainDailyManualRptConfDaoImpl;
	
	@Autowired
	private ChainDailyManualRptDaoImpl chainDailyManualRptDaoImpl;
	

	
	public void preparePreCreateManualRptUI(
			ChainDailyManualRptActionFormBean formBean,
			ChainDailyManualRptActionUIBean uiBean, ChainUserInfor userInfor) {
		if (!ChainUserInforService.isMgmtFromHQ(userInfor)){
			int chainStoreId = userInfor.getMyChainStore().getChain_id();
			ChainStore chainStore = chainStoreDaoImpl.get(chainStoreId, true);
			formBean.setChainStore(chainStore);
		}
		
		if (formBean.getRptDate() == null)
		    formBean.setRptDate(Common_util.getToday());
		
	}

	/**
	 * validate create manual rpt
	 * 1. 如果不是mgmt只能创建自己连锁店
	 * 2. 日期不能超过conf的lock date
	 * 3. 不能重复提交
	 * @param chainStore
	 * @param rptDate
	 * @param userInfor
	 * @return
	 */
	@Transactional
	public Response validateCreateManualRpt(ChainStore chainStore,
			Date rptDate, ChainUserInfor userInfor) {
		Response response = new Response();
		
		int overDayLock = 0;

		ChainDailyManualRptConf conf = getChainDailyConf();
		if (conf != null)
			overDayLock= conf.getOverDayLock();
		
		if (!isOKforOverDayLock(rptDate, overDayLock)){
			response.setReturnCode(Response.FAIL);
			response.setMessage("只能填写从 今天 到 倒数" + overDayLock +"天的报表。超过时间范围日期已经锁定");
			return response;
		} else if (!ChainUserInforService.isMgmtFromHQ(userInfor) && (chainStore.getChain_id() != userInfor.getMyChainStore().getChain_id())){
			response.setReturnCode(Response.FAIL);
			response.setMessage("只能选择自己连锁店，并填写报表");
			return response;
		} else if (getRptCountByChainIdRptDate(chainStore.getChain_id(), rptDate) > 0){
			response.setReturnCode(Response.FAIL);
			response.setMessage("当前日期报表已经填写，请勿重复填写");
			return response;
			
		}
		
		response.setReturnCode(Response.SUCCESS);
		return response;
	}
	
	
	private int getRptCountByChainIdRptDate(int chainId, Date rptDate){
		String hql = "SELECT COUNT(id) FROM ChainDailyManualRpt WHERE chainStore.chain_id =? AND rptDate = ?";
		Object[] value = new Object[]{chainId, rptDate};
		
		int count = chainDailyManualRptDaoImpl.executeHQLCount(hql, value, true);
		
		return count;		
	}
	
	private ChainDailyManualRpt getRptByChainIdRptDate(int chainId, Date rptDate){
		DetachedCriteria criteria = DetachedCriteria.forClass(ChainDailyManualRpt.class);
		criteria.add(Restrictions.eq("chainStore.chain_id", chainId));
		criteria.add(Restrictions.eq("rptDate", rptDate));
		List<ChainDailyManualRpt> chainDailyRpts = chainDailyManualRptDaoImpl.getByCritera(criteria, true);
		
		if (chainDailyRpts == null || chainDailyRpts.size() ==0)
			return null;
		else 
		    return chainDailyRpts.get(0);
	}
	
	/**
	 * 检查是否已经过期
	 * @param rptDate
	 * @param overDayLock
	 * @return
	 */
	private boolean isOKforOverDayLock(Date rptDate, int overDayLock) {
		Date today = Common_util.getToday();
        
		int diff = Math.abs(Common_util.getDateInterval(rptDate, today));
		
		if (diff <= overDayLock && !today.before(rptDate))
			return true;
		return false;
	}

	private ChainDailyManualRptConf getChainDailyConf(){
		List<ChainDailyManualRptConf> confs = chainDailyManualRptConfDaoImpl.getAll(true);
		if (confs == null || confs.size() == 0)
			return null;
		else 
			return confs.get(0);
	}
	
	public static void main(String[] args){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE,  -1);
		
		Date date = calendar.getTime();
		
		System.out.println(date);
		
//		System.out.println(ChainDailyManualRptService.isOKforOverDayLock(date, 1));
		
	}

	public void prepareCreateRptFormBean(ChainDailyManualRptActionFormBean formBean) {
		Date rptDay = formBean.getRptDate();
		
		formBean.getRpt().setRptDate(rptDay);
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(rptDay);
		formBean.getRpt().setWeekday(Common_util.getWeekDays(calendar.get(Calendar.DAY_OF_WEEK) - 1));
		
		ChainStore chainStore = chainStoreDaoImpl.get(formBean.getChainStore().getChain_id(), true);
		formBean.getRpt().setChainStore(chainStore);
	}

	public Response saveManualRpt(ChainDailyManualRpt rpt, ChainUserInfor userInfor) {
		Response response = new Response();
		
		try {
		    rpt.setCreator(userInfor);
		    
//		    rpt.setAnalysisItems(new ArrayList<ChainDailyManualReportAnalysisItem>());
		    chainDailyManualRptDaoImpl.save(rpt, true);
		    response.setSuccess("");
		} catch(Exception e ){
			e.printStackTrace();
			response.setFail(e.getMessage());
		}

		return response;
	}


}
