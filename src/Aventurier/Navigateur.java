package Aventurier;

import Aventurier.Aventurier;
import main.main.NOM_AVENTURIER;
import main.main.Tuile;

import main.main.Utils.Pion;
public class Navigateur extends Aventurier {

    public Navigateur(Tuile tuile, String nom) {
        super(tuile, nom);
        setNomRole(NOM_AVENTURIER.NAVIGATEUR);
        setPion(Pion.JAUNE);
    }
}
