package com.neko.moviedataservice.model.response;

import com.neko.moviedataservice.model.cliententity.Schedule;
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
