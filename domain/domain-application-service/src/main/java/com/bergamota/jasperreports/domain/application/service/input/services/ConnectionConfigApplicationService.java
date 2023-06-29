package com.bergamota.jasperreports.domain.application.service.input.services;

import com.bergamota.jasperreports.domain.application.service.dto.configconnection.CreateConfigConnection;
import com.bergamota.jasperreports.domain.application.service.dto.configconnection.UpdateConfigConnection;
import com.bergamota.jasperreports.domain.core.entities.ConnectionConfig;

import java.util.List;

public interface ConnectionConfigApplicationService {

    ConnectionConfig create(CreateConfigConnection createConfigConnection);
    ConnectionConfig update(UpdateConfigConnection updateConfigConnection);
    ConnectionConfig findById(Long id);
    List<ConnectionConfig> findAll();
    void remove(Long id);
}
