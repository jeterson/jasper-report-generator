package com.bergamota.jasperreports.dataaccess.report.adapter;

import com.bergamota.jasperreports.dataaccess.report.entities.ReportEntity;
import com.bergamota.jasperreports.dataaccess.report.mapper.ReportParameterDataAccessMapper;
import com.bergamota.jasperreports.dataaccess.report.repository.ReportParameterJpaRepository;
import com.bergamota.jasperreports.dataaccess.report.repository.ReportParameterViewJpaRepository;
import com.bergamota.jasperreports.domain.application.service.output.repository.ReportParameterRepository;
import com.bergamota.jasperreports.domain.core.entities.ReportParameter;
import jakarta.persistence.PreRemove;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ReportParameterRepositoryImpl implements ReportParameterRepository {
    private final ReportParameterJpaRepository reportParameterJpaRepository;
    private final ReportParameterViewJpaRepository reportParameterViewJpaRepository;
    private final ReportParameterDataAccessMapper reportParameterDataAccessMapper;

    @Transactional
    @Override
    public ReportParameter save(ReportParameter obj) {
        var reportParameterEntity = reportParameterDataAccessMapper.dataAccessEntity(obj, false);
        reportParameterEntity.setReportParameterView(null);
        var saved = reportParameterJpaRepository.save(reportParameterEntity);
        var reportParameterView = reportParameterDataAccessMapper.dataAccessEntityView(obj.getReportParameterView());
        saved.setReportParameterView(reportParameterView);
        if(reportParameterView != null) {
            reportParameterView.setReportParameter(saved);
            reportParameterViewJpaRepository.save(reportParameterView);
        }
        return reportParameterDataAccessMapper.domainEntity(saved);
    }

    @Override
    public Optional<ReportParameter> findById(Long aLong) {
        return reportParameterJpaRepository.findById(aLong).map(reportParameterDataAccessMapper::domainEntity);
    }

    @Transactional
    @PreRemove
    @Override
    public void remove(Long aLong) {
        var item = reportParameterJpaRepository.findById(aLong);
        if(item.isPresent()){
            if(item.get().getReportParameterView() != null)
                reportParameterViewJpaRepository.delete(item.get().getReportParameterView());

            reportParameterJpaRepository.delete(item.get());
        }
    }

    @Override
    public List<ReportParameter> findByReport(Long reportId) {
        return reportParameterJpaRepository.findByReport(ReportEntity.builder().id(reportId).build())
                .stream().map(reportParameterDataAccessMapper::domainEntity).toList();
    }
}
