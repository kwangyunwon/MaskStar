package com.mask8.backend.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MailService {
	
	@Autowired
	private JavaMailSender javaMailSender;

    public String sendEmail(String email) {
    	SimpleMailMessage message = new SimpleMailMessage();
    	
    	String key = getAuthCode();
    	message.setTo(email);
    	message.setSubject("OnlineMask 이메일 인증");
    	message.setText("인증번호 : " +key);
        javaMailSender.send(message);
        
        
        return key;
    }
    
    private String getAuthCode() {
        Random random = new Random();
        StringBuffer buffer = new StringBuffer();
        int num = 0;

        while(buffer.length() < 6) {
            num = random.nextInt(10);
            buffer.append(num);
        }

        return buffer.toString();
    }
}
