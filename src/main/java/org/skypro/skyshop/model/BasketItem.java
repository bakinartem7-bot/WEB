package org.skypro.skyshop.model;

import org.skypro.skyshop.model.product.Product;

public record BasketItem(Product product, int quantity) {}
