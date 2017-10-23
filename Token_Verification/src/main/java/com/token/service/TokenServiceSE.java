package com.token.service;

import java.math.BigDecimal;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.token.service.TokenServiceSO;

@Controller
public class TokenServiceSE {
	
	@Autowired
	TokenServiceSO tokenserv;
	
	/**
	 * Service Method for tokenCreate
	 * 
	 * @param payLoad
	 *            ==> userId
	 * @return appToken
	 * @throws Exception
	 */
	@RequestMapping(value = "tokenCreate/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> createToken(@RequestParam("payLoad") String payLoad,
			@RequestParam("lifeTime") BigDecimal lifeTime, HttpSession session) {

		String token = null;
		token = tokenserv.tokenCreate(payLoad, lifeTime, session);
		return new ResponseEntity<String>(token, HttpStatus.OK);
	}

	/**
	 * Service Method for tokenValidate
	 * 
	 * @param appToken
	 * @return payLoad ==> userId
	 * @throws Exception
	 */
	@RequestMapping(value = "tokenValidate/{token}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> tokenValidate(@PathVariable("token") String token, HttpSession session) {

		String payLoad = null;
		payLoad = tokenserv.tokenValidate(token, session);
		return new ResponseEntity<>(payLoad, HttpStatus.OK);
	}
	
}
