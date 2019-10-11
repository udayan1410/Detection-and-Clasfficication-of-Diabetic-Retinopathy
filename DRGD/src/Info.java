import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.sun.glass.ui.Cursor;
import com.sun.org.apache.xerces.internal.util.URI;

public class Info {

	JFrame f1;
	JLabel l1,l2,l3,l4,l5,l6,l7,l8,l9,l10,l11;
	JPanel p1,p2,p3;
	JTextField t1,t2,t3,t4;
	JPasswordField t5;
	JButton b1;
	Image img;
	
	Info()
	{
		
		f1 = new JFrame("Info");
		f1.setLayout(null);
		f1.setExtendedState(JFrame.MAXIMIZED_BOTH);
	    f1.getContentPane().setBackground(new Color(44,62,80));
	    f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ImageIcon img1= new ImageIcon("F:\\BE Project\\images\\back.png");
		ImageIcon img2= new ImageIcon("F:\\BE Project\\images\\info1.jpg");
		//ImageIcon img3= new ImageIcon("F:\\BE Project\\images\\dr.jpg");
		
		
		l1 = new JLabel("Diabetic Retinopathy");
		l1.setBounds(220,25,980,100);
		l1.setFont(new Font("Cooper Black",Font.BOLD,50));

		
	   
		p2=new JPanel();
		p2.setLayout(null);
		p2.setBounds(0, 0, 1400, 150);
		p2.setBackground(new Color(248,148,6));
		
			
		l2 = new JLabel("");
		l2.setBounds(0,35,130,70);
		l2.setIcon(img1);
		l2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		l2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                l2MouseClicked(evt);
            }
        });
		
		l3 = new JLabel("");
		l3.setBounds(750,150,600,600);
		l3.setIcon(img2);
		
		l4 = new JLabel("Some Useful Information:");
		l4.setBounds(20,200,300,20);
		l4.setFont(new Font("Cooper Black",Font.PLAIN,20));
		l4.setForeground(new Color(0,0,0));
		
				
		
		
		

		
		
		
		
		
				
	
		
	
		p2.add(l1);
		p2.add(l2);
		f1.add(l3);
		f1.add(l4);
		f1.add(l5);
		f1.add(p2);		
		
		
		
		
		
		f1.setVisible(true);
	}	
		
		
	 private void l2MouseClicked(java.awt.event.MouseEvent evt) {   
		 f1.setVisible(false);
		 new Welcome();
		        
	    }
	
      
	public static void main(String[] args) {
		new Welcome();
	}









	

	
}
