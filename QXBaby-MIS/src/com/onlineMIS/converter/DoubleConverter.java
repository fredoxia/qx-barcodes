package com.onlineMIS.converter;

import java.util.Map;   
import org.apache.struts2.util.StrutsTypeConverter;  

import com.onlineMIS.common.loggerLocal;
  
public class DoubleConverter extends StrutsTypeConverter {  
  
    @Override  
    public Object convertFromString(Map context, String[] values, Class toClass) {  
        if (Double.class == toClass || toClass.getName().equalsIgnoreCase("double")) {  
            String doubleStr = values[0];  
            Double d = 0.0;
            if (doubleStr.trim().equals(""))
            	return 0;
            else {
	            try {
	                d = Double.parseDouble(doubleStr);  
	            } catch (Exception e) {
	            	loggerLocal.error("Convert exception : " + doubleStr);
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

