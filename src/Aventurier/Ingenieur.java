package Aventurier;

import Aventurier.Aventurier;
import main.main.Tuile;

import main.main.Utils.Pion;
public class Ingenieur extends Aventurier {

    public Ingenieur(Tuile tuile, String nom) {
        super(tuile, nom);
        setNomRole("Ingénieur");
        setPion(Pion.ROUGE);
    }
}
