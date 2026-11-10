package com.example.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RegisterRequest {

    @Pattern(regexp = "^[А-Я][а-я]+ [А-Я][а-я]+ [А-Я][а-я]+$", message = "Incorrect fullName")
    @JsonProperty("full_name")
    private String fullName;

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;

    @Pattern(regexp = "^\\d{3}-\\d{3}-\\d{4}$", message = "Phone must be in the format XXX-XXX-XXXX")
    private String phone;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;
}
