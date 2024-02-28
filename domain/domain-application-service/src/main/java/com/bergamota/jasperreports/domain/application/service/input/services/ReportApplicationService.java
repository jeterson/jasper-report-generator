package com.bergamota.jasperreports.domain.application.service.input.services;

import com.bergamota.jasperreports.domain.application.service.dto.report.CreateReportCommand;
import com.bergamota.jasperreports.domain.application.service.dto.report.UpdateReportCommand;
import com.bergamota.jasperreports.domain.application.service.dto.report.UploadReportFileResponse;
import com.bergamota.jasperreports.domain.core.entities.CategoryTree;
import com.bergamota.jasperreports.domain.core.entities.Report;
import com.bergamota.jasperreports.domain.core.entities.ReportParameter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public interface ReportApplicationService {
    Report findById(Long id);
    List<Report> findAll(String reportName, Long categoryId, String categoryPath);
    Report create(Report report);
    Report update(UpdateReportCommand reportCommand);
    void remove(Long id);
    Report createWithFile(Long connectionConfigId, MultipartFile file);
    List<Report> findByCategory(Long categoryId);
    Report setReportFile(MultipartFile file, Long reportId);
    UploadReportFileResponse storeFile(Long categoryId, MultipartFile file);
    Report createWithFile(CreateReportCommand reportCommand, MultipartFile file);
    Report updateWithFile(UpdateReportCommand reportCommand, MultipartFile file);
    List<ReportParameter> findParametersFromJrxml(Long reportId);
    boolean isReportFileAvailable(Long reportId);
    List<CategoryTree> getReportsAndCategoriesTree();

}
