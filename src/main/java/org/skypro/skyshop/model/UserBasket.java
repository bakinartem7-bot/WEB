package org.skypro.skyshop.model;

import java.util.Collections;
import java.util.List;
import static java.util.Objects.requireNonNull;

public record UserBasket(List<BasketItem> items, int total) {

    public UserBasket(List<BasketItem> items, int total) {
        requireNonNull(items, "Список товаров не может быть null");
        this.items = Collections.unmodifiableList(items);

        int calculatedTotal = items.stream()
                .mapToInt(item -> item.product().getPrice() * item.quantity())
                .sum();

        this.total = calculatedTotal;

        if (calculatedTotal < 0) {
            throw new IllegalArgumentException("Общая стоимость не может быть отрицательной");
        }
    }

    public static UserBasket of(List<BasketItem> items) {
        return new UserBasket(items, 0);
    }
}
