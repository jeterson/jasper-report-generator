package com.bergamota.jasperreport.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bergamota.jasperreport.entities.EmailTaskDefinition;

public interface EmailTaskDefinitionRepository extends JpaRepository<EmailTaskDefinition, Long> {

}
