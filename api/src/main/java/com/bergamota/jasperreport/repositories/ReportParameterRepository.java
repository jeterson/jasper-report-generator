package com.bergamota.jasperreport.repositories;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bergamota.jasperreport.entities.Report;
import com.bergamota.jasperreport.entities.ReportParameter;

public interface ReportParameterRepository extends JpaRepository<ReportParameter, Long>{

	Set<ReportParameter> findByReport(Report report);
}
