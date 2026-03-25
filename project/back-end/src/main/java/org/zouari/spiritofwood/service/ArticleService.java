package org.zouari.spiritofwood.service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.zouari.spiritofwood.model.Article;
import org.zouari.spiritofwood.model.ArticleRequest;
import org.zouari.spiritofwood.repository.ArticleRepository;

@Service
public final class ArticleService {
  private static final int NAME_MAX_LENGTH = 255;
  private static final int DESCRIPTION_MAX_LENGTH = 4000;
  private static final int IMAGE_MAX_LENGTH = 2_000_000;

  private final ArticleRepository articleRepository;

  public ArticleService(final ArticleRepository articleRepository) {
    this.articleRepository = articleRepository;
  }

  public List<Article> list() {
    return articleRepository.findAll().stream()
        .sorted(Comparator.comparing(Article::getName, String.CASE_INSENSITIVE_ORDER))
        .toList();
  }

  public List<Article> listPublic(final String query) {
    final String normalizedQuery = query == null ? "" : query.trim().toLowerCase();

    return articleRepository.findAll().stream()
        .filter(article -> normalizedQuery.isBlank()
            || article.getName().toLowerCase().contains(normalizedQuery)
            || article.getDescription().toLowerCase().contains(normalizedQuery))
        .sorted(Comparator.comparing(Article::getName, String.CASE_INSENSITIVE_ORDER))
        .toList();
  }

  public Article create(final ArticleRequest request) {
    validateRequest(request);

    final Article article = new Article();
    article.setName(request.name().trim());
    article.setDescription(request.description().trim());
    article.setImage(request.image().trim());
    article.setPrice(request.price());

    return articleRepository.save(article);
  }

  public Article update(final UUID id, final ArticleRequest request) {
    validateRequest(request);

    final Article article = articleRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Article not found"));

    article.setName(request.name().trim());
    article.setDescription(request.description().trim());
    article.setImage(request.image().trim());
    article.setPrice(request.price());

    return articleRepository.save(article);
  }

  public void delete(final UUID id) {
    if (!articleRepository.existsById(id)) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Article not found");
    }

    articleRepository.deleteById(id);
  }

  private void validateRequest(final ArticleRequest request) {
    if (request == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request body is required");
    }

    validateText("name", request.name());
    validateText("description", request.description());
    validateImage("image", request.image());

    final BigDecimal price = request.price();
    if (price == null || price.compareTo(BigDecimal.ZERO) < 0) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "price is required and must be non-negative");
    }
  }

  private void validateText(final String field, final String value) {
    if (value == null || value.isBlank()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, field + " is required");
    }

    final int maxLength = switch (field) {
      case "name" -> NAME_MAX_LENGTH;
      case "description" -> DESCRIPTION_MAX_LENGTH;
      case "image" -> IMAGE_MAX_LENGTH;
      default -> 255;
    };

    if (value.trim().length() > maxLength) {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST,
          field + " is too long (max " + maxLength + " characters)");
    }
  }

  private void validateImage(final String field, final String value) {
    validateText(field, value);

    final String trimmed = value.trim();
    final boolean isHttpUrl = trimmed.startsWith("http://") || trimmed.startsWith("https://");
    final boolean isDataImage = trimmed.startsWith("data:image/");

    if (!isHttpUrl && !isDataImage) {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST,
          "image must be a valid http(s) URL or data:image/* base64 value");
    }

    if (trimmed.length() > IMAGE_MAX_LENGTH) {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST,
          field + " is too long (max " + IMAGE_MAX_LENGTH + " characters)");
    }
  }
}
