package com.springboot.jpa.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.springboot.jpa.exception.ResourceNotFoundException;
import com.springboot.jpa.model.Product;
import com.springboot.jpa.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

	@Mock
	ProductRepository productRepository;

	@InjectMocks
	ProductServiceImpl productService;

	private Product product;

	@BeforeEach
	public void setup() {
		product = Product.builder().id(1L).name("apple").description("green apple").price(new BigDecimal(100)).build();
	}

	@DisplayName("JUnit test for saveProduct method")
	@Test
	void givenProductObject_whenSaveProduct_thenReturnProductObject() {

		// given - precondition or setup
		given(productRepository.save(product)).willReturn(product);

		// when - action or the behavior that we are going test
		Product savedProduct = productService.createProduct(product);

		// then - verify the output
		assertThat(savedProduct).isNotNull();
	}

	@DisplayName("JUnit test for getAllProducts method")
	@Test
	void givenProductsList_whenGetAllProducts_thenReturnProductsList() {

		// given - precondition or setup
		Product product2 = Product.builder().id(1L).name("apple").description("green apple").price(new BigDecimal(100))
				.build();
		given(productRepository.findAll()).willReturn(Arrays.asList(product, product2));

		// when - action or the behavior that we are going test
		List<Product> productList = productService.getAllProducts();

		// then - verify the output
		assertThat(productList).isNotNull();
		assertThat(productList.size()).isEqualTo(2);
	}

	@DisplayName("JUnit test for getAllProducts method (negative scenario)")
	@Test
	void givenEmptyProductsList_whenGetAllProducts_thenReturnEmptyProductsList() {

		// given - precondition or setup
		given(productRepository.findAll()).willReturn(Collections.emptyList());

		// when - action or the behavior that we are going test
		List<Product> productList = productService.getAllProducts();

		// then - verify the output
		assertThat(productList).isEmpty();
		assertThat(productList.size()).isEqualTo(0);
	}

	@DisplayName("JUnit test for getProduct method")
	@Test
	public void givenProductId_whenGetProduct_thenReturnProductObject() {
		// given
		given(productRepository.findById(1L)).willReturn(Optional.of(product));

		// when
		Product savedProduct = productService.getProduct(product.getId());

		// then
		assertThat(savedProduct).isNotNull();

	}
	
	@DisplayName("JUnit test for getProduct method (negative scenario)")
	@Test
	public void givenInvalidProductId_whenGetProduct_thenReturnBadRequest() {
		// given
		long productId = 0L;

		// then - verify the output
		ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
				() -> productService.getProduct(productId));
		assertTrue(exception.getMessage().contains("Record not found with id:0"));

	}

	@DisplayName("JUnit test for updateProduct method")
	@Test
	public void givenProductObject_whenUpdateProduct_thenReturnUpdatedProduct() {
		// given - precondition or setup
		given(productRepository.findById(1L)).willReturn(Optional.of(product));
		given(productRepository.save(product)).willReturn(product);
		product.setName("orange");
		product.setDescription("Sweet fruit");

		// when - action or the behavior that we are going test
		Product updatedProduct = productService.updateProduct(product);

		// then - verify the output
		assertThat(updatedProduct.getName()).isEqualTo("orange");
		assertThat(updatedProduct.getDescription()).isEqualTo("Sweet fruit");
	}

	@DisplayName("JUnit test for updateProduct method (negative scenario)")
	@Test
	public void givenEmptyProductObject_whenUpdateProduct_thenReturnUpdatedProduct() {
		// given - precondition or setup
		given(productRepository.findById(2L)).willReturn(Optional.empty());
		product.setId(2L);
		product.setName("orange");
		product.setDescription("Sweet fruit");

		// then - verify the output
		ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
				() -> productService.updateProduct(product));
		assertTrue(exception.getMessage().contains("Record not found with id:2"));
	}

	@DisplayName("JUnit test for deleteProduct method")
	@Test
	public void givenProductId_whenDeleteProduct_thenNothing() {
		// given - precondition or setup
		long productId = 1L;
		given(productRepository.findById(1L)).willReturn(Optional.of(product));
		willDoNothing().given(productRepository).delete(product);

		// when - action or the behavior that we are going test
		productService.deleteProduct(productId);

		// then - verify the output
		verify(productRepository, times(1)).delete(product);
	}

	@DisplayName("JUnit test for deleteProduct method (negative scenario)")
	@Test
	public void givenInvalidProductId_whenDeleteProduct_thenNothing() {
		// given - precondition or setup
		long productId = 0L;
		given(productRepository.findById(0L)).willReturn(Optional.empty());

		// when - action or the behavior that we are going test
		// then - verify the output
		ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
				() -> productService.deleteProduct(productId));
		assertTrue(exception.getMessage().contains("Record not found with id:0"));
	}

}
