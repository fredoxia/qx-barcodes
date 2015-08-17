package com.onlineMIS.filter;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.onlineMIS.common.QXMsgManager;
import com.onlineMIS.common.loggerLocal;

/**
 * Application Lifecycle Listener implementation class SystemParmLoader
 *
 */
public class SystemParmLoader implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public SystemParmLoader() {
    	loggerLocal.info("Starting the SystemParmLoader ...");
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0) {
    	loggerLocal.info("Start loading the headq function mapping ...");
        SystemFunctionHeadQMapping.load();
        
        loggerLocal.info("Start loading the headq default function mapping ...");
        DefaultFunctionHeadQ.load();
        
        loggerLocal.info("Start loading the chain function mapping ...");
        SystemFunctionChainMapping.load();
        
        loggerLocal.info("Start loading the chain default function mapping ...");
        DefaultFunctionChain.load();
        
        loggerLocal.info("Start loading the System parm ...");
        SystemParm.load();
        
        loggerLocal.info("Start loading the message ...");
        QXMsgManager.load();
        
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0) {
        // TODO Auto-generated method stub
    }
	
}
