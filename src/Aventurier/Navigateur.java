package Aventurier;

import Aventurier.Aventurier;
import main.main.Tuile;

import main.main.Utils.Pion;
public class Navigateur extends Aventurier {

    public Navigateur(Tuile tuile, String nom) {
        super(tuile, nom);
        setNomRole("Navigateur");
        setPion(Pion.JAUNE);
    }
}
