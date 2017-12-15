package Views;

import main.main.NOM_TUILE;
import main.main.Tuile;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Observer;
import java.util.Observable;


public abstract class Vue extends Observable {
    public Vue(){};
    public void abonner(Observer ecoutant){ this.addObserver(ecoutant);}
    public abstract void setVisible(Boolean b);
    public void setPosition(String pos){};
    public void setAvailableTuile(ArrayList<Tuile> arTuile){}
    public abstract NOM_TUILE getTuileSelectionnee();

}
