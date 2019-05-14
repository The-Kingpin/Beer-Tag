package com.beertag.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.IOException;
import java.util.*;

@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String firstName;

    private String lastName;

    @Email
    private String email;

    @Lob
    private byte[] userPicture;

    private String username;

    private String password;

    private boolean isAccountNonExpired;

    private boolean isAccountNonLocked;

    private boolean isCredentialsNonExpired;

    private boolean isEnabled;

    public User() {
    }

    public User(String firstName, String lastName, String email, String username, boolean isEnabled) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.isEnabled = isEnabled;

    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinTable(name = "drunked_beers",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "beer_id"))
    private Set<Beer> drunkedBeers;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinTable(name = "wanted_to_drink",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "beer_id"))
    private Set<Beer> wantedToDrinks;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> authorities;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL
    )
    private List<Rating>ratings = new ArrayList<>();

    @Override
    public Set<Role> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        isAccountNonExpired = accountNonExpired;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        isAccountNonLocked = accountNonLocked;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        isCredentialsNonExpired = credentialsNonExpired;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public void setAuthorities(Set<Role> authorities) {
        this.authorities = authorities;
    }

    public Set<Beer> getDrunkedBeers() {
        return drunkedBeers;
    }

    public void setDrunkedBeers(Set<Beer> drunkedBeers) {
        this.drunkedBeers = drunkedBeers;
    }

    public Set<Beer> getWantedToDrinks() {
        return wantedToDrinks;
    }

    public void setWantedToDrinks(Set<Beer> wantedToDrinks) {
        this.wantedToDrinks = wantedToDrinks;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getUserPicture() {
        return userPicture;
    }

    public String printUserPicture(){
        return new String(Base64.encodeBase64(this.userPicture));
    }

    public void setUserPicture(MultipartFile userPicture) throws IOException {
        this.userPicture = userPicture.getBytes();
    }
    public void updateUeserPicture(byte[] newPicture){
        this.userPicture = newPicture;
    }
}
