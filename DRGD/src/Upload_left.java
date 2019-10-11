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

public class Upload_left {

	 private Connection con;
	  private Statement st;
	  private ResultSet rs;
	  
	  
	
	private static final int MODE_OPEN = 1;
	JFrame f1;
	  JPanel p1;
	  JLabel l1,l2,l3,l4;
	  JTextField t1,t2;
	  JButton b1;
	  private JFileChooser fileChooser;
	  public static final int MODE_SAVE = 2;
	  private int mode;
	  String id;
	  String fname;
	  
	  Upload_left(String fname)
	  {
		  this.fname=fname;
		  try{
			  Class.forName("com.mysql.jdbc.Driver");
			  con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dr_project","root","");
			  st = con.createStatement();
			  
			 
			  
		  }
		  catch(Exception ex){
			  System.out.println("Error: "+ex);
			  
		  }
		  
		  try { 
	            BufferedReader in = new BufferedReader( 
	                        new FileReader(fname));
	            
	            id=in.readLine();
		  }
		  catch (IOException e) { 
	            System.out.println("Exception Occurred" + e); 
	        } 
		  
		  
		  fileChooser = new JFileChooser();
		  f1 =  new JFrame("Upload");
		  f1.setExtendedState(JFrame.MAXIMIZED_BOTH);
		  f1.setLayout(null);
		  f1.getContentPane().setBackground(new Color(255,255,255));
		  f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		  ImageIcon img1= new ImageIcon("F:\\BE Project\\images\\back.png");
		  ImageIcon img2= new ImageIcon("F:\\BE Project\\images\\add1.png");
		  ImageIcon img3= new ImageIcon("F:\\BE Project\\images\\loading.png");
		  
		  l1 = new JLabel("Upload Left Eye Image");
		  l1.setBounds(220,25,980,100);
		  l1.setFont(new Font("Times New Roman",Font.BOLD,50));
		  l1.setForeground(new Color(255,255,255));
		  
		  p1=new JPanel();
		  p1.setLayout(null);
		  p1.setBounds(0, 0, 1400, 150);
		  p1.setBackground(new Color(0,87,75));
			
		  l2 = new JLabel("Image:");
		  l2.setBounds(380,300,150,30);
		  l2.setFont(new Font("Times New Roman",Font.PLAIN,20));
		  l2.setForeground(new java.awt.Color(0,87,75));
		  
		  t1 = new JTextField(110);
		  t1.setBounds(460,300,400,30);
		  t1.setBackground(new Color(255,255,255));
		  t1.setForeground(new Color(0,0,0));
		  
		 
		
		  
		  
		  
	
		  
		  l4 = new JLabel("");
		  l4.setBounds(880,280,130,70);
		  l4.setIcon(img2);	
		  l4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		  l4.addMouseListener(new java.awt.event.MouseAdapter() {
	            public void mouseClicked(java.awt.event.MouseEvent evt) {
	                l4MouseClicked(evt);
	            }
	        });
		  
		  b1=new JButton("Upload");
		   b1.setBounds(580,380,150,30);
		   b1.setBackground(new Color(0,87,75));
		   b1.setForeground(new Color(255,255,255));
		   b1.setFont(new Font("Times New Roman",Font.PLAIN,20));
		   b1.addMouseListener(new java.awt.event.MouseAdapter() {
	           public void mouseClicked(java.awt.event.MouseEvent evt) {
	               try {
					b1MouseClicked(evt);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	           }
	       });
		   setMode(Upload.MODE_SAVE);
		   addFileTypeFilter(".jpg", "JPEG Images");
		   addFileTypeFilter(".png", "PNG Images");
		  
		  f1.add(p1);
		  p1.add(l1);
		  
		  f1.add(l2);
		  f1.add(t1);
		  
		  f1.add(l4);
		  f1.add(b1);
		  
		  f1.setVisible(true);
		  JFileChooser fileChooser = getFileChooser();
	        fileChooser.setCurrentDirectory(new File("F:\\BE Project\\Python Model\\trimmed\\"));
	  }
	  
	  private void l4MouseClicked(java.awt.event.MouseEvent evt) {   
		  if (mode == MODE_OPEN) {
	            if (fileChooser.showOpenDialog(t1) == JFileChooser.APPROVE_OPTION) {
	                t1.setText(fileChooser.getSelectedFile().getAbsolutePath());
	            }
	        } else if (mode == MODE_SAVE) {
	            if (fileChooser.showSaveDialog(t1) == JFileChooser.APPROVE_OPTION) {
	                t1.setText(fileChooser.getSelectedFile().getAbsolutePath());
	            }
	        }
			   
		    }
	  
	  private void b1MouseClicked(java.awt.event.MouseEvent evt) throws Exception {
		  
		  
		  String path=t1.getText();
		  File file=new File(path);
		  long left_eyen = System.currentTimeMillis();
		  File new_file=new File("F:\\BE Project\\Python Model\\trimmed\\"+left_eyen+".jpeg");
		  
		  if(file.renameTo(new_file))
		  {
			 String final_left=Long.toString(left_eyen);
			 final_left=final_left+".jpeg";
			 
			 String query="update login set left_eye='"+final_left+"' where id='"+id+"'";
			 st.executeUpdate(query);
			 
			 
		  }
		  else
		  {
			  System.out.println("Error");
		  }
		  
		  f1.setVisible(false);
		  new Upload_right(fname);
		  
		  
		  
	  }
	  
	  public void addFileTypeFilter(String extension, String description) {
	        FileTypeFilter filter = new FileTypeFilter(extension, description);
	        fileChooser.addChoosableFileFilter(filter);
	    }
	     
	    public void setMode(int mode) {
	        this.mode = mode;
	    }
	     
	    public String getSelectedFilePath() {
	        return t1.getText();
	    }
	     
	    public JFileChooser getFileChooser() {
	        return this.fileChooser;
	    }
	    

}
