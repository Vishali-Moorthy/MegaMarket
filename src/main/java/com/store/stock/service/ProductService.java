package com.store.stock.service;

import java.util.List;

import com.store.stock.entity.Product;
import com.store.stock.exception.ProductNotFoundException;
/**
 * This interface is used to get the methods for product details
 * 
 * @author Vishalakshi D
 * @version V1.1
 * @since 23-12-2019
 *
 */
public interface ProductService {
	/**
	 * searchProduct is used to search the product by productName
	 * 
	 * @param searchValue is the productName for searching
	 * @return the list of products
	 */
	public List<Product> searchProduct(String searchValue);
	/**
	 * GetProduct is used to get the particular product by productId
	 * 
	 * @param productId by passing productId particular products can be get
	 * @return the particular product details
	 * @throws ProductNotFoundException This exception occurs when product is not
	 *                                  found
	 */
	public Product getProduct(Integer productId) throws ProductNotFoundException;

}
