package Aventurier;

import main.main.Grille;
import main.main.Messages;
import main.main.Tuile;

import java.util.ArrayList;

public abstract class Aventurier {

    private Tuile tuile;
    private String nom;
    private final static int nombreAction = 3;

    public Aventurier(Tuile tuile, String nom){
        setTuile(tuile);
        setNom(nom);
    }

    public int getNombreAction(){
        return nombreAction;
    }

    public Tuile getPosition(){
        return tuile;
    }

    public ArrayList getTuilesAccesibles(Grille g){
        return g.getTuilesAdjacentes(getPosition(), Messages.DEPLACER);
    }

    public ArrayList getTuilesAssechables(Grille g){
        return g.getTuilesAdjacentes(getPosition(), Messages.ASSECHER);
    }

    public void setTuile(Tuile tuile) {
        this.tuile = tuile;
    }

    public String getNom(){return nom;}

    public void setNom(String nom){this.nom=nom;}
}
