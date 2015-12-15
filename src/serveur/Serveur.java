package serveur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Serveur {
    public static void main(String[] zero) {
        
        ServerSocket socketserver; //Variable du socket du serveur permettant aux clients de s'y connecter
        Socket socketduserveur = null;  //Initialisation d'un socket pour la communication avec le/les clients
        BufferedReader in;
        
        try {
            socketserver = new ServerSocket(2009);  //Initialisation d'un ServerSocket sur le port 2009
            System.out.println("Le serveur est à l'écoute du port "+socketserver.getLocalPort());   //Indique sur quel port le serveur est à l'écoute
            int i = 0;  //Initialisation d'un compteur
            while(i<5) //Pendant 5 itérations
            {
                socketduserveur = socketserver.accept(); //Le Socket attend un socket de la part du client
                System.out.println("Un client s'est connecté !");   //Le client s'est connecté
//                String ipClient = socketduserveur.getRemoteSocketAddress().toString()+"\n";   //Récupère l'adresse IP du client
                in = new BufferedReader (new InputStreamReader (socketduserveur.getInputStream())); //permet de lire les caractères provenant du socketduserveur
                System.out.println(in.readLine());
                socketduserveur.close();    //Le socket se ferme
                i++;    //Le compteur augmente
            }
                socketserver.close();   //Le SocketServer se ferme
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}