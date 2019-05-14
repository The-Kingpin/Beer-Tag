package com.beertag;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class BeerTagApplication {

    public static void main(String[] args) {
        SpringApplication.run(BeerTagApplication.class, args);
    }

	@Bean
	public ModelMapper getModelMapper(){
		return new ModelMapper();
	}

}
