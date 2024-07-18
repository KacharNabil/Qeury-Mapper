package com.query_mapper.auth_service.email;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.bouncycastle.util.encoders.UTF8;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static java.nio.charset.StandardCharsets.*;
import static org.springframework.mail.javamail.MimeMessageHelper.MULTIPART_MODE_MIXED;



@Service
@RequiredArgsConstructor
public class EmailService {


    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;


    @Async
    public void sendEmail(
            String to,
            String username,
            EmailTemplateName emailTemplateName,
            String confirmationUrl,
            String activationCode,
            String Subject
    ) throws MessagingException {
        String templateName;
        if (emailTemplateName == null) {
            templateName = "confirm_email";
        }else {
            templateName = emailTemplateName.name();
        }
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(
                mimeMessage,
                MULTIPART_MODE_MIXED,
                UTF_8.name()
        );
        Map<String, Object> prop = new HashMap<>();
        prop.put("username", username);
        prop.put("confirmationUrl", confirmationUrl);
        prop.put("activationCode", activationCode);

        Context context = new Context();
        context.setVariables(prop);

        helper.setFrom("contat@mapper.com");
        helper.setTo(to);
        helper.setSubject(Subject);

        String  template = templateEngine.process(templateName, context);

        helper.setText(template, true);

        mailSender.send(mimeMessage);



    }

}
