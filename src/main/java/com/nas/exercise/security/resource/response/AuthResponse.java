package com.nas.exercise.security.resource.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    @JsonProperty("access_token")
    String accessToken;
    @JsonProperty("token_type")
    String tokenType;
    @JsonProperty("expires_in")
    LocalDateTime expiresIn;
    @JsonProperty("user")
    UserResponse userResponse;
}
