package com.store.stock.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.MessagingException;

import com.store.stock.dto.BuyProductRequestDto;
import com.store.stock.dto.ValidateOtpDto;
import com.store.stock.entity.Order;
import com.store.stock.exception.OrderNotFoundException;
import com.store.stock.exception.ProductNotFoundException;
import com.store.stock.exception.UserNotFoundException;

/**
 * This interface has method for view my orders
 * 
 * @author Priyadharshini S
 * 
 * @version V1.1
 * @since 23-12-2019
 */
public interface OrderService {
	/**
	 * viewMyOrder method is used to view the products ordered by the user
	 * 
	 * @param userId by passing it view the particular user's order
	 * @return the list of orders
	 * @throws OrderNotFoundException This exception occurs when order is not found
	 * @throws UserNotFoundException  This exception occurs when user is not found
	 */
	List<Order> viewMyOrder(Integer userId) throws OrderNotFoundException, UserNotFoundException;

	public void buyProduct(String userId, BuyProductRequestDto buyProductRequestDto)
			throws ProductNotFoundException, UserNotFoundException, MessagingException, UnsupportedEncodingException;

	public void validateOtp(Integer orderId, ValidateOtpDto validateOtpDto)
			throws UserNotFoundException, OrderNotFoundException;
}
