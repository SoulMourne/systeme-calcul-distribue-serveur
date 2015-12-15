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
    private BufferedReader in;
    private PrintWriter out;
    private Socket socketDuServeur;
    
    public Serveur(int numPort)
    {
        socketDuServeur = null;  //Initialisation d'un socket pour la communication avec le/les clients
        
        try {
            socketServer = new ServerSocket(2009);  //Initialisation d'un ServerSocket sur le port 2009
            System.out.println("Le serveur est à l'écoute du port "+socketServer.getLocalPort());   //Indique sur quel port le serveur est à l'écoute
        } catch (Exception e) {

        }
        
    }
    
    public static void main(String[] zero) 
    {
        Serveur serveur = new Serveur(2009); //Ouverture du serveur
        
        int i = 0;  //Initialisation d'un compteur
        while(i<5) //Pendant 5 itérations
        {
            serveur.accepterClient();
            
            String messageClient = serveur.lectureMessage(serveur.socketDuServeur);
            System.out.println(messageClient);
            
            String ipClient = serveur.socketDuServeur.getRemoteSocketAddress().toString()+"\n";   //Récupère l'adresse IP du client
            serveur.envoiMessage(serveur.socketDuServeur, "Bienvenue client, vous avez pour adresse IP : "+ipClient);
            
            serveur.fermerClient();
            i++;    //Le compteur augmente
        }
        serveur.fermetureServeur();
    }
    
    public boolean envoiMessage(Socket socketClient,String message)
    {
        try{
            out = new PrintWriter(socketClient.getOutputStream());   //Récupère l'OutputStream du socket du client et ouvre un PrintWriter permettant au serveur d'y écrire
            out.println(message); //Envoi d'un message au client ainsi que son adresse IP
            out.flush();    //Vide l'OutputStream
        }catch (IOException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    public String lectureMessage(Socket SocketClient)
    {
        try{
        in = new BufferedReader (new InputStreamReader (socketDuServeur.getInputStream())); //permet de lire les caractères provenant du socketduserveur
        return in.readLine();
        }catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public void fermetureServeur()
    {
        try {
            socketServer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void accepterClient()
    {
        try{
            this.socketDuServeur =socketServer.accept();
            System.out.println("Un client s'est connecté !");   //Le client s'est connecté
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    
    public void fermerClient()
    {
        try{
        this.socketDuServeur.close();
        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    
    public Socket getSocketDuServeur()
    {
        return this.socketDuServeur;
    }
    
    public ServerSocket getServerSocket()
    {
        return  this.socketServer;
    }
}