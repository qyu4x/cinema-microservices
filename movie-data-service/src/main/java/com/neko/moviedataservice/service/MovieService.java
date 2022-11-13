package com.neko.moviedataservice.service;

import com.neko.moviedataservice.model.request.MovieRequest;
import com.neko.moviedataservice.model.response.MovieResponse;

import java.util.List;

public interface MovieService {

    MovieResponse add(MovieRequest movieRequest);

    MovieResponse update(MovieRequest movieRequest, String id);

    Boolean delete(String id);

    List<MovieResponse> isShowing();

}
