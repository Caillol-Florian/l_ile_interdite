package Views;

import main.main.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Observer;
import java.util.Observable;
import main.main.NOM_TUILE;

public abstract class Vue extends Observable {
    public Vue(){}
    public void abonner(Observer ecoutant){ this.addObserver(ecoutant);}
    public abstract void setVisible(Boolean b);
    public void setPosition(String pos){}
    public void setAvailableTuile(ArrayList<String> arTuile){}
    public NOM_TUILE getTuileSelectionnee(){return null;}
    public NOM_AVENTURIER getRoleSelectionne(){return null;}
    public String getNom(){return null;}
    public void resetInscription(NOM_AVENTURIER nom_aventurier){}
    public void updateVue(String pos, String nom, NOM_AVENTURIER nomRole, Color couleur){}
    public JButton getBtnFinir(){return null;}
}
