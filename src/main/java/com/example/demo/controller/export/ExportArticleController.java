package com.example.demo.controller.export;

import com.example.demo.service.export.ArticleExportCVSService;
import com.example.demo.service.export.ArticleExportPDFService;
import com.example.demo.service.export.ArticleExportXLSXService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Controller pour réaliser export des articles.
 */
@Controller
@RequestMapping("export")
public class ExportArticleController {

    @Autowired
    private ArticleExportCVSService articleExportCVSService;

    @Autowired
    private ArticleExportXLSXService articleExportXLSXService;

    @Autowired
    private ArticleExportPDFService articleExportPDFService;

    /**
     * Export des articles au format CSV.
     */
    @GetMapping("/articles/csv")
    public void articlesCSV(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"export-articles.csv\"");
        PrintWriter writer = response.getWriter();
        articleExportCVSService.export(writer);
    }

    @GetMapping("/articles/xlsx")
    public void articlesXLSX(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=\"export-articles.xlsx\"");
        OutputStream outputStream = response.getOutputStream();
        articleExportXLSXService.export(outputStream);
    }

    @GetMapping("/articles/pdf")
    public void articlesPDF(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"export-articles.pdf\"");
        OutputStream outputStream = response.getOutputStream();
        articleExportPDFService.export(outputStream);
    }

    /**
     * Méthode utilisée pour l'export facture (point n°5)
     */
    @GetMapping("/clients/{id}/factures/xlsx")
    public void clientGetFacturesXLSX(@PathVariable Long id, HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=\"client-" + id + "-factures.xlsx\"");
        // TODO
    }

}
