package com.neko.moviedataservice.model.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class MovieResponse {

    private String id;

    private String title;

    private Boolean showStatus;

    private Integer duration;

    private LocalDate startDate;

    private LocalDate endDate;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String description;

    private String genre;

    private String country;

    private String language;

    private Boolean deleted;

}
