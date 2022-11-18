package com.neko.seatservice.model.client;

import lombok.*;

import java.time.LocalDateTime;

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
