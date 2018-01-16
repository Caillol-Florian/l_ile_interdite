/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Enums;

/**
 *
 * @author souliern
 */
public enum NOM_TUILE {
    EAU("Eau", null, null),
    LE_PONT_DES_ABIMES ("Le Pont des Abimes", "images/tuiles/LePontDesAbimes", null),
    LA_PORTE_DE_BRONZE ("La Porte de Bronze", "images/tuiles/LaPorteDeBronze", null),
    LA_CAVERNE_DES_OMBRES ("La Caverne des Ombres", "images/tuiles/LaCarverneDesOmbres", TYPE_TRESOR.CRISTAL),
    LA_PORTE_DE_FER("La Porte de Fer", "images/tuiles/LaPorteDeFer", null),
    LA_PORTE_D_OR("La Porte d'Or", "images/tuiles/LaPortedOr", null),
    LES_FALAISES_DE_L_OUBLI("Les Falaises de l'Oubli", "images/tuiles/LesFalaisesDeLOubli", null),
    LE_PALAIS_DE_CORAIL("Le Palais de Corail", "images/tuiles/LePalaisDeCorail", TYPE_TRESOR.CALICE),
    LA_PORTE_D_ARGENT("La Porte d'Argent", "images/tuiles/LaPortedArgent", null),
    LES_DUNES_DE_L_ILLUSION("Les Dunes de l'Illusion", "images/tuiles/LesDunesDeLIllusion", null),
    HELIPORT("Heliport", "images/tuiles/Heliport", null),
    LA_PORTE_DE_CUIVRE("La Porte de Cuivre", "images/tuiles/LaPorteDeCuivre", null),
    LE_JARDIN_DES_HURLEMENTS("Le Jardin des Hurlements", "images/tuiles/LeJardinDesHurlements", TYPE_TRESOR.ZEPHYR),
    LA_FORET_POURPRE("La Forêt Pourpre", "images/tuiles/LaForetPourpre", null),
    LE_LAGON_PERDU("Le Lagon Perdu", "images/tuiles/LeLagonPerdu", null),
    LE_MARAIS_BRUMEUX("Le Marais Brumeux", "images/tuiles/LeMaraisBrumeux", null),
    OBSERVATOIRE("Observatoire", "images/tuiles/Observatoire", null),
    LE_ROCHER_FANTOME("Le Rocher Fantôme", "images/tuiles/LeRocherFantome", null),
    LA_CAVERNE_DU_BRASIER("La Caverne du Brasier", "images/tuiles/LaCarverneDuBrasier", TYPE_TRESOR.CRISTAL),
    LE_TEMPLE_DU_SOLEIL("Le Temple du Soleil", "images/tuiles/LeTempleDuSoleil", TYPE_TRESOR.PIERRE),
    LE_TEMPLE_DE_LA_LUNE("Le Temple de La Lune", "images/tuiles/LeTempleDeLaLune", TYPE_TRESOR.PIERRE),
    LE_PALAIS_DES_MAREES("Le Palais des Marees", "images/tuiles/LePalaisDesMarees", TYPE_TRESOR.CALICE),
    LE_VAL_DU_CREPUSCULE("Le Val du Crepuscule", "images/tuiles/LeValDuCrepuscule", null),
    LA_TOUR_DU_GUET("La Tour du Guet", "images/tuiles/LaTourDuGuet", null),
    LE_JARDIN_DES_MURMURES("Le Jardin des Murmures", "images/tuiles/LeJardinDesMurmures", TYPE_TRESOR.ZEPHYR);

    private final String name ;
    private final String path ;
    private final TYPE_TRESOR tresor;

    NOM_TUILE(String name, String path, TYPE_TRESOR tresor) {
        this.name = name ;
        this.path = path;
        this.tresor = tresor;
    }

    @Override
    public String toString() {
        return this.name ;
    }

    public String getPath(){
        return path + ".png";
    }

    public String getPathInonde(){
        return path + "_Inonde.png";
    }

    public TYPE_TRESOR getTresor(){return tresor;}
}
