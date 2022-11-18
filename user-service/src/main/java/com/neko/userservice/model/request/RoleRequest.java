package com.neko.userservice.model.request;

import com.neko.userservice.model.enums.UserRole;
import lombok.*;

import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RoleRequest {

    @NotEmpty(message = "The role is required.")
    private UserRole role;

}
