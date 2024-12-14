package com.nas.exercise.security.resource.request;

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
public class CodeVerificationRequest {
    @NotNull
    @NotBlank
    public String code;

    @NotNull
    @NotBlank
    public String email;
}
