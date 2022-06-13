package com.bergamota.jasperreport.components.types;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConversionResponse<T> {

	private boolean ok;
	private T value;
	
	public ConversionResponse(boolean ok) {
		this(ok, null);
	}
		
}
