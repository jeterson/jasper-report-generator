package com.bergamota.jasperreports.executable;

import com.bergamota.jasperreports.domain.application.service.DomainReportApplicationServiceLayer;
import com.bergamota.jasperreports.domain.application.service.common.ReportGeneratorConfig;
import com.bergamota.jasperreports.domain.application.service.input.services.FileSystemApplicationService;
import com.bergamota.jasperreports.domain.application.service.input.services.JasperReportGeneratorApplicationService;
import com.bergamota.jasperreports.domain.core.entities.report.ReportProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Import;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
@Import({DomainReportApplicationServiceLayer.class})
public class JasperReportGeneratorMain implements CommandLineRunner {

    @Autowired
    private JasperReportGeneratorApplicationService jasperReportGeneratorApplicationService;

    @Autowired
    private FileSystemApplicationService fileSystemApplicationService;

    public static void main(String[] args) {
        new SpringApplicationBuilder(JasperReportGeneratorMain.class)
                .web(WebApplicationType.NONE).run(args);

    }

    private  String getBasePathParam(String[] args){
        var defaultPath = System.getProperty("user.home") + fileSystemApplicationService.separator() + "reports-executable";
        if(Arrays.stream(args).toList().stream().anyMatch(x -> x.equals("--basePath"))){
            var index = List.of(args).indexOf("--basePath");
            try {
                return args[index + 1];
            }catch(Exception ex) {
                return defaultPath;
            }
        }
        return defaultPath;
    }
    @Override
    public void run(String... args) throws Exception {

        if(args.length == 0)
            return;

        var basePath = getBasePathParam(args);

        var serializedText = args[0];
        var prop = toReportProperties(serializedText);
        if(prop.isEmpty())
            return;

        BasePathSingleton.getInstance().setBasePath(basePath);
        var fileName = jasperReportGeneratorApplicationService.generateReport(prop.get());
        System.out.println("REPORT_GENERATED_PDF_PATH=" + fileName);
    }

    private Optional<ReportProperties> toReportProperties(String json){
        try {
            ObjectMapper om = new ObjectMapper();
            return Optional.ofNullable(om.readValue(json, ReportProperties.class));
        }catch (Exception ex){
            System.err.println(ex.getMessage());
            return Optional.empty();
        }
    }
}
