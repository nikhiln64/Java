/**
 * @author Nikhil Bharadwaj Ramashasthri
 * @since October-2017
 * @version 1.0
 */

package com.sharepoint.connect;

class AccessToken
{
	private String	token_type;
	private String	expires_in;
	private String	not_before;
	private String	expires_on;
	private String	resource;
	private String	access_token;

	public String getToken_type()
	{
		return token_type;
	}

	public void setToken_type(String token_type)
	{
		this.token_type = token_type;
	}

	public String getExpires_in()
	{
		return expires_in;
	}

	public void setExpires_in(String expires_in)
	{
		this.expires_in = expires_in;
	}

	public String getNot_before()
	{
		return not_before;
	}

	public void setNot_before(String not_before)
	{
		this.not_before = not_before;
	}

	public String getExpires_on()
	{
		return expires_on;
	}

	public void setExpires_on(String expires_on)
	{
		this.expires_on = expires_on;
	}

	public String getResource()
	{
		return resource;
	}

	public void setResource(String resource)
	{
		this.resource = resource;
	}

	public String getAccess_token()
	{
		return access_token;
	}

	public void setAccess_token(String access_token)
	{
		this.access_token = access_token;
	}

}