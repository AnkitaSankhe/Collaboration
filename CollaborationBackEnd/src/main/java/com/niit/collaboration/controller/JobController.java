package com.niit.collaboration.controller;

import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import com.*;
import com.niit.collaboration.dao.JobDAO;
import com.niit.collaboration.model.Job;

@RestController
public class JobController {
	
	@Autowired
	private JobDAO jobDAO;
	
	
	@Autowired
	private Job job;

	@Autowired
	ServletContext req;
	
	@GetMapping("/JobDetails/")
	public ResponseEntity<List<Job>> listAllJobDetails(){
		List<Job> jobDetails = jobDAO.list();
		System.out.println(jobDetails);
		if (jobDetails.isEmpty()){
			
			return new ResponseEntity<List<Job>>(HttpStatus.NO_CONTENT);
			
		}
		
		return new ResponseEntity<List<Job>>(jobDetails, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/JobAdd", method = RequestMethod.GET)
	public ModelAndView addJob(@ModelAttribute("command") Job jobBean, BindingResult result) {
		// System.out.println("$$$$$$$$$$$$$$$$$$$$");

		ModelAndView model = new ModelAndView("JobAdd");
		model.addObject("job",jobBean);
		// System.out.println("Hello1");
		return model;

	}
	
	@PostMapping("/JobSave/")
	public ResponseEntity<Void> createJob(@RequestBody Job job, UriComponentsBuilder ucBuilder)
	{
	System.out.println("@@@@@@@@@@@@@---------------->>>In Job Detail");
	System.out.println(job.getCompany_name()+"  "+ job.getLocation());
		
//		Blog.setStatus("Pending");
		jobDAO.save(job);
		
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
        headers.setLocation(ucBuilder.path("/Job/{id}").buildAndExpand(job.getLocation()).toUri());
	    return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
		
		
	}
	
	/*@RequestMapping(value = "/JobSave", method = RequestMethod.POST)
	public ModelAndView saveJob(@ModelAttribute("job") Job jobBean, BindingResult result)
	{
		jobDAO.addJob(jobBean);
		return new ModelAndView("JobView");
	}*/

	@RequestMapping("/JobView")
	public ModelAndView viewJob() {
		 System.out.println("in view all");
		List<Job> jobList = jobDAO.list();
		 System.out.println("end view all");
		return new ModelAndView("JobView", "jobList", jobList);

	}
	@RequestMapping("/admin/JobDelete")
	public String deleteJob(@RequestParam("id") int id) {
		jobDAO.deleteJob(id);
		return "redirect:/chat/JobView";

	}
	
	 @RequestMapping("/admin/JobEdit")  
	  public ModelAndView editJob(@RequestParam int id, @ModelAttribute Job job) {
		 System.out.println("Hello");
	   Job jobObject =jobDAO.getJobbyId(id);
	   System.out.println("Hello1");
	  // System.out.println("EditMovie:"+movieObject.getMoviename());
	   return new ModelAndView("JobEdit", "job", jobObject);  
	  }  
	   
	  @RequestMapping("/admin/updateJob")  
	  public ModelAndView updateJob(@ModelAttribute("job") Job job,BindingResult result) 
	  {   
		  jobDAO.editJob(job);
		  return new ModelAndView("redirect:/chat/JobView");  

	 }
	  


	@RequestMapping("/JobViewRecord")
	public ModelAndView getRecord(@RequestParam int id, @ModelAttribute Job job) {
		Job jobObject = jobDAO.getJobbyId(id);
		System.out.println("Hello");
		return new ModelAndView("JobViewRecord", "jobObject", jobObject);
	}

}
