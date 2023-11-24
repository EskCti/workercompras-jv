package com.eskcti.workercompras.services.emails;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Slf4j
public class GmailSmtpSendEmailService implements SendEmailService{
    private final JavaMailSender mailSender;

    private final EmailProperties emailProperties;

    public GmailSmtpSendEmailService(JavaMailSender mailSender, EmailProperties emailProperties) {
        this.mailSender = mailSender;
        this.emailProperties = emailProperties;
    }

    @Override
    public void send(Message message) {
        try {
            var msg = new SimpleMailMessage();
            msg.setTo(message.getReceiver());
            msg.setSubject(message.getSubject());
            msg.setText(message.getBody());
            mailSender.send(msg);
            log.info("Cliente notificado com sucesso!!");
        } catch (Exception ex) {
            log.error("Erro smtp {}", ex);
            throw new EmailException("Não foi possível enviar o email", ex);
        }
    }
}
