package com.bergamota.jasperreports.dataaccess;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = {"com.bergamota.jasperreports.dataaccess"})
@EntityScan(basePackages = {"com.bergamota.jasperreports.dataaccess"})
@ComponentScan(basePackages = {"com.bergamota.jasperreports.dataaccess"})
public class DataAccessLayerConfig {
}
