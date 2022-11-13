package com.neko.moviedataservice.model.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WebResponse<T> {

    private Integer code;

    private String status;

    private T data;

}
