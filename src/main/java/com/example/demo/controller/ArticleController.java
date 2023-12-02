package com.example.demo.controller;

import com.example.demo.basic.Article;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ArticleController {

    // 静态对象用于暂时表示文章数据
    private static List<Article> articles = new ArrayList<>();

    static {
        // 初始化一些示例文章数据
        articles.add(new Article(1, "Article 1", "Content 1"));
        articles.add(new Article(2, "Article 2", "Content 2"));
        articles.add(new Article(3, "Article 3", "Content 3"));
    }

    @GetMapping("/articles")
    public List<Article> getArticleAll() {
        return articles;
    }

    @GetMapping("/articles/{id}")
    public Article getArticleById(@PathVariable int id) {
        for (Article article : articles) {
            if (article.getId() == id) {
                return article;
            }
        }
        return null; // 如果找不到对应ID的文章，可以返回null或者抛出异常
    }
}