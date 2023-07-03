package com.bergamota.jasperreports.domain.core.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfigConnectionStatus {
    private boolean ok;
    private String message;
}
