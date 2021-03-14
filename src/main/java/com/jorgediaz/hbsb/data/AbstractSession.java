package com.jorgediaz.hbsb.data;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractSession {
	
	@Autowired
	private SessionFactory sesionFactory;

	protected Session getSession(){
		return this.sesionFactory.getCurrentSession();
	}
}
