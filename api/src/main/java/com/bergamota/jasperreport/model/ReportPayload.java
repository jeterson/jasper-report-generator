package com.bergamota.jasperreport.model;

import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ReportPayload {

	
	
	private List<ReportParameter> parameters = new ArrayList<>();
	@ApiModelProperty(example = "reports")
    private String reportsFolderName;
	@ApiModelProperty(example = "Teste")
    private String fileName;
	@ApiModelProperty(example = "false")
    private Boolean deleteAfterDownload;
	@ApiModelProperty(example = "sqlite")
    private String connectionKey;    
	@ApiModelProperty(dataType = "java.util.List")
    private List<?> data;
	@ApiModelProperty(example = "false")
    private boolean useDatasourceAndConnection;
}
