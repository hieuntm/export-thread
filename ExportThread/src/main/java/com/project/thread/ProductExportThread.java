package com.project.thread;


import com.project.entity.Product;
import com.project.repository.ProductRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.*;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.project.mapper.ProductMapper.toDTO;
import static com.project.util.ExportUtils.createProductHeader;
import static com.project.util.ExportUtils.createProductRow;

public class ProductExportThread implements Runnable {

    private Thread t;
    private String productType;
    private List<File> filesExport = new ArrayList<>();
    private ProductRepository productRepository;

    public ProductExportThread(String productType, ProductRepository productRepository) {
        this.productType = productType;
        this.productRepository = productRepository;
    }

    @Override
    public void run() {
        try {
            exportData();
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean isAlive() {
        return t != null && t.isAlive();
    }

    public void start() {
        if (t == null) {
            t = new Thread(this);
            t.start();
        }
    }

    private void exportData() {
        long time = System.currentTimeMillis();
        if (productType != null && !productType.isEmpty()) {
            File tempFile;
            try {
                tempFile = File.createTempFile("Export_Product_" + productType + "_" + time, ".xlsx");

                List<Product> productList = productRepository.findByProductType(productType);
                XSSFWorkbook workbook = new XSSFWorkbook();
                XSSFSheet sheet = workbook.createSheet(productType);// creating a blank sheet
                int rownum = 0;
                Row rowHeader = sheet.createRow(rownum++);
                createProductHeader(rowHeader, sheet, workbook);
                Iterator itr = productList.iterator();
                while (itr.hasNext()) {
                    Product product = (Product) itr.next();
                    sheet.autoSizeColumn(rownum);
                    Row row = sheet.createRow(rownum);
                    createProductRow(toDTO(product), row);
                    rownum++;
                    itr.remove();
                }

                FileOutputStream out2 = null;
                try {
                    out2 = new FileOutputStream(tempFile);
                    workbook.write(out2);
                    filesExport.add(tempFile);

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (out2 != null) {
                        try {
                            out2.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public List<File> getFilesExport() {
        return filesExport;
    }

    public void setFilesExport(List<File> filesExport) {
        this.filesExport = filesExport;
    }
}
