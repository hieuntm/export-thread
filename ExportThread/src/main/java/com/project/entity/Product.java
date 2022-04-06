package com.project.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "tbl_pm_product")
public class Product implements Serializable {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_type")
    private String productType;

    @Column(name = "product_description")
    private String productDescription;

}
