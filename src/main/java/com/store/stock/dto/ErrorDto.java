package com.store.stock.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorDto {

	private String errorMessage;
	private Integer errorStatusCode;

}
