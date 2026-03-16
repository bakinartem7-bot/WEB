package org.skypro.skyshop.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.skyshop.model.basket.ProductBasket;
import org.skypro.skyshop.model.basket.UserBasket;
import org.skypro.skyshop.model.product.Product;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * Тестовый класс для BasketService.
 * Проверяет сценарии работы с корзиной и взаимодействие с StorageService.
 */
@ExtendWith(MockitoExtension.class)
class BasketServiceTest {

    @Mock
    private StorageService storageService;

    @Mock
    private ProductBasket productBasket;

    @InjectMocks
    private BasketService basketService;

    private UUID validProductId;
    private UUID invalidProductId;
    private Product validProduct;

    @BeforeEach
    void setUp() {
        // Инициализация тестовых данных
        validProductId = UUID.randomUUID();
        invalidProduct/Id = UUID.randomUUID();

        validProduct = new Product(validProductId, "Valid Product", 100.0);
    }

    @Test
    void addProduct_whenProductNotExists_shouldThrowException() {

        when(storageService.getProductById(invalidProductId))
                .thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            basketService.addProduct(invalidProductId);
        });

        verify(productBasket, never()).addProduct(any());
    }

    @Test
    void addProduct_whenProductExists_shouldAddToBasket() {
        // Настраиваем мок: StorageService находит товар
        when(storageService.getProductById(validProductId))
                .thenReturn(Optional.of(validProduct));

        basketService.addProduct(validProductId);

        // Проверяем, что метод addProduct был вызван один раз
        verify(productBasket, times(1)).addProduct(validProductId);
    }

    @Test
    void getUserBasket_whenBasketEmpty_shouldReturnEmptyBasket() {

        when(productBasket.getItems()).thenReturn(new HashMap<>());

        UserBasket userBasket = basketService.getUserBasket();

        assertThat(userBasket.getItems()).isEmpty();
        assertThat(userBasket.getTotal()).isZero();
    }

    @Test
    void getUserBasket_whenBasketHasItems_shouldCalculateTotal() {

        Map<UUID, Integer> basketItems = new HashMap<>();
        basketItems.put(validProductId, 2);


        when(productBasket.getItems()).thenReturn(basketItems);
        when(storageService.getProductById(validProductId))
                .thenReturn(Optional.of(validProduct));


        UserBasket userBasket = basketService.getUserBasket();

        assertThat(userBasket.getItems())
                .hasSize(1)
                .containsKey(validProductId);


        assertThat(userBasket.getTotal())
                .isEqualTo(200.0);  // 2 × 100.0
    }

    @Test
    void getUserBasket_withMultipleProducts_shouldSumTotalsCorrectly() {

        UUID secondProductId = UUID.randomUUID();
        Product secondProduct = new Product(secondProductId, "Second Product", 50.0);

        Map<UUID, Integer> basketItems = new HashMap<>();
        basketItems.put(validProductId, 1);
        basketItems.put(secondProductId, 3);

        when(productBasket.getItems()).thenReturn(basketItems);
        when(storageService.getProductById(validProductId))
                .thenReturn(Optional.of(validProduct));
        when(storageService.getProductById(secondProductId))
                .thenReturn(Optional.of(secondProduct));

        UserBasket userBasket = basketService.getUserBasket();

        assertThat(userBasket.getItems())
                .hasSize(2)
                .containsKeys(validProductId, secondProductId);

        assertThat(userBasket.getTotal())
                .isEqualTo(250.0);  // (1 × 100) + (3 × 50)
    }
}
