package com.woh.mailservice.Service.Mailimtl;

import com.woh.mailservice.Service.MailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class MailserviceImtl implements MailService {
    private JavaMailSender Mailsender;

    public MailserviceImtl(JavaMailSender mailsender) {
        Mailsender = mailsender;
    }


    @Override
    public String sendMail() {
        String verificationCode = generateVerificationCode();
        MimeMessage mimeMessage = Mailsender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, "utf-8");

        try {
            messageHelper.setFrom("serkafidanci@gmail.com");
            messageHelper.setTo("erdemoden5@gmail.com");
            messageHelper.setSubject("Doğrulama Kodu");
            messageHelper.setText("<h1>Doğrulama Kodunuz: " + verificationCode + "</h1>", true);
            Mailsender.send(mimeMessage);
            return verificationCode;
        } catch (MessagingException e) {
            e.printStackTrace();
            return "Doğrulama Kodunda Hata Oluştu";
        }
    }
    public String generateVerificationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }
}
