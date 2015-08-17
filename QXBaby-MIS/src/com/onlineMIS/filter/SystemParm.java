package com.onlineMIS.filter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.onlineMIS.common.loggerLocal;

public class SystemParm {

	public static final Properties SystemParm = new Properties();
	
	public static void load() {
		try {
			InputStream is = SystemParm.class.getClassLoader()
					 .getResourceAsStream("sysParms/SystemParm.properties");
			
			SystemParm.load(is);
			
			
		} catch (IOException e) {
			loggerLocal.error(e);
		}
	}
	
	public static String getParm(String parmName){
		String value = SystemParm.getProperty(parmName);
		if (value == null)
			value = "";
		return value.trim();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
