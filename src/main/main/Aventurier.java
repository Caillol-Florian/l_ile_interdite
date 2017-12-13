package main.main;

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
        return tuile.getNom;
    }

    public ArrayList getTuilesAccesibles(Grille g){
        ArrayList<Tuile> tuilesAdjacentes = g.getTuilesAdjacentes(getPosition());

        return null;
    }

    public ArrayList getTuilesAssechable(Grille g){
        ArrayList<Tuile> tuilesAdjacentes = g.getTuilesAdjacentes(getPosition();

        return null;
    }


    public Tuile getTuile() {
        return tuile;
    }

    public void setTuile(Tuile tuile) {
        this.tuile = tuile;
    }
}
