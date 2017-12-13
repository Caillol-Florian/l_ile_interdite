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
    
    public void setNom(NOM_TUILE nom){
        this.nom = nom;
    }
    
    public void setEtat(ETAT_TUILE etat){
        this.etat = etat;
    }
    
    @Override
    public String toString(){
        return nom.toString();
    }
    
    public ETAT_TUILE getEtat(){
        return etat;
    }
}
