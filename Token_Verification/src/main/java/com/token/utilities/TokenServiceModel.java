package com.token.utilities;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import org.springframework.format.annotation.DateTimeFormat;


public class TokenServiceModel {

	private String token;
	private String payload;
	private String userId;
	private BigDecimal lifeTime;
	private long count;
	@DateTimeFormat(pattern = "MM-dd-yyyy")
	private Date timeStamp;
	private String ipAddress;
	private String appName;
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public BigDecimal getLifeTime() {
		return lifeTime;
	}

	public void setLifeTime(BigDecimal lifeTime) {
		this.lifeTime = lifeTime;
	}

	@Column(name = "COUNT")
	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

}
