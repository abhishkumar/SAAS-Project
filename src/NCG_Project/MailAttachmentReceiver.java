package NCG_Project;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeBodyPart;
import javax.mail.search.SearchTerm;

public class MailAttachmentReceiver {
	// Defining private variables.
	private String saveDirectory;
	private int temp = 0;
	private int count = 0;
	private String vendorName;
	private String vendorSub;

	// constructor
	public MailAttachmentReceiver(String vendorName, String vendorSub) {
		this.vendorName = vendorName;
		this.vendorSub = vendorSub;
	}

	// Function to set save Directory
	public void setSaveDirectory(String dir) {
		this.saveDirectory = dir;
	}

	// Checking for new attachments.
	public int downloadEmailAttachments(String host, String port, String userName, String password) {
		Properties properties = new Properties();
		properties.put("mail.pop3.host", host);
		properties.put("mail.pop3.port", port);
		properties.setProperty("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		properties.setProperty("mail.pop3.socketFactory.fallback", "false");
		properties.setProperty("mail.pop3.socketFactory.port", String.valueOf(port));
		Session session = Session.getDefaultInstance(properties);
		try {
			Store store = session.getStore("pop3");
			store.connect(userName, password);
			Folder folderInbox = store.getFolder("INBOX");
			folderInbox.open(Folder.READ_ONLY);
			SearchTerm searchCondition = new SearchTerm() {
				private static final long serialVersionUID = 1L;

				@Override
				public boolean match(Message message) {
					try {
						if (message.getSubject().contains(vendorSub)) {
							return true;
						}
					} catch (MessagingException ex) {
						ex.printStackTrace();
					}
					return false;
				}
			};
			Message[] arrayMessages = folderInbox.search(searchCondition);
			for (int i = 0; i < arrayMessages.length; i++) {
				Message message = arrayMessages[i];
				Address[] fromAddress = message.getFrom();
				String from = fromAddress[0].toString();
				String contentType = message.getContentType();
				String subject=message.getSubject();
				if (from.contains(vendorName)) {
					String attachFiles = "";

					if (contentType.contains("multipart")) {
						// content may contain attachments
						Multipart multiPart = (Multipart) message.getContent();
						int numberOfParts = multiPart.getCount();
						for (int partCount = 0; partCount < numberOfParts; partCount++) {
							MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(partCount);
							if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
								// this part is attachment
								String fileName = part.getFileName();
								attachFiles += saveDirectory + fileName + " , ";
								part.saveFile(saveDirectory + File.separator + fileName);
							} else {
							}
						}

						if (attachFiles.length() > 1) {
							attachFiles = attachFiles.substring(0, attachFiles.length() - 2);
						}
					} else if (contentType.contains("text/plain") || contentType.contains("text/html")) {
						Object content = message.getContent();
						if (content != null) {
						}
					}
					count++;
					temp = temp + attachFiles.length();
					System.out.println("Message :");
					System.out.println(from);
					System.out.println("\t Subject: " + subject);
					System.out.println("\t Attachments: " + attachFiles);
				}
			}

			// disconnect
			folderInbox.close(false);
			store.close();
		} catch (NoSuchProviderException ex) {
			System.out.println("No provider for pop3.");
			ex.printStackTrace();
			
		} catch (MessagingException ex) {
			System.out.println("Could not connect to the message store");
			ex.printStackTrace();
			
		} catch (IOException ex) {
			ex.printStackTrace();
			
		} finally {
			if (count > 0) {
				if (temp == 0) {
					System.out.println("\n*No attachments found*");
					return 0;
				}

			} else {
				System.out.println("\n*The mail is not found*");
				return 0;
			}
			
		}
		return 1;
	}

	/**
	 * Runs this program with Gmail POP3 server
	 */
}