/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controleur;

import java.util.ArrayList;
import Aventurier.*;
import Views.*;
import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;
import main.main.*;
/**
 *
 * @author souliern
 */

public class Controleur implements Observer {
    private Grille grille = new Grille();
    private ArrayList<Aventurier>aventuriers = new ArrayList<>();
    private ArrayList<Vue>vues = new ArrayList<>();

    public Controleur(){};

    public void addView(Vue vue){
        vue.abonner(this);
        vues.add(vue);
    }

    public void openView(Vue vue){
        vue.setVisible(true);
    }
    public void closeView(Vue vue){
        vue.setVisible(false);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg == Messages.ASSECHER){
            openView(vues.get(1));
        }
        if (arg == Messages.DEPLACER){
            openView(vues.get(2));
        }

        if (arg == Messages.RETOUR){
            closeView((Vue)o);
        }
    }

    public Grille getGrille(){
        return grille;
    }

    public ArrayList getVues(){
        return vues;
    }
}
