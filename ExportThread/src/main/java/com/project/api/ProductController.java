package com.project.api;

import com.project.dto.request.ProductRequestFileDTO;
import com.project.dto.response.ResponseDTO;
import com.project.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/product")
public class ProductController {

    @Autowired
    private IProductService iProductService;

    @CrossOrigin
    @GetMapping("/")
    public void getProduct() {
        iProductService.getProduct();
    }

    @CrossOrigin
    @GetMapping("/type")
    public List<String> getProductType() {
        return iProductService.getAllProductType();
    }

    @CrossOrigin
    @PostMapping("/file")
    public ResponseDTO getFile(@RequestBody ProductRequestFileDTO productRequestFileDTO) {
        return iProductService.getFile(productRequestFileDTO);
    }

    @CrossOrigin
    @GetMapping("/tempData")
    public void createProduct() {
        iProductService.createProduct();
    }
}
