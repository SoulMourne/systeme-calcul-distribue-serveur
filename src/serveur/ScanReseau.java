package serveur;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *Cette class permet de scanner le reseau et recuperer les adresses ip 
 * des clients en voyant ceux qui sont disponibles (avec un ping)
 * @author sahmed
 * reference site web (inspiration) : http://www.fobec.com/java/1141/scanner-postes-sur-reseau-local.html
 */


public class ScanReseau extends Thread {

    //Variables
    private final String ipRoot;
    
    //Constructeur
    public ScanReseau(String parIpRoot){
        this.ipRoot = parIpRoot;
    }
    
    public void joignable(){
        String[] nip = ipRoot.split("\\.");
        if (nip.length !=4){
            System.out.println("L'adresse IP n'est pas IPv4 ! \n");
            
        }
        byte[] ip = {(byte) Integer.parseInt(nip[0]), (byte) Integer.parseInt(nip[1]),
           (byte) Integer.parseInt(nip[2]), (byte) Integer.parseInt(nip[3])};
        
        
        try{
            InetAddress addr = InetAddress.getByAddress(ip);
            if (isAlive(addr.getHostAddress())){
                    System.out.printf("%-16s\n", addr.getHostAddress() + "\t\tAlive");
            }
            else{
                System.out.println(addr.getHostAddress() + "\t\tDead");
                
            }
        }catch(UnknownHostException e){
            System.out.println(e.getMessage());
        }
    }
    
    public static void affichage(){
        //Entete du tableau
        System.out.printf("%s\t\t%s\n\n", "AdresseIP", "Etat");
    }
    
    
    /**
     * Ping sur une adresse ip
     *
     * @param Ipv4Adr adresse IP du poste
     * @return boolean
     */
    
    public static boolean isAlive(String Ipv4Adr) {
        Process p1;
        boolean reachable = false;
        try {
            p1 = java.lang.Runtime.getRuntime().exec("ping -w 2 -n 2 " + Ipv4Adr);
            int returnVal = p1.waitFor();
            reachable = (returnVal == 0);
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(ScanReseau.class.getName()).log(Level.SEVERE, null, ex);
        }
        return reachable;
    }
 
    /**
     * Tester l'état du port sur un poste
     *
     * @param ip adresse ip du poste
     * @param port Numero du port
     * @param timeout délai en ms
     * @return port ouvert ou non
     */
    public boolean portIsOpen(String ip, int port, int timeout) {
        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(ip, port), timeout);
            socket.close();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
    
    public void run()
    {
        this.joignable();
    }
    
}
