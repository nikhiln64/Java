package com.token.service;

import java.io.Serializable;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.token.bo.TokenServiceBO;


@Service
public class TokenServiceSDO {

	@Autowired
	SessionFactory sessionFactory;
	
	
	public boolean pushToDatabase(Object classObject) {
		Session hibSession = null;
		Transaction tx = null;
		hibSession = sessionFactory.openSession();
		tx = hibSession.beginTransaction();
		hibSession.saveOrUpdate(classObject);
		tx.commit();
		if(tx.wasCommitted()){
			return true;
		}
		return false;
	}
	
	
	/**
	 * It is used to fetch record from database based on serializable id
	 * 
	 * @param classType
	 * @param id
	 * @return Class
	 */
	@ SuppressWarnings("unchecked")
	public <T> T getFromDatabase(Class<T> classType, Serializable token)
	{
		Session session = null;
		session = sessionFactory.openSession();
		T classObject = (T) session.get(classType, token);
		session.close();
		return classObject;
	}
	
}
