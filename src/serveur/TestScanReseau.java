package serveur;


import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author sahmed
 */

public class TestScanReseau {
    
    //Variables
    private static ArrayList<String> listIP = new ArrayList<>();
    private static String ip ; 
    private static final int port = 22;
    private static final int timeout = 100;
    private static int clients;
      
    
    public static void main(String[] args) throws IOException, InterruptedException
    {
        //Entete du tableau
        ScanReseau.affichage();
        clients = 0;
        ip = "192.168.1.";
        int lastByte= 0;
        
        //Boucle de thread
        for (lastByte=0 ; lastByte<255 ;lastByte++){
            
            ip = "192.168.1."+lastByte;
            ScanReseau lan = new ScanReseau(ip);
            lan.start();
            if (lan.portIsOpen(ip, port, timeout) == true)
            {
                listIP.add(ip);
                clients ++;
            }  
        }
        Thread.sleep(2000);
        lastByte= 0;
        ip = "192.168.1."+lastByte;
        System.out.println("ArrayList d'adressesIP : " + listIP + "\n");
        
        System.out.println(clients + " client(s) connecté(s) sur le reseau " + ip + " sur le port " + port );
    }
  
}
