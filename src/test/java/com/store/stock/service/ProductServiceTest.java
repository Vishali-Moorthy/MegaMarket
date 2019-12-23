package com.store.stock.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.store.stock.entity.Product;
import com.store.stock.exception.ProductNotFoundException;
import com.store.stock.repository.ProductRepository;

@RunWith(MockitoJUnitRunner.Silent.class)
public class ProductServiceTest {

	@InjectMocks
	ProductServiceImpl productServiceImpl;

	@Mock
	ProductRepository productRepository;

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
	public void testSearchProduct() {
		List<Product> products = new ArrayList<>();
		products.add(product);
		Mockito.when(productRepository.findAllByProductNameContains("pen")).thenReturn(products);
		List<Product> result = productServiceImpl.searchProduct("pen");

		assertNotNull(result);
		assertEquals(products, result);
	}


	@Test
	public void testGetProduct() throws ProductNotFoundException {
		Mockito.when(productRepository.findById(1)).thenReturn(Optional.of(product));
		Product result = productServiceImpl.getProduct(1);

		assertNotNull(result);
		assertEquals(product, result);
	}
	
	@Test(expected = ProductNotFoundException.class)
	public void testGetProductForNegative() throws ProductNotFoundException {
		Mockito.when(productRepository.findById(1)).thenReturn(Optional.of(product));
		productServiceImpl.getProduct(2);
	}


}