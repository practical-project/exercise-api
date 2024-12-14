package com.nas.exercise.core.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.nas.exercise.core.data.DynamicSecurity;
import com.nas.exercise.core.jwt.JwtAuthenticationFilter;

import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authProvider;
    private final DynamicSecurity DynamicSecurity;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        DynamicSecurity.applySecurityConfig(http);
        return http
                .cors(cors -> {
                    CorsConfigurationSource source = corsConfigurationSource();
                    cors.configurationSource(source);
                })
                .csrf(AbstractHttpConfigurer::disable)
                // .authorizeHttpRequests(authRequest ->
                //     authRequest
                //         //.requestMatchers(HttpMethod.PUT).permitAll()
                //         .requestMatchers("/api/v1/auth/**").permitAll()
                //         .requestMatchers(HttpMethod.valueOf(HttpMethodEnum.GET.toString()), "/api/v1/users").hasRole("ADMIN")
                //         .requestMatchers(HttpMethod.valueOf(HttpMethodEnum.GET.toString()), "/api/v1/users/me").hasAnyRole("USER", "ADMIN")
                //         .requestMatchers(HttpMethod.valueOf(HttpMethodEnum.PUT.toString()), "/api/v1/users/me").hasAnyRole("USER", "ADMIN")
                //         .requestMatchers(HttpMethod.valueOf(HttpMethodEnum.DELETE.toString()), "/api/v1/users/me").hasAnyRole("USER", "ADMIN")
                //         //.anyRequest().authenticated()
                //         )
                .sessionManagement(sessionManager->
                    sessionManager
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:4200", "http://localhost:5000")); // URL de tu frontend
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
