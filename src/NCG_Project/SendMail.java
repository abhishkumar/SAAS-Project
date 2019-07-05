package NCG_Project;

import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SendMail {
	private int InvoiceNum;
	private String userName;

	public SendMail(int InvoiceNum,String userName) {
		this.InvoiceNum = InvoiceNum;
		this.userName=userName;
	}

	public void send() {
		// System.out.println("welcome");

		final String username = "abhishkumar1971998@gmail.com"; // enter your
																// mail id
		final String password = "LAKSHMI@mom5";// enter ur password

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("abhishkumar1971998@gmail.com")); // same
																					// email
																					// id
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(userName));// whome
																													// u
																													// have
																													// to
																													// send
																													// mails
																													// that
																													// person
																													// id
			message.setSubject("Invoice Approved");
			// Create the message part
			BodyPart messageBodyPart = new MimeBodyPart();

			// Now set the actual message
			messageBodyPart.setText("Your Invoice, Invoice number: " + InvoiceNum + " is approved"
					+ "\nFor futher updates contact ERP Altimetrik");

			// Create a multipar message
			Multipart multipart = new MimeMultipart();

			// Set text message part
			multipart.addBodyPart(messageBodyPart);

			// Part two is attachment
			// messageBodyPart = new MimeBodyPart();
			// String filename = "D:/sonoo.jpg";
			// DataSource source = new FileDataSource(filename);
			// messageBodyPart.setDataHandler(new DataHandler(source));
			// messageBodyPart.setFileName(filename);
			// multipart.addBodyPart(messageBodyPart);
			//
			// // Send the complete message parts
			message.setContent(multipart);

			Transport.send(message);

			System.out.println("\n You approved the Invoice : " + InvoiceNum + "A conformation Mail is send to '"+userName+"'");

		} catch (MessagingException ex) {
			System.out.println("Could not connect to the message store");
			ex.printStackTrace();
			System.out.println("------------------------------------------------program ended------------------------------------------------");
			System.exit(0);
		}
	}
}
