package com.beertag.service;

import com.beertag.entities.Beer;
import com.beertag.entities.User;
import com.beertag.model.RegistrationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    void register(RegistrationModel registrationModel) throws Exception;

    @PreAuthorize("hasRole('ADMIN')")
    void delete();

    User getUserByUsername(String user);

    List<User>getAllUsers();

    User getUserById(int id);

    void deleteUser(int id);

    User updateUser(User user, int id);
}