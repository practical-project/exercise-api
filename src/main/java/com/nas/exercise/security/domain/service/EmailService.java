package com.nas.exercise.security.domain.service;

public interface EmailService {
    public void sendEmailVerification(String addressee, String subject, String urlCode);
    public void sendPasswordReset(String addressee, String subject, String urlCode);
} 
