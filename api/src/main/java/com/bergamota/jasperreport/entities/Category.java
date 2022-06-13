package com.bergamota.jasperreport.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.TableGenerator;

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
public class Category {

	@Id	
	@GeneratedValue(generator="sqlite_category")
	@TableGenerator(name="sqlite_category", table="jsr_sequence",
	    pkColumnName="id", valueColumnName="seq",
	    pkColumnValue="category",
	    initialValue=1, allocationSize=1)
	private Long id;
	private String description;
	private String path;
	
	@OneToOne(fetch=FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name="parent_category_id", referencedColumnName="id", nullable = true)  	   
	private Category parent;
	
	
}
