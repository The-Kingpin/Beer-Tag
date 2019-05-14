package com.beertag.service;

import com.beertag.config.BeerAlreadyAddedToTagException;
import com.beertag.config.BeerAlreadyMarkedException;
import com.beertag.config.BeerAlreadyVotedException;
import com.beertag.config.ItemAlreadyExistException;
import com.beertag.entities.Beer;
import com.beertag.entities.BeerStyle;
import com.beertag.entities.Country;

import java.util.List;

public interface BeerService{

    List<Beer>getAllBeers();

    Beer getBeerById(int beer_id);

    List<Beer>sortBeersByName();

    List<Beer> sortBeersByABV();

    List<Beer> sortBeersByRating();

    Beer updateBeer(Beer upd_beer, int id);

    void markAsWish(int user_id, int beer_id) throws BeerAlreadyMarkedException;

    Beer createNewBeer (Beer beer) throws ItemAlreadyExistException;

    void markAsDrunk(int user_id, int beer_id) throws BeerAlreadyMarkedException;

    void addBeerToTag(int beer_id, String tag_name) throws BeerAlreadyAddedToTagException;

    List<Beer> getBeersByTags(String tag);

    void deleteBeer(int id);

    void rateBeer(int beer_id, int user_id, int rating) throws BeerAlreadyVotedException;

    List<Beer> getBeersByStyle(String style);

    List<Beer> getBeersByCountry(String name);

    void unmarkAsWish(int user_id, int beer_id);

    void unmarkAsDrunk(int user_id, int beer_id);

    List<Country>findAllCountries();

    List<BeerStyle> getAllstyles();
}
