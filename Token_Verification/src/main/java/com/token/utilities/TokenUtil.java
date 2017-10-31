package com.token.utilities;

import com.token.bo.TokenServiceBO;
import com.token.utilities.TokenUtil.TokenStatus;

public class TokenUtil {
	// if token life time is null, then default token expiry value is 5 minutes
	public static final long DEFAULT_TOKEN_EXPIRY = 300000;
	public static final String TOKEN_EXPIREY_MESSAGE = "Expired Token";
	public static final String INVALID_IP_ADDRESS = "Invalid IP Address";

	/**
	 * @author Nikhil Bharadwaj Ramashasthri
	 * @since October-2017
	 * @version 1.0
	 */
	public enum TokenStatus {
		INITIAL(0), DELETED(-2), USED(-1);
		private int statusId;

		TokenStatus(int statusId) {
			this.statusId = statusId;
		}

		public int getStatusId() {
			return statusId;
		}
	}

	public TokenServiceModel convertBOtoVO(TokenServiceModel tokenServiceModel, TokenServiceBO maptoken) {
		if (maptoken != null) {
			if (TokenStatus.DELETED.getStatusId() == maptoken.getCount()) {
				tokenServiceModel.setMessage(TOKEN_EXPIREY_MESSAGE);
				return tokenServiceModel;
			}
			if (maptoken.getAppName() != null)
				tokenServiceModel.setAppName(maptoken.getAppName());
			if (maptoken.getIpAddress() != null)
				tokenServiceModel.setIpAddress(maptoken.getIpAddress());
			if (maptoken.getUserId() != null)
				tokenServiceModel.setUserId(maptoken.getUserId());
			if (maptoken.getLifeTime() != null)
				tokenServiceModel.setLifeTime(maptoken.getLifeTime());
			if (maptoken.getToken() != null)
				tokenServiceModel.setToken(maptoken.getToken());
			if (maptoken.getTimeStamp() != null)
				tokenServiceModel.setTimeStamp(maptoken.getTimeStamp());
		}
		return tokenServiceModel;
	}
}
