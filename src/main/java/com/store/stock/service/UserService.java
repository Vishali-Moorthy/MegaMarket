package com.store.stock.service;

import com.store.stock.dto.UserLoginDto;
import com.store.stock.dto.UserLoginResponseDto;
import com.store.stock.exception.UserNotFoundException;

/**
 * This interface has method loginUser for login the user
 * 
 * 
 * @Api loginUser method is used to login the user
 * @Api userRegistration method iis used to register the new user
 * 
 * @author Vishalakshi D
 * @author Priyadharshini S
 * 
 * @version V1.1
 * @since 23-12-2019
 */
public interface UserService {

	/**
	 * This loginUser method is used to login the user
	 * 
	 * @param userLoginDto contains email and password fields
	 * @return userLoginResponseDto which returns the status message and status code
	 * @throws UserNotFoundException This exception occurs when the user is not
	 *                               found
	 */
	public UserLoginResponseDto loginUser(UserLoginDto userLoginDto) throws UserNotFoundException;
}
