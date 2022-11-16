package com.neko.moviescheduleservice.repository;

import com.neko.moviescheduleservice.model.entity.MovieSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieScheduleRepository extends JpaRepository<MovieSchedule, String> {

    @Query(
            value = "SELECT * FROM movie_schedule ms JOIN schedules s on s.id = ms.schedule_id WHERE ms.movie_id = ?1",
            nativeQuery = true
    )
    List<MovieSchedule> findMovieScheduleById(String movieId);
}
