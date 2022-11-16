package com.neko.studioservice.service.impl;

import com.neko.studioservice.exception.DataNotFoundException;
import com.neko.studioservice.model.entity.MovieStudio;
import com.neko.studioservice.model.entity.Studio;
import com.neko.studioservice.model.request.MovieStudioRequest;
import com.neko.studioservice.model.response.MovieStudioResponse;
import com.neko.studioservice.repository.MovieStudioRepository;
import com.neko.studioservice.repository.StudioRepository;
import com.neko.studioservice.service.MovieStudioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class MovieStudioServiceImpl implements MovieStudioService {

    private final MovieStudioRepository movieStudioRepository;

    private final StudioRepository studioRepository;

    @Autowired
    public MovieStudioServiceImpl(MovieStudioRepository movieStudioRepository, StudioRepository studioRepository) {
        this.movieStudioRepository = movieStudioRepository;

        this.studioRepository = studioRepository;
    }

    @Override
    public List<MovieStudioResponse> add(List<MovieStudioRequest> movieStudioRequests) {
        List<MovieStudioResponse> movieStudioResponses = new ArrayList<>();
        movieStudioRequests.stream().forEach(movieStudioRequest -> {
           Studio studio = studioRepository.findById(movieStudioRequest.getStudioId())
                   .orElseThrow(() -> new DataNotFoundException(String.format("studio with id %s not found", movieStudioRequest.getStudioId())));
            MovieStudio movieStudio = MovieStudio.builder()
                    .id(UUID.randomUUID().toString())
                    .movieId(movieStudioRequest.getMovieId())
                    .studioId(studio)
                    .build();
            movieStudioRepository.save(movieStudio);

            MovieStudioResponse movieResponse = MovieStudioResponse.builder()
                    .id(movieStudio.getId())
                    .movieId(movieStudio.getMovieId())
                    .studio(studio)
                    .build();
            movieStudioResponses.add(movieResponse);
        });

        return movieStudioResponses;
    }

    @Override
    public List<MovieStudioResponse> findByMovieId(String movieId) {
        if (movieId == null) {
            throw new DataNotFoundException("movie id is null");
        }
        List<MovieStudio> movieStudioResponseRequests = movieStudioRepository.findMovieStudiosByMovieId(movieId);
        if (movieStudioResponseRequests.get(0) == null) {
            throw new DataNotFoundException("movie studio response is null");
        }
        List<MovieStudioResponse> movieStudioResponses = new ArrayList<>();
        movieStudioResponseRequests.stream().forEach(response -> {
            MovieStudioResponse movieStudioResponse = MovieStudioResponse.builder()
                    .id(response.getId())
                    .movieId(response.getMovieId())
                    .studio(response.getStudioId())
                    .build();
            movieStudioResponses.add(movieStudioResponse);
        });

        return movieStudioResponses;
    }
}
