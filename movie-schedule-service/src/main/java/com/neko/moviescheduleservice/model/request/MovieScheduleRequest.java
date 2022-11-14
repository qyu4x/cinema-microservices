package com.neko.moviescheduleservice.model.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieScheduleRequest {

    private String movieId;

    private String scheduleId;

}
