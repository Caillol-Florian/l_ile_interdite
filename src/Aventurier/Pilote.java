package Aventurier;

import main.main.Grille;
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
    @Override
    public ArrayList getTuilesAccesibles(Grille g){
        return g.getTuilesNonCoulee(getPosition());
    }
}
