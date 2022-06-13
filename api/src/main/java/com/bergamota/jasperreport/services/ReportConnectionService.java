package com.bergamota.jasperreport.services;

import java.sql.Connection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.bergamota.jasperreport.entities.ReportConnection;
import com.bergamota.jasperreport.exceptions.ConnectionException;
import com.bergamota.jasperreport.exceptions.EntityNotFoundException;
import com.bergamota.jasperreport.exceptions.http.BadRequestException;
import com.bergamota.jasperreport.repositories.ReportConnectionRepository;
import com.bergamota.jasperreport.services.base.interfaces.ConnectionService;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReportConnectionService {

	private final ReportConnectionRepository repository;
	private final EncryptionService encryptionService;
	private final ConnectionService connectionService;

	public ReportConnection save(ReportConnection connection) {
		if(connection.getId() == null && findByName(connection.getName()) != null)
			throw new BadRequestException(String.format("ReportConnection with name %s already exists", connection.getName()));
		
		testConnection(connection);
		
		var encryptedPwd = connection.getPassword() != null ? encryptionService.encrypt(connection.getPassword()) : null;
		var encryptedUser = connection.getUsername() != null ? encryptionService.encrypt(connection.getUsername()) : null;
		
		connection.setPassword(encryptedPwd);
		connection.setUsername(encryptedUser);		
		return repository.save(connection);
	}
	
	public ReportConnection save(Long id, ReportConnection connection) {
		var c = repository.findById(id).orElseGet(null);
		if(c != null) {
			if(c.getConnectionUrl() != null && !c.getPassword().isBlank())
				connection.setPassword(encryptionService.decrypt(c.getPassword()));
			//if(c.getUsername() != null && !c.getUsername().isBlank())
			//	connection.setUsername(encryptionService.decrypt(c.getUsername()));
		}else {
			throw new EntityNotFoundException(ReportConnection.class, "id",id);
		}
			
		connection.setId(id);
		return save(connection);
	}
	
	public List<ReportConnection> findAll() {
		return repository.findAll();
	}
	public List<ReportConnection> findAllWithDecryptedUsername() {
		return repository.findAll().stream().map(c -> {
			String decryptedUsername = c.getUsername() != null ? encryptionService.decrypt(c.getUsername()) : null;
			c.setUsername(decryptedUsername);
			c.setPassword(null);
			return c;
		}).collect(Collectors.toList());
	}
	
	@SneakyThrows
	private void testConnection(ReportConnection reportConnection) {
		Connection conn = null;
		try {
			conn = connectionService.getConnection(reportConnection);
		}catch (ConnectionException e) {
			throw new BadRequestException(e.getMessage(), e);
		}finally {
			if(conn != null && !conn.isClosed())
				conn.close();
		}
		
	}

	public ReportConnection findByName(String name) {
		var connection = repository.findByName(name);
		if(connection != null) {
			connection.setPassword(encryptionService.decrypt(connection.getPassword()));
			connection.setUsername((encryptionService.decrypt(connection.getUsername()))); 			
		}else {
			log.info("None connection will be used. reportConnection is null");

		}
		return connection;
	}

	public boolean deleteByName(String name) {
		var connection = findByName(name);
		if(connection != null) {
			repository.delete(connection);
			return true;
		}else {
			return false;
		}
	}
}
