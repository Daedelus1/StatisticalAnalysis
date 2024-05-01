package org.example;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DataPusher {
    private final Workbook workbook;
    private Sheet sheet;
    private Map<String, String[]> data;

    public DataPusher() {
        this.workbook = new HSSFWorkbook();
        this.sheet = this.workbook.createSheet();
        this.data = new HashMap<>();
    }

    public void setRow(int row, String[] rowData) {
        data.put(Integer.toString(row), rowData);
    }

    public void addRow(String[] rowData) {
        data.put(Integer.toString(data.size() + 1), rowData);
    }

    public void writeToFile(File fileToBeWritten) throws IOException {
        Row row;
        for (String key : data.keySet()) {
            row = sheet.createRow(Integer.parseInt(key));
            int cellId = 0;
            for (String obj : data.get(key)) {
                row.createCell(cellId++).setCellValue(obj);
            }
        }
        FileOutputStream fileOutputStream = new FileOutputStream(fileToBeWritten);
        workbook.write(fileOutputStream);
        fileOutputStream.close();
        this.sheet = this.workbook.createSheet();
        this.data = new HashMap<>();
    }

}
