package experience;

import java.lang.Double;

/**
 * Classe qui permet d'enregistrer les paramètres via un intervalle
 */
public class ParametreInterval extends Parametre
{
	/**
	 * Paramètre minimum à exécuter.
	 */
	private double parametreMin;

	/**
	 * Paramètre maximum à exécuter.
	 */
	private double parametreMax;

	/**
	 * Pas entre deux paramètres. Permet de faire des intervalle de paramètre qui évolue de 2 en 2 par exemple.
	 */
	private double step;

	/**
	 * Parametre actuel du parcours de l'ensemble des paramètres
	 */
	private double current;

	/**
	 * Constructeur par défaut avec un pas de 0 par défaut
	 * @param minimum Minimum de l'intervalle
	 * @param maximum Maximum de l'intervalle
	 */
	public ParametreInterval(double minimum, double maximum)
	{
		this.parametreMin = minimum;
		this.parametreMax = maximum;
		this.step = 1;
		this.current = minimum;
	}

	/**
	 * Constructeur par défaut avec un pas de 0 par défaut
	 * @param minimum Minimum de l'intervalle
	 * @param maximum Maximum de l'intervalle
	 * @param step Pas de l'interval
	 */
	public ParametreInterval(double minimum, double maximum, double step)
	{
		this.parametreMin = minimum;
		this.parametreMax = maximum;
		this.step = step;
		this.current = minimum;
	}

	/**
     * Permet de savoir si il reste des paramètres dans le parcours des paramètres
     * @return true si il reste des paramètres, false sinon
     */
    public boolean hasNext()
    {
    	if (this.parametreMax == this.current+this.step)
    		return false;
    	else
    		return true;
    }

    /**
     * Retourne le prochain paramètre du parcours
     * @return paramètre sous forme de chaine de caractère
     */
    public String next()
    {
    	String resultat = Double.toString(this.current);
    	this.current += this.step;
    	return resultat;
    }
}