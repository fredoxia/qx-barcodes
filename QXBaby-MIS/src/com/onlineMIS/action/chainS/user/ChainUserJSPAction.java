package com.onlineMIS.action.chainS.user;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.onlineMIS.ORM.DAO.Response;
import com.onlineMIS.ORM.DAO.chainS.report.ChainReportService;
import com.onlineMIS.ORM.DAO.headQ.user.NewsService;
import com.onlineMIS.ORM.DAO.headQ.user.UserInforService;
import com.onlineMIS.ORM.entity.base.Pager;
import com.onlineMIS.ORM.entity.chainS.report.ChainWMRank;
import com.onlineMIS.ORM.entity.chainS.user.ChainUserInfor;
import com.onlineMIS.ORM.entity.headQ.user.News;
import com.onlineMIS.common.Common_util;
import com.onlineMIS.common.QXMsgManager;
import com.onlineMIS.common.loggerLocal;
import com.opensymphony.xwork2.ActionContext;

public class ChainUserJSPAction extends ChainUserAction{

	private static final long serialVersionUID = 1L;
	 
	@Autowired
	private NewsService newsService;
	
	@Autowired
	private ChainReportService chainReportService;
	
//	/**
//	 * after login, the user need select the chain store to continue
//	 * 
//	 * @return
//	 */
//	public String selectChainStore(){
//		ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
//    	
//		int chainStoreId = formBean.getChainUserInfor().getChainStore().getChain_id();
//		
//		boolean valid = userInfor.belongToChain(chainStoreId);
//
//		if (valid){
//			ChainStore chainStore = chainStoreService.getChainStoreByID(chainStoreId);
//			userInfor.setChainStore(chainStore);
//			ActionContext.getContext().getSession().put(Common_util.LOGIN_CHAIN_USER,userInfor);
//			
//			//prepare the news
//			uiBean.setNews(newsService.getNews(News.TYPE_CHAIN_S));
//			return "BoardNews";
//		} else {
//			addActionError("所选连锁店不在你范围之类，请重新选择");
//			return "login_chain";
//		}
//	}
	
	
	/**
	 * 用户成功登陆之后
	 * @return
	 */
	public String getNews(){
    	ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"");
    	
		//1. 准备千禧消息
		uiBean.setNews(newsService.getNews(News.TYPE_CHAIN_S));
		
		//2. 准备特别信息，比如会员日加倍积分
//		Date today = Common_util.getToday();
//		if (today.getDate() == Common_util.VIP_DATE)
//			uiBean.setSpecialMsg(QXMsgManager.getMsg("VIP_DATE_MSG"));
		
		//3. 准备每周排名信息
		Response response = new Response();
		try {
		     response = chainReportService.getRank(userInfor);
		     if (response.getReturnCode() == Response.SUCCESS){
		    	 List<Object> returnValue = (List<Object>)response.getReturnValue();
		    	 ChainWMRank chainWMRank = (ChainWMRank)returnValue.get(0);
		    	 uiBean.setChainWMRank(chainWMRank);
		    	 
		    	 List<ChainWMRank> myRank = (List<ChainWMRank>)returnValue.get(1);
		    	 uiBean.setMyDailyRank(myRank);
		     }
		} catch (Exception e) {
			loggerLocal.error(e);
		}
		
		return "BoardNews";
	}

	/**
	 * this function is triggered when user clicks the head menu of "员工信息管理"
	 * @return
	 */
    public String getChainUsers(){
    	ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"");
    	
    	uiBean.setChainUserInfors(chainUserInforService.getChainUsers(userInfor, formBean.getPager()));
    	
    	return "ChainUserInforList";
    }
    
    /**
     * get the chain user by user id
     * @return
     */
    public String getChainUserByIDForUpdate(){
    	ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"");
    	
    	int user_id = formBean.getChainUserInfor().getUser_id();
    	
    	ChainUserInfor chainUserInfor = chainUserInforService.getChainUserByID(userInfor, user_id);
    	
    	if (chainUserInfor == null){
    		addActionError("无法获取对应连锁用户信息,请联系系统管理员!");
    	} else {
    		//prepare the chain store list
            chainUserInforService.prepareChainUserUpdateUI(uiBean, userInfor, chainUserInfor);
            
            //set the default value
            formBean.getChainUserInfor().setMyChainStore(chainUserInfor.getMyChainStore());
            formBean.getChainUserInfor().getRoleType().setChainRoleTypeId(chainUserInfor.getRoleType().getChainRoleTypeId());
    	}
    	
    	return "ChainUserInforWindow";
    }
    
    /**
     * click the button to open the window to add chain user
     * @return
     */
    public String preAddChainUser(){
    	ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"");
    	
        chainUserInforService.prepareChainUserUpdateUI(uiBean, userInfor, null);
        
        return "ChainUserInforWindow";
    }
    
    /**
     * to save and update user
     * @return
     */
    public String saveUpdateUser(){
    	ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"");
    	
    	ChainUserInfor user = formBean.getChainUserInfor();
    	
    	//error validation
    	//1. user name is duplicated?
    	if (!chainUserInforService.validateChainUsername(user)){
    		addActionError("系统用户名有重复，请重新输入");
    		return "ChainUserInforWindow";
    	} 
    		
    	chainUserInforService.saveUpdateUser(userInfor, user);
    	
    	return "updateSuccess";
    }
    
    /**
     * the admin swith back to head quarter interface
     * @return
     */
    public String switchToHeadq(){
    	ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"");
    	
    	return "headqInterface";
    }
    
    /**
     * to logoff the system
     * @return
     */
    public String logoff(){

		ActionContext.getContext().getSession().clear();
		
		return "login_chain";
    }
    
    /**
     * to go to set the user function
     * @return
     */
    public String preMgmtUserFunction(){
    	ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"");
    	
    	chainUserInforService.prepareChainUserFunUI(uiBean);
    	
    	return "editChainUserFunction";
    }
    
    /**
     * to edit my account
     * @return
     */
    public String editMyAcct(){
    	ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"");
    	
    	ChainUserInfor user = formBean.getChainUserInfor();
    		
    	Response response = chainUserInforService.saveUpdateMyAcct(userInfor, user, formBean.getPassword());
    	if (response.getReturnCode() == Response.FAIL){
    		addActionError(response.getMessage());
    		return preEditMyAcct();
    	} else if (response.getReturnCode() == Response.SUCCESS){
    		addActionMessage("成功更新");
    		return preEditMyAcct();
    	} else {
    		addActionError("系统遇到错误");
    		return preEditMyAcct();
    	}
    }
    
    /**
     * use edit my acctount
     * @return
     */
	public String preEditMyAcct() {
    	ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"");
    	
		formBean.setChainUserInfor(userInfor);
		
		return "editMyAcct";
	}



}
