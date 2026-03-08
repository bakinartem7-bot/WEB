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
        items.computeIfAbsent(id, k -> 0);
        items.put(id, items.get(id) + 1);
    }

    public Map<UUID, Integer> getItems() {
        return unmodifiableMap(items);
    }
}
