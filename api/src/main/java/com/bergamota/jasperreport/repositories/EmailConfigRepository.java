package com.bergamota.jasperreport.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bergamota.jasperreport.entities.EmailConfig;

public interface EmailConfigRepository extends JpaRepository<EmailConfig, Long>{

}
