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
        // Продукты
        products.put(UUID.randomUUID(), new SimpleProduct(
                UUID.randomUUID(), "Яблоко", 150));
        products.put(UUID.randomUUID(), new DiscountedProduct(
                UUID.randomUUID(), "Банан", 200, 25));

        // Статьи
        articles.put(UUID.randomUUID(), new Article(
                UUID.randomUUID(), "Как выбрать фрукты", "Советы по выбору..."));
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
}
