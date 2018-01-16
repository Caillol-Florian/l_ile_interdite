package Modèles.Aventurier;

import Enums.NOM_AVENTURIER;
import Enums.PION;
import Modèles.Tuile;

public class Ingenieur extends Aventurier {

    private final static int nombreActions = 4;

    public Ingenieur(Tuile tuile, String nom) {
        super(tuile, nom);
        setNomRole(NOM_AVENTURIER.INGENIEUR);
        setPion(PION.ROUGE);
    }
}
