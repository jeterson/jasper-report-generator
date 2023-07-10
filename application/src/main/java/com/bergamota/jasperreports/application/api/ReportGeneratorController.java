package com.bergamota.jasperreports.application.api;

import com.bergamota.jasperreports.domain.application.service.dto.generatereport.GenerateReportCommand;
import com.bergamota.jasperreports.domain.application.service.input.services.JasperReportGeneratorApplicationService;
import com.bergamota.jasperreports.domain.application.service.input.services.ReportApplicationService;
import com.bergamota.jasperreports.domain.core.entities.Report;
import com.bergamota.jasperreports.domain.core.entities.report.ReportProperties;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/generate-report")
@RequiredArgsConstructor
public class ReportGeneratorController {
    private final JasperReportGeneratorApplicationService jasperReportGeneratorApplicationService;
    private final ReportApplicationService reportApplicationService;

    @PostMapping
    public ResponseEntity<Void> generateReport(@RequestBody ReportProperties reportProperties, HttpServletResponse response){
        jasperReportGeneratorApplicationService.generateReport(reportProperties, response);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/by-report")
    public ResponseEntity<Void> generateReport(@RequestBody GenerateReportCommand generateReportCommand, HttpServletResponse response){
        jasperReportGeneratorApplicationService.generateReport(reportApplicationService.findById(generateReportCommand.reportId()), generateReportCommand.parameters());
        return ResponseEntity.noContent().build();
    }


}
