package com.onlineMIS.filter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

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
	
	public static int getQXCategory(){
		String value = SystemParm.getProperty("QX_PURCHASE_CATEGORY_ID");
		if (StringUtils.isEmpty(value))
			return 0;
		return Integer.parseInt(value);
	}
	
	public static Set<Integer> getParmSet(String parmName){
		Set<Integer> parmSet = new HashSet<Integer>();
		String value = getParm(parmName);
		if (!value.equals("")){
			String[] valueArray = value.split(",");
			for (String value2 : valueArray){
				parmSet.add(Integer.parseInt(value2));
			}
		}
			
		return parmSet;
		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
