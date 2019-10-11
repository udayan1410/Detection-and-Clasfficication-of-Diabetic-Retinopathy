import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.*;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Upload {

	  JFrame f1;
	  JPanel p1;
	  JLabel l1,l2,l3,l4;
	  JTextField t1,t2;
	  JButton b1;
	  String fname,path,res,result,res2,res3,res4,res5,res6;
	  char a;
	  int a1,c;
	  
	  private JFileChooser fileChooser;
	  private int mode;
	    public static final int MODE_OPEN = 1;
	    public static final int MODE_SAVE = 2;
	    ImageIcon img3;
	    
	   
	  
	  Upload(String x)
	  {
		  fname=x;
		  fileChooser = new JFileChooser();
		  f1 =  new JFrame("Upload");
		  f1.setExtendedState(JFrame.MAXIMIZED_BOTH);
		  f1.setLayout(null);
		  f1.getContentPane().setBackground(new Color(44,62,80));
		  f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		  ImageIcon img1= new ImageIcon("F:\\BE Project\\images\\back.png");
		  ImageIcon img2= new ImageIcon("F:\\BE Project\\images\\add1.png");
		  img3= new ImageIcon("F:\\BE Project\\images\\loading.png");
		  
		  l1 = new JLabel("Upload Image");
		  l1.setBounds(220,25,980,100);
		  l1.setFont(new Font("Cooper Black",Font.BOLD,50));
		  
		  p1=new JPanel();
		  p1.setLayout(null);
		  p1.setBounds(0, 0, 1400, 150);
		  p1.setBackground(new Color(248,148,6));
			
		  l2 = new JLabel("Image:");
		  l2.setBounds(380,300,150,30);
		  l2.setFont(new Font("Cooper Black",Font.PLAIN,20));
		  l2.setForeground(new java.awt.Color(255, 255, 255));
		  
		  t1 = new JTextField(110);
		  t1.setBounds(460,300,400,30);
		  t1.setBackground(new Color(108,122,137));
		  t1.setForeground(new Color(255,255,255));
		  
		 
		
		  
		  
		  
		  l3 = new JLabel("");
		  l3.setBounds(0,35,130,70);
		  l3.setIcon(img1);	
		  l3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		  l3.addMouseListener(new java.awt.event.MouseAdapter() {
	            public void mouseClicked(java.awt.event.MouseEvent evt) {
	                l3MouseClicked(evt);
	            }
	        });
		  
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
		   b1.setBackground(new Color(30, 139, 195));
		   b1.setForeground(new Color(0,0,0));
		   b1.setFont(new Font("Cooper Black",Font.PLAIN,20));
		   b1.addMouseListener(new java.awt.event.MouseAdapter() {
	           public void mouseClicked(java.awt.event.MouseEvent evt) {
	               try {
					b1MouseClicked(evt);
				} catch (IOException e) {
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
		  p1.add(l3);
		  f1.add(l2);
		  f1.add(t1);
		  
		  f1.add(l4);
		  f1.add(b1);
		  
		  f1.setVisible(true);
		  JFileChooser fileChooser = getFileChooser();
	        fileChooser.setCurrentDirectory(new File("F:\\BE Project\\Python Model\\trimmed\\"));
		  
	  }
	  private void l3MouseClicked(java.awt.event.MouseEvent evt) {   
			 f1.setVisible(false);
			 new Register();
			   
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
	  private void b1MouseClicked(java.awt.event.MouseEvent evt) throws IOException {                                      
		     
		  l4.setIcon(img3);
		 
		  
		  path=t1.getText();
		  int len;
		  len=path.length();
		  
		  for(int i=0,j=0; i<path.length();i++,j++)
			{
				a=path.charAt(i);
				a1=(int)a;
				if(a1==92)
					{
						len+=1;
					}
			
						
			}
		 // len+=2;
		  
		  char[] nm1 = new char[len];
		 // nm1[0]=34;
		  int i,j;
		  for( i=0,j=0; i<path.length();i++,j++)
			{
				a=path.charAt(i);
				a1=(int)a;
				if(a1==92)
					{
						nm1[j]=a;
						nm1[j+1]=a;
						j++;
					}
				else
				{
					nm1[j]=a;
				}
						
			}
		  //nm1[j]=34;
			res =  String.valueOf(nm1);
			
			
			System.out.println("Res="+res);
			My_thread t1 = new My_thread();
			
			res2=res;
			
			int len2,b1,len3,len4;
			char b;
			len2=res2.length();
		   
			for(i=len2-1;i>=0;i--)
			{
				b=res2.charAt(i);
				System.out.println("B="+b);
				b1=(int)b;
				if(b1==46)
				{
					c=i;
					break;
				}
					
			}
			len3=len2-c;
			System.out.println("C="+c);
			char[] nm2=new char[len3];
			for(i=c,j=0;i<len2;i++,j++)
			{
				a=res2.charAt(i);
				nm2[j]=a;
				
				
			}
			
          res3=String.valueOf(nm2);
          len4=res3.length();
          
          BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fname)));
          res4=br.readLine();
          //System.out.println("Res4="+res4);
        	res5=res4+res3;
        	System.out.println("Res5="+res5);
          
          
          System.out.println("Res3="+res3);
          
          Path temp=Files.move(Paths.get(res),Paths.get("F:\\BE Project\\Python Model\\trimmed\\"+res5));
          res6="F:\\BE Project\\Python Model\\trimmed\\"+res5;
          
			t1.start();
			
			
			
		  
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
	    
	    public class My_thread extends Thread
	    {
	    	
	    public void run()
	    {
	    	
	    	
	    
	    	try
	    	{
	    		
	    		 String pypath = "F:\\load_python_model.py";
	    		// String pypath = "F:\\BE Project\\GUI\\hello\\hello.py";
	    		 //String pypath = "F:\\hello.py";
				    String[] cmd = new String[2];
				    cmd[0] = "F:\\Python 36\\python.exe";
				    cmd[1] = pypath;
				    String fpath="F:\\hello1.txt";
				    
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
			  
				 //   System.out.println("Result= "+result);
				    
				    
			  

				 
				  BufferedWriter out = new BufferedWriter( 
				  new FileWriter(fname, true)); 
				  out.write(res6+"\n");
				  out.write(result);
				  out.close(); 
				  
				  Path temp=Files.move(Paths.get(res6),Paths.get("F:\\BE Project\\Python Model\\Tp\\"+res5));
				   
				 
			  
			  
			     
			  
			   
			  
			  
	    	
				    f1.setVisible(false);
					   new Result(fname);
	    	
				     System.out.println("Done "+br.readLine());
				     
	    	}
			   catch (IOException e) { 
					  System.out.println("exception occoured" + e); 
					  } catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
	    		
	    		
	    		
	    	}
	    	
	    }
	  
	
}
