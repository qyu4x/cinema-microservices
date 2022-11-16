package com.neko.moviescheduleservice.repository;

import com.neko.moviescheduleservice.model.entity.MovieSchedule;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class MovieScheduleRepositoryTest {

    @Autowired
    private MovieScheduleRepository movieScheduleRepository;

    @Test
    void testGetByMovieScheduleById() {
        List<MovieSchedule> responses = movieScheduleRepository.findMovieScheduleById("test doang movie id");
        responses.stream().forEach(response -> {
            log.info("id movie {} is {} ", response.getMovieId(),response.getScheduleId().getId());
        });
    }
}