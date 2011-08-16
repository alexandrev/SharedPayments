package com.xandrev.sharedpayments.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="PAYMENT")
@SequenceGenerator(
	    name="SEQ_STORE_3",
	    sequenceName="sequencepayment",
	    initialValue= 100 ,
	    allocationSize=20)
public class Payment {
	@Id @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_STORE_3")
	private Long id;
	@ManyToOne
	private User fromUser;
	@ManyToOne
	private User toUser;
	private String issue;
	private Date date;
	private Double quantity;
	
	public Payment(){
		
	}
	
	public Payment(User user){
		fromUser = user;
		date = new Date();
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public User getFromUser() {
		return fromUser;
	}
	public void setFromUser(User fromUser) {
		this.fromUser = fromUser;
	}
	public User getToUser() {
		return toUser;
	}
	public void setToUser(User toUser) {
		this.toUser = toUser;
	}
	public String getIssue() {
		return issue;
	}
	public void setIssue(String issue) {
		this.issue = issue;
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
