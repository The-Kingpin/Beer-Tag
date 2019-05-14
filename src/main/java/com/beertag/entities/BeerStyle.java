package com.beertag.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "beer_styles")
public class BeerStyle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "style")
    private String beerStyle;

    public BeerStyle(){

    }

    public BeerStyle(String beerStyle) {
        this.beerStyle = beerStyle;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "beerStyle")//, cascade = CascadeType.ALL)
    private Set<Beer> beers;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBeerStyle() {
        return beerStyle;
    }

    public void setBeerStyle(String beerStyle) {
        this.beerStyle = beerStyle;
    }

    public Set<Beer> getBeers() {
        return beers;
    }

    public void setBeers(Set<Beer> beers) {
        this.beers = beers;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof BeerStyle)) {
            return false;
        }
        BeerStyle t = (BeerStyle)o;
        return t.beerStyle.equals(beerStyle);
    }
}
