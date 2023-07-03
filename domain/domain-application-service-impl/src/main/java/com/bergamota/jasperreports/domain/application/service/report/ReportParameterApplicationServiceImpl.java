package com.bergamota.jasperreports.domain.application.service.report;

import com.bergamota.jasperreports.common.domain.exception.NotFoundException;
import com.bergamota.jasperreports.domain.application.service.dto.reportparameter.CreateReportParameterCommand;
import com.bergamota.jasperreports.domain.application.service.dto.reportparameter.UpdateReportParameterCommand;
import com.bergamota.jasperreports.domain.application.service.input.services.ReportApplicationService;
import com.bergamota.jasperreports.domain.application.service.input.services.ReportParameterApplicationService;
import com.bergamota.jasperreports.domain.application.service.output.repository.ReportParameterRepository;
import com.bergamota.jasperreports.domain.core.entities.ReportParameter;
import com.bergamota.jasperreports.domain.core.valueobjects.ReportParamType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ReportParameterApplicationServiceImpl implements ReportParameterApplicationService {
    private final ReportParameterRepository reportParameterRepository;
    private final ReportApplicationService reportApplicationService;

    @Override
    public ReportParameter create(CreateReportParameterCommand reportParameterCommand) {

        var toCreate = ReportParameter.builder()
                .report(reportApplicationService.findById(reportParameterCommand.reportId()))
                .createdManually(true)
                .pattern(reportParameterCommand.pattern())
                .name(reportParameterCommand.name())
                .defaultValue(reportParameterCommand.defaultValue())
                .type(ReportParamType.toEnum(reportParameterCommand.type()))
                .reportType(ReportParamType.toEnum(reportParameterCommand.reportType()))
                .build();
        return reportParameterRepository.save(toCreate);
    }

    @Override
    public ReportParameter update(Long id, UpdateReportParameterCommand reportParameterCommand) {
        var auxParameterOpt = reportParameterRepository.findById(id);
        var auxParameter = auxParameterOpt.orElseThrow(() -> new NotFoundException(ReportParameter.class, "id", id));

        var toUpdateParameter = ReportParameter.builder()
                .id(id)
                .createdManually(auxParameter.isCreatedManually())
                .name(reportParameterCommand.name())
                .defaultValue(reportParameterCommand.defaultValue())
                .reportType(ReportParamType.toEnum(reportParameterCommand.reportType()))
                .type(ReportParamType.toEnum(reportParameterCommand.type()))
                .reportParameterView(auxParameter.getReportParameterView())
                .pattern(reportParameterCommand.pattern())
                .report(auxParameter.getReport())
                .build();

        return reportParameterRepository.save(toUpdateParameter);
    }

    private void removeAllParameters(Long reportId){
        var parameters =  reportParameterRepository.findByReport(reportId);
        for(var p : parameters){
            reportParameterRepository.remove(p.getId());
        }
    }

    public void remove(Long id){
        reportParameterRepository.remove(id);
    }
    public boolean createFromXml(Long reportId) {
        var parameters = reportApplicationService.findParametersFromJrxml(reportId);
        removeAllParameters(reportId);
        for(var p : parameters){
           reportParameterRepository.save(p);
        }
        return parameters.size() > 0;
    }
}
