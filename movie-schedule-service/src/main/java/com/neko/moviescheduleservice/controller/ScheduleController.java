package com.neko.moviescheduleservice.controller;

import com.neko.moviescheduleservice.model.request.ScheduleRequest;
import com.neko.moviescheduleservice.model.response.ScheduleResponse;
import com.neko.moviescheduleservice.model.response.WebResponse;
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

    @Autowired
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
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
}
