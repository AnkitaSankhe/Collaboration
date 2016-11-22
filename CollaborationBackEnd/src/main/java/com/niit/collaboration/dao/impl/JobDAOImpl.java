package com.niit.collaboration.dao.impl;
import java.util.List;



import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.*;
import com.niit.collaboration.dao.JobDAO;
import com.niit.collaboration.model.Job;

@Repository
public class JobDAOImpl implements JobDAO
{

	@Autowired
	SessionFactory sessionFactory;
	
	
	@Transactional
	public void addJob(Job job) {
		Session session = sessionFactory.openSession();	
		session.saveOrUpdate(job);
		session.flush();
		session.close();
		
		
	}

	@Transactional
	public void editJob(Job job) {
		Session session = sessionFactory.openSession();
		session.saveOrUpdate(job);

		session.flush();
		session.close();
	}

	@Transactional
	public void deleteJob(int id) {
		sessionFactory.openSession().createQuery("DELETE FROM Job WHERE id = " + id).executeUpdate();
	}

	@Transactional
	public Job getJobbyId(int id) {
		Session session = sessionFactory.openSession();
		Job job = (Job) session.load(Job.class, id);
		return job;
	}

	public List<Job> list() 
	{
		Session session = sessionFactory.openSession();
		@SuppressWarnings("unchecked")

		List<Job> jobDetailsList = session.createQuery("from Job").list();
		System.out.println("--------->>>>>> Query Fired");
		session.close();
		System.out.println("--------->>>>>> Returning Job Details");
		return jobDetailsList;
	}
	@Transactional
	public void save(Job jobDetails) 
	{
		System.out.println("-@@@@@@@@@@@@@@@@@@@@@--------- before .save query fired");
		System.out.println("in save"+jobDetails.getCompany_name());
		sessionFactory.getCurrentSession().save(jobDetails);
		System.out.println("-@@@@@@@@@@@@@@@@@@@@@--------- .save query fired");
	}
}
