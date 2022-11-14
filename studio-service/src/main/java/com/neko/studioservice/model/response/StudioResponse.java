package com.neko.studioservice.model.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudioResponse {

    private String id;

    private String movieId;

    private String name;

    private Boolean isFull;

    private LocalDateTime createdAt;


}
