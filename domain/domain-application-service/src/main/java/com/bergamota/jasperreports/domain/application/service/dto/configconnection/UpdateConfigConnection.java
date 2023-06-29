package com.bergamota.jasperreports.domain.application.service.dto.configconnection;

import lombok.Builder;
import lombok.Getter;
import lombok.With;

@Builder
@Getter
public class UpdateConfigConnection {

    @With
    private Long id;
    private String name;
    private String password;
    private String username;
    private String connectionUrl;
}
