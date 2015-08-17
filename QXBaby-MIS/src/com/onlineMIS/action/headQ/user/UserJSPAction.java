package com.onlineMIS.action.headQ.user;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.struts2.components.UIBean;
import org.apache.struts2.util.ServletContextAware;
import org.springframework.stereotype.Controller;

import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.ORM.entity.chainS.user.ChainUserInfor;
import com.onlineMIS.ORM.entity.headQ.user.SystemLog;
import com.onlineMIS.ORM.entity.headQ.user.UserInfor;
import com.onlineMIS.common.Common_util;
import com.onlineMIS.common.loggerLocal;
import com.opensymphony.xwork2.ActionContext;

@Controller
public class UserJSPAction extends UserAction implements ServletContextAware{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5368542996850921741L;
	private final String CURRENT_LOG="log.log";
	private final int AVAILABLE_DATE = 10;
    private ServletContext context;
	private InputStream logStream;
	private String fileName;

	public InputStream getLogStream() {
		return logStream;
	}

	public void setLogStream(InputStream logStream) {
		this.logStream = logStream;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	

	@SuppressWarnings("unchecked")
	/**
	 * for the admin to edit the user's information
	 * @return
	 */
	public String preEdit(){

	    loggerLocal.info("UserJSPAction - preEdit");
		List<UserInfor> users = userInforService.getAllUsers();
		ActionContext.getContext().put(Common_util.ALL_USER, users);

		return SUCCESS;
	}
	
	/**
	 * admin save the user information
	 * @return
	 */
	public String saveOrUpdate(){
		loggerLocal.info("UserJSPAction - saveOrUpdate");
		
		boolean isSuccess = userInforService.saveOrUpdate(formBean.getUserInfor());
		
		return "preEdit";
	}
	
	/**
	 * admin edit the function
	 * @return
	 */
	public String preEditFunctionality(){
		loggerLocal.info("UserJSPAction - preEditFunctionality");
		
		List<UserInfor> users = userInforService.getAllNormalUsers();
		ActionContext.getContext().put(Common_util.ALL_USER, users);
		
		return "EditFunctionality";
	}
	
	/**
	 * for the people to update their own acct
	 * @return
	 */
	public String preUpdateMyAcct(){
		UserInfor me = (UserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_USER);
		
		formBean.setUserInfor(me);
		
		return "EditMyAcct";
	}
	
	public String logoff(){
		loggerLocal.info("UserJSPAction - logoff");
		
		ActionContext.getContext().getSession().clear();
		
		return "login";
	}
	
	public String logoff4Chain(){
		ActionContext.getContext().getSession().clear();
		
		return "login4Chain";
	}
	
	public String pdaLogoff(){
		loggerLocal.info("UserJSPAction - pdaLogoff");
		
		ActionContext.getContext().getSession().clear();
		
		return "pdaLogin";
	}
	
	/**
	 * to retrieve the log information from the corresponding folder
	 * @return
	 */
	public String retrieveLog(){
		loggerLocal.info("UserJSPAction - retrieveLog");
		
		File[] logs  = new File(context.getInitParameter("logFolder")).listFiles();
		
		List<SystemLog> log_list = new ArrayList<SystemLog>();
	   
		
		for (File log: logs){
			String name = log.getName();
			if (isShown(name)){
				SystemLog systemLog = new SystemLog();
				systemLog.setFileName(log.getName());
				systemLog.setFileSize(log.length()/1000 + "KB");
				log_list.add(systemLog);
			}
		}
		
		ActionContext.getContext().put(Common_util.SYSTEM_LOG_LIST, log_list);
		
		return "viewLog";
	}
	
	/**
	 * function to swith to chain web interface
	 * @return
	 */
	public String swithToChain(){
		UserInfor loginUserInfor = (UserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_USER);
		
		ChainUserInfor chainUserInfor = userInforService.transferHeadUserToChainUser(loginUserInfor);
		
		ActionContext.getContext().getSession().put(Common_util.LOGIN_CHAIN_USER, chainUserInfor);
		
		return "chainStore";
	}

	
	
	/**
	 * ----------------------------------------------------------------
	 * private functions
	 * 
	 * to check whether the file should be shown
	 * @param fileName
	 * @return
	 */
	private boolean isShown(String fileName){
		Calendar today = Calendar.getInstance();

		if (fileName.equalsIgnoreCase(CURRENT_LOG))
			return true;
		else {
			try{
				String fileDate = fileName.split("\\.")[2];
				String[] date  = fileDate.split("-");
				Calendar fileCalendar = Calendar.getInstance();
				fileCalendar.set(Integer.parseInt(date[0]), Integer.parseInt(date[1])-1, Integer.parseInt(date[2]));
				fileCalendar.add(Calendar.DATE, AVAILABLE_DATE);
				
				if (fileCalendar.after(today))
					return true;
			} catch (Exception e) {
				loggerLocal.error(fileName);
				loggerLocal.error(e);
			}
			return false;
		}	
	}
	
	public String downloadLog(){
		String logPath = context.getInitParameter("logFolder") + fileName;
		
		try {
			logStream = new FileInputStream(logPath);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return "logReport";
	}

	@Override
	public void setServletContext(ServletContext arg0) {
		context = arg0;
		
	}
}
