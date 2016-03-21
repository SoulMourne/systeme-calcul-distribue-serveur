package experience;

import java.lang.Double;

/**
 * Classe qui permet d'enregistrer les arguments via un intervalle
 */
public class ArgumentInterval extends Argument
{
	/**
	 * Argument minimum à exécuter.
	 */
	private double argumentMin;

	/**
	 * Argument maximum à exécuter.
	 */
	private double argumentMax;

	/**
	 * Pas entre deux arguments. Permet de faire des intervalle de argument qui évolue de 2 en 2 par exemple.
	 */
	private double step;

	/**
	 * Argument actuel du parcours de l'ensemble des arguments
	 */
	private double current;

	/**
	 * Constructeur par défaut avec un pas de 0 par défaut
	 * @param minimum Minimum de l'intervalle
	 * @param maximum Maximum de l'intervalle
	 */
	public ArgumentInterval(double minimum, double maximum)
	{
		this.argumentMin = minimum;
		this.argumentMax = maximum;
		this.step = 1;
		this.current = minimum;
	}

	/**
	 * Constructeur par défaut avec un pas de 0 par défaut
	 * @param minimum Minimum de l'intervalle
	 * @param maximum Maximum de l'intervalle
	 * @param step Pas de l'interval
	 */
	public ArgumentInterval(double minimum, double maximum, double step)
	{
		this.argumentMin = minimum;
		this.argumentMax = maximum;
		this.step = step;
		this.current = minimum;
	}

	/**
     * Permet de savoir si il reste des arguments dans le parcours des arguments
     * @return true si il reste des arguments, false sinon
     */
    public boolean hasNext()
    {
    	if (this.argumentMax == this.current+this.step)
    		return false;
    	else
    		return true;
    }

    /**
     * Retourne le prochain argument du parcours
     * @return argument sous forme de chaine de caractère
     */
    public String next()
    {
    	String resultat = Double.toString(this.current);
    	this.current += this.step;
    	return resultat;
    }
}
