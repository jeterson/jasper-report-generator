package com.bergamota.jasperreport.enums;

public enum TypeYesNo {

	YES("S"), NO("N");
	private String acronym;
	
	TypeYesNo(String acronym) {
		this.acronym = acronym;
	}
	
	
	public String value() {
		return acronym;
	}
	
	public TypeYesNo fromAcronym(String value) {
		
		for(TypeYesNo t: TypeYesNo.values()) {
			if(value.toLowerCase().equals(t.value()))
				return t;
		}
		
		return TypeYesNo.NO;
	}
	
	
}
