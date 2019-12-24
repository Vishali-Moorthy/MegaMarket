package com.store.stock.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.mail.MessagingException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.store.stock.dto.BuyProductRequestDto;
import com.store.stock.dto.OrderResponseDto;
import com.store.stock.dto.ResponseDto;
import com.store.stock.dto.ValidateOtpDto;
import com.store.stock.exception.OrderNotFoundException;
import com.store.stock.exception.ProductNotFoundException;
import com.store.stock.exception.UserNotFoundException;
import com.store.stock.service.OrderService;

@RunWith(MockitoJUnitRunner.Silent.class)
public class OrderControllerTest {

	@InjectMocks
	OrderController orderController;

	@Mock
	OrderService orderService;

	BuyProductRequestDto buyProductRequestDto = new BuyProductRequestDto();
	ValidateOtpDto validateOtpDto = new ValidateOtpDto();

	@Before
	public void init() {
		buyProductRequestDto.setCardNumber(5000000);
		buyProductRequestDto.setProductId(1);

		validateOtpDto.setCardNumber(5000000L);
		validateOtpDto.setOtpValue(588633);
	}

	@Test
	public void testBuyProduct()
			throws ProductNotFoundException, UserNotFoundException, MessagingException, UnsupportedEncodingException {
		when(orderService.buyProduct("moorthy127@gmail.com", buyProductRequestDto)).thenReturn(new OrderResponseDto());
		ResponseEntity<OrderResponseDto> response = orderController.buyProduct("moorthy127@gmail.com", buyProductRequestDto);
		assertEquals(HttpStatus.CREATED.value(), response.getBody().getStatusCode());
	}

	@Test
	public void testValidateOtp() throws ProductNotFoundException, UserNotFoundException, OrderNotFoundException {
		ResponseEntity<ResponseDto> response = orderController.validateOtp(1, validateOtpDto);
		assertEquals(HttpStatus.OK.value(), response.getBody().getStatusCode());
	}

	@Test
	public void testViewMyOrder() throws OrderNotFoundException, UserNotFoundException{
	Mockito.when(orderService.viewMyOrder(Mockito.any())).thenReturn(new ArrayList<>());
	        assertEquals(HttpStatus.OK, orderController.viewMyOrder(1).getStatusCode());
	}
	
	@Test(expected = UserNotFoundException.class)
	public void testViewMyOrderForUserNotFound() throws OrderNotFoundException, UserNotFoundException{
		orderController.viewMyOrder(null);
	}
}
