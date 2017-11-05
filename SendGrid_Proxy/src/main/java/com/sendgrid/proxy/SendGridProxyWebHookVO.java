package com.sendgrid.proxy;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SendGridProxyWebHookVO
{
	private String sg_message_id;
	private String email;
	private BigDecimal timestamp;
	private String smtp_id;
	private String asm_group_id;
	private String url;
	private String category;
	
	
	public String getSg_message_id()
	{
		return sg_message_id;
	}
	public void setSg_message_id(String sg_message_id)
	{
		this.sg_message_id = sg_message_id;
	}
	public String getEmail()
	{
		return email;
	}
	public void setEmail(String email)
	{
		this.email = email;
	}
	public BigDecimal getTimestamp()
	{
		return timestamp;
	}
	public void setTimestamp(BigDecimal timestamp)
	{
		this.timestamp = timestamp;
	}
	public String getSmtp_id()
	{
		return smtp_id;
	}
	public void setSmtp_id(String smtp_id)
	{
		this.smtp_id = smtp_id;
	}
	public String getAsm_group_id()
	{
		return asm_group_id;
	}
	public void setAsm_group_id(String asm_group_id)
	{
		this.asm_group_id = asm_group_id;
	}
	public String getUrl()
	{
		return url;
	}
	public void setUrl(String url)
	{
		this.url = url;
	}
	public String getCategory()
	{
		return category;
	}
	public void setCategory(String category)
	{
		this.category = category;
	}
	
	
	
}
