package org.skypro.skyshop.model.basket;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import static java.util.Collections.unmodifiableMap;

@Component
@SessionScope
public class ProductBasket {

    private final Map<UUID, Integer> items = new HashMap<>();

    public void addProduct(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("ID товара не может быть null");
        }
        items.merge(id, 1, Integer::sum);
    }

    public void removeProduct(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("ID товара не может быть null");
        }
        items.remove(id);
    }

    public void setQuantity(UUID id, int quantity) {
        if (id == null) {
            throw new IllegalArgumentException("ID товара не может быть null");
        }
        if (quantity < 0) {
            throw new IllegalArgumentException("Количество не может быть отрицательным");
        }
        items.put(id, quantity);
    }

    public boolean containsProduct(UUID id) {
        return items.containsKey(id);
    }

    public Map<UUID, Integer> getItems() {
        return unmodifiableMap(items);
    }

    public int getTotalItems() {
        return items.values().stream().mapToInt(Integer::intValue).sum();
    }
}
