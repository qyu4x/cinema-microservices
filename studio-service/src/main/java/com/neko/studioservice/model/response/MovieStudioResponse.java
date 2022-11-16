package com.neko.studioservice.model.response;

import com.neko.studioservice.model.entity.Studio;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieStudioResponse {

    private String id;

    private String movieId;

    private Studio studio;

}
