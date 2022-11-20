package com.neko.seatservice.service;

import com.neko.seatservice.model.request.SeatRequest;
import com.neko.seatservice.model.response.SeatResponse;

public interface SeatService {

    SeatResponse add(SeatRequest seatRequest);

    Boolean findIfExist(String id);

}
