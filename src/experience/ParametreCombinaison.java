package experience;

import java.util.ArrayList;
import java.util.Iterator;
import java.io.*;

/**
 * Classe qui permet d'enregistrer les tuples de paramètres à exécuter
 */
public class ParametreCombinaison extends Parametre
{
    /**
     * Combinaison de paramètre à exécuter
     */
    private ArrayList<String> parametres;

    /**
     * Itérateur de l'ArrayList des paramètres
     */
    private Iterator iterateur;

    /**
     * Constructeur par défaut avec un fichier
     * @param fileName nom du fichier de paramètre
     */
    public ParametreCombinaison(String fileName)
    {
        this.parametres = new ArrayList<String>();
        String line = null;

        try {
                FileReader fileReader = new FileReader(fileName);
                BufferedReader bufferedReader = new BufferedReader(fileReader);

                int i = 0;
                while((line = bufferedReader.readLine()) != null)
                {
                this.parametres.add(line);
                }   
                bufferedReader.close();         
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");
        }
        catch(IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");                  
        }
        this.iterateur = this.parametres.iterator();
    }

	/**
     * Permet de savoir si il reste des paramètres dans le parcours des paramètres
     * @return true si il reste des paramètres, false sinon
     */
    public boolean hasNext()
    {
    	return iterateur.hasNext();
    }

    /**
     * Retourne le prochain paramètre du parcours
     * @return paramètre sous forme de chaine de caractère
     */
    public String next()
    {
    	return (String) iterateur.next();
    }
}