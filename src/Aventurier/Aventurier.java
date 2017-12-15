package Aventurier;

import main.main.Grille;
import main.main.Messages;
import main.main.Tuile;

import java.util.ArrayList;

public abstract class Aventurier {

    private Tuile tuile;
    private final static int nombreAction = 3;

    public Aventurier(Tuile tuile){
        setTuile(tuile);
    }

    public int getNombreAction(){
        return nombreAction;
    }

    public Tuile getPosition(){
        return tuile;
    }

    public ArrayList getTuilesAccesibles(Grille g){
        ArrayList<Tuile> tuilesAdjacentes = g.getTuilesAdjacentes(getPosition(), Messages.DEPLACER);
        return tuilesAdjacentes;
    }

    public ArrayList getTuilesAssechable(Grille g){
        ArrayList<Tuile> tuilesAdjacentes = g.getTuilesAdjacentes(getPosition(), Messages.ASSECHER);
        return tuilesAdjacentes;
    }

    public Tuile getTuile() {
        return tuile;
    }

    public void setTuile(Tuile tuile) {
        this.tuile = tuile;
    }
}
