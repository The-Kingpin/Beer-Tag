package com.beertag.repository;

import com.beertag.entities.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating, Integer> {

    Rating findRatingByBeer_IdAndUser_Id(int beer_id, int user_id);

}
