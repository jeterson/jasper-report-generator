package com.bergamota.jasperreport.services.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.bergamota.jasperreport.entities.ReportConnection;
import com.bergamota.jasperreport.exceptions.ConnectionException;
import com.bergamota.jasperreport.services.base.interfaces.ConnectionService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConnectionServiceImpl implements ConnectionService {

    @Override
    public Connection getConnection(ReportConnection reportConnection) {
        if (reportConnection == null)
            return null;

        log.info("Trying get connection of {}-{}", reportConnection.getDatabase(), reportConnection.getName());
        var connection = createConnection(reportConnection.getUsername(), reportConnection.getPassword(), reportConnection.getConnectionUrl(), reportConnection.getDatabase().getDriver());
        log.info("Connection retrieved successfully");
        return connection;
    }


    private Connection createConnection(String username, String password, String url, String driver) {
        Connection con = null;

        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, username, password);
            return con;
        } catch (SQLException | ClassNotFoundException e) {
            throw new ConnectionException(String.format("Error when get database connection. %s", e.getMessage()), e);
        }
    }

}
