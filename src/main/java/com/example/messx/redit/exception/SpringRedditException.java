package com.example.messx.redit.exception;

import org.springframework.mail.MailException;

public class SpringRedditException extends RuntimeException {
    public SpringRedditException(String exc){
        super(exc);
    }
}
