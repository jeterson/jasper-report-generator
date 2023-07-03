package com.bergamota.jasperreports.dataaccess.report.adapter;

import com.bergamota.jasperreports.dataaccess.report.entities.ReportParameterEntity;
import com.bergamota.jasperreports.dataaccess.report.mapper.ReportDataAccessMapper;
import com.bergamota.jasperreports.dataaccess.report.repository.ReportJpaRepository;
import com.bergamota.jasperreports.dataaccess.report.repository.ReportParameterJpaRepository;
import com.bergamota.jasperreports.dataaccess.report.repository.ReportParameterViewJpaRepository;
import com.bergamota.jasperreports.domain.application.service.output.repository.ReportParameterRepository;
import com.bergamota.jasperreports.domain.application.service.output.repository.ReportRepository;
import com.bergamota.jasperreports.domain.core.entities.Report;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import com.bergamota.jasperreports.domain.core.entities.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class ReportRepositoryImpl implements ReportRepository {
    private final ReportJpaRepository reportJpaRepository;
    private final ReportDataAccessMapper reportDataAccessMapper;
    private final ReportParameterRepository reportParameterRepository;
    private final ReportParameterJpaRepository reportParameterJpaRepository;
    private final ReportParameterViewJpaRepository reportParameterViewJpaRepository;

    @Override
    public Optional<Report> findById(Long id) {
        return reportJpaRepository.findById(id).map(reportDataAccessMapper::domainEntity);
    }

    @Transactional
    @Override
    public Report save(Report report) {
        var entityReport = reportDataAccessMapper.dataAccessEntity(report);
        var savedReport = reportJpaRepository.save(entityReport);

        for (ReportParameterEntity p : savedReport.getParameters()) {
            p.setReport(savedReport);
        }
        reportParameterJpaRepository.saveAll(savedReport.getParameters());
        for(ReportParameterEntity p: savedReport.getParameters()){
            if(p.getReportParameterView() != null)
                p.getReportParameterView().setReportParameter(p);
        }
       var parametersViews = savedReport.getParameters().stream().map(ReportParameterEntity::getReportParameterView).toList();
        reportParameterViewJpaRepository.saveAll(parametersViews.stream().filter(Objects::nonNull).toList());

        return reportDataAccessMapper.domainEntity(savedReport);
    }

    @Transactional
    @Override
    public void remove(Long id) {
        var item = reportJpaRepository.findById(id);
        if(item.isPresent()){
            item.get().getParameters().forEach(i -> reportParameterRepository.remove(i.getId()));
            reportJpaRepository.delete(item.get());
        };
    }

    @Override
    public List<Report> findByCategory(Long categoryId) {
        var category = Category.builder().id(categoryId).build();
        return reportJpaRepository.findByCategory(category).stream().map(reportDataAccessMapper::domainEntity).toList();
    }

    @Override
    public List<Report> findAll(String reportName, Long categoryId, String categoryPath) {
        return reportJpaRepository.findByNameAndCategoryAndCategoryPath(reportName, categoryId, categoryPath)
                .stream().map(reportDataAccessMapper::domainEntity).toList();
    }
}
