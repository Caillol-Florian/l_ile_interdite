package Views;

import javax.swing.*;
import java.util.Observer;
import java.util.Observable;


public abstract class Vue extends Observable {
    public Vue(){};
    public void abonner(Observer ecoutant){ this.addObserver(ecoutant);}
    public void setVisible(){}
}
