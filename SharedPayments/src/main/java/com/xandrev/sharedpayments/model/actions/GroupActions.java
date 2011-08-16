package com.xandrev.sharedpayments.model.actions;

import java.util.Collection;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import com.xandrev.sharedpayments.model.Bill;
import com.xandrev.sharedpayments.model.Category;
import com.xandrev.sharedpayments.model.Group;
import com.xandrev.sharedpayments.model.Message;
import com.xandrev.sharedpayments.model.Payment;
import com.xandrev.sharedpayments.model.User;
import com.xandrev.sharedpayments.model.utils.Utilies;

@ContextConfiguration
@Controller
public class GroupActions {

	@Autowired
	private SessionFactory sessionFactory;
	
	@RequestMapping(value = "/group.add", method = RequestMethod.GET)
	@ResponseBody
	@Transactional
	public String addGroup(@RequestParam String groupName, @RequestParam String groupDescription, HttpSession session) {
		try {
			User user = (User) session.getAttribute("user");
			Session persSession = sessionFactory.getCurrentSession();
			if(user != null){
			Group tmpGroup = null;
			try{
			tmpGroup = (Group) persSession.load(Group.class, groupName);
			}catch(Exception ex){
				tmpGroup = new Group();
				tmpGroup.setId(10L);
				tmpGroup.setName(groupName);
				tmpGroup.setDescription(groupDescription);
				tmpGroup.setCreator(user);
				tmpGroup.getMembers().add(user);
				persSession.save(tmpGroup);
				//user.getGroupList().add(tmpGroup);
				//persSession.save(user);
				persSession.flush();
				return "{\"add_group\" : { \"status\": \"ok\",\"id\":"+tmpGroup.getId()+"}}";
			}
			}
		} catch (Exception ex) {

		}
		
		return "{\"add_group\" : { \"status\": \"error\",\"message\":\"Error\"}}";
	}
	
	@RequestMapping(value = "/group.category.add", method = RequestMethod.GET)
	@ResponseBody
	@Transactional
	public String addCategory(@RequestParam Long groupId, @RequestParam String categoryName, HttpSession session) {
		try {
			User user = (User) session.getAttribute("user");
			Session persSession = sessionFactory.getCurrentSession();
			if(user != null){
					Group group = Utilies.findGroup(groupId);
					if(group != null){
						if(group.getCreator().getUserName().equals(user.getUserName())){
							if(group.getCategory(categoryName) == null){
								Category cat = group.addCategory(categoryName);
								persSession.save(cat);
								persSession.save(group);
								persSession.flush();
								return "{\"add_category\" : { \"status\": \"ok\",\"id\":"+cat.getId()+"}}";
							}
						}
					}
				
			
			
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "{\"add_category\" : { \"status\": \"error\",\"message\": \"error\"}}";
	}
	
	@RequestMapping(value = "/group.category.edit", method = RequestMethod.GET)
	@ResponseBody
	@Transactional
	public String editCategory(@RequestParam Long groupId, @RequestParam String categoryName, @RequestParam String categoryNewName, HttpSession session) {
		try {
			User user = (User) session.getAttribute("user");
			Session persSession = sessionFactory.getCurrentSession();
			if(user != null){
					Group group = Utilies.findGroup(groupId);
					if(group != null){
						if(group.getCreator().getUserName().equals(user.getUserName())){
							Category category = group.getCategory(categoryName);
							if(category != null){
								category.setName(categoryNewName);
								persSession.persist(category);
								persSession.flush();
								return "{\"edit_category\" : { \"status\": \"ok\",\"id\":"+category.getId()+"}}";
							}
						}
					}
				}
		} catch (Exception ex) {

		}
		return "{\"edit_category\" : { \"status\": \"error\",\"message\": \"error\"}}";
	}
	
	@RequestMapping(value = "/group.category.delete", method = RequestMethod.GET)
	@ResponseBody
	@Transactional String deleteCategory(@RequestParam Long groupId, @RequestParam String categoryName, HttpSession session) {
		try {
			User user = (User) session.getAttribute("user");
			Session persSession = sessionFactory.getCurrentSession();
			if(user != null){
					Group group = Utilies.findGroup(groupId);
					if(group != null){
						if(group.getCreator().getUserName().equals(user.getUserName())){
							Category category = group.getCategory(categoryName);
							if(category != null){
								group.removeCategory(category);
								persSession.save(group);
								persSession.flush();
								return "{\"delete_category\" : { \"status\": \"ok\",\"id\": "+category.getId()+"}}";
							}
						}
					}
			}
		} catch (Exception ex) {

		}
		return "{\"delete_category\" : { \"status\": \"error\",\"message\": \"error\"}}";
	}
	
	@RequestMapping(value = "/group.users.add", method = RequestMethod.GET)
	@ResponseBody
	@Transactional
	public String addUserGroup(@RequestParam String userName, @RequestParam Long groupId, HttpSession session) {
		try {
			User user = (User) session.getAttribute("user");
			Session persSession = sessionFactory.getCurrentSession();
			if(user != null){
					Group group = Utilies.findGroup(groupId);
					if(group != null){
						if(group.getCreator().getUserName().equals(user.getUserName())){
							if(group.getUser(userName) == null){
								User newUser = Utilies.findUser(userName);
								group.addUser(newUser);
								persSession.save(group);
								persSession.flush();
								return "{\"group_user_add\" : { \"status\": \"ok\",\"id\": "+newUser.getUserName()+"}}";
							}
						}
					}
			}
		} catch (Exception ex) {

		}
		return "{\"group_user_add\" : { \"status\": \"error\",\"message\": \"error\"}}";
	}
	
	@RequestMapping(value = "/group.bill.add", method = RequestMethod.GET)
	@ResponseBody
	@Transactional
	public String addBill(@RequestParam Long groupId,@RequestParam String concept, @RequestParam Long categoryId, @RequestParam Double quantity,HttpSession session){
		User user = (User) session.getAttribute("user");
		Session persSession = sessionFactory.getCurrentSession();
		if(user != null){
		Group tmpGroup = (Group) persSession.load(Group.class, groupId);
		if(tmpGroup != null){
			if(tmpGroup.containsUser(user)){
				if(concept != null){
					Category tmpCategory = (Category) persSession.load(Category.class,categoryId);
					if(tmpCategory != null){
					Bill bill = new Bill(user);
					bill.setConcept(concept);
					bill.setCategory(tmpCategory);
					bill.setQuantity(quantity);
					tmpGroup.addBill(bill);
					persSession.save(bill);
					persSession.save(tmpGroup);
					persSession.flush();
					return "{\"group_bill_add\" : { \"status\": \"ok\",\"id\": "+bill.getId()+"}}";
					}
				}
			}
		}
		}
		return "{\"group_bill_add\" : { \"status\": \"error\",\"message\": \"error\"}}";
	}
	
	@RequestMapping(value = "/group.payment.add", method = RequestMethod.GET)
	@ResponseBody
	@Transactional
	public String addPayment(@RequestParam Long groupId, @RequestParam String concept, @RequestParam String toUser, @RequestParam Double quantity,HttpSession session){
		User user = (User) session.getAttribute("user");
		Session persSession = sessionFactory.getCurrentSession();
		if(user != null){
		Group tmpGroup = (Group) persSession.load(Group.class, groupId);
		if(tmpGroup != null){
			if(tmpGroup.containsUser(user)){
				if(concept != null){
					User tmpUser = (User) persSession.load(User.class,toUser);
					if(tmpUser != null){
					Payment payment = new Payment(user);
					payment.setIssue(concept);
					payment.setToUser(user);
					tmpGroup.addPayment(payment);
					persSession.save(payment);
					persSession.save(tmpGroup);
					persSession.flush();
					return "{\"group_payment_add\" : { \"status\": \"ok\",\"id\": "+payment.getId()+"}}";
					}
				}
			}
		}
		}
		return "{\"group_payment_add\" : { \"status\": \"error\",\"message\": \"error\"}}";
	}
	
	@RequestMapping(value = "/group.message.add", method = RequestMethod.GET)
	@ResponseBody
	@Transactional
	public String addMessage(@RequestParam Long groupId,@RequestParam String concept,@RequestParam String toUser,@RequestParam String content,HttpSession session){
		User user = (User) session.getAttribute("user");
		Session persSession = sessionFactory.getCurrentSession();
		if(user != null){
		Group tmpGroup = (Group) persSession.load(Group.class, groupId);
		if(tmpGroup != null){
			if(tmpGroup.containsUser(user)){
				if(concept != null){
					User tmpUser = (User) persSession.load(User.class,toUser);
					if(tmpUser != null){
					Message message = new Message(user);
					message.setSubject(concept);
					message.setToUser(user);
					tmpGroup.addMessage(message);
					persSession.save(message);
					persSession.save(tmpGroup);
					persSession.flush();
					return "{\"group_message_add\" : { \"status\": \"ok\",\"id\": "+message.getId()+"}}";
					}
				}
			}
		}
		}
		return "{\"group_message_add\" : { \"status\": \"error\",\"message\": \"error\"}}";
	}
	
	
	@RequestMapping(value = "/group.list", method = RequestMethod.GET)
	@ResponseBody
	@Transactional
	public String getGroupList(HttpSession session){
		User user = (User) session.getAttribute("user");
		Session persSession = sessionFactory.getCurrentSession();
		if(user != null){
		User tmpGroup = (User) persSession.load(User.class, user.getUserName());
		if(tmpGroup != null){
			Collection<Group> groups = tmpGroup.getGroupList();
			clearCircularReferences(groups);
			return Utilies.groupToJSON(groups);
			
		}
		}
		return "{\"group_list\" : { \"status\": \"error\",\"message\": \"error\"}}";
	}

	private void clearCircularReferences(Collection<Group> tmpGroup) {
		// TODO Auto-generated method stub
		for(Group group : tmpGroup){
			for(User user : group.getMembers()){
				user.setGroupList(null);
			}
		}
		
	}
	
}
