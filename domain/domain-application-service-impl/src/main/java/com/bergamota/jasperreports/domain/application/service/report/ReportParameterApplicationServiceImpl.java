package com.bergamota.jasperreports.domain.application.service.report;

import com.bergamota.jasperreports.domain.application.service.input.services.ReportParameterApplicationService;
import com.bergamota.jasperreports.domain.application.service.output.repository.ReportParameterRepository;
import com.bergamota.jasperreports.domain.core.entities.ReportParameter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportParameterApplicationServiceImpl implements ReportParameterApplicationService {
    private final ReportParameterRepository reportParameterRepository;

    @Override
    public ReportParameter create(ReportParameter reportParameter) {
        return reportParameterRepository.save(reportParameter);
    }
}
