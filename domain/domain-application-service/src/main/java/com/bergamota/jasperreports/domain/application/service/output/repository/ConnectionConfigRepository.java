package com.bergamota.jasperreports.domain.application.service.output.repository;

import com.bergamota.jasperreports.common.domain.valueobjects.ReportDatabase;
import com.bergamota.jasperreports.domain.application.service.common.CrudRepository;
import com.bergamota.jasperreports.domain.core.entities.ConnectionConfig;

import java.util.List;

public interface ConnectionConfigRepository extends CrudRepository<ConnectionConfig, Long> {
    List<ConnectionConfig> findAll(String name, ReportDatabase database);
}
