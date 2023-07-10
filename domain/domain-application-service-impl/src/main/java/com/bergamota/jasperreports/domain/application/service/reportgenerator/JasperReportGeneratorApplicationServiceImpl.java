package com.bergamota.jasperreports.domain.application.service.reportgenerator;

import com.bergamota.jasperreports.common.domain.utils.ParseUtils;
import com.bergamota.jasperreports.domain.application.service.common.ReportGeneratorConfig;
import com.bergamota.jasperreports.domain.application.service.input.services.*;
import com.bergamota.jasperreports.domain.core.entities.Report;
import com.bergamota.jasperreports.domain.core.entities.ReportConfig;
import com.bergamota.jasperreports.domain.core.entities.report.GenerateReportParameter;
import com.bergamota.jasperreports.domain.core.entities.report.ReportProperties;
import com.bergamota.jasperreports.domain.core.entities.report.ReportPropertiesSubReport;
import com.bergamota.jasperreports.domain.core.exceptions.ReportDomainException;
import com.bergamota.jasperreports.domain.core.valueobjects.ReportParamType;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.base.JRBaseSubreport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRSaver;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.util.*;


@Service
@RequiredArgsConstructor
@Slf4j
public class JasperReportGeneratorApplicationServiceImpl implements JasperReportGeneratorApplicationService{

    private final ReportGeneratorConfig reportGeneratorConfig;
    private final FileSystemApplicationService fileSystemService;
    private final ExportPDFApplicationService exportPDFApplicationService;
    private final ConnectionApplicationService connectionApplicationService;
    private final DownloadApplicationService downloadApplicationService;
    private final ConnectionConfigApplicationService connectionConfigApplicationService;

    private final Map<String, Object> params = new HashMap<>();

    private ReportConfig config(){
        return reportGeneratorConfig.findDefault();
    }
    private String getPathFile(ReportProperties p) {
        return config().getBasePath() + fileSystemService.separator()
                + p.getFolderReportsName()
                + fileSystemService.separator()
                + p.getReportFileName()
                + reportGeneratorConfig.getTypeFileInput();
    }
    private String getCompiledPath(ReportProperties p) {
        return config().getBasePath()
                + fileSystemService.separator()
                + p.getFolderReportsName()
                + fileSystemService.separator()
                + config().getGeneratedReportBasePath();
    }
    private String getCompiledFileName(String compiledPath) {
        return compiledPath + fileSystemService.separator()
                + System.currentTimeMillis()
                + reportGeneratorConfig.generatedJasperFileName();
    }
    private List<String> compileSubReport(ReportProperties prop) throws JRException, java.io.FileNotFoundException {
        var subReportCompiledFiles = new ArrayList<String>();
        for(ReportPropertiesSubReport subRpt: prop.getSubReports()) {

            var fileName = subRpt.getFileName();
            var fullName = subRpt.getFilePath() + fileSystemService.separator() + fileName;
            var filePath = subRpt.getFilePath();
            var paramName = fileSystemService.getFileName(fileName);

            log.info("Compiling subReport {}", fullName);
            var file = fileSystemService.createFile(fullName);

            if(!file.exists())
                throw new FileNotFoundException(fullName);

            var compiled = JasperCompileManager.compileReport(new FileInputStream(file));
            log.info("SubReport compiled");
            var compiledFileName = filePath + fileSystemService.separator()
                    + config().getGeneratedReportBasePath()
                    + fileSystemService.separator()
                    +fileName+System.currentTimeMillis()+".jasper";

            log.info("saving SubReport {}", compiledFileName);
            JRSaver.saveObject(compiled, compiledFileName);
            log.info("Saved.");
            params.put(paramName, compiledFileName);
            log.info("Param " + paramName + " added in report");
            subReportCompiledFiles.add(compiledFileName);

        }
        return subReportCompiledFiles;

    }


    private Map.Entry<String, Object> createEntrySet(String key, Object value) {
        return new Map.Entry<String, Object>() {
            @Override
            public Object setValue(Object value) {
                return value;
            }

            @Override
            public Object getValue() {
                return value;
            }

            @Override
            public String getKey() {
                return key;
            }
        };
    }
    private Map.Entry<String, Object> handleWhenTypeIsDate(Object value, String name, ReportParamType type, ReportParamType reportType, String pattern) {
        if(pattern == null || pattern.isBlank())
            throw new ReportDomainException(String.format("Parameter %s is a dateType, but not contain a pattern of date", name));

        if(reportType == ReportParamType.DATE) {
            var cr = ParseUtils.parseDateUtil(value, pattern);
            return createEntrySet(name, cr.orElse(null));
        }else {
            var cr = ParseUtils.parseDateUtil(value, pattern);
            if(cr.isPresent())
                return createEntrySet(name, ParseUtils.parseString(value));
            else
                return createEntrySet(name, null);
        }

    }

    private Map.Entry<String, Object> convertToEntryMap(GenerateReportParameter parameter){
        var value = parameter.getValue();
        var name = parameter.getName();
        var reportType = parameter.getReportType();
        var type = parameter.getType();
        String pattern = parameter.getDatePattern();

        switch (type) {
            case DATE -> {
                return handleWhenTypeIsDate(value, name, type, reportType, pattern);
            }
            case INTEGER -> {
                return createEntrySet(name, ParseUtils.parseInteger(value).orElse(null));
            }
            case DECIMAL -> {
                return createEntrySet(name, ParseUtils.parseDouble(value).orElse(null));
            }
            case BOOLEAN -> {
                var cr = ParseUtils.parseToYesNo(value);
                return createEntrySet(name, cr.orElse("N"));
            }
            case ARRAY -> {
                return createEntrySet(name, ParseUtils.toCollection(value));
            }
            default -> {
                return createEntrySet(name, ParseUtils.parseString(value));
            }
        }

    }

    private void fillReportSystemParams() {
        params.put("context", config().getBasePath());
        params.put("imagesPath", config().getImagePath());
        params.put("os", System.getProperty("os.name"));
        params.put("user", System.getProperty("user.name"));
        params.put("lang", System.getProperty("user.language"));
    }

    private void fillReportParamsMap(List<GenerateReportParameter> parameters) {
        parameters.forEach(p -> {
            var entrySet = convertToEntryMap(p);
            params.put(entrySet.getKey(), entrySet.getValue());
        });

        fillReportSystemParams();

    }

    protected void fillSubReports(JasperReport jasperReport, ReportProperties prop) {

        // if already exists subReports in list, use it.
        if(prop.getSubReports().size() > 0)
            return;

        List<ReportPropertiesSubReport> subReports = new ArrayList<ReportPropertiesSubReport>();
        for(JRBand band: jasperReport.getAllBands()) {
            var subReportsOfBand = band.getChildren()
                    .stream()
                    .filter(c -> c instanceof JRBaseSubreport)
                    .toList();

            for(JRChild child: subReportsOfBand) {
                JRBaseSubreport subRpt = (JRBaseSubreport) child;
                var chunk = Arrays.stream(subRpt.getExpression().getChunks()).findFirst().orElse(null);
                if(chunk != null) {
                    String fileNameChunk = chunk.getText();
                    subReports.add(new ReportPropertiesSubReport(prop.getFolderReportsName(), fileNameChunk));
                }
            }
        }
        prop.setSubReports(subReports);
    }

    protected ReportProperties getReportProperties(Report report) {
        var properties = new ReportProperties();
        var connection = connectionApplicationService.getConnection(report.getConnectionConfig());
        if(connection.isEmpty())
            throw new ReportDomainException("Can't get connection of database");

        properties.setReportConnectionId(report.getConnectionConfig().getId());
        properties.setDeleteAfterDownload(true);
        properties.setReportFileName(fileSystemService.getFileName(report.getFileName()));
        properties.setFolderReportsName(report.getFilePath().replace(config().getBasePath(), ""));
        properties.setUseConnection(true);
        properties.setData(null);
        report.getSubReports().forEach(subRpt -> {
            properties.getSubReports().add(new ReportPropertiesSubReport(subRpt.getFilePath(), subRpt.getFileName()));
        });

        return properties;
    }

    @SneakyThrows
    public String generateReport(ReportProperties properties)  {
        return generateReport(properties, null);
    }

    @SneakyThrows
    public String generateReport(Report report, List<GenerateReportParameter> parameters) {
        var properties = getReportProperties(report);
        properties.setReportParams(parameters);
        return generateReport(properties);
    }

    private Connection getReportConnection(ReportProperties reportProperties) {
        if(!reportProperties.isUseConnection())
            return null;
        var connectionConfig = connectionConfigApplicationService.findById(reportProperties.getReportConnectionId());
        var connection = connectionApplicationService.getConnection(connectionConfig);
        if(connection.isEmpty())
            throw new ReportDomainException("Can't get connection for report");

        return connection.get();
    }

    @SneakyThrows
    public String generateReport(ReportProperties reportProperties, HttpServletResponse response) {
        Connection connection = getReportConnection(reportProperties);
        List<String> subReportCompiledFiles = null;
        String compiledFileName = "";
        var data = reportProperties.getData();

        try {
            log.info("Generating report {}", reportProperties.getReportFileName());
            fillReportParamsMap(reportProperties.getReportParams());

            var pathFile = getPathFile(reportProperties);
            var compiledPath = getCompiledPath(reportProperties);
            fileSystemService.createDirectoryIfNotExists(compiledPath);
            fileSystemService.createDirectoryIfNotExists(config().getBasePath() + fileSystemService.separator()  + reportProperties.getFolderReportsName());

            var file = fileSystemService.createFile(pathFile);
            var reportStream = new FileInputStream(file);
            log.info("Compiling jasper...");
            var jasperReport = JasperCompileManager.compileReport(reportStream);
            fillSubReports(jasperReport, reportProperties);
            compiledFileName = getCompiledFileName(compiledPath);
            JRSaver.saveObject(jasperReport, compiledFileName);
            log.info("Jasper compiled");
            subReportCompiledFiles = compileSubReport(reportProperties);

            JasperPrint jasperPrint;

            if(reportProperties.isUseConnection()) {
                log.info("No datasource is passed. Using external datasource");
                jasperPrint = JasperFillManager.fillReport(jasperReport, params, connection);
            }else {
                log.info("Using local datasource. {} items", data.size());
                jasperPrint = JasperFillManager.fillReport(jasperReport, params, new JRBeanCollectionDataSource(data));
            }

            String outputFilePath = exportPDFApplicationService.export(reportProperties, jasperPrint);
            if(response != null) {
                log.info("Put binary on response");
                downloadApplicationService.download(response, outputFilePath, reportProperties.isDeleteAfterDownload());
            }else {
                log.info("Report generated");
            }
            return outputFilePath;

        }catch (Exception e){
            log.error(e.getMessage(), e);
            throw new ReportDomainException(e.getMessage(), e);
        }
        finally {
            deleteFiles(subReportCompiledFiles);
            deleteFiles(List.of(compiledFileName));

            if( connection != null && !connection.isClosed())
                connection.close();

        }
    }

    private void deleteFiles(List<String> files) {
        if(files == null)
            return;

        for(String file : files) {
            var f = fileSystemService.createFile(file);
            if(f.exists())
                f.delete();
        }
    }
}
