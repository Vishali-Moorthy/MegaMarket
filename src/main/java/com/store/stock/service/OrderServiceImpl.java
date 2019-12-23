package com.store.stock.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store.stock.constant.AppConstant;
import com.store.stock.entity.Order;
import com.store.stock.entity.User;
import com.store.stock.exception.OrderNotFoundException;
import com.store.stock.exception.UserNotFoundException;
import com.store.stock.repository.OrderRepository;
import com.store.stock.repository.UserRepository;

@Service
public class OrderServiceImpl implements OrderService{
	
	@Autowired
	OrderRepository orderRepository;

	@Autowired
	UserRepository userRepository;
	
	@Override
	public List<Order> viewMyOrder(Integer userId) throws OrderNotFoundException, UserNotFoundException {
		Optional<User> user = userRepository.findById(userId);
		List<Order> orders = orderRepository.findAllByUser(user.get());
		if (!user.isPresent()) {
			throw new UserNotFoundException(AppConstant.USER_NOT_FOUND);
		} else if (orders.isEmpty()) {
			throw new OrderNotFoundException(AppConstant.ORDER_NOT_FOUND);
		} else {
			return orders;
		}
	}

}
