package com.onlineMIS.converter;

import java.util.Map;

import org.apache.struts2.util.StrutsTypeConverter;

import com.onlineMIS.common.Common_util;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import freemarker.log.Logger;
/**
 * class to do the date converter for the IBM date picker
 * @author fredo
 *
 */
public class DateSQLConverter extends StrutsTypeConverter {
	private static final Logger LOGGER=Logger.getLogger(DateSQLConverter.class.getName());

	@SuppressWarnings("deprecation")
	@Override
	public Object convertFromString(Map map, String[] string, Class convertClass) {
		String value = string[0];

		if (value != null && !value.trim().equals("")){
			Matcher match = null;
			match = Common_util.pattern.matcher(value);
			try {
				if (match.find()){
					Date date = new Date(Common_util.dateFormat.parse(value).getTime());
	
					return date;
				}  else {
					match = Common_util.pattern_f.matcher(value);
					if (match.find()){
						Date date = new Date(Common_util.dateFormat_f.parse(value).getTime());
						return date;
					} 
				}
			} catch (ParseException e) {
				e.printStackTrace();
				LOGGER.error("DateSQL Convertion error on " + value, e);
			}
			
			Date transferDate = null;
			try {
				transferDate = new Date((new java.util.Date(value)).getTime());
			} catch (Exception e) {
				e.printStackTrace();
				LOGGER.error("DateSQL Convertion error on " + value, e);
			}
			return transferDate;
		} else {
			return null;
		}
	}

	@Override
	public String convertToString(Map map, Object convertObject) {

		if (convertObject != null){
			Date date = (Date) convertObject;
	
			return Common_util.dateFormat.format(date);
		} else{
			return "";
		}
	}

}

