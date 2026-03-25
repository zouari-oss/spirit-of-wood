package org.zouari.spiritofwood.model;

import java.math.BigDecimal;

public record ArticleRequest(String name, String description, String image, BigDecimal price) {
}
