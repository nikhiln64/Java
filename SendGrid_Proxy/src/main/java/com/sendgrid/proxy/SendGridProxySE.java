/**
 * This Service Exposure Layer helpful for operations related to SendGrid proxy
 * 
 * @author Nikhil Bharadwaj Ramashasthri
 * @since September-2017
 * @version 1.0
 */
package com.sendgrid.proxy;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@ Controller
@ RequestMapping(value = "/proxy")
public class SendGridProxySE
{

	/* Autowiring the classes starts */
	@ Autowired
	private SendGridProxySO sendGridProxySO;

	public void setSendGridProxySO(SendGridProxySO sendGridProxySO)
	{
		this.sendGridProxySO = sendGridProxySO;
	}
	/* Autowiring the classes ends */

	/* Defining loggers starts */
	static final Logger	logger						= Logger.getLogger(SendGridProxySE.class);
	/* Defining loggers ends */

	/* Defining constant REST_URL variables start */
	static final String	SendGridSendEmail_RestURL	= "/sendEmail";
	static final String	SendGridWebHook_RestURL		= "/webhook";
	/* Defining constant REST_URL variables end */

	/* REST methods start */
	@ RequestMapping(value = SendGridSendEmail_RestURL, method = RequestMethod.POST)
	public @ ResponseBody Boolean sendGrid(@ RequestBody SendGridProxyVO sendGridProxyVO) throws Exception
	{
		try
		{
			return sendGridProxySO.sendGridSendEmail(sendGridProxyVO);
		}
		catch (Exception e)
		{
			logger.error("getAutho2Login: Failed due to==>" + e);
			return false;
		}
	}

	/**
	 * This service method: used to achieve sendGrid webhook concept.
	 * 
	 * @return HttpStatus.
	 * @throws Exception
	 */
	@ RequestMapping(value = SendGridWebHook_RestURL, method = RequestMethod.POST)
	public HttpStatus putRecordInSharePoint(@ RequestBody List<SendGridProxyWebHookVO> reqParams) throws Exception
	{
		try
		{
			logger.warn("reqParams = " + reqParams);
			return HttpStatus.OK;
		}
		catch (Exception e)
		{
			logger.error("supervisorEmailFetch: Failed due to==>" + e);
			return HttpStatus.OK;
		}
	}

}
