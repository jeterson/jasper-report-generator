package com.bergamota.jasperreport.services.base.interfaces;

import com.bergamota.jasperreport.model.ReportProperties;

import net.sf.jasperreports.engine.JasperPrint;

public interface ExportPDF {

	/**
	 * 
	 * @param properties
	 * @param jasperPrint
	 * @return path of file exported
	 */
	String export(ReportProperties properties, JasperPrint jasperPrint);
}
