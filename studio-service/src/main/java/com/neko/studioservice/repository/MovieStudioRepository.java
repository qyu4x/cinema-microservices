package com.neko.studioservice.repository;

import com.neko.studioservice.model.entity.MovieStudio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieStudioRepository extends JpaRepository<MovieStudio, String> {

    @Query(
            nativeQuery = true,
            value = "SELECT * FROM movie_studio ms INNER JOIN studios s on ms.studio_id = s.id WHERE ms.movie_id = ?1"
    )
    List<MovieStudio> findMovieStudiosByMovieId(String id);

}
