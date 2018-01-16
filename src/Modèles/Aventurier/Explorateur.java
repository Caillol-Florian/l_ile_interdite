package Modèles.Aventurier;

import Enums.NOM_AVENTURIER;
import Enums.PION;
import Modèles.Grille;
import Enums.Messages;
import Modèles.Tuile;
import java.util.ArrayList;

public class Explorateur extends Aventurier {

    public Explorateur(Tuile tuile, String nom) {
        super(tuile, nom);
        setNomRole(NOM_AVENTURIER.EXPLORATEUR);
        setPion(PION.VERT);
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
