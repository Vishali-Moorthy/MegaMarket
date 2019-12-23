package com.store.stock.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store.stock.constant.Constant;
import com.store.stock.dto.UserLoginDto;
import com.store.stock.dto.UserLoginResponseDto;
import com.store.stock.entity.User;
import com.store.stock.exception.UserNotFoundException;
import com.store.stock.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	

	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);


	/**
	 * This will injects all the implementations of userRepository
	 */
	@Autowired
	UserRepository userRepository;

	/**
	 * This loginUser method is used to login the user
	 * 
	 * @param userLoginDto contains email and password fields
	 * @return userLoginResponseDto which returns the status message and status code
	 * @throws UserNotFoundException This exception occurs when the user is not
	 *                               found
	 */
	@Override
	public UserLoginResponseDto loginUser(UserLoginDto userLoginDto) throws UserNotFoundException {
		log.info("loginUser service method - login the user");
		UserLoginResponseDto userLoginResponseDto = new UserLoginResponseDto();
		Optional<User> user = userRepository.findByEmailIdAndPassword(userLoginDto.getUserId(),
				userLoginDto.getPassword());
		if (user.isPresent()) {
			userLoginResponseDto.setUserId(user.get().getUserId());
			userLoginResponseDto.setEmailId(user.get().getEmailId());
			userLoginResponseDto.setUserName(user.get().getUserName());
			return userLoginResponseDto;

		} else {
			log.error("loginUser service method - UserNotFoundException occurs");
			throw new UserNotFoundException(Constant.USER_NOT_FOUND);
		}
	}
}
