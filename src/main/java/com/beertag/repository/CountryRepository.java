package com.beertag.repository;

import com.beertag.entities.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CountryRepository extends JpaRepository<Country, Integer> {

    Country findCountriesByName(String name);

    @Override
    List<Country> findAll();
}
