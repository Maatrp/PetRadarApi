package com.api.petradar.email;

import com.api.petradar.authetication.AuthenticationService;
import com.api.petradar.user.User;
import com.api.petradar.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Properties;

import jakarta.mail.*;
import jakarta.mail.internet.*;

@Service
public class EmailService {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserService userService;

    @Value("${mail.smtp.host}")
    private String host;

    @Value("${mail.smtp.port}")
    private int port;

    @Value("${mail.smtp.user}")
    private String username;

    @Value("${mail.smtp.password}")
    private String password;

    @Value("${mail.smtp.auth}")
    private boolean auth;

    @Value("${mail.smtp.ssl.enable}")
    private boolean sslEnable;


    public Boolean sendEmail(String to) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", this.auth);
        props.put("mail.smtp.ssl.enable", this.sslEnable);
        props.put("mail.smtp.host", this.host);
        props.put("mail.smtp.port", this.port);

        try {
            // Crear sesión de correo
            Session session = Session.getInstance(props,
                    new Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });
            session.setDebug(true);

            // Crear mensaje
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("Verificación de email - PetRadar");
            User user = userService.findUserByEmail(to);
            String body = "<h1>Verificación de Email</h1>"
                    + "<p>Clica en el enlace de a continuación para verificar su correo electrónico</p>"
                    + "<a href=\"https://petradar.click/user/validate-email?email="
                    + to + "&token=" + authenticationService.generateJwt(user) + "\">Verificar Email</a>";
            message.setContent(body, "text/html");

            // Enviar mensaje
            Transport.send(message);


        } catch (MessagingException e) {
            return false;

        }

        return true;
    }
}
