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
    private static String ipRoot = ConstantesScanReseau.ipRoot; 
    private static final int port = ConstantesScanReseau.port;
    private static final int portWindows = ConstantesScanReseau.portWindows;
    private static final int timeout = ConstantesScanReseau.timeout;
    private static final int hostMin = ConstantesScanReseau.hostMin;
    private static final int hostMax = ConstantesScanReseau.hostMax;
    private static int clients;
      
    
    public static void main(String[] args) throws IOException, InterruptedException
    {
        //Entete du tableau
        ScanReseau.affichage();
        
        //Nombre de clients initialisés à 0
        clients = 0;
        
        //dernier octet
        int lastByte;
        
        /*
        Boucle du Thread :
        Cette boucle permet de verifier si des postes sont tout d'abord joignables ( on les marque Alive)
        On vérifie ensuite les ports avec portIsOpen, le port 22 doit être ouvert pour l'installation des clients.
        On fait aussi un test sur un port spécifique windows afin de ne pas récupérer les machines windows.
        */
        for (lastByte=hostMin ; lastByte<hostMax ;lastByte++){
            
            ip = ipRoot+lastByte;
            ScanReseau lan = new ScanReseau(ip);
            lan.start();
            if (lan.portIsOpen(ip, port, timeout) == true && lan.portIsOpen(ip, portWindows, timeout) == false )
            {
                listIP.add(ip);
                clients ++;
            }  
        }
        //Pause du Thread puis affichage de la liste d'adresses IP 
        Thread.sleep(2000);
        System.out.println("ArrayList d'adressesIP : " + listIP + "\n");
        
        System.out.println(clients + " client(s) connecté(s) sur le reseau " + ipRoot+"0" + " sur le port " + port );
    }
  
}
