package com.example.demo.service.impl;

import com.example.demo.dto.ArticleDto;
import com.example.demo.entity.Article;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.service.ArticleService;
import com.example.demo.service.mapper.ArticleMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Service contenant les actions métiers liées aux articles.
 */
@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {

    private ArticleRepository articleRepository;
    private ArticleMapper articleMapper;

    public ArticleServiceImpl(ArticleRepository articleRepository, ArticleMapper articleMapper) {
        this.articleRepository = articleRepository;
        this.articleMapper = articleMapper;
    }

    @Override
    public List<ArticleDto> findAll() {
        List<Article> articles = articleRepository.findAll();

        List<ArticleDto> articleDtos = new ArrayList<>();
        for (Article article : articles) {
            ArticleDto articleDto = articleMapper.articleDto(article);
            articleDtos.add(articleDto);
        }
        return articleDtos;

       // return articles
       //         .stream()
       //         .map(article -> articleMapper.articleDto(article))
       //         .collect(Collectors.toList());
    }

}
