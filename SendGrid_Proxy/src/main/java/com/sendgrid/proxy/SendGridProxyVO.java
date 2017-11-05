/**
 * @author Nikhil Bharadwaj Ramashasthri
 * @since September-2017
 * @version 1.0
 */
package com.sendgrid.proxy;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sendgrid.Email;
import com.sendgrid.Personalization;

public class SendGridProxyVO
{
	@JsonProperty("fromEmailAddress") private Email fromEmailAddress;
	@JsonProperty("personalizations") private Personalization personalization;
	@JsonProperty("subject") private String subject;
	@JsonProperty("contentType") private String contentType;
	@JsonProperty("emailBody") private String emailBody;
	@JsonProperty("emailCategory") private String emailCategory;
	@JsonProperty("emailSentTimeStamp") private Date emailSentTimeStamp;
	@JsonProperty("messageIdPk") private String messageIdPk;
	@JsonProperty("emailCreatedAt") private BigDecimal emailCreatedAt;
	@JsonProperty("emailFailReason") private String emailFailReason;
	@JsonProperty("emailStatus") private String emailStatus;
	@JsonProperty("checkFlag") private String checkFlag;
	@JsonProperty("accessToken") private String accessToken;
	
	@JsonProperty("accessToken")
	public String getAccessToken()
	{
		return accessToken;
	}
	public void setAccessToken(String accessToken)
	{
		this.accessToken = accessToken;
	}
	@JsonProperty("messageIdPk")
	public String getMessageIdPk()
	{
		return messageIdPk;
	}
	public void setMessageIdPk(String messageIdPk)
	{
		this.messageIdPk = messageIdPk;
	}
	
	@JsonProperty("fromEmailAddress")
	public Email getFromEmailAddress()
	{
		return fromEmailAddress;
	}
	public void setFromEmailAddress(Email fromEmailAddress)
	{
		this.fromEmailAddress = fromEmailAddress;
	}
	
	@JsonProperty("personalizations")
	public Personalization getPersonalization()
	{
		return personalization;
	}
	public void setPersonalization(Personalization personalization)
	{
		this.personalization = personalization;
	}
	
	@JsonProperty("subject")
	public String getSubject()
	{
		return subject;
	}
	public void setSubject(String subject)
	{
		this.subject = subject;
	}
	
	@JsonProperty("contentType")
	public String getContentType()
	{
		return contentType;
	}
	public void setContentType(String contentType)
	{
		this.contentType = contentType;
	}
	
	@JsonProperty("emailBody")
	public String getEmailBody()
	{
		return emailBody;
	}
	public void setEmailBody(String emailBody)
	{
		this.emailBody = emailBody;
	}
	
	@JsonProperty("emailCategory")
	public String getEmailCategory()
	{
		return emailCategory;
	}
	public void setEmailCategory(String emailCategory)
	{
		this.emailCategory = emailCategory;
	}
	
	@JsonProperty("emailSentTimeStamp")
	public Date getEmailSentTimeStamp()
	{
		return emailSentTimeStamp;
	}
	public void setEmailSentTimeStamp(Date emailSentTimeStamp)
	{
		this.emailSentTimeStamp = emailSentTimeStamp;
	}
	
	@JsonProperty("emailCreatedAt")
	public BigDecimal getEmailCreatedAt()
	{
		return emailCreatedAt;
	}
	public void setEmailCreatedAt(BigDecimal emailCreatedAt)
	{
		this.emailCreatedAt = emailCreatedAt;
	}

	@JsonProperty("emailFailReason")
	public String getEmailFailReason()
	{
		return emailFailReason;
	}
	public void setEmailFailReason(String emailFailReason)
	{
		this.emailFailReason = emailFailReason;
	}
	
	@JsonProperty("emailStatus")
	public String getEmailStatus()
	{
		return emailStatus;
	}
	public void setEmailStatus(String emailStatus)
	{
		this.emailStatus = emailStatus;
	}
	
	@JsonProperty("checkFlag")
	public String getCheckFlag()
	{
		return checkFlag;
	}
	public void setCheckFlag(String checkFlag)
	{
		this.checkFlag = checkFlag;
	}
}
