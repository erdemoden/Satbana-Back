package com.woh.mailservice.Service;

import ch.qos.logback.core.joran.event.BodyEvent;

public interface MailService {
    String sendMail(String mail);
    Boolean checkMail(String key,String mail);
}
