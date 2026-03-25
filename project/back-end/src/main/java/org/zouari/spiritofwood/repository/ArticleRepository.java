package org.zouari.spiritofwood.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zouari.spiritofwood.model.Article;

public interface ArticleRepository extends JpaRepository<Article, UUID> {
}
