package com.token.bo;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;

import com.token.utilities.CustDateDeSerializer;
import com.token.utilities.CustDateSerializer;

@Entity
@Table(name = "POC_TOKEN_AUTHENTICATION", uniqueConstraints =
{
		@ UniqueConstraint(columnNames =
		{
			"TOKEN"
		})
	})
public class TokenServiceBO {

	private String token;
	private String payload;
	private String userId;
	private BigDecimal lifeTime;
	private long count;
	@ DateTimeFormat(pattern = "MM-dd-yyyy")
	private Date timeStamp;
	
	@ Id
	@Column(name = "TOKEN")
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	@Column(name = "PAYLOAD")
	public String getPayload() {
		return payload;
	}
	public void setPayload(String payload) {
		this.payload = payload;
	}
	
	@Column(name = "USER_ID")
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Column(name = "LIFE_TIME")
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
	
	@ Temporal(TemporalType.TIMESTAMP)
	@ Column(name = "TIME_STAMP", updatable = false, insertable = false)
	@ JsonSerialize(using = CustDateSerializer.class)
	public Date getTimeStamp() {
		return timeStamp;
	}
	@ JsonDeserialize(using = CustDateDeSerializer.class)
	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	
	
}
