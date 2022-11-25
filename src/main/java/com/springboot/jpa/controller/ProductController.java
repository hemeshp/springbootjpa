package com.springboot.jpa.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.jpa.dto.ProductRequest;
import com.springboot.jpa.mapper.ProductMapper;
import com.springboot.jpa.model.Product;
import com.springboot.jpa.service.ProductService;

@RestController
public class ProductController {

	@Autowired
	ProductService productService;

	@GetMapping("/products")
	public ResponseEntity<List<Product>> getAllProducts() {
		return ResponseEntity.ok(this.productService.getAllProducts());
	}

	@GetMapping("/products/{id}")
	public ResponseEntity<Product> getProductsById(@PathVariable long id) {
		return ResponseEntity.ok(this.productService.getProduct(id));
	}

	@PostMapping("/products")
	public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductRequest productRequest) {
		return ResponseEntity.ok(this.productService.createProduct(ProductMapper.INSTANCE.productRequestToProduct(productRequest)));
	}

	@PutMapping("/products/{id}")
	public ResponseEntity<Product> updateProduct(@PathVariable long id, @RequestBody Product product) {
		product.setId(id);
		return ResponseEntity.ok(this.productService.updateProduct(product));
	}

	@DeleteMapping("/products/{id}")
	public ResponseEntity<?> deleteProduct(@PathVariable long id) {
		this.productService.deleteProduct(id);
		return new ResponseEntity<>(id, HttpStatus.OK);
	}

}
