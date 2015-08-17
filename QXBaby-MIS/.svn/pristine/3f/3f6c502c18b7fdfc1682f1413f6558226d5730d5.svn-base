package com.onlineMIS.unit_testing;

import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import com.onlineMIS.ORM.DAO.headQ.user.UserInforService;
import com.onlineMIS.action.headQ.user.LoginAction;


public class testSpring {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Resource  resource = new FileSystemResource("F:\\development\\QXBaby-MIS\\WebContent\\WEB-INF\\applicationContext.xml");
		
		XmlBeanFactory factory = new XmlBeanFactory(resource);
		
		UserInforService userInforDaoImpl = (UserInforService)factory.getBean("UserInforService");

		userInforDaoImpl.getAvailableEvaluaters();
	}

}
