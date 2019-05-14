package com.beertag.serviceImpl;

import com.beertag.config.BeerAlreadyAddedToTagException;
import com.beertag.config.BeerAlreadyMarkedException;
import com.beertag.config.BeerAlreadyVotedException;
import com.beertag.config.ItemAlreadyExistException;
import com.beertag.entities.*;
import com.beertag.repository.*;
import com.beertag.service.BeerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Service
public class BeerServiceImpl implements BeerService {

    private BeerRepository beerRepository;
    private UserRepository userRepository;
    private TagRepository tagRepository;
    private RatingRepository ratingRepository;
    private StyleRepository styleRepository;
    private CountryRepository countryRepository;


    @Autowired
    public BeerServiceImpl(BeerRepository beerRepository, UserRepository userRepository, TagRepository tagRepository,
                           RatingRepository ratingRepository, StyleRepository styleRepository,
                           CountryRepository countryRepository) {
        this.beerRepository = beerRepository;
        this.userRepository = userRepository;
        this.tagRepository = tagRepository;
        this.ratingRepository = ratingRepository;
        this.styleRepository = styleRepository;
        this.countryRepository = countryRepository;
    }

    @Override
    public List<Beer>getAllBeers() {

        return beerRepository.findAll().stream()
                .filter(isDel -> isDel.isDeleted()==false)
                .collect(Collectors.toList());

    }

    public List<Country>findAllCountries(){
        return countryRepository.findAll();
    }

    @Override
    public List<BeerStyle> getAllstyles() {
        return   styleRepository.findAll();
    }


    @Override
    public Beer getBeerById(int id) {
            return beerRepository.getBeersById(id);
    }

    @Override
    public List<Beer> sortBeersByName() {
        return getAllBeers().stream()
                .sorted(Comparator.comparing(Beer::getBeerName))
                .collect(Collectors.toList());
    }

    @Override
    public List<Beer> sortBeersByABV() {
        return getAllBeers().stream()
                .sorted(Comparator.comparing(Beer::getAlcoholByVolume).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<Beer> sortBeersByRating() {
        return getAllBeers().stream()
                .sorted(Comparator.comparing(Beer::getAvgRating).reversed())
                .collect(Collectors.toList());
    }


    @Override
    public Beer updateBeer(Beer upd_beer, int id) {

        Beer beer = beerRepository.getBeersById(id);

        BeerStyle newStyle = upd_beer.getBeerStyle();
        BeerStyle bs = styleRepository.findBeerStyleByBeerStyle(newStyle.getBeerStyle());
        beer.setBeerStyle(bs);

        Country newCountry = upd_beer.getCountry();
        Country c = countryRepository.findCountriesByName(newCountry.getName());
        beer.setCountry(c);
        beer.setBeerName(upd_beer.getBeerName());
        beer.setAlcoholByVolume(upd_beer.getAlcoholByVolume());
        beer.updateBeerPicture(upd_beer.getBeerPicture());
        beer.setBrewery(upd_beer.getBrewery());
        beer.setDescription(upd_beer.getDescription());

        return beerRepository.save(beer);
//        return null;

    }

    @Override
    public void markAsWish(int user_id, int beer_id) throws BeerAlreadyMarkedException {
            Beer beer = beerRepository.getBeersById(beer_id);
            User user = userRepository.getUserById(user_id);
            if(user.getWantedToDrinks().contains(beer)){
                throw new BeerAlreadyMarkedException();
            }
            user.getWantedToDrinks().add(beer);
            userRepository.save(user);
    }

    @Override
    public void unmarkAsWish(int user_id, int beer_id){
        beerRepository.unmarkWanted(user_id, beer_id);
    }

    @Override
    public void unmarkAsDrunk(int user_id, int beer_id){
        beerRepository.unmarkDrunkBeers(user_id, beer_id);
    }

    @Override
    public void markAsDrunk(int user_id, int beer_id) throws BeerAlreadyMarkedException {
        Beer beer = beerRepository.getBeersById(beer_id);
        User user = userRepository.getUserById(user_id);
        if(user.getDrunkedBeers().contains(beer)){
            throw new BeerAlreadyMarkedException();
        }
        user.getDrunkedBeers().add(beer);
        userRepository.save(user);
    }

    @Override
    public Beer createNewBeer (Beer beer) throws ItemAlreadyExistException{

        Beer existingBeer = beerRepository.findBeerByBeerName(beer.getBeerName());

        if (existingBeer!= null){
            throw new ItemAlreadyExistException(beer.getBeerName());
        }

        BeerStyle newStyle = beer.getBeerStyle();
        BeerStyle bs = styleRepository.findBeerStyleByBeerStyle(newStyle.getBeerStyle());
        beer.setBeerStyle(bs);
        Country newCountry = beer.getCountry();
        Country c = countryRepository.findCountriesByName(newCountry.getName());
        beer.setCountry(c);

        return beerRepository.saveAndFlush(beer);
    }


    public void rateBeer(int beer_id, int user_id, int rating) throws BeerAlreadyVotedException{
        Rating existRate = ratingRepository.findRatingByBeer_IdAndUser_Id(beer_id, user_id);
        if(existRate != null){
            throw new BeerAlreadyVotedException();
        }
        Rating rate = new Rating();
        Beer b = beerRepository.findBeerById(beer_id);
        User u = userRepository.findUserById(user_id);
        rate.setUser(u);
        rate.setBeer(b);
        rate.setBeerRating(rating);
        ratingRepository.save(rate);
    }

    @Override
    public void addBeerToTag(int beer_id, String tag_name) throws BeerAlreadyAddedToTagException{

        Beer beer = beerRepository.findBeerById(beer_id);
        Tag existingTag = tagRepository.findTagByTagName(tag_name);

        if(existingTag != null){
            if(beer.getTags().contains(existingTag)) {
                throw new BeerAlreadyAddedToTagException(); //не работи
            } else {
                //existingTag.getBeers().add(beer);
                beer.getTags().add(existingTag);
                //tagRepository.save(existingTag);
                beerRepository.save(beer);
            }
        } else {
            Tag tag = new Tag();
            tag.setTagName(tag_name);
            //tag.getBeers().add(beer);
            beer.getTags().add(tag);
            beerRepository.save(beer);
            //tagRepository.save(tag);
        }
    }

    @Override
    public List<Beer> getBeersByTags(String tag) {
        Tag t = tagRepository.findTagByTagName(tag);
        return getAllBeers().stream()
                .filter(b -> b.getTagIds().contains(t.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Beer> getBeersByStyle(String style){
        BeerStyle s = styleRepository.findBeerStyleByBeerStyle(style);
        return getAllBeers().stream()
                .filter(st -> st.getStyleId() == s.getId())
                //.filter(st -> st.getBeerStyle().equals(s))
                .collect(Collectors.toList());
    }

    @Override
    public List<Beer> getBeersByCountry(String name){
        Country country = countryRepository.findCountriesByName(name);
        return getAllBeers().stream()
                .filter(c -> c.getCountry().equals(country))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteBeer(int id){
        Beer beer = beerRepository.findBeerById(id);
        beer.setDeleted(true);
        beerRepository.save(beer);
    }

}
