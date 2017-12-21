package Aventurier;

import main.main.Grille;
import main.main.Messages;
import main.main.NOM_AVENTURIER;
import main.main.Tuile;
import java.util.ArrayList;
import main.main.Utils.Pion;

public abstract class Aventurier {

    private Tuile tuile;
    private String nomJoueur;
    private NOM_AVENTURIER nomRole;
    private Pion pion;
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

    public ArrayList getTuilesAccesibles(Grille g){
        return g.getTuilesAdjacentes(getPosition(), Messages.DEPLACER);
    }

    public ArrayList getTuilesAssechables(Grille g){
        return g.getTuilesAdjacentes(getPosition(), Messages.ASSECHER);
    }

    public void setPosition(Tuile tuile) {
        this.tuile = tuile;
    }

    public String getNomJoueur(){return nomJoueur;}

    public void setNomJoueur(String nomJoueur){this.nomJoueur = nomJoueur;}

    public void setNomRole(NOM_AVENTURIER nomRole){this.nomRole = nomRole;}

    public NOM_AVENTURIER getNomRole(){return nomRole;}

    public Pion getPion() {
        return pion;
    }

    public void setPion(Pion pion) {
        this.pion = pion;
    }
}
