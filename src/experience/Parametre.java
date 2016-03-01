package experience;

/**
 * Classe abstraite qui permet de stocker les paramètres d'exécution de l'expérience
 */
public abstract class Parametre
{
    /**
     * Permet de savoir si il reste des paramètres dans le parcours des paramètres
     * @return true si il reste des paramètres, false sinon
     */
    public abstract boolean hasNext();

    /**
     * Retourne le prochain paramètre du parcours
     * @return paramètre sous forme de chaine de caractère
     */
    public abstract String next();
}