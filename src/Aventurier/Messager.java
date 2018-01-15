package Aventurier;

import main.main.NOM_AVENTURIER;
import main.main.PION;
import main.main.Tuile;

public class Messager extends Aventurier {

    public Messager(Tuile tuile, String nom) {
        super(tuile, nom);
        setNomRole(NOM_AVENTURIER.MESSAGER);
        setPion(PION.VIOLET);
    }
}
