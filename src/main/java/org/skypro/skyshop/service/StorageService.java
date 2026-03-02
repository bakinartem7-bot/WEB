package org.skypro.skyshop.service;

import org.skypro.skyshop.model.Searchable;
import org.skypro.skyshop.model.article.Article;
import org.skypro.skyshop.model.product.DiscountedProduct;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.product.SimpleProduct;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class StorageService {
    private final Map<UUID, Product> products = new HashMap<>();
    private final Map<UUID, Article> articles = new HashMap<>();

    public StorageService() {
        initTestData();
    }

    private void initTestData() {
        UUID appleId = UUID.randomUUID();
        products.put(appleId, new SimpleProduct(appleId, "Яблоко", 150));

        UUID bananaId = UUID.randomUUID();
        products.put(bananaId, new DiscountedProduct(bananaId, "Банан", 200, 25));

        UUID articleId = UUID.randomUUID();
        articles.put(articleId, new Article(articleId, "Как выбрать фрукты", "Советы по выбору..."));
    }

    public Collection<Product> getAllProducts() {
        return products.values();
    }

    public Collection<Article> getAllArticles() {
        return articles.values();
    }

    public Collection<Searchable> getAllSearchable() {
        return Stream.concat(
                        products.values().stream(),
                        articles.values().stream())
                .collect(Collectors.toList());
    }

    public Optional<Product> getProductById(UUID id) {
        return Optional.ofNullable(products.get(id));
    }
}
