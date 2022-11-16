package com.neko.moviedataservice.controller;

import com.neko.moviedataservice.exception.DataNotFoundException;
import com.neko.moviedataservice.model.request.MovieRequest;
import com.neko.moviedataservice.model.response.MovieDetailResponse;
import com.neko.moviedataservice.model.response.MovieResponse;
import com.neko.moviedataservice.model.response.WebResponse;
import com.neko.moviedataservice.service.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/movie")
public class MovieController {

    private final static Logger log = LoggerFactory.getLogger(MovieController.class);
    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping(value = "/add")
    @ResponseBody
    public ResponseEntity<WebResponse<MovieResponse>> addNewMovie(@Valid @RequestBody MovieRequest movieRequest) {
        try {
            log.info("calling controller addNewMovie");
            final MovieResponse movieResponse =  movieService.add(movieRequest);
            log.info("successfully added movie with title {} ", movieRequest.getTitle());
            WebResponse<MovieResponse> webResponse = new WebResponse<>(
                    HttpStatus.OK.value(),
                    HttpStatus.OK.getReasonPhrase(),
                    movieResponse
            );
            return new ResponseEntity<>(webResponse, HttpStatus.OK);
        }catch (RuntimeException exception) {
            log.info("failed added movie with title {} ", movieRequest.getTitle());
            WebResponse<MovieResponse> webResponse = new WebResponse<>(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                    null
            );
            return new ResponseEntity<>(webResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/update/{id}")
    @ResponseBody
    public ResponseEntity<WebResponse<MovieResponse>> updateMovie(@Valid @RequestBody MovieRequest movieRequest, @PathVariable("id") String id) {
        try {
            log.info("calling controller updateMovie");
            final MovieResponse movieResponse =  movieService.update(movieRequest, id);
            log.info("successfully added movie with title {} ", movieRequest.getTitle());
            WebResponse<MovieResponse> webResponse = new WebResponse<>(
                    HttpStatus.OK.value(),
                    HttpStatus.OK.getReasonPhrase(),
                    movieResponse
            );
            return new ResponseEntity<>(webResponse, HttpStatus.OK);
        }catch (DataNotFoundException exception) {
            log.info("failed added movie with title {} ", movieRequest.getTitle());
            WebResponse<MovieResponse> webResponse = new WebResponse<>(
                    HttpStatus.NOT_FOUND.value(),
                    HttpStatus.NOT_FOUND.getReasonPhrase(),
                    null
            );
            return new ResponseEntity<>(webResponse, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/delete/{id}")
    @ResponseBody
    public ResponseEntity<WebResponse<Boolean>> deleteMovie(@PathVariable("id") String id){
        try {
            log.info("calling controller deleteMovie");
            final Boolean status = movieService.delete(id);
            log.info("successfully delete movie with id {} ", id);
            WebResponse<Boolean> webResponse = new WebResponse<>(
                    HttpStatus.OK.value(),
                    HttpStatus.OK.getReasonPhrase(),
                    status
            );
            return new ResponseEntity<>(webResponse, HttpStatus.OK);

        } catch (DataNotFoundException exception) {
            log.info("failed delete movie with id {} ", id);
            WebResponse<Boolean> webResponse = new WebResponse<>(
                    HttpStatus.NOT_FOUND.value(),
                    HttpStatus.NOT_FOUND.getReasonPhrase(),
                    false
            );
            return new ResponseEntity<>(webResponse, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/is-showing")
    @ResponseBody
    public ResponseEntity<WebResponse<List<MovieResponse>>> getIsShowing(){
        try {
            log.info("calling controller getIsShowing");
            final List<MovieResponse> movieResponses = movieService.isShowing();
            log.info("successfully get all movie currently showing");
            WebResponse<List<MovieResponse>> webResponse = new WebResponse<>(
                    HttpStatus.OK.value(),
                    HttpStatus.OK.getReasonPhrase(),
                    movieResponses
            );
            return new ResponseEntity<>(webResponse, HttpStatus.OK);

        } catch (DataNotFoundException exception) {
            log.info("failed get movie movie currently showing");
            WebResponse<List<MovieResponse>> webResponse = new WebResponse<>(
                    HttpStatus.NOT_FOUND.value(),
                    HttpStatus.NOT_FOUND.getReasonPhrase(),
                    new ArrayList<>()
            );
            return new ResponseEntity<>(webResponse, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/schedule/{id}")
    @ResponseBody
    public ResponseEntity<WebResponse<MovieDetailResponse>> findScheduleByMovieId(@PathVariable("id") String id){
        try {
            log.info("calling controller get schedule by movie id");
            final MovieDetailResponse movieResponses = movieService.findScheduleAndStudioByMovieId(id);
            log.info("successfully get schedule with movie id {} ", id);
            WebResponse<MovieDetailResponse> webResponse = new WebResponse<>(
                    HttpStatus.OK.value(),
                    HttpStatus.OK.getReasonPhrase(),
                    movieResponses
            );
            return new ResponseEntity<>(webResponse, HttpStatus.OK);

        } catch (DataNotFoundException exception) {
            log.error("failed get movie schedule with id {} ", id);
            log.error("error {} ", exception.getMessage());
            WebResponse<MovieDetailResponse> webResponse = new WebResponse<>(
                    HttpStatus.NOT_FOUND.value(),
                    HttpStatus.NOT_FOUND.getReasonPhrase(),
                    null
            );
            return new ResponseEntity<>(webResponse, HttpStatus.NOT_FOUND);
        }
    }
}
