package com.xandrev.sharedpayments.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="CATEGORY")
@SequenceGenerator(
	    name="SEQ_STORE_4",
	    sequenceName="sequencecategory",
	    initialValue= 100 ,
	    allocationSize=20)
public class Category {
	@Id @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_STORE_4")
	private Long id;
	private String name;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
