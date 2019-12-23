package com.store.stock.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store.stock.constant.AppConstant;
import com.store.stock.entity.Product;
import com.store.stock.exception.ProductNotFoundException;
import com.store.stock.repository.ProductRepository;
/**
 * This interface is used to get the methods for product details
 * 
 * @author Vishalakshi D
 * @version V1.1
 * @since 23-12-2019
 *
 */
@Service
public class ProductServiceImpl implements ProductService {
	
	
	private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

	/**
	 * This will inject all the implementation of productRepository
	 */
	@Autowired
	ProductRepository productRepository;
	/**
	 * searchProduct is used to search the product by productName
	 * 
	 * @param searchValue is the productName for searching
	 * @return the list of products
	 */
	@Override
	public List<Product> searchProduct(String searchValue) {
		log.info("searchProduct service method - searching the product");
		if(searchValue.equalsIgnoreCase(AppConstant.PRODUCT_ALL)) {
			return productRepository.findAll();
		} else {
			return productRepository.findAllByProductNameContains(searchValue);
		}
	}
	/**
	 * GetProduct is used to get the particular product by productId
	 * 
	 * @param productId by passing productId particular products can be get
	 * @return the particular product details
	 * @throws ProductNotFoundException This exception occurs when product is not
	 *                                  found
	 */
	@Override
	public Product getProduct(Integer productId) throws ProductNotFoundException {
		log.info("getProduct service method - getting the product");
		Optional<Product> product = productRepository.findById(productId);
		if(product.isPresent()) {
			return product.get();
		} else {
			log.error("getProduct service method - ProductNotFoundException occurs");
			throw new ProductNotFoundException(AppConstant.PRODUCT_NOT_FOUND);
		}
	}
	
	

}
