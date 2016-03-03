package serveur;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

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
     * @param parSocket Socket du client
     * @param parServeur Socket du serveur
     */
    public ServeurThread(int parNumClient, Socket parSocket, Serveur parServeur)
    {
        this.socketClient = parSocket;
        this.serveur = parServeur;
        this.numClient = parNumClient;
        try {
            this.in = new BufferedReader (new InputStreamReader (this.socketClient.getInputStream())); //permet de lire les caractères provenant du socketduserveur
        this.out = new PrintWriter(socketClient.getOutputStream());   //Récupère l'OutputStream du socket du client et ouvre un PrintWriter permettant au serveur d'y écrire
        } catch (IOException e)
        {
            System.err.println(e.getMessage());
        }
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
        
        File file = new File("src/test.txt");
        
        //A corriger au plus vite, (voir issue #5 sur Bitbucket)
        /*for(int i = 0;  i<10; i++)
        {
            this.envoiObjet(socketClient, i*2);
        }*/
        
        //this.envoiObjet(socketClient, 4);
        
        //this.envoiObjet(socketClient, (int)file.length());
        //this.envoiFichier(socketClient, file);

        
        while(continuer)
        {
            try {
                if (this.socketClient.isClosed() || this.in.readLine()== null) //si le socket est fermé
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
     * Envoie une chaîne de caractères au client via le socket correspondant
     * @param socketClient socket permettant de communiquer avec le client
     * @param message chaine de caractères à envoyer sur le socket
     * @return booléen si le message a été ou non envoyé
     */
    public boolean envoiMessage(Socket socketClient,String message)
    {
        out.println(message);
        out.flush();
        return !out.checkError();
    }
    
    /**
     * Lit un message envoyé via le socket du client
     * @param socketClient Le socket sur lequel le message est envoyé
     * @return la chaine de caractère lue ou bien null si erreur
     */
    public String lectureMessage(Socket socketClient)
    {
        try{
        return in.readLine();   //Renvoie le contenu de in
        }catch (IOException e){ //En cas d'erreur
            System.err.println(e.getMessage());
            return null;
        }
    }
    
    /**
     * Envoie un objet sur le socket client
     * Pour un envoi de fichier voir envoiFichier
     * @see envoiFichier
     * @param socketClient le socket sur lequel envoyer l'objet
     * @param o l'objet a envoyé
     * @return un boolean permettant de savoir si l'objet a été envoyé
     */
    public boolean envoiObjet(Socket socketClient, Object o)
    {
        try {
            //Connexion des flux de sortie
            ObjectOutputStream sortie = new ObjectOutputStream(this.socketClient.getOutputStream()); // On instancie un flux de sortie
            sortie.flush();
            sortie.writeObject(o); // Echange de données avec le socket client
            sortie.flush();
            sortie.close();
            } catch (IOException e) { //En cas d'erreur
                System.err.println(e.getMessage());
                    return false;
            }
            return true; // En cas de succès
    }
    
    /**
     * Envoie un fichier sur le socket client
     * @param socket le socket sur lequel envoyer l'objet
     * @param fichier le fichier à envoyer
     * @return un boolean permettant de savoir si l'objet a été envoyé
     */
    public boolean envoiFichier(Socket socket, File fichier)
    {
        //this.envoiObjet(socketClient, (int)fichier.length());       
        byte[] myByteArray = new byte[(int)fichier.length()];
        FileInputStream fis = null;
        try 
        {
            fis = new FileInputStream(fichier);
        } catch (FileNotFoundException ex) {
            System.err.println(ex.getMessage());
            return false;
        }
        BufferedInputStream bis = new BufferedInputStream(fis);
        OutputStream os = null;
        try 
        {
            bis.read(myByteArray,0,myByteArray.length);
            os = socket.getOutputStream();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
            return false;
        }
        System.out.println("Sending  " + fichier.getName()+"("+myByteArray.length+" bytes)");
        try 
        {
            os.write(myByteArray,0,myByteArray.length);
            os.flush();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
            return false;
        }
        System.out.println("Done");
        return true;
    }
}
