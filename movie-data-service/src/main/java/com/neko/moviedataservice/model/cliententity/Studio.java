package com.neko.moviedataservice.model.cliententity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Studio {

    private String id;

    private String name;

    private Boolean isFull;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
