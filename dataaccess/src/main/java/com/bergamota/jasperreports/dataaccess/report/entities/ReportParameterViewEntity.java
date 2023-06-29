package com.bergamota.jasperreports.dataaccess.report.entities;

import com.bergamota.jasperreports.domain.core.entities.ReportParameter;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter
@Getter
@Table(name = "report_parameter_view")
public class ReportParameterViewEntity {

    @Id
    @GeneratedValue(generator="sqlite_report_parameter")
    @TableGenerator(name="sqlite_report_parameter", table="jsr_sequence",
            pkColumnName="id", valueColumnName="seq",
            pkColumnValue="report_parameter",
            initialValue=1, allocationSize=1)
    private Long id;
    private String label;
    private int sortOrder;
    private boolean visible;
    private boolean required;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "report_parameter_id", referencedColumnName = "id")
    private ReportParameterEntity reportParameter;

}
