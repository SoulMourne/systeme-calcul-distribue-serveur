package serveur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

/**
 * Classe faisant l'abstraction d'un serveur et gérant les connexions entrantes et sortante du serveur
 */
public class Serveur 
{
    private ServerSocket socketServer;//Variable du socket du serveur permettant aux clients de s'y connecter
    private BufferedReader in;  //Permet de lire des caractères
    private PrintWriter out;    //Permet d'écrire un message
    private Socket socketDuClient;  //Socket servant à communiquer avec le client
    private HashMap <Integer, ServeurThread> connexions;
    
    /**
     * Constructeur par défaut
     * @param numPort Le numéro du port sur lequel mettre le serveur à l'écoute
     */
    public Serveur(int numPort)
    {
        socketDuClient = null;  //Initialisation d'un socket pour la communication avec le/les clients
        connexions = new HashMap<>();
        try {
            socketServer = new ServerSocket(2009);  //Initialisation d'un ServerSocket sur le port 2009
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
        Serveur serveur = new Serveur(2009); //Ouverture du serveur
        
        int i = 0;  //Initialisation d'un compteur
        while(i<20) //Pendant 5 itérations
        {
            serveur.accepterClient();   //Attend et accepte un client
            
            String messageClient = serveur.lectureMessage(serveur.socketDuClient);  //Lit et récupère le message du client
            System.out.println(messageClient);  //Affiche le message du client
            
            String ipClient = serveur.socketDuClient.getRemoteSocketAddress().toString()+"\n";   //Récupère l'adresse IP du client
            serveur.envoiMessage(serveur.socketDuClient, "Bienvenue client, vous avez pour adresse IP : "+ipClient);    //Envoie un message au client
            
            serveur.fermerClient(); //Ferme la connexion avec le client
            i++;    //Le compteur augmente
        }
        serveur.fermetureServeur(); //Ferme le socket serveur
    }
    
    /**
     * Envoie une chaine de caractères au client via le socket correspondant
     * @param socketClient socket permettant de communiquer avec le client
     * @param message chaine de caractères à envoyer sur le socket
     * @return booléen si le message a été ou non envoyé
     */
    public boolean envoiMessage(Socket socketClient,String message)
    {
        try{
            out = new PrintWriter(socketClient.getOutputStream());   //Récupère l'OutputStream du socket du client et ouvre un PrintWriter permettant au serveur d'y écrire
            out.println(message); //Envoi d'un message au client ainsi que son adresse IP
            out.flush();    //Vide l'OutputStream
        }catch (IOException e){ //En cas d'erreur
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    /**
     * Lit un message envoyé via le socket du client
     * @param socketClient Le socket sur lequel le message est envoyé
     * @return la chaine de caractère lue ou bien null si erreur
     */
    public String lectureMessage(Socket socketClient)
    {
        try{
        in = new BufferedReader (new InputStreamReader (socketDuClient.getInputStream())); //permet de lire les caractères provenant du socketduserveur
        return in.readLine();   //Renvoie le contenu de in
        }catch (IOException e){ //En cas d'erreur
            e.printStackTrace();
            return null;
        }
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
            this.connexions.put(i, new ServeurThread(i,socketDuClient));
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
}