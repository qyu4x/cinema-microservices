package com.neko.moviescheduleservice.service;

import com.neko.moviescheduleservice.model.request.MovieScheduleRequest;
import com.neko.moviescheduleservice.model.response.MovieScheduleResponse;

import java.util.List;

public interface MovieScheduleService {

    List<MovieScheduleResponse> add(List<MovieScheduleRequest>  movieScheduleRequests);

}
