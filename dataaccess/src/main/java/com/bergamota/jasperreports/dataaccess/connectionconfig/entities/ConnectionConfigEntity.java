package com.bergamota.jasperreports.dataaccess.connectionconfig.entities;

import com.bergamota.jasperreports.common.domain.valueobjects.ReportDatabase;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter
@Getter
@Table(name = "report_connection")
public class ConnectionConfigEntity {

    @Id
    @GeneratedValue(generator="sqlite_connection_config")
    @TableGenerator(name="sqlite_connection_config", table="jsr_sequence",
            pkColumnName="id", valueColumnName="seq",
            pkColumnValue="connection_config",
            initialValue=1, allocationSize=1)
    private Long id;
    private String connectionUrl;
    private String username;
    private String password;
    @Column(unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    private ReportDatabase database;
}
