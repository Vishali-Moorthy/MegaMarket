package com.store.stock.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderResponseDto {
	
	private Integer statusCode;
	private String message;
	private Integer orderId;
}
