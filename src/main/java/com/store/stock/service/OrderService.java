package com.store.stock.service;

import java.util.List;

import com.store.stock.entity.Order;
import com.store.stock.exception.OrderNotFoundException;
import com.store.stock.exception.UserNotFoundException;

public interface OrderService {

	List<Order> viewMyOrder(Integer userId) throws OrderNotFoundException, UserNotFoundException;

}
