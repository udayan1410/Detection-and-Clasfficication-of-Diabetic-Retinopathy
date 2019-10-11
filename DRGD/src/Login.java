import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;

import javax.swing.*;

import com.sun.glass.ui.Cursor;
import com.sun.javafx.collections.MappingChange.Map;

public class Login implements ActionListener{

	
	JFrame f1;
	JLabel l1,l2,l3,l4,l5,l6,l7,l8,l9,l10,l11;
	JPanel p1,p2,p3;
	JTextField t1,t2,t3,t4;
	JPasswordField t5;
	JButton b1;
	Image img;
	
	
	Login()
	{
		f1 = new JFrame("Login");
		f1.setLayout(null);
		f1.setExtendedState(JFrame.MAXIMIZED_BOTH);
	    f1.getContentPane().setBackground(new Color(255,255,255));
	    f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    ImageIcon img= new ImageIcon("F:\\BE Project\\images\\group.png");
	    
	        
	   
		
	    
	    
	    
	    p2=new JPanel();
		p2.setLayout(null);
		p2.setBounds(0, 0, 1400, 150);
		p2.setBackground(new Color(0,87,75));
		

		l1 = new JLabel("Login");
	    l1.setBounds(220,25,980,100);
		l1.setFont(new Font("Times New Roman",Font.BOLD,50));
		l1.setForeground(new Color(255,255,255));
		
		t1=new JTextField(20);
		t1.setBounds(550,400,250,40);
		t1.setBackground(new Color(255,255,255));
		t1.setForeground(new Color(0,0,0));
		
		t2=new JTextField(20);
		t2.setBounds(550,500,250,40);
		t2.setBackground(new Color(255,255,255));
		t2.setForeground(new Color(0,0,0));
		
		l2 = new JLabel("Username:");
	    l2.setBounds(550,370,200,25);
		l2.setFont(new Font("Times New Roman",Font.BOLD,20));
		l2.setForeground(new Color(0,87,75));
		
		l3 = new JLabel("Password:");
	    l3.setBounds(550,470,200,25);
		l3.setFont(new Font("Times New Roman",Font.BOLD,20));
		l3.setForeground(new Color(0,87,75));
		
		b1=new JButton("Login");
		b1.setBounds(620,580,100,30);
	    b1.setBackground(new Color(0,87,75));
		b1.setForeground(new Color(255,255,255));
	    b1.setFont(new Font("Times New Roman",Font.PLAIN,20));
		
	    b1.addMouseListener(new java.awt.event.MouseAdapter() {
	           public void mouseClicked(java.awt.event.MouseEvent evt) {
	               b1MouseClicked(evt);
	           }
	       });
	    
	    l4 = new JLabel("<HTML><U>Sign Up</U></HTML>");
	    l4.setBounds(638,620,200,25);
		l4.setFont(new Font("Times New Roman",Font.BOLD,20));
		l4.setForeground(new Color(0,87,75));
		l4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		
		l5 = new JLabel("");
		l5.setBounds(420,120,350,250);
		l5.setIcon(img);
	    
		p2.add(l1);
        f1.add(p2); 
        f1.add(t1);
        f1.add(t2);
        f1.add(l2);
        f1.add(l3);
        f1.add(b1);
        f1.add(l4);
        f1.add(l5);
        f1.setVisible(true);
        
	}
	
	 
	
	public static void main(String[] args) {
		new Login();
	}
	
	
	 private void b1MouseClicked(java.awt.event.MouseEvent evt) {
	 
		  f1.setVisible(false);
		  new Welcome();
	 
	 }
	 
	 
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
