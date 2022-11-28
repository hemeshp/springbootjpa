/**
 * 
 */
package com.springboot.jpa.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.jpa.dto.ProductRequest;
import com.springboot.jpa.exception.ResourceNotFoundException;
import com.springboot.jpa.model.Product;
import com.springboot.jpa.service.ProductService;

/**
 * @author hemesh
 *
 */
@WebMvcTest
class ProductControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ProductService productService;

	@Autowired
	private ObjectMapper objectMapper;

	// JUnit test for Create products REST API
	@Test
	void createProduct_whenPostMethod() throws Exception {
		// given - precondition or setup
		Product product = Product.builder().name("mango").price(new BigDecimal(50)).build();
		ProductRequest productRequest = ProductRequest.builder().name("mango").price(new BigDecimal(50)).build();

		given(productService.createProduct(any(Product.class))).willAnswer((invocation) -> invocation.getArgument(0));

		// when - action or behavior that we are going test
		ResultActions response = mockMvc.perform(post("/products").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(productRequest)));

		// then - verify the result or output using assert statements
		response.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.name", is(product.getName())));

	}

	// JUnit test for Get All products REST API
	@Test
	public void listAllProducts_whenGetMethod() throws Exception {
		// given - precondition or setup
		List<Product> listOfProducts = Arrays.asList(Product.builder().name("mango").price(new BigDecimal(50)).build(),
				Product.builder().name("apple").price(new BigDecimal(80)).build(),
				Product.builder().name("grape").price(new BigDecimal(40)).build());
		given(productService.getAllProducts()).willReturn(listOfProducts);

		// when - action or the behavior that we are going test
		ResultActions response = mockMvc.perform(get("/products"));

		// then - verify the output
		response.andExpect(status().isOk()).andDo(print()).andExpect(jsonPath("$.size()", is(listOfProducts.size())));

	}

	// positive scenario - valid Product id
	// JUnit test for GET Product by id REST API
	@Test
	public void listProductById_whenGetMethod() throws Exception {
		// given - precondition or setup
		long productId = 1L;
		Product product = Product.builder().id(productId).name("mango").description("malgova")
				.price(new BigDecimal(50.00)).build();
		given(productService.getProduct(productId)).willReturn(product);

		// when - action or the behavior that we are going test
		ResultActions response = mockMvc.perform(get("/products/{id}", productId));

		// then - verify the output
		response.andExpect(status().isOk()).andDo(print()).andExpect(jsonPath("$.name", is(product.getName())))
				// .andExpect(jsonPath("$.price", is(product.getPrice())))
				.andExpect(jsonPath("$.description", is(product.getDescription())));

	}

	// negative scenario - valid Product id
	// JUnit test for GET Product by id REST API
	@Test
	public void should_throw_exceptionOnInvalidProductById_whenGetMethod() throws Exception {
		// given - precondition or setup
		long productId = 1L;
		given(productService.getProduct(productId))
				.willThrow(new ResourceNotFoundException("Record not found with id:" + productId));

		// when - action or the behavior that we are going test
		ResultActions response = mockMvc.perform(get("/products/{id}", productId));

		// then - verify the output
		response.andExpect(status().isNotFound()).andDo(print());

	}

	// JUnit test for update product REST API - positive scenario
	@Test
	public void updateProduct_whenPutProduct() throws Exception {
		// given - precondition or setup
		long productId = 1L;
		Product savedProduct = Product.builder().id(productId).name("mango").description("malgova")
				.price(new BigDecimal(50.00)).build();

		Product updatedProduct = Product.builder().name("apple").description("green apple").price(new BigDecimal(50.00))
				.build();

		given(productService.getProduct(productId)).willReturn(savedProduct);
		given(productService.updateProduct(any(Product.class))).willAnswer((invocation) -> invocation.getArgument(0));

		// when - action or the behavior that we are going test
		ResultActions response = mockMvc.perform(put("/products/{id}", productId)
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(updatedProduct)));

		// then - verify the output
		response.andExpect(status().isOk()).andDo(print()).andExpect(jsonPath("$.name", is(updatedProduct.getName())))
				.andExpect(jsonPath("$.description", is(updatedProduct.getDescription())));
	}

	// JUnit test for update product REST API - negative scenario
	@Test
	public void should_throw_exceptionOnInvalidProductUpdate_whenPutProduct() throws Exception {
		// given - precondition or setup
		long productId = 1L;

		Product updatedProduct = Product.builder().name("apple").description("green apple").price(new BigDecimal(50.00))
				.build();

		given(productService.updateProduct(any(Product.class)))
				.willThrow(new ResourceNotFoundException("Record not found with id:" + productId));

		// when - action or the behavior that we are going test
		ResultActions response = mockMvc.perform(put("/products/{id}", productId)
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(updatedProduct)));

		// then - verify the output
		response.andExpect(status().isNotFound()).andDo(print());
	}

	// JUnit test for delete product REST API - positive scenario
	@Test
	public void shouldDeleteProductwhenGivenValidProductId() throws Exception {
		// given - precondition or setup
		long productId = 1L;
		willDoNothing().given(productService).deleteProduct(productId);

		// when - action or the behavior that we are going test
		ResultActions response = mockMvc.perform(delete("/products/{id}", productId));

		// then - verify the output
		response.andExpect(status().isOk()).andDo(print());
	}

	// JUnit test for delete product REST API - negative scenario
	@Test
	public void should_throw_exceptionOnInvalidProductDelete_whenDeleteProduct() throws Exception {
		// given - precondition or setup
		long productId = 1L;
		willThrow(new ResourceNotFoundException("Record not found with id:" + productId)).given(productService)
				.deleteProduct(productId);

		// when - action or the behavior that we are going test
		ResultActions response = mockMvc.perform(delete("/products/{id}", productId));

		// then - verify the output
		response.andExpect(status().isNotFound()).andDo(print());
	}

}
