package com.beertag.controller;

import com.beertag.config.*;
import com.beertag.entities.User;
import com.beertag.model.RegistrationModel;
import com.beertag.service.BeerService;
import com.beertag.service.UserService;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
public class UserController {

    private UserService userService;
    private BeerService beerService;

    @Autowired
    public UserController(UserService userService, BeerService beerService) {
        this.userService = userService;
        this.beerService = beerService;
    }

    @GetMapping("/register")
    public String getRegisterPage(@ModelAttribute RegistrationModel registrationModel){
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute RegistrationModel registrationModel, BindingResult bindingResult) throws Exception {
        if(bindingResult.hasErrors()){
            return "register";
        }

        this.userService.register(registrationModel);

        return "redirect:/";
    }

    @PostMapping("/login")
    public String posLoginPage(@RequestParam(required = false) String error, Model model){
        if(error != null){
            model.addAttribute("error", Errors.INVALID_CREDENTIALS);
        }

        return "home";
    }

    @GetMapping("/login")
    public String getLoginPage(@RequestParam(required = false) String error, Model model){
        if(error != null){
            model.addAttribute("error", Errors.INVALID_CREDENTIALS);
        }

        return "login";
    }


    @GetMapping("/user")
    public String getUserPage(Principal principal){
        System.out.println(principal.getName());
        //this.userService.delete();
        return "user";
    }

    @GetMapping("/admin")
    public String getAdminPage(){

        return "admin";
    }

    @GetMapping("/unauthorized")
    public String getUnauthorizedPage(){

        return "unauthorized";
    }

    @GetMapping("/users")
    public String getAllUsers(Model model){
        List<User> users = userService.getAllUsers();

        model.addAttribute("users", users);

        return "users";
    }

    //OK
    //@Transactional
    @PostMapping("/user/addtowishlist")
    public void markAsWish(@RequestParam(value = "user_id") int user_id,
                           @RequestParam(value = "beer_id") int beer_id) throws DatabaseItemNotFoundException, BeerAlreadyMarkedException {
        try {
            beerService.markAsWish(user_id, beer_id);
        } catch (HibernateException he) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Failed to access database.");
        } catch (DatabaseItemNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    e.getMessage()
            );
        }
    }

    //OK
    @DeleteMapping("/user/addtowishlist")
    public void unmarkAsWish(@RequestParam(value = "user_id") int user_id,
                             @RequestParam(value = "beer_id") int beer_id) throws DatabaseItemNotFoundException, BeerIsNotMarkedAsWishException {
        try {
            beerService.unmarkAsWish(user_id, beer_id);
        } catch (HibernateException he) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Failed to access database.");
        } catch (DatabaseItemNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    e.getMessage()
            );
        }
    }

    //OK
    //@Transactional
    @PostMapping("/user/markasdrunk")
    public void markAsDrunk(@RequestParam(value = "user_id") int user_id,
                           @RequestParam(value = "beer_id") int beer_id) throws DatabaseItemNotFoundException, BeerAlreadyMarkedException {
        try {
            beerService.markAsDrunk(user_id, beer_id);
        } catch (HibernateException he) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Failed to access database.");
        } catch (DatabaseItemNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    e.getMessage()
            );
        }
    }

    //OK
    @DeleteMapping("/user/markasdrunk")
    public void unmarkAsDrunk(@RequestParam(value = "user_id") int user_id,
                              @RequestParam(value = "beer_id") int beer_id) throws DatabaseItemNotFoundException {
        try {
            beerService.unmarkAsDrunk(user_id, beer_id);
        } catch (HibernateException he) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Failed to access database.");
        } catch (DatabaseItemNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    e.getMessage()
            );
        }
    }

    //OK
    @PostMapping("/user/ratebeer/{beer_id}/{user_id}/{rating}")
    public void rateBeer(@PathVariable int beer_id, @PathVariable int user_id, @PathVariable int rating) throws BeerAlreadyVotedException {
        beerService.rateBeer(beer_id, user_id, rating);
    }

    @GetMapping("/user/{id}")
    public User getUserBuId (@PathVariable int id){
        return userService.getUserById(id);
    }


    //OK
    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable int id){
        userService.deleteUser(id);
    }

    //OK
    @PutMapping("/user/{id}")
    public void updateUser(@Valid @RequestBody User user, @PathVariable int id){
        userService.updateUser(user, id);
    }
}
