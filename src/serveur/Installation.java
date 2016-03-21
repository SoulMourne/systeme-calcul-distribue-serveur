package serveur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class Installation extends Thread
{
	private int i;

	public Installation(int i)
	{
		this.i = i;
	}
	
	public void run()
	{
		try{
	  		String cmd;
			Process p;
			
			cmd = "bash serveur/installation.sh 192.168.0." + i + " client/client.sh";
			p = Runtime.getRuntime().exec(cmd);
			p.waitFor();

			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
		   	String line = "";
			while ((line = reader.readLine()) != null) {
			   System.out.println(line);
			}
		   
		} catch(Exception e){
			e.printStackTrace();
		}
	}
}
