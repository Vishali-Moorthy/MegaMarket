package com.store.stock.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import com.store.stock.constant.AppConstant;
import com.store.stock.dto.BuyProductRequestDto;
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

@RunWith(MockitoJUnitRunner.Silent.class)
public class OrderServiceImplTest {

	@InjectMocks
	OrderServiceImpl orderServiceImpl;

	@Mock
	ProductRepository productRepository;

	@Mock
	UserRepository userRepository;

	@Mock
	OrderRepository orderRepository;

	@Mock
	RestTemplate restTemplate;

	@Mock
	MailService mailService;

	BuyProductRequestDto buyProductRequestDto = new BuyProductRequestDto();
	ValidateOtpDto validateOtpDto = new ValidateOtpDto();
	TransactionRequestDto transactionRequestDto = new TransactionRequestDto();
	TransactionResponseDto transactionResponseDto = new TransactionResponseDto();
	Product product = new Product();
	User user = new User();
	Order order = new Order();

	@Before
	public void init() {
		buyProductRequestDto.setCardNumber(5000000);
		buyProductRequestDto.setProductId(1);

		validateOtpDto.setCardNumber(5000000L);
		validateOtpDto.setOtpValue(588633);

		user.setEmailId("moorthy127@gmail.com");
		product.setProductName("Pen");
		order.setProduct(product);
		order.setOrderId(1);

		order.setUser(user);
	}

	@Test(expected = ProductNotFoundException.class)
	public void testBuyProductForProductNotFound()
			throws ProductNotFoundException, UserNotFoundException, MessagingException, UnsupportedEncodingException {
		when(productRepository.findById(buyProductRequestDto.getProductId())).thenReturn(Optional.ofNullable(null));
		orderServiceImpl.buyProduct("moorthy127@gmail.com", buyProductRequestDto);
	}

	@Test(expected = UserNotFoundException.class)
	public void testBuyProductForUserNotFound()
			throws ProductNotFoundException, UserNotFoundException, MessagingException, UnsupportedEncodingException {
		when(productRepository.findById(buyProductRequestDto.getProductId())).thenReturn(Optional.of(product));
		when(userRepository.findByEmailId("moorthy127@gmail.com")).thenReturn(Optional.ofNullable(null));
		orderServiceImpl.buyProduct("moorthy127@gmail.com", buyProductRequestDto);
	}

	@Test
	public void testBuyProduct()
			throws ProductNotFoundException, UserNotFoundException, MessagingException, UnsupportedEncodingException {
		when(productRepository.findById(buyProductRequestDto.getProductId())).thenReturn(Optional.of(product));
		when(userRepository.findByEmailId("moorthy127@gmail.com")).thenReturn(Optional.of(user));
		when(orderRepository.save(Mockito.any())).thenReturn(order);
		when(mailService.sendEmail(Mockito.any())).thenReturn(AppConstant.SUCCESS);
		orderServiceImpl.buyProduct("moorthy127@gmail.com", buyProductRequestDto);
		assertEquals("moorthy127@gmail.com", user.getEmailId());
	}

	@Test(expected = UserNotFoundException.class)
	public void testValidateOtpForUserNotFound() throws UserNotFoundException, OrderNotFoundException {
		when(userRepository.findByEmailId(validateOtpDto.getUserId())).thenReturn(Optional.ofNullable(null));
		orderServiceImpl.validateOtp(1, validateOtpDto);
	}

	@Test(expected = OrderNotFoundException.class)
	public void testValidateOtpForOrderNotFound() throws UserNotFoundException, OrderNotFoundException {
		when(userRepository.findByEmailId(validateOtpDto.getUserId())).thenReturn(Optional.of(user));
		when(orderRepository.findById(1)).thenReturn(Optional.ofNullable(null));
		orderServiceImpl.validateOtp(1, validateOtpDto);
	}

	@Test(expected = OrderNotFoundException.class)
	public void testValidateOtpForOrderNotFound1() throws UserNotFoundException, OrderNotFoundException {
		when(userRepository.findByEmailId(validateOtpDto.getUserId())).thenReturn(Optional.of(user));
		when(orderRepository.findById(1)).thenReturn(Optional.of(order));
		when(orderRepository.findByOtpValue(validateOtpDto.getOtpValue())).thenReturn(Optional.ofNullable(null));
		orderServiceImpl.validateOtp(1, validateOtpDto);
	}

	@Test
	public void testViewMyOrder() throws OrderNotFoundException, UserNotFoundException {

		User user = new User();
		user.setUserId(1);

		List<Order> orders = new ArrayList<>();
		orders.add(order);
		Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(user));
		Mockito.when(orderRepository.findAllByUser(user)).thenReturn(orders);
		List<Order> result = orderServiceImpl.viewMyOrder(1);
		assertNotNull(result);
	}

	@Test(expected = UserNotFoundException.class)
	public void testViewMyOrderForUserNotFound() throws OrderNotFoundException, UserNotFoundException {
		when(userRepository.findById(1)).thenReturn(Optional.ofNullable(null));
		orderServiceImpl.viewMyOrder(1);
	}

	@Test(expected = OrderNotFoundException.class)
	public void testViewMyOrderForOrderNotFound() throws OrderNotFoundException, UserNotFoundException {
		List<Order> orders = new ArrayList<>();
		when(userRepository.findById(1)).thenReturn(Optional.of(user));
		when(orderRepository.findAllByUser(user)).thenReturn(orders);
		orderServiceImpl.viewMyOrder(1);
	}
}
