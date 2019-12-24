package com.store.stock.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mail.javamail.JavaMailSender;

import com.store.stock.constant.AppConstant;
import com.store.stock.dto.Mail;

@RunWith(MockitoJUnitRunner.class)
public class MailServiceImplTest {

	@InjectMocks
	MailServiceImpl mailServiceImpl;

	@Mock
	JavaMailSender javaMailSender;
	Mail mail = new Mail();

	@Before
	public void init() {
		mail.setMailFrom(AppConstant.EMAIL_ADDRESS);
		mail.setMailTo("moorthy127@gmail.com");
		mail.setMailSubject(AppConstant.EMAIL_SUBJECT);
		mail.setMailContent("OTP");
	}

	@Test
	public void testSendEmail() throws UnsupportedEncodingException, MessagingException {
		// create a collection of the SMTP server settings
		Properties props = System.getProperties();

		props.put("mail.smtp.host", "smtp.gmail.com");
		 // create email session
        Session session = Session.getDefaultInstance(props, null);

        // based on session I create the unsigned MimeMessage and configure it
        MimeMessage mimeMessage = new MimeMessage(session);
        mimeMessage.setFrom(new InternetAddress("moorthyotpsend@gmail.com")); 
		when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
		String response = mailServiceImpl.sendEmail(mail);
		assertEquals(AppConstant.SUCCESS, response);
	}

}
