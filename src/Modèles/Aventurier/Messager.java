package Modèles.Aventurier;

import Enums.NOM_AVENTURIER;
import Enums.PION;
import Modèles.Tuile;

public class Messager extends Aventurier {

    public Messager(Tuile tuile, String nom) {
        super(tuile, nom);
        setNomRole(NOM_AVENTURIER.MESSAGER);
        setPion(PION.VIOLET);
    }
}
