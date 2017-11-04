package io.github.marcelovca90.helper;

import java.io.File;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MailHelper
{
    private static final Logger LOGGER = LogManager.getLogger(MailHelper.class);

    public enum CryptoProtocol
    {
        SSL,
        TLS
    };

    public static void sendMail(CryptoProtocol protocol, String username, String password, String host, String from, String recipients, String subject, String text, String filename)
    {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", host);

        switch (protocol)
        {
            case SSL:
                props.put("mail.smtp.port", "465");
                props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                props.put("mail.smtp.socketFactory.port", "465");
                break;

            case TLS:
                props.put("mail.smtp.port", "587");
                props.put("mail.smtp.starttls.enable", "true");
                break;
        }

        Authenticator authenticator = buildAuthenticator(username, password);
        Session session = Session.getDefaultInstance(props, authenticator);

        try
        {
            Message message = buildMessage(session, from, recipients, subject, text, filename);
            Transport.send(message);
        }
        catch (MessagingException e)
        {
            LOGGER.error(e);
        }
    }

    private static Authenticator buildAuthenticator(final String username, final String password)
    {
        return new Authenticator()
        {
            @Override
            protected PasswordAuthentication getPasswordAuthentication()
            {
                return new PasswordAuthentication(username, password);
            }
        };
    }

    private static Message buildMessage(Session session, String from, String recipients, String subject, String text, String filename) throws MessagingException, AddressException
    {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipients));
        message.setSubject(subject);

        BodyPart messageTextPart = new MimeBodyPart();
        messageTextPart.setText(text);

        BodyPart messageAttachmentPart = new MimeBodyPart();
        DataSource source = new FileDataSource(new File(filename));
        messageAttachmentPart.setDataHandler(new DataHandler(source));
        messageAttachmentPart.setFileName(filename);

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageTextPart);
        multipart.addBodyPart(messageAttachmentPart);
        message.setContent(multipart);

        return message;
    }
}
