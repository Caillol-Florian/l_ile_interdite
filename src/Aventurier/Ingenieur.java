package Aventurier;

import Aventurier.Aventurier;
import main.main.NOM_AVENTURIER;
import main.main.Tuile;

import main.main.Utils.Pion;
public class Ingenieur extends Aventurier {

    public Ingenieur(Tuile tuile, String nom) {
        super(tuile, nom);
        setNomRole(NOM_AVENTURIER.INGENIEUR);
        setPion(Pion.ROUGE);
    }
}
