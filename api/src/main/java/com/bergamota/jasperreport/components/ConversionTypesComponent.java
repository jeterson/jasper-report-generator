package com.bergamota.jasperreport.components;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.bergamota.jasperreport.components.types.ConversionResponse;
import com.bergamota.jasperreport.enums.TypeYesNo;

@Component
public class ConversionTypesComponent {
	
	private SimpleDateFormat simpleDateFormat;	
	
	public  List<?> toCollection(Object value) {
		try {
			return (List<?>) value;
		}catch (Exception e) {
			return Arrays.asList(value);
		}
	}
	
	public ConversionResponse<Double> parseDobule(Object value) {
		try {
			return new ConversionResponse<Double>(true, Double.parseDouble(parseString(value))); 
		}catch (Exception e) {
			return new ConversionResponse<Double>(false);
		}
	}
	
	public ConversionResponse<Integer> parseInteger(Object value) {
		try {
			return new ConversionResponse<Integer>(true, Integer.parseInt(parseString(value))); 
		}catch (Exception e) {
			return new ConversionResponse<Integer>(false);
		}
	}
	
	public ConversionResponse<Boolean> parseBoolean(Object value) {
		try {
			var str = parseString(value).toLowerCase();
			
			if(value instanceof TypeYesNo)
				str = ((TypeYesNo) value).value();
			
			if(str.equals("s") || str.equals("y") || str.equals("1") || str.equals("sim") || str.equals("yes"))
				str = "true";
			else
				str = "false";
			
			return new ConversionResponse<Boolean>(true, Boolean.parseBoolean(str)); 
		}catch (Exception e) {
			return new ConversionResponse<Boolean>(false);
		}
	}
	
	public ConversionResponse<TypeYesNo> parseToYesNo(Object value) {
		var cr = parseBoolean(value);
		return new ConversionResponse<TypeYesNo>(cr.isOk(), cr.getValue().equals(true) ? TypeYesNo.YES : TypeYesNo.NO);				
	}
	
	
	public ConversionResponse<Date> parseDateUtil(Object value, String format) {
		simpleDateFormat = new SimpleDateFormat(format);
		try {
			return new ConversionResponse<Date>(true, simpleDateFormat.parse(parseString(value)));
		}catch (Exception e) {
			return new ConversionResponse<Date>(false);
		}
	}
	
	public ConversionResponse<LocalDate> parseLocalDate(Object value, String format) {
		final DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
		try {
			return new ConversionResponse<LocalDate>(true, LocalDate.parse(parseString(value), dtf));
		}catch (Exception e) {
			return new ConversionResponse<LocalDate>(false);
		}
	}
	
	public ConversionResponse<String> formatLocalDate(LocalDate value, String format) {
		final DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
		try {
			return new ConversionResponse<String>(true, dtf.format(value));
		}catch (Exception e) {
			return new ConversionResponse<String>(false);
		}
	}
	
	public ConversionResponse<LocalDate> dateToLocalDate(Date value, String format) {
		var dateStr = formatDateUtil(value, format);		
		var localDate = parseLocalDate(dateStr.getValue(), format);
		return new ConversionResponse<LocalDate>(localDate.isOk(), localDate.getValue());
	}
	
	public ConversionResponse<Date> localDateToDate(LocalDate value, String format) {
		var dateStr = formatLocalDate(value, format);		
		var date = parseDateUtil(dateStr.getValue(), format);
		return new ConversionResponse<Date>(date.isOk(), date.getValue());
	}
	
	public ConversionResponse<String> formatDateUtil(Date value, String format) {
		simpleDateFormat = new SimpleDateFormat(format);
		try {
			return new ConversionResponse<String>(true, simpleDateFormat.format(value));
		}catch (Exception e) {
			return new ConversionResponse<String>(false);
		}
	}
	
	
	public String parseString(Object value) {
		return String.valueOf(value);
	}
	
}
