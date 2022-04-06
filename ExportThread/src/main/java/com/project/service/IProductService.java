package com.project.service;

import com.project.dto.request.ProductRequestFileDTO;
import com.project.dto.response.ResponseDTO;

import javax.xml.ws.Response;
import java.util.List;

public interface IProductService {

    void createProduct();
    void getProduct();
    List<String> getAllProductType();
    ResponseDTO getFile(ProductRequestFileDTO productRequestFileDTO);

}
