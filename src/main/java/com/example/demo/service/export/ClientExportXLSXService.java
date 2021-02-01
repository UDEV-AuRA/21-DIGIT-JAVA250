package com.example.demo.service.export;

import com.example.demo.entity.Client;
import com.example.demo.repository.ClientRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Service
public class ClientExportXLSXService {

    @Autowired
    private ClientRepository clientRepository;

    public void export(OutputStream outputSteam) {
        try {
            List<Client> clients = clientRepository.findAll();
            // Apache POI
            Workbook wb = new XSSFWorkbook();
            Sheet sheet = wb.createSheet("Clients");

            Font fontHeader = wb.createFont();
            fontHeader.setBold(true);
            fontHeader.setColor(IndexedColors.PINK.getIndex());

            CellStyle cellStyleHeader = wb.createCellStyle();
            cellStyleHeader.setFont(fontHeader);
            applyFullBorder(cellStyleHeader, BorderStyle.THICK, IndexedColors.BLUE);

            Row rowHeader = sheet.createRow(0);
            Cell cellHeaderNom = rowHeader.createCell(0);
            cellHeaderNom.setCellValue("Nom");
            cellHeaderNom.setCellStyle(cellStyleHeader);
            Cell cellHeaderPrenom = rowHeader.createCell(1);
            cellHeaderPrenom.setCellValue("Prénom");
            cellHeaderPrenom.setCellStyle(cellStyleHeader);

            CellStyle cellStyleDefault = wb.createCellStyle();
            applyFullBorder(cellStyleDefault, BorderStyle.THICK, IndexedColors.BLUE);

            int iRow = 1;
            for (Client client : clients) {
                Row row = sheet.createRow(iRow++);
                Cell cellNom = row.createCell(0);
                cellNom.setCellValue(client.getNom());
                cellNom.setCellStyle(cellStyleDefault);

                Cell cellPrenom = row.createCell(1);
                cellPrenom.setCellValue(client.getPrenom());
                cellPrenom.setCellStyle(cellStyleDefault);
            }
            wb.write(outputSteam);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void applyFullBorder(CellStyle cellStyle, BorderStyle borderStyle, IndexedColors color) {
        cellStyle.setBorderBottom(borderStyle);
        cellStyle.setBorderTop(BorderStyle.THICK);
        cellStyle.setBorderLeft(BorderStyle.THICK);
        cellStyle.setBorderRight(BorderStyle.THICK);
        cellStyle.setBottomBorderColor(color.getIndex());
        cellStyle.setTopBorderColor(color.getIndex());
        cellStyle.setLeftBorderColor(color.getIndex());
        cellStyle.setRightBorderColor(color.getIndex());
    }

}
