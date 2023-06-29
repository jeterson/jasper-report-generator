package com.bergamota.jasperreports.domain.application.service;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.bergamota.jasperreports.domain.application.service.reportgenerator",
                                "com.bergamota.jasperreports.domain.application.service.filesystem",
                                "com.bergamota.jasperreports.domain.application.service.download",
        "com.bergamota.jasperreports.domain.application.service.connection",
        "com.bergamota.jasperreports.domain.application.service.encryption"})
public class DomainReportApplicationServiceLayer {
}
