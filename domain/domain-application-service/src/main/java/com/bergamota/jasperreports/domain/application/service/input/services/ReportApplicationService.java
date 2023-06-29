package com.bergamota.jasperreports.domain.application.service.input.services;

import com.bergamota.jasperreports.domain.application.service.dto.report.CreateReportCommand;
import com.bergamota.jasperreports.domain.core.entities.Report;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ReportApplicationService {
    Report findById(Long id);
    Report create(Report report);
    Report update(Report report);
    void remove(Long id);
    Report createWithFile(Long connectionConfigId, MultipartFile file);
    List<Report> findByCategory(Long categoryId);
    Report setReportFile(MultipartFile file, Long reportId);
    Report createWithFile(CreateReportCommand reportCommand, MultipartFile file);

}
