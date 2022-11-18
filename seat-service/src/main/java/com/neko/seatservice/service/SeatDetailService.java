package com.neko.seatservice.service;

import com.neko.seatservice.model.request.SeatDetailRequest;
import com.neko.seatservice.model.response.SeatDetailResponse;

import java.util.List;

public interface SeatDetailService {

    SeatDetailResponse addSeatAndStudio(SeatDetailRequest seatDetailRequest);

    List<SeatDetailResponse> getSeatIfAvailable();

}
