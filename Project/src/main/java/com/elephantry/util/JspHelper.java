package com.elephantry.util;

import java.util.Date;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

import com.elephantry.entity.Customer;
import com.elephantry.entity.Notification;
import com.elephantry.entity.User;
import com.elephantry.service.CustomerService;
import com.elephantry.service.NotificationService;
import com.elephantry.service.UserService;
import com.elephantry.service.impl.CustomerServiceImpl;
import com.elephantry.service.impl.NotificationServiceImpl;
import com.elephantry.service.impl.UserServiceImpl;

public class JspHelper {
	public static Customer findCustomer(String email) {
		ApplicationContext applicationContext = null;
		Customer customer = null;
		try {

			applicationContext = ContextLoader.getCurrentWebApplicationContext();
			CustomerService customerService = (CustomerServiceImpl) applicationContext.getBean("customerService");
			System.out.println("dmm: " + email);
			System.out.println("dmm1: " + customerService);
			customer = customerService.findByEmail(email);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return customer;
	}

	public static User findUser(String email) {
		ApplicationContext applicationContext = null;
		User user = null;
		try {

			applicationContext = ContextLoader.getCurrentWebApplicationContext();
			UserService userService = (UserServiceImpl) applicationContext.getBean("userService");

			user = userService.findByEmail(email);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}
	public static List<Notification> findNotificationByCustomerId(int customerId){
		ApplicationContext applicationContext = null;
		List<Notification> listNoti= null;
		try {
			applicationContext = ContextLoader.getCurrentWebApplicationContext();
			NotificationService notificationService = (NotificationServiceImpl) applicationContext.getBean("notificationService");
			listNoti = notificationService.findbyCustomerId(customerId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listNoti;
	}
	public static String calculateDate(Date d){
		Date now = new Date();
		long minute = (now.getTime() - d.getTime())/60000;
		if (minute<60) {
			return minute <=1 ? (minute +" minute ago") : (minute +" minutes ago");
		}else if(minute>=60 && minute<1440){
			return minute/60 <=1 ? (minute/60 + " hour ago") : (minute/60 + " hours ago");
		}else{
			return minute/1440 <=1 ? (minute/1440 + " day ago") : (minute/1440 + " days ago");
		}
		
	}
}
