package com.nas.exercise.security.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Integer id;

  @NotNull
  @NotBlank
  @Email
  @Column(name = "email", nullable = false, unique = true)
  String email;

  @NotNull
  @NotBlank
  @Column(name = "username", nullable = false, unique = true)
  String username;

  @NotNull
  @NotBlank
  @Column(name = "hashed_password", nullable = false)
  String hashedPassword;

  @NotNull
  @NotBlank
  @Column(name = "verification_uuid", nullable = false, unique = true)
  String verificationUuid;

  @Column(name = "email_verified_at", nullable = true)
  LocalDateTime emailVerifiedAt;

  @CreationTimestamp
  @Column(name = "created_at", nullable = false, updatable = false)
  LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at", nullable = false)
  LocalDateTime updatedAt;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "role", nullable = false)
  Role role;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
  }

  @Override
  public String getUsername() {
    return this.email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public String getPassword() {
    return this.hashedPassword;
  }
}
