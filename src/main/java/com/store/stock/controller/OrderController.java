package com.store.stock.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.store.stock.constant.AppConstant;
import com.store.stock.entity.Order;
import com.store.stock.exception.OrderNotFoundException;
import com.store.stock.exception.UserNotFoundException;
import com.store.stock.service.OrderService;
/**
 * This orderController has method for view my orders
 * 
 * @author Priyadharshini S
 * 
 * @version V1.1
 * @since 23-12-2019
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

}
