package com.neko.studioservice.model.response;

import com.neko.studioservice.model.entity.MovieStudio;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudioResponse {

    private String id;

    private String name;

    private Boolean isFull;

    private LocalDateTime createdAt;

}
