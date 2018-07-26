package com.elephantry.util;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * 
 * @author hieunm
 *
 */
public class MailHelper {
	
	public static void sendMailRecommendationFinished(String recipient, String recommendationName){
		try {
			String content = "Your Recommendation [" + recommendationName + "] finish. <a href='http://elephantry-corp.com:8080/en/portal/recommendation/completed'>See your result</a>" ; 
			
			sendMail(recipient, "[Elephantry] Your recommendation finished!", content);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void sendMailSignupSuccess(String recipient, String customerName){
		try {
			String content = "Thanks for believe and using our system !";
			
			sendMail(recipient, "[Elephantry] Sign up for Elephantry successfully!", content);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void sendMailAddNewAdmin(String recipient, String newPassword){
		try {
			String content = "Your elephantry account password is: " + newPassword;
			
			sendMail(recipient, "[Elephantry] Your Elephantry account has been created!", content);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void sendMailResetPassword(String recipient, String newPassword){
		try {
			String content = "Your elephantry account password is: " + newPassword;
			
			sendMail(recipient, "[Elephantry] Your Elephantry password has been reset!", content);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void sendMail(String recipient, String subject, String message)
			throws MessagingException {
		InternetAddress[] internetAddresses = { new InternetAddress(recipient) };
		sendMail(internetAddresses, subject, message);
	}

	public static void sendMail(String[] recipients, String subject, String message) throws MessagingException {
		InternetAddress[] internetAddresses = new InternetAddress[recipients.length];
		for (int i = 0; i < internetAddresses.length; i++) {
			internetAddresses[i] = new InternetAddress(recipients[i]);
		}
		sendMail(internetAddresses, subject, message);
	}

	private static void sendMail(InternetAddress[] internetAddresses, String subject, String message)
			throws AddressException, MessagingException {
		// sets SMTP server properties
		Properties properties = new Properties();
		properties.put("mail.smtp.host", InitConstant.MAIL_HOST);
		properties.put("mail.smtp.port", InitConstant.MAIL_PORT);
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");

		// creates a new session with an authenticator
		Authenticator auth = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(InitConstant.SENDER_EMAIL, InitConstant.SENDER_PASSWORD);
			}
		};

		Session session = Session.getInstance(properties, auth);

		// creates a new e-mail message
		Message msg = new MimeMessage(session);

		msg.setFrom(new InternetAddress(InitConstant.SENDER_EMAIL));

		msg.setRecipients(Message.RecipientType.TO, internetAddresses);
		msg.setSubject(subject);
		msg.setSentDate(new Date());
		msg.setContent(message, "text/html; charset=utf-8");

		// sends the e-mail
		Transport.send(msg);
	}
}