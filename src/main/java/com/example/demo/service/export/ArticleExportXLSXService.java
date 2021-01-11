package com.example.demo.service.export;

import com.example.demo.entity.Article;
import com.example.demo.repository.ArticleRepository;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Service
public class ArticleExportXLSXService {

    @Autowired
    private ArticleRepository articleRepository;

    public void export(OutputStream outputStream) {
        try {
            List<Article> articles = articleRepository.findAll();

            Workbook wb = new HSSFWorkbook();

            //...

            wb.write(outputStream);
            wb.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
