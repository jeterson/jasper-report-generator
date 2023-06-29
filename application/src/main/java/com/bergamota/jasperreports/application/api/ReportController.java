package com.bergamota.jasperreports.application.api;

import com.bergamota.jasperreports.common.domain.utils.JSONParseExtension;
import com.bergamota.jasperreports.domain.application.service.dto.report.CreateReportCommand;
import com.bergamota.jasperreports.domain.application.service.input.services.ReportApplicationService;
import com.bergamota.jasperreports.domain.core.entities.Report;
import com.bergamota.jasperreports.domain.core.exceptions.ReportDomainException;
import lombok.RequiredArgsConstructor;
import lombok.experimental.ExtensionMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
@ExtensionMethod({
        JSONParseExtension.class
})
public class ReportController {

    private final ReportApplicationService reportApplicationService;

    @PostMapping(consumes = { "multipart/form-data" })
    public ResponseEntity<Report> createReportWithFile(@RequestParam(name = "file") MultipartFile file, @RequestParam("report") String serializedReport){
        var reportCommandOpt = serializedReport.deserialize(CreateReportCommand.class);
        if(reportCommandOpt.isEmpty())
            throw new ReportDomainException("Can't deserialize Report object json");

        var reportCommand = reportCommandOpt.get();

        var reportCreated = reportApplicationService.createWithFile(reportCommand, file);
        return ResponseEntity.status(HttpStatus.CREATED).body(reportCreated);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Report> changeReportFile(@PathVariable  Long id,@RequestParam(name = "file") MultipartFile file){
        var reportSaved = reportApplicationService.setReportFile(file, id);
        return ResponseEntity.ok(reportSaved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        reportApplicationService.remove(id);
        return ResponseEntity.noContent().build();
    }
}
