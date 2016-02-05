package serveur;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * Classe faisant l'abstraction d'un serveur gérer par un thread
 * @author jgoodwin
 */
public class ServeurThread extends Thread
{
    private ServerSocket socketserver;
    private Socket socket;
    private int numClient;
    
    /**
     * Constructeur par défaut prenant le numéro du client et le socket du client
     * @param parNumClient Numéro du client
     * @param s Socket du client
     */
    public ServeurThread(int parNumClient, Socket s)
    {
        this.socket = s;
        this.numClient = parNumClient;
    }
    
    @Override
    public void run()
    {
        
    }
}
