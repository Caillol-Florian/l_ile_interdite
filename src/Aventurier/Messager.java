package Aventurier;

import Aventurier.Aventurier;
import main.main.Tuile;
import main.main.Utils;

import main.main.Utils.Pion;
public class Messager extends Aventurier {

    public Messager(Tuile tuile, String nom) {
        super(tuile, nom);
        setNomRole("Messager");
        setPion(Pion.VIOLET);
    }
}
