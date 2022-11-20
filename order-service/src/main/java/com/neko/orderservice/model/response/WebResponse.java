package com.neko.orderservice.model.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WebResponse <T>{

    private Integer code;

    private String status;

    private T data;
}
