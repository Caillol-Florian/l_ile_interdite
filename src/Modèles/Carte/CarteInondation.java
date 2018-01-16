package Modèles.Carte;

import Modèles.Tuile;

public class CarteInondation {

    private Tuile tuile;

    public CarteInondation(Tuile tuile){
        this.tuile = tuile;
    }

    public Tuile getTuile(){
        return tuile;
    }
}
