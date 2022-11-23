package com.springboot.jpa.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class ProductRequest {
	@NotBlank(message = "name is mandatory")
	private String name;

	private String description;

	@NotNull(message = "price is mandatory")
	private BigDecimal price;
}
