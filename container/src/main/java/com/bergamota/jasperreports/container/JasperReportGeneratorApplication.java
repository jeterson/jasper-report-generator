package com.bergamota.jasperreports.container;

import com.bergamota.jasperreports.application.ApplicationLayerConfig;
import com.bergamota.jasperreports.dataaccess.DataAccessLayerConfig;
import com.bergamota.jasperreports.domain.application.service.DomainApplicationServiceLayer;
import com.bergamota.jasperreports.domain.application.service.input.services.*;
import com.bergamota.jasperreports.domain.application.service.output.repository.CategoryRepository;
import com.bergamota.jasperreports.domain.core.entities.ReportParameter;
import com.bergamota.jasperreports.domain.core.entities.ReportParameterView;
import com.bergamota.jasperreports.domain.core.entities.report.GenerateReportParameter;
import com.bergamota.jasperreports.domain.core.entities.report.ReportProperties;
import com.bergamota.jasperreports.domain.core.valueobjects.ReportParamType;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@Import({DataAccessLayerConfig.class, DomainApplicationServiceLayer.class, ApplicationLayerConfig.class})
public class JasperReportGeneratorApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(JasperReportGeneratorApplication.class, args);
    }

    @Autowired
    private CategoryApplicationService categoryApplicationService;
    @Autowired
    private ConnectionConfigApplicationService connectionConfigApplicationService;
    @Autowired
    private ConnectionApplicationService connectionApplicationService;

    @Autowired
    private ReportConfigApplicationService reportConfigApplicationService;

    @Autowired
    private JasperReportGeneratorApplicationService jasperReportGeneratorApplicationService;

    @Autowired
    private ReportApplicationService reportApplicationService;

    @Autowired
    private ReportParameterApplicationService reportParameterRepository;

    @Autowired
    private ReportExtractorParameterApplicationService reportExtractorParameterApplicationService;
    @Autowired
    private Environment env;

    @Override
    public void run(String... args) throws Exception {
       /*var tree = categoryApplicationService.getCategoriesAsTree();
       var connectionConfig = connectionConfigApplicationService.findById(1L);
       var connectionOk = connectionApplicationService.testConnection(connectionConfig);

       var config = reportConfigApplicationService.findDefault();
       var configCache = reportConfigApplicationService.findDefault();

       var report = reportApplicationService.findById(24L);

      // var file = jasperReportGeneratorApplicationService.generateReport(report, List.of());

        var parameter1 = ReportParameter.builder()
                .type(ReportParamType.STRING)
                .name("DESCRICAO")
                .reportType(ReportParamType.STRING)
                .report(report)
                .reportParameterView(ReportParameterView.builder()
                        .label("Descrição")
                        .sortOrder(2)
                        .visible(true)
                        .required(false)
                        .build())
                .build();

        //reportParameterRepository.create(parameter1);

        reportExtractorParameterApplicationService.extractParametersFromJrXml(report.getFullFilePath());

        */
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        if (Arrays.asList(env.getActiveProfiles()).contains("test")) {
            http.headers().frameOptions().disable();
        }

        http.cors().and().csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().anyRequest().permitAll();

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
        configuration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "OPTIONS", "PATCH"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
