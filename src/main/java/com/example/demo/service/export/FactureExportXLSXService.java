package com.example.demo.service.export;

import com.example.demo.entity.Client;
import com.example.demo.entity.Facture;
import com.example.demo.entity.LigneFacture;
import com.example.demo.repository.ClientRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Service
public class FactureExportXLSXService {

    @Autowired
    private ClientRepository clientRepository;

    public void export(OutputStream outputSteam, long idClient) {
        try {
            Workbook wb = new XSSFWorkbook();
            Client client = clientRepository.findById(idClient).get();
            createSheetClient(wb, client);
            for (Facture facture : client.getFactures()) {
                createSheetFacture(wb, facture);
            }
            wb.write(outputSteam);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void export(OutputStream outputSteam) {
        try {
            Workbook wb = new XSSFWorkbook();
            List<Client> clients = clientRepository.findAll();
            for (Client client : clients) {
                createSheetClient(wb, client);
                for (Facture facture : client.getFactures()) {
                    createSheetFacture(wb, facture);
                }
            }
            wb.write(outputSteam);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createSheetClient(Workbook wb, Client client) {
        Sheet sheetClient = wb.createSheet(client.getNom() + " " + client.getPrenom());
        Row rowNom = sheetClient.createRow(0);
        rowNom.createCell(0).setCellValue("Nom :");
        rowNom.createCell(1).setCellValue(client.getNom());

        Row rowPrenom = sheetClient.createRow(1);
        rowPrenom.createCell(0).setCellValue("Prénom :");
        rowPrenom.createCell(1).setCellValue(client.getPrenom());

        Row rowNbFactures = sheetClient.createRow(2);
        int nbFactures = client.getFactures().size();
        rowNbFactures.createCell(0).setCellValue(nbFactures + " facture(s) :");
        int iFacture = 1;
        for (Facture facture : client.getFactures()) {
            rowNbFactures.createCell(iFacture++).setCellValue(facture.getId());
        }
    }

    private void createSheetFacture(Workbook wb, Facture facture) {
        Sheet sheetFacture = wb.createSheet("Facture n°" + facture.getId());

        Row header = sheetFacture.createRow(0);
        header.createCell(0).setCellValue("Désignation article");
        header.createCell(1).setCellValue("Quantité");
        header.createCell(2).setCellValue("Prix unitaire");

        int iRow = 1;
        for (LigneFacture ligneFacture : facture.getLigneFactures()) {
            Row row = sheetFacture.createRow(iRow++);
            row.createCell(0).setCellValue(ligneFacture.getArticle().getLibelle());
            row.createCell(1).setCellValue(ligneFacture.getQuantite());
            row.createCell(2).setCellValue(ligneFacture.getArticle().getPrix());
        }
        Row total = sheetFacture.createRow(iRow++);
        total.createCell(0).setCellValue("Total :");
        // total.createCell(2).setCellValue(facture.getTotal());

        //TODO fusionner les deux 1er cellules.
    }

}
