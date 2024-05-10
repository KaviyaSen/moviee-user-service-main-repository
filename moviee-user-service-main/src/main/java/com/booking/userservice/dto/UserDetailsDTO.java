package com.booking.userservice.dto;

import com.booking.userservice.entity.UserPlan;
import com.booking.userservice.utils.validator.EnumOf;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDetailsDTO {

    @Pattern(regexp = "\\w+", message = "Username must not contain special characters")
    @NotBlank(message = "Username is mandatory")
    @Size(min = 2, max = 48, message = "Username must be between 2 and 48 characters")
    private String userName;

    @Pattern(regexp = "^(?=(.*\\d){2})(?=.*[a-zA-Z])(?=.*[!@#$%])[0-9a-zA-Z!@#$%]{8,}",
            message = "Password must contain alphabets, numbers, special characters(!@#$%) and have length more than 8")
    @NotBlank(message = "Password is mandatory")
    private String password;

    @Pattern(regexp = "[a-zA-Z]+", message = "Not a valid name")
    @NotBlank(message = "First name is mandatory")
    private String firstName;

    @Pattern(regexp = "[a-zA-Z]+", message = "Not a valid name")
    @NotBlank(message = "Last name is mandatory")
    private String lastName;

    @NotBlank(message = "Email Id is mandatory")
    @Email(message = "Invalid email format", regexp = ".+@.+\\..+")
    private String emailId;

    @Pattern(regexp = "\\d+", message = "Not a valid phone number")
    @NotBlank(message = "Phone number is mandatory")
    @Size(min = 10, max = 10, message = "Enter a valid phone number")
    private String phoneNumber;

    @NotNull(message = "Date of birth is mandatory")
    @Past(message = "Enter a valid Date of birth")
    private LocalDate dateOfBirth;

    @NotNull(message = "Must be a valid user plan")
    @EnumOf(enumClass = UserPlan.class, message = "Must be a valid user plan")
    private String userPlan;
}
