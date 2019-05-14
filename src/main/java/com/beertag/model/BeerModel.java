package com.beertag.model;

import com.beertag.entities.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class BeerModel {

    private int id;
    private String beerName;
    private String brewery;
    private double alcoholByVolume;
    private String description;
    private MultipartFile beerPicture;
    private List<Tag> tags;
    private BeerStyle beerStyle;
    private Country country;
    private List<User> drunkedBeers;
    private List<User> wantedToDrinks;
    private int rating;
    private int avgRating;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBeerName() {
        return beerName;
    }

    public void setBeerName(String beerName) {
        this.beerName = beerName;
    }

    public String getBrewery() {
        return brewery;
    }

    public void setBrewery(String brewery) {
        this.brewery = brewery;
    }

    public double getAlcoholByVolume() {
        return alcoholByVolume;
    }

    public void setAlcoholByVolume(double alcoholByVolume) {
        this.alcoholByVolume = alcoholByVolume;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MultipartFile getBeerPicture() {
        return beerPicture;
    }

    public void setBeerPicture(MultipartFile beerPicture) {
        this.beerPicture = beerPicture;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public BeerStyle getBeerStyle() {
        return beerStyle;
    }

    public void setBeerStyle(BeerStyle beerStyle) {
        this.beerStyle = beerStyle;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public List<User> getDrunkedBeers() {
        return drunkedBeers;
    }

    public void setDrunkedBeers(List<User> drunkedBeers) {
        this.drunkedBeers = drunkedBeers;
    }

    public List<User> getWantedToDrinks() {
        return wantedToDrinks;
    }

    public void setWantedToDrinks(List<User> wantedToDrinks) {
        this.wantedToDrinks = wantedToDrinks;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public double getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(int avgRating) {
        this.avgRating = avgRating;
    }
}
