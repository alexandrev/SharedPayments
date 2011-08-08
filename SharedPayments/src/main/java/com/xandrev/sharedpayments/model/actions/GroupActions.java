package com.xandrev.sharedpayments.model.actions;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


import com.xandrev.sharedpayments.model.Bill;
import com.xandrev.sharedpayments.model.Category;
import com.xandrev.sharedpayments.model.Group;
import com.xandrev.sharedpayments.model.Message;
import com.xandrev.sharedpayments.model.Payment;
import com.xandrev.sharedpayments.model.User;
import com.xandrev.sharedpayments.model.utils.Utilies;

public class GroupActions {

	@PersistenceContext
	private static EntityManager entityManager;
	
	public static Group addGroup(String groupName, String groupDescription, User user) {
		try {
			Group tmpGroup = new Group();
			tmpGroup.setName(groupName);
			tmpGroup = entityManager.find(Group.class, tmpGroup);
			if(tmpGroup == null){
				tmpGroup = new Group();
				tmpGroup.setName(groupName);
				tmpGroup.setDescription(groupDescription);
				tmpGroup.setCreator(user);
				entityManager.persist(tmpGroup);
				entityManager.flush();
				return tmpGroup;
			}
		} catch (Exception ex) {

		}
		return null;
	}
	
	public static boolean addCategory(String groupName, String categoryName, User user) {
		try {
			if(user != null){
				if(groupName != null && !groupName.equals("")){
					Group group = Utilies.findGroup(groupName);
					if(group != null){
						if(group.getCreator().getUserName().equals(user.getUserName())){
							if(group.getCategory(categoryName) == null){
								Category cat = group.addCategory(categoryName);
								entityManager.persist(cat);
								entityManager.persist(group);
								entityManager.flush();
								return true;
							}
						}
					}
				}
			
			
			}
		} catch (Exception ex) {

		}
		return false;
	}
	
	public static boolean editCategory(String groupName, String categoryName, String categoryNewName, User user) {
		try {
			if(user != null){
				if(groupName != null && !groupName.equals("")){
					Group group = Utilies.findGroup(groupName);
					if(group != null){
						if(group.getCreator().getUserName().equals(user.getUserName())){
							Category category = group.getCategory(categoryName);
							if(category != null){
								category.setName(categoryNewName);
								entityManager.persist(category);
								entityManager.flush();
								return true;
							}
						}
					}
				}
			}
		} catch (Exception ex) {

		}
		return false;
	}
	
	public static boolean deleteCategory(String groupName, String categoryName, User user) {
		try {
			if(user != null){
				if(groupName != null && !groupName.equals("")){
					Group group = Utilies.findGroup(groupName);
					if(group != null){
						if(group.getCreator().getUserName().equals(user.getUserName())){
							Category category = group.getCategory(categoryName);
							if(category != null){
								group.removeCategory(category);
								entityManager.persist(group);
								entityManager.flush();
								return true;
							}
						}
					}
				}
			}
		} catch (Exception ex) {

		}
		return false;
	}
	
	public static boolean addUserGroup(String userName, String groupName, User user) {
		try {
			if(user != null){
				if(groupName != null && !groupName.equals("")){
					Group group = Utilies.findGroup(groupName);
					if(group != null){
						if(group.getCreator().getUserName().equals(user.getUserName())){
							if(group.getUser(userName) == null){
								User newUser = Utilies.findUser(userName);
								group.addUser(newUser);
								entityManager.persist(group);
								entityManager.flush();
								return true;
							}
						}
					}
				}
			}
		} catch (Exception ex) {

		}
		return false;
	}
	
	public static boolean addBill(Long groupId,String concept,Long categoryId,Double quantity,User user){
		Group tmpGroup = entityManager.find(Group.class, groupId);
		if(tmpGroup != null){
			if(tmpGroup.containsUser(user)){
				if(concept != null){
					Category tmpCategory = entityManager.find(Category.class,categoryId);
					if(tmpCategory != null){
					Bill bill = new Bill(user);
					bill.setConcept(concept);
					bill.setCategory(tmpCategory);
					tmpGroup.addBill(bill);
					entityManager.persist(tmpGroup);
					entityManager.flush();
					return true;
					}
				}
			}
		}
		return false;
	}
	
	public static boolean addPayment(Long groupId,String concept,String toUser,Double quantity,User user){
		Group tmpGroup = entityManager.find(Group.class, groupId);
		if(tmpGroup != null){
			if(tmpGroup.containsUser(user)){
				if(concept != null){
					User tmpUser = entityManager.find(User.class,toUser);
					if(tmpUser != null){
					Payment payment = new Payment(user);
					payment.setIssue(concept);
					payment.setToUser(user);
					tmpGroup.addPayment(payment);
					entityManager.persist(tmpGroup);
					entityManager.flush();
					return true;
					}
				}
			}
		}
		return false;
	}
	
	public static boolean addMessage(Long groupId,String concept,String toUser,String content,User user){
		Group tmpGroup = entityManager.find(Group.class, groupId);
		if(tmpGroup != null){
			if(tmpGroup.containsUser(user)){
				if(concept != null){
					User tmpUser = entityManager.find(User.class,toUser);
					if(tmpUser != null){
					Message message = new Message(user);
					message.setSubject(concept);
					message.setToUser(user);
					tmpGroup.addMessage(message);
					entityManager.persist(tmpGroup);
					entityManager.flush();
					return true;
					}
				}
			}
		}
		return false;
	}
}
