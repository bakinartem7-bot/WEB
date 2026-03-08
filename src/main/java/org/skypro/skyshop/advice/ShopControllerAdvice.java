package org.skypro.skyshop.advice;

import org.skypro.skyshop.exception.NoSuchProductException;
import org.skypro.skyshop.model.ShopError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ShopControllerAdvice {

    @ExceptionHandler(NoSuchProductException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ShopError handleNoSuchProductException(NoSuchProductException ex) {
        return new ShopError("PRODUCT_NOT_FOUND", ex.getMessage());
    }
}
