package org.zouari.spiritofwood.controller;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zouari.spiritofwood.model.Article;
import org.zouari.spiritofwood.service.ArticleService;

@RestController
@RequestMapping("/api/articles")
public final class PublicArticleController {

  private final ArticleService articleService;

  public PublicArticleController(final ArticleService articleService) {
    this.articleService = articleService;
  }

  @GetMapping
  public ResponseEntity<List<Article>> list() {
    final List<Article> articles = articleService.list();
    return ResponseEntity.ok()
        .cacheControl(CacheControl.maxAge(0, TimeUnit.SECONDS).mustRevalidate().noStore())
        .body(articles);
  }
}
