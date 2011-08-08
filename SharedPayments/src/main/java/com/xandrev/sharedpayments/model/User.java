package com.xandrev.sharedpayments.model;

import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.JoinTable;
import javax.persistence.Table;

@Entity
@Table(name="USER")
public class User {
	@Id
	private String userName;
	private String firstName;
	private String lastName;
	private String mailAddress;
	private String password;
	
	@ManyToMany
    @JoinTable(name="USER_GROUP",
                joinColumns=
                     @JoinColumn(name="USER_ID"),
                inverseJoinColumns=
                     @JoinColumn(name="GROUP_ID")
    )
	private Collection<Group> groupList;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	public Collection<Group> getGroupList() {
		return groupList;
	}

	public void setGroupList(Collection<Group> groupList) {
		this.groupList = groupList;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
}
