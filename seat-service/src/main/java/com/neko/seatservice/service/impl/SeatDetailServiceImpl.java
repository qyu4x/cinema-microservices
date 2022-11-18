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

import java.util.ArrayList;
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
        log.info("success get studio response");
        if (seat.get() == null || studioResponse.getData() == null) {
            throw new DataNotFoundException("seat or studio is not found");
        }

        SeatDetail seatDetail = SeatDetail.builder()
                .id(UUID.randomUUID().toString())
                .seatId(seat.get())
                .studioId(studioResponse.getData().getName())
                .status(seatDetailRequest.getStatus())
                .build();

        seatDetailRepository.save(seatDetail);
        log.info("success add seat and studio");

        return SeatDetailResponse.builder()
                .seatCode(seat.get().getSeatCode())
                .studioName(studioResponse.getData().getName())
                .status(seatDetail.getStatus())
                .build();
        
    }

    @Override
    public List<SeatDetailResponse> getSeatIfAvailable() {
        log.info("do get seat detail if available");
        List<SeatDetailResponse> seatDetailResponses = new ArrayList<>();
        List<SeatDetail> seatDetails = seatDetailRepository.findSeatDetailByStatus(true);
        seatDetails.stream().forEach(seatDetail -> {
            SeatDetailResponse seatDetailResponse = SeatDetailResponse.builder()
                    .studioName(seatDetail.getStudioId())
                    .seatCode(seatDetail.getSeatId().getSeatCode())
                    .status(seatDetail.getStatus())
                    .build();
            seatDetailResponses.add(seatDetailResponse);
        } );
        log.info("success get seat detail, if status = true");
        return seatDetailResponses;
    }
}
