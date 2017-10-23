package com.token.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.token.bo.TokenServiceBO;
import com.token.utilities.TokenUtil;
import com.token.utilities.TokenUtil.TokenStatus;

@Service
public class TokenServiceSO {

	@Autowired
	SessionFactory sessionFactory;
	
	TokenServiceBO maptoken = new TokenServiceBO();

	public String tokenCreate(String payLoad, BigDecimal lifeTime, HttpSession session) {
		String token = null;
		maptoken = new TokenServiceBO();
		try {
			Session hibSession = null;
			Transaction tx = null;
			hibSession = sessionFactory.openSession();
			tx = hibSession.beginTransaction();
			token = UUID.randomUUID().toString().toUpperCase();
			maptoken.setToken(token);
			maptoken.setPayload(payLoad);
			if (lifeTime != null) {
				int lifeTimeIntVal=	lifeTime.multiply(new BigDecimal(60)).intValue();
				BigDecimal tokenLifeTime = new BigDecimal(lifeTimeIntVal);
				maptoken.setLifeTime(tokenLifeTime);
			} else {
				maptoken.setLifeTime(null);
			}
			hibSession.save(maptoken);
			tx.commit();
		} catch (Exception e) {
			System.out.println("Token Service Failed " + e);
			token = null;
		}
		return token;
	}
	
	
	public String tokenValidate(String token, HttpSession session) {
		String payLoad = null;
		maptoken = new TokenServiceBO();
		try {
			Session hibSession = null;
			Transaction tx = null;
			hibSession = sessionFactory.openSession();
			tx = hibSession.beginTransaction();
			Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
			maptoken = (TokenServiceBO) hibSession.get(TokenServiceBO.class, token);
			if (maptoken != null) {
				// Check if status is equal to 0 then
					// Validate provided time period
					if (maptoken.getLifeTime() != null && maptoken.getCount() == TokenStatus.INITIAL.getStatusId()) {
						long createdTimeStampDiff = TimeUnit.MILLISECONDS
								.toMinutes(currentTimestamp.getTime() - maptoken.getTimeStamp().getTime());
						if (createdTimeStampDiff < maptoken.getLifeTime().intValue()) {
							// Save payload into local variable
							payLoad = maptoken.getPayload();
							//maptoken.setStatus(1);
						} else {
							// Delete token in DB and commit
							maptoken.setCount(TokenStatus.DELETED.getStatusId());
						}
					} else {
						// Validate the 5 min time period
					if (maptoken.getCount() == TokenStatus.INITIAL.getStatusId()) {
							// Save payload into local variable
							payLoad = maptoken.getPayload();
							maptoken.setCount(TokenStatus.DELETED.getStatusId());
					}else {
							// Delete token in DB and commit
							maptoken.setCount(TokenStatus.DELETED.getStatusId());
						}
				}
				hibSession.saveOrUpdate(maptoken);
				tx.commit();
			}
			hibSession.close();
		} catch (Exception e) {
			System.out.println("Token Service Failed " + e);
			payLoad = null;
		}
		return payLoad;
	}
	
}
