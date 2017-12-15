/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.main;

/**
 *
 * @author souliern
 */
public class Tuile {

    private NOM_TUILE nom;
    private ETAT_TUILE etat;
    
    public Tuile(NOM_TUILE nom, ETAT_TUILE etat){
        setNom(nom);
        setEtat(etat);
    }

    public Tuile(){}; // /!\ Utilisé pour getTuile() et autre dans Grille, trouver un moyen de faire autrement ? /!\
    
    public void setNom(NOM_TUILE nom){
        this.nom = nom;
    }
    
    public void setEtat(ETAT_TUILE etat){
        this.etat = etat;
    }

    public ETAT_TUILE getEtat(){
        return etat;
    }

    public boolean estCoulee(){
        if (getEtat()==ETAT_TUILE.COULEE){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString(){
        return nom.toString();
    }
}
