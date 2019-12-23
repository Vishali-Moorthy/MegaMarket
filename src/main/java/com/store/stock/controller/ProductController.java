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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.store.stock.entity.Product;
import com.store.stock.exception.ProductNotFoundException;
import com.store.stock.service.ProductService;
/**
 * This controller is used to get the product details
 * 
 * @author Vishalakshi D
 * @version V1.1
 * @since 23-12-2019
 *
 */
@RestController
@RequestMapping("/products")
@CrossOrigin
public class ProductController {
	
	
	private static final Logger log = LoggerFactory.getLogger(ProductController.class);

	/**
	 * This will inject all the implementation of productController
	 */
	@Autowired
	ProductService productService;

	/**
	 * searchProduct is used to search the product by productName
	 * 
	 * @param searchValue is the productName for searching
	 * @return the list of products
	 */
	@GetMapping
	public ResponseEntity<List<Product>> searchProduct(@RequestParam String searchValue) {
		log.info("searchProduct controller method - searching the product");
		return new ResponseEntity<>(productService.searchProduct(searchValue), HttpStatus.OK);
	}

	/**
	 * GetProduct is used to get the particular product by productId
	 * 
	 * @param productId by passing productId particular products can be get
	 * @return the particular product details
	 * @throws ProductNotFoundException This exception occurs when product is not
	 *                                  found
	 */
	@GetMapping("/{productId}")
	public ResponseEntity<Product> getProduct(@PathVariable Integer productId) throws ProductNotFoundException {
		log.info("getProduct controller method - getting the products");
		return new ResponseEntity<>(productService.getProduct(productId), HttpStatus.OK);
	}

}
