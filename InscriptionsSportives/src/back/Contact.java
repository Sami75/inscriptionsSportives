package back;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import inscriptions.Personne;

import javax.activation.*;

public class Contact {

	public Contact() {}
	
	public static void sendMail(String mail, String object, String content) {


		final String username = "inscription.sportive@gmail.com";
		final String password = "123456azerty";
		System.out.println(mail);
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.addRecipients(Message.RecipientType.CC,
					InternetAddress.parse(mail));
			message.setSubject(object);
			message.setText(content);

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
