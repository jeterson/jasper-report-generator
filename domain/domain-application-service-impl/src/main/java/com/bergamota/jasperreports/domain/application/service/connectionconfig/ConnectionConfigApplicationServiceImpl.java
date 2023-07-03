package com.bergamota.jasperreports.domain.application.service.connectionconfig;

import com.bergamota.jasperreports.common.domain.valueobjects.ReportDatabase;
import com.bergamota.jasperreports.domain.application.service.dto.configconnection.CreateConfigConnection;
import com.bergamota.jasperreports.domain.application.service.dto.configconnection.UpdateConfigConnection;
import com.bergamota.jasperreports.domain.application.service.input.services.ConnectionApplicationService;
import com.bergamota.jasperreports.domain.application.service.input.services.ConnectionConfigApplicationService;
import com.bergamota.jasperreports.domain.application.service.input.services.EncryptionApplicationService;
import com.bergamota.jasperreports.domain.application.service.output.repository.ConnectionConfigRepository;
import com.bergamota.jasperreports.domain.core.entities.ConnectionConfig;
import com.bergamota.jasperreports.domain.core.exceptions.ConnectionConfigNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConnectionConfigApplicationServiceImpl implements ConnectionConfigApplicationService {
    private final ConnectionConfigRepository connectionConfigRepository;
    private final EncryptionApplicationService encryptionApplicationService;
    private final ConnectionApplicationService connectionApplicationService;

    @Override
    public ConnectionConfig create(CreateConfigConnection createConfigConnection) {
        var connectionConfig = ConnectionConfig.builder()
                .database(createConfigConnection.getDatabase())
                .name(createConfigConnection.getName())
                .connectionUrl(createConfigConnection.getConnectionUrl())
                .username(createConfigConnection.getUsername())
                .password(createConfigConnection.getPassword())
                .build();

        connectionConfig.setPassword(encryptionApplicationService.encrypt(connectionConfig.getPassword()));
        connectionConfig.setUsername(encryptionApplicationService.encrypt(connectionConfig.getUsername()));
        var status = connectionApplicationService.testConnection(connectionConfig);
        connectionConfig = connectionConfigRepository.save(connectionConfig);
        connectionConfig.hiddenCredentials();
        return connectionConfig.withStatus(status);
    }

    @Override
    public ConnectionConfig update(UpdateConfigConnection updateConfigConnection) {
        var connectionConfig = ConnectionConfig
                .builder()
                .id(updateConfigConnection.getId())
                .name(updateConfigConnection.getName())
                .connectionUrl(updateConfigConnection.getConnectionUrl())
                .password(updateConfigConnection.getPassword())
                .username(updateConfigConnection.getUsername())
                .build();

        var tempOpt = connectionConfigRepository.findById(connectionConfig.getId());
        if(tempOpt.isEmpty())
            throw new ConnectionConfigNotFoundException(ConnectionConfig.class, "id", connectionConfig.getId());

        var temp = tempOpt.get();
        connectionConfig = connectionConfig.withDatabase(temp.getDatabase());

        updatePasswordAndUsernameIfNecessary(connectionConfig, temp);
        var status = connectionApplicationService.testConnection(connectionConfig);
        connectionConfig = connectionConfigRepository.save(connectionConfig);
        connectionConfig.hiddenCredentials();
        return connectionConfig.withStatus(status);
    }

    private void updatePasswordAndUsernameIfNecessary(ConnectionConfig connectionConfig, ConnectionConfig temp) {

        if(!encryptionApplicationService.areEqual(connectionConfig.getPassword(), temp.getPassword()) && isNotNullOrEmpty(connectionConfig.getPassword()))
            connectionConfig.setPassword(encryptionApplicationService.encrypt(connectionConfig.getPassword()));
        else
            connectionConfig.setPassword(temp.getPassword());

        if(!encryptionApplicationService.areEqual(connectionConfig.getUsername(), temp.getUsername()) && isNotNullOrEmpty(connectionConfig.getUsername()))
            connectionConfig.setUsername(encryptionApplicationService.encrypt(connectionConfig.getUsername()));
        else
            connectionConfig.setUsername(temp.getUsername());
    }
    private boolean isNotNullOrEmpty(String value){
        if(value == null)
            return false;
        return !value.isBlank() && !value.isEmpty();
    }


    @Override
    public ConnectionConfig findById(Long id) {
        return connectionConfigRepository.findById(id)
                .map(e ->
                {
                    e = e.withStatus(connectionApplicationService.testConnection(e));
                    e.hiddenCredentials();
                    return e;
                })
                .orElseThrow(() -> new ConnectionConfigNotFoundException(ConnectionConfig.class, "id",id));

    }

    @Override
    public List<ConnectionConfig> findAll() {
        return findAll("", ReportDatabase.NONE);
    }

    @Override
    public List<ConnectionConfig> findAll(String name, ReportDatabase database) {
        return connectionConfigRepository
                .findAll(name, database)
                .stream()
                .map(e -> {
                    e = e.withStatus(connectionApplicationService.testConnection(e));
                    e.hiddenCredentials();
                    return e;
                })
                .toList();
    }

    @Override
    public void remove(Long id) {
        connectionConfigRepository.remove(id);
    }
}
