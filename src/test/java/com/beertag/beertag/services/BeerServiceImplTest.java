package com.beertag.beertag.services;

import com.beertag.config.BeerAlreadyAddedToTagException;
import com.beertag.config.BeerAlreadyMarkedException;
import com.beertag.config.BeerAlreadyVotedException;
import com.beertag.config.ItemAlreadyExistException;
import com.beertag.entities.*;
import com.beertag.repository.*;
import com.beertag.serviceImpl.BeerServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

@RunWith(MockitoJUnitRunner.class)
public class BeerServiceImplTest {

    @Mock
    BeerRepository beerRepository;

    @Mock
    StyleRepository styleRepository;

    @Mock
    CountryRepository countryRepository;

    @Mock
    TagRepository tagRepository;

    @Mock
    RatingRepository ratingRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    BeerServiceImpl beerServiceImpl;

    @InjectMocks
    BeerServiceImpl beerService;

    @Test
    public void GetAllBeers_ReturnCorrectSizeOfBeers() {
        Mockito.when(beerRepository.findAll())
                .thenReturn(Arrays.asList(
                        new Beer(),
                        new Beer(),
                        new Beer()
                ));

        List<Beer> result = beerService.getAllBeers();

        Assert.assertEquals(3, result.size());
        Assert.assertFalse(result.get(0).isDeleted());
        Assert.assertFalse(result.get(1).isDeleted());
        Assert.assertFalse(result.get(2).isDeleted());
    }

    @Test
    public void getBeerWhenBeerIdGiven_ShouldReturnCorrectBeer() {
        Mockito.when(beerRepository.getBeersById(1))
                .thenReturn(new Beer());

        Beer result = beerService.getBeerById(1);
        result.setBeerName("Zagorka");

        Assert.assertEquals("Zagorka", result.getBeerName());
    }

    @Test
    public void sortBeersByName_SouldReturnCorrectSortList(){
        List<Beer> beers = new ArrayList<>();
        beers.add(new Beer());
        beers.add(new Beer());
        beers.add(new Beer());
        beers.get(0).setBeerName("Zagorka");
        beers.get(1).setBeerName("Ariana");
        beers.get(2).setBeerName("Kamenitza");
        Mockito.when(beerRepository.findAll())
                .thenReturn(beers);

        beers = beerService.sortBeersByName();
        String[]arr = {beers.get(0).getBeerName(), beers.get(1).getBeerName(), beers.get(2).getBeerName()};
        String[] expected = {"Ariana", "Kamenitza", "Zagorka"};

        Assert.assertEquals(arr[0], expected[0]);
        Assert.assertEquals(arr[1], expected[1]);
        Assert.assertEquals(arr[2], expected[2]);
    }

    @Test
    public void sortBeersByABV_SouldReturnCorrectSortList(){
        List<Beer> beers = new ArrayList<>();
        beers.add(new Beer());
        beers.add(new Beer());
        beers.add(new Beer());
        beers.get(0).setAlcoholByVolume(3);
        beers.get(1).setAlcoholByVolume(4);
        beers.get(2).setAlcoholByVolume(5);

        Mockito.when(beerRepository.findAll()).thenReturn(beers);

        beers = beerService.sortBeersByABV();
        double[] arr = {beers.get(0).getAlcoholByVolume(), beers.get(1).getAlcoholByVolume(), beers.get(2).getAlcoholByVolume()};
        double[] expected = {5, 4, 3};

        Assert.assertEquals(Double.doubleToLongBits(arr[0]), Double.doubleToLongBits(expected[0]));
        //or use - Assert.assertEquals(0, Double.compareTo(arr[0], expected[0]));
        Assert.assertEquals(Double.doubleToLongBits(arr[1]), Double.doubleToLongBits(expected[1]));
        Assert.assertEquals(Double.doubleToLongBits(arr[2]), Double.doubleToLongBits(expected[2]));
    }

    @Test
    public void sortBeersByRating_SouldReturnCorrectSortList(){
        List<Beer> beers = new ArrayList<>();
        beers.add(new Beer());
        beers.add(new Beer());
        beers.add(new Beer());
        beers.get(0).setAvgRating(3);
        beers.get(1).setAvgRating(4);
        beers.get(2).setAvgRating(5);
        Mockito.when(beerRepository.findAll())
                .thenReturn(beers);

        beers = beerService.sortBeersByRating();
        double[] arr = {beers.get(0).getAvgRating(), beers.get(1).getAvgRating(), beers.get(2).getAvgRating()};
        double[] expected = {5, 4, 3};

        Assert.assertEquals(Double.doubleToLongBits(arr[0]), Double.doubleToLongBits(expected[0]));
        //or use - Assert.assertEquals(0, Double.compareTo(arr[0], expected[0]));
        Assert.assertEquals(Double.doubleToLongBits(arr[1]), Double.doubleToLongBits(expected[1]));
        Assert.assertEquals(Double.doubleToLongBits(arr[2]), Double.doubleToLongBits(expected[2]));
    }

    @Test
    public void UpdateBeer_ShouldReturnCorrectUpdatedBeer() {

        Beer beer = new Beer();
        beer.setId(1);

        BeerStyle style = new BeerStyle();
        style.setBeerStyle("Brown");

        Country country = new Country();
        country.setName("UNITED KINGDOM");

        Mockito.when(beerRepository.getBeersById(1)).thenReturn(beer);
        Mockito.when(styleRepository.findBeerStyleByBeerStyle("Brown")).thenReturn(style);
        Mockito.when(countryRepository.findCountriesByName("UNITED KINGDOM")).thenReturn(country);

        Beer updBeer = new Beer();
        updBeer.setBeerName("beer");
        updBeer.setBrewery("brewery");
        updBeer.setAlcoholByVolume(5);
        updBeer.setDescription("bla");
        updBeer.setBeerStyle(style);
        updBeer.setCountry(country);

        beerService.updateBeer(updBeer, 1);

        Mockito.verify(beerRepository, Mockito.times(1)).getBeersById(1);
        Assert.assertEquals("beer", beer.getBeerName());
        Assert.assertEquals("Brown", beer.getBeerStyle().getBeerStyle());
    }

    @Test
    public void CreateBeer_ShouldReturnCorrectCreatedBeer() throws ItemAlreadyExistException {

        Beer beer = new Beer();
        beer.setBeerName("name");

        BeerStyle style = new BeerStyle();
        style.setBeerStyle("Brown");

        Country country = new Country();
        country.setName("UNITED KINGDOM");

        Mockito.when(beerRepository.findBeerByBeerName("name")).thenReturn(null);
        Mockito.when(styleRepository.findBeerStyleByBeerStyle("Brown")).thenReturn(style);
        Mockito.when(countryRepository.findCountriesByName("UNITED KINGDOM")).thenReturn(country);
        beer.setBeerStyle(style);
        beer.setCountry(country);

        beerService.createNewBeer(beer);

        Mockito.verify(beerRepository, Mockito.times(1)).findBeerByBeerName("name");
        Mockito.verify(styleRepository, Mockito.times(1)).findBeerStyleByBeerStyle("Brown");
        Mockito.verify(countryRepository, Mockito.times(1)).findCountriesByName("UNITED KINGDOM");
        Assert.assertEquals("UNITED KINGDOM", beer.getCountry().getName());
        Assert.assertEquals("Brown", beer.getBeerStyle().getBeerStyle());
    }

    @Test
    public void GetBeerByTag_ShouldReturnOnlyBeersAddedToTag() {
        Set<Tag> tags = new HashSet<>();
        Tag tag = new Tag();
        tags.add(tag);

        List<Beer>beers = new ArrayList<>();
        beers.add(new Beer());
        beers.add(new Beer());
        beers.add(new Beer());
        beers.get(0).setTags(tags);
        beers.get(2).setTags(tags);
        Mockito.when(tagRepository.findTagByTagName("yummy")).thenReturn(tag);
        Mockito.when(beerService.getAllBeers()).thenReturn(beers);

        List<Beer>result;
        result = beerService.getBeersByTags("yummy");

        Assert.assertEquals(2, result.size());
        Mockito.verify(tagRepository, Mockito.times(1)).findTagByTagName("yummy");
    }

    @Test
    public void GetBeersByStyle_ShouldReturnOnlyBeersWithThisStyle(){
        BeerStyle style = new BeerStyle("Brown");
        style.setId(1);
        BeerStyle style2 = new BeerStyle("Pale");
        style.setId(2);

        List<Beer>beers = new ArrayList<>();
        beers.add(new Beer());
        beers.add(new Beer());
        beers.add(new Beer());
        beers.get(0).setId(1);
        beers.get(1).setId(2);
        beers.get(2).setId(3);
        beers.get(0).setBeerStyle(style);
        beers.get(2).setBeerStyle(style);
        beers.get(1).setBeerStyle(style2);

        Mockito.when(styleRepository.findBeerStyleByBeerStyle("Brown")).thenReturn(style);
        Mockito.when(beerService.getAllBeers()).thenReturn(beers);

        List<Beer>result;
        result = beerService.getBeersByStyle("Brown");

        Assert.assertEquals(2, result.size());
        Mockito.verify(styleRepository, Mockito.times(1)).findBeerStyleByBeerStyle("Brown");
    }

    @Test
    public void GetBeersByCountry_ShouldReturnOnlyBeersWithThisCountry(){
        Country country = new Country("GERMANY");
        country.setId(1);
        Country country2 = new Country("BULGARIA");
        country2.setId(2);

        List<Beer>beers = new ArrayList<>();
        beers.add(new Beer());
        beers.add(new Beer());
        beers.add(new Beer());
        beers.get(0).setId(1);
        beers.get(1).setId(2);
        beers.get(2).setId(3);
        beers.get(0).setCountry(country);
        beers.get(2).setCountry(country);
        beers.get(1).setCountry(country2);

        Mockito.when(countryRepository.findCountriesByName("GERMANY")).thenReturn(country);
        Mockito.when(beerService.getAllBeers()).thenReturn(beers);

        List<Beer>result;
        result = beerService.getBeersByCountry("GERMANY");

        Assert.assertEquals(2, result.size());
        Mockito.verify(countryRepository, Mockito.times(1)).findCountriesByName("GERMANY");
    }

    @Test
    public void deleteBeer_ShouldReturnIsDeletedBeerTrue(){
        Beer beer = new Beer();
        beer.setId(1);
        Mockito.when(beerRepository.findBeerById(1))
                .thenReturn(beer);

        Beer testBeer = beerRepository.findBeerById(1);
        beerService.deleteBeer(1);

        Assert.assertTrue(testBeer.isDeleted());
    }

    @Test
    public void markAsWish_ShouldAddBeerInUserWishList() throws BeerAlreadyMarkedException {
        Beer beer = new Beer();
        beer.setId(1);
        beer.setBeerName("name");
        User user = new User("FirstName1", "LastName1", "e-mail","user1",true);
        user.setId(1);
        Beer beer2 = new Beer();
        user.setWantedToDrinks(new HashSet<>());
        user.getWantedToDrinks().add(beer2);
        beer.setWantedToDrinks(new HashSet<>());

        Mockito.when(beerRepository.getBeersById(1)).thenReturn(beer);
        Mockito.when(userRepository.getUserById(1)).thenReturn(user);
        Mockito.when(userRepository.save(user)).thenReturn(user);

        beerService.markAsWish(beer.getId(),user.getId());

        Mockito.verify(beerRepository, Mockito.times(1)).getBeersById(1);
        Mockito.verify(userRepository, Mockito.times(1)).getUserById(1);
        Mockito.verify(userRepository, Mockito.times(1)).save(user);
    }

    @Test
    public void markAsDrunk_ShouldAddBeerInUserDrunkBeers() throws BeerAlreadyMarkedException {
        Beer beer = new Beer();
        beer.setId(1);
        beer.setBeerName("name");
        User user = new User("FirstName1", "LastName1", "e-mail","user1",true);
        user.setId(1);
        Beer beer2 = new Beer();
        user.setDrunkedBeers(new HashSet<>());
        user.getDrunkedBeers().add(beer2);
        beer.setDrunkedBeers(new HashSet<>());

        Mockito.when(beerRepository.getBeersById(1)).thenReturn(beer);
        Mockito.when(userRepository.getUserById(1)).thenReturn(user);
        Mockito.when(userRepository.save(user)).thenReturn(user);

        beerService.markAsDrunk(beer.getId(),user.getId());

        Mockito.verify(beerRepository, Mockito.times(1)).getBeersById(1);
        Mockito.verify(userRepository, Mockito.times(1)).getUserById(1);
        Mockito.verify(userRepository, Mockito.times(1)).save(user);
    }

    @Test
    public void RateBeer_SuoldAddRatingToSelectedBeer() throws BeerAlreadyVotedException {
        Beer beer = new Beer();
        beer.setId(1);
        User user = new User();
        user.setId(1);
        Rating rate = new Rating();
        rate.setBeerRating(5);

        Mockito.when(beerRepository.findBeerById(1)).thenReturn(beer);
        Mockito.when(userRepository.findUserById(1)).thenReturn(user);
        Mockito.when(ratingRepository.findRatingByBeer_IdAndUser_Id(1,1)).thenReturn(null);

        beerService.rateBeer(1,1,rate.getBeerRating());

        Mockito.verify(beerRepository, Mockito.times(1)).findBeerById(1);
        Mockito.verify(userRepository, Mockito.times(1)).findUserById(1);
        Mockito.verify(ratingRepository, Mockito.times(1)).findRatingByBeer_IdAndUser_Id(1,1);
    }

    @Test
    public void AddBeerToTag_ShouldAddBeerToTagIfTagDoesNotExist() throws BeerAlreadyAddedToTagException {
        Beer beer = new Beer();
        beer.setId(1);

        Mockito.when(beerRepository.findBeerById(1)).thenReturn(beer);
        Mockito.when(tagRepository.findTagByTagName("nice")).thenReturn(null);

        Tag tag = new Tag();
        tag.setTagName("nice");

        beerService.addBeerToTag(1, "nice");

        Mockito.verify(beerRepository, Mockito.times(1)).findBeerById(1);
        Mockito.verify(tagRepository, Mockito.times(1)).findTagByTagName("nice");
    }

    @Test
    public void AddBeerToTag_ShouldAddBeerToTagIfTagExist() throws BeerAlreadyAddedToTagException {
        Beer beer = new Beer();
        beer.setId(1);
        Tag existingTag = new Tag();
        existingTag.setTagName("nice");

        Mockito.when(beerRepository.findBeerById(1)).thenReturn(beer);
        Mockito.when(tagRepository.findTagByTagName("nice")).thenReturn(existingTag);

        beerService.addBeerToTag(1, "nice");

        Mockito.verify(beerRepository, Mockito.times(1)).findBeerById(1);
        Mockito.verify(tagRepository, Mockito.times(1)).findTagByTagName("nice");
    }

    @Test
    public void unmarkAsWish_ShouldRemoveBeerFromWantedList(){
        Beer beer = new Beer();
        beer.setId(1);
        beer.setBeerName("name");
        User user = new User("FirstName1", "LastName1", "e-mail","user1",true);
        user.setId(1);
        Beer beer2 = new Beer();
        user.setWantedToDrinks(new HashSet<>());
        user.getWantedToDrinks().add(beer2);
        beer.setWantedToDrinks(new HashSet<>());

        beerService.unmarkAsWish(beer.getId(),user.getId());

        Mockito.verify(beerRepository, Mockito.times(1)).unmarkWanted(1,1);
    }

    @Test
    public void unmarkAsDrunk_ShouldRemoveBeerFromDrunkList(){
        Beer beer = new Beer();
        beer.setId(1);
        beer.setBeerName("name");
        User user = new User("FirstName1", "LastName1", "e-mail","user1",true);
        user.setId(1);
        Beer beer2 = new Beer();
        user.setWantedToDrinks(new HashSet<>());
        user.getWantedToDrinks().add(beer2);
        beer.setWantedToDrinks(new HashSet<>());

        beerService.unmarkAsDrunk(beer.getId(),user.getId());

        Mockito.verify(beerRepository, Mockito.times(1)).unmarkDrunkBeers(1,1);
    }
}
