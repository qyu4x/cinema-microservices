package com.neko.moviescheduleservice.service.impl;

import com.neko.moviescheduleservice.exception.DataNotFoundException;
import com.neko.moviescheduleservice.model.entity.MovieSchedule;
import com.neko.moviescheduleservice.model.entity.Schedule;
import com.neko.moviescheduleservice.model.request.MovieScheduleRequest;
import com.neko.moviescheduleservice.model.response.MovieScheduleResponse;
import com.neko.moviescheduleservice.repository.MovieScheduleRepository;
import com.neko.moviescheduleservice.repository.ScheduleRepository;
import com.neko.moviescheduleservice.service.MovieScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class MovieScheduleServiceImpl implements MovieScheduleService {

    private final MovieScheduleRepository movieScheduleRepository;

    private final ScheduleRepository scheduleRepository;

    @Autowired
    public MovieScheduleServiceImpl(MovieScheduleRepository movieScheduleRepository, ScheduleRepository scheduleRepository) {
        this.movieScheduleRepository = movieScheduleRepository;
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public List<MovieScheduleResponse> add(List<MovieScheduleRequest> movieScheduleRequests) {
        List<MovieScheduleResponse> movieScheduleResponses = new ArrayList<>();
        movieScheduleRequests.stream().forEach(movieRequest -> {
                    Schedule schedule = scheduleRepository.findById(movieRequest.getScheduleId())
                            .orElseThrow(() -> new DataNotFoundException(String.format("scheule with id %s not found", movieRequest.getScheduleId())));
                    MovieSchedule movieSchedule = MovieSchedule.builder()
                            .id(UUID.randomUUID().toString())
                            .movieId(movieRequest.getMovieId())
                            .scheduleId(schedule)
                            .build();
                    movieScheduleRepository.save(movieSchedule);

                    MovieScheduleResponse movieScheduleResponse = MovieScheduleResponse.builder()
                            .movieId(movieSchedule.getMovieId())
                            .schedule(schedule)
                            .build();

                    movieScheduleResponses.add(movieScheduleResponse);
                }
        );
        return movieScheduleResponses;
    }
}
