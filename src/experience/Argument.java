package experience;

/**
 * Classe abstraite qui permet de stocker les arguments d'exécution de l'expérience
 */
public abstract class Argument
{
    /**
     * Permet de savoir si il reste des arguments dans le parcours des arguments
     * @return true si il reste des arguments, false sinon
     */
    public abstract boolean hasNext();

    /**
     * Retourne le prochain argument du parcours
     * @return argument sous forme de chaine de caractère
     */
    public abstract String next();
}
