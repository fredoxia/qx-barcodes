package com.onlineMIS.action.headQ.user;


import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import net.sf.json.JSONObject;
import com.onlineMIS.ORM.DAO.Response;
import com.onlineMIS.ORM.DAO.headQ.user.UserInforService;
import com.onlineMIS.ORM.entity.headQ.user.UserFunctionality;
import com.onlineMIS.ORM.entity.headQ.user.UserInfor;
import com.onlineMIS.action.BaseAction;
import com.onlineMIS.common.Common_util;
import com.onlineMIS.common.loggerLocal;
import com.onlineMIS.filter.SystemParm;
import com.opensymphony.xwork2.ActionContext;

@Controller
public class LoginAction extends BaseAction{

	private static final long serialVersionUID = 1L;
	@Autowired
	private UserInforService userInforService;

	private UserInfor user = new UserInfor();
	private boolean isAdmin;
    private Map<String,Object> jsonMap = new HashMap<String, Object>();
	private JSONObject jsonObject;

	public JSONObject getJsonObject() {
		return jsonObject;
	}

	public void setJsonObject(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
	}
	public boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public UserInfor getUser() {
		return user;
	}

	public void setUser(UserInfor user) {
		this.user = user;
	}

	public String myTask(){
		return "myTasks";
	}

	
	/**
	 * login for jsp head quarter user login
	 */
	public String login(){
		String userName = user.getUser_name();
		String password = user.getPassword();
		
		Response response = userInforService.validateUser(userName, password);
		
		if (response.getReturnCode() == Response.SUCCESS){
			UserInfor user = (UserInfor)response.getReturnValue();
			userInforService.setFunctions(user);
			ActionContext.getContext().getSession().put(Common_util.LOGIN_USER, user);
			
			/**
			 * 直接login到客户端
			 * 1: admin
			 * 2: non-admin
			 */
			if (isAdmin && user.containFunction(UserInfor.SWITCH_CHAIN_FUNCTION)){
				response.setReturnValue(1);
			} else 
				response.setReturnValue(2);	
		}
		
		jsonMap.put("response", response);
		
		try{
			   jsonObject = JSONObject.fromObject(jsonMap);
			} catch (Exception e){
				loggerLocal.error(e);
			}
		
		return SUCCESS;
	}
	

	
	/**
	 * login for json, head quarter user login
	 */
	public String reLogin(){
		String userName = user.getUser_name();
		String password = user.getPassword();
		
		Response response = userInforService.validateUser(userName, password);
		
		if (response.getReturnCode() != Response.SUCCESS){
			jsonMap.put("error", response.getMessage());
		} else {
			UserInfor user = (UserInfor)response.getReturnValue();
			userInforService.setFunctions(user);
			ActionContext.getContext().getSession().put(Common_util.LOGIN_USER, user);
			response.setReturnValue(null);
		}
		
		try{
			   jsonObject = JSONObject.fromObject(jsonMap);
			} catch (Exception e){
				loggerLocal.error(e);
			}
		
		return SUCCESS;
	}	
	
	/**
	 * this is the login from PDA
	 * @return
	 */
	public String PDALogin(){
		int userId = user.getUser_id();
		String password = user.getPassword();

		Response response = userInforService.validateUser(userId, password);
		
		if (response.getReturnCode() == Response.SUCCESS){
			UserInfor user = (UserInfor)response.getReturnValue();
			if (!user.containFunction(UserFunctionality.PDA_FUNCTION) ){
				response.setQuickValue(Response.FAIL, "你的账户没有开通PDA开单功能");
			} else {
				userInforService.setFunctions(user);
				ActionContext.getContext().getSession().put(Common_util.LOGIN_USER, user);
			}
			response.setReturnValue(null);
		}
		
		jsonMap.put("response", response);
		
		try{
			   jsonObject = JSONObject.fromObject(jsonMap);
			} catch (Exception e){
				loggerLocal.error(e);
			}
		
		return SUCCESS;
	}
	
	/**
	 * 条码制作时客户登陆用
	 * @return
	 */
	public String login4Chain(){
		String userName = user.getUser_name();
		String password = user.getPassword();
		
		Response response = userInforService.validateUser4Chain(userName, password);
		if (response.getReturnCode() == Response.SUCCESS)
			ActionContext.getContext().getSession().put(Common_util.LOGIN_CHAIN_USER, response.getReturnValue());
		
		jsonMap.put("response", response);
		
		try{
			   jsonObject = JSONObject.fromObject(jsonMap);
			} catch (Exception e){
				loggerLocal.error(e);
			}
		
		return SUCCESS;
	}

}