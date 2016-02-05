package serveur;

import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author jgoodwin
 */
public class ServeurThread extends Thread
{
    private ServerSocket socketserver;
    private Socket socket;
    private int numClient;
    
    private ServeurThread(int parNumClient, Socket s)
    {
        this.socket = s;
        this.numClient = parNumClient;
    }
    
    @Override
    public void run()
    {
        
    }
}
