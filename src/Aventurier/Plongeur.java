package Aventurier;

import Aventurier.Aventurier;
import main.main.NOM_AVENTURIER;
import main.main.Tuile;
import main.main.Utils;

public class Plongeur extends Aventurier {

    public Plongeur(Tuile tuile, String nom) {
        super(tuile, nom);
        setNomRole(NOM_AVENTURIER.PLONGEUR);
        setPion(Utils.Pion.ORANGE);
    }
}
