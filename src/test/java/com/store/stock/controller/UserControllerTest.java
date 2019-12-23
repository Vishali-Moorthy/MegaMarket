package com.store.stock.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import com.store.stock.constant.AppConstant;
import com.store.stock.dto.UserLoginDto;
import com.store.stock.dto.UserLoginResponseDto;
import com.store.stock.exception.UserNotFoundException;
import com.store.stock.service.UserService;

@RunWith(MockitoJUnitRunner.Silent.class)
public class UserControllerTest {

	@InjectMocks
	UserController userController;

	@Mock
	UserService userService;

	UserLoginDto userLoginDto = null;
	UserLoginResponseDto userLoginResponseDto = null;

	@Before
	public void setup() {
		userLoginResponseDto = new UserLoginResponseDto();
		userLoginResponseDto.setMessage(AppConstant.LOGIN_SUCCESS_MESSAGE);
		userLoginResponseDto.setStatusCode(AppConstant.LOGIN_SUCCESS_CODE);
		userLoginResponseDto.setLoginType(AppConstant.LOGIN_TYPE);

		userLoginDto = new UserLoginDto();
		userLoginDto.setPassword("vishali");
		userLoginDto.setUserId("vishali@gmail.com");

	}

	@Test
	public void testLoginUser() throws UserNotFoundException {
		Mockito.when(userService.loginUser(userLoginDto)).thenReturn(userLoginResponseDto);
		ResponseEntity<UserLoginResponseDto> result = userController.loginUser(userLoginDto);
		assertNotNull(userLoginResponseDto);
		assertEquals(AppConstant.LOGIN_SUCCESS_CODE, result.getStatusCodeValue());

	}
}