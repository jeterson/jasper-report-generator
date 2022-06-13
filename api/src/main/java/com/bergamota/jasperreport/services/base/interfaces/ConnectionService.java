package com.bergamota.jasperreport.services.base.interfaces;

import java.sql.Connection;

import com.bergamota.jasperreport.entities.ReportConnection;

public interface ConnectionService {

	Connection getConnection(ReportConnection connection);
}
