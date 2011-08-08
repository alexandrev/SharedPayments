package com.xandrev.sharedpayments.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;


@Entity
public class Bill {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String concept;
	@OneToOne
	private Category category;
	@OneToOne
	private User payer;
	
	private Date date;
	
	private Double quantity;
	
	public Bill(User user){
		payer = user;
		date = new Date();
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getConcept() {
		return concept;
	}
	public void setConcept(String concept) {
		this.concept = concept;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public User getPayer() {
		return payer;
	}
	public void setPayer(User payer) {
		this.payer = payer;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}
	
	
	
}
