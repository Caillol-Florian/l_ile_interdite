package Views;

import Enums.NOM_AVENTURIER;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Observer;
import java.util.Observable;
import Enums.NOM_TUILE;

public abstract class Vue extends Observable {

    public Vue(){}
    public void abonner(Observer ecoutant){ this.addObserver(ecoutant);}
    public abstract void setVisible(Boolean b);

    // =============================
    // Vue Inscription
    public NOM_AVENTURIER getRoleSelectionne(){return null;}
    public String getNom(){return null;}
    public void resetInscription(NOM_AVENTURIER nom_aventurier){}

    // =============================
    // Vue Aventurier
    public void setPosition(String pos){}

    // =============================
    // Vue Assèchement / Déplacement
    public void setAvailableTuile(ArrayList<String> arTuile){}
    public NOM_TUILE getTuileSelectionnee(){return null;}

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }
}
