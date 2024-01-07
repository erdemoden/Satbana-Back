package com.woh.satbana.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "MAILSERVICE/mail")
public interface MailServiceClient {

    @GetMapping("/codesend")
    ResponseEntity<String> sendCodeMail(@RequestParam("mail") String mail);

    @GetMapping("/checkmail")
    ResponseEntity<Boolean> checkCodeMail(@RequestParam("mail") String mail,@RequestParam("key") String key);
}
