package com.springboot.jpa.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@NotBlank(message = "name is mandatory")
	@Column(name = "name")
	private String name;
	
	@Column(name = "description")
	private String description;
	
	@NotNull(message = "price is mandatory")
	@Column(name = "price")
	private BigDecimal price;
	
	@CreationTimestamp
	private Date createdDate;
	
	@CreationTimestamp
	private Date updatedDate;

}
