package com.neko.seatservice.service.impl;

import com.neko.seatservice.model.entity.Seat;
import com.neko.seatservice.model.request.SeatRequest;
import com.neko.seatservice.model.response.SeatResponse;
import com.neko.seatservice.repository.SeatRepository;
import com.neko.seatservice.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SeatServiceImpl implements SeatService {

    private final SeatRepository seatRepository;

    @Autowired
    public SeatServiceImpl(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    @Override
    public SeatResponse add(SeatRequest seatRequest) {
        Seat seat = Seat.builder()
                .seatCode(seatRequest.getSeatCode())
                .build();
        seatRepository.save(seat);
        return SeatResponse.builder()
                .seatCode(seat.getSeatCode())
                .build();
    }
}
