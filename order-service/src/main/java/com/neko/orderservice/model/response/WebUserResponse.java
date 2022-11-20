package com.neko.orderservice.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WebUserResponse {

    private Integer code;

    private String  status;

    private UserResponse data;

}
