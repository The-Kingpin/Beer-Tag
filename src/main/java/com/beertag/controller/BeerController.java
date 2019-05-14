package com.beertag.controller;

import com.beertag.config.BeerAlreadyAddedToTagException;
import com.beertag.config.ItemAlreadyExistException;
import com.beertag.entities.Beer;
import com.beertag.entities.BeerStyle;
import com.beertag.entities.Country;
import com.beertag.repository.BeerRepository;
import com.beertag.repository.StyleRepository;
import com.beertag.service.BeerService;
import com.beertag.serviceImpl.BeerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/beers")
public class BeerController {

    private BeerService beerService;

    @Autowired
    public BeerController(BeerService beerService) {
        this.beerService = beerService;
    }

    @GetMapping
    public String getAllBeers(Model model) {

        List<Beer> beers = beerService.getAllBeers();

        model.addAttribute("beers", beers);
        //model.addAttribute("currentPage", page);

        List<Country> countries = beerService.findAllCountries();
        model.addAttribute("countries", countries);

        List<BeerStyle> styles = beerService.getAllstyles();
        model.addAttribute("styles", styles);

        return "beers";
    }

    //OK
    @GetMapping("/getBeerById")
    @Valid
    @ResponseBody
    public Beer getBeerById(int id) {

        return beerService.getBeerById(id);
    }

    @GetMapping("/beerDetails")
    public String beerDetails(int id, Model model) {
        Beer beer = beerService.getBeerById(id);

        List<Beer> beers;
        beers = new ArrayList<>();

        beers.add(beer);

        model.addAttribute("beer",beers);

        return  "beer";
    }


    //OK
    @GetMapping("/sortbyname")
    public String sortBeersByName(Model model) {

        List<Beer> beers = beerService.sortBeersByName();

        model.addAttribute("beers", beers);
        // model.addAttribute("currentPage", page);

        List<Country> countries = beerService.findAllCountries();
        model.addAttribute("countries", countries);

        List<BeerStyle> styles = beerService.getAllstyles();
        model.addAttribute("styles", styles);

        return "beers";

    }

    //OK
    @GetMapping("/sortbyABV")
    public String sortBeersByABV(Model model) {

        List<Beer> beers = beerService.sortBeersByABV();

        model.addAttribute("beers", beers);

        List<Country> countries = beerService.findAllCountries();
        model.addAttribute("countries", countries);

        List<BeerStyle> styles = beerService.getAllstyles();
        model.addAttribute("styles", styles);

        return "beers";
    }

    //OK
    @GetMapping("/sortbyrating")
    public String sortBeersByRating(Model model) {
        List<Beer> beers = beerService.sortBeersByRating();

        model.addAttribute("beers", beers);

        List<Country> countries = beerService.findAllCountries();
        model.addAttribute("countries", countries);

        List<BeerStyle> styles = beerService.getAllstyles();
        model.addAttribute("styles", styles);

        return "beers";
    }

    //OK
    @PostMapping("/createNew")
    public String createNewBeer(Beer beer) throws ItemAlreadyExistException {

        beerService.createNewBeer(beer);

        return "redirect:/beers";
    }

    //OK
    @PutMapping("/update/{id}")
    public String updateBeer(@Valid @RequestBody Beer beer, @PathVariable int id) {
        beerService.updateBeer(beer, id);

        return "redirect:/beers";
    }

    @PostMapping("/tagbeer/{beer_id}/{tag_name}")
    public void addBeerToTag(@PathVariable int beer_id, @PathVariable String tag_name) throws BeerAlreadyAddedToTagException {
        beerService.addBeerToTag(beer_id, tag_name);
    }

    @GetMapping("/filterbytag/{tag}")
    public List<Beer> getBeersByTag(@PathVariable String tag) {
        return beerService.getBeersByTags(tag);
    }

    //OK
    @GetMapping("/filterbystyle/{style}")
    public List<Beer> getBeerByStyle(@PathVariable String style) {
        return beerService.getBeersByStyle(style);
    }

    //OK
    @GetMapping("/filterbycountry/{country}")
    public List<Beer> getBeerByCountry(@PathVariable String country) {
        return beerService.getBeersByCountry(country);
    }

    //OK
    @GetMapping("/delete/{id}")
    public String deleteBeer(@PathVariable int id) {

        beerService.deleteBeer(id);

        return "redirect:/beers";
    }
}