package Modèles.Aventurier;

import Enums.Messages;
import Enums.NOM_AVENTURIER;
import Enums.PION;
import Modèles.*;
import Modèles.Carte.CarteStockable;

import java.util.ArrayList;

public abstract class Aventurier {

    private Tuile tuile;
    private String nomJoueur;
    private NOM_AVENTURIER nomRole;
    private PION pion;
    private ArrayList<Tresor>tresors = new ArrayList<>();
    private ArrayList<CarteStockable>cartes = new ArrayList<>();

    private final static int nombreAction = 3;

    public Aventurier(Tuile tuile, String nomJoueur){
        setPosition(tuile);
        setNomJoueur(nomJoueur);
    }

    public int getNombreAction(){
        return nombreAction;
    }

    public Tuile getPosition(){
        return tuile;
    }

    public ArrayList<Integer> getTuilesAccesibles(Grille g){
        return g.getTuilesAdjacentes(getPosition(), Messages.DEPLACER);
    }

    public ArrayList<Integer> getTuilesAssechables(Grille g){
        return g.getTuilesAdjacentes(getPosition(), Messages.ASSECHER);
    }

    public void setPosition(Tuile tuile) {
        this.tuile = tuile;
    }

    public String getNomJoueur(){return nomJoueur;}

    public void setNomJoueur(String nomJoueur){this.nomJoueur = nomJoueur;}

    public void setNomRole(NOM_AVENTURIER nomRole){this.nomRole = nomRole;}

    public NOM_AVENTURIER getNomRole(){return nomRole;}

    public PION getPion() {
        return pion;
    }

    public void setPion(PION pion) {
        this.pion = pion;
    }

    public ArrayList<Tresor> getTresors() {
        return tresors;
    }

    public void addTresor(Tresor tresor) {
        this.tresors.add(tresor);
    }

    public ArrayList<CarteStockable> getCartes() {
        return cartes;
    }

    public void addCarte(CarteStockable carte) {
        this.cartes.add(carte);
    }
}
