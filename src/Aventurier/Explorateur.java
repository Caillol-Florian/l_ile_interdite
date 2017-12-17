package Aventurier;

import Aventurier.Aventurier;
import main.main.*;
import main.main.Utils.Pion;
import java.util.ArrayList;

public class Explorateur extends Aventurier {

    public Explorateur(Tuile tuile, String nom) {
        super(tuile, nom);
        setNomRole(NOM_AVENTURIER.EXPLORATEUR);
        setPion(Pion.VERT);
    }

    @Override
    public ArrayList getTuilesAccesibles(Grille g){
        ArrayList<Tuile>tuilesAccessibles = g.getTuilesDiagonales(getPosition(), Messages.DEPLACER);
        tuilesAccessibles.addAll(g.getTuilesAdjacentes(getPosition(), Messages.DEPLACER));
        return tuilesAccessibles;
    }

    @Override
    public ArrayList getTuilesAssechables(Grille g){
        ArrayList<Tuile>tuilesAssechables = g.getTuilesDiagonales(getPosition(), Messages.ASSECHER);
        tuilesAssechables.addAll(g.getTuilesAdjacentes(getPosition(), Messages.ASSECHER));
        return tuilesAssechables;
    }
}
