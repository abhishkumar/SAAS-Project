package NCG_Project;

import java.util.Scanner;

public class MainClass {
//	
//
//	Enter the FileDirectory of the Invoice to be checked(refer the above attachment dir)
//	D:/Acushnet.pdf

	public static void main(String[] args) throws Exception {
		System.out.println("****************************************************program started****************************************************");
		Scanner s=new Scanner(System.in); //close this scanner!
		//assigning the userName and the password for the user mail.
		String host = "pop.gmail.com";
		String port = "995";
		System.out.println("Enter your mail Id");
		String userName = "abhishkumar1971998@gmail.com";
		System.out.println("Enter the Password");
		final String password = "LAKSHMI@mom5";
		System.out.println("Enter the vendor's mail Id to check for new mails");
		String vendorName=s.nextLine();
		System.out.println("Enter the subject for the invoice mail");
		String vendorSub=s.nextLine();
		//assigning the file location for the attachments to save.
		System.out.println("Enter the Directory to store the Attachments");
		String saveDirectory = s.nextLine(); //example : "D:/Attachment/"
		//checking for the new mails.
		System.out.println("\nSearching for new mail from '"+vendorName+"' with subject '"+vendorSub+"'....please wait\n");
		MailAttachmentReceiver receiver = new MailAttachmentReceiver(vendorName,vendorSub);
		int checkMail;
		receiver.setSaveDirectory(saveDirectory);
		checkMail=receiver.downloadEmailAttachments(host, port, userName, password);	
		
		if(checkMail==1){
		System.out.println("\nEnter the FileDirectory of the Invoice to be checked(refer the above attachment dir)");
		String FileDirectory;
		FileDirectory=s.nextLine();
		int PageNo=3;
		System.out.println("\nThe file: "+FileDirectory+" is in processing....please wait\n");
		ReadingPdf readingPdf=new ReadingPdf(FileDirectory,PageNo,vendorName);
		readingPdf.GetData();
		}
		
		System.out.println("Do you want to list the invoices, (y/n):");
		String UserCmd;
		UserCmd=s.nextLine();
		if(UserCmd.contains("y"))
		{
			DisplayData displayData=new DisplayData();
			displayData.Display();
		}
		else
		{
			System.out.println("Thank you, for more details contact the developer");
		}
		s.close();
		System.out.println("****************************************************program ended****************************************************");
	}

}
