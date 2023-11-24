package com.eskcti.workercompras.services.emails;

import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;

import com.sendgrid.*;

@Slf4j
public class SendGridSmtpSendEmailService implements SendEmailService{

    private final JavaMailSender mailSender;

    private final EmailProperties emailProperties;

    public SendGridSmtpSendEmailService(JavaMailSender mailSender, EmailProperties emailProperties) {
        this.mailSender = mailSender;
        this.emailProperties = emailProperties;
    }

    @Override
    public void send(Message message) {
        Email from = new Email(emailProperties.getSender());
        String subject = message.getSubject();
        Email to = new Email(message.getReceiver());
        Content content = new Content("text/html", message.getBody());
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(emailProperties.getSendgrid());
        log.info(emailProperties.getSendgrid());
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (Exception ex) {
            log.error("Erro smtp {}", ex);
            throw new EmailException("Não foi possível enviar o email", ex);
        }
    }
}
