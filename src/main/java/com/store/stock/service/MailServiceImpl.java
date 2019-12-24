package com.store.stock.service;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.store.stock.constant.AppConstant;
import com.store.stock.dto.Mail;

@Service
public class MailServiceImpl implements MailService {
	@Autowired
	JavaMailSender mailSender;

	public String sendEmail(Mail mail) throws MessagingException, UnsupportedEncodingException {
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
		mimeMessageHelper.setSubject(mail.getMailSubject());
		mimeMessageHelper.setFrom(new InternetAddress(mail.getMailFrom(), AppConstant.COMPANY_NAME));
		mimeMessageHelper.setTo(mail.getMailTo());
		mimeMessageHelper.setText(mail.getMailContent());
		mailSender.send(mimeMessageHelper.getMimeMessage());
        return AppConstant.SUCCESS;
	}
}
