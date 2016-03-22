/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package serveur;

/**
 * Classe de variables pour le scan Reseau
 * @author sahmed
 */
public class ConstantesScanReseau {
    
    /**
     * Adresse reseau racine dans laquelle on testera une plage de hosts
     */
    protected static String ipRoot ="192.168.0."; 
    
    /**
     * Port a tester : ici 22 pour permettre l'installation des clients en ssh
     */
    protected static final int port = 22;
    
    /**
     * Port spécifique windows
     */
    protected static final int portWindows = 445;
    
    /**
     * Timeout en millisecondes
     */
    protected static final int timeout = 100;
    
    /**
     * Valeur minimum du host ( pour la boucle Thread dans le Main)
     * Si l'on test tout les hosts, hostMin = 0
     */
    protected static final int hostMin = 126;
    
    /**
     * Valeur maximum du host ( pour la boucle Thread dans le Main)
     * Si l'on test tout les hosts, hostMax = 255
     */
    protected static final int hostMax = 139;
    
}
