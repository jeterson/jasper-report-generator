package com.bergamota.jasperreports.domain.core.entities;

import com.bergamota.jasperreports.common.domain.valueobjects.ReportDatabase;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.With;

@Getter
@Builder
@With
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConnectionConfig {
    private Long id;
    private String connectionUrl;
    @Setter
    private String username;
    @Setter
    private String password;
    private String name;
    private ReportDatabase database;
    private ConfigConnectionStatus status;

    public void hiddenCredentials(){
        password = null;
        username = null;
    }

}
