package com.woh.mailservice.Service.Mailimtl;

import com.woh.mailservice.Service.MailService;
import com.woh.mailservice.redis.RedisStore;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class MailserviceImtl implements MailService {
    private JavaMailSender Mailsender;
    private RedisStore Redisstore;
    public MailserviceImtl(JavaMailSender mailsender,RedisStore redisStore) {
        Mailsender = mailsender;
        Redisstore = redisStore;
    }


    @Override
    public String sendMail(String mail) {
        String verificationCode = generateVerificationCode();
        MimeMessage mimeMessage = Mailsender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, "utf-8");
        try {
            messageHelper.setFrom("serkafidanci@gmail.com");
            messageHelper.setTo(mail);
            messageHelper.setSubject("Doğrulama Kodu");
            messageHelper.setText("<h1>Doğrulama Kodunuz: " + verificationCode + "</h1>", true);
            Mailsender.send(mimeMessage);
            Redisstore.put(verificationCode,mail);
            return "Doğrulama Kodu Başarıyla Gönderildi";
        } catch (MessagingException e) {
            e.printStackTrace();
            return "Doğrulama Kodunda Hata Oluştu";
        }
    }
    @Override
    public Boolean checkMail(String key,String mail){
        if( Redisstore.getValue(key)!=null && Redisstore.getValue(key).equals(mail)){
            return true;
        }
        return false;
    }
    public String generateVerificationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }
}
