package com.neko.userservice.model.request;

import com.neko.userservice.model.entity.User;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {

    @NotEmpty(message = "The full name is required.")
    @Size(min = 2, max = 100, message = "The length of name must be between 2 and 100 characters.")
    private String name;

    @NotEmpty(message = "The email address is required.")
    @Email(message = "The email address is invalid.", flags = { Pattern.Flag.CASE_INSENSITIVE })
    private String email;

    @NotEmpty(message = "The phone is required.")
    @Pattern(regexp = "^[0-9]*$",message = "The phone number is invalid.")
    private String phone;

    @NotEmpty(message = "The password address is required.")
    private String password;

    public User toUser() {
        return User.builder()
                .name(this.name)
                .email(this.email)
                .phone(this.phone)
                .password(this.password)
                .build();
    }

}
