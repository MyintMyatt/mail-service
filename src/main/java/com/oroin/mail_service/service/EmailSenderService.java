package com.oroin.mail_service.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmailSenderService {

    private static final Logger logger = LoggerFactory.getLogger(EmailSenderService.class);
    private final JavaMailSender mailSender;

    public boolean send(List<String> to, List<String> cc, List<String> bcc, String subject, String html) {

        logger.info("sending otp mail", to, cc, subject);

        try {
            InternetAddress[] addressTo = to.stream()
                    .map(email -> {
                        try {
                            return new InternetAddress(email);
                        } catch (AddressException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .toArray(InternetAddress[]::new);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setSubject(subject);
            helper.setFrom(new InternetAddress("nyinyimyintmyat@cp.com.mm"));

            // Use the trimmed array
            helper.setTo(addressTo);

            if (cc.size() > 0 && cc != null && !cc.isEmpty()) {
                InternetAddress[] ccAdd = cc.stream()
                        .map(email -> {
                            try {
                                return new InternetAddress(email);
                            } catch (AddressException e) {
                                throw new RuntimeException(e);
                            }
                        })
                        .toArray(InternetAddress[]::new);
                helper.setCc(ccAdd);
            }
            if (bcc.size() > 0 && bcc != null && !bcc.isEmpty()) {
                InternetAddress[] bccAddresses = bcc.stream()
                        .map(email -> {
                            try {
                                return new InternetAddress(email);
                            } catch (AddressException e) {
                                throw new RuntimeException(e);
                            }
                        })
                        .toArray(InternetAddress[]::new);
                helper.setBcc(bccAddresses);
            }
            helper.setText(html, true);
            helper.addInline("companyLogo", new ClassPathResource("static/logo.jpg"));
            mailSender.send(message);

            logger.info("sent otp mail successfully");
            // return success;
            return true;
        } catch (MessagingException e) {
            System.err.println(e.getMessage());
            logger.error("sent otp mail filed" + e.getMessage());
            // return send mail failed;
            return false;
        }
    }
}
