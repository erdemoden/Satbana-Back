package com.woh.mailservice.Service.Mailimtl;

import com.woh.mailservice.Service.MailService;
import com.woh.mailservice.Redis.RedisStore;
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
            String htmlContent = "<!DOCTYPE html>"
                    + "<html>"
                    + "<head>"
                    + "    <style>"
                    + "        .email-container {"
                    + "            font-family: Arial, sans-serif;"
                    + "            color: #333333;"
                    + "            background-color: #f4f4f4;"
                    + "            padding: 20px;"
                    + "            text-align: center;"
                    + "        }"
                    + "        .email-header {"
                    + "            background-color: #4a7ebc;"
                    + "            color: white;"
                    + "            padding: 10px 0;"
                    + "        }"
                    + "        .email-body {"
                    + "            background-color: white;"
                    + "            padding: 20px;"
                    + "            margin-top: 20px;"
                    + "        }"
                    + "        .verification-code {"
                    + "            font-size: 24px;"
                    + "            font-weight: bold;"
                    + "            color: #4a7ebc;"
                    + "            margin: 20px 0;"
                    + "        }"
                    + "        .footer {"
                    + "            margin-top: 30px;"
                    + "            font-size: 12px;"
                    + "            color: #777777;"
                    + "        }"
                    + "    </style>"
                    + "</head>"
                    + "<body>"
                    + "    <div class=\"email-container\">"
                    + "        <div class=\"email-header\">"
                    + "            Doğrulama Kodunuz"
                    + "        </div>"
                    + "        <div class=\"email-body\">"
                    + "            Aşağıdaki doğrulama kodunu kullanarak işleminizi tamamlayabilirsiniz:"
                    + "            <div class=\"verification-code\">"
                    +                 verificationCode
                    + "            </div>"
                    + "            Bu kodu kimseyle paylaşmayın."
                    + "        </div>"
                    + "        <div class=\"footer\">"
                    + "            Bu e-posta otomatik olarak gönderilmiştir, lütfen yanıtlamayınız."
                    + "        </div>"
                    + "    </div>"
                    + "</body>"
                    + "</html>";
            messageHelper.setFrom("serkafidanci@gmail.com");
            messageHelper.setTo(mail);
            messageHelper.setSubject("Doğrulama Kodu");
            messageHelper.setText(htmlContent, true);
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
        if(Redisstore.getValue(key)!=null && Redisstore.getValue(key).equals(mail)){
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
