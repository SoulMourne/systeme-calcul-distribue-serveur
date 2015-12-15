package serveur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Serveur 
{
    private ServerSocket socketServer;//Variable du socket du serveur permettant aux clients de s'y connecter
    private BufferedReader in;  //Permet de lire des caractères
    private PrintWriter out;    //Permet d'écrire un message
    private Socket socketDuClient;  //Socket servant à communiquer avec le client
    
    public Serveur(int numPort)
    {
        socketDuClient = null;  //Initialisation d'un socket pour la communication avec le/les clients
        
        try {
            socketServer = new ServerSocket(2009);  //Initialisation d'un ServerSocket sur le port 2009
            System.out.println("Le serveur est à l'écoute du port "+socketServer.getLocalPort());   //Indique sur quel port le serveur est à l'écoute
        } catch (IOException e) {   //En cas d'erreur
            e.printStackTrace();
        }
        
    }
    
    public static void main(String[] zero) 
    {
        Serveur serveur = new Serveur(2009); //Ouverture du serveur
        
        int i = 0;  //Initialisation d'un compteur
        while(i<5) //Pendant 5 itérations
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
    
    public String lectureMessage(Socket SocketClient)
    {
        try{
        in = new BufferedReader (new InputStreamReader (socketDuClient.getInputStream())); //permet de lire les caractères provenant du socketduserveur
        return in.readLine();   //Renvoie le contenu de in
        }catch (IOException e){ //En cas d'erreur
            e.printStackTrace();
            return null;
        }
    }

    public void fermetureServeur()
    {
        try {
            socketServer.close();   //Ferme le socket serveur
        } catch (IOException ex) {  //En cas d'erreur
            ex.printStackTrace();
        }
    }
    
    public void accepterClient()
    {
        try{
            this.socketDuClient =socketServer.accept(); //Attend la connexion d'un client
            System.out.println("Un client s'est connecté !");   //Le client s'est connecté
        }
        catch(IOException e){   //En cas d'erreur
            e.printStackTrace();
        }
    }
    
    public void fermerClient()
    {
        try{
        this.socketDuClient.close();    //Ferme le socket du client
        }catch(IOException e){  //En cas d'erreur
            e.printStackTrace();
        }
    }
    
    public Socket getSocketDuClient()
    {
        return this.socketDuClient; //Renvoie le socket du client
    }
    
    public ServerSocket getServerSocket()
    {
        return  this.socketServer;  //Renvoie le socket serveur
    }
}