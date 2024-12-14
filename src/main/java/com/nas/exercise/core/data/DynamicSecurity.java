package com.nas.exercise.core.data;

import com.nas.exercise.security.domain.model.SecurityRule;
import com.nas.exercise.security.domain.persistence.RoleOperationRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
//import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class DynamicSecurity {
    private final RoleOperationRepository roleOperationRepository;

    public void applySecurityConfig(HttpSecurity http) throws Exception {
        List<SecurityRule> rules = roleOperationRepository.findSecurityRules();

        Map<String, Map<String, Set<String>>> groupedRules = new HashMap<>();

        for (SecurityRule rule : rules) {
            String method = rule.getHttpMethod();
            String path = rule.getPath();
            String role = rule.getRole();

            groupedRules
                .computeIfAbsent(method, k -> new HashMap<>())
                .computeIfAbsent(path, k -> new HashSet<>())
                .add(role);
        }

        http
            .authorizeHttpRequests(authorizeRequests -> {
                authorizeRequests
                    .requestMatchers("/api/v1/auth/**").permitAll();

                for (Map.Entry<String, Map<String, Set<String>>> methodEntry : groupedRules.entrySet()) {
                    for (Map.Entry<String, Set<String>> pathEntry : methodEntry.getValue().entrySet()) {
                        authorizeRequests
                            .requestMatchers(HttpMethod.valueOf(methodEntry.getKey()), pathEntry.getKey())
                                .hasAnyRole(pathEntry.getValue().toArray(new String[0]));
                        //System.out.println("metodo: " + methodEntry.getKey() + ", endpoint: " + pathEntry.getKey() + ", roles: " + Arrays.toString(pathEntry.getValue().toArray(new String[0])));
                    }
                }
            authorizeRequests.anyRequest().permitAll();
            });
    }
}
