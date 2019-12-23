package com.store.stock.dto;

import com.store.stock.entity.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginResponseDto {

	private String message;
	private Integer statusCode;
	private Integer userId;
	private String emailId;
	private String userName;
	private String loginType;
	private User user;

}
