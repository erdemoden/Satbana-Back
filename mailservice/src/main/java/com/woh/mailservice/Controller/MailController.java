package com.woh.mailservice.Controller;

import com.woh.mailservice.Service.MailService;
import com.woh.mailservice.Service.Mailimtl.MailserviceImtl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail")
public class MailController {

    private final MailService mailService;

@Autowired
    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    @GetMapping("/codesend")
    public ResponseEntity<String> sendCodeMail(){
        return ResponseEntity.ok(mailService.sendMail());
    }
}
