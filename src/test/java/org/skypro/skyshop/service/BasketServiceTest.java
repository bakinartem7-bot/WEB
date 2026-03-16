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

    /**
     * Сценарий: попытка добавить несуществующий товар в корзину.
     * Ожидаемое поведение: выбрасывается IllegalArgumentException.
     */
    @Test
    void addProduct_whenProductNotExists_shouldThrowException() {
        // Настраиваем мок: StorageService возвращает пустой Optional для несуществующего товара
        when(storageService.getProductById(invalidProductId))
                .thenReturn(Optional.empty());

        // Проверяем, что метод выбрасывает исключение
        assertThrows(IllegalArgumentException.class, () -> {
            basketService.addProduct(invalidProductId);
        });

        // Убеждаемся, что addProduct в корзине НЕ вызывался
        verify(productBasket, never()).addProduct(any());
    }

    /**
     * Сценарий: добавление существующего товара в корзину.
     * Ожидаемое поведение: вызов метода addProduct у ProductBasket.
     */
    @Test
    void addProduct_whenProductExists_shouldAddToBasket() {
        // Настраиваем мок: StorageService находит товар
        when(storageService.getProductById(validProductId))
                .thenReturn(Optional.of(validProduct));

        // Выполняем действие
        basketService.addProduct(validProductId);

        // Проверяем, что метод addProduct был вызван один раз
        verify(productBasket, times(1)).addProduct(validProductId);
    }

    /**
     * Сценарий: получение пустой корзины.
     * Ожидаемое поведение: UserBasket с пустым списком товаров и total = 0.
     */
    @Test
    void getUserBasket_whenBasketEmpty_shouldReturnEmptyBasket() {
        // Настраиваем мок: корзина пуста
        when(productBasket.getItems()).thenReturn(new HashMap<>());

        // Получаем корзину
        UserBasket userBasket = basketService.getUserBasket();

        // Проверки
        assertThat(userBasket.getItems()).isEmpty();
        assertThat(userBasket.getTotal()).isZero();
    }

    /**
     * Сценарий: получение корзины с товарами.
     * Ожидаемое поведение: корректный расчёт total (цена × количество).
     */
    @Test
    void getUserBasket_whenBasketHasItems_shouldCalculateTotal() {
        // Подготавливаем данные: в корзине 2 шт. validProduct
        Map<UUID, Integer> basketItems = new HashMap<>();
        basketItems.put(validProductId, 2);

        // Настраиваем моки
        when(productBasket.getItems()).thenReturn(basketItems);
        when(storageService.getProductById(validProductId))
                .thenReturn(Optional.of(validProduct));


        // Получаем корзину
        UserBasket userBasket = basketService.getUserBasket();

        // Проверки
        assertThat(userBasket.getItems())
                .hasSize(1)
                .containsKey(validProductId);


        assertThat(userBasket.getTotal())
                .isEqualTo(200.0);  // 2 × 100.0
    }

    /**
     * Дополнительный сценарий: корзина с несколькими разными товарами.
     * Проверяет корректность суммирования total.
     */
    @Test
    void getUserBasket_withMultipleProducts_shouldSumTotalsCorrectly() {
        // Создаём второй товар
        UUID secondProductId = UUID.randomUUID();
        Product secondProduct = new Product(secondProductId, "Second Product", 50.0);

        // Заполняем корзину: 1 шт. первого товара, 3 шт. второго
        Map<UUID, Integer> basketItems = new HashMap<>();
        basketItems.put(validProductId, 1);
        basketItems.put(secondProductId, 3);

        // Настраиваем моки
        when(productBasket.getItems()).thenReturn(basketItems);
        when(storageService.getProductById(validProductId))
                .thenReturn(Optional.of(validProduct));
        when(storageService.getProductById(secondProductId))
                .thenReturn(Optional.of(secondProduct));

        // Получаем корзину
        UserBasket userBasket = basketService.getUserBasket();

        // Проверки
        assertThat(userBasket.getItems())
                .hasSize(2)
                .containsKeys(validProductId, secondProductId);

        assertThat(userBasket.getTotal())
                .isEqualTo(250.0);  // (1 × 100) + (3 × 50)
    }
}
