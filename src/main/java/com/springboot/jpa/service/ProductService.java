package com.springboot.jpa.service;

import java.util.List;

import com.springboot.jpa.model.Product;

public interface ProductService {
	public Product createProduct(Product product);

	public Product updateProduct(Product product);

	public List<Product> getAllProducts();

	public Product getProduct(long productId);

	void deleteProduct(long productId);
}
