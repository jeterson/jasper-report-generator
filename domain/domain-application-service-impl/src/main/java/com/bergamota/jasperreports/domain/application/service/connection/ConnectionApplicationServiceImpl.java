package com.bergamota.jasperreports.domain.application.service.connection;

import com.bergamota.jasperreports.domain.application.service.input.services.ConnectionApplicationService;
import com.bergamota.jasperreports.domain.application.service.input.services.EncryptionApplicationService;
import com.bergamota.jasperreports.domain.core.entities.ConfigConnectionStatus;
import com.bergamota.jasperreports.domain.core.entities.ConnectionConfig;
import com.bergamota.jasperreports.domain.core.exceptions.ConnectionException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConnectionApplicationServiceImpl implements ConnectionApplicationService {

    private final EncryptionApplicationService encryptionApplicationService;

    @Override
    public Optional<Connection> getConnection(ConnectionConfig connectionConfig) {
        if(connectionConfig == null)
            return Optional.empty();

        var username = encryptionApplicationService.decrypt(connectionConfig.getUsername());
        var password = encryptionApplicationService.decrypt(connectionConfig.getPassword());

        log.info("Trying get connection of {}-{}", connectionConfig.getDatabase(), connectionConfig.getName());
        var connection = createConnection(username, password, connectionConfig.getConnectionUrl(), connectionConfig.getDatabase().getDriver());
        log.info("Connection retrieved successfuly");
        return Optional.of(connection);
    }

    @Override
    @SneakyThrows
    public ConfigConnectionStatus testConnection(ConnectionConfig connectionConfig){
        Connection conn = null;
        try {
            log.info("Driver name: {}", connectionConfig.getDatabase().getDriver());
            var conOpt = getConnection(connectionConfig);
            if(conOpt.isEmpty())
                throw new ConnectionException("Failed to test connection. Connection is Empty");
            conn = conOpt.get();
            log.info("Connection successfully");
            return new ConfigConnectionStatus(true, "Connection OK");
        }catch (ConnectionException e) {
            log.error("Connection Failed. Reason: " + e.getMessage(), e);
            return new ConfigConnectionStatus(false, e.getMessage());
        }finally {
            if(conn != null && !conn.isClosed())
                conn.close();
        }
    }

    private Connection createConnection(String username, String password, String url, String driver) {
        Connection con;

        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, username, password);
            return con;
        } catch (SQLException | ClassNotFoundException e) {
            throw new ConnectionException(String.format("Error when get database connection. %s", e.getMessage()), e);
        }
    }

}
