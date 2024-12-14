package com.nas.exercise.security.resource.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    @NotNull
    @NotBlank
    @Email
    String email;

    @NotNull
    @NotBlank
    @Email
    String username;

    @NotNull
    @NotBlank
    String password;
}
