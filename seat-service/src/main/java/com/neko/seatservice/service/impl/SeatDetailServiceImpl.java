package com.neko.seatservice.service.impl;

import com.neko.seatservice.client.StudioExternalClient;
import com.neko.seatservice.exception.DataNotFoundException;
import com.neko.seatservice.model.entity.Seat;
import com.neko.seatservice.model.entity.SeatDetail;
import com.neko.seatservice.model.request.SeatDetailRequest;
import com.neko.seatservice.model.response.SeatDetailResponse;
import com.neko.seatservice.model.response.WebStudioResponse;
import com.neko.seatservice.repository.SeatDetailRepository;
import com.neko.seatservice.repository.SeatRepository;
import com.neko.seatservice.service.SeatDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class SeatDetailServiceImpl implements SeatDetailService {

    private final SeatDetailRepository seatDetailRepository;

    private final StudioExternalClient studioExternalClient;

    private final SeatRepository seatRepository;

    @Autowired
    public SeatDetailServiceImpl(SeatDetailRepository seatDetailRepository, StudioExternalClient studioExternalClient, SeatRepository seatRepository) {
        this.seatDetailRepository = seatDetailRepository;
        this.studioExternalClient = studioExternalClient;
        this.seatRepository = seatRepository;
    }

    @Override
    public SeatDetailResponse addSeatAndStudio(SeatDetailRequest seatDetailRequest) {
        log.info("do add a seat with seat code {} ", seatDetailRequest.getSeatCode());
        Optional<Seat> seat = seatRepository.findById(seatDetailRequest.getSeatCode());
        WebStudioResponse studioResponse = studioExternalClient.findStudioById(seatDetailRequest.getStudioId());
        if (seat.get() == null || studioResponse.getData() == null) {
            throw new DataNotFoundException("seat or studio is not found");
        }

        SeatDetail seatDetail = SeatDetail.builder()
                .id(UUID.randomUUID().toString())
                .seatId(seat.get())
                .studioId(studioResponse.getData().getName())
                .build();

        seatDetailRepository.save(seatDetail);
        log.info("success add seat and studio");

        return SeatDetailResponse.builder()
                .seatCode(seat.get().getSeatCode())
                .studioName(studioResponse.getData().getName())
                .build();
        
    }

    @Override
    public List<SeatDetailResponse> getSeatIfAvailable(Boolean status) {
        return null;
    }
}
