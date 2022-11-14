package com.neko.studioservice.service.impl;

import com.neko.studioservice.exception.DataNotFoundException;
import com.neko.studioservice.model.entity.Studio;
import com.neko.studioservice.model.request.StudioRequest;
import com.neko.studioservice.model.response.StudioResponse;
import com.neko.studioservice.repository.StudioRepository;
import com.neko.studioservice.service.StudioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class StudioServiceImpl implements StudioService {

    private final StudioRepository studioRepository;

    @Autowired
    public StudioServiceImpl(StudioRepository studioRepository) {
        this.studioRepository = studioRepository;
    }

    @Override
    public StudioResponse save(StudioRequest studioRequest) {
        Studio studio = Studio.builder()
                .id(UUID.randomUUID().toString())
                .movieId(studioRequest.getMovieId())
                .name(studioRequest.getName())
                .createdAt(LocalDateTime.now())
                .isFull(false)
                .build();

        studioRepository.save(studio);

        return StudioResponse.builder()
                .id(studio.getId())
                .movieId(studio.getMovieId())
                .name(studio.getName())
                .isFull(studio.getIsFull())
                .createdAt(studio.getCreatedAt())
                .build();
    }

    @Override
    public StudioResponse findById(String id) {
        Studio studio =  studioRepository.findById(id).orElseThrow(() -> {
            throw new DataNotFoundException(String.format("studio with id %s not found", id));
        });

        return StudioResponse.builder()
                .id(studio.getId())
                .name(studio.getName())
                .movieId(studio.getMovieId())
                .isFull(studio.getIsFull())
                .createdAt(studio.getCreatedAt())
                .build();
    }
}
