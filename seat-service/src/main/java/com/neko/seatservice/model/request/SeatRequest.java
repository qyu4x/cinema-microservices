package com.neko.seatservice.model.request;

import com.neko.seatservice.model.entity.SeatDetail;
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
public class SeatRequest {

    @NotEmpty(message = "The seat code is required.")
    private String seatCode;

    private List<SeatDetailRequest> seatDetailRequests;

}
