package com.bergamota.jasperreports.dataaccess.connectionconfig.mapper;

import com.bergamota.jasperreports.dataaccess.connectionconfig.entities.ConnectionConfigEntity;
import com.bergamota.jasperreports.domain.core.entities.ConnectionConfig;
import org.springframework.stereotype.Component;

@Component
public class ConnectionConfigDataAccessMapper {

    public ConnectionConfig domainEntity(ConnectionConfigEntity connectionConfigEntity){
        return ConnectionConfig.builder()
                .id(connectionConfigEntity.getId())
                .connectionUrl(connectionConfigEntity.getConnectionUrl())
                .password(connectionConfigEntity.getPassword())
                .username(connectionConfigEntity.getUsername())
                .name(connectionConfigEntity.getName())
                .database(connectionConfigEntity.getDatabase())
                .build();
    }
    public ConnectionConfigEntity dataAccessEntity(ConnectionConfig connectionConfig) {
        return ConnectionConfigEntity.builder()
                .connectionUrl(connectionConfig.getConnectionUrl())
                .password(connectionConfig.getPassword())
                .username(connectionConfig.getUsername())
                .database(connectionConfig.getDatabase())
                .id(connectionConfig.getId())
                .name(connectionConfig.getName())
                .build();
    }

}
