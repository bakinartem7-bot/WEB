package org.skypro.skyshop.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.skyshop.model.Searchable;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.product.SimpleProduct;

import java.util.*;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class SearchServiceTest {

    @Mock
    private StorageService storageService;

    @InjectMocks
    private SearchService searchService;

    private Product testProduct;

    @BeforeEach
    void setUp() {
        testProduct = new SimpleProduct(UUID.randomUUID(), "TestProduct", 100.0);
    }

    /**
     * Тест: поиск при пустом хранилище.
     * Ожидаем: пустой список результатов.
     */
    @Test
    void search_whenStorageEmpty_shouldReturnEmptyList() {
        // Настраиваем мок: возвращает пустые коллекции
        when(storageService.getAllSearchable()).thenReturn(Collections.emptyList());

        List<Searchable> results = searchService.search("query");

        assertThat(results).isEmpty();
    }

    /**
     * Тест: хранилище не пустое, но нет совпадений по запросу.
     * Ожидаем: пустой список.
     */
    @Test
    void search_whenNoMatches_shouldReturnEmptyList() {
        List<Searchable> allItems = Collections.singletonList(testProduct);
        when(storageService.getAllSearchable()).thenReturn(allItems);

        List<Searchable> results = searchService.search("NonExistingProduct");

        assertThat(results).isEmpty();
    }

    /**
     * Тест: есть подходящий объект (имя содержит "Test").
     * Ожидаем: список размером 1 с найденным продуктом.
     */
    @Test
    void search_whenMatchFound_shouldReturnSingleItem() {
        List<Searchable> allItems = Collections.singletonList(testProduct);
        when(storageService.getAllSearchable()).thenReturn(allItems);

        List<Searchable> results = searchService.search("Test");

        assertThat(results)
                .hasSize(1)
                .contains(testProduct);
    }

    /**
     * Дополнительный тест: поиск по частичному совпадению в названии.
     */
    @Test
    void search_partialMatchInName_shouldFindProduct() {
        Product anotherProduct = new SimpleProduct(UUID.randomUUID(), "AnotherTestProduct", 200.0);
        List<Searchable> allItems = Arrays.asList(testProduct, anotherProduct);
        when(storageService.getAllSearchable()).thenReturn(allItems);

        List<Searchable> results = searchService.search("Another");
        assertThat(results)
                .hasSize(1)
                .contains(anotherProduct);
    }
}
