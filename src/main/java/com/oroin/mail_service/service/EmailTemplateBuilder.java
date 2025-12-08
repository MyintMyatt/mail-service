package com.oroin.mail_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
@RequiredArgsConstructor
public class EmailTemplateBuilder {

    private final SpringTemplateEngine engine;

    public String buildEmailTemplate(String bodyHtml){
        Context ctx = new Context();
        ctx.setVariable("body", bodyHtml);

        return engine.process("layout/base-email", ctx);
    }
}
