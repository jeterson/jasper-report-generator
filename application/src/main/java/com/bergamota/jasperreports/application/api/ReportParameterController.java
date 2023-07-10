package com.bergamota.jasperreports.application.api;

import com.bergamota.jasperreports.domain.application.service.dto.reportparameter.CreateReportParameterCommand;
import com.bergamota.jasperreports.domain.application.service.dto.reportparameter.UpdateReportParameterCommand;
import com.bergamota.jasperreports.domain.application.service.input.services.ReportParameterApplicationService;
import com.bergamota.jasperreports.domain.core.entities.ReportParameter;
import com.bergamota.jasperreports.domain.core.valueobjects.ReportParamType;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RequestMapping("/parameters")
@RestController
@RequiredArgsConstructor
@Hidden
public class ReportParameterController {

    private final ReportParameterApplicationService reportParameterApplicationService;

    @GetMapping("/types")
    public ResponseEntity<List<ReportParamType>> getParametersType(){
        var parametersType = Arrays.stream(ReportParamType.values()).toList();
        return ResponseEntity.ok(parametersType);
    }

    @PatchMapping("/create-from-jrxml/report/{reportId}")
    public ResponseEntity<Boolean> createFromXml(@PathVariable Long reportId){
        var created = reportParameterApplicationService.createFromXml(reportId);
        if(created)
            return ResponseEntity.status(HttpStatus.CREATED).body(true);
        else
            return ResponseEntity.ok(false);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReportParameter> update(@PathVariable Long id, @RequestBody  UpdateReportParameterCommand createReportParameterCommand){
        var saved = reportParameterApplicationService.update(id, createReportParameterCommand);
        return ResponseEntity.ok(saved);
    }
    @PostMapping
    public ResponseEntity<ReportParameter> create(@RequestBody CreateReportParameterCommand reportParameterCommand){
        var saved = reportParameterApplicationService.create(reportParameterCommand);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        reportParameterApplicationService.remove(id);
        return ResponseEntity.noContent().build();
    }
}
