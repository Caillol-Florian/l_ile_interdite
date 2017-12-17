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
        return getEtat()==ETAT_TUILE.COULEE;
    }

    public boolean estInondee(){
        return getEtat()==ETAT_TUILE.INONDEE;
    }

    public boolean estSeche(){
        return getEtat()==ETAT_TUILE.SECHE;
    }

    public boolean estOcean(){
        return getNom()==NOM_TUILE.EAU;
    }

    @Override
    public String toString(){
        return getNom().toString();
    }

    public NOM_TUILE getNom() {
        return nom;
    }
}
