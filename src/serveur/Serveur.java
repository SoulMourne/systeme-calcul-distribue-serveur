package serveur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Serveur {
    public static void main(String[] zero) 
    {
        ServerSocket socketserver; //Variable du socket du serveur permettant aux clients de s'y connecter
        Socket socketduserveur = null;  //Initialisation d'un socket pour la communication avec le/les clients
        BufferedReader in = null;
        PrintWriter out;
        
        try {
            socketserver = new ServerSocket(2009);  //Initialisation d'un ServerSocket sur le port 2009
            System.out.println("Le serveur est à l'écoute du port "+socketserver.getLocalPort());   //Indique sur quel port le serveur est à l'écoute
            int i = 0;  //Initialisation d'un compteur
            while(i<5) //Pendant 5 itérations
            {
                socketduserveur = socketserver.accept(); //Le Socket attend un socket de la part du client
                System.out.println("Un client s'est connecté !");   //Le client s'est connecté
                
                in = new BufferedReader (new InputStreamReader (socketduserveur.getInputStream())); //permet de lire les caractères provenant du socketduserveur
                System.out.println(in.readLine());
                
                String ipClient = socketduserveur.getRemoteSocketAddress().toString()+"\n";   //Récupère l'adresse IP du client
                out = new PrintWriter(socketduserveur.getOutputStream());   //Récupère l'OutputStream du socket du client et ouvre un PrintWriter permettant au serveur d'y écrire
                out.println("Bienvenue client, vous avez pour adresse IP : "+ipClient); //Envoi d'un message au client ainsi que son adresse IP
                out.flush();    //Vide l'OutputStream
                socketduserveur.close();    //Le socket se ferme
                i++;    //Le compteur augmente
            }
                socketserver.close();   //Le SocketServer se ferme
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}