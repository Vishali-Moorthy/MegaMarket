package com.store.stock.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Mail {
	private String mailFrom;
	private String mailTo;
	private String mailCc;
	private String mailBcc;
	private String mailSubject;
	private String mailContent;
	private String contentType;

}
