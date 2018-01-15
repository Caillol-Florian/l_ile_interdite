/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.main;

/**
 *
 * @author souliern
 */
public enum NOM_TUILE {
    EAU("Eau", null),
    LE_PONT_DES_ABIMES ("Le Pont des Abimes", "images/tuiles/LePontDesAbimes"),
    LA_PORTE_DE_BRONZE ("La Porte de Bronze", "images/tuiles/LaPorteDeBronze"),
    LA_CAVERNE_DES_OMBRES ("La Caverne des Ombres", "images/tuiles/LaCarverneDesOmbres"),
    LA_PORTE_DE_FER("La Porte de Fer", "images/tuiles/LaPorteDeFer"),
    LA_PORTE_D_OR("La Porte d'Or", "images/tuiles/LaPortedOr"),
    LES_FALAISES_DE_L_OUBLI("Les Falaises de l'Oubli", "images/tuiles/LesFalaisesDeLOubli"),
    LE_PALAIS_DE_CORAIL("Le Palais de Corail", "images/tuiles/LePalaisDeCorail"),
    LA_PORTE_D_ARGENT("La Porte d'Argent", "images/tuiles/LaPortedArgent"),
    LES_DUNES_DE_L_ILLUSION("Les Dunes de l'Illusion", "images/tuiles/LesDunesDeLIllusion"),
    HELIPORT("Heliport", "images/tuiles/Heliport"),
    LA_PORTE_DE_CUIVRE("La Porte de Cuivre", "images/tuiles/LaPorteDeCuivre"),
    LE_JARDIN_DES_HURLEMENTS("Le Jardin des Hurlements", "images/tuiles/LeJardinDesHurlements"),
    LA_FORET_POURPRE("La Forêt Pourpre", "images/tuiles/LaForetPourpre"),
    LE_LAGON_PERDU("Le Lagon Perdu", "images/tuiles/LeLagonPerdu"),
    LE_MARAIS_BRUMEUX("Le Marais Brumeux", "images/tuiles/LeMaraisBrumeux"),
    OBSERVATOIRE("Observatoire", "images/tuiles/Observatoire"),
    LE_ROCHER_FANTOME("Le Rocher Fantôme", "images/tuiles/LeRocherFantome"),
    LA_CAVERNE_DU_BRASIER("La Caverne du Brasier", "images/tuiles/LaCarverneDuBrasier"),
    LE_TEMPLE_DU_SOLEIL("Le Temple du Soleil", "images/tuiles/LeTempleDuSoleil"),
    LE_TEMPLE_DE_LA_LUNE("Le Temple de La Lune", "images/tuiles/LeTempleDeLaLune"),
    LE_PALAIS_DES_MAREES("Le Palais des Marees", "images/tuiles/LePalaisDesMarees"),
    LE_VAL_DU_CREPUSCULE("Le Val du Crepuscule", "images/tuiles/LeValDuCrepuscule"),
    LA_TOUR_DU_GUET("La Tour du Guet", "images/tuiles/LaTourDuGuet"),
    LE_JARDIN_DES_MURMURES("Le Jardin des Murmures", "images/tuiles/LeJardinDesMurmures");

    private final String name ;
    private final String path ;


    NOM_TUILE(String name, String path) {
        this.name = name ;
        this.path = path;
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
}
