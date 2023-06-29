package com.bergamota.jasperreports.domain.application.service.reportgenerator;

import com.bergamota.jasperreports.domain.application.service.common.ReportGeneratorConfig;
import com.bergamota.jasperreports.domain.application.service.input.services.ExportPDFApplicationService;
import com.bergamota.jasperreports.domain.application.service.input.services.FileSystemApplicationService;
import com.bergamota.jasperreports.domain.core.entities.report.ReportProperties;
import com.bergamota.jasperreports.domain.core.exceptions.ExportPDFDomainException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.export.SimplePdfReportConfiguration;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExportPDFApplicationServiceImpl implements ExportPDFApplicationService {

    private final ReportGeneratorConfig reportGeneratorConfig;
    private final FileSystemApplicationService fileSystemService;


    @Override
    public String export(ReportProperties properties, JasperPrint jasperPrint) {
        JRPdfExporter exporter = new JRPdfExporter();
        var defaultConfig = reportGeneratorConfig.findDefault();
        var fileName = getExportedFileName(properties.getReportFileName()) + reportGeneratorConfig.getTypeFileOutput();
        var exportPath = defaultConfig.getBasePath()
                + fileSystemService.separator()
                + properties.getFolderReportsName()
                + fileSystemService.separator()
                + defaultConfig.getGeneratedReportBasePath()
                + fileSystemService.separator()
                + properties.getFolderReportsName();
        var exportFileName = exportPath + fileSystemService.separator() + fileName;

        fileSystemService.createDirectoryIfNotExists(exportPath);
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));

        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(exportFileName));

        SimplePdfReportConfiguration simplePdfExporterConfiguration = new SimplePdfReportConfiguration();
        simplePdfExporterConfiguration.setForceLineBreakPolicy(false);

        SimplePdfExporterConfiguration exporterConfiguration = new SimplePdfExporterConfiguration();
        exporterConfiguration.setMetadataAuthor(System.getProperty("user.name"));

        exporter.setConfiguration(simplePdfExporterConfiguration);
        exporter.setConfiguration(exporterConfiguration);


        try {
            exporter.exportReport();
            return exportFileName;
        }catch (Exception e) {
            log.error("Error when trying export pdf of file " + exportFileName, e);
            throw new ExportPDFDomainException("Error when trying export pdf of file " + exportFileName, e);
        }

    }

    private String getExportedFileName(String filename) {
        var dateNow = LocalDateTime.now();
        return String.format("%s %s-%s-%s-%s-%s-%s", filename, dateNow.getYear(), dateNow.getMonthValue(), dateNow.getDayOfMonth(), dateNow.getHour(), dateNow.getMinute(), dateNow.getSecond());
    }
}
