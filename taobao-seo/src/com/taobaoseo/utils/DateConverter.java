package com.taobaoseo.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import com.opensymphony.xwork2.conversion.impl.DefaultTypeConverter;

public class DateConverter extends DefaultTypeConverter{

	private static final DateFormat[] ACCEPT_DATE_FORMATS = {
		new SimpleDateFormat("yyyy-MM-dd HH:mm"),
	};
	
	public Object convertValue(Map<String, Object> context, Object value, Class toType) {
		
		if (toType == Date.class) {
			String[] params = (String[])value;
			String dateString = params[0];
			for (DateFormat format : ACCEPT_DATE_FORMATS) {
				try {
					return format.parse(dateString);
				} catch(Exception e) {
					continue;
				}
			}
			return null;
		}
		else if (toType == String.class) {
			Date date = (Date)value;
			return ACCEPT_DATE_FORMATS[0].format(date);
		}
		return null;
	}
}
