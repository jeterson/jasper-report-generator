package com.bergamota.jasperreports.domain.application.service.input.services;

import com.bergamota.jasperreports.domain.core.entities.ConnectionConfig;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public interface ConnectionApplicationService {

    Optional<Connection> getConnection(ConnectionConfig connectionConfig);
    boolean testConnection(ConnectionConfig connectionConfig);
}
