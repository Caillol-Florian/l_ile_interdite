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
    EAU("Eau"),
    LE_PONT_DES_ABIMES ("Le Pont des Abimes"),
    LA_PORTE_DE_BRONZE ("La Porte de Bronze"),
    LA_CAVERNE_DES_OMBRES ("La Caverne des Ombres"),
    LA_PORTE_DE_FER("La Porte de Fer"),
    LA_PORTE_D_OR("La Porte d'Or"),
    LES_FALAISES_DE_L_OUBLI("Les Falaises de l'Oubli"),
    LE_PALAIS_DE_CORAIL("Le Palais de Corail"),
    LA_PORTE_D_ARGENT("La Porte d'Argent"),
    LES_DUNES_DE_L_ILLUSION("Les Dunes de l'Illusion"),
    HELIPORT("Heliport"),
    LA_PORTE_DE_CUIVRE("La Porte de Cuivre"),
    LE_JARDIN_DES_HURLEMENTS("Le Jardin des Hurlements"),
    LA_FORET_POURPRE("La Forêt Pourpre"),
    LE_LAGON_PERDU("Le Lagon Perdu"),
    LE_MARAIS_BRUMEUX("Le Marais Brumeux"),
    OBSERVATOIRE("Observatoire"),
    LE_ROCHER_FANTOME("Le Rocher Fantôme"),
    LA_CAVERNE_DU_BRASIER("La Caverne du Brasier"),
    LE_TEMPLE_DU_SOLEIL("Le Temple du Soleil"),
    LE_TEMPLE_DE_LA_LUNE("Le Temple de La Lune"),
    LE_PALAIS_DES_MAREES("Le Palais des Marees"),
    LE_VAL_DU_CREPUSCULE("Le Val du Crepuscule"),
    LA_TOUR_DU_GUET("La Tour du Guet"),
    LE_JARDIN_DES_MURMURES("Le Jardin des Murmures");
    
    private final String name ;

    NOM_TUILE(String name) {
        this.name = name ;
    }
    
    @Override
    public String toString() {
        return this.name ;
    }  
}
