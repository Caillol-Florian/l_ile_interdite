package Modèles.Aventurier;

import Enums.NOM_AVENTURIER;
import Enums.PION;
import Modèles.Tuile;

public class Navigateur extends Aventurier {

    public Navigateur(Tuile tuile, String nom) {
        super(tuile, nom);
        setNomRole(NOM_AVENTURIER.NAVIGATEUR);
        setPion(PION.JAUNE);
    }
}
