package com.neko.moviescheduleservice.repository;

import com.neko.moviescheduleservice.model.entity.MovieSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieScheduleRepository extends JpaRepository<MovieSchedule, String> {

}
