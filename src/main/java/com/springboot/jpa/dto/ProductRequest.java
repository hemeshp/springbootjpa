package com.springboot.jpa.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductRequest {
	@NotBlank(message = "name is mandatory")
	private String name;

	private String description;

	@NotNull(message = "price is mandatory")
	private BigDecimal price;
}
