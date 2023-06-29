package com.bergamota.jasperreports.domain.application.service.report;

import com.bergamota.jasperreports.domain.application.service.dto.report.CreateReportCommand;
import com.bergamota.jasperreports.domain.application.service.input.services.*;
import com.bergamota.jasperreports.domain.application.service.output.repository.ReportRepository;
import com.bergamota.jasperreports.domain.core.entities.Report;
import com.bergamota.jasperreports.domain.core.exceptions.ReportDomainException;
import com.bergamota.jasperreports.domain.core.exceptions.ReportNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.bergamota.jasperreports.domain.core.entities.*;

import java.util.List;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReportApplicationServiceImpl implements ReportApplicationService {

    private final ReportRepository reportRepository;
    private final CategoryApplicationService categoryApplicationService;
    private final FileSystemApplicationService fileSystemApplicationService;
    private final ConnectionConfigApplicationService  connectionConfigApplicationService;
    private final ReportConfigApplicationService reportConfigApplicationService;
    private final ReportExtractorParameterApplicationService reportExtractorParameterApplicationService;

    @Override
    public Report findById(Long id) {
        return reportRepository.findById(id).orElseThrow(() -> new ReportNotFoundException(Report.class, "id", id));
    }

    @Override
    public Report create(Report report) {
        return reportRepository.save(report);
    }

    @Override
    public Report update(Report report) {
        return reportRepository.save(report);
    }

    @Override
    public void remove(Long id) {
        reportRepository.remove(id);
    }

    @Override
    public Report createWithFile(Long connectionConfigId, MultipartFile file) {
        try {

            var category = categoryApplicationService.create(Category
                    .builder()
                    .description(String.format("Report %s",fileSystemApplicationService.getFileName(file.getOriginalFilename())))
                    .build());
            var connectionConfig = connectionConfigApplicationService.findById(connectionConfigId);

            var basePath = reportConfigApplicationService.findDefault().getBasePath();
            var fullPath = basePath + fileSystemApplicationService.separator() + category.getPath();
            var reportName = fileSystemApplicationService.getFileName(file.getOriginalFilename());

            var reportParameters = reportExtractorParameterApplicationService
                    .extractParametersFromJrXml(fullPath + fileSystemApplicationService.separator() + reportName);

            var report = Report.builder()
                    .category(category)
                    .parameters(reportParameters)
                    .subReports(Set.of())
                    .filePath(fullPath)
                    .fileName(reportName)
                    .connectionConfig(connectionConfig)
                    .build();
            fileSystemApplicationService.copy(fullPath, file);
            return create(report);
        }catch (Exception e) {
            log.error("Error when trying upload file ", e);
            throw new ReportDomainException("Error when trying upload file " + e.getMessage(), e);
        }

    }

    @Override
    public List<Report> findByCategory(Long categoryId) {
        return reportRepository.findByCategory(categoryId);
    }

    public Report addReportFile(MultipartFile file, Report report, boolean refresh) {
        if(refresh)
            report = findById(report.getId());

        var basePath = reportConfigApplicationService.findDefault().getBasePath();
        var fullPath = basePath + fileSystemApplicationService.separator() + report.getCategory().getPath();
        try {
            fileSystemApplicationService.copy(fullPath, file);

            var reportParameters = reportExtractorParameterApplicationService
                    .extractParametersFromJrXml(fullPath + fileSystemApplicationService.separator() + report.getFileName());

            return update(report.withFilePath(fullPath).withParameters(reportParameters));
        }catch (Exception ex){
            log.error("Error when trying upload file ", ex);
            throw new ReportDomainException("Error when trying upload file " + ex.getMessage(), ex);
        }
    }

    @Override
    public Report setReportFile(MultipartFile file, Long reportId) {
        return addReportFile(file, Report.builder().id(reportId).build(),true);
    }

    @Override
    public Report createWithFile(CreateReportCommand createReportCommand, MultipartFile file) {

        var report = Report.builder()
                .name(createReportCommand.name() == null ? fileSystemApplicationService.getFileName(file.getOriginalFilename()) : createReportCommand.name())
                .category(categoryApplicationService.findById(createReportCommand.categoryId()))
                .connectionConfig(connectionConfigApplicationService.findById(createReportCommand.connectionId()))
                .parameters(Set.of())
                .subReports(Set.of())
                .fileName(file.getOriginalFilename())
                .build();

        return addReportFile(file, report, false);
    }
}
