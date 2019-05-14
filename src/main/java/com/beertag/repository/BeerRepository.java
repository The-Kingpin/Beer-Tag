package com.beertag.repository;

import com.beertag.entities.Beer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface BeerRepository extends JpaRepository<Beer, Integer> {

    Beer getBeersById(int id);

    Beer findBeerByBeerName(String name);

    Beer findBeerById(int id);

    Page<Beer> findAll(Pageable pageable);

    @Modifying
    @Query(value = "delete from wanted_to_drink\n" +
            "where user_id = :userId and beer_id = :beerId", nativeQuery = true)
    void unmarkWanted(@Param(value = "userId") int userId, @Param(value = "beerId") int beerId);


    @Modifying
    @Query(value = "delete from drunked_beers\n" +
            "where user_id = :userId and beer_id = :beerId", nativeQuery = true)
    void unmarkDrunkBeers(@Param(value = "userId") int userId, @Param(value = "beerId") int beerId);
}
