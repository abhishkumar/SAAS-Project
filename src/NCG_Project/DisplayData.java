package NCG_Project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class DisplayData {
	private int count;
	private String userName;
	public DisplayData() {
		
		// TODO Auto-generated constructor stub
	}
	public void Display() {
		//PreparedStatement pStatement = null;
		try {
			// Class.forName("com.mysql.jdbc.Driver").newInstance();

			// serverhost = localhost, port=3306, username=root, password=123
			Connection cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test1?serverTimezone=UTC", "root",
					"");
			//pStatement = cn.prepareStatement("select * from invoicedetails ");
			//pStatement.setString(1, "Pending");
			//Statement smt = cn.createStatement();

			Statement smt=cn.createStatement();

			ResultSet rs=smt.executeQuery("select * from invoicedetails");
			//ResultSet rs = pStatement.executeQuery();
			// to execute query
			//ResultSet rs = smt.executeQuery(q);
			

			count=0;
			// to print the resultset on console
			if (rs.next()) {
				System.out.println("----------------------------------------------------------------------------------------------------------------------------------");
				System.out.println("Invoice ID"+"  "+"Status"+"    "+"Invoice Number"+"  "+
						"Invoice Date"+"  "+"Customer PO"+"  "+"Amount"+"    "+"Address");
							System.out.println("----------------------------------------------------------------------------------------------------------------------------------");
							System.out.println();

				do {
					
					System.out.print((count+1)+"           ");
					System.out.print(rs.getString(6)+"  ");
					System.out.print("  "+rs.getString(1)+"       ");
					System.out.print(rs.getString(2)+"      ");
					System.out.print(rs.getString(3)+"   ");
					System.out.print(rs.getString(5)+"   ");
					System.out.println(rs.getString(4));
					System.out.println();
					System.out.println("----------------------------------------------------------------------------------------------------------------------------------");
//					System.out.println("Invoice ID: \n"+(count+1)+"\n");
//					System.out.println("Invoice Number: \n" + rs.getString(1));
//					System.out.println("Invoice Date: \n" + rs.getString(2));
//					System.out.println("Customer PO: \n" + rs.getString(3));
//					System.out.println("Address: \n" + rs.getString(4));
//					System.out.println("Amount: \n" + rs.getString(5));
//					System.out.println("Status: \n" + rs.getString(6));					
//					System.out.println("---------------------------------------");
					count++;
				} while (rs.next());
			} else {
				System.out.println("Record Not Found...");
				System.out.println("------------------------------------------------program ended------------------------------------------------");
				System.exit(0);
			}
			
			System.out.println();
			System.out.println("you can approve any invoice now");
			System.out.println("Enter the Invoice ID to approve:");
			Scanner s=new Scanner(System.in);
			int UserCmd;
			UserCmd=s.nextInt();
			if(UserCmd<=count){
			ResultSet rtemp = smt.executeQuery("select * from invoicedetails");;
			for(int i=0;i<UserCmd;i++){
			rtemp.next();
			}
			int dtemp=Integer.parseInt(rtemp.getString(1));
			UpdateData updateData = new UpdateData(dtemp,rtemp.getString(7));
				
			updateData.update();
			count--;
			if(count>=1)
			{
				System.out.println("\n\n\n*********Still some invoice are left to approve*********");
				Display();
				
			}
			}
			else{
				System.out.println("The ID entered is not found");
				System.out.println(
						"------------------------------------------------program ended------------------------------------------------");
				System.exit(0);
			}
			cn.close();
			s.close();
		} catch (Exception e) {
			System.out.println(e);
			System.out.println(
					"------------------------------------------------program ended------------------------------------------------");
			System.exit(0);
		}

	}
}
