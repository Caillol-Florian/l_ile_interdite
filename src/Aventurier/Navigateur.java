package Aventurier;

import main.main.NOM_AVENTURIER;
import main.main.PION;
import main.main.Tuile;

public class Navigateur extends Aventurier {

    public Navigateur(Tuile tuile, String nom) {
        super(tuile, nom);
        setNomRole(NOM_AVENTURIER.NAVIGATEUR);
        setPion(PION.JAUNE);
    }
}
