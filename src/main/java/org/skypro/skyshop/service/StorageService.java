package org.skypro.skyshop.service;


import org.skypro.skyshop.model.Searchable;
import org.skypro.skyshop.model.article.Article;
import org.skypro.skyshop.model.product.DiscountedProduct;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.product.SimpleProduct;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class StorageService {
    // Хранилища: Map<UUID, Entity>
    private final Map<UUID, Product> products = new HashMap<>();
    private final Map<UUID, Article> articles = new HashMap<>();

    // Конструктор: заполняем тестовыми данными
    public StorageService() {
        initTestData();
    }

    // Приватный метод для инициализации данных
    private void initTestData() {
        // Добавляем 2 продукта
        products.put(
                UUID.randomUUID(),
                new SimpleProduct(
                        UUID.randomUUID(),
                        "Яблоко",
                        150
                )
        );
        products.put(
                UUID.randomUUID(),
                new DiscountedProduct(
                        UUID.randomUUID(),
                        "Банан",
                        200,
                        25  // скидка 25%
                )
        );

        articles.put(
                UUID.randomUUID(),
                new Article(
                        UUID.randomUUID(),
                        "Как выбрать фрукты",
                        "Советы по выбору свежих фруктов: смотрите на цвет, запах и текстуру..."
                )
        );
    }

    public Collection<Product> getAllProducts() {
        return products.values();
    }

    public Collection<Article> getAllArticles() {
        return articles.values();
    }
    public Collection<Searchable> getAllSearchable() {
        List<Searchable> searchableItems = new ArrayList<>();
        searchableItems.addAll(products.values());  // products — ваша Map<String, Product>
        searchableItems.addAll(articles.values());     // articles — ваша Map<String, Article>
        return searchableItems;
    }
}

