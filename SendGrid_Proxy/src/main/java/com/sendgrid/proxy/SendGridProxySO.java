/**
 * @author Nikhil Bharadwaj Ramashasthri
 * @since September-2017
 * @version 1.0
 */
package com.sendgrid.proxy;

import java.math.BigDecimal;
import java.sql.Clob;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.sql.rowset.serial.SerialClob;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Personalization;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;

@EnableAsync
@Service
public class SendGridProxySO {
	/* Autowiring the classes starts */
	@Autowired
	private SendGridProxySDO sendGridProxySDO;

	public void setSendGridProxySDO(SendGridProxySDO sendGridProxySDO) {
		this.sendGridProxySDO = sendGridProxySDO;
	}
	/* Autowiring the classes ends */

	/* Defining loggers starts */
	static final Logger logger = Logger.getLogger(SendGridProxySO.class);
	/* Defining loggers ends */

	/* Defining static variables starts */
	static final String SendGrid_BLOCKS_URL = "https://api.sendgrid.com/v3/suppression/blocks";
	static final String SendGrid_BOUNCE_URL = "https://api.sendgrid.com/v3/suppression/bounces";
	static final String SendGrid_INVALI_EMAIL_URL = "https://api.sendgrid.com/v3/suppression/invalid_emails";
	private static String API_KEY;

	public static String getAPI_KEY() {
		return API_KEY;
	}

	public static void setAPI_KEY(String aPI_KEY) {
		API_KEY = aPI_KEY;
	}
	/* Defining static variables ends */

	/* Reusable SendGrid email sending functionality Starts */
	@Transactional(value = "txManager")
	public Boolean sendGridSendEmail(SendGridProxyVO sendGridProxyVO) throws Exception {
		try {
			Content content = new Content(sendGridProxyVO.getContentType(), sendGridProxyVO.getEmailBody());
			Mail mail = new Mail();
			mail.setFrom(sendGridProxyVO.getFromEmailAddress());
			mail.setSubject(sendGridProxyVO.getSubject());
			mail.addContent(content);
			mail.addCategory(sendGridProxyVO.getEmailCategory());
			mail.addPersonalization(sendGridProxyVO.getPersonalization());
			if (API_KEY != null) {
				SendGrid sendGrid = new SendGrid(API_KEY);
				Request request = new Request();
				request.setMethod(Method.POST);
				request.setEndpoint("mail/send");
				request.setBody(mail.build());
				Response response = sendGrid.api(request);
				logger.warn("statusCode for " + sendGridProxyVO.getSubject() + " = " + response.getStatusCode());
				logger.warn("statusCode for " + sendGridProxyVO.getSubject() + " = " + response);
				if (response.getStatusCode() == HttpStatus.ACCEPTED.value()
						|| response.getStatusCode() == HttpStatus.OK.value()) {
					SimpleDateFormat parser = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss zzz");
					Date date = parser.parse(response.getHeaders().get("Date").toString());
					sendGridProxyVO.setMessageIdPk(response.getHeaders().get("X-Message-Id").toString());
					sendGridProxyVO.setEmailCreatedAt(new BigDecimal(date.getTime() / 1000));
					saveSendGridDetails(sendGridProxyVO);
					return true;
				}
			}
		} catch (Exception e) {
			logger.error("SendGridProxySO-->" + sendGridProxyVO.getSubject()
					+ "-->Exception while sending the Email ==> " + e);
			return false;
		}
		return false;
	}
	/* Reusable SendGrid email sending functionality Ends */

	@Async
	@Transactional(value = "txManager")
	public void saveSendGridDetails(SendGridProxyVO sendGridProxyVO) {
		SendGridProxyBO sendGridProxyBO = new SendGridProxyBO();
		sendGridProxyBO.setEmailCreatedAt(sendGridProxyVO.getEmailCreatedAt());
		sendGridProxyBO.setEmailStatus(HttpStatus.OK.toString());
		sendGridProxyBO.setFromEmailAddress(sendGridProxyVO.getFromEmailAddress().getEmail());
		sendGridProxyBO.setMessageIdPk(sendGridProxyVO.getMessageIdPk());
		StringBuilder sbToEmailAddresses = new StringBuilder();
		for (Email toEmailAddresses : sendGridProxyVO.getPersonalization().getTos()) {
			sbToEmailAddresses.append(toEmailAddresses.getEmail());
		}
		sendGridProxyBO.setToEmailAddress(sbToEmailAddresses.toString());
		if (sendGridProxyVO.getPersonalization().getCcs() != null) {
			StringBuilder sbCcEmailAddresses = new StringBuilder();
			for (Email ccEmailAddresses : sendGridProxyVO.getPersonalization().getCcs()) {
				sbCcEmailAddresses.append(ccEmailAddresses.getEmail());
			}
			sendGridProxyBO.setCcEmailAddress(sbCcEmailAddresses.toString());
		}

		try {
			Clob myClobFile = new SerialClob(sendGridProxyVO.getEmailBody().toCharArray());
			sendGridProxyBO.setEmailBody(myClobFile);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			logger.error("SendGridProxySO--> EmailBody setting failed ==> " + e1);
		}
		sendGridProxyBO.setEmailCategory(sendGridProxyVO.getEmailCategory());
		sendGridProxyBO.setContentType(sendGridProxyVO.getContentType());
		sendGridProxyBO.setSubject(sendGridProxyVO.getSubject());
		try {
			sendGridProxySDO.saveToDatabase(sendGridProxyBO);
		} catch (Exception e) {
			logger.error("SendGridProxySO--> save failed due to ==> " + e);
		}
	}

	@Scheduled(fixedRate = 300000)
	@Transactional(value = "txManager")
	public void sendGridFailureCheck() throws Exception {
		logger.info("sendGridFailureCheck Starts");
		@SuppressWarnings("unchecked")
		List<SendGridProxyBO> sendGridProxyBO = sendGridProxySDO.getList(SendGridProxyBO.class);
		List<SendGridProxyVO> sendGridProxyVOForBounceBacks = getSendGridBounceBacks();
		List<SendGridProxyVO> sendGridProxyVOForInvalidEmails = getSendGridInvalidEmails();
		List<SendGridProxyVO> sendGridProxyVOForBlocks = getSendGridBlocks();

		if (sendGridProxyVOForBounceBacks != null) {
			for (int i = 0; i < sendGridProxyBO.size(); i++) {
				for (int j = 0; j < sendGridProxyVOForBounceBacks.size(); j++) {
					if (sendGridProxyBO.get(i).getToEmailAddress().equalsIgnoreCase(
							sendGridProxyVOForBounceBacks.get(j).getPersonalization().getTos().get(0).getEmail())) {
						sendGridProxyBO.get(i)
								.setEmailFailReason(sendGridProxyVOForBounceBacks.get(j).getEmailFailReason());
						sendGridProxyBO.get(i).setEmailStatus(sendGridProxyVOForBounceBacks.get(j).getEmailStatus());
						sendGridProxyBO.get(i)
								.setEmailCreatedAt(sendGridProxyVOForBounceBacks.get(j).getEmailCreatedAt());
						sendGridProxyBO.get(i).setCheckFlag(Boolean.TRUE.toString());
					} else {
						sendGridProxyBO.get(i).setCheckFlag(Boolean.TRUE.toString());
					}
				}
			}
		}
		if (sendGridProxyVOForInvalidEmails != null) {
			for (int i = 0; i < sendGridProxyBO.size(); i++) {
				for (int j = 0; j < sendGridProxyVOForInvalidEmails.size(); j++) {
					if (sendGridProxyBO.get(i).getToEmailAddress().equalsIgnoreCase(
							sendGridProxyVOForInvalidEmails.get(j).getPersonalization().getTos().get(0).getEmail())) {
						sendGridProxyBO.get(i)
								.setEmailFailReason(sendGridProxyVOForInvalidEmails.get(j).getEmailFailReason());
						sendGridProxyBO.get(i).setEmailStatus(sendGridProxyVOForInvalidEmails.get(j).getEmailStatus());
						sendGridProxyBO.get(i)
								.setEmailCreatedAt(sendGridProxyVOForInvalidEmails.get(j).getEmailCreatedAt());
						sendGridProxyBO.get(i).setCheckFlag(Boolean.TRUE.toString());
					} else {
						sendGridProxyBO.get(i).setCheckFlag(Boolean.TRUE.toString());
					}
				}
			}
		}
		if (sendGridProxyVOForBlocks != null) {
			for (int i = 0; i < sendGridProxyBO.size(); i++) {
				for (int j = 0; j < sendGridProxyVOForBlocks.size(); j++) {
					if (sendGridProxyBO.get(i).getToEmailAddress().equalsIgnoreCase(
							sendGridProxyVOForBlocks.get(j).getPersonalization().getTos().get(0).getEmail())) {
						sendGridProxyBO.get(i).setEmailFailReason(sendGridProxyVOForBlocks.get(j).getEmailFailReason());
						sendGridProxyBO.get(i).setEmailStatus(sendGridProxyVOForBlocks.get(j).getEmailStatus());
						sendGridProxyBO.get(i).setEmailCreatedAt(sendGridProxyVOForBlocks.get(j).getEmailCreatedAt());
						sendGridProxyBO.get(i).setCheckFlag(Boolean.TRUE.toString());
					} else {
						sendGridProxyBO.get(i).setCheckFlag(Boolean.TRUE.toString());
					}
				}
			}
		}
	}

	private List<SendGridProxyVO> getSendGridBlocks() throws Exception {
		/* Building URL */
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet get = new HttpGet(SendGrid_BLOCKS_URL);
		get.setHeader("Authorization", "Bearer " + API_KEY);
		get.setHeader("Content-Type", "application/json");

		/* Executing the Get request */
		HttpResponse response = client.execute(get);

		if (response.getStatusLine().getStatusCode() == HttpStatus.OK.value()
				|| response.getStatusLine().getStatusCode() == HttpStatus.ACCEPTED.value()) {
			/* Returning JSON Object */
			HttpEntity entity = response.getEntity();
			String responseString = EntityUtils.toString(entity, "UTF-8");
			JSONArray myObject = new JSONArray(responseString);
			logger.debug("getSendGridBlocks response : " + myObject);
			List<SendGridProxyVO> sendGridProxyVOList = new ArrayList<>();
			@SuppressWarnings("rawtypes")
			Iterator iterator = myObject.iterator();
			JSONObject bounceJsonObject;
			while (iterator.hasNext()) {
				bounceJsonObject = (JSONObject) iterator.next();
				sendGridProxyVOList.add(convertJsonToClass(bounceJsonObject, new SendGridProxyVO()));
			}
			return sendGridProxyVOList;
		}
		return null;
	}

	private List<SendGridProxyVO> getSendGridInvalidEmails() throws Exception {
		/* Building URL */
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet get = new HttpGet(SendGrid_INVALI_EMAIL_URL);
		get.setHeader("Authorization", "Bearer " + API_KEY);
		get.setHeader("Content-Type", "application/json");

		/* Executing the Get request */
		HttpResponse response = client.execute(get);

		if (response.getStatusLine().getStatusCode() == HttpStatus.OK.value()
				|| response.getStatusLine().getStatusCode() == HttpStatus.ACCEPTED.value()) {
			/* Returning JSON Object */
			HttpEntity entity = response.getEntity();
			String responseString = EntityUtils.toString(entity, "UTF-8");
			JSONArray myObject = new JSONArray(responseString);
			logger.debug("getSendGridInvalidEmails response : " + myObject);
			List<SendGridProxyVO> sendGridProxyVOList = new ArrayList<>();
			@SuppressWarnings("rawtypes")
			Iterator iterator = myObject.iterator();
			JSONObject bounceJsonObject;
			while (iterator.hasNext()) {
				bounceJsonObject = (JSONObject) iterator.next();
				sendGridProxyVOList.add(convertJsonToClass(bounceJsonObject, new SendGridProxyVO()));
			}
			return sendGridProxyVOList;
		}
		return null;
	}

	private List<SendGridProxyVO> getSendGridBounceBacks() throws Exception {
		/* Building URL */
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet get = new HttpGet(SendGrid_BOUNCE_URL);
		get.setHeader("Authorization", "Bearer " + API_KEY);
		get.setHeader("Content-Type", "application/json");

		/* Executing the Get request */
		HttpResponse response = client.execute(get);

		if (response.getStatusLine().getStatusCode() == HttpStatus.OK.value()
				|| response.getStatusLine().getStatusCode() == HttpStatus.ACCEPTED.value()) {
			/* Returning JSON Object */
			HttpEntity entity = response.getEntity();
			String responseString = EntityUtils.toString(entity, "UTF-8");
			JSONArray myObject = new JSONArray(responseString);
			logger.debug("getSendGridBounceBacks response : " + myObject);
			List<SendGridProxyVO> sendGridProxyVOList = new ArrayList<>();
			@SuppressWarnings("rawtypes")
			Iterator iterator = myObject.iterator();
			JSONObject bounceJsonObject;
			while (iterator.hasNext()) {
				bounceJsonObject = (JSONObject) iterator.next();
				sendGridProxyVOList.add(convertJsonToClass(bounceJsonObject, new SendGridProxyVO()));
			}
			return sendGridProxyVOList;
		}
		return null;
	}

	private SendGridProxyVO convertJsonToClass(JSONObject bounceJsonObject, SendGridProxyVO sendGridProxyVO) {
		if (bounceJsonObject.has("created")) {
			sendGridProxyVO.setEmailCreatedAt(bounceJsonObject.getBigDecimal("created"));
		}
		if (bounceJsonObject.has("email")) {
			Personalization email = new Personalization();
			email.addTo(new Email(bounceJsonObject.getString("email")));
			sendGridProxyVO.setPersonalization(email);
		}
		if (bounceJsonObject.has("reason")) {
			sendGridProxyVO.setEmailFailReason(bounceJsonObject.getString("reason"));
		}
		if (bounceJsonObject.has("status")) {
			sendGridProxyVO.setEmailStatus(bounceJsonObject.getString("status"));
		}
		return sendGridProxyVO;
	}

}
