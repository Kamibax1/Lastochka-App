package com.example.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserUpdateRequest {
    @Pattern(regexp = "^[А-Я][а-я]+ [А-Я][а-я]+ [А-Я][а-я]+$", message = "Incorrect fullName")
    @JsonProperty("full_name")
    private String fullName;

    @Pattern(regexp = "^\\d{3}-\\d{3}-\\d{4}$", message = "Phone must be in the format XXX-XXX-XXXX")
    private String phone;

    @Pattern(regexp = "^https?://(?:www\\.)?[-a-zA-Z0-9@:%._+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b[-a-zA-Z0-9()@:%_+.~#?&/=]*$\n",
    message = "Incorrect url")
    private String avatarUrl;
}
