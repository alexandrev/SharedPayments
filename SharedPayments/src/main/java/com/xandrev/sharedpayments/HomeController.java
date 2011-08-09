package com.xandrev.sharedpayments;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import com.xandrev.sharedpayments.model.*;
import com.xandrev.sharedpayments.model.actions.UserActions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Handles requests for the application home page.
 */
@ContextConfiguration
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! the client locale is "+ locale.toString());
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/user.login", method = RequestMethod.GET)
	@ResponseBody
	public String loginUser(@RequestParam String userName, @RequestParam String password,HttpSession session) {
		logger.info("Request to log in a new user with following data: "+ userName);
		if(userName != null && !userName.equals("")){
			if(password != null && !password.equals("")){
				UserActions.loginUser(userName, password,session);
			}
		}
		logger.info("Handled request to log in a new user with following data: "+ userName);
		return "{\"login\": \"ok\"}";
	}
	
	@RequestMapping(value = "/user.add", method = RequestMethod.GET)
	public String addUser(@RequestParam String userName, @RequestParam String firstName, @RequestParam String lastName, @RequestParam String mailAddress, @RequestParam String password) {
		String response = "";
		logger.debug("Request to add a new user with following data: "+userName+" "+ firstName +" "+ lastName +" "+ mailAddress+" "+password);
		if(userName != null && !userName.equals("")){
			if(firstName != null && !firstName.equals("")){
				if(lastName != null && !lastName.equals("")){
					if(mailAddress != null && !mailAddress.equals("")){
						if(password != null && !password.equals("")){
							if(UserActions.addUser(userName, firstName, lastName, mailAddress, password)){
								logger.info("New user added: "+userName);
							}else{
								logger.error("Error adding user: "+userName);
							}
						}
					}
				}
			}
		}
		
		return response;
	}
	
	
	
}
