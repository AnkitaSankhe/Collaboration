package com.niit.collaboration.controller;

import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.niit.collaboration.dao.ForumDAO;
import com.niit.collaboration.model.Forum;

@RestController
public class ForumController 
{
	@Autowired
	private ForumDAO forumDAO;
	
	
	@Autowired
	private Forum forum;

	@Autowired
	ServletContext req;
	
	
	@GetMapping("/ForumDetails/")
	public ResponseEntity<List<Forum>> listAllUserDetails(){
		List<Forum> forumDetails = forumDAO.list();
		System.out.println(forumDetails);
		if (forumDetails.isEmpty()){
			
			return new ResponseEntity<List<Forum>>(HttpStatus.NO_CONTENT);
			
		}
		
		return new ResponseEntity<List<Forum>>(forumDetails, HttpStatus.OK);
		
	}
	
	
	
	@GetMapping("/forum/{username}")
	public ResponseEntity<Forum> getBlog(@ModelAttribute("username") int id){
		
		Forum Forum= forumDAO.get(id);
		if (Forum == null){
			return new ResponseEntity<Forum>(HttpStatus.NOT_FOUND);
			
		}
		return new ResponseEntity<Forum>(Forum, HttpStatus.OK);
	}
	
	
	@PostMapping("/ForumSave/")
	public ResponseEntity<Void> createForum(@RequestBody Forum forum, UriComponentsBuilder ucBuilder)
	{
	System.out.println("@@@@@@@@@@@@@---------------->>>In Forum Detail");
	System.out.println(forum.getTitle()+"  "+ forum.getDescription());
		/*if(forumDAO.get(forum.getId())!= null)
		{
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}*/
	
		
//		Blog.setStatus("Pending");
		
		forumDAO.save(forum);
//		
//		String subject = "Registration Successfull !";
//		String emailText1= "Dear ";
//		String emailText2= ". We have successfully received your request to be a part of HUB. You have requested to register as ";
//		String emailText3= ". We will shortly notify you about your request approval. Thank You";
//		System.out.println("To: " + Blog.getEmail());
//		SimpleMailMessage email = new SimpleMailMessage();
//		email.setTo(Blog.getEmail());
//		email.setSubject(subject);
//		email.setText(emailText1+Blog.getName()+emailText2+Blog.getRole()+emailText3);
//		
//		mailSender.send(email);
		
		HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/forum/{id}").buildAndExpand(forum.getTitle()).toUri());
	    return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
		
		
	}
	
	
	/*@PutMapping("/Forum/{username}")
	public ResponseEntity<Forum> update(@ModelAttribute("username") int id, @RequestBody Forum forum){
		
		forum = forumDAO.get(id);
		if (forum == null){
			return new ResponseEntity<Forum>(HttpStatus.NOT_FOUND);
			
		}
		forumDAO.save(forum);
		return new ResponseEntity<Forum>(forum, HttpStatus.OK);
	}*/
	
	
	@DeleteMapping("/Forum/{username}")
	public ResponseEntity<Forum> deleteUser(@ModelAttribute("username") int id){
		
		Forum Forum = forumDAO.get(id);
		if (Forum== null){
			return new ResponseEntity<Forum>(HttpStatus.NOT_FOUND);
			
		}
		forumDAO.delete(id);
		return new ResponseEntity<Forum>(HttpStatus.NO_CONTENT);

	}
	
	@PostMapping("/admin/del/{user}")
	public ResponseEntity<Forum> deleteForum(@ModelAttribute("user") int id)
	{
		System.out.println(id);
		System.out.println("inside ForumController delete Forum");
		Forum Forum= forumDAO.get(id);
		System.out.println("inside ForumController "+Forum.getTitle());
		if (Forum == null){
			return new ResponseEntity<Forum>(HttpStatus.NOT_FOUND);
			
		}
		forumDAO.delete(id);
		return new ResponseEntity<Forum>(HttpStatus.NO_CONTENT);

	}

}
