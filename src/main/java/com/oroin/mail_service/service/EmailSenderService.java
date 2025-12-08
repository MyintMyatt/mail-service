package com.oroin.mail_service.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailSenderService {

    private final JavaMailSender mailSender;

    public boolean send(List<String> to, List<String> cc, List<String> bcc, String subject, String html) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setSubject(subject);
            helper.setTo(to.toArray(new String[0]));

            if (cc != null && !cc.isEmpty()) helper.setCc(cc.toArray(new String[0]));
            if (bcc != null && !bcc.isEmpty()) helper.setBcc(bcc.toArray(new String[0]));

            helper.setText(html, true);
            mailSender.send(message);

            // return success;
            return true;
        } catch (MessagingException e) {
            System.err.println(e.getMessage());
            // return send mail failed;
            return false;
        }
    }
}
