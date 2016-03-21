package experience;

import java.util.ArrayList;
import java.util.Iterator;
import java.io.*;

/**
 * Classe qui permet d'enregistrer les tuples d'arguments à exécuter
 */
public class ArgumentCombinaison extends Argument
{
	/**
	 * Combinaison d'argument à exécuter
	 */
	private ArrayList<String> arguments;

	/**
	 * Itérateur de l'ArrayList des arguments
	 */
	private Iterator iterateur;

	/**
	 * Constructeur par défaut avec un fichier
	 */
	public ArgumentCombinaison(String fileName)
	{
		this.arguments = new ArrayList<String>();
		String line = null;

        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

			int i = 0;
			while((line = bufferedReader.readLine()) != null)
			{
		        this.arguments.add(line);
			}   
			bufferedReader.close();         
		}
		catch(FileNotFoundException ex) {
		    System.out.println("Unable to open file '" + fileName + "'");
		}
		catch(IOException ex) {
		    System.out.println("Error reading file '" + fileName + "'");                  
		}
		this.iterateur = this.arguments.iterator();
	}

	/**
     * Permet de savoir si il reste des arguments dans le parcours des arguments
     * @return true si il reste des arguments, false sinon
     */
    public boolean hasNext()
    {
    	return iterateur.hasNext();
    }

    /**
     * Retourne le prochain argument du parcours
     * @return argument sous forme de chaine de caractère
     */
    public String next()
    {
    	return (String) iterateur.next();
    }
}
