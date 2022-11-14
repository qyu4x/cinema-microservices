package com.neko.moviescheduleservice.model.response;

import com.neko.moviescheduleservice.model.entity.Schedule;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieScheduleResponse {

    private String id;

    private String movieId;

    private Schedule schedule;

}
