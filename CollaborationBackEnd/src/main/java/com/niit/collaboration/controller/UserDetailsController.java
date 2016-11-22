package com.niit.collaboration.controller;


import java.sql.Date;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.niit.collaboration.dao.UserDetailsDAO;

import com.niit.collaboration.model.UserDetails;

import oracle.sql.DATE;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@RestController
public class UserDetailsController {

	@Autowired
	private UserDetailsDAO userDetailsDAO;
	
	
	@Autowired
	private UserDetails userDetails;

	@Autowired
	ServletContext req;
	
	/*@Autowired
    private JavaMailSender mailSender;*/
	 private static final Logger log = LoggerFactory.getLogger(UserDetailsController.class);

	
	@GetMapping("/UserDetails/")
	public ResponseEntity<List<UserDetails>> listAllUserDetails(){
		log.debug("Starting of the method listAllUserDetails" );

		
		List<UserDetails> userDetailsList = userDetailsDAO.list();
		if (userDetailsList.isEmpty()){
			userDetails.setErrorCode("404");
			userDetails.setErrorMessage("No Users are available");
			
		}
		log.debug("Ending of the method listAllUserDetails" );

		return new ResponseEntity<List<UserDetails>>(userDetailsList, HttpStatus.OK);
		
	}
	
	
	@GetMapping("/UserDetails/{id}")
	public ResponseEntity<UserDetails> getUserDetails(@ModelAttribute("id") String id){
		
		UserDetails UserDetails = userDetailsDAO.get(id);
		if (UserDetails == null){
			return new ResponseEntity<UserDetails>(HttpStatus.NOT_FOUND);
			
		}
		return new ResponseEntity<UserDetails>(UserDetails, HttpStatus.OK);
	}
	
	
	
	@PostMapping("/UserSave/")
	public ResponseEntity<Void> createUserDetails(@RequestBody UserDetails userDetails, UriComponentsBuilder ucBuilder)
	{
	System.out.println("@@@@@@@@@@@@@---------------->>>In Save User Detail");
	System.out.println(userDetails.getName()+"  "+ userDetails.getId());
		if(userDetailsDAO.get(userDetails.getId())!= null){
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
	
		
//		userDetails.setStatus("Pending");
		Date todaydate = new Date(Calendar.getInstance().getTime().getTime());
		userDetails.setDatecreated(todaydate);
		userDetails.setEnabled("TRUE");
		userDetails.setUserRole("ROLE_USER");
		userDetails.setErrorCode("200"); // NLP NullPointerException
		userDetails.setErrorMessage("User successfully got created:" + userDetails.getId());
		userDetailsDAO.save(userDetails);
//		
	/*	String subject = "Registration Successfull !";
		String emailText1= "Dear ";
		String emailText2= ". We have successfully received your request to be a part of HUB. You have requested to register as ";
		String emailText3= ". We will shortly notify you about your request approval. Thank You";
		System.out.println("To: " + userDetails.getEmail());
		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(userDetails.getEmail());
		email.setSubject(subject);
		email.setText(emailText1+userDetails.getName()+emailText2+userDetails.getEmail()+emailText3);
		
		mailSender.send(email);*/
		
		HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/UserDetails/{id}").buildAndExpand(userDetails.getName()).toUri());
	    return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
		
		
	}
	@PutMapping("/UserDetails/{id}")
	public ResponseEntity<UserDetails> update(@ModelAttribute("id") String id, @RequestBody UserDetails userDetails){
		
		UserDetails UserDetails = userDetailsDAO.get(id);
		if (UserDetails == null){
			return new ResponseEntity<UserDetails>(HttpStatus.NOT_FOUND);
			
		}
		userDetailsDAO.update(UserDetails);
		return new ResponseEntity<UserDetails>(UserDetails, HttpStatus.OK);
	}
	
	
	@DeleteMapping("/UserDetails/{id}")
	public ResponseEntity<UserDetails> deleteUser(@ModelAttribute("id") String id){
		
		UserDetails UserDetails = userDetailsDAO.get(id);
		if (UserDetails == null){
			return new ResponseEntity<UserDetails>(HttpStatus.NOT_FOUND);
			
		}
		userDetailsDAO.delete(id);
		return new ResponseEntity<UserDetails>(HttpStatus.NO_CONTENT);

	}
	
	@RequestMapping(value = "/logout/", method = RequestMethod.GET)
	public ResponseEntity<UserDetails> logout(HttpSession session) {
		log.debug("Starting of the method logout" );
		
		String loggedInUserID= (String) session.getAttribute("loggedInUserID");
		
		log.debug("loggedInUserID : " + loggedInUserID);
		
		userDetails =userDetailsDAO.get(loggedInUserID);
		
		log.debug("user:"+ userDetails);
		//userDetails.setIsOnline('N');

		session.invalidate();

		//if (userDetailsDAO.update(userDetails)) {
			userDetails.setErrorCode("200");
			userDetails.setErrorMessage("You have logged out successfully");
		/*} else {
			userDetails.setErrorCode("404");
			userDetails.setErrorMessage("You could not logged. please contact admin");
		}*/
		log.debug("Ending of the method logout" );
		return new ResponseEntity<UserDetails>(userDetails, HttpStatus.OK);

	}
//	@PostMapping ("/UserValidate/")
//public ResponseEntity<UserDetails> checkUser(@RequestBody UserDetails userDetails){
//		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@in login method");
//		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@ Received UserId as: "+userDetails.getUserId()+" & password as: "+userDetails.getPassword());
//		userDetails = userDetailsDAO.validate(userDetails.getUserId(), userDetails.getPassword());
//		if (userDetails==null){
//			userDetails = new UserDetails();
//			userDetails.setErrorMessage("Invalid Credentials!");
//			return new ResponseEntity<UserDetails>(userDetails,HttpStatus.OK);
//	}	else if(userDetails.getStatus().equalsIgnoreCase("Pending")){
//		userDetails = new UserDetails();
//		userDetails.setErrorMessage("Your Registration Request is not yet Approved.");
//		return new ResponseEntity<UserDetails>(userDetails,HttpStatus.OK);
//	}
//	else if(userDetails.getStatus().equalsIgnoreCase("Rejected")){
//		userDetails = new UserDetails();
//		userDetails.setErrorMessage("Your Request for Registration has been Rejected!");
//		return new ResponseEntity<UserDetails>(userDetails,HttpStatus.OK);
//	}
//	else
//		return new ResponseEntity<UserDetails>(userDetails, HttpStatus.OK);
//
//	}
	@RequestMapping(value = "/tvalidate/", method = RequestMethod.POST)
	public ResponseEntity<UserDetails> login(@RequestBody UserDetails userDetails, HttpSession session) {
		log.debug("Starting of the method login" );
		
		String id = userDetails.getId();
		userDetails = userDetailsDAO.validate(userDetails.getId(), userDetails.getPassword());
		if (userDetails == null) {
			userDetails = new UserDetails();
			userDetails.setErrorCode("404"); // NLP NullPointerException
			userDetails.setErrorMessage("User does not exist with this id:" + id);
		} else {
			//userDetails.setIsOnline('Y');
			userDetails.setErrorCode("200"); // NLP NullPointerException
			userDetails.setErrorMessage("You have successfully loggedIN");
	
			userDetailsDAO.update(userDetails);

			session.setAttribute("loggedInUserID", userDetails.getId());
		}
		log.debug("Ending of the method login" );
		return new ResponseEntity<UserDetails>(userDetails, HttpStatus.OK);
	}
}
