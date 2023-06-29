package com.bergamota.jasperreports.executable;

import com.bergamota.jasperreports.domain.application.service.common.ReportGeneratorConfig;
import com.bergamota.jasperreports.domain.core.entities.ReportConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class ExecutableConfiguration {

    @Bean
    @Scope(value="prototype")
    public ReportGeneratorConfig reportGeneratorConfig(){
        return new ReportGeneratorConfig() {
            @Override
            public ReportConfig findDefault() {
                return ReportConfig.builder()
                        .imagePath("images")
                        .generatedReportBasePath("generated")
                        .basePath(BasePathSingleton.getInstance().getBasePath())
                        .build();
            }
        };
    }
}
