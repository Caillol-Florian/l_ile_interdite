package Aventurier;

import main.main.NOM_AVENTURIER;
import main.main.PION;
import main.main.Tuile;

public class Ingenieur extends Aventurier {

    private final static int nombreActions = 4;

    public Ingenieur(Tuile tuile, String nom) {
        super(tuile, nom);
        setNomRole(NOM_AVENTURIER.INGENIEUR);
        setPion(PION.ROUGE);
    }
}
