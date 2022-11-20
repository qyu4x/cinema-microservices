package com.neko.orderservice.model.request;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderSeatRequest {

    @NotEmpty(message = "The seat code is required.")
    private String seatCode;

    @NotEmpty(message = "The seat code is required.")
    private String studioName;

}
