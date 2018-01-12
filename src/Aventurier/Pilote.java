package Aventurier;

import main.main.Grille;
import main.main.Messages;
import main.main.NOM_AVENTURIER;
import main.main.Tuile;
import main.main.Utils.Pion;
import java.util.ArrayList;

public class Pilote extends Aventurier {

    public Pilote(Tuile tuile, String nom) {
        super(tuile, nom);
        setNomRole(NOM_AVENTURIER.PILOTE);
        setPion(Pion.BLEU);
    }

    public ArrayList getTuilesAccesibles(Grille g, Boolean specialUtilisé){
        if (!specialUtilisé) {
            return g.getTuilesNonCoulee(getPosition());
        } else {
            return g.getTuilesAdjacentes(getPosition(), Messages.DEPLACER);
        }
    }
}
