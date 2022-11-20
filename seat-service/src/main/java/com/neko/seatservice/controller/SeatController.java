package com.neko.seatservice.controller;

import com.neko.seatservice.model.request.SeatDetailRequest;
import com.neko.seatservice.model.request.SeatRequest;
import com.neko.seatservice.model.response.SeatDetailResponse;
import com.neko.seatservice.model.response.SeatResponse;
import com.neko.seatservice.model.response.WebResponse;
import com.neko.seatservice.service.SeatDetailService;
import com.neko.seatservice.service.SeatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/seat")
public class SeatController {


    private final SeatService seatService;

    private final SeatDetailService seatDetailService;

    @Autowired
    public SeatController(SeatService seatService, SeatDetailService seatDetailService) {
        this.seatService = seatService;
        this.seatDetailService = seatDetailService;
    }

    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<WebResponse<SeatResponse>> addSeat(@RequestBody SeatRequest seatRequest) {
        log.info("call controller add seat");
        try {
            SeatResponse seatResponse = seatService.add(seatRequest);
            log.info("success add data seat into table seat");
            WebResponse webResponse = new WebResponse(
                    HttpStatus.OK.value(),
                    HttpStatus.OK.getReasonPhrase(),
                    seatResponse
            );
            return new ResponseEntity<>(webResponse, HttpStatus.OK);
        }catch (Exception exception) {
            WebResponse webResponse = new WebResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                    null
            );

            return new ResponseEntity<>(webResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/add-seat-studio")
    @ResponseBody
    public ResponseEntity<WebResponse<SeatDetailResponse>> addSeatAndStudio(@RequestBody SeatDetailRequest seatDetailRequest) {
        log.info("call controller add seat and studio");
        try {
            SeatDetailResponse seatDetailResponse = seatDetailService.addSeatAndStudio(seatDetailRequest);
            log.info("success to add seat and studio into table seat detail - seat detail service");
            WebResponse webResponse = new WebResponse(
                    HttpStatus.OK.value(),
                    HttpStatus.OK.getReasonPhrase(),
                    seatDetailResponse
            );
            return new ResponseEntity<>(webResponse, HttpStatus.OK);
        }catch (Exception exception) {
            log.error("error {} ", exception.getMessage());
            WebResponse webResponse = new WebResponse(
                    HttpStatus.NOT_FOUND.value(),
                    HttpStatus.NOT_FOUND.getReasonPhrase(),
                    null
            );

            return new ResponseEntity<>(webResponse, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/get-available")
    @ResponseBody
    public ResponseEntity<WebResponse<List<SeatDetailResponse>>> getSeatIfAvailable() {
        log.info("call controller getSeatIfAvailable - seat detail service");
        try {
            List<SeatDetailResponse> seatIfAvailableResponse = seatDetailService.getSeatIfAvailable();
            WebResponse webResponse = new WebResponse(
                    HttpStatus.OK.value(),
                    HttpStatus.OK.getReasonPhrase(),
                    seatIfAvailableResponse
            );
            return new ResponseEntity<>(webResponse, HttpStatus.OK);
        }catch (Exception exception) {
            log.error("error {} ", exception.getMessage());
            WebResponse webResponse = new WebResponse(
                    HttpStatus.NOT_FOUND.value(),
                    HttpStatus.NOT_FOUND.getReasonPhrase(),
                    null
            );

            return new ResponseEntity<>(webResponse, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/exists/{id}")
    @ResponseBody
    public ResponseEntity<WebResponse<Boolean>> checkIfExists(@PathVariable("id") String id) {
        log.info("call controller check if exists - seat detail service");
        try {
            Boolean seatIfAvailableResponse = seatService.findIfExist(id);
            WebResponse webResponse = new WebResponse(
                    HttpStatus.OK.value(),
                    HttpStatus.OK.getReasonPhrase(),
                    seatIfAvailableResponse
            );
            return new ResponseEntity<>(webResponse, HttpStatus.OK);
        }catch (Exception exception) {
            log.error("error {} ", exception.getMessage());
            WebResponse webResponse = new WebResponse(
                    HttpStatus.NOT_FOUND.value(),
                    HttpStatus.NOT_FOUND.getReasonPhrase(),
                    false
            );

            return new ResponseEntity<>(webResponse, HttpStatus.NOT_FOUND);
        }
    }


}
