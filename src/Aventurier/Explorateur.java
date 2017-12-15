package Aventurier;

import Aventurier.Aventurier;
import main.main.Grille;
import main.main.Messages;
import main.main.NOM_TUILE;
import main.main.Tuile;

import java.util.ArrayList;

public class Explorateur extends Aventurier {

    public Explorateur(Tuile tuile, String nom) {
        super(tuile, nom);
    }

    @Override
    public ArrayList getTuilesAccesibles(Grille g){
        ArrayList<Tuile>tuilesAccessibles = g.getTuilesDiagonales(getPosition(), Messages.DEPLACER);
        tuilesAccessibles.addAll(g.getTuilesAdjacentes(getPosition(), Messages.DEPLACER));
        return tuilesAccessibles;
    }
    public ArrayList getTuilesAssechables(Grille g){
        ArrayList<Tuile>tuilesAssechables = g.getTuilesDiagonales(getPosition(), Messages.ASSECHER);
        tuilesAssechables.addAll(g.getTuilesAdjacentes(getPosition(), Messages.ASSECHER));
        return tuilesAssechables;
    }
}
