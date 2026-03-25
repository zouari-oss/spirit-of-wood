package org.zouari.spiritofwood.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.zouari.spiritofwood.model.Article;
import org.zouari.spiritofwood.model.ArticleRequest;
import org.zouari.spiritofwood.service.AdminAuthService;
import org.zouari.spiritofwood.service.ArticleService;

@RestController
@RequestMapping("/api/admin/articles")
public final class AdminArticleController {

  private final AdminAuthService adminAuthService;
  private final ArticleService articleService;

  public AdminArticleController(final AdminAuthService adminAuthService, final ArticleService articleService) {
    this.adminAuthService = adminAuthService;
    this.articleService = articleService;
  }

  @GetMapping
  public List<Article> list(
      @RequestHeader(value = "Authorization", required = false) final String authorizationHeader) {
    adminAuthService.validateBearerToken(authorizationHeader);
    return articleService.list();
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Article create(
      @RequestHeader(value = "Authorization", required = false) final String authorizationHeader,
      @RequestBody final ArticleRequest request) {
    adminAuthService.validateBearerToken(authorizationHeader);
    return articleService.create(request);
  }

  @PutMapping("/{id}")
  public Article update(
      @RequestHeader(value = "Authorization", required = false) final String authorizationHeader,
      @PathVariable final UUID id,
      @RequestBody final ArticleRequest request) {
    adminAuthService.validateBearerToken(authorizationHeader);
    return articleService.update(id, request);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(
      @RequestHeader(value = "Authorization", required = false) final String authorizationHeader,
      @PathVariable final UUID id) {
    adminAuthService.validateBearerToken(authorizationHeader);
    articleService.delete(id);
  }
}
