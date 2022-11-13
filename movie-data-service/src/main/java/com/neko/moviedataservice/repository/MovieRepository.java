package com.neko.moviedataservice.repository;

import com.neko.moviedataservice.model.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, String> {

    @Query(
            value = "SELECT * FROM films as f WHERE show_status = ?1 AND deleted=false",
            nativeQuery = true
    )
    List<Movie> findAllIfShown(Boolean status);

}
