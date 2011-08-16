package com.xandrev.sharedpayments;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.xandrev.sharedpayments.model.User;

@ContextConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class OrderPersistenceTests {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Test
	@Transactional
	public void testSaveUser() throws Exception {
		Session session = sessionFactory.getCurrentSession();
		User order = new User();
		order.setUserName("userName");
		order.setFirstName("aaa");
		order.setLastName("bbb");
		order.setMailAddress("aaa");
		order.setPassword("bbb");
		session.save(order);
		session.flush();
		User tmpUser = (User) session.load(User.class, order.getUserName());
		assertNotNull(tmpUser);
	}


}
