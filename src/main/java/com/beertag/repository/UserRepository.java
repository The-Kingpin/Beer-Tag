package com.beertag.repository;

import com.beertag.entities.Beer;
import com.beertag.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findOneByUsername(String username);

    User getUserById(int id);

    User findUserById(int id);

    List<User> findAll();

}
