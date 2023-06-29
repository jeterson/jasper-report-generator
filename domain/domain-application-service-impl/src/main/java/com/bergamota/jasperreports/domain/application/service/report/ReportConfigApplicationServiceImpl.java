package com.bergamota.jasperreports.domain.application.service.report;

import com.bergamota.jasperreports.domain.application.service.cache.reportconfig.ReportConfigCache;
import com.bergamota.jasperreports.domain.application.service.input.services.ReportConfigApplicationService;
import com.bergamota.jasperreports.domain.application.service.output.repository.ReportConfigRepository;
import com.bergamota.jasperreports.domain.core.entities.ReportConfig;
import com.bergamota.jasperreports.domain.core.exceptions.ReportConfigDomainException;
import com.bergamota.jasperreports.domain.core.exceptions.ReportConfigNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReportConfigApplicationServiceImpl implements ReportConfigApplicationService {

    private final ReportConfigRepository reportConfigRepository;
    @Override
    public ReportConfig create(ReportConfig reportConfig) {
        if(reportConfig.isActive())
            ReportConfigCache.getInstance().invalidate();

        return reportConfigRepository.save(reportConfig);
    }

    @Override
    public ReportConfig update(ReportConfig reportConfig) {
        if(reportConfig.isActive())
            ReportConfigCache.getInstance().invalidate();

        return reportConfigRepository.save(reportConfig);
    }

    @Override
    public ReportConfig findDefault() {

        if(ReportConfigCache.getInstance().getReportConfig().isPresent()) {
            log.info("Getting config from cache");
            return ReportConfigCache.getInstance().getReportConfig().orElseThrow();
        }

        var config = reportConfigRepository
                .findAll()
                .stream()
                .filter(ReportConfig::isActive)
                .findFirst()
                .orElseThrow(() -> new ReportConfigDomainException("No one reportConfig default found"));

        ReportConfigCache.getInstance().setReportConfig(config);
        return ReportConfigCache.getInstance().getReportConfig().orElseThrow();
    }

    @Override
    public void remove(String s) {
        var config = reportConfigRepository.findById(s).orElseThrow(() -> new ReportConfigNotFoundException(ReportConfig.class, "key", s));
        var newConfigActive = config.electOtherConfigToActive();
        if(newConfigActive.isEmpty())
            throw new ReportConfigDomainException("You need more than once config to remove this");

        ReportConfigCache.getInstance().invalidate();
        update(newConfigActive.get());
        reportConfigRepository.remove(s);

    }

    @Override
    public void makeAsDefault(String id) {
        var config = reportConfigRepository.findById(id);
        if(config.isPresent()){
            config.get().makeAsDefault();
            create(config.get());
            ReportConfigCache.getInstance().invalidate();
        }

    }
}
