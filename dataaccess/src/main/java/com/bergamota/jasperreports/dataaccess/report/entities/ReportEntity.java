package com.bergamota.jasperreports.dataaccess.report.entities;

import com.bergamota.jasperreports.dataaccess.category.entities.CategoryEntity;
import com.bergamota.jasperreports.dataaccess.connectionconfig.entities.ConnectionConfigEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter
@Getter
@Table(name = "report")
public class ReportEntity {

    @Id
    @GeneratedValue(generator="sqlite_report")
    @TableGenerator(name="sqlite_report", table="jsr_sequence",
            pkColumnName="id", valueColumnName="seq",
            pkColumnValue="report",
            initialValue=1, allocationSize=1)
    private Long id;
    private String filePath;
    private String name;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private CategoryEntity category;

    @ManyToOne
    @JoinColumn(name="connection_id", referencedColumnName = "id")
    private ConnectionConfigEntity connection;

    @Column(unique = true)
    private String fileName;

    @OneToMany(mappedBy = "report", fetch = FetchType.EAGER)
    @Builder.Default
    private Set<ReportParameterEntity> parameters = new HashSet<>();

    @OneToMany(mappedBy = "parent", fetch=FetchType.EAGER)
    @Builder.Default
    private Set<ReportEntity> subReports = new HashSet<>();

    @ManyToOne(cascade=CascadeType.MERGE)
    @JoinColumn(name="report_parent_id")
    private ReportEntity parent;
}
