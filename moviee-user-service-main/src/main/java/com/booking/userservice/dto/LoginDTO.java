package com.booking.userservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginDTO {
    @Pattern(regexp = "\\w+", message = "Username must not contain special characters")
    @NotBlank(message = "Username is mandatory")
    @Size(min = 2, max = 48, message = "Username must be between 2 and 48 characters")
    private String userName;

    @Pattern(regexp = "^(?=(.*\\d){2})(?=.*[a-zA-Z])(?=.*[!@#$%])[0-9a-zA-Z!@#$%]{8,}",
            message = "Password must contain alphabets, numbers, special characters(!@#$%) and have length more than 8")
    @NotBlank(message = "Password is mandatory")
    private String password;
}
