package Aventurier;

import main.main.NOM_AVENTURIER;
import main.main.Tuile;
import main.main.Utils.Pion;

public class Ingenieur extends Aventurier {
    private boolean assechementBonus=false;
    public Ingenieur(Tuile tuile, String nom) {
        super(tuile, nom);
        setNomRole(NOM_AVENTURIER.INGENIEUR);
        setPion(Pion.ROUGE);
    }
    @Override
    public void setAssechementBonus(boolean b){
        assechementBonus=b;
    }
    @Override
    public boolean getAssechementBonus(){
        return assechementBonus;
    }
}
