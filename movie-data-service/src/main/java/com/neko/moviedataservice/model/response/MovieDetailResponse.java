package com.neko.moviedataservice.model.response;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieDetailResponse {

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


    private List<MovieScheduleResponse> scheduleResponses;

    private List<MovieStudioResponse> movieStudioResponses;

}
