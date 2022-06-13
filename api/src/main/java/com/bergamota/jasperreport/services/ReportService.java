package com.bergamota.jasperreport.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bergamota.jasperreport.entities.Category;
import com.bergamota.jasperreport.entities.Report;
import com.bergamota.jasperreport.exceptions.http.BadRequestException;
import com.bergamota.jasperreport.repositories.ReportRepository;
import com.bergamota.jasperreport.services.base.BaseService;
import com.bergamota.jasperreport.services.base.interfaces.FileSystemService;
import com.bergamota.jasperreport.services.base.interfaces.ReportConfigService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ReportService extends BaseService<Report, Long, ReportRepository> {



	private CategoryService categoryService;
	private ReportConfigService reportConfigService;
	private FileSystemService fileSystemService;
	@Autowired
	public ReportService(ReportRepository repository, CategoryService categoryService, ReportConfigService reportConfigService, FileSystemService fileSystemService) {
		super(repository);		
		this.categoryService = categoryService;
		this.reportConfigService = reportConfigService;
		this.fileSystemService = fileSystemService;
	}

	@Override
	public Report save(Report entity) {	
		entity.getParameters().forEach(p -> {
			p.setReport(entity);
		});

		return super.save(entity);

	}
	

	public void saveWithUpload(Report rpt, MultipartFile file) {
		try {

			Category category;
			
			if(rpt.getParent() != null) {
				var parent = findById(rpt.getParent().getId());
				rpt.setCategory(parent.getCategory());
				rpt.setConnection(parent.getConnection());
				category = rpt.getCategory();
			}else {
				category = categoryService.findById(rpt.getCategory().getId());
			}			
			
			var basePath = this.reportConfigService.getBasePath();
			var fullPath = basePath + fileSystemService.separator() + category.getPath();	
			rpt.setFileName(file.getOriginalFilename());
			rpt.setFilePath(fullPath);			
			fileSystemService.copy(fullPath, file);			
			save(rpt);
		}catch (Exception e) {
			log.error("Error when trying upload file ", e);
			throw new BadRequestException("Error when trying upload file " + e.getMessage(), e);
		}


	}
	
	@Override
	public Report save(Long id, Report report) {
		report.setId(id);
		return save(report);
	}


}
