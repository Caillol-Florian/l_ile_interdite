package Aventurier;

import main.main.Grille;
import main.main.Tuile;

import java.util.ArrayList;

public abstract class Aventurier {

    private Tuile tuile;

    public Aventurier(Tuile tuile){
        setTuile(tuile);
    }

    public int getNombreAction(){
        return 0;
    }

    public Tuile getPosition(){
        return null;
    }

    public ArrayList getTuilesAccesibles(Grille g){
        return null;
    }

    public ArrayList getTuilesAssechable(Grille g){
        return null;
    }


    public Tuile getTuile() {
        return tuile;
    }

    public void setTuile(Tuile tuile) {
        this.tuile = tuile;
    }
}
