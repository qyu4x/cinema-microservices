package com.neko.studioservice.controller;

import com.neko.studioservice.exception.DataNotFoundException;
import com.neko.studioservice.model.request.StudioRequest;
import com.neko.studioservice.model.response.StudioResponse;
import com.neko.studioservice.model.response.WebResponse;
import com.neko.studioservice.service.StudioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/studio")
public class StudioController {

    private final static Logger log = LoggerFactory.getLogger(StudioController.class);

    private final StudioService studioService;

    @Autowired
    public StudioController(StudioService studioService) {
        this.studioService = studioService;
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
            log.error("failed to save studio data - studio service");
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
}
