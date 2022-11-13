package com.neko.moviedataservice.model.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@Builder
public class MovieRequest {

    @NotEmpty(message = "The full title is required.")
    @Size(min = 2, max = 100, message = "The length of full title must be between 2 and 100 characters.")
    private String title;

    private Boolean showStatus;

    private Integer duration;

    private LocalDate startDate;

    private LocalDate endDate;

    private String description;

    private String genre;

    private String country;

    private String language;

}
