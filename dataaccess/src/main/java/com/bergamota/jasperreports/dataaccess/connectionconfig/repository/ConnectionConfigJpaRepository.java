package com.bergamota.jasperreports.dataaccess.connectionconfig.repository;

import com.bergamota.jasperreports.dataaccess.connectionconfig.entities.ConnectionConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConnectionConfigJpaRepository extends JpaRepository<ConnectionConfigEntity, Long> {

    @Query("SELECT c FROM ConnectionConfigEntity c " +
            "WHERE UPPER(c.name) LIKE CONCAT('%', UPPER(:name), '%') " +
            "AND UPPER(c.database) = CASE WHEN :database = 'NONE' THEN UPPER(c.database) ELSE upper(:database) END")
    List<ConnectionConfigEntity> findAll(@Param("name") String name, @Param("database") String database);
}
