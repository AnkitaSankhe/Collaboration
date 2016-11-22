package com.niit.collaboration.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Entity
@Component
@Table(name="JOB")
public class Job 
{
@Id
@Column
private int id;

@Column
private String job_type;

@Column
private String company_name;

@Column
private String description;

@Column
private String location;

@Column
private String salary;

@Column
private int workinghour;

public int getId() {
	return id;
}

public void setId(int id) {
	this.id = id;
}

public String getJob_type() {
	return job_type;
}

public void setJob_type(String job_type) {
	this.job_type = job_type;
}

public String getCompany_name() {
	return company_name;
}

public void setCompany_name(String company_name) {
	this.company_name = company_name;
}

public String getDesc() {
	return description;
}

public void setDesc(String description) {
	this.description= description;
}

public String getLocation() {
	return location;
}

public void setLocation(String location) {
	this.location = location;
}

public String getSalary() {
	return salary;
}

public void setSalary(String salary) {
	this.salary = salary;
}

public int getWorkinghour() {
	return workinghour;
}

public void setWorkinghour(int workinghour) {
	this.workinghour = workinghour;
}


}
