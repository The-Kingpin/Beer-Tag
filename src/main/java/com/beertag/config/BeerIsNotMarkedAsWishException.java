package com.beertag.config;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BeerIsNotMarkedAsWishException extends Exception {
    public BeerIsNotMarkedAsWishException() {
        super("Beer is not marked as wish to unmark it");
    }
}
