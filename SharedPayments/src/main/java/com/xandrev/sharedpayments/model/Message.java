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
@Table(name="MESSAGE")
@SequenceGenerator(
	    name="SEQ_STORE_5",
	    sequenceName="sequencemessage",
	    initialValue= 100 ,
	    allocationSize=20)
public class Message {
	@Id @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_STORE_5")
	private Long id;
	@ManyToOne
	private User fromUser;
	@ManyToOne
	private User toUser;
	private String subject;
	private String content;
	private Date date;
	private boolean readed;
	
	
	public Message(){
		
	}
	public Message(User user) {
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
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public boolean isReaded() {
		return readed;
	}
	public void setReaded(boolean readed) {
		this.readed = readed;
	}
	
	
}
