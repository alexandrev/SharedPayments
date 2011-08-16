package com.xandrev.sharedpayments.model.utils;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.google.gson.Gson;
import com.xandrev.sharedpayments.model.Bill;
import com.xandrev.sharedpayments.model.Group;
import com.xandrev.sharedpayments.model.Message;
import com.xandrev.sharedpayments.model.Payment;
import com.xandrev.sharedpayments.model.User;

public class Utilies {

	@PersistenceContext
	private static EntityManager entityManager;
	
	public static String hashPassword(String password) {
		return password;
	}

	public static Group findGroup(Long groupId) {
		try{
		Group tmpGroup = null;
		tmpGroup = entityManager.find(Group.class, groupId);
		if(tmpGroup == null){
			return tmpGroup;
		}
		}catch(Exception ex){
			
		}
		return null;
	}

	public static User findUser(String userName) {
		try{
		User tmpUser = new User();
		tmpUser.setUserName(userName);
		tmpUser = entityManager.find(User.class, tmpUser.getUserName());
		if(tmpUser == null){
			return tmpUser;
		}
		}catch(Exception ex){
			
		}
		return null;
	}

	public static String groupToJSON(Collection<Group> groupList) {
		String response = "{\"groupList\":[";
		for(Group group : groupList){
			response +="{\"id\":\""+group.getId()+"\",";
			response +="\"name\":\""+group.getName()+"\",";
			response +="\"description\":\""+group.getDescription()+"\",";
			response +="\"bill_number\":\""+group.getBills().size()+"\",";
			response +="\"payment_number\":\""+group.getPayments().size()+"\",";
			response +="\"message_number\":\""+group.getMessages().size()+"\",";
			response +="\"member_number\":\""+group.getMembers().size()+"\",";
			response +="\"creator\":\""+group.getCreator().getUserName()+"\"},";	
		}
		response=response.substring(0,response.length()-1);
		response +="]}";
		return response;
	}
	
	public static String billsToJSON(Collection<Bill> billList) {
		String response = "{\"billList\":[";
		for(Bill bill : billList){
			response +="{\"id\":\""+bill.getId()+"\",";
			response +="\"concept\":\""+bill.getConcept()+"\",";
			response +="\"category\":\""+bill.getCategory().getName()+"\",";
			response +="\"quantity\":\""+bill.getQuantity()+"\",";
			response +="\"payer\":\""+bill.getPayer().getUserName()+"\",";
			response +="\"date\":\""+bill.getDate().toString()+"},";	
		}
		response=response.substring(0,response.length()-1);
		response +="]}";
		return response;
	}

	
	public static String paymentsToJSON(Collection<Payment> paymentList) {
		String response = "{\"paymentList\":[";
		for(Payment payment : paymentList){
			response +="{\"id\":\""+payment.getId()+"\",";
			response +="\"issue\":\""+payment.getIssue()+"\",";
			response +="\"from\":\""+payment.getFromUser().getUserName()+"\",";
			response +="\"to\":\""+payment.getToUser().getUserName()+"\",";
			response +="\"quantity\":\""+payment.getQuantity()+"\",";
			response +="\"date\":\""+payment.getDate().toString()+"},";	
		}
		response=response.substring(0,response.length()-1);
		response +="]}";
		return response;
	}
	
	public static String messagesToJSON(Collection<Message> messageList) {
		String response = "{\"messageList\":[";
		for(Message message : messageList){
			response +="{\"id\":\""+message.getId()+"\",";
			response +="\"from\":\""+message.getFromUser().getUserName()+"\",";
			response +="\"to\":\""+message.getToUser().getUserName()+"\",";
			response +="\"subject\":\""+message.getSubject()+"\",";
			response +="\"content\":\""+message.getContent()+"\",";
			response +="\"date\":\""+message.getDate().toString()+"},";	
		}
		response=response.substring(0,response.length()-1);
		response +="]}";
		return response;
	}
}
