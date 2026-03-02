package org.skypro.skyshop.model.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.skypro.skyshop.model.Searchable;

import java.util.UUID;

public abstract class Product implements Searchable {
    private final String productName;
    private final UUID id;

    public Product(UUID id, String productName) {
        if (productName == null || productName.isBlank()) {
            throw new IllegalArgumentException("Название продукта не может быть null или пустым (включая пробелы)");
        }
        this.id = id;
        this.productName = productName;
    }

    @Override
    public String getId() {  // ← Изменён тип возврата: UUID → String
        return this.id.toString();  // ← Преобразуем UUID в строку
    }

    public String getProductName() {
        return this.productName;
    }

    abstract int getPrice();

    boolean isSpecial() {
        return false;
    }

    @Override
    @JsonIgnore
    public String getSearchTerm() {
        return productName;
    }

    @Override
    @JsonIgnore
    public String getContentType() {
        return "PRODUCT";
    }

    @Override
    public String getName() {
        return productName;
    }
}
