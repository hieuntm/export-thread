package com.project.repository;

import com.project.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Stream;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query("select distinct tpp.productType from Product tpp")
    List<String> findDistinctProductTypes();

    Stream<Product> streamByProductType(String productType);

    List<Product> findByProductType(String productType);
}
