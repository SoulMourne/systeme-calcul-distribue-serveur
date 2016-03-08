package serveur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

/**
 * Classe faisant l'abstraction d'un serveur et gérant les connexions entrantes et sortantes du serveur
 */
public class Serveur 
{
    private ServerSocket socketServer; //Variable du socket du serveur permettant aux clients de s'y connecter
    private Socket socketDuClient;  //Socket servant à communiquer avec le client
    private HashMap <Integer, ServeurThread> connexions; //HashMap permettant de gérer les Serveurs Thread en leur donnant un numéro
    
    /**
     * Constructeur par défaut
     * @param numPort Le numéro du port sur lequel mettre le serveur à l'écoute
     */
    public Serveur(int numPort)
    {
    	this.installationClients();
        socketDuClient = null;  //Initialisation d'un socket pour la communication avec le/les clients
        connexions = new HashMap<Integer, ServeurThread>();
        try {
            socketServer = new ServerSocket(numPort);  //Initialisation d'un ServerSocket sur le port 2009
            System.out.println("Le serveur est à l'écoute du port "+socketServer.getLocalPort());   //Indique sur quel port le serveur est à l'écoute
        } catch (IOException e) {   //En cas d'erreur
            e.printStackTrace();
        }
        
    }
    
    /**
     * Méthode d'éxécution du programme
     * @param args Arguments à ajouter pour le lancement du programme
     */
    public static void main(String[] args) 
    {
        Serveur serveur = new Serveur(5000); //Ouverture du serveur
        
        int i = 0;  //Initialisation d'un compteur
        while(i<20) //Pendant 5 itérations
        {
            serveur.accepterClient();   //Attend et accepte un client
            
            
            
            //serveur.fermerClient(); //Ferme la connexion avec le client
            i++;    //Le compteur augmente
        }
        serveur.fermetureServeur(); //Ferme le socket serveur
    }

    /**
     * Ferme le socket serveur
     */
    public void fermetureServeur()
    {
        try {
            socketServer.close();   //Ferme le socket serveur
        } catch (IOException ex) {  //En cas d'erreur
            ex.printStackTrace();
        }
    }
    
    /**
     * Attend un socket client et l'accepte.
     * Signale que le client s'est bien connecté
     */
    public void accepterClient()
    {
        try{
            this.socketDuClient =socketServer.accept(); //Attend la connexion d'un client
            int i;
            for (i = 0; i <= this.connexions.size();i++) //Cherche une place vacante dans la liste des ServeurThread
            {
                if (!this.connexions.containsKey(i))    //Si la place est vacante ajoute un serveur Thread sinon l'ajoute en bout de file
                {
                    break;
                }
            }
            this.connexions.put(i, new ServeurThread(i,socketDuClient,this)); //Créé un serveur Thread et le référence dans la HashMap
            this.connexions.get(i).start();
            System.out.println("Le client " +i+ " s'est connecté !");   //Le client s'est connecté
        }
        catch(IOException e){   //En cas d'erreur
            e.printStackTrace();
        }
    }
    
    /**
     * Ferme le socket du client 
     * Signale que le client a été déconnecté
     */
    public void fermerClient()
    {
        try{
        this.socketDuClient.close();    //Ferme le socket du client
            System.out.println("Un client s'est déconnecté !");
        }catch(IOException e){  //En cas d'erreur
            e.printStackTrace();
        }
    }
    
    /**
     * Renvoie le socket du client
     * @return le socket du client
     */
    public Socket getSocketDuClient()
    {
        return this.socketDuClient; //Renvoie le socket du client
    }
    
    /**
     * Renvoie le socket d'écoute du Serveur
     * @return le socket d'écoute du serveur
     */
    public ServerSocket getServerSocket()
    {
        return  this.socketServer;  //Renvoie le socket serveur
    }
    
    /**
     * Récupère une HashMap contenant tous les ServeurThread et indexés selon leur numero dans la file
     * @return HashMap contenant tous les ServeurThread et indexés selon leur numero dans la file
     */
    public HashMap<Integer, ServeurThread> getConnexions() 
    {
        return connexions;
    }
    
    /**
     * Installe les clients
     */
    public void installationClients()
    {
    	try{
    		String cmd = "yum install sshpass -y";
			Process p = Runtime.getRuntime().exec(cmd);
			p.waitFor();
    	
			cmd = "bash serveur/installation.sh 192.168.0.113 client/client.sh";
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
