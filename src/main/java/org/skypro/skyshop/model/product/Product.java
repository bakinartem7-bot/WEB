package org.skypro.skyshop.model.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.skypro.skyshop.model.Searchable;

import java.util.Objects;
import java.util.UUID;

public abstract class Product implements Searchable {
    private final UUID id;
    private final String productName;

    public Product(UUID id, String productName) {
        this.id = Objects.requireNonNull(id, "ID продукта не может быть null");
        if (productName == null || productName.isBlank()) {
            throw new IllegalArgumentException("Название продукта не может быть null или пустым (включая пробелы)");
        }
        this.productName = productName;
    }

    @Override
    public String getId() {
        return id.toString();
    }

    public UUID getUuid() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public abstract int getPrice();

    public boolean isSpecial() {
        return false;
    }

    @Override
    @JsonIgnore
    public String getSearchTerm() {
        return productName;
    }

    @Override
    public String getContentType() {
        return "PRODUCT";
    }

    @Override
    public String getName() {
        return productName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Product{" + "id=" + id + ", productName='" + productName + '\'' + '}';
    }
}
