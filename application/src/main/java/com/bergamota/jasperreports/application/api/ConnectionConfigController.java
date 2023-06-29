package com.bergamota.jasperreports.application.api;

import com.bergamota.jasperreports.common.domain.valueobjects.ReportDatabase;
import com.bergamota.jasperreports.domain.application.service.dto.configconnection.CreateConfigConnection;
import com.bergamota.jasperreports.domain.application.service.dto.configconnection.UpdateConfigConnection;
import com.bergamota.jasperreports.domain.application.service.input.services.ConnectionApplicationService;
import com.bergamota.jasperreports.domain.application.service.input.services.ConnectionConfigApplicationService;
import com.bergamota.jasperreports.domain.core.entities.ConnectionConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/connection-config")
@RequiredArgsConstructor
public class ConnectionConfigController {

    private final ConnectionApplicationService connectionApplicationService;
    private final ConnectionConfigApplicationService connectionConfigApplicationService;


    @GetMapping("/{id}")
    public ResponseEntity<ConnectionConfig> findById(@PathVariable Long id){
        var config = connectionConfigApplicationService.findById(id);
        return ResponseEntity.ok(config);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        connectionConfigApplicationService.remove(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/databases")
    public ResponseEntity<ReportDatabase[]> findAvailableDatabases() {
        return ResponseEntity.ok(ReportDatabase.values());
    }
    @GetMapping
    public ResponseEntity<List<ConnectionConfig>> findAll(){
        var configs = connectionConfigApplicationService.findAll();
        return ResponseEntity.ok(configs);
    }

    @PostMapping
    public ResponseEntity<ConnectionConfig> create(@RequestBody CreateConfigConnection connectionConfig){
        var config = connectionConfigApplicationService.create(connectionConfig);
        return ResponseEntity.ok(config);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ConnectionConfig> update(@PathVariable Long id, @RequestBody UpdateConfigConnection connectionConfig){
        var config = connectionConfigApplicationService.update(connectionConfig.withId(id));
        return ResponseEntity.ok(config);
    }

}
