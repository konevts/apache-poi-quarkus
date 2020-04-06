package org.acme.service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.enterprise.context.ApplicationScoped;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@ApplicationScoped
public class ExcelService {

    public ByteArrayInputStream getReport() {
        String[] columns = {"One", "Two", "Three", "Four", "Five"};


        // Create a Workbook
        Workbook workbook = new XSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file

        /* CreationHelper helps us create instances of various things like DataFormat,
           Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way */
        CreationHelper createHelper = workbook.getCreationHelper();

        // Create a Sheet
        Sheet sheet = workbook.createSheet("Employee");

        // Create a Font for styling header cells
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        short fontFeight = 18;
        headerFont.setFontHeightInPoints(fontFeight);
        headerFont.setColor(IndexedColors.RED.getIndex());

        // Create a CellStyle with the font
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        // Create a Row
        Row headerRow = sheet.createRow(0);

        // Create cells

        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerCellStyle);
        }

        // Create Cell Style for formatting Date
        CellStyle dateCellStyle = workbook.createCellStyle();
        dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));

        // Create Other rows and cells with employees data
        // Resize all columns to fit the content size
//        for (int i=0; i<columns.length; i++) {
//            sheet.autoSizeColumn(i);
//        }

        // Write the output to a file
        ByteArrayOutputStream fileOut = new ByteArrayOutputStream();
        try {
            workbook.write(fileOut);
            fileOut.close();

            // Closing the workbook
            workbook.close();
            return new ByteArrayInputStream(fileOut.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Report generation issue");
        }
    }

}
