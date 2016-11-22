package com.niit.collaboration.dao;

import java.util.List;

import com.*;
import com.niit.collaboration.model.Forum;

public interface ForumDAO 
{
    public void delete(int forumid);
	public List<Forum> list();
	 public void save(Forum forum);
    public Forum get(int forumid);
}
