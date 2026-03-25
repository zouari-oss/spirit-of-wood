package org.zouari.spiritofwood.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.zouari.spiritofwood.model.Article;
import org.zouari.spiritofwood.service.ArticleService;

@RestController
@RequestMapping("/api/articles")
public final class PublicArticleController {

  private final ArticleService articleService;

  public PublicArticleController(ArticleService articleService) {
    this.articleService = articleService;
  }

  @GetMapping
  public List<Article> list(@RequestParam(value = "q", required = false) String query) {
    return articleService.listPublic(query);
  }
}
