package com.store.stock.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.store.stock.constant.AppConstant;
import com.store.stock.entity.Order;
import com.store.stock.exception.OrderNotFoundException;
import com.store.stock.exception.UserNotFoundException;
import com.store.stock.service.OrderService;

public class OrderController {
	
	@Autowired
	OrderService orderService;

	@GetMapping("/{userId}")
	public ResponseEntity<List<Order>> viewMyOrder(@PathVariable("userId") Integer userId)
			throws OrderNotFoundException, UserNotFoundException {
		if (userId == null) {
			throw new UserNotFoundException(AppConstant.USER_NOT_FOUND);
		} else {
			return new ResponseEntity<List<Order>>(orderService.viewMyOrder(userId), HttpStatus.OK);
		}

	}

}
