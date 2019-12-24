package com.store.stock.service;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;

import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.store.stock.constant.AppConstant;
import com.store.stock.dto.BuyProductRequestDto;
import com.store.stock.dto.Mail;
import com.store.stock.dto.TransactionRequestDto;
import com.store.stock.dto.TransactionResponseDto;
import com.store.stock.dto.ValidateOtpDto;
import com.store.stock.entity.Order;
import com.store.stock.entity.Product;
import com.store.stock.entity.User;
import com.store.stock.exception.OrderNotFoundException;
import com.store.stock.exception.ProductNotFoundException;
import com.store.stock.exception.UserNotFoundException;
import com.store.stock.repository.OrderRepository;
import com.store.stock.repository.ProductRepository;
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
public class OrderServiceImpl implements OrderService {

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

	@Autowired
	ProductRepository productRepository;

	@Autowired
	MailService mailService;

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
		if (!user.isPresent()) {
			log.error("viewMyOrder service method - UserNotFoundException occurs");
			throw new UserNotFoundException(AppConstant.USER_NOT_FOUND);
		}else {
		List<Order> orders = orderRepository.findAllByUser(user.get());
		  if (orders.isEmpty()) {
			log.error("viewMyOrder service method - OrderNotFoundException occurs");
			throw new OrderNotFoundException(AppConstant.ORDER_NOT_FOUND);
		} else {
			return orders;
		}
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
	@Override
	public void buyProduct(String userId, BuyProductRequestDto buyProductRequestDto)
			throws ProductNotFoundException, UserNotFoundException, MessagingException, UnsupportedEncodingException {
		log.info("buy a product based on user input...");
		// Check product detail is present or not.
		Optional<Product> product = productRepository.findById(buyProductRequestDto.getProductId());
		if (!product.isPresent()) {
			throw new ProductNotFoundException(AppConstant.PRODUCT_NOT_FOUND);
		}

		// Check product detail is present or not.
		Optional<User> user = userRepository.findByEmailId(userId);
		if (!user.isPresent()) {
			throw new UserNotFoundException(AppConstant.USER_NOT_FOUND);
		}
		log.debug("buy a product based on user input...");

		// Save the order details.
		Order order = new Order();
		order.setAmount(product.get().getPriceValue());
		order.setOrderDate(LocalDate.now());
		order.setProduct(product.get());
		order.setUser(user.get());

		// Genetate and save the otp value.
		Integer otpValue = generateOtpValue();
		order.setOtpValue(otpValue);

		orderRepository.save(order);

		Mail mail = new Mail();
		mail.setMailFrom(AppConstant.EMAIL_ADDRESS);
		mail.setMailTo(user.get().getEmailId());
		mail.setMailSubject(AppConstant.EMAIL_SUBJECT);
		mail.setMailContent(AppConstant.EMAIL_BODY.concat(" ")
				.concat(String.valueOf(otpValue).concat(" ").concat(AppConstant.EMAIL_BODY_1)));

		// Send Email Service.
		mailService.sendEmail(mail);
	}

	@Override
	public void validateOtp(Integer orderId, ValidateOtpDto validateOtpDto)
			throws UserNotFoundException, OrderNotFoundException {
		// Check product detail is present or not.
		Optional<User> user = userRepository.findByEmailId(validateOtpDto.getUserId());
		if (!user.isPresent()) {
			throw new UserNotFoundException(AppConstant.USER_NOT_FOUND);
		}

		// Check Order detail is present or not.
		Optional<Order> order = orderRepository.findById(orderId);
		if (!order.isPresent()) {
			throw new OrderNotFoundException(AppConstant.ORDER_NOT_FOUND);
		}

		// Check Order detail is present or not.
		Optional<Order> orderResult = orderRepository.findByOtpValue(validateOtpDto.getOtpValue());
		if (!orderResult.isPresent()) {
			throw new OrderNotFoundException(AppConstant.OTP_WRONG_VALUE);
		}

		// Call the Rest Template for Update the transaction details.
		RestTemplate restTemplate = new RestTemplate();
		TransactionRequestDto transactionRequestDto = new TransactionRequestDto();
		transactionRequestDto.setCardNumber(validateOtpDto.getCardNumber());
		transactionRequestDto.setTransactionAmount(order.get().getAmount());
		transactionRequestDto.setDescription(order.get().getProduct().getDescription());
		transactionRequestDto.setUserId(validateOtpDto.getUserId());
		restTemplate.postForEntity(AppConstant.CREATE_TRANSACTION_URL, transactionRequestDto,
				TransactionResponseDto.class);
	}

	/**
	 * get the otp value based on the numeric values.
	 * 
	 * @return return the long value of the generated otp value.
	 */
	private Integer generateOtpValue() {
		String number = RandomStringUtils.random(6, false, true);
		return Integer.valueOf(number);
	}

}
