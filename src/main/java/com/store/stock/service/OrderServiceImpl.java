package com.store.stock.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store.stock.constant.AppConstant;
import com.store.stock.entity.Order;
import com.store.stock.entity.User;
import com.store.stock.exception.OrderNotFoundException;
import com.store.stock.exception.UserNotFoundException;
import com.store.stock.repository.OrderRepository;
import com.store.stock.repository.UserRepository;
/**
 * This orderServiceImpl has method for view my orders
 * 
 * @author Priyadharshini S
 * 
 * @version V1.1
 * @since 23-12-2019
 */
@Service
public class OrderServiceImpl implements OrderService{
	
	private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

	/**
	 * This will inject all the implementation of orderRepository
	 */
	@Autowired
	OrderRepository orderRepository;
	/**
	 * This will inject all the implementation of userRepository
	 */
	@Autowired
	UserRepository userRepository;
	/**
	 * viewMyOrder method is used to view the products ordered by the user
	 * 
	 * @param userId by passing it view the particular user's order
	 * @return the list of orders
	 * @throws OrderNotFoundException This exception occurs when order is not found
	 * @throws UserNotFoundException  This exception occurs when user is not found
	 */
	@Override
	public List<Order> viewMyOrder(Integer userId) throws OrderNotFoundException, UserNotFoundException {
		log.info("viewMyOrder service method - viewing the orders");
		Optional<User> user = userRepository.findById(userId);
		List<Order> orders = orderRepository.findAllByUser(user.get());
		if (!user.isPresent()) {
			log.error("viewMyOrder service method - UserNotFoundException occurs");
			throw new UserNotFoundException(AppConstant.USER_NOT_FOUND);
		} else if (orders.isEmpty()) {
			log.error("viewMyOrder service method - OrderNotFoundException occurs");
			throw new OrderNotFoundException(AppConstant.ORDER_NOT_FOUND);
		} else {
			return orders;
		}
	}

}
