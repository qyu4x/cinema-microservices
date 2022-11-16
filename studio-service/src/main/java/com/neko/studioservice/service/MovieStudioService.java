package com.neko.studioservice.service;

import com.neko.studioservice.model.request.MovieStudioRequest;
import com.neko.studioservice.model.response.MovieStudioResponse;

import java.util.List;

public interface MovieStudioService {

    List<MovieStudioResponse> add(List<MovieStudioRequest> movieStudioRequests);

    List<MovieStudioResponse> findByMovieId(String movieId);

}
