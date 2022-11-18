package com.neko.moviescheduleservice.controller;

import com.neko.moviescheduleservice.model.request.MovieScheduleRequest;
import com.neko.moviescheduleservice.model.request.ScheduleRequest;
import com.neko.moviescheduleservice.model.response.MovieScheduleResponse;
import com.neko.moviescheduleservice.model.response.ScheduleResponse;
import com.neko.moviescheduleservice.model.response.WebResponse;
import com.neko.moviescheduleservice.service.MovieScheduleService;
import com.neko.moviescheduleservice.service.ScheduleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedule")
public class ScheduleController {

    private final static Logger log = LoggerFactory.getLogger(ScheduleController.class);

    private final ScheduleService scheduleService;

    private final MovieScheduleService movieScheduleService;

    @Autowired
    public ScheduleController(ScheduleService scheduleService, MovieScheduleService movieScheduleService) {
        this.scheduleService = scheduleService;
        this.movieScheduleService = movieScheduleService;
    }

    @PostMapping("/create")
    @ResponseBody
    public ResponseEntity<WebResponse<List<ScheduleResponse>>> createSchedule(@RequestBody List<ScheduleRequest> scheduleRequest) {
        log.info("calling controller createSchedule");
        try {
            List<ScheduleResponse> scheduleResponses = scheduleService.add(scheduleRequest);
            log.info("successfully entered schedule data");
            WebResponse<List<ScheduleResponse>> webResponse = new WebResponse<>(
                    HttpStatus.OK.value(),
                    HttpStatus.OK.getReasonPhrase(),
                    scheduleResponses
            );
            return new ResponseEntity<>(webResponse, HttpStatus.OK);
        } catch (Exception exception) {
            log.error("error {} ", exception.getMessage());
            WebResponse<List<ScheduleResponse>> webResponse = new WebResponse<>(
                    HttpStatus.OK.value(),
                    HttpStatus.OK.getReasonPhrase(),
                    null
            );
            return new ResponseEntity<>(webResponse, HttpStatus.OK);
        }
    }

    @PostMapping("/save")
    @ResponseBody
    public ResponseEntity<WebResponse<List<MovieScheduleResponse>>> saveMovieSchedule(@RequestBody List<MovieScheduleRequest> movieScheduleRequests) {
        log.info("calling controller saveMovieSchedule");
        try {
            List<MovieScheduleResponse> scheduleResponses = movieScheduleService.add(movieScheduleRequests);
            log.info("successfully entered movie schedule data");
            WebResponse<List<MovieScheduleResponse>> webResponse = new WebResponse<>(
                    HttpStatus.OK.value(),
                    HttpStatus.OK.getReasonPhrase(),
                    scheduleResponses
            );
            return new ResponseEntity<>(webResponse, HttpStatus.OK);
        } catch (Exception exception) {
            log.error("error {} ", exception.getMessage());
            WebResponse<List<MovieScheduleResponse>> webResponse = new WebResponse<>(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                    null
            );
            return new ResponseEntity<>(webResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get/{id}")
    @ResponseBody
    public ResponseEntity<WebResponse<List<MovieScheduleResponse>>> findMovieScheduleByMovieId(@PathVariable("id") String id) {
        log.info("call controller find movie schedule by movie id");
        try {
            List<MovieScheduleResponse> movieScheduleResponses = movieScheduleService.findMovieScheduleByMovieId(id);
            log.info("successfuly get schedule with id {} ", id );
            WebResponse<List<MovieScheduleResponse>> webResponse = new WebResponse<>(
                    HttpStatus.OK.value(),
                    HttpStatus.OK.getReasonPhrase(),
                    movieScheduleResponses
            );
            return new ResponseEntity<>(webResponse, HttpStatus.OK);
        }catch (Exception exception) {
            log.error("error {} ", exception.getMessage());
            WebResponse<List<MovieScheduleResponse>> webResponse = new WebResponse<>(
                    HttpStatus.NOT_FOUND.value(),
                    HttpStatus.NOT_FOUND.getReasonPhrase(),
                    null
            );
            return new ResponseEntity<>(webResponse, HttpStatus.NOT_FOUND);
        }
    }
}
