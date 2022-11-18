package com.neko.seatservice.model.response;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SeatResponse {

    private String seatCode;

}
