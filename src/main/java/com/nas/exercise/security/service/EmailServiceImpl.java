package com.nas.exercise.security.service;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.nas.exercise.security.domain.service.EmailService;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService{
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;
    
    @Override
    @Async("asyncTaskExecutor")
    public void sendEmailVerification(String addressee, String subject, String urlCode){
        try{
            //System.out.println("Starting email send task for: " + email.getAddressee());
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(addressee);
            helper.setSubject(subject);
            
            // Procesar la plantilla Thymeleaf
            Context context = new Context();
            context.setVariable("UrlCode", urlCode);
            String contentHTML = templateEngine.process("EmailVerificationTemplate", context);
            
            helper.setText(contentHTML, true);
            
            javaMailSender.send(message);
            System.out.println("Email sent to: " + urlCode);
            //System.out.println("Message: " + email.getMessage());
        } catch (Exception e){
            //System.out.println("Error sending email to: " + email.getAddressee() + ", " + e);
            throw new RuntimeException("Error al enviar al correo: " + e.getMessage(), e);
        }
    }

    @Override
    @Async("asyncTaskExecutor")
    public void sendPasswordReset(String addressee, String subject, String code){
        try{
            //System.out.println("Starting email send task for: " + email.getAddressee());
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(addressee);
            helper.setSubject(subject);
            
            // Procesar la plantilla Thymeleaf
            Context context = new Context();
            context.setVariable("Code", code);
            String contentHTML = templateEngine.process("PasswordResetCodeTemplate", context);
            
            helper.setText(contentHTML, true);
            
            javaMailSender.send(message);
            System.out.println("Email sent to: " + code);
            //System.out.println("Message: " + email.getMessage());
        } catch (Exception e){
            //System.out.println("Error sending email to: " + email.getAddressee() + ", " + e);
            throw new RuntimeException("Error al enviar al correo: " + e.getMessage(), e);
        }
    }
}
