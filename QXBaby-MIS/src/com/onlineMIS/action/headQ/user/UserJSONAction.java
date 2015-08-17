package com.onlineMIS.action.headQ.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Controller;
import net.sf.json.JSONObject;
import com.onlineMIS.ORM.entity.headQ.user.UserInfor;
import com.onlineMIS.common.Common_util;
import com.onlineMIS.common.loggerLocal;

import com.opensymphony.xwork2.ActionContext;

@Controller
public class UserJSONAction extends UserAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1155815720822402087L;
	/**
	 * for the function edit parameters
	 */
    private List<Integer> functionalities = new ArrayList<Integer>();
    
	public List<Integer> getFunctionalities() {
		return functionalities;
	}

	public void setFunctionalities(List<Integer> functionalities) {
		this.functionalities = functionalities;
	}

	private Map<String,Object> jsonMap = new HashMap<String, Object>();
	private JSONObject jsonObject;

	public JSONObject getJsonObject() {
		return jsonObject;
	}

	public void setJsonObject(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
	}

    /**
     * to get one user's detail information
     * @return
     */
	public String getUser(){
		loggerLocal.info("UserJSONAction - getUser");
		
		UserInfor user = userInforService.getUser(formBean.getUserInfor().getUser_id(),true);

		jsonMap.put("user", user);

		try{
//			JsonConfig jsonConfig = new JsonConfig();
//			jsonConfig.setExcludes( new String[]{"employeeUnder_Set"} );
//			
		   jsonObject = JSONObject.fromObject(jsonMap);
		} catch (Exception e){
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	
	/**
	 * in user function jsp, when save the user functions
	 * @return
	 */
	public String saveUserFunctions(){
		loggerLocal.info("UserJSONAction - saveUserFunctions");
		
		int user_id = formBean.getUserInfor().getUser_id();
		
		boolean isSuccess = userInforService.updateUserFunctions(user_id, functionalities);

		if (isSuccess)
			jsonMap.put("isSuccess", true);
		else {
			jsonMap.put("isSuccess", false);
		}
		try{
			   jsonObject = JSONObject.fromObject(jsonMap);
			} catch (Exception e){
				e.printStackTrace();
			}
		
		return SUCCESS;
	}
	
	/**
	 * to check whether the user name is used before
	 * @return
	 */
	public String checkUserName(){
		loggerLocal.info("UserJSONAction - checkUserName");
		
		boolean result = userInforService.checkUserName(formBean.getUserInfor());

		jsonMap.put("result", result);
		try{
			   jsonObject = JSONObject.fromObject(jsonMap);
			} catch (Exception e){
				loggerLocal.error(e);
			}
		
		return SUCCESS;
	}
	
	/**
	 * to check the user's session
	 * @return
	 */
	public String checkSession(){
		loggerLocal.info("UserJSONAction - checkSession");
		Object loginUser = ActionContext.getContext().getSession().get(Common_util.LOGIN_USER);

        boolean isExpired = false;
        if (loginUser == null){
        	isExpired = true;
        } else {
        	loggerLocal.info("UserJSONAction - check session " + ((UserInfor)loginUser).getName());
        }
        
		jsonMap.put("sessionID", isExpired);
		try{
			   jsonObject = JSONObject.fromObject(jsonMap);
			} catch (Exception e){
				loggerLocal.error(e);
			}
		
		return SUCCESS;
	}
	
	/**
	 * user update his/her own information
	 * @return
	 */
	public String updateMyAcct(){
		UserInfor loginUser = (UserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_USER);

		formBean.getUserInfor().setUser_id(loginUser.getUser_id());
		if (formBean.getPassword2() != null && !formBean.getPassword2().equals("")){
			System.out.println(formBean.getUserInfor().getPassword());
			System.out.println(loginUser.getPassword());
			if (!formBean.getUserInfor().getPassword().equals(loginUser.getPassword())){
				jsonMap.put("error", "原始密码错误");
				try{
					   jsonObject = JSONObject.fromObject(jsonMap);
					} catch (Exception e){
						loggerLocal.error(e);
					}
				
				return SUCCESS;
			}
		    formBean.getUserInfor().setPassword(formBean.getPassword2());
		}else
			formBean.getUserInfor().setPassword(loginUser.getPassword());
		
		loginUser = userInforService.updateMyAcct(loginUser,formBean.getUserInfor());
		
		ActionContext.getContext().getSession().put(Common_util.LOGIN_USER, loginUser);
		System.out.println(formBean.getUserInfor().getBirthday());
		
		jsonMap.put("success", "成功更新");
		try{
			   jsonObject = JSONObject.fromObject(jsonMap);
			} catch (Exception e){
				loggerLocal.error(e);
			}
		
		return SUCCESS;
	}

}
