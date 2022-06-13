package com.bergamota.jasperreport.utils;

public enum ReportParamType {
	DATE, STRING, INTEGER, DECIMAL, ARRAY,BOOLEAN;

	public static ReportParamType toEnum(String type) {
		type = type.toUpperCase();
		for (ReportParamType p : ReportParamType.values()) {
			if (p.toString().contentEquals(type)) {
				return p;
			}
		}
		return null;
	}
}

