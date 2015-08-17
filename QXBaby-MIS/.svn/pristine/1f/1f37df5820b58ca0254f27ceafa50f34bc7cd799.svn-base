package com.onlineMIS.common;

import org.apache.log4j.Logger;

import com.onlineMIS.ORM.entity.chainS.user.ChainUserInfor;

public class loggerLocal{

    private static Logger loggerProxy = Logger.getLogger("normalLogger"); 
    private static Logger batchLoggerProxy = Logger.getLogger("batchLogger"); 

    /**
     * for the normal logger
     * @param userInfor
     * @param method
     */
    public static void chainActionInfo(ChainUserInfor userInfor, String method){
    	loggerProxy.info("*Action*:" +userInfor.getMyChainStore().getChain_name() + ","+userInfor.getMyChainStore().getChain_id() + "," + userInfor.getUser_name() + "," + method);
    }
    
    public static void chainActionError(ChainUserInfor userInfor, String method){
    	loggerProxy.error("*Error*:" +userInfor.getMyChainStore().getChain_name() +  "," + userInfor.getMyChainStore().getChain_id() + "," + userInfor.getUser_name() + "," + method);
    }
	
	public static void info(String logInfor){
		loggerProxy.info(logInfor);
	}
	/**
	 * 在某些动作做完了之后，回调的log
	 * @param logInfor
	 */
	public static void infoR(String logInfor){
		loggerProxy.info("R - " + logInfor);
	}
	
	public static void warn(String logInfor){
		loggerProxy.warn(logInfor);
	}
	
	public static void error(Exception e){
		StackTraceElement[] eles = e.getStackTrace();
		loggerLocal.error(e.getMessage());
		for (StackTraceElement ele: eles)
			loggerLocal.error(ele.toString());
	}

	public static void error(String logInfor){
		loggerProxy.error(logInfor);
	}
	
	/**
	 * for the batch logger
	 */
	public static void infoB(String logInfor){
		batchLoggerProxy.info(logInfor);
	}
	
	public static void warnB(String logInfor){
		batchLoggerProxy.warn(logInfor);
	}
	
	public static void errorB(Exception e){
		StackTraceElement[] eles = e.getStackTrace();
		loggerLocal.errorB(e.getMessage());
		for (StackTraceElement ele: eles)
			loggerLocal.errorB(ele.toString());
	}

	public static void errorB(String logInfor){
		batchLoggerProxy.error(logInfor);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		loggerLocal log = new loggerLocal();
		loggerLocal.info("dfdfdfdf");

	}

}
