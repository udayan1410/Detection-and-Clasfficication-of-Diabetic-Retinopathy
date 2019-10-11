import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Welcome implements ActionListener{

	JFrame f1;
	JLabel l1,l2,l3,l4,l5,l6,l7,l8,l9,l10,l11;
	JPanel p1,p2,p3;
	JTextField t1,t2,t3,t4;
	JPasswordField t5;
	JButton b1;
	Image img;
	
	Welcome()
	{
		
		f1 = new JFrame("WELCOME");
		f1.setLayout(null);
		f1.setExtendedState(JFrame.MAXIMIZED_BOTH);
	    f1.getContentPane().setBackground(new Color(255,255,255));
	    f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ImageIcon img1= new ImageIcon("F:\\BE Project\\images\\add.png");
		ImageIcon img3= new ImageIcon("F:\\BE Project\\images\\search.png");
		ImageIcon img4= new ImageIcon("F:\\BE Project\\images\\info.png");
		ImageIcon img5= new ImageIcon("F:\\BE Project\\images\\g1.png");
		
		
		l1 = new JLabel("Welcome");
		l1.setBounds(220,25,980,100);
		l1.setFont(new Font("Times New Roman",Font.BOLD,50));
		l1.setForeground(new Color(255,255,255));

		
	    p1=new JPanel();
		p1.setLayout(null);
		p1.setBounds(500, 170, 350, 500);
		p1.setBackground(new Color(255,255,255));
		
		p2=new JPanel();
		p2.setLayout(null);
		p2.setBounds(0, 0, 1400, 150);
		p2.setBackground(new Color(0,87,75));
		
			
		l2 = new JLabel("");
		l2.setBounds(10,150,130,70);
		l2.setIcon(img1);
		l2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		l2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                l2MouseClicked(evt);
            }
        });
		
		l3 = new JLabel("");
		l3.setBounds(10,250,130,70);
		l3.setIcon(img3);	
		l3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		
		l4 = new JLabel("");
		l4.setBounds(10,350,130,70);
		l4.setIcon(img4);
		l4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		l4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                l4MouseClicked(evt);
            }
        });

		
		l5 = new JLabel("New");
		l5.setBounds(110,165,500,40);
		l5.setFont(new Font("Times New Roman",Font.BOLD,40));
		l5.setForeground(new java.awt.Color(0,87,75));
		
		l6 = new JLabel("Search");
		l6.setBounds(110,265,500,40);
		l6.setFont(new Font("Times New Roman",Font.BOLD,40));
		l6.setForeground(new java.awt.Color(0,87,75));
		
		l7 = new JLabel("Logout");
		l7.setBounds(110,365,500,45);
		l7.setFont(new Font("Times New Roman",Font.BOLD,40));
		l7.setForeground(new java.awt.Color(0,87,75));
				
		l11 = new JLabel("");
		l11.setBounds(1150,150,180,100);
		l11.setIcon(img5);
		
	
		p2.add(l1);
	
		
		f1.add(p2);		f1.add(p1);		
		p1.add(l2);
		p1.add(l3);
		p1.add(l4);
		p1.add(l5);
		p1.add(l6);
		p1.add(l7);
		f1.add(l11);
		
		
		f1.setVisible(true);
	}	
		
		
	 private void l2MouseClicked(java.awt.event.MouseEvent evt) {   
		 f1.setVisible(false);
		 new Register();
		        
	    }
	 
	 
	 private void l4MouseClicked(java.awt.event.MouseEvent evt) {   
		 f1.setVisible(false);
		 new Info();
		        
	    }
	
      
	public static void main(String[] args) {
		new Welcome();
	}









	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
