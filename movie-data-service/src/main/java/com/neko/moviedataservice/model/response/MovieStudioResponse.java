package com.neko.moviedataservice.model.response;

import com.neko.moviedataservice.model.cliententity.Studio;
import lombok.*;

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
