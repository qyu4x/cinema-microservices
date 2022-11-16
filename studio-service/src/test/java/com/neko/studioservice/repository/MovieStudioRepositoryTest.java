package com.neko.studioservice.repository;

import com.neko.studioservice.model.entity.MovieStudio;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
@Slf4j
class MovieStudioRepositoryTest {

    @Autowired
    private MovieStudioRepository movieStudioRepository;

    @Test
    void testFindMovieStudioByMovieId() {

        List<MovieStudio> movieStudioResponse = movieStudioRepository.findMovieStudiosByMovieId("test movie id");
        movieStudioResponse.stream().forEach(movieStudio -> {
            log.info("this is name for movie id {} -> {}" ,movieStudio.getMovieId(), movieStudio.getStudioId().getName());
        });

    }
}