 
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class tp1 
{
	
  public static void main(String args[]) throws Exception
  {
	String pypath = "H:\\Python\\prog\\hello.py";
    String[] cmd = new String[2];
    cmd[0] = "H:\\Python\\python.exe";
    cmd[1] = pypath;
    
    Runtime rt = Runtime.getRuntime();
    Process pr = rt.exec(cmd);
    
    BufferedReader br = new BufferedReader(new InputStreamReader(pr.getInputStream()));
    String line="";
    while((line=br.readLine())!=null)
    {
    	System.out.println(line);
    }
  
  }
	

}
