package com.bergamota.jasperreport.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bergamota.jasperreport.entities.Report;

public interface ReportRepository extends JpaRepository<Report, Long>{

}
