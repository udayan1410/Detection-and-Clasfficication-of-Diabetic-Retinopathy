import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.*;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
public class Loading {

	my_thread thread1;
	
	JFrame f1;
	  JPanel p1;
	  JLabel l1,l2,l3,l4;
	  JTextField t1,t2;
	  JButton b1;
	  
	  Loading()
	  {
		  thread1=new my_thread();
		  f1 =  new JFrame("Upload");
		  f1.setExtendedState(JFrame.MAXIMIZED_BOTH);
		  f1.setLayout(null);
		  f1.getContentPane().setBackground(new Color(255,255,255));
		  f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		  
		  p1=new JPanel();
		  p1.setLayout(null);
		  p1.setBounds(0, 0, 1400, 150);
		  p1.setBackground(new Color(0,87,75));
		  
		  f1.add(p1);
		  f1.setVisible(true);
		  thread1.start();
	  }
	  
    class my_thread extends Thread
    {
    	String result;
    	public void run()
    	{
    		try{
    		String pypath = "F:\\java_sanket_server.py";
    		// String pypath = "F:\\BE Project\\GUI\\hello\\hello.py";
    		 //String pypath = "F:\\hello.py";
			    String[] cmd = new String[2];
			    cmd[0] = "F:\\Python 36\\python.exe";
			    cmd[1] = pypath;
			    String fpath="F:\\result.txt";
			    
			    //String fpath="F:\\BE Project\\GUI\\hello\\hello.txt";
			    try { 
		            BufferedWriter out = new BufferedWriter( 
		                          new FileWriter(fpath)); 
		            out.write("Hello"); 
		            
		            
		            out.close(); 
		           
		        } 
		        catch (IOException e) { 
		            System.out.println("Exception Occurred" + e); 
		        } 

			    Runtime rt = Runtime.getRuntime();
			    Process pr =  rt.exec(cmd);
			    
			    
			    
			    
			  
			    BufferedReader br;
			    br=new BufferedReader(new InputStreamReader(new FileInputStream(fpath)));
			    System.out.println("BR="+br.readLine());
			    br=new BufferedReader(new InputStreamReader(new FileInputStream(fpath)));
			    
			    while(br.readLine().equalsIgnoreCase("Hello"))
			    {
			    	Thread.sleep(2000);
			    	System.out.println("1");
			    	br=new BufferedReader(new InputStreamReader(new FileInputStream(fpath)));
			    }
			    br=new BufferedReader(new InputStreamReader(new FileInputStream(fpath)));
			    
			    System.out.println("BR= "+br);
			    
			    String line="";
			    
			    while((line=br.readLine())!=null)
			    {
			    	result=line;
			    }
			    System.out.println("Result="+result);
    		}
    		catch(Exception ex)
    		{
    			ex.printStackTrace();
    		}
    	}
    }
	  
	  public static void main(String args[])
	  {
		  new Loading();
	  }
}

