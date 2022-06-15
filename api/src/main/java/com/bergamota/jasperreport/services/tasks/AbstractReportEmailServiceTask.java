package com.bergamota.jasperreport.services.tasks;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.context.ApplicationContext;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.bergamota.jasperreport.entities.EmailTaskDefinition;
import com.bergamota.jasperreport.entities.Report;
import com.bergamota.jasperreport.model.ReportParameter;
import com.bergamota.jasperreport.services.EmailConfigService;
import com.bergamota.jasperreport.services.JavaMailerService;
import com.bergamota.jasperreport.services.ReportService;
import com.bergamota.jasperreport.services.base.interfaces.FileSystemService;
import com.bergamota.jasperreport.services.base.interfaces.MailSenderService;
import com.bergamota.jasperreport.services.base.interfaces.ReportGeneratorService;
import com.bergamota.jasperreport.services.base.interfaces.TaskletService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractReportEmailServiceTask implements TaskletService, MailSenderService{

	private EmailTaskDefinition taskDefinition;
	private Long reportId;		


	protected JavaMailSender javaMailSender;

	private ReportGeneratorService generatorService;

	private FileSystemService fileSystemService;

	private ReportService reportService;

	private ApplicationContext context;

	private EmailConfigService emailConfigService;


	public AbstractReportEmailServiceTask(EmailTaskDefinition taskDefinition, ApplicationContext context) {
		this.reportId = taskDefinition.getReferenceId();
		this.taskDefinition = taskDefinition;
		this.context = context;
		
		this.reportService = getInstance(ReportService.class);
		this.fileSystemService = getInstance(FileSystemService.class);
		this.generatorService = getInstance(ReportGeneratorService.class);
		this.emailConfigService = getInstance(EmailConfigService.class);
		this.javaMailSender = javaMailSender();
	}

	public <T> T getInstance(Class<T> classz) {
		return context.getBean(classz);
	}	

	@Override
	public void run() {
		String generateReportPath = "";
		log.info("Start running task {}", taskDefinition.getName());
		try {
			var report = reportService.findById(reportId);
			if(report == null) {
				log.warn("Report with id {} not found", reportId);
				return;
			}
			generateReportPath = generatorService.generateReport(report, entitiesParameterToReportParameter(report.getParameters()));
			var mm = prepareMimeMessageFromPedido(report, generateReportPath);
			sendHtmlEmail(mm);
		}catch(Exception ex) {
			log.error("Error when trying send report email", ex);
		}finally {
			fileSystemService.delete(generateReportPath);
			log.info("Task {} completed", taskDefinition.getName());
		}
	}

	private List<ReportParameter> entitiesParameterToReportParameter(Set<com.bergamota.jasperreport.entities.ReportParameter> entityParameters) {
		List<ReportParameter> parameters = new ArrayList<ReportParameter>();
		entityParameters.forEach(p -> {
			ReportParameter param = new ReportParameter();
			param.setDatePattern(p.getPattern());
			param.setName(p.getName());
			param.setReportType(p.getReportType());
			param.setType(p.getType());
			param.setValue(p.getDefaultValue());
			parameters.add(param);
		});
		return parameters;
	}

	private MimeMessage prepareMimeMessageFromPedido(Report rpt, String filePath) throws MessagingException {
		MimeMessage mm =  javaMailSender.createMimeMessage();

		MimeMessageHelper mmh = new MimeMessageHelper(mm, true);
		mmh.setTo(taskDefinition.getRecipients());
		mmh.setFrom("noreply@gmail.com");
		mmh.setSubject(taskDefinition.getSubject().replace("{{REPORT_NAME}}", rpt.getName()));
		mmh.setSentDate(new Date(System.currentTimeMillis()));
		mmh.setText(taskDefinition.getBody() == null || taskDefinition.getBody().isBlank() ? "Segue em anexo" : taskDefinition.getBody());
		var file = fileSystemService.createFile(filePath);
		mmh.addAttachment(file.getName(), file);

		return mm;
	}

	public JavaMailSender javaMailSender() {
		
		if(JavaMailerService.getInstance().getSender() == null) {
			var emailConfig = emailConfigService.getDefault();						
			JavaMailerService.getInstance().setEmailConfig(emailConfig);
		}		
		return JavaMailerService.getInstance().getSender();

	}

	@Override
	public String getCronExpression() {
		return taskDefinition.getCronExpression();
	}	

}
