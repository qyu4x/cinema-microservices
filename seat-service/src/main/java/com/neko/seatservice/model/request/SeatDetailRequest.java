package com.neko.seatservice.model.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class SeatDetailRequest {

    @NotEmpty(message = "The id studio is required.")
    private String studioId;

    @NotEmpty(message = "The seat code is required.")
    private String seatCode;

    private Boolean status;


}
