package com.bergamota.jasperreport.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.bergamota.jasperreport.exceptions.ReportParameterException;
import com.bergamota.jasperreport.model.ReportParameter;
import com.bergamota.jasperreport.model.ReportProperties;
import com.bergamota.jasperreport.services.base.interfaces.ReportConfigService;

import lombok.SneakyThrows;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.export.SimplePdfReportConfiguration;

public class ReportParamsUtil {

	private static SimpleDateFormat dateFormat;
	public static final String separator = System.getProperty("file.separator");
	public static final String userHome = System.getProperty("user.home");

	public static Map<String, Object> toMap(List<ReportParameter> parameters) {

		var params = new HashMap<String, Object>();

		parameters.forEach(p -> {
			var entrySet = convertToEntryMap(p);
			params.put(entrySet.getKey(), entrySet.getValue());
		});

		return params;

	}
		
	
	

	private static Entry<String, Object> convertToEntryMap(ReportParameter parameter) {
		var value = parameter.getValue();
		var name = parameter.getName();
		var type = parameter.getReportType();

		if (parameter.getType() == ReportParamType.DATE) {
			if(parameter.getDatePattern() == null || parameter.getDatePattern().isBlank()) 
				throw new ReportParameterException(String.format("Parameter %s is a dateType, but not contain a pattern of date", parameter.getName()), null);


			if(parameter.getReportType() == ReportParamType.DATE) {
				if(isDate(parameter.getValue(), parameter.getDatePattern()))
					return createEntrySet(parameter.getName(), toDate(parameter.getValue(), parameter.getDatePattern()));
				else
					return createEntrySet(parameter.getName(), null);				
			}else {
				if(isDate(parameter.getValue(), parameter.getDatePattern()))
					return createEntrySet(parameter.getName(), String.valueOf(parameter.getValue()));
				else
					return createEntrySet(parameter.getName(), null);
			}

		}else if(type == ReportParamType.INTEGER) {
			if(isInt(value))
				return createEntrySet(name, toInt(value));
			else
				return createEntrySet(name, null);
		}else if(type == ReportParamType.DECIMAL) {
			if(isDecimal(value))
				return createEntrySet(name, toDecimal(value));
			else
				return createEntrySet(name, null);
		}else if(type == ReportParamType.STRING) {
			return createEntrySet(name, toStr(value));
		}else if(type == ReportParamType.ARRAY) {
			var x = toCollection(value);
			return createEntrySet(name, toCollection(value));
		} else if(type == ReportParamType.BOOLEAN) {
			return createEntrySet(name, toYesNo(value));
		}
		else {
			return createEntrySet(name, toStr(value));
		}
	}

	private static List<?> toCollection(Object value) {
		try {
			return (List<?>) value;
		}catch (Exception e) {
			return Arrays.asList(value);
		}
	}

	private static Double toDecimal(Object value) {
		return Double.parseDouble(toStr(value));
	}

	private static boolean isDecimal(Object value) {
		try {
			toDecimal(value);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private static boolean toBoolean(Object value) {		
			return String.valueOf(value).equalsIgnoreCase("false") ? false : true;		
	}
	
	private static String toYesNo(Object value) {		
		return toBoolean(value) ? "N" : "S";		
}

	private static boolean isDate(Object date, String pattern) {		
		try {
			dateFormat = new SimpleDateFormat(pattern);
			dateFormat.parse(String.valueOf(date));
			return true;
		}catch (Exception e) {
			return false;
		}finally {
			dateFormat = null;
		}
	}

	@SneakyThrows
	private static Date toDate(Object date, String pattern) {
		dateFormat = new SimpleDateFormat(pattern);
		var dateParsed = dateFormat.parse(String.valueOf(date));
		dateFormat = null;
		return dateParsed;
	}

	private static String toStr(Object value) {
		return String.valueOf(value);
	}

	private static boolean isInt(Object value) {
		try {
			Integer.parseInt(toStr(value));
			return true;
		}catch (Exception e) {
			return false;
		}
	}

	private static int toInt(Object value) {
		return Integer.parseInt(toStr(value));
	}


	private static Entry<String, Object> createEntrySet(String key, Object value) {
		return new Entry<String, Object>() {


			@Override
			public Object setValue(Object value) {
				// TODO Auto-generated method stub
				return value;
			}

			@Override
			public Object getValue() {
				// TODO Auto-generated method stub
				return value;
			}

			@Override
			public String getKey() {
				// TODO Auto-generated method stub
				return key;
			}
		};
	}
}
