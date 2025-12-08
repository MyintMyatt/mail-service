package com.oroin.mail_service.grpc;

import com.oroin.mail_service.service.EmailSenderService;
import com.oroin.mail_service.service.EmailTemplateBuilder;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.springframework.grpc.server.service.GrpcService;
import orion.grpc.mail.EmailRequest;
import orion.grpc.mail.EmailResponse;
import orion.grpc.mail.EmailServiceGrpc;

@GrpcService
@RequiredArgsConstructor
public class GrpcEmailService extends EmailServiceGrpc.EmailServiceImplBase {

    private final EmailTemplateBuilder templateBuilder;
    private final EmailSenderService emailSenderService;

    @Override
    public void sendEmail(EmailRequest request, StreamObserver<EmailResponse> responseObserver) {

        /*build email template*/
        templateBuilder.buildEmailTemplate(request.getEmailBodyHtml());

        /*send mail*/
//        emailSenderService.send(request.getReceivers())
    }

}
