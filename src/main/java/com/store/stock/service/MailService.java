package com.store.stock.service;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;

import com.store.stock.dto.Mail;

public interface MailService {

	public String sendEmail(Mail mail) throws MessagingException, UnsupportedEncodingException;

}
