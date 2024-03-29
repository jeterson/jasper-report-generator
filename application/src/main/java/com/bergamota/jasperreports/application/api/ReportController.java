package com.bergamota.jasperreports.application.api;

import com.bergamota.jasperreports.common.domain.utils.JSONParseExtension;
import com.bergamota.jasperreports.domain.application.service.dto.report.CreateReportCommand;
import com.bergamota.jasperreports.domain.application.service.dto.report.ReportRequestFilter;
import com.bergamota.jasperreports.domain.application.service.dto.report.UpdateReportCommand;
import com.bergamota.jasperreports.domain.application.service.dto.report.UploadReportFileResponse;
import com.bergamota.jasperreports.domain.application.service.input.services.ReportApplicationService;
import com.bergamota.jasperreports.domain.core.entities.Report;
import com.bergamota.jasperreports.domain.core.exceptions.ReportDomainException;
import io.swagger.models.Response;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import lombok.experimental.ExtensionMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
@ExtensionMethod({
        JSONParseExtension.class
})
@Hidden
public class ReportController {

    private final ReportApplicationService reportApplicationService;

    @PostMapping(consumes = { "multipart/form-data" })
    public ResponseEntity<Report> createReportWithFile(@RequestParam(name = "file") MultipartFile file, @RequestParam("report") String serializedReport){
        var reportCommandOpt = serializedReport.deserialize(CreateReportCommand.class);
        if(reportCommandOpt.isEmpty()) {
            throw new ReportDomainException("Can't deserialize Report object json");
        }

        var reportCommand = reportCommandOpt.get();

        var reportCreated = reportApplicationService.createWithFile(reportCommand, file);
        return ResponseEntity.status(HttpStatus.CREATED).body(reportCreated);
    }
    @PutMapping(consumes = { "multipart/form-data" })
    public ResponseEntity<Report> updateReportWithFile(@RequestParam(name = "file", required = false) MultipartFile file, @RequestParam("report") String serializedReport){
        var reportCommandOpt = serializedReport.deserialize(UpdateReportCommand.class);
        if(reportCommandOpt.isEmpty()) {
            throw new ReportDomainException("Can't deserialize Report object json");
        }
        var reportCommand = reportCommandOpt.get();

        Report rpt;
        if(file == null)
            rpt = reportApplicationService.update(reportCommand);
        else
            rpt = reportApplicationService.updateWithFile(reportCommand, file);

        return ResponseEntity.ok(rpt);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        reportApplicationService.remove(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping
    public ResponseEntity<List<Report>> findAll(ReportRequestFilter filter) {
        var reports = reportApplicationService.findAll(filter.reportName(), filter.categoryId(),filter.path());
        return ResponseEntity.ok(reports);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Report> findById(@PathVariable Long id) {
        var report = reportApplicationService.findById(id);
        return ResponseEntity.ok(report);
    }

    @GetMapping("/is-report-file-available/{reportId}")
    public ResponseEntity<Boolean> isFileAvailable(@PathVariable Long reportId){
        return ResponseEntity.ok(reportApplicationService.isReportFileAvailable(reportId));
    }
}
