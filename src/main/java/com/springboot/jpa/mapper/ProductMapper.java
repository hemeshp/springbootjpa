/**
 * 
 */
package com.springboot.jpa.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mapstruct.ReportingPolicy;

import com.springboot.jpa.dto.ProductRequest;
import com.springboot.jpa.model.Product;

/**
 * @author Hemesh
 *
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {
	ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

	Product productRequestToProduct(ProductRequest productRequest);

}
