package com.bergamota.jasperreports.domain.application.service.dto.configconnection;

import com.bergamota.jasperreports.common.domain.valueobjects.ReportDatabase;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateConfigConnection {
    private String name;
    private String connectionUrl;
    private String password;
    private String username;
    private ReportDatabase database;
}
