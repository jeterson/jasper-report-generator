package com.bergamota.jasperreport.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bergamota.jasperreport.entities.ReportConnection;

public interface ReportConnectionRepository extends JpaRepository<ReportConnection, Long> {

	ReportConnection findByName(String name);
}
