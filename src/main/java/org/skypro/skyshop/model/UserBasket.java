package org.skypro.skyshop.model;

import java.util.List;

public record UserBasket(List<BasketItem> items) {
    public int getTotal() {
        return items.stream()
                .mapToInt(item -> item.product().getPrice() * item.quantity())
                .sum();
    }
}
