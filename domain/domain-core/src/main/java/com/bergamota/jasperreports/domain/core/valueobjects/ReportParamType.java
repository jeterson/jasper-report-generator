package com.bergamota.jasperreports.domain.core.valueobjects;

public enum ReportParamType {
	DATE("java.util.Date"),
	STRING("java.lang.String"),
	INTEGER("java.lang.Integer"),
	DECIMAL("java.lang.Double"),
	ARRAY("java.util.List"),
	BOOLEAN("java.lang.Boolean");

	private String classz;

	ReportParamType(String classz) {
		this.classz = classz;
	}

	public static ReportParamType toEnum(String type) {
		type = type.toUpperCase();
		for (ReportParamType p : ReportParamType.values()) {
			if (p.toString().contentEquals(type)) {
				return p;
			}
			if(p.classz.toUpperCase().contentEquals(type)){
				return p;
			}
		}
		return null;
	}
}

