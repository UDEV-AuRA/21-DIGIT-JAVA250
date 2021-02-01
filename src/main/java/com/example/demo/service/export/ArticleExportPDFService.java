package com.example.demo.service.export;

import com.example.demo.entity.Article;
import com.example.demo.repository.ArticleRepository;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.util.List;

@Service
public class ArticleExportPDFService {

    @Autowired
    private ArticleRepository articleRepository;

    public void export(OutputStream outputSteam) {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, outputSteam);
            document.open();

            PdfPTable table = new PdfPTable(3);
            table.addCell(new PdfPCell(new Phrase("Libelle")));
            table.addCell(new PdfPCell(new Phrase("Prix")));
            table.addCell(new PdfPCell(new Phrase("Stock")));

            List<Article> articles = articleRepository.findAll();
            for (Article article : articles) {
                table.addCell(new PdfPCell(new Phrase(article.getLibelle())));
                table.addCell(new PdfPCell(new Phrase(article.getPrix() + "")));
                table.addCell(new PdfPCell(new Phrase(article.getStock() + "")));
            }
            PdfPCell footer = new PdfPCell(new Phrase("footer"));
            footer.setColspan(3);
            table.addCell(footer);

            document.add(table);
            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

}
