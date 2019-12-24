package com.store.stock.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.store.stock.constant.AppConstant;
import com.store.stock.dto.BuyProductRequestDto;
import com.store.stock.dto.ResponseDto;
import com.store.stock.dto.ValidateOtpDto;
import com.store.stock.entity.Order;
import com.store.stock.exception.OrderNotFoundException;
import com.store.stock.exception.ProductNotFoundException;
import com.store.stock.exception.UserNotFoundException;
import com.store.stock.service.OrderService;

/**
 * OrderController - In Mega Market application user order(buy) a products and
 * view the user order details of the Rest Api's implement in this Order
 * Controller.
 * 
 * @author Govindasamy.C
 * @version V1.1
 * @since 23-12-2019
 *
 */
@RestController
@RequestMapping("/orders")
@CrossOrigin
public class OrderController {

	private static final Logger log = LoggerFactory.getLogger(OrderController.class);

	/**
	 * This will inject all the implementation of orderService
	 */
	@Autowired
	OrderService orderService;

	/**
	 * viewMyOrder method is used to view the products ordered by the user
	 * 
	 * @param userId by passing it view the particular user's order
	 * @return the list of orders
	 * @throws OrderNotFoundException This exception occurs when order is not found
	 * @throws UserNotFoundException  This exception occurs when user is not found
	 */
	@GetMapping("/{userId}")
	public ResponseEntity<List<Order>> viewMyOrder(@PathVariable("userId") Integer userId)
			throws OrderNotFoundException, UserNotFoundException {
		log.info("viewMyOrder controller method - viewing the orders");
		if (userId == null) {
			log.error("viewMyOrder controller method - UserNotFoundException occurs");
			throw new UserNotFoundException(AppConstant.USER_NOT_FOUND);
		} else {
			return new ResponseEntity<List<Order>>(orderService.viewMyOrder(userId), HttpStatus.OK);
		}

	}

	/**
	 * Buy a product based on the userId.
	 * 
	 * @param userId
	 * @param buyProductRequestDto
	 * @return
	 * @throws ProductNotFoundException     if product is not found we can throw the
	 *                                      productnotfoundexception.
	 * @throws UserNotFoundException        if user is not found we can throw the
	 *                                      usernotfoundexception.
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException
	 */
	@PostMapping("/{userId}")
	public ResponseEntity<ResponseDto> buyProduct(@PathVariable String userId,
			@Valid @RequestBody BuyProductRequestDto buyProductRequestDto)
			throws ProductNotFoundException, UserNotFoundException, MessagingException, UnsupportedEncodingException {
		log.info("validate otp value...");
		ResponseDto responseDto = new ResponseDto();
		// Buy the product service call.
		orderService.buyProduct(userId, buyProductRequestDto);
		responseDto.setMessage(AppConstant.BUY_SUCCESS_OTP_SEND);
		responseDto.setStatusCode(HttpStatus.CREATED.value());
		return new ResponseEntity<>(responseDto, HttpStatus.OK);
	}

	/**
	 * validate the otp value based on the order id value.
	 * 
	 * @param userId
	 * @param orderId
	 * @param validateOtpDto
	 * @return
	 * @throws ProductNotFoundException
	 * @throws UserNotFoundException
	 * @throws OrderNotFoundException
	 */
	@PutMapping("/{orderId}")
	public ResponseEntity<ResponseDto> validateOtp(@PathVariable Integer orderId,
			@Valid @RequestBody ValidateOtpDto validateOtpDto)
			throws ProductNotFoundException, UserNotFoundException, OrderNotFoundException {
		log.info("buy a product based on the userId");
		ResponseDto responseDto = new ResponseDto();
		// Buy the product service call.
		orderService.validateOtp(orderId, validateOtpDto);
		responseDto.setMessage(AppConstant.ORDER_SUCCESS_MESSAGE);
		responseDto.setStatusCode(HttpStatus.OK.value());
		return new ResponseEntity<>(responseDto, HttpStatus.OK);
	}

}
