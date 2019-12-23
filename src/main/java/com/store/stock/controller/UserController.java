package com.store.stock.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.store.stock.constant.Constant;
import com.store.stock.dto.UserLoginDto;
import com.store.stock.dto.UserLoginResponseDto;
import com.store.stock.exception.UserNotFoundException;
import com.store.stock.service.UserService;

/**
 * This service has the methods for the implementations of userLogin
 * 
 * @Api loginUser method is used to login the user
 * 
 * @author Vishalakshi D
 * 
 * @version V1.1
 * @since 23-12-2019
 */
@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {

	private static final Logger log = LoggerFactory.getLogger(UserController.class);

	/**
	 * This will injects all the implementations of userService method
	 */
	@Autowired
	UserService userService;

	/**
	 * This loginUser method is used to login the user
	 * 
	 * @param userLoginDto contains email and password fields
	 * @return userLoginResponseDto which returns the status message and status code
	 * @throws UserNotFoundException This exception occurs when the user is not
	 *                               found
	 */
	@PostMapping("/login")
	public ResponseEntity<UserLoginResponseDto> loginUser(@RequestBody UserLoginDto userLoginDto)
			throws UserNotFoundException {
		log.info("loginUser controller method - login the user");
		UserLoginResponseDto userLoginResponseDto = userService.loginUser(userLoginDto);
		userLoginResponseDto.setMessage(Constant.LOGIN_SUCCESS_MESSAGE);
		userLoginResponseDto.setStatusCode(Constant.LOGIN_SUCCESS_CODE);
		userLoginResponseDto.setLoginType(Constant.LOGIN_TYPE);
		return new ResponseEntity<>(userLoginResponseDto, HttpStatus.OK);
	}
}