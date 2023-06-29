package com.bergamota.jasperreports.dataaccess.connectionconfig.repository;

import com.bergamota.jasperreports.dataaccess.connectionconfig.entities.ConnectionConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConnectionConfigJpaRepository extends JpaRepository<ConnectionConfigEntity, Long> {
}
