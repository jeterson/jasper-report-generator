package com.bergamota.jasperreports.dataaccess.report.entities;

import com.bergamota.jasperreports.dataaccess.category.entities.CategoryEntity;
import com.bergamota.jasperreports.dataaccess.connectionconfig.entities.ConnectionConfigEntity;
import com.bergamota.jasperreports.domain.core.entities.ReportParameter;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private CategoryEntity category;

    @ManyToOne
    @JoinColumn(name="connection_id", referencedColumnName = "id")
    private ConnectionConfigEntity connection;

    @Column(unique = true)
    private String fileName;

    @OneToMany(mappedBy = "report", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @Builder.Default
    private List<ReportParameterEntity> parameters = new ArrayList<>();

    @OneToMany(mappedBy = "parent", fetch=FetchType.EAGER)
    @Builder.Default
    private Set<ReportEntity> subReports = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="report_parent_id")
    private ReportEntity parent;

    public void fillParametersWithReport(){
        for(var p : parameters){
            p.setReport(this);
            p.fillParameterViewWithParameter();
        }

    }
}
