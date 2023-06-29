package com.bergamota.jasperreports.dataaccess.connectionconfig.adapter;

import com.bergamota.jasperreports.dataaccess.connectionconfig.entities.ConnectionConfigEntity;
import com.bergamota.jasperreports.dataaccess.connectionconfig.mapper.ConnectionConfigDataAccessMapper;
import com.bergamota.jasperreports.dataaccess.connectionconfig.repository.ConnectionConfigJpaRepository;
import com.bergamota.jasperreports.dataaccess.report.repository.ReportJpaRepository;
import com.bergamota.jasperreports.domain.application.service.output.repository.ConnectionConfigRepository;
import com.bergamota.jasperreports.domain.core.entities.ConnectionConfig;
import com.bergamota.jasperreports.domain.core.exceptions.ConnectionConfigDomainException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ConnectionConfigRepositoryImpl implements ConnectionConfigRepository {
    private final ConnectionConfigDataAccessMapper connectionConfigDataAccessMapper;
    private final ConnectionConfigJpaRepository connectionConfigJpaRepository;
    private final ReportJpaRepository reportJpaRepository;

    @Override
    public ConnectionConfig save(ConnectionConfig obj) {
        return connectionConfigDataAccessMapper.domainEntity(connectionConfigJpaRepository.save(connectionConfigDataAccessMapper.dataAccessEntity(obj)));
    }

    @Override
    public Optional<ConnectionConfig> findById(Long id) {
        return connectionConfigJpaRepository.findById(id).map(connectionConfigDataAccessMapper::domainEntity);
    }

    @Override
    public void remove(Long id) {
        if(isUsingByReport(id))
            throw new ConnectionConfigDomainException("Integrity Violated. This connection is used by another resource");
        connectionConfigJpaRepository.deleteById(id);
    }

    @Override
    public List<ConnectionConfig> findAll() {
        return connectionConfigJpaRepository.findAll().stream().map(connectionConfigDataAccessMapper::domainEntity).toList();
    }
    private boolean isUsingByReport(Long id){
        return reportJpaRepository.findByConnection(ConnectionConfigEntity.builder().id(id).build()).stream().findAny().isPresent();
    }
}
