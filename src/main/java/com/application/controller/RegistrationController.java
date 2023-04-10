package com.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.application.model.User;
import com.application.service.RegistrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class RegistrationController {
	 private static final Logger logger = LoggerFactory.getLogger(RegistrationController.class);
	
	@Autowired  
	private RegistrationService service;
	@PostMapping("/registeruser")
	@CrossOrigin(origins="http://localhost:4200")
	public User registerUser(@RequestBody User user ) throws Exception{
		logger.debug("registerUser called with user: {}", user);
		String tempEmailId=user.getEmailId();
		if(tempEmailId !=null && !"".equals(tempEmailId)) {
			User userobj=service.fetchUserByEmailId(tempEmailId);
			if(userobj !=null) {
				throw new Exception("user with "+tempEmailId+" is already exist");
			}
		}
		User userObj=null;
		userObj= service.saveUser( user);
		return userObj;
	}
	@PostMapping("/login")
	@CrossOrigin(origins="http://localhost:4200")
	public User loginUser(@RequestBody User user) throws Exception {
		String tempEmailId=user.getEmailId();
		String tempPass=user.getPassword();
		User userObj=null;
		if(tempEmailId !=null && tempPass !=null) {
			 userObj=service.fetchUserByEmailIdAndPassword(tempEmailId,tempPass);	
		}
		if(userObj ==null) {
			throw new Exception("Bad credentials");
		}
		return userObj;
	}

}
