package com.beertag.repository;

import com.beertag.entities.BeerStyle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StyleRepository extends JpaRepository<BeerStyle, Integer> {
    BeerStyle findBeerStyleByBeerStyle(String name);

    List<BeerStyle> findAll();
}
