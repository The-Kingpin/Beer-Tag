package com.beertag.config;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BeerAlreadyAddedToTagException extends Exception{
    public BeerAlreadyAddedToTagException() {
        super("Beer already tagged here");
    }
}
