/**
 * @author Nikhil Bharadwaj Ramashasthri
 * @since September-2017
 * @version 1.0
 */
package com.sendgrid.proxy;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
@Repository
public class SendGridProxySDO {
	/* Autowiring the classes starts */
	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	/* Autowiring the classes ends */

	/* Defining loggers starts */
	static final Logger logger = Logger.getLogger(SendGridProxySDO.class);
	/* Defining loggers ends */

	/*
	 * Saves record in database
	 */
	protected void saveToDatabase(Object object) throws Exception {
		try {
			this.sessionFactory.getCurrentSession().save(object);
		} catch (Exception ex) {
			logger.error("saveToDatabase failed due to ==> " + ex);
		}
	}

	/**
	 * It is used to fetch all the records from a table in database in a list
	 * 
	 * @param classType
	 * @return Class
	 */
	public <T> List<T> getList(Class<T> classType) {
		@SuppressWarnings("unchecked")
		List<T> classObject = (List<T>) this.sessionFactory.getCurrentSession().createCriteria(classType)
				.add(Restrictions.isNull("checkFlag")).list();
		return classObject;
	}

}
