package com.xandrev.sharedpayments.model.utils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.xandrev.sharedpayments.model.Group;
import com.xandrev.sharedpayments.model.User;

public class Utilies {

	@PersistenceContext
	private static EntityManager entityManager;
	
	public static String hashPassword(String password) {
		// TODO Auto-generated method stub
		return null;
	}

	public static Group findGroup(String groupName) {
		Group tmpGroup = new Group();
		tmpGroup.setName(groupName);
		tmpGroup = entityManager.find(Group.class, tmpGroup.getId());
		if(tmpGroup == null){
			return tmpGroup;
		}
		return null;
	}

	public static User findUser(String userName) {
		User tmpUser = new User();
		tmpUser.setUserName(userName);
		tmpUser = entityManager.find(User.class, tmpUser.getUserName());
		if(tmpUser == null){
			return tmpUser;
		}
		return null;
	}

}
