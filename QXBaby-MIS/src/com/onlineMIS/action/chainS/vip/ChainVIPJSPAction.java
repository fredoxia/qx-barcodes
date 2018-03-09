package com.onlineMIS.action.chainS.vip;

import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.hibernate.hql.ast.tree.FromClause;


import com.onlineMIS.ORM.DAO.Response;
import com.onlineMIS.ORM.entity.base.Pager;
import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainInvenTraceInfoVO;
import com.onlineMIS.ORM.entity.chainS.user.ChainUserInfor;
import com.onlineMIS.ORM.entity.chainS.vip.ChainVIPCard;
import com.onlineMIS.ORM.entity.chainS.vip.ChainVIPScore;
import com.onlineMIS.ORM.entity.chainS.vip.ChainVIPType;
import com.onlineMIS.ORM.entity.headQ.user.UserInfor;
import com.onlineMIS.common.Common_util;
import com.onlineMIS.common.loggerLocal;
import com.opensymphony.xwork2.ActionContext;

public class ChainVIPJSPAction extends ChainVIPAction {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 650258879600292909L;
	/**
	 * the barcode file
	 */
	private final String templateFileName = "VIPsTemplate.xls";
	private String excelFileName = "vips.xls";
	private InputStream excelStream;
	
	public String getExcelFileName() {
		return excelFileName;
	}
	public void setExcelFileName(String excelFileName) {
		this.excelFileName = excelFileName;
	}
	public InputStream getExcelStream() {
		return excelStream;
	}
	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	/**
	 * function to search vip cards by the search criteria
	 * @return
	 */
	public String preSearchSpecialVIPs(){
    	ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"preSearchSpecialVIPs");
    	
		//1. prepare the ui bean for the search part
		chainVIPService.prepareSearchVIPCardUI(uiBean, userInfor);
		
		return  "ChainSearchSpecialVIPs";
	}
	/**
	 * to get all chain vip type list from the UI
	 * @return
	 */
	public String viewVIPTypes(){
    	ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"getPurchase");
    	
		List<ChainVIPType> vipTypes = chainVIPService.getAllChainVIPTypes();
		uiBean.setChainVIPTypes(vipTypes);
		
		return "ChainVIPTypeList";
	}
	

	
	/**
	 * delete the vip type from the web
	 * @return
	 */
	public String deleteVIPType(){
    	ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"deleteVIPType");
    	
		//1. delete vip type
        Response response = chainVIPService.deleteVIPType(formBean.getVipType().getId());
        switch (response.getReturnCode()) {
		   case 1: addActionError("还有顾客使用此VIP类型，无法删除"); break;
		   case Response.SUCCESS: addActionMessage("成功删除VIP类型"); break;
		   case Response.FAIL: addActionError("删除VIP类型遇到错误，请核查"); break;
		   default:
			   break;
		}
		
        //2. retain the chain vip type list
		List<ChainVIPType> vipTypes = chainVIPService.getAllChainVIPTypes();
		uiBean.setChainVIPTypes(vipTypes);
        
        return "ChainVIPTypeList";
	}
	
	/**
	 * get the chain vip type by id
	 * @return
	 */
	public String preSaveUpdateVIPType(){
    	ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"preSaveUpdateVIPType");
    	
    	
		ChainVIPType vipType = chainVIPService.getVIPTypeById(formBean.getVipType().getId());
		formBean.setVipType(vipType);
		
		return "EditVIPType";
	}
	
	/**
	 * pre add the vip type
	 * @return
	 */
	public String preAddVIPType(){
    	ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"preAddVIPType");

		return "EditVIPType";
	}
	
	/**
	 * to get all chain vip cards list from the ui
	 * @return
	 */
	public String viewAllVIPCards(){
    	ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"viewAllVIPCards");
    	
    	formBean.getVipCard().setIssueChainStore(userInfor.getMyChainStore());
    	
    	//1. prepare the ui bean for the search part
		chainVIPService.prepareSearchVIPCardUI(uiBean, userInfor);
		
		//2. get the list
		List<ChainVIPCard> vipCards = chainVIPService.getChainVIPCards(userInfor, formBean.getPager());

		uiBean.setChainVIPCards(vipCards);
		
		return "ChainVIPCardList";
	}
	
	/**
	 * function to search vip cards by the search criteria
	 * @return
	 */
	public String searchVIPCards(){
    	ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"searchVIPCards");
    	
		//1. prepare the ui bean for the search part
		chainVIPService.prepareSearchVIPCardUI(uiBean, userInfor);
		
		//2. get the list  
		List<ChainVIPCard> vipCards = chainVIPService.searchVIPCards(formBean.getVipCard(), formBean.getPager());

		uiBean.setChainVIPCards(vipCards);
		
		return  "ChainVIPCardList";
	}
	
	/**
	 * to delete the vip card
	 * @return
	 */
	public String deleteVIPCard(){
    	ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"deleteVIPCard");
    	
		int vipCardId = formBean.getSelectedCardId();

        Response response = new Response();
        
        try {
        	response = chainVIPService.deleteVIPCard(vipCardId,userInfor);
        } catch (Exception e){
        	response.setFail(e.getMessage());
        }
        switch (response.getReturnCode()) {
		   case Response.SUCCESS: addActionMessage("成功删除VIP卡: " + response.getMessage()); break;
		   case Response.FAIL: addActionError("删除VIP卡失败: " + response.getMessage()); break;
		   case Response.NO_AUTHORITY: addActionError("你没有权限删除其他连锁店VIP卡: " + response.getMessage()); break;
		   default:break;
		}
	    
	    //2. retain the chain vip type list
	    List<ChainVIPCard> vipCards = chainVIPService.searchVIPCards(formBean.getVipCard(), formBean.getPager());
	    uiBean.setChainVIPCards(vipCards);
	    
	    //3. prepare the ui bean
	    chainVIPService.prepareSearchVIPCardUI(uiBean, userInfor);
        return "ChainVIPCardList";
	}
	
	/**
	 * get the chain vip card by id
	 * @return
	 */
	public String preSaveUpdateVIPCard(){
    	ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"preSaveUpdateVIPCard");
    	
		//get the vip card
		int vipCardId = formBean.getSelectedCardId();

		ChainVIPCard vipCard = chainVIPService.getVIPCardByCardId(vipCardId);
		formBean.setVipCard(vipCard);
		
		//prepare the UI
		chainVIPService.prepareEditVIPCardUI(uiBean, userInfor);
		
		return "EditVIPCard";	
	}

	
	/**
	 * pre add the vip card
	 * @return
	 */
	public String preAddVIPCard(){
    	ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"preAddVIPCard");
    	
		Date today = new Date();
		formBean.getVipCard().setCardIssueDate(today);
		formBean.getVipCard().setCardExpireDate(Common_util.calculateVIPCardExpireDate(today));		
		formBean.getVipCard().setStatus(ChainVIPCard.STATUS_GOOD);
		
		//prepare the UI
		chainVIPService.prepareEditVIPCardUI(uiBean, userInfor);
		
		return "EditVIPCard";
	}
	
	/**
	 * to start a vip card
	 * @return
	 */
	public String startVIPCard(){
    	ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"startVIPCard");
    	
        Response response = changeVipCardStatus(ChainVIPCard.STATUS_GOOD);
        switch (response.getReturnCode()) {
		   case Response.SUCCESS: addActionMessage("成功启用VIP卡: " + response.getMessage()); break;
		   case Response.FAIL: addActionError("启用VIP卡失败: " + response.getMessage()); break;
		   case Response.NO_AUTHORITY: addActionError("你没有权限启用其他连锁店VIP卡: " + response.getMessage()); break;
		   default:break;
		}
	    return "ChainVIPCardList";
	}

	/**
	 * to start a vip card
	 * @return
	 */
	public String stopVIPCard(){
    	ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"stopVIPCard");
    	
		Response response = changeVipCardStatus(ChainVIPCard.STATUS_STOP);
        switch (response.getReturnCode()) {
		   case Response.SUCCESS: addActionMessage("成功停用VIP卡: " + response.getMessage()); break;
		   case Response.FAIL: addActionError("停用VIP卡失败: " + response.getMessage()); break;
		   case Response.NO_AUTHORITY: addActionError("你没有权限停用其他连锁店VIP卡: " + response.getMessage()); break;
		   default:break;
		}
	    return "ChainVIPCardList";
	}
	
	/**
	 * to lost a vip card
	 * @return
	 */
	public String lostVIPCard(){
    	ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"lostVIPCard");
    	
		Response response = changeVipCardStatus(ChainVIPCard.STATUS_LOST);
        switch (response.getReturnCode()) {
		   case Response.SUCCESS: addActionMessage("成功挂失VIP卡 : " + response.getMessage()); break;
		   case Response.FAIL: addActionError("挂失VIP卡失败: " + response.getMessage()); break;
		   case Response.NO_AUTHORITY: addActionError("你没有权限挂失其他连锁店VIP卡: " + response.getMessage()); break;
		   default:break;
		}
	    return "ChainVIPCardList";
	}
	
	/**
	 * 上传vip文件
	 * @return
	 */
	public String preUploadVIPs(){
    	ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"preUploadVIPs");

		return "UploadVIPs";
	}
	
	/**
	 * 上传vip文件
	 * @return
	 */
	public String uploadVIPs(){
    	ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"uploadVIPs");
    	
    	Response response = new Response();
    	try {
    	     response = chainVIPService.uploadVIPs(formBean.getVips(), formBean.getVipsFileName(), formBean.getChainStore().getChain_id(), formBean.getOverWrite());
    	} catch (Exception e) {
			response.setQuickValue(Response.ERROR, e.getMessage());
			loggerLocal.error(e);
		}
		
    	if (response.getReturnCode() == Response.WARNING)
    		addActionMessage("导入某些VIP卡发生错误 : " + response.getMessage());
    	else if (response.getReturnCode() != Response.SUCCESS)
    		addActionError(response.getMessage());
    	else if (response.getReturnCode() == Response.SUCCESS)
    		addActionMessage(response.getMessage());
		
		return "UploadVIPs";
	}
	
	/**
	 * 下载vip信息
	 * @return
	 */
	public String downloadVIPs(){
		UserInfor loginUserInfor = (UserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_USER);
		loggerLocal.info("chainVipJSPAction - downloadVIPs");

		HttpServletRequest request = (HttpServletRequest)ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);   
		String contextPath= request.getRealPath("/"); 

		
		Map<String,Object> map= chainVIPService.downloadVIPs(formBean.getVipCard(),contextPath + "WEB-INF\\template\\" + templateFileName);     
 
		excelStream=(InputStream)map.get("stream"); 
		
		return "successful"; 
	}
	
	private Response changeVipCardStatus(int status){
		ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	
	    //1. start the vip card
	    Response response = chainVIPService.changeVIPCardStatus(status, formBean.getSelectedCardId(), userInfor);
	    
	    //2. retain the chain vip type list
	    List<ChainVIPCard> vipCards = chainVIPService.searchVIPCards(formBean.getVipCard(), formBean.getPager());
	    uiBean.setChainVIPCards(vipCards);
	    
	    //3. prepare the ui bean
	    chainVIPService.prepareSearchVIPCardUI(uiBean, userInfor);
	    
	    return response;
	}
	
	/**
	 * 点击vip card号之后，获取一个vip的消费历史记录 近两个月消费记录
	 * @return
	 */
	public String getVIPConsumptionHis(){
		ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
		loggerLocal.info(userInfor.getName() + " : chainVipJSPAction - getVIPConsumptionHis");

		Date today = Common_util.getToday();
		Calendar todayCalendar = Calendar.getInstance();
		todayCalendar.add(Calendar.MONTH, -12);
		Date lastTwoMonth = new Date(todayCalendar.getTimeInMillis());
		
		Response response = chainVIPService.getVIPConsumptionHis(formBean.getChainStore().getChain_id(), formBean.getVipCard(), lastTwoMonth,today,   this.getPage(), this.getRows(), false);
		if (response.getReturnCode() == Response.SUCCESS){
			Map<String, Object> data = (Map<String, Object>)response.getReturnValue();
			List<ChainVIPScore> contents = (List<ChainVIPScore>)data.get("rows");
			List<ChainVIPScore> footers = (List<ChainVIPScore>)data.get("footer");
			
			if (footers != null && footers.size() >0)
				uiBean.setVipConsumpFooter(footers.get(0));
			
			uiBean.setVipConumps(contents);
		}
		return "OpenVIPConsumptionHisPage";

	}
	
	/**
	 * 给vip升级
	 * @return
	 */
	public String preUpgradeVIP(){
		ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
		loggerLocal.info(userInfor.getName() + " : chainVipJSPAction - preUpgradeVIP");
		
		chainVIPService.prepareUpgradeVipUI(uiBean, formBean);
		
		return "upgradeVIP";
	}

	/**
	 * 调整vip的积分
	 * @return
	 */
	public String preUpdateVIPScore(){
		ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
		loggerLocal.info(userInfor.getName() + " : chainVipJSPAction - preUpdateVIPScore");
		
		chainVIPService.prepareUpdateVIPScoreUI(formBean);
		
		return "updateVIPScore";
	}
	
	/**
	 * 准备预存金页面
	 * @return
	 */
	public String preDepositVIPPrepaid(){
		ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
		loggerLocal.info(userInfor.getName() + " : chainVipJSPAction - preDepositVIPPrepaid");

		chainVIPService.prepareDepositVIPPrepaidUI(formBean, uiBean, userInfor);
		
		return "depositVIPPrepaid";
	}
	
	/**
	 * 搜索 vip的预存金
	 * @return
	 */
	public String preSearchVIPPrepaid(){
		ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
		loggerLocal.info(userInfor.getName() + " : chainVipJSPAction - preSearchVIPPrepaid");

		chainVIPService.prepareSearchVIPPrepaidUI(formBean, uiBean, userInfor);
		
		return "searchVIPPrepaid";
	}
}
