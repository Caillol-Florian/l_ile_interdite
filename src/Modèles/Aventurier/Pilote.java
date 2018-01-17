package Modèles.Aventurier;

import Enums.NOM_AVENTURIER;
import Enums.PION;
import Modèles.Grille;
import Enums.Messages;
import Modèles.Tuile;
import java.util.ArrayList;

public class Pilote extends Aventurier {

    public Pilote(Tuile tuile, String nom) {
        super(tuile, nom);
        setNomRole(NOM_AVENTURIER.PILOTE);
        setPion(PION.BLEU);
    }

    public ArrayList<Integer> getTuilesAccesibles(Grille g, Boolean specialUtilisé){
        if (!specialUtilisé) {
            return g.getTuilesNonCoulee(getPosition());
        } else {
            return g.getTuilesAdjacentes(getPosition(), Messages.DEPLACER);
        }
    }
}
