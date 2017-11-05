/**
 * @author Nikhil Bharadwaj Ramashasthri
 * @since September-2017
 * @version 1.0
 */
package com.sendgrid.proxy;

import java.math.BigDecimal;
import java.sql.Clob;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;

import com.sendgrid.utilities.CustDateDeSerializer;
import com.sendgrid.utilities.CustDateSerializer;

@Entity
@Table(name = "SENDGRID_PROXY_MST")
public class SendGridProxyBO
{
	private String messageIdPk;
	@ DateTimeFormat(pattern = "MM-dd-yyyy")
	private Date emailSentTimeStamp;
	private String fromEmailAddress;
	private String toEmailAddress;
	private String ccEmailAddress;
	private String subject;
	private String contentType;
	private Clob emailBody;
	private String emailCategory;
	private BigDecimal emailCreatedAt;
	private String emailFailReason;
	private String emailStatus;
	private String checkFlag;

	@Id
	@Column(name = "EMAIL_MESSAGE_ID_PK", unique = true, nullable = false)
	public String getMessageIdPk()
	{
		return messageIdPk;
	}
	public void setMessageIdPk(String messageIdPk)
	{
		this.messageIdPk = messageIdPk;
	}
	
	@ Temporal(TemporalType.TIMESTAMP)
	@ Column(name = "EMAIL_SENT_TIMESTAMP", updatable = false, insertable = false)
	@ JsonSerialize(using = CustDateSerializer.class)
	public Date getEmailSentTimeStamp()
	{
		return emailSentTimeStamp;
	}
	@ JsonDeserialize(using = CustDateDeSerializer.class)
	public void setEmailSentTimeStamp(Date emailSentTimeStamp)
	{
		this.emailSentTimeStamp = emailSentTimeStamp;
	}
	
	@Column(name = "FROM_EMAIL_ADDRESS", nullable = true)
	public String getFromEmailAddress()
	{
		return fromEmailAddress;
	}
	public void setFromEmailAddress(String fromEmailAddress)
	{
		this.fromEmailAddress = fromEmailAddress;
	}
	
	@Column(name = "TO_EMAIL_ADDRESS", nullable = true)
	public String getToEmailAddress()
	{
		return toEmailAddress;
	}
	public void setToEmailAddress(String toEmailAddress)
	{
		this.toEmailAddress = toEmailAddress;
	}
	
	@Column(name = "CC_EMAIL_ADDRESS", nullable = true)
	public String getCcEmailAddress()
	{
		return ccEmailAddress;
	}
	public void setCcEmailAddress(String ccEmailAddress)
	{
		this.ccEmailAddress = ccEmailAddress;
	}
	
	@Column(name = "EMAIL_SUBJECT", nullable = true)
	public String getSubject()
	{
		return subject;
	}
	public void setSubject(String subject)
	{
		this.subject = subject;
	}
	
	@Column(name = "EMAIL_CONTENT_TYPE", nullable = true)
	public String getContentType()
	{
		return contentType;
	}
	public void setContentType(String contentType)
	{
		this.contentType = contentType;
	}

	@Column(name = "EMAIL_BODY", nullable = true)
	public Clob getEmailBody()
	{
		return emailBody;
	}
	public void setEmailBody(Clob emailBody)
	{
		this.emailBody = emailBody;
	}
	
	@Column(name = "EMAIL_CATEGORY", nullable = true)
	public String getEmailCategory()
	{
		return emailCategory;
	}

	public void setEmailCategory(String emailCategory)
	{
		this.emailCategory = emailCategory;
	}
	
	@Column(name = "EMAIL_CREATED_AT", nullable = true)
	public BigDecimal getEmailCreatedAt()
	{
		return emailCreatedAt;
	}
	public void setEmailCreatedAt(BigDecimal emailCreatedAt)
	{
		this.emailCreatedAt = emailCreatedAt;
	}
	
	@Column(name = "EMAIL_FAIL_REASON", nullable = true)
	public String getEmailFailReason()
	{
		return emailFailReason;
	}
	public void setEmailFailReason(String emailFailReason)
	{
		this.emailFailReason = emailFailReason;
	}
	
	@Column(name = "EMAIL_STATUS", nullable = true)
	public String getEmailStatus()
	{
		return emailStatus;
	}
	public void setEmailStatus(String emailStatus)
	{
		this.emailStatus = emailStatus;
	}
	
	@Column(name = "CHECK_FLAG", nullable = true)
	public String getCheckFlag()
	{
		return checkFlag;
	}
	public void setCheckFlag(String checkFlag)
	{
		this.checkFlag = checkFlag;
	}
}
