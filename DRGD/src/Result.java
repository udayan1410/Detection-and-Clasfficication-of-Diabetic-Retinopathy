import java.awt.Color;
import java.awt.Font;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

import javax.imageio.ImageIO;
import javax.swing.*;

import com.sun.javafx.collections.MappingChange.Map;
import com.sun.javafx.tk.Toolkit;
import com.sun.prism.Graphics;
import com.sun.prism.Image;

public class Result {
	
	JFrame f1;
	JPanel p1,p2;
	JLabel l1,l2,l3,l4,l5,l6,l7,l8,l9,l10,l11,l12,l13,l14,l15,l16,l17,l18,l19,l20,l21,l22,l23,l24,l25,l26,l27,l28,l29,l30,l31;
	private JFileChooser fileChooser;
	String fname,path;
	
	Result(String f) throws IOException
	{
		fname=f;
		
		fileChooser = new JFileChooser();
		  f1 =  new JFrame("Upload");
		  f1.setExtendedState(JFrame.MAXIMIZED_BOTH);
		  f1.setLayout(null);
		  f1.getContentPane().setBackground(new Color(44,62,80));
		  f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		  ImageIcon img1= new ImageIcon("F:\\BE Project\\images\\back.png");
		  
		  l1 = new JLabel("Report");
		  l1.setBounds(220,25,980,100);
		  l1.setFont(new Font("Cooper Black",Font.BOLD,50));
		  
		  p1=new JPanel();
		  p1.setLayout(null);
		  p1.setBounds(0, 0, 1400, 150);
		  p1.setBackground(new Color(248,148,6));
		  
		  
			
		  l2 = new JLabel("<HTML><U>Patient Details :</U></HTML>");
		  l2.setBounds(70,140,300,80);
		  l2.setFont(new Font("Cooper Black",Font.PLAIN,25));
		  l2.setForeground(new java.awt.Color(255, 255, 255));
		  
		  
		  
		  
		  
		  l3 = new JLabel("");
		  l3.setBounds(0,35,130,70);
		  l3.setIcon(img1);	
		  l3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		  l3.addMouseListener(new java.awt.event.MouseAdapter() {
	            public void mouseClicked(java.awt.event.MouseEvent evt) {
	                l3MouseClicked(evt);
	            }
	        });
		  
		   l4=new JLabel("Name :");
		   l4.setBounds(70,220,150,30);
		   l4.setFont(new Font("Cooper Black",Font.PLAIN,20));
		   l4.setForeground(new java.awt.Color(191, 191, 191));
		   
		   l5=new JLabel("");
		   l5.setBounds(150,221,250,30);
		   l5.setFont(new Font("Cooper Black",Font.PLAIN,20));
		   l5.setForeground(new java.awt.Color(0,0,0));
		   
		   l6=new JLabel("Age :");
		   l6.setBounds(410,220,70,30);
		   l6.setFont(new Font("Cooper Black",Font.PLAIN,20));
		   l6.setForeground(new java.awt.Color(191,191,191));
		   
		   l7=new JLabel("");
		   l7.setBounds(470,221,80,30);
		   l7.setFont(new Font("Cooper Black",Font.PLAIN,20));
		   l7.setForeground(new java.awt.Color(0,0,0));
		   
		   l8=new JLabel("Sex :");
		   l8.setBounds(70,280,50,30);
		   l8.setFont(new Font("Cooper Black",Font.PLAIN,20));
		   l8.setForeground(new java.awt.Color(191,191,191));
		   
		   l9=new JLabel("");
		   l9.setBounds(125,281,50,30);
		   l9.setFont(new Font("Cooper Black",Font.PLAIN,20));
		   l9.setForeground(new java.awt.Color(0,0,0));
		   
		   l10=new JLabel("Diabetic Patient :");
		   l10.setBounds(280,280,200,30);
		   l10.setFont(new Font("Cooper Black",Font.PLAIN,20));
		   l10.setForeground(new java.awt.Color(191,191,191));
		   
		   l11=new JLabel("");
		   l11.setBounds(460,281,50,30);
		   l11.setFont(new Font("Cooper Black",Font.PLAIN,20));
		   l11.setForeground(new java.awt.Color(0,255,0));
		   
		   l12=new JLabel("Blood Group :");
		   l12.setBounds(70,340,150,30);
		   l12.setFont(new Font("Cooper Black",Font.PLAIN,20));
		   l12.setForeground(new java.awt.Color(191,191,191));
		   
		   l13=new JLabel("");
		   l13.setBounds(220,341,50,30);
		   l13.setFont(new Font("Cooper Black",Font.PLAIN,20));
		   l13.setForeground(new java.awt.Color(0,0,0));
		   
		   l14=new JLabel("Height :");
		   l14.setBounds(330,340,90,30);
		   l14.setFont(new Font("Cooper Black",Font.PLAIN,20));
		   l14.setForeground(new java.awt.Color(191,191,191));
		   
		   l15=new JLabel("");
		   l15.setBounds(420,341,80,30);
		   l15.setFont(new Font("Cooper Black",Font.PLAIN,20));
		   l15.setForeground(new java.awt.Color(0,0,0));
		   
		   l16=new JLabel("Weight :");
		   l16.setBounds(70,400,90,30);
		   l16.setFont(new Font("Cooper Black",Font.PLAIN,20));
		   l16.setForeground(new java.awt.Color(191,191,191));
		   
		   l17=new JLabel("");
		   l17.setBounds(165,401,80,30);
		   l17.setFont(new Font("Cooper Black",Font.PLAIN,20));
		   l17.setForeground(new java.awt.Color(0,0,0));
		   
		   l18=new JLabel("DOB :");
		   l18.setBounds(300,400,90,30);
		   l18.setFont(new Font("Cooper Black",Font.PLAIN,20));
		   l18.setForeground(new java.awt.Color(191,191,191));
		   
		   l19=new JLabel("");
		   l19.setBounds(370,401,150,30);
		   l19.setFont(new Font("Cooper Black",Font.PLAIN,20));
		   l19.setForeground(new java.awt.Color(0,0,0));
		   
		   l20 = new JLabel("<HTML><U>Reports :</U></HTML>");
		   l20.setBounds(70,460,300,80);
		   l20.setFont(new Font("Cooper Black",Font.PLAIN,25));
		   l20.setForeground(new java.awt.Color(255, 255, 255));
		   
		   l21=new JLabel("Grade :");
		   l21.setBounds(70,550,90,30);
		   l21.setFont(new Font("Cooper Black",Font.PLAIN,20));
		   l21.setForeground(new java.awt.Color(191,191,191));
		   
		   l22=new JLabel("");
		   l22.setBounds(160,550,150,30);
		   l22.setFont(new Font("Cooper Black",Font.PLAIN,20));
		   l22.setForeground(new java.awt.Color(255,255,51));
		   
		   l23=new JLabel("Severity :");
		   l23.setBounds(300,550,110,30);
		   l23.setFont(new Font("Cooper Black",Font.PLAIN,20));
		   l23.setForeground(new java.awt.Color(191,191,191));
		   
		   l24=new JLabel("");
		   l24.setBounds(410,550,150,30);
		   l24.setFont(new Font("Cooper Black",Font.PLAIN,20));
		   l24.setForeground(new java.awt.Color(255,255,51));
		   
		   l25=new JLabel("DR Presence :");
		   l25.setBounds(70,610,170,30);
		   l25.setFont(new Font("Cooper Black",Font.PLAIN,20));
		   l25.setForeground(new java.awt.Color(191,191,191));
		   
		   l26=new JLabel("");
		   l26.setBounds(220,611,80,30);
		   l26.setFont(new Font("Cooper Black",Font.PLAIN,20));
		   l26.setForeground(new java.awt.Color(255,0,0));
		   
		   l27 = new JLabel("<HTML><U>Eye Image :</U></HTML>");
		   l27.setBounds(930,140,300,80);
		   l27.setFont(new Font("Cooper Black",Font.PLAIN,25));
	       l27.setForeground(new java.awt.Color(255, 255, 255));
	       
	     
		   
	       set(fname);
	       ImageIcon img2= new ImageIcon("F:\\BE Project\\images\\dr.jpg");
	       p2=new JPanel();	 
	   	  p2.setLayout(null);
		   p2.setBounds(750, 230, 500, 450);
		   p2.setBackground(new Color(189, 195, 199));
			  
		   l28 = new JLabel("");
		   l28.setBounds(0,0,700,500);
		   l28.setIcon(img2);
				
		   
		  
		  f1.add(p1);
		  f1.add(p2);
		  p1.add(l1);
		  f1.add(l2);
		  p1.add(l3);
		  p2.add(l28);
		  f1.add(l4);
		  f1.add(l5);
		  f1.add(l6);
		  f1.add(l7);
		  f1.add(l8);
		  f1.add(l9);
		  f1.add(l10);
		  f1.add(l11);
		  f1.add(l12);
		  f1.add(l13);
		  f1.add(l14);
		  f1.add(l15);
		  f1.add(l16);
		  f1.add(l17);
		  f1.add(l18);
		  f1.add(l19);
		  f1.add(l20);
		  f1.add(l21);
		  f1.add(l22);
		  f1.add(l23);
		  f1.add(l24);
		  f1.add(l25);
		  f1.add(l26);
		  f1.add(l27);
		  
		  
		  f1.setVisible(true);
		  
		
	}
	
	protected void drawImage(java.awt.Image i, int j, int k, int width, int height, JPanel jPanel) {
		// TODO Auto-generated method stub
		
	}

	public void set(String fname)
	{
		try { 
            BufferedReader in = new BufferedReader( 
                        new FileReader(fname)); 
  
            String mystring; 
            
            l5.setText(mystring=in.readLine());
            l7.setText(mystring=in.readLine());
            l9.setText(mystring=in.readLine());
            l11.setText(mystring=in.readLine());
            l13.setText(mystring=in.readLine());
            l15.setText(mystring=in.readLine());
            l17.setText(mystring=in.readLine());
            l19.setText(mystring=in.readLine());
            l26.setText(mystring=in.readLine());
            mystring=in.readLine();
            if(mystring.equalsIgnoreCase("0"))
            {
            	l26.setText("No");
            	l22.setText("0");
            	l24.setText("None");
            	
            }
            else if(mystring.equalsIgnoreCase("1"))
            {
            	l26.setText("Yes");
            	l22.setText("3");
            	l24.setText("Modarate");
            }
            
            
            
            
           
        } 
        catch (IOException e) { 
            System.out.println("Exception Occurred" + e); 
        } 
		
		
	}
	
	  private void l3MouseClicked(java.awt.event.MouseEvent evt) {   
			 f1.setVisible(false);
			 new Upload(fname);
			   
		    }
	
	
	

}
