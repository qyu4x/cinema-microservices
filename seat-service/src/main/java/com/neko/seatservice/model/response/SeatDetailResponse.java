package com.neko.seatservice.model.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeatDetailResponse {

    private String studioName;

    private String seatCode;

    private Boolean status;

}
