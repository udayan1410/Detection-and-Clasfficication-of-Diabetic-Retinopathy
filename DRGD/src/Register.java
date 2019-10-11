import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import com.toedter.calendar.JDateChooser;


import javax.swing.*;

import com.sun.glass.events.KeyEvent;
import com.sun.org.apache.xerces.internal.impl.xs.identity.Selector.Matcher;
import com.sun.xml.internal.fastinfoset.sax.Properties;



public class Register {
	
	 private Connection con;
	  private Statement st;
	  private ResultSet rs;
	
	JFrame f1;
	JPanel p1,p2,p3;
	JLabel l1,l2,l3,l4,l5,l6,l7,l8,l9,l10,l11,l12,l13,l14,l15,l16,l17,l18,l19,l20,l21,l22,l23,l24,l25;
	JButton b1,b2,b3,b4;
	JTextField t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11,t12,t13,t14;
	JRadioButton j1,j2,j3,j4;
	JDateChooser dc;
	String nm,age,sex,dr,bg,h,w,dob,fileName;
	
	
	
	Register()
	{
		try{
			  Class.forName("com.mysql.jdbc.Driver");
			  con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dr_project","root","");
			  st = con.createStatement();
			  
			  
		  }
		  catch(Exception ex){
			  System.out.println("Error: "+ex);
			  
		  }
		
		
		int c1,c2;
		c1=0;
		c2=0;
		
	   f1=new JFrame("Paitent Details");
	   f1.setExtendedState(JFrame.MAXIMIZED_BOTH);
	   f1.setLayout(null);
	   f1.getContentPane().setBackground(new Color(255,255,255));
	   f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	   ImageIcon img1= new ImageIcon("F:\\BE Project\\images\\back.png");
	   ImageIcon img2= new ImageIcon("F:\\BE Project\\images\\g1.png");
	   
	   l18 = new JLabel("Paitent Details");
		l18.setBounds(220,25,980,100);
		l18.setFont(new Font("Times New Roman",Font.BOLD,50));
		l18.setForeground(new Color(255,255,255));
	   
	   p1=new JPanel();
		p1.setLayout(null);
		p1.setBounds(0, 0, 1400, 150);
		p1.setBackground(new Color(0,87,75));
	   
		
		
	 
	   
	   l2=new JLabel("First Name:");
	   l2.setBounds(180,200,150,30);
	   l2.setFont(new Font("Times New Roman",Font.PLAIN,20));
	   l2.setForeground(new java.awt.Color(0,87,75));
	   
	   l20=new JLabel("Please Enter Valid Name");
	   l20.setBounds(560,200,250,30);
	   l20.setFont(new Font("Times New Roman",Font.PLAIN,15));
	   l20.setForeground(new java.awt.Color(207, 0, 15));
	   
	 
	   l3=new JLabel("Last Name:");
	   l3.setBounds(180,260,150,30);
	   l3.setFont(new Font("Times New Roman",Font.PLAIN,20));
	   l3.setForeground(new java.awt.Color(0,87,75));

	   l21=new JLabel("Please Enter Valid Name");
	   l21.setBounds(560,260,250,30);
	   l21.setFont(new Font("Times New Roman",Font.PLAIN,15));
	   l21.setForeground(new java.awt.Color(207, 0, 15));

	   
	   
	   
	   
 
	   l6=new JLabel("E-mail:");
	   l6.setBounds(180,320,150,30);
	   l6.setFont(new Font("Times New Roman",Font.PLAIN,20));
	   l6.setForeground(new java.awt.Color(0,87,75));
	   
	   l23=new JLabel("Please Enter Valid Email:");
	   l23.setBounds(560,320,250,30);
	   l23.setFont(new Font("Times New Roman",Font.PLAIN,15));
	   l23.setForeground(new java.awt.Color(207, 0, 15));
	   
	   l1 = new JLabel("Gender:");
	   l1.setBounds(180,380,150,30);
	   l1.setFont(new Font("Times New Roman",Font.PLAIN,20));
	   l1.setForeground(new java.awt.Color(0,87,75));
	   
	   l4=new JLabel("Diabetic Patient?:");
	   l4.setBounds(180,440,250,30);
	   l4.setFont(new Font("Times New Roman",Font.PLAIN,20));
	   l4.setForeground(new java.awt.Color(0,87,75));
	   
	   l5=new JLabel("Blood Group:");
	   l5.setBounds(180,500,250,30);
	   l5.setFont(new Font("Times New Roman",Font.PLAIN,20));
	   l5.setForeground(new java.awt.Color(0,87,75));
	   
	   l22=new JLabel("Please Enter Valid Blood Group");
	   l22.setBounds(560,500,250,30);
	   l22.setFont(new Font("Times New Roman",Font.PLAIN,15));
	   l22.setForeground(new java.awt.Color(207, 0, 15));
	  

		l25 = new JLabel("");
		l25.setBounds(1150,150,180,100);
		l25.setIcon(img2);
	   
	  
	   
	   
	   
		
	  
	   
	
	   
	   //Name
	   t1=new JTextField(40);
	   t1.setBounds(370,200,180,30);
	   t1.setBackground(new Color(255,255,255));
	   t1.setForeground(new Color(0,0,0));
	
	   //Age
	   t2=new JTextField(20);
	   t2.setBounds(370,260,180,30);
	   t2.setBackground(new Color(255,255,255));
	   t2.setForeground(new Color(0,0,0));
	   
	   //Email
	   t3=new JTextField(20);
	   t3.setBounds(370,320,180,30);
	   t3.setBackground(new Color(255,255,255));
	   t3.setForeground(new Color(0,0,0));
	   
	   //Male
	    j3 = new JRadioButton();
	   j3.setBounds(370,390,70,15);
	   j3.setBackground(new Color(255,255,255));
	   j3.setText("Male");
	   j3.setForeground(new Color(0,0,0));
	   j3.setFont(new Font("Times New Roman",Font.PLAIN,15));
	   
	   //Female
	   j4 = new JRadioButton();
	   j4.setBounds(450,390,110,15);
	   j4.setBackground(new Color(255,255,255));
	   j4.setText("Female");
	   j4.setForeground(new Color(0,0,0));
	   j4.setFont(new Font("Times New Roman",Font.PLAIN,15));
	   
	   ButtonGroup group1 = new ButtonGroup();
	   group1.add(j3);
	   group1.add(j4);
	   
	   //Yes
	    j1 = new JRadioButton();
	   j1.setBounds(370,450,70,15);
	   j1.setBackground(new Color(255,255,255));
	   j1.setText("YES");
	   j1.setForeground(new Color(0,0,0));
	   j1.setFont(new Font("Times New Roman",Font.PLAIN,15));
	   
	   //No
	    j2 = new JRadioButton();
	   j2.setBounds(450,450,110,15);
	   j2.setBackground(new Color(255,255,255));
	   j2.setText("NO");
	   j2.setForeground(new Color(0,0,0));
	   j2.setFont(new Font("Times New Roman",Font.PLAIN,15));
	   
	   ButtonGroup group = new ButtonGroup();
	   group.add(j1);
	   group.add(j2);
	   
	   t4=new JTextField(20);
	   t4.setBounds(370,500,180,30);
	   t4.setBackground(new Color(255,255,255));
	   t4.setForeground(new Color(0,0,0));
	   
	   
	   
	   
	
	 
	   
	   b1=new JButton("Next");
	   b1.setBounds(180,580,100,30);
	   b1.setBackground(new Color(0,87,75));
	   b1.setForeground(new Color(255,255,255));
	   b1.setFont(new Font("Times New Roman",Font.PLAIN,20));
	  
	   b1.addMouseListener(new java.awt.event.MouseAdapter() {
           public void mouseClicked(java.awt.event.MouseEvent evt) {
               b1MouseClicked(evt);
           }
       });
	   
	   f1.add(p1);
	   p1.add(l18);
	  
	   
	   f1.add(l2);
	   f1.add(l3);
	   f1.add(l1);
	   f1.add(l4);
	   f1.add(l5);
	   f1.add(l6);
	   f1.add(t3);
	  
	   f1.add(t1);
	   f1.add(t2);
	   f1.add(j3);
	   f1.add(j4); 
	   f1.add(j1);
	   f1.add(j2);
	   f1.add(t4);
	   
	   
	   f1.add(b1);
	   f1.add(l20);
	   f1.add(l21);
	   f1.add(l22);
	   f1.add(l23);
	   f1.add(l25);
	   
	   l20.setVisible(false);
	   l21.setVisible(false);
	   l22.setVisible(false);
	   l23.setVisible(false);
	   
	   
	  
	   
	   
	   
	   
	   f1.setVisible(true);
	   
	   
	   
	   
		
	}
	
	public void actionPerformed(ActionEvent e) {
        if(e.getSource()==t1) {
            try {
                String uFName = t1.getText().toString();

                if(!uFName.matches("[A-Za-z]+"))
                    throw new Exception();
            }
            catch(Exception e1) {
                t1.setText("");
                JOptionPane.showMessageDialog(t1,"Please enter a valid name!");
            }
        }
    }

	public int validate()
	{
		l20.setVisible(false);
		   l21.setVisible(false);
		   l22.setVisible(false);
		   l23.setVisible(false);
		  
		String in1,in2,in3,in4,in5;
		in1=t1.getText();
		int len,a1,count;
		count=0;
		len=in1.length();
		int i,j;
		char a;
		
		//Name 
		for(i=0;i<len;i++)
		{
			a=in1.charAt(i);
		    a1=(int)a;
		    if((a1>=65 && a1<=90) || (a1>=97 && a1<=122) || a1==32 )
		    {
		    	
		    }
		    else
		    {
		    	t1.setText("");
		    	l20.setVisible(true);
		    	count++;
		    }
		}
		//Last name
		in2=t2.getText();
		len=in2.length();
		
		for(i=0;i<len;i++)
		{
			a=in2.charAt(i);
		    a1=(int)a;
		    if((a1>=65 && a1<=90) || (a1>=97 && a1<=122) || a1==32)
		    {
		    	
		    }
		    else
		    {
		    	t2.setText("");
		    	l21.setVisible(true);
		    	count++;
		    }
		}
		
		in4=t3.getText();
		len=in4.length();
		
		if(in4.indexOf("@")>0 && in4.indexOf(".")>0)
		{
			
		}
		else
		{
			t3.setText("");
			l23.setVisible(true);
			count++;
		}
		
		//blood
		in3=t4.getText();
		len=in3.length();
		
		if(len>3)
		{
			t4.setText("");
	    	l22.setVisible(true);
	    	count++;
		}
		else
		{
		for(i=0;i<len;i++)
		{
			a=in3.charAt(i);
		    a1=(int)a;
		    if(a1==65 || a1==66 || a1==79 || a1==43 )
		    {
		    	
		    }
		    else
		    {
		    	t2.setText("");
		    	l22.setVisible(true);
		    	count++;
		    }
		}
		}
		
			
		return count;
		
	}
	

	  public static void appendStrToFile(String fileName, 
              String str) 
{ 
try { 

 
BufferedWriter out = new BufferedWriter( 
new FileWriter(fileName, true)); 
out.write(str);
out.write("\n");
out.close(); 
} 
catch (IOException e) { 
System.out.println("exception occoured" + e); 
} 
} 
	
	private void l19MouseClicked(java.awt.event.MouseEvent evt) {   
		 f1.setVisible(false);
		 new Welcome();
		   
	    }
	
	 private void b1MouseClicked(java.awt.event.MouseEvent evt) { 
		
		 
		 int g;
		 int id = 0;
		 Date date =  Calendar.getInstance().getTime();
		 SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy");
		 String dt;
		 dt=sdf.format(date);
		 if(j3.isSelected()){
				sex=j3.getText();
			}
			else if(j4.isSelected())
			{
				sex=j4.getText();
			}
			
			if(j1.isSelected()){
				dr=j1.getText();
			}
			else if(j2.isSelected())
			{
				dr=j2.getText();
			}
			
			bg = t4.getText();
		
		 
		g=validate();
		if(g==0)
		{
		//write();
		 
			try{
				String query= "insert into login(first_name,last_name,email_id,left_eye,right_eye,left_result,right_result,date,gender,dr,blood_group) values('"+t1.getText()+"','"+t2.getText()+"','"+t3.getText()+"','','','','','"+dt+"','"+sex+"','"+dr+"','"+bg+"')";
				st.executeUpdate(query);
				query="select id from login where first_name='"+t1.getText()+"' and email_id='"+t3.getText()+"'";
				rs = st.executeQuery(query);
				
				while(rs.next())
				{
					id=rs.getInt(1);
					
				}
				System.out.println("ID="+id);
			
 			}
			catch(Exception ex)
			{
				System.out.println("Error: "+ex);
			}
			
			fileName="F:\\idfile.txt";
			try { 
	            BufferedWriter out = new BufferedWriter( 
	                          new FileWriter(fileName)); 
	            out.write(""+id); 
	            out.write("\n");
	            out.close(); 
	        } 
	        catch (IOException e) { 
	            System.out.println("Exception Occurred" + e); 
	        } 
			
			
			
			
	     f1.setVisible(false);
	     new Upload_left(fileName);
		}
		else
		{
			f1.setVisible(true);
		}
		 
	    }
	 
	 public static void main(String args[])
	 {
		 new Register();
	 }

}
