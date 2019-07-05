package NCG_Project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * A Java MySQL DELETE example.
 * Demonstrates the use of a SQL DELETE statement against a
 * MySQL database, called from a Java program, using a
 * Java PreparedStatement.
 * 
 * Created by Alvin Alexander, http://devdaily.com
 */
public class UpdateData
{

	private int InvoiceNum;
	private String vendorName;
  public UpdateData(int InvoiceNum,String vendorName) {
		this.InvoiceNum=InvoiceNum;
		this.vendorName=vendorName;
	}

public void update() {
	  
	  System.out.println("Please wait for a moment...");
    try
    {
    	Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/test1?serverTimezone=UTC", "root","");
    	
      
      
    	
//      String query = "delete from invoicedetails where InvoiceNumber = ?";
//      PreparedStatement preparedStmt = conn.prepareStatement(query);
//      preparedStmt.setString(1, InvoiceNum);
    	
    	String query = "update invoicedetails set Status = ? where InvoiceNumber = ? and Status = ?";
        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.setString(1, "Approved");
        preparedStmt.setInt(2, InvoiceNum);
        preparedStmt.setString(3, "Pending");

        // execute the java preparedstatement
        
        preparedStmt.executeUpdate();
        PreparedStatement preparedstmt = conn.prepareStatement("select * from invoicedetails where InvoiceNumber = ?");
        

		ResultSet rs=preparedstmt.executeQuery();
        

      // execute the preparedstatement
     // preparedStmt.execute();
        if(rs.getString(7).equals("Pending")){
        SendMail sendMail=new SendMail(InvoiceNum,vendorName);
        sendMail.send();}
        else
        	System.out.println("this invoice is already approved");
      
      conn.close();
      
      
      
      
    }
    catch (Exception e)
    {
      System.err.println("Got an exception! ");
      System.err.println(e.getMessage());
      System.out.println("------------------------------------------------program ended------------------------------------------------");
		System.exit(0);
    }
    //System.out.println("done");

  }
}
