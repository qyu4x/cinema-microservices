package com.neko.moviescheduleservice.service;

import com.neko.moviescheduleservice.model.request.MovieScheduleRequest;
import com.neko.moviescheduleservice.model.response.MovieScheduleResponse;
import com.neko.moviescheduleservice.model.response.ScheduleResponse;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MovieScheduleService {

    List<MovieScheduleResponse> add(List<MovieScheduleRequest>  movieScheduleRequests);

    List<MovieScheduleResponse> findMovieScheduleByMovieId(String movieId);

}
