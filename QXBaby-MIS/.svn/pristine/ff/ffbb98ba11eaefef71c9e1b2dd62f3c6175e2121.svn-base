package com.onlineMIS.converter;



import java.text.SimpleDateFormat;
import java.sql.Date;

import com.onlineMIS.common.Common_util;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

public class JSONUtil2SQLDateConverter implements JsonValueProcessor {
	
	public JSONUtil2SQLDateConverter() {
		super();
	}

	public JSONUtil2SQLDateConverter(String format) {
		super();
	}

	@Override
	public Object processArrayValue(Object value, JsonConfig jsonConfig) {
		String[] obj = {};
		if (value instanceof Date[]) {
			SimpleDateFormat sf = Common_util.dateFormat;
			Date[] dates = (Date[]) value;
			obj = new String[dates.length];
			for (int i = 0; i < dates.length; i++) {
				obj[i] = sf.format(dates[i]);
			}
		}
		return obj;
	}

	@Override
	public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
		if (value == null)
			value="";
		else if (value instanceof java.sql.Date || value instanceof java.util.Date) {
			String str =  Common_util.dateFormat.format((Date) value);
			return str;
		}
		return value.toString();
	}
}
