package org.skypro.skyshop.model.product;

import org.skypro.skyshop.model.Searchable;
import org.skypro.skyshop.model.product.Product;

import java.util.UUID;

public class FixPriceProduct extends Product implements Searchable {
    private int fixedPrice;

    public FixPriceProduct(UUID id, String productName, int fixedPrice) {
        super(id, productName);
        if (fixedPrice <= 0) {
            throw new IllegalArgumentException(
                    "Фиксированная цена должна быть строго больше 0 (получено: " + fixedPrice + ")"
            );
        }
        this.fixedPrice = fixedPrice;
    }

    @Override
    public String getId() {
        return super.getId();
    }

    @Override
    public int getPrice() {
        return fixedPrice;
    }

    public int getFixedPrice() {
        return fixedPrice;
    }
}
