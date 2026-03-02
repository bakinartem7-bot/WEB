package org.skypro.skyshop.model.product;

import java.util.UUID;

public class DiscountedProduct extends Product {
    private final int basePrice;
    private final double discountPercent;

    public DiscountedProduct(UUID id, String productName, int basePrice, double discountPercent) {
        super(id, productName);
        if (basePrice <= 0) {
            throw new IllegalArgumentException("Базовая цена должна быть строго больше 0 (получено: " + basePrice + ")");
        }

        if (discountPercent < 0 || discountPercent > 100) {
            throw new IllegalArgumentException("Процент скидки должен быть в диапазоне [0, 100] (получено: " + discountPercent + "%)");
        }
        this.basePrice = basePrice;
        this.discountPercent = discountPercent;
    }

    @Override
    public int getPrice() {
        return (int) (basePrice * (1 - discountPercent / 100));
    }

    @Override
    public String toString() {
        return getProductName() + ": " + getPrice() + " (" + (int)discountPercent + " %)";
    }
}