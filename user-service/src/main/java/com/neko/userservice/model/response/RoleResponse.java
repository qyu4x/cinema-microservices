package com.neko.userservice.model.response;

import com.neko.userservice.model.enums.UserRole;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RoleResponse {

    private String id;

    private UserRole role;

}
