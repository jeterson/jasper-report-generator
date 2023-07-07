package com.bergamota.jasperreports.dataaccess.report.entities;

import com.bergamota.jasperreports.domain.core.valueobjects.ReportParamType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter
@Getter
@Table(name = "report_parameter")
public class ReportParameterEntity {


    @Id
    @GeneratedValue(generator="sqlite_report_parameter")
    @TableGenerator(name="sqlite_report_parameter", table="jsr_sequence",
            pkColumnName="id", valueColumnName="seq",
            pkColumnValue="report_parameter",
            initialValue=1, allocationSize=1)
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private ReportParamType type;
    @Enumerated(EnumType.STRING)
    private ReportParamType reportType;
    private String pattern;
    private String defaultValue;
    @Column()
    private Boolean createdManually = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "report_id", referencedColumnName = "id")
    private ReportEntity report;

    @OneToOne(mappedBy = "reportParameter", cascade = CascadeType.ALL, orphanRemoval = true)
    private ReportParameterViewEntity reportParameterView;

    public void fillParameterViewWithParameter() {
        if(reportParameterView != null)
            reportParameterView.setReportParameter(this);
    }
}
