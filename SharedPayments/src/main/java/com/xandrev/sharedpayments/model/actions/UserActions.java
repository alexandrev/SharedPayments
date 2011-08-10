package com.xandrev.sharedpayments.model.actions;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.xandrev.sharedpayments.model.User;
import com.xandrev.sharedpayments.model.utils.Utilies;

@ContextConfiguration
public class UserActions {

	@PersistenceContext(unitName="application")
	private EntityManager entityManager;
	
	@Transactional
	public boolean addUser(String userName, String firstName,
			String lastName, String mailAddress, String password) {
		try {
			User user = new User();
			user.setUserName(userName);
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setMailAddress(mailAddress);
			user.setPassword(password);
			entityManager.persist(user);
			entityManager.flush();
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}
	
	public User loginUser(String userName, String password, HttpSession session) {
		try {
			User user = new User();
			user.setUserName(userName);
			User found = entityManager.find(User.class, user.getUserName());
			if(found != null){
				String hashPassword = found.getPassword();
				String otherHashPassword = Utilies.hashPassword(password);
				if(hashPassword != null && hashPassword.equals(otherHashPassword)){
					session.setAttribute("user", found);
					return found;
				}
			}
		} catch (Exception ex) {

		}
		return null;
	}
	
	public boolean disabledUser(String userName,HttpSession session) {
		try {
			User user = new User();
			user.setUserName(userName);
			User found = entityManager.find(User.class, user.getUserName());
			if(found != null){
				User userInSession = (User) session.getAttribute("user");
				if(userInSession != null){
					if(userInSession.getUserName().equals(userName)){
						found.setPassword("-1");
						entityManager.persist(found);
						entityManager.flush();
						session.setAttribute("user", found);
						return true;
					}
					
				}
			}
		} catch (Exception ex) {

		}
		return false;
	}
}
