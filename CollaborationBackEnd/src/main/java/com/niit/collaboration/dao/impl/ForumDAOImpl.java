package com.niit.collaboration.dao.impl;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.*;
import com.niit.collaboration.dao.ForumDAO;
import com.niit.collaboration.model.Forum;

@Repository("forumDAO")
public class ForumDAOImpl implements ForumDAO
{
	@Autowired
	private SessionFactory sessionFactory;
	
	public ForumDAOImpl(SessionFactory sessionFactory) 
	{
		this.sessionFactory = sessionFactory;
	}


	@Transactional
	public void save(Forum forum) 
	{
		System.out.println("-@@@@@@@@@@@@@@@@@@@@@--------- before .save query fired");
		//System.out.println("in save"+forumDetails.getDescription());
		sessionFactory.getCurrentSession().save(forum);
		System.out.println("-@@@@@@@@@@@@@@@@@@@@@--------- .save query fired");
	}
	
	@Transactional
	public void delete(int ForumId) 
	{
		System.out.println("Before Delete"+ForumId);
		Forum forumToDelete = new Forum();
		forumToDelete.setId(ForumId);
		sessionFactory.getCurrentSession().delete(forumToDelete);
		System.out.println("After Delete"+ForumId);
	}

	
	
	
	@Transactional
	public Forum get(int forumid) 
	{
		String hql = "from Forum  where id=" + "'" + forumid + "'";
		// from user where id = '101'
		System.out.println("in ForumDAOIMPL "+forumid);
		@SuppressWarnings({ })
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
System.out.println("After Proessing Query in ForumDAOImpl ");
		@SuppressWarnings({ "unchecked" })
		List<Forum> listForum = (List<Forum>) query.list();
		
		System.out.println("After retriving list");
		
		if (listForum != null && !listForum.isEmpty()) {
			return listForum.get(0);
		}
		return null;
	}
	
	public List<Forum> list() {
		Session session = sessionFactory.openSession();
		@SuppressWarnings("unchecked")

		List<Forum> forumDetailsList = session.createQuery("from Forum").list();
		System.out.println("--------->>>>>> Query Fired");
		session.close();
		System.out.println("--------->>>>>> Returning Forum Details");
		return forumDetailsList;
	}


}
	
