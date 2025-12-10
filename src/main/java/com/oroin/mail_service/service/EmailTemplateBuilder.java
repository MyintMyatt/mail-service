package com.oroin.mail_service.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class EmailTemplateBuilder {

    private static final Logger logger = LoggerFactory.getLogger(EmailTemplateBuilder.class);
    private final SpringTemplateEngine engine;

    public String buildEmailTemplateThymleaf(String bodyHtml) {
        Context ctx = new Context();
        ctx.setVariable("body", bodyHtml);

        return engine.process("layout/base-email", ctx);
    }

    public String buildEmailTemplatePure(String bodyHtml) {

        try {
            String template = new String(
                    getClass().getResourceAsStream("/templates/otp-email-template.html").readAllBytes()
            );

            // Replace placeholder with body HTML
            String result = template.replace("{{body}}", bodyHtml);

            logger.info("successfully built mail template");

            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


}
