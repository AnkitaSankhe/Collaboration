package com.niit.collaboration.dao;

import java.util.List;

import com.*;
import com.niit.collaboration.model.Job;

public interface JobDAO
{
	public List<Job> list();
	public void addJob(Job job);
	public void editJob(Job job);
	public void deleteJob(int id);
	public Job getJobbyId(int id);
	public void save(Job job);

}
