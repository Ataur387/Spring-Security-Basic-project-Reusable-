package com.example.Real.Time.Chat.Application.Helpers.MailSender;

import com.example.Real.Time.Chat.Application.Security.JWT.JwtHelper;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
public class LocalMailSender {
    @Autowired
    private JavaMailSender javaMailSender;
    private final JwtHelper jwtHelper;

    public LocalMailSender(JwtHelper jwtHelper) {
        this.jwtHelper = jwtHelper;
    }

    public void  sendRegistrationVerificationMail(String to, String subject, String body){
        MimeMessagePreparator mimeMessagePreparator = new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                mimeMessageHelper.setTo(to);
                mimeMessageHelper.setSubject(subject);
                String verificationLink = "http://127.0.0.1:8080/register/verify?email=" + to;

                String emailBody = "<p>Thank you for signing up! Please click the link below to verify your account:</p>" +
                        "<a href='" + verificationLink + "'>Verify Email</a>";
                mimeMessageHelper.setText(emailBody, true);
            }
        };
        javaMailSender.send(mimeMessagePreparator);
    }
}
