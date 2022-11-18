package com.neko.seatservice.model.response;

import com.neko.seatservice.model.client.StudioResponse;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WebStudioResponse {

    private String code;

    private String status;

    private StudioResponse data;

}
