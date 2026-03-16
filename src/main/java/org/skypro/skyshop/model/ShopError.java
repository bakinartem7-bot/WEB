package org.skypro.skyshop.model;

import java.util.Objects;

public record ShopError(String code, String message) {

    public ShopError {
        Objects.requireNonNull(code, "Код ошибки не может быть null");
        Objects.requireNonNull(message, "Сообщение об ошибке не может быть null");
    }
}
