package com.bergamota.jasperreport.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bergamota.jasperreport.entities.ReportConfig;

public interface ReportConfigRepository extends JpaRepository<ReportConfig, String>{

}
