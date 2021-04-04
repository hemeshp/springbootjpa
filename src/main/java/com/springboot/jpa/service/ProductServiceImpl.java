package com.springboot.jpa.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.jpa.exception.ResourceNotFoundException;
import com.springboot.jpa.model.Product;
import com.springboot.jpa.repository.ProductRepository;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Override
	public Product createProduct(Product product) {
		return this.productRepository.save(product);
	}

	@Override
	public Product updateProduct(Product product) {
		Optional<Product> optProductObj = productRepository.findById(product.getId());
		if (optProductObj.isPresent()) {
			Product productUpdate = optProductObj.get();
			productUpdate.setName(product.getName());
			productUpdate.setDescription(product.getDescription());
			productUpdate.setPrice(product.getPrice());
			return this.productRepository.save(productUpdate);
		} else {
			throw new ResourceNotFoundException("Record not found with id:" + product.getId());
		}
	}

	@Override
	public List<Product> getAllProducts() {
		return this.productRepository.findAll();
	}

	@Override
	public Product getProduct(long productId) {
		return Optional.ofNullable(productRepository.findById(productId).get())
				.orElseThrow(ResourceNotFoundException::new);
	}

	@Override
	public void deleteProduct(long productId) {
		Optional<Product> optProductObj = productRepository.findById(productId);
		if (optProductObj.isPresent()) {
			Product productDelete = optProductObj.get();
			this.productRepository.delete(productDelete);
		} else {
			throw new ResourceNotFoundException("Record not found with id:" + productId);
		}
	}

}
