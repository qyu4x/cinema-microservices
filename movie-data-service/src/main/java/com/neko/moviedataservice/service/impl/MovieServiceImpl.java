package com.neko.moviedataservice.service.impl;

import com.neko.moviedataservice.client.ScheduleExternalClient;
import com.neko.moviedataservice.client.StudioExternalClient;
import com.neko.moviedataservice.exception.DataNotFoundException;
import com.neko.moviedataservice.model.entity.Movie;
import com.neko.moviedataservice.model.request.MovieRequest;
import com.neko.moviedataservice.model.response.*;
import com.neko.moviedataservice.repository.MovieRepository;
import com.neko.moviedataservice.service.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    private final StudioExternalClient studioExternalClient;

    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository, ScheduleExternalClient scheduleExternalClient, StudioExternalClient studioExternalClient) {
        this.movieRepository = movieRepository;
        this.scheduleExternalClient = scheduleExternalClient;
        this.studioExternalClient = studioExternalClient;
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
        WebMovieScheduleResponse webMovieScheduleResponse = scheduleExternalClient.postScheduleForMovies(movieRequest.getScheduleRequests());
        log.info("successfully save movie and schedule data");

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
                .scheduleResponses(webMovieScheduleResponse.getData())
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

    @Override
    public MovieDetailResponse findScheduleAndStudioByMovieId(String movieId) {
        if (movieId == null) {
            throw new DataNotFoundException("data movie id is null");
        }
        Optional<Movie> movieResponse = movieRepository.findById(movieId);
        if (movieResponse.isEmpty()) {
            throw new DataNotFoundException("data movie response  is null");
        }
        WebMovieScheduleResponse scheduleResponses = scheduleExternalClient.findScheduleMovieByMovieId(movieId);
        WebMovieStudioResponse studioResponses = studioExternalClient.findMovieStudioByMovieId(movieId);
        if (!scheduleResponses.getCode().equals(HttpStatus.OK.value()) || scheduleResponses.getCode().equals(HttpStatus.NOT_FOUND.value())) {
            throw new DataNotFoundException("failed fetch data schedule");
        }

        if (!studioResponses.getCode().equals(HttpStatus.OK.value()) || studioResponses.getCode().equals(HttpStatus.NOT_FOUND.value())) {
            throw new DataNotFoundException("failed fetch data movie studio");
        }

        List<MovieScheduleResponse> movieScheduleResponses = new ArrayList<>();
        scheduleResponses.getData().stream().forEach(scheduleResponse -> {
            MovieScheduleResponse movieScheduleResponse = MovieScheduleResponse.builder()
                    .id(scheduleResponse.getMovieId())
                    .movieId(scheduleResponse.getMovieId())
                    .schedule(scheduleResponse.getSchedule())
                    .build();
            movieScheduleResponses.add(movieScheduleResponse);
        });

        List<MovieStudioResponse> movieStudioResponses = new ArrayList<>();
        studioResponses.getData().stream().forEach(studioResponse -> {
            MovieStudioResponse movieStudioResponse = MovieStudioResponse.builder()
                    .id(studioResponse.getId())
                    .movieId(studioResponse.getMovieId())
                    .studio(studioResponse.getStudio())
                    .build();
            movieStudioResponses.add(movieStudioResponse);
        });

        Movie movie = movieResponse.get();
        return MovieDetailResponse.builder()
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
                .scheduleResponses(movieScheduleResponses)
                .movieStudioResponses(movieStudioResponses)
                .build();
    }

}
