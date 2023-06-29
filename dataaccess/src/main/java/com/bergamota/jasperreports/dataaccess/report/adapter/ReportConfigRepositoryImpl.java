package com.bergamota.jasperreports.dataaccess.report.adapter;

import com.bergamota.jasperreports.dataaccess.report.mapper.ReportConfigDataAccessMapper;
import com.bergamota.jasperreports.dataaccess.report.repository.ReportConfigJpaRepository;
import com.bergamota.jasperreports.domain.application.service.output.repository.ReportConfigRepository;
import com.bergamota.jasperreports.domain.core.entities.ReportConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ReportConfigRepositoryImpl implements ReportConfigRepository {

    private final ReportConfigJpaRepository reportConfigJpaRepository;
    private final ReportConfigDataAccessMapper reportConfigDataAccessMapper;

    @Override
    public ReportConfig save(ReportConfig obj) {
        var config = reportConfigDataAccessMapper.domainEntity(reportConfigDataAccessMapper.dataAccessEntity(obj));
        obj.getConfigurations().forEach(e -> reportConfigDataAccessMapper.domainEntity(reportConfigDataAccessMapper.dataAccessEntity(e)));
        var allConfigs = findAll();
        return config.withConfigurations(allConfigs);
    }

    @Override
    public Optional<ReportConfig> findById(String s) {
        var all = reportConfigJpaRepository.findAll().stream().map(reportConfigDataAccessMapper::domainEntity).toList();
        var opt = reportConfigJpaRepository.findById(s).map(reportConfigDataAccessMapper::domainEntity);
        if(opt.isPresent()) {
            var reportConfig = opt.get();
            return Optional.of(reportConfig.withConfigurations(all));
        }
        return Optional.empty();
    }

    @Override
    public void remove(String s) {
        reportConfigJpaRepository.deleteById(s);
    }

    @Override
    public List<ReportConfig> findAll() {
        var all = reportConfigJpaRepository.findAll().stream().map(reportConfigDataAccessMapper::domainEntity).toList();
        return all.stream().map(e -> e.withConfigurations(all)).toList();
    }
}
