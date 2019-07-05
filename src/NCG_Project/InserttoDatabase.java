package NCG_Project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class InserttoDatabase {
	private int InvoiceNum;
	private String InvoiceDate;
	private int CustomerPO;
	private String Address;
	private double Amount;
	private String vendorName;
	public InserttoDatabase(int InvoiceNum, String InvoiceDate, int CustomerPO, String Address, double Amount,String vendorName) {
		this.InvoiceNum=InvoiceNum;
		this.InvoiceDate=InvoiceDate;
		this.CustomerPO=CustomerPO;
		this.Address=Address;
		this.Amount=Amount;
		this.vendorName=vendorName;
		
	}
	public void insert(){
		
		try{  
			//Class.forName("oracle.jdbc.driver.OracleDriver");  
			  
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/test1?serverTimezone=UTC", "root","");  
			  
			PreparedStatement stmt=con.prepareStatement("insert into invoicedetails values(?,?,?,?,?,?,?)");  
			
			stmt.setInt(1,InvoiceNum);
			stmt.setString(2,InvoiceDate);
			stmt.setInt(3,CustomerPO);
			stmt.setString(4,Address);
			stmt.setDouble(5,Amount);
			stmt.setString(6,"Pending");
			stmt.setString(7, vendorName);
			
			
			  
			int i=stmt.executeUpdate();  
			System.out.println(i+" records inserted");  
			  
			con.close();  
			  
			}catch(Exception e){ System.out.println(e);
			} 
		//finally{System.out.println("updated");}
		
	}
}