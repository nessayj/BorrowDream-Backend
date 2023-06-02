package com.kh.borrow_dream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Random;

@Service
public class EmailService {
    @Autowired
    JavaMailSender ms;
    private String authCode;
    public MimeMessage authCodeMessage(String to) throws MessagingException, UnsupportedEncodingException {
        MimeMessage msg = ms.createMimeMessage();
        msg.addRecipients(Message.RecipientType.TO, to);
        msg.setSubject("e-PL 회원가입 인증");
        String msgs = "";
        msgs += "<div style='margin:100px;'>";
        msgs += "<h1> 안녕하세요? </h1>";
        msgs += "<h1> e-PL 입니다.</h1>";
        msgs += "<br>";
        msgs += "<h2>회원가입을 위한 인증코드 입니다.</h2>";
        msgs += "<br>";
        msgs += "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgs += "<h3>인증코드 : </h3> <strong>";
        msgs += authCode + "</strong>";
        msg.setText(msgs, "utf-8", "html");

        msg.setFrom(new InternetAddress("chosh930911@gmail.com", "e-PL_Admin"));
        return msg;
    }

    public String createKey() {
        StringBuffer key = new StringBuffer();
        Random rnd = new Random();

        for (int i = 0; i < 8; i++) {
            int index = rnd.nextInt(3);

            switch (index) {
                case 0:
                    key.append((char) ((int) (rnd.nextInt(26)) +97));
                    break;
                case 1:
                    key.append((char) ((int) (rnd.nextInt(26)) + 65));
                    break;
                case 2:
                    key.append((rnd.nextInt(10)));
                    break;
            }
        }
        return key.toString();
    }

    public String authCodeSendMessage(String to) throws Exception {
        authCode = createKey();
        MimeMessage msg = authCodeMessage(to);
        try {
            ms.send(msg);
        } catch(MailException e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
        return authCode;
    }
}
