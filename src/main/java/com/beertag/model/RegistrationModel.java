package com.beertag.model;

import com.beertag.annotations.IsPasswordsMatching;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Lob;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.io.IOException;

@IsPasswordsMatching
public class RegistrationModel {

    private String firstName;

    private String lastName;

    @Email
    private String email;

    @Lob
    private byte[] userPicture;

    @Size(min = 5, message = "Username too short")
    private String username;

    @Size(min = 5, message = "Password too short")
    private String password;

    private String confirmPassword;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
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

    public void setUserPicture(byte[] userPicture) {
        this.userPicture = userPicture;
    }
}
