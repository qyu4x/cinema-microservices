package com.neko.studioservice.controller;

import com.neko.studioservice.exception.DataNotFoundException;
import com.neko.studioservice.model.request.MovieStudioRequest;
import com.neko.studioservice.model.request.StudioRequest;
import com.neko.studioservice.model.response.MovieStudioResponse;
import com.neko.studioservice.model.response.StudioResponse;
import com.neko.studioservice.model.response.WebResponse;
import com.neko.studioservice.service.MovieStudioService;
import com.neko.studioservice.service.StudioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/studio")
public class StudioController {

    private final static Logger log = LoggerFactory.getLogger(StudioController.class);

    private final StudioService studioService;

    private final MovieStudioService movieStudioService;

    @Autowired
    public StudioController(StudioService studioService, MovieStudioService movieStudioService) {
        this.studioService = studioService;
        this.movieStudioService = movieStudioService;
    }

    @PostMapping("/save")
    @ResponseBody
    public ResponseEntity<WebResponse<StudioResponse>> save(@RequestBody StudioRequest studioRequest) {
        log.info("calling controller save - studio service");
        try {
            log.info("do save studio data");
            StudioResponse studioRepsonse = studioService.save(studioRequest);
            WebResponse<StudioResponse> webResponse = new WebResponse<>(
                    HttpStatus.OK.value(),
                    HttpStatus.OK.getReasonPhrase(),
                    studioRepsonse
            );
            log.info("successfuly save studio data - studio service");
            return new ResponseEntity<>(webResponse, HttpStatus.OK);
        }catch (Exception exception) {
            log.error("failed to save studio data, error {} - studio service",exception.getMessage());
            WebResponse<StudioResponse> webResponse = new WebResponse<>(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                    null
            );
            return new ResponseEntity<>(webResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/find/{id}")
    @ResponseBody
    public ResponseEntity<WebResponse<StudioResponse>> findById(@PathVariable("id") String id) {
        log.info("calling controller findById - studio service");
        try {
            StudioResponse studioResponse = studioService.findById(id);
            WebResponse<StudioResponse> webResponse = new WebResponse<>(
                    HttpStatus.OK.value(),
                    HttpStatus.OK.getReasonPhrase(),
                    studioResponse
            );
            log.info("successfuly find studio data - studio service");
            return new ResponseEntity<>(webResponse, HttpStatus.OK);
        }catch (DataNotFoundException exception) {
            log.error("studio with id {} not found ", id);
            WebResponse<StudioResponse> webResponse = new WebResponse<>(
                    HttpStatus.NOT_FOUND.value(),
                    HttpStatus.NOT_FOUND.getReasonPhrase(),
                    null
            );
            return new ResponseEntity<>(webResponse, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<WebResponse<List<MovieStudioResponse>>> addMovieStudio(@RequestBody List<MovieStudioRequest> movieStudioRequests) {
        log.info("calling controller add movie studio - studio service");
        try {
            log.info("do save studio and movie id");
            List<MovieStudioResponse> movieStudioResponses = movieStudioService.add(movieStudioRequests);
            WebResponse<List<MovieStudioResponse>> webResponse = new WebResponse<>(
                    HttpStatus.OK.value(),
                    HttpStatus.OK.getReasonPhrase(),
                    movieStudioResponses
            );
            log.info("successfuly save studio and movie id - studio service");
            return new ResponseEntity<>(webResponse, HttpStatus.OK);
        }catch (Exception exception) {
            log.error("failed to save studio data, error {} - studio service", exception.getMessage());
            WebResponse<List<MovieStudioResponse>> webResponse = new WebResponse<>(
                    HttpStatus.NOT_FOUND.value(),
                    HttpStatus.NOT_FOUND.getReasonPhrase(),
                    null
            );
            return new ResponseEntity<>(webResponse, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/get/{id}")
    @ResponseBody
    public ResponseEntity<WebResponse<List<MovieStudioResponse>>> getMovieStudioByMovieId(@PathVariable("id") String id) {
        log.info("call controller getMovieStudio by movieID");
        try {
            log.info("do get studio and movie studio with movie id");
            List<MovieStudioResponse> movieStudioResponses = movieStudioService.findByMovieId(id);
            WebResponse<List<MovieStudioResponse>> webResponse = new WebResponse<>(
                    HttpStatus.OK.value(),
                    HttpStatus.OK.getReasonPhrase(),
                    movieStudioResponses
            );
            log.info("successfuly get all studio and movie id by movie id");
            return new ResponseEntity<>(webResponse, HttpStatus.OK);
        } catch (Exception exception) {
            log.error("failed to get movieStudio and studio data, error {} ", exception.getMessage());
            WebResponse<List<MovieStudioResponse>> webResponse = new WebResponse<>(
                    HttpStatus.NOT_FOUND.value(),
                    HttpStatus.NOT_FOUND.getReasonPhrase(),
                    null
            );
            return new ResponseEntity<>(webResponse, HttpStatus.NOT_FOUND);
        }
    }
}
