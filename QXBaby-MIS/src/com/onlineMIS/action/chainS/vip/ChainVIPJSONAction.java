package com.onlineMIS.action.chainS.vip;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.onlineMIS.ORM.DAO.Response;
import com.onlineMIS.ORM.entity.chainS.chainMgmt.ChainStoreConf;
import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.ORM.entity.chainS.user.ChainUserInfor;
import com.onlineMIS.ORM.entity.chainS.vip.ChainVIPCard;
import com.onlineMIS.ORM.entity.chainS.vip.ChainVIPType;
import com.onlineMIS.common.Common_util;
import com.onlineMIS.common.loggerLocal;
import com.onlineMIS.converter.JSONSQLDateConverter;
import com.onlineMIS.converter.JSONUtil2SQLDateConverter;
import com.onlineMIS.converter.JSONUtilDateConverter;
import com.opensymphony.xwork2.ActionContext;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

public class ChainVIPJSONAction extends ChainVIPAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1267884643402430105L;
	private JSONObject jsonObject;
	private Map<String,Object> jsonMap = new HashMap<String, Object>();

	public JSONObject getJsonObject() {
		return jsonObject;
	}
	public void setJsonObject(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
	}
	
	/**
	 * save and update the chain vip type
	 * @return
	 */
	public String saveUpdateVIPType(){
    	ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"saveUpdateVIPType");
    	
		ChainVIPType vipType = formBean.getVipType();
		
		Response validateResponse = chainVIPService.validateChainVIPType(vipType);
		int isSuccess = 0;
		String error = "";
		if (validateResponse.getReturnCode() == Response.SUCCESS){
			chainVIPService.saveOrupdateVIPType(vipType);
			isSuccess =1;
		} else {
			error = validateResponse.getMessage();
		}
		
		jsonMap.put("isSuccess", isSuccess);
		jsonMap.put("error", error);
		
		try{
		   jsonObject = JSONObject.fromObject(jsonMap);
		} catch (Exception e){
			loggerLocal.chainActionError(userInfor,this.getClass().getName()+ "."+"saveUpdateVIPType");
			loggerLocal.error(e);
		}
		
		return SUCCESS;
	}
	
	/**
	 * save and update the chain vip card
	 * @return
	 */
	public String saveUpdateVIPCard(){
    	ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"saveUpdateVIPCard");
    	
		ChainVIPCard vipCard = formBean.getVipCard();
		
		Response validateResponse = chainVIPService.validateChainVIPCard(vipCard,userInfor);
		int isSuccess = 0;
		String error = "";
		if (validateResponse.getReturnCode() == Response.SUCCESS){
			chainVIPService.saveOrupdateVIPCard(vipCard);
			isSuccess =1;
		} else {
			error = validateResponse.getMessage();
		}
		
		jsonMap.put("isSuccess", isSuccess);
		jsonMap.put("error", error);
		
		try{
		   jsonObject = JSONObject.fromObject(jsonMap);
		} catch (Exception e){
			loggerLocal.chainActionError(userInfor,this.getClass().getName()+ "."+"saveUpdateVIPCard");
			loggerLocal.error(e);
		}		
		return SUCCESS;
	}
	
	/**
	 * trigger by user entering the VIP number and search it
	 * @return
	 */
	public String getVIPCard(){
    	ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"getVIPCard");
    	
		String vipCardNo = formBean.getVipCard().getVipCardNo();
		ChainVIPCard vipCard = chainVIPService.getVIPCardByCardNo(vipCardNo);
		
		if (vipCard != null && vipCard.getStatus() == ChainVIPCard.STATUS_GOOD){
		    List<Double> results = chainVIPService.getVIPCardTotalScore(vipCard.getId());
		    jsonMap.put("totalScore", results.get(0));
	        jsonMap.put("totalCash", results.get(1));
		}
	    
	    jsonMap.put("vipCard", vipCard);

		
		//to excludes the set and list inforamtion
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes( new String[]{"issueChainStore"} );
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JSONUtilDateConverter());  
		jsonConfig.registerJsonValueProcessor(java.sql.Date.class, new JSONSQLDateConverter());  
		try{
			   jsonObject = JSONObject.fromObject(jsonMap,jsonConfig);
//			   System.out.println(jsonObject.toString());
			} catch (Exception e){
				loggerLocal.chainActionError(userInfor,this.getClass().getName()+ "."+"getVIPCard");
				loggerLocal.error(e);
			}
		
		
		return SUCCESS;
	}
	
	/**
	 * 在搜索页面查找VIP，主要查找当日生日，当月生日，当周生日这类
	 * @return
	 */
	public String searchSpecialVIPs(){
    	ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"searchSpecialVIPs");
    	
		Response response = chainVIPService.searchSpecialVIPs(formBean.getChainStore().getChain_id(), formBean.getSearchType(), this.getPage(), this.getRows());

	    jsonMap = (Map)response.getReturnValue();

		//to excludes the set and list inforamtion
		JsonConfig jsonConfig = new JsonConfig();
		//jsonConfig.setExcludes( new String[]{"issueChainStore"} );
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JSONUtil2SQLDateConverter());  

		try{
			   jsonObject = JSONObject.fromObject(jsonMap,jsonConfig);
//			   System.out.println(jsonObject.toString());
		   } catch (Exception e){
				loggerLocal.chainActionError(userInfor,this.getClass().getName()+ "."+"searchSpecialVIPs");
				loggerLocal.error(e);
			}

		return SUCCESS;
	}
	
    /**
     * vip升级
     * @return
     */
	public String upgradeVIP(){
    	ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"upgradeVIP");

		Response response = new Response();
		
		try {
			response = chainVIPService.upgradeVipCard(formBean.getVipCard(), formBean.getVipScore());
		} catch (Exception e) {
			response.setReturnCode(Response.FAIL);
			response.setMessage(e.getMessage());
		}

	    jsonMap.put("response", response);

		try {
			jsonObject = JSONObject.fromObject(jsonMap);
//			System.out.println(jsonObject.toString());
		} catch (Exception e) {
			loggerLocal.chainActionError(userInfor,this.getClass().getName()+ "."+"upgradeVIP");
			loggerLocal.error(e);
		}

		return SUCCESS;
	}

	/**
	 * 调整vip积分
	 * @return
	 */
	public String updateVipScore(){
    	ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"updateVipScore");

		Response response = new Response();
		
		try {
			response = chainVIPService.updateVipScore(formBean.getVipCard(), formBean.getVipScore(), formBean.getComment());
		} catch (Exception e) {
			response.setReturnCode(Response.FAIL);
			response.setMessage(e.getMessage());
		}

	    jsonMap.put("response", response);

		try{
			   jsonObject = JSONObject.fromObject(jsonMap);
			   System.out.println(jsonObject.toString());
			} catch (Exception e){
				loggerLocal.chainActionError(userInfor,this.getClass().getName()+ "."+"updateVipScore");
				loggerLocal.error(e);
			}
		
		
		return SUCCESS;
	}
	
	/**
	 * 在预付中
	 * @return
	 */
	public String getVIPCardVIPPrepaid(){
	 	ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"getVIPCardVIPPrepaid");
    	
		String vipCardNo = formBean.getVipCard().getVipCardNo();
		Response response = chainVIPService.getVIPCardVIPPrepaid(vipCardNo,formBean.getChainStore());
	
	    
	    jsonMap.put("response", response);

		
		//to excludes the set and list inforamtion
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes( new String[]{"issueChainStore"} );
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JSONUtilDateConverter());  
		jsonConfig.registerJsonValueProcessor(java.sql.Date.class, new JSONSQLDateConverter());  
		try{
			   jsonObject = JSONObject.fromObject(jsonMap,jsonConfig);
//			   System.out.println(jsonObject.toString());
			} catch (Exception e){
				loggerLocal.chainActionError(userInfor,this.getClass().getName()+ "."+"getVIPCard");
				loggerLocal.error(e);
			}
		
		
		return SUCCESS;
	}

}
