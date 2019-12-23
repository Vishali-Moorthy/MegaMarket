package com.store.stock.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.store.stock.entity.Product;
import com.store.stock.exception.ProductNotFoundException;
import com.store.stock.service.ProductService;

@RunWith(MockitoJUnitRunner.Silent.class)
public class ProductControllerTest {

	@InjectMocks
	ProductController productController;

	@Mock
	ProductService productService;
	
	Product product = null;
	
	@Before
	public void setup() {
		product = new Product();
		product.setDescription("abc");
		product.setPriceValue(1000.0);
		product.setProductId(1);
		product.setProductName("pen");
		product.setQuantityAvailable(123);
	}
	
	@Test
	public void testSearchProduct() throws ProductNotFoundException {
		List<Product> products = new ArrayList<>();
		products.add(product);
		Mockito.when(productService.searchProduct("pen")).thenReturn(products);
		ResponseEntity<List<Product>> result = productController.searchProduct("pen");
		
		assertNotNull(result);
		assertEquals(HttpStatus.OK, result.getStatusCode());
	}
	
	@Test(expected = ProductNotFoundException.class)
	public void testSearchProductForNegative() throws ProductNotFoundException {
		List<Product> products = new ArrayList<>();
		products.add(product);
		Mockito.when(productService.searchProduct("pen")).thenReturn(products);
		productController.searchProduct(null);
	}
	
	@Test
	public void testGetProduct() throws ProductNotFoundException {
		Mockito.when(productService.getProduct(1)).thenReturn(product);
		ResponseEntity<Product> result = productController.getProduct(1);
		
		assertNotNull(result);
		assertEquals(HttpStatus.OK, result.getStatusCode());
	}
}
