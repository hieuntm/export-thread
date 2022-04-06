package com.project.mapper;

import com.project.dto.ProductDTO;
import com.project.entity.Product;

public class ProductMapper {

    public static ProductDTO toDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName(product.getProductName());
        productDTO.setType(product.getProductType());
        productDTO.setDescription(product.getProductDescription());
        return productDTO;
    }
}
