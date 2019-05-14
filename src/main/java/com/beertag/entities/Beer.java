package com.beertag.entities;

import org.apache.tomcat.util.codec.binary.Base64;
import org.hibernate.annotations.Formula;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.IOException;
import java.sql.Blob;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Entity
@Table(name = "beers")
public class Beer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "beername")
    @NotEmpty
    @Size(min = 1, max = 50, message = "Beer name must be between 1 and 50 characters!")
    private String beerName;
    @Column(name = "brewery")
    @Size(min = 1, max = 50, message = "Brewery name must be between 1 and 50 characters!")
    private String brewery;
    @Column(name = "alcohol_by_volume")
    private double alcoholByVolume;
    @Column(name = "beer_description")
    private String description;
    @Lob
    @Column(name = "beer_picture")
    //private Blob beerPicture;
    private byte[] beerPicture;

    @Formula("(select ifnull(avg(r.rating), 0) from beers as b join rating as r on b.id = r.beer_id " +
            "where b.id = id)")
    private double avgRating;

    @Column(name = "isDeleted")
    private boolean isDeleted;

    @ManyToMany(fetch= FetchType.EAGER, cascade = CascadeType.ALL)
    //@Fetch(value = FetchMode.SUBSELECT)
    @JoinTable(name = "beers_tags",
            joinColumns = @JoinColumn(name = "beer_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<Tag> tags = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "style_id")
    private BeerStyle beerStyle;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "country_id")
    private Country country;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "drunkedBeers", cascade = CascadeType.ALL)
    //@Fetch(value = FetchMode.SUBSELECT)
    //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<User> drunkedBeers = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "wantedToDrinks", cascade = CascadeType.ALL)
    //@Fetch(value = FetchMode.SUBSELECT)
    //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<User> wantedToDrinks = new HashSet<>();

    @OneToMany(
            fetch = FetchType.EAGER,
            mappedBy = "beer",
            cascade = CascadeType.ALL
    )
    //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<Rating> ratings = new HashSet<>();



    public Beer() {
    }


    public Beer(String beerName) {
        this.beerName = beerName;
    }

    public Set<User> getDrunkedBeers() {
        return drunkedBeers;
    }

    public void setDrunkedBeers(Set<User> drunkedBeers) {
        this.drunkedBeers = drunkedBeers;
    }

    public Set<User> getWantedToDrinks() {
        return wantedToDrinks;
    }

    public Set<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(Set<Rating> ratings) {
        this.ratings = ratings;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

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


    public byte[] getBeerPicture() {
        return beerPicture;
    }

    public String printBeerPicture(){
        return new String(Base64.encodeBase64(this.beerPicture));
    }


    public void setBeerPicture(MultipartFile beerPicture) throws IOException {
        this.beerPicture = beerPicture.getBytes();
    }

    public void updateBeerPicture(byte[] newPicture){
        this.beerPicture = newPicture;
    }


    public BeerStyle getBeerStyle() {
        return beerStyle;
    }

    public void setBeerStyle(BeerStyle beerStyle) {
        this.beerStyle = beerStyle;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public Set<Integer> getTagIds(){
       Set<Integer> tagIds = new HashSet<Integer>();

       Set<Tag> tags = getTags();

       Iterator<Tag> tagsIterator = tags.iterator();

       while(tagsIterator.hasNext()) {
           Tag tag = tagsIterator.next();

           tagIds.add(tag.getId());
       }
       return tagIds;
    }

    public int getStyleId(){

        BeerStyle style = getBeerStyle();

        int styleId = style.getId();

        return styleId;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public double getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(double avgRating) {
        this.avgRating = avgRating;
    }

    public void setWantedToDrinks(Set<User> wantedToDrinks) {
        this.wantedToDrinks = wantedToDrinks;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
