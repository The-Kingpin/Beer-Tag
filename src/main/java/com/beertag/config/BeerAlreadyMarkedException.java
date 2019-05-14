package com.beertag.config;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BeerAlreadyMarkedException extends Exception{
    public BeerAlreadyMarkedException() {
        super("You have already selected this option");
    }
}
