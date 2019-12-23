package com.store.stock.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.store.stock.dto.UserLoginDto;
import com.store.stock.dto.UserLoginResponseDto;
import com.store.stock.entity.User;
import com.store.stock.exception.UserNotFoundException;
import com.store.stock.repository.UserRepository;

@RunWith(MockitoJUnitRunner.Silent.class)
public class UserServiceTest {

	@InjectMocks
	UserServiceImpl userServiceImpl;

	@Mock
	UserRepository userRepository;

	User user = null;
	UserLoginResponseDto userLoginResponseDto = null;
	UserLoginDto userLoginDto = null;

	@Before
	public void setup() {
		user = new User();
		user.setEmailId("vishali@gmail.com");
		user.setPassword("vishali");

		userLoginResponseDto = new UserLoginResponseDto();
		userLoginResponseDto.setEmailId(user.getEmailId());
		userLoginResponseDto.setUserId(1);
		userLoginResponseDto.setUserName("Vishalakshi");

		userLoginDto = new UserLoginDto();
		userLoginDto.setPassword(user.getPassword());
		userLoginDto.setUserId(user.getEmailId());

		userLoginDto = new UserLoginDto();
		userLoginDto.setPassword("vishali");
		userLoginDto.setUserId("vishali@gmail.com");

	}

	@Test
	public void testLoginUser() throws UserNotFoundException {
		Mockito.when(userRepository.findByEmailIdAndPassword(userLoginDto.getUserId(), userLoginDto.getPassword()))
				.thenReturn(Optional.of(user));
		UserLoginResponseDto result = userServiceImpl.loginUser(userLoginDto);

		assertNotNull(userLoginResponseDto);
		assertEquals("vishali@gmail.com", result.getEmailId());
	}
	
	@Test(expected = UserNotFoundException.class)
	public void testLoginUserForUserNotFoundException() throws UserNotFoundException {
		Mockito.when(userRepository.findByEmailIdAndPassword(userLoginDto.getUserId(), userLoginDto.getPassword()))
				.thenReturn(Optional.ofNullable(null));
		userServiceImpl.loginUser(userLoginDto);
	}
}
