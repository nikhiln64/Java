package com.token.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.token.bo.TokenServiceBO;
import com.token.utilities.TokenServiceModel;
import com.token.utilities.TokenUtil;
import com.token.utilities.TokenUtil.TokenStatus;

@Service
public class TokenServiceSO {

	@Autowired
	TokenServiceSDO tokenServiceSDO;

	TokenServiceBO maptoken;
	TokenUtil tokenUtil;

	public TokenServiceModel tokenCreate(TokenServiceModel tokenServiceModel) {
		String token = null;
		maptoken = new TokenServiceBO();
		try {
			/* Creating UUID based token */
			token = UUID.randomUUID().toString().toUpperCase();
			maptoken.setToken(token);
			maptoken.setPayload(tokenServiceModel.getPayload());
			maptoken.setAppName(tokenServiceModel.getAppName());
			maptoken.setIpAddress(tokenServiceModel.getIpAddress());
			maptoken.setUserId(tokenServiceModel.getUserId());
			tokenServiceModel.setToken(token);
			if (tokenServiceModel.getLifeTime() != null) {
				int lifeTimeIntVal = tokenServiceModel.getLifeTime().multiply(new BigDecimal(60)).intValue();
				BigDecimal tokenLifeTime = new BigDecimal(lifeTimeIntVal);
				maptoken.setLifeTime(tokenLifeTime);
				tokenServiceModel.setLifeTime(tokenLifeTime);
			} else {
				maptoken.setLifeTime(null);
			}
			if (tokenServiceSDO.pushToDatabase(maptoken)) {
				return tokenServiceModel;
			}
		} catch (Exception e) {
			System.out.println("Token Service Failed " + e);
			token = null;
		}
		return tokenServiceModel;
	}

	public TokenServiceModel tokenValidate(String token) {
		maptoken = new TokenServiceBO();
		tokenUtil = new TokenUtil();
		TokenServiceModel tokenServiceModel = new TokenServiceModel();
		try {
			Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
			maptoken = tokenServiceSDO.getFromDatabase(TokenServiceBO.class, token);
			if (maptoken != null) {
				/*
				 * Check if status is equal to 0 then Validate provided time
				 * period
				 */
				if (maptoken.getLifeTime() != null && maptoken.getCount() == TokenStatus.INITIAL.getStatusId()) {
					long createdTimeStampDiff = TimeUnit.MILLISECONDS
							.toMinutes(currentTimestamp.getTime() - maptoken.getTimeStamp().getTime());
					if (createdTimeStampDiff < maptoken.getLifeTime().intValue()) {
						tokenServiceModel.setPayload(maptoken.getPayload());
					} else {
						// Delete token in DB and commit
						maptoken.setCount(TokenStatus.DELETED.getStatusId());
						tokenServiceModel.setMessage(TokenUtil.TOKEN_EXPIREY_MESSAGE);
					}
				} else {
					// Validate the 5 min time period
					if (maptoken.getCount() == TokenStatus.INITIAL.getStatusId()) {
						// Save payload into local variable
						tokenServiceModel.setPayload(maptoken.getPayload());
						maptoken.setCount(TokenStatus.DELETED.getStatusId());
					} else {
						// Delete token in DB and commit
						maptoken.setCount(TokenStatus.DELETED.getStatusId());
						tokenServiceModel.setMessage(TokenUtil.TOKEN_EXPIREY_MESSAGE);
					}
				}
				if (tokenServiceSDO.pushToDatabase(maptoken)) {
					return tokenUtil.convertBOtoVO(tokenServiceModel,maptoken);
				}
			}
		} catch (Exception e) {
			System.out.println("Token Service Failed " + e);
			return tokenUtil.convertBOtoVO(tokenServiceModel,maptoken);
		}
		return tokenUtil.convertBOtoVO(tokenServiceModel,maptoken);
	}

}
