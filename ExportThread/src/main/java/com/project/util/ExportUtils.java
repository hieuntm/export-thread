package com.project.util;


import com.project.dto.ProductDTO;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.ArrayList;
import java.util.List;

public class ExportUtils {

    public static void createProductHeader(Row row, XSSFSheet sheet,
                                           XSSFWorkbook workbook) {
        List<String> headers = new ArrayList<>();
        headers.add("Product Name");
        headers.add("Product Type");
        headers.add("Product Description");
        Cell cell;
        int i = 0;
        for (String string : headers) {
            cell = row.createCell(i);
            cell.setCellValue(string);
            sheet.autoSizeColumn(i);
            i++;
        }
    }

    public static void createProductRow(ProductDTO productDTO, Row row) {
        Cell cell = row.createCell(0);
        cell.setCellValue(productDTO.getName());
        cell = row.createCell(1);
        cell.setCellValue(productDTO.getType());
        cell = row.createCell(2);
        cell.setCellValue(productDTO.getDescription());


    }
}
