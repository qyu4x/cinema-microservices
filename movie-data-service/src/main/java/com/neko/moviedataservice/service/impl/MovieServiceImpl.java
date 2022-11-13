package com.neko.moviedataservice.service.impl;

import com.neko.moviedataservice.client.ScheduleExternalClient;
import com.neko.moviedataservice.exception.DataNotFoundException;
import com.neko.moviedataservice.model.entity.Movie;
import com.neko.moviedataservice.model.request.MovieRequest;
import com.neko.moviedataservice.model.response.MovieResponse;
import com.neko.moviedataservice.model.response.ScheduleResponse;
import com.neko.moviedataservice.model.response.WebScheduleResponse;
import com.neko.moviedataservice.repository.MovieRepository;
import com.neko.moviedataservice.service.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MovieServiceImpl implements MovieService {

    private final static Logger log = LoggerFactory.getLogger(MovieServiceImpl.class);

    private final MovieRepository movieRepository;
    private final ScheduleExternalClient scheduleExternalClient;

    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository, ScheduleExternalClient scheduleExternalClient) {
        this.movieRepository = movieRepository;
        this.scheduleExternalClient = scheduleExternalClient;
    }

    @Override
    public MovieResponse add(MovieRequest movieRequest) {
        Movie movie = Movie.builder()
                .id(UUID.randomUUID().toString())
                .title(movieRequest.getTitle())
                .showStatus(movieRequest.getShowStatus())
                .duration(movieRequest.getDuration())
                .startDate(movieRequest.getStartDate())
                .endDate(movieRequest.getEndDate())
                .genre(movieRequest.getGenre())
                .description(movieRequest.getDescription())
                .country(movieRequest.getCountry())
                .language(movieRequest.getLanguage())
                .build();

        movieRequest.getScheduleRequests().stream().forEach(scheduleRequest -> {
            scheduleRequest.setMovieId(movie.getId());
        });

        log.info("do save movie data");
        movieRepository.save(movie);
        WebScheduleResponse webScheduleResponse = scheduleExternalClient.postScheduleForMovies(movieRequest.getScheduleRequests());
        log.info("successfully save movie and schedule data");
        List<ScheduleResponse> scheduleResponses = new ArrayList<>();
        webScheduleResponse.getData().stream().forEach(response -> {
            scheduleResponses.add(new ScheduleResponse(
                    response.getId(),
                    response.getMovieId(),
                    response.getShowDate(),
                    response.getStartTime(),
                    response.getEndTime(),
                    response.getPrice()
            ));
        });

        return MovieResponse.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .showStatus(movie.getShowStatus())
                .duration(movie.getDuration())
                .startDate(movie.getStartDate())
                .createdAt(LocalDateTime.now())
                .endDate(movie.getEndDate())
                .genre(movie.getGenre())
                .description(movie.getDescription())
                .country(movie.getCountry())
                .language(movie.getLanguage())
                .scheduleResponses(scheduleResponses)
                .build();

    }

    @Override
    public MovieResponse update(MovieRequest movieRequest, String id) {
        Optional<Movie> movieToUpdate = movieRepository.findById(id);
        if (movieToUpdate.isEmpty()) {
            log.info("movies with id {} not found", id);
            throw new DataNotFoundException("data with id " + id + "not found");
        }
        Movie movieToUpdateTitle = movieToUpdate.get();
        movieToUpdateTitle.setTitle(movieRequest.getTitle());
        movieToUpdateTitle.setUpdatedAt(LocalDateTime.now());

        log.info("do update title movie with id {} ", id);
        movieRepository.save(movieToUpdateTitle);
        log.info("successfully update title movie data");

        return MovieResponse.builder()
                .id(movieToUpdateTitle.getId())
                .title(movieToUpdateTitle.getTitle())
                .showStatus(movieToUpdateTitle.getShowStatus())
                .duration(movieToUpdateTitle.getDuration())
                .startDate(movieToUpdateTitle.getStartDate())
                .endDate(movieToUpdateTitle.getEndDate())
                .genre(movieToUpdateTitle.getGenre())
                .description(movieToUpdateTitle.getDescription())
                .country(movieToUpdateTitle.getCountry())
                .language(movieToUpdateTitle.getLanguage())
                .createdAt(movieToUpdateTitle.getCreatedAt())
                .updatedAt(movieToUpdateTitle.getUpdatedAt())
                .build();
    }

    @Override
    public Boolean delete(String id) {
        if(!movieRepository.existsById(id)) {
            log.error("failed to delete movie by id {} ", id);
            throw new DataNotFoundException("data with id " + id + "not found");

        }
        movieRepository.deleteById(id);
        log.info("successfully delete movie with id {} ", id);
        return true;
    }

    @Override
    public List<MovieResponse> isShowing() {
        log.info("do take all the movies currently showing");
        List<Movie> movies =  movieRepository.findAllIfShown(true);
        List<MovieResponse> movieResponses = new ArrayList<>();
        movies.stream()
                .forEach(movie -> {
                    MovieResponse movieResponse  = MovieResponse.builder()
                            .id(movie.getId())
                            .title(movie.getTitle())
                            .country(movie.getCountry())
                            .language(movie.getLanguage())
                            .genre(movie.getGenre())
                            .duration(movie.getDuration())
                            .startDate(movie.getStartDate())
                            .endDate(movie.getEndDate())
                            .showStatus(movie.getShowStatus())
                            .description(movie.getDescription())
                            .createdAt(movie.getCreatedAt())
                            .updatedAt(movie.getUpdatedAt())
                            .build();
                    movieResponses.add(movieResponse);
                });
        log.info("successful take all movies currently showing");
        return movieResponses;
    }
}
