package com.onlineMIS.filter;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.onlineMIS.common.loggerLocal;

public class SystemFunctionHeadQMapping {
	public static final String SPLITER = ",";
	public static final Properties functionMappingProperties = new Properties();
	
	public static void load() {
		try {
			InputStream is = SystemFunctionHeadQMapping.class.getClassLoader()
					 .getResourceAsStream("sysParms/function-mapping-hq.properties");
			
			functionMappingProperties.load(is);
			
			
		} catch (IOException e) {
			loggerLocal.error(e);
		}
	}
	
	/**
	 * to get the function mapping by one mapping id
	 * @param mappingId
	 * @return
	 */
	public static List<String> getFunctionMapping(int mappingId){
		List<String> functionList = new ArrayList<String>();
		
		String functions =  functionMappingProperties.getProperty(String.valueOf(mappingId));
		
		if (functions != null && !functions.trim().equals("")){
			String[] function_Array = functions.split(SPLITER);
			for (int i = 0; i < function_Array.length ; i++){
				functionList.add(function_Array[i].toString().trim());
			}
		}
		
		return functionList;
	}
	
	/**
	 * to get the functions by the mapping ids
	 * @param mappingIds
	 * @return
	 */
	public static List<String> getFunctionMapping(List<Integer> mappingIds){
		List<String> functionList = new ArrayList<String>();
		
		for (Integer mappingId: mappingIds){
			List<String> functions = getFunctionMapping(mappingId);
			functionList.addAll(functions);
		}
		
		return functionList;
	}

}
