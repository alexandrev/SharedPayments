package com.xandrev.sharedpayments.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;

@Entity
@Table(name="GROUP_DEF")
public class Group {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String name;
	private String description;
	
	@ManyToMany
	private Collection<User> members;
	@ManyToOne
	private User creator;
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="GROUP_ID")
	private Collection<Category> cateogries;
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="GROUP_ID")
	private Collection<Bill> bills;
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="GROUP_ID")
	private Collection<Payment> payments;
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="GROUP_ID")
	private Collection<Message> messages;
	
	private Hashtable<User,Double> debts;
	
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
	public Collection<User> getMembers() {
		return members;
	}
	public void setMembers(Collection<User> members) {
		this.members = members;
	}
	public User getCreator() {
		return creator;
	}
	public void setCreator(User creator) {
		this.creator = creator;
	}
	public Collection<Category> getCateogries() {
		return cateogries;
	}
	public void setCateogries(Collection<Category> cateogries) {
		this.cateogries = cateogries;
	}
	public Collection<Bill> getBills() {
		return bills;
	}
	public void setBills(Collection<Bill> bills) {
		this.bills = bills;
	}
	public Collection<Payment> getPayments() {
		return payments;
	}
	public void setPayments(Collection<Payment> payments) {
		this.payments = payments;
	}
	public Collection<Message> getMessages() {
		return messages;
	}
	public void setMessages(Collection<Message> messages) {
		this.messages = messages;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void addUser(User newUser) {
		if(members == null){
			members = new ArrayList<User>();
		}
		if(newUser != null){
			if(!members.contains(newUser)){
				members.add(newUser);
			}
		}
	}
	public Category addCategory(String categoryName) {
		if(cateogries == null){
			cateogries = new ArrayList<Category>();
		}
		if(categoryName != null){
			if(getCategory(categoryName) == null){
				Category category = new Category();
				category.setName(name);
				cateogries.add(category);
				return category;
			}
		}
		return null;
		
	}
	public Category getCategory(String categoryName) {
		if(categoryName != null){
			for(Category cat : cateogries){
				if(cat.getName().equals(categoryName)){
					return cat;
				}
			}
		}
		return null;
	}
	public boolean removeCategory(Category category) {
		if(cateogries == null){
			cateogries = new ArrayList<Category>();
		}
		if(category != null){
			if(getCategory(category.getName()) != null){
				cateogries.remove(category);
				return true;
			}
		}
		return false;
		
	}
	public User getUser(String userName) {
		if(userName != null){
			for(User tmp : members){
				if(tmp.getUserName().equals(userName)){
					return tmp;
				}
			}
		}
		return null;
	}
	public void addBill(Bill bill) {
		if(bill != null){
			if(bills == null){
				bills = new ArrayList<Bill>();
			}
			bills.add(bill);
			recaculateDebts(bill);
		}
		
	}
	private void recaculateDebts(Bill bill) {
		if(debts == null){
			debts = new Hashtable<User, Double>();
			for(User user : members){
				debts.put(user, 0.0);
			}
		}
		User payer = bill.getPayer();
		for(User user : members){
			double tmpQuantity =  bill.getQuantity() / members.size();
			if(user.getUserName().equals(payer.getUserName())){
				Double tmpDouble = debts.get(user);
				debts.put(user,tmpDouble - tmpQuantity);
			}else{
				Double tmpDouble = debts.get(user);
				debts.put(user,tmpDouble + tmpQuantity);
			}
		}
		
	}
	public boolean containsUser(User user) {
		if(user != null){
			for(User tmpUser : members){
				if(tmpUser.getUserName().equals(user.getUserName())){
					return true;
				}
			}
		}
		return false;	
	}
	public void addPayment(Payment payment) {
		if(payment != null){
			if(payments == null){
				payments = new ArrayList<Payment>();
			}
			payments.add(payment);
			recaculateDebts(payment);
		}
	}
	private void recaculateDebts(Payment payment) {
		if(debts == null){
			debts = new Hashtable<User, Double>();
			for(User user : members){
				debts.put(user, 0.0);
			}
		}
		User payer = payment.getFromUser();
		User receiver = payment.getToUser();
		
		Double tmpDouble = debts.get(receiver);
		debts.put(receiver, tmpDouble - payment.getQuantity());
		tmpDouble = debts.get(payer);
		debts.put(payer, tmpDouble + payment.getQuantity());
	}
	
	
	public void addMessage(Message message) {
		if(messages == null){
			messages = new ArrayList<Message>();
		}
		if(message != null){
			messages.add(message);
		}
		
	}
	
}
