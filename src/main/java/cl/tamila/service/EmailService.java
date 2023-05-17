package cl.tamila.service;

import cl.tamila.modelos.MailModel;
import cl.tamila.modelos.UsuarioModel;
import cl.tamila.utilidades.Constantes;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;
@Service
@Primary
public class EmailService {
    public void sendMail(String mail, String asunto, String mensaje) throws AddressException,MessagingException {
        //Constantes constantes=new Constantes();
        Properties props =new Properties();
        props.put("mail.smtp.auth","true");
        props.put("mail.smtp.starttls.enable","true");
        props.put("mail.smtp.host",Constantes.SMTP_MAIL);
        props.put("mail.smtp.port",Constantes.SMTP_PORT);
        Session session= Session.getInstance(props, new javax.mail.Authenticator(){//Configuracion ya definida de nuestra variable props
            //Creando atributo con la siguiente configuracion
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(Constantes.SMTP, Constantes.SMTP_PASSWORD);
            }
        });
        Message msg = new MimeMessage(session);
        //Generamos el envio desde el servidor
        msg.setFrom(new InternetAddress(Constantes.SMTP,false));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail));
        msg.setSubject(asunto);
        msg.setContent(mensaje,"text/html");
        msg.setSentDate(new Date(0));

        MimeBodyPart mesageBodyPart = new MimeBodyPart();
        mesageBodyPart.setContent(mensaje,"text/html");
        Transport.send(msg);
    }
}
