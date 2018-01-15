package Aventurier;

import main.main.*;
import java.util.ArrayList;

public class Pilote extends Aventurier {

    public Pilote(Tuile tuile, String nom) {
        super(tuile, nom);
        setNomRole(NOM_AVENTURIER.PILOTE);
        setPion(PION.BLEU);
    }

    public ArrayList getTuilesAccesibles(Grille g, Boolean specialUtilisé){
        if (!specialUtilisé) {
            return g.getTuilesNonCoulee(getPosition());
        } else {
            return g.getTuilesAdjacentes(getPosition(), Messages.DEPLACER);
        }
    }
}
