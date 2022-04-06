package com.project.service.impl;

import com.project.dto.request.ProductRequestFileDTO;
import com.project.dto.response.ResponseDTO;
import com.project.entity.Product;
import com.project.repository.ProductRepository;
import com.project.service.IProductService;
import com.project.thread.ProductExportThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.project.util.FileUtils.encodeFileToBase64Binary;
import static com.project.util.FileUtils.writeZipFile;

@Service
public class ProductService implements IProductService {

    private final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);
    private List<ProductExportThread> productExportThreads = new ArrayList<>();

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void createProduct() {
        List<String> productTypes = new ArrayList<>();
        productTypes.add("Television");
        productTypes.add("Chair");
        productTypes.add("Desk");
        productTypes.add("Phone");
        productTypes.add("Tablet");
        productTypes.add("Accu");
        productTypes.add("Laptop");
        productTypes.add("Mouse");
        productTypes.add("Keyboard");
        productTypes.add("Screen");
        productTypes.add("Door");
        productTypes.add("Toys");
        productTypes.add("Slipper");
        productTypes.add("Pods");
        productTypes.add("Vape");
        Random rand = new Random();
        for (int i = 0; i < 2_000_000; i++) {
            Product product = new Product();
            product.setProductName("Item Test " + i);
            product.setProductDescription("Item Des " + i);
            product.setProductType(productTypes.get(rand.nextInt(productTypes.size())));

            productRepository.save(product);
        }

    }

    @Override
    public void getProduct() {
        List<Product> productList = productRepository.findAll();
        LOGGER.info("Check size: " + productList.size());
    }

    @Override
    public List<String> getAllProductType() {
        return productRepository.findDistinctProductTypes();
    }

    @Override
    public ResponseDTO getFile(ProductRequestFileDTO productRequestFileDTO) {
        long startTime = System.currentTimeMillis();
        ResponseDTO response = new ResponseDTO("NOK", "Temp Message", null);
        if (productRequestFileDTO != null) {
            List<String> productTypes = productRequestFileDTO.getProductTypes();
            if (productTypes != null) {
                int productTypesSize = productTypes.size();
                if (productTypesSize == 0) {
                    productTypes = getAllProductType();
                }
                try {
                    for (String productType : productTypes) {
                        exportProduct(productType);
                    }

                    File resultFile = File.createTempFile("resultFile",".zip");
                    List<File> files = handleFiles();
                    writeZipFile(resultFile, files);

                    long endTime = System.currentTimeMillis();
                    System.out.println("Took " + (endTime - startTime) + " milliseconds");

                    response.setType("OK");
                    response.setFileBase64(encodeFileToBase64Binary(resultFile));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return response;
    }

    private List<File> handleFiles(){
        List<File> files = new ArrayList<>();
        boolean check = true;
        List<Boolean> checkList = new ArrayList();
        while (check) {
            try {
                for (ProductExportThread productExportThread : productExportThreads) {
                    if (!productExportThread.isAlive()) {
                        checkList.add(true);
                        files.addAll(productExportThread.getFilesExport());
                    }
                }
                int size = checkList.size();
                if (size == productExportThreads.size()) {
                    check = false;
                } else {
                    checkList.clear();
                    files.clear();
                }
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return files;
    }

    private void exportProduct(String productType) {
        ProductExportThread productExportThread = new ProductExportThread(productType, productRepository);
        productExportThread.start();
        productExportThreads.add(productExportThread);
    }


}
