package com.neko.orderservice.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WebStudioResponse {

    private Integer code;

    private String status;

    private StudioResponse data;

}
