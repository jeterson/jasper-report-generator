package com.bergamota.jasperreports.dataaccess.category.entities;

import com.bergamota.jasperreports.dataaccess.report.entities.ReportEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "category")
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter
@Getter
public class CategoryEntity {

	@Id
	@GeneratedValue(generator="sqlite_category")
	@TableGenerator(name="sqlite_category", table="jsr_sequence",
	    pkColumnName="id", valueColumnName="seq",
	    pkColumnValue="category",
	    initialValue=1, allocationSize=1)
	private Long id;
	private String description;
	private String path;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="parent_category_id", referencedColumnName="id")
	private CategoryEntity parent;

	@OneToMany(mappedBy = "category", fetch = FetchType.EAGER)
	private List<ReportEntity> reports;
	
	
}
