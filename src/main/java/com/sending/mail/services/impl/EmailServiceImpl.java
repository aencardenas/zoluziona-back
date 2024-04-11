package com.sending.mail.services.impl;

import com.sending.mail.services.IEmailService;
import com.sending.mail.services.models.EmailDTO;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailServiceImpl implements IEmailService {
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    public EmailServiceImpl(JavaMailSender javaMailSender, TemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    @Override
    public void sendMail(EmailDTO email) throws MessagingException {

        try {

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo("aencardenas@gmail.com");
            helper.setCc("mariana.ag3299@gmail.com");
            helper.setSubject("Consulta de baterías Zoluziona");

            Context context = new Context();
            context.setVariable("message", email.getMessage());
            context.setVariable("name", email.getName());
            context.setVariable("lastName", email.getLastName());
            context.setVariable("email", email.getEmail());
            context.setVariable("phoneNumber", email.getPhoneNumber());

            String contentHtml = templateEngine.process("email", context);

            helper.setText(contentHtml, true);
            javaMailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Erro al enviar el correo: " + e.getMessage(),e);
        }


    }
}
