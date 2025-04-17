package com.autenticacao.app.transportLayer.sender;

import com.autenticacao.app.domain.constants.MessageError;
import com.autenticacao.app.domain.exception.GeneralErrorException;
import com.autenticacao.app.transportLayer.model.BodySendEmailModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class EmailSender {
    @Value("${smtp.host}")
    private String host;

    @Value("${smtp.port}")
    private String port;

    @Value("${smtp.username}")
    private String username;

    @Value("${smtp.password}")
    private String password;

    @Autowired
    private MessageError error;

    private Session configureSession() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);

        return Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }

    public void send(BodySendEmailModel bodySendEmailModel) {
        try {
            Session session = configureSession();

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(bodySendEmailModel.getEmail()));
            message.setSubject(bodySendEmailModel.getTitle());
            message.setText(bodySendEmailModel.getMessage());

            Transport.send(message);
        }
        catch (Exception e) {
            throw new GeneralErrorException(error.ERROR_SEND_EMAIL);
        }
    }
}
