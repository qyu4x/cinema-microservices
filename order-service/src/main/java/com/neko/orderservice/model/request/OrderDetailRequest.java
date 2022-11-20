package com.neko.orderservice.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailRequest {

    @NotEmpty(message = "The studioId is required.")
    private String studioId;

    @NotEmpty(message = "The scheduleId is required.")
    private String scheduleId;

    @NotEmpty(message = "The quantity is required.")
    private Integer quantity;

    private List<OrderSeatRequest> orderSeatRequests;

}
