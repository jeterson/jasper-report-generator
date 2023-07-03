package com.bergamota.jasperreports.domain.application.service.input.services;

import com.bergamota.jasperreports.domain.core.entities.ConfigConnectionStatus;
import com.bergamota.jasperreports.domain.core.entities.ConnectionConfig;

import java.sql.Connection;
import java.util.Optional;

public interface ConnectionApplicationService {

    Optional<Connection> getConnection(ConnectionConfig connectionConfig);
    ConfigConnectionStatus testConnection(ConnectionConfig connectionConfig);
}
