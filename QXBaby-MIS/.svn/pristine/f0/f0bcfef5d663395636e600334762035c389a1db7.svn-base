package com.onlineMIS.converter;

import java.util.Map;   
import org.apache.struts2.util.StrutsTypeConverter;  

import com.onlineMIS.common.loggerLocal;
  
public class IntegerConverter extends StrutsTypeConverter {  
  
    @Override  
    public Object convertFromString(Map context, String[] values, Class toClass) {  
        if (Integer.class == toClass || toClass.getName().equalsIgnoreCase("int")) {  
            String intStr = values[0];  
            Integer d = 0;
            if (intStr.trim().equals(""))
            	return 0;
            else {
	            try {
	                d = Integer.parseInt(intStr);  
	            } catch (Exception e) {
	            	loggerLocal.error("Convert exception : " + intStr);
					loggerLocal.error(e);
				}
	            return d;  
            }
        }  
        return null;
    }  
  
    @Override  
    public String convertToString(Map context, Object o) {   
        return o.toString();  
    }  
}

