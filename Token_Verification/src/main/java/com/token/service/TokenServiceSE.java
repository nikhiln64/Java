package com.token.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.token.service.TokenServiceSO;
import com.token.utilities.TokenServiceModel;

@Controller
public class TokenServiceSE {

	@Autowired
	TokenServiceSO tokenserv;

	/**
	 * Service Method for tokenCreate
	 * 
	 * @param TokenServiceModel
	 * @return TokenServiceModel
	 * @throws Exception
	 */
	@RequestMapping(value = "tokenCreate/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TokenServiceModel> createToken(@RequestBody TokenServiceModel tokenServiceModel) {

		return new ResponseEntity<TokenServiceModel>(tokenserv.tokenCreate(tokenServiceModel), HttpStatus.OK);

	}

	/**
	 * Service Method for tokenValidate
	 * 
	 * @param token
	 * @return TokenServiceModel
	 * @throws Exception
	 */
	@RequestMapping(value = "tokenValidate/{token}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TokenServiceModel> tokenValidate(@PathVariable("token") String token) {

		return new ResponseEntity<TokenServiceModel>(tokenserv.tokenValidate(token), HttpStatus.OK);
	}

}
