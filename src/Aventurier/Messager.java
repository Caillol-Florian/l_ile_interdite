package Aventurier;

import Aventurier.Aventurier;
import main.main.NOM_AVENTURIER;
import main.main.Tuile;
import main.main.Utils;

import main.main.Utils.Pion;
public class Messager extends Aventurier {

    public Messager(Tuile tuile, String nom) {
        super(tuile, nom);
        setNomRole(NOM_AVENTURIER.MESSAGER);
        setPion(Pion.VIOLET);
    }
}
