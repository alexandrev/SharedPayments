package com.xandrev.sharedpayments.model;

import java.util.ArrayList;
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
@Table(name="USER_DEF")
public class User {
	@Id
	private String userName;
	private String firstName;
	private String lastName;
	private String mailAddress;
	private String password;
	
	@ManyToMany
    @JoinTable(name="group_def_user_def",
                joinColumns=
                     @JoinColumn(name="members_username"),
                inverseJoinColumns=
                     @JoinColumn(name="group_def_id")
    )
	private Collection<Group> groupList;

	public User(){
		groupList = new ArrayList<Group>();
	}
	
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
