import java.sql.*;
public class Db_connect {


	  private Connection con;
	  private Statement st;
	  private ResultSet rs;
	  
	  public Db_connect()
	  {
		  try{
			  Class.forName("com.mysql.jdbc.Driver");
			  con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dr_project","root","");
			  st = con.createStatement();
			  
			  
		  }
		  catch(Exception ex){
			  System.out.println("Error: "+ex);
			  
		  }
	  }
	 
}
 