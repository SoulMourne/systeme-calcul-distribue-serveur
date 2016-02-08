package serveur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Classe faisant l'abstraction d'un serveur gérer par un thread
 * @author jgoodwin
 */
public class ServeurThread extends Thread
{
    /**
     * Référence vers le serveur
     */
    private Serveur serveur;
    /**
     * Socket du client
     */
    private Socket socketClient;    
    /**
     * Numero du client
     */
    private int numClient;  
    /**
     * Permet de lire des caractères
     */
    private BufferedReader in;
    /**
     * Permet d'écrire un message
     */
    private PrintWriter out;
    
    /**
     * Constructeur par défaut prenant le numéro du client et le socket du client
     * @param parNumClient Numéro du client
     * @param s Socket du client
     */
    public ServeurThread(int parNumClient, Socket parSocket, Serveur parServeur)
    {
        this.socketClient = parSocket;
        this.serveur = parServeur;
        this.numClient = parNumClient;
    }
    
    /**
     * Methode réalisé lors de l'éxécution d'un thread et permettant le suivi permanent d'une connexion avec un client
     */
    @Override
    public void run()
    {
        boolean continuer = true;
        
        String messageClient = this.lectureMessage(this.socketClient);  //Lit et récupère le message du client
        System.out.println(messageClient);  //Affiche le message du client

        String ipClient = this.socketClient.getRemoteSocketAddress().toString()+"\n";   //Récupère l'adresse IP du client
        this.envoiMessage(this.socketClient, "Bienvenue client, vous avez pour adresse IP : "+ipClient);    //Envoie un message au client
        
        while(continuer)
        {
            try {
                if (this.in.readLine()== null) //si le socket est fermé
                {
                    continuer = false;
                    this.socketClient.close(); //on ferme le socket du coté serveur
                    System.out.println("Le client "+this.numClient+ " est déconnecté");
                    this.serveur.getConnexions().remove(numClient, this); //retire le socket de la socket
                }
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        }
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
        in = new BufferedReader (new InputStreamReader (this.socketClient.getInputStream())); //permet de lire les caractères provenant du socketduserveur
        return in.readLine();   //Renvoie le contenu de in
        }catch (IOException e){ //En cas d'erreur
            e.printStackTrace();
            return null;
        }
    }
}
