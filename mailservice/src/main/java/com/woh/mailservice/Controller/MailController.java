package com.woh.mailservice.Controller;

import com.woh.mailservice.Service.MailService;
import com.woh.mailservice.Service.Mailimtl.MailserviceImtl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail")
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;

    @GetMapping("/codesend")
    public ResponseEntity<String> sendCodeMail(@RequestParam("mail") String mail){
        return ResponseEntity.ok(mailService.sendMail(mail));
    }
    @GetMapping("/checkmail")
    public ResponseEntity<Boolean> checkCodeMail(@RequestParam("mail") String mail,@RequestParam("key") String key){
        return ResponseEntity.ok(mailService.checkMail(key,mail));
    }
}
