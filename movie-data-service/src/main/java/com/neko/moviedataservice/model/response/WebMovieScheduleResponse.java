package com.neko.moviedataservice.model.response;

import com.neko.moviedataservice.model.request.MovieScheduleRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WebMovieScheduleResponse {

    private Integer code;

    private String status;

    private List<MovieScheduleResponse> data;

}
