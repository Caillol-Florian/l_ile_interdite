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
import java.awt.Toolkit;
import com.sun.prism.paint.Color;
import main.main.*;
/**
 *
 * @author souliern
 */

public class Controleur implements Observer {
    private Grille grille = new Grille();
    private int nbActions = 0;
    private boolean tourPassé = false;
    private int joueurActif = 0;
    private ArrayList<Aventurier>aventuriers = new ArrayList<>();
    // -----------------
    // Les vues seront rangées dans cet ordre :
    // 0 : VueInscription
    // 1 : VueAssechement
    // 2 : VueDeplacement
    // 3 : vueAventurier
    private ArrayList<Vue>vues = new ArrayList<>();

    public Controleur(){
        getGrille().getTuiles()[0][3].setEtat(ETAT_TUILE.INONDEE);
        getGrille().getTuiles()[2][2].setEtat(ETAT_TUILE.COULEE);
        getGrille().getTuiles()[3][1].setEtat(ETAT_TUILE.INONDEE);
        getGrille().getTuiles()[3][3].setEtat(ETAT_TUILE.INONDEE);
        getGrille().getTuiles()[3][4].setEtat(ETAT_TUILE.COULEE);
        getGrille().getTuiles()[3][2].setEtat(ETAT_TUILE.COULEE);
        getGrille().getTuiles()[3][5].setEtat(ETAT_TUILE.INONDEE);
        getGrille().getTuiles()[4][2].setEtat(ETAT_TUILE.COULEE);
        getGrille().getTuiles()[5][3].setEtat(ETAT_TUILE.INONDEE);

        VueInscription vueInscription = new VueInscription();
        VueAssechement vueAssechement = new VueAssechement();
        VueDeplacement vueDeplacement = new VueDeplacement();

        addView(vueInscription);
        addView(vueAssechement);
        addView(vueDeplacement);
        startInscription();
    }

    public void addView(Vue vue){
        vue.abonner(this);
        vues.add(vue);
    }

    public void startInscription(){
        openView(vues.get(0));
    }

    public void startGame(){
        VueAventurier vueAventurier = new VueAventurier(aventuriers.get(0).getNomJoueur(), aventuriers.get(0).getNomRole(), aventuriers.get(0).getPion().getCouleur());
        addView(vueAventurier);
        updatePos(aventuriers.get(0));
        openView(vues.get(3));
    }


    public void updatePos(Aventurier a){
        vues.get(3).setPosition(a.getPosition().toString());
    }

    @Override
    public void update(Observable o, Object arg) {

        // ---------------------------------- //
        // ----- INSCRIPTION DES JOUEURS ---- //
        // ---------------------------------- //

            if (arg == Messages.VALIDERINSCRIPTION) {
                System.out.println(aventuriers.size());
                if (((Vue) o).getRoleSelectionne() == NOM_AVENTURIER.EXPLORATEUR) {
                    Explorateur aventurier = new Explorateur(getGrille().getTuile(NOM_TUILE.LA_PORTE_DE_CUIVRE), ((Vue) o).getNom());
                    aventuriers.add(aventurier);
                }

                if (((Vue) o).getRoleSelectionne() == NOM_AVENTURIER.INGENIEUR) {
                    Ingenieur aventurier = new Ingenieur(getGrille().getTuile(NOM_TUILE.LA_PORTE_DE_BRONZE), ((Vue) o).getNom());
                    aventuriers.add(aventurier);
                }

                if (((Vue) o).getRoleSelectionne() == NOM_AVENTURIER.MESSAGER) {
                    Messager aventurier = new Messager(getGrille().getTuile(NOM_TUILE.LA_PORTE_DE_FER), ((Vue) o).getNom());
                    aventuriers.add(aventurier);
                }

                if (((Vue) o).getRoleSelectionne() == NOM_AVENTURIER.NAVIGATEUR) {
                    Navigateur aventurier = new Navigateur(getGrille().getTuile(NOM_TUILE.LA_PORTE_D_OR), ((Vue) o).getNom());
                    aventuriers.add(aventurier);
                }

                if (((Vue) o).getRoleSelectionne() == NOM_AVENTURIER.PILOTE) {
                    Pilote aventurier = new Pilote(getGrille().getTuile(NOM_TUILE.HELIPORT), ((Vue) o).getNom());
                    aventuriers.add(aventurier);
                }

                if (((Vue) o).getRoleSelectionne() == NOM_AVENTURIER.PLONGEUR) {
                    Plongeur aventurier = new Plongeur(getGrille().getTuile(NOM_TUILE.LA_PORTE_D_ARGENT), ((Vue) o).getNom());
                    aventuriers.add(aventurier);
                }
                if(aventuriers.size()==4){ // Si après avoir inscrit un aventurier il s'agissait du 4ème joueur, on commence la partie
                    closeView((Vue) o);
                    startGame();
                }
                // On remet à 0 la vue après avoir inscrit un joueur.
                ((Vue) o).resetInscription(((Vue) o).getRoleSelectionne());
            }

        // ---------------------------------- //
        // --------- VUE AVENTURIER  -------- //
        // ---------------------------------- //

        if (arg == Messages.ASSECHER) {
            openView(vues.get(1));
            vues.get(1).setAvailableTuile(aventuriers.get(joueurActif%4).getTuilesAssechables(grille));
        }

        if (arg == Messages.VALIDERASSECHEMENT) {
            System.out.print(nbActions);
            getGrille().getTuile(((Vue) o).getTuileSelectionnee()).setEtat(ETAT_TUILE.SECHE);
            closeView((Vue) o);
            if (aventuriers.get(joueurActif%4).getNomRole() == NOM_AVENTURIER.INGENIEUR && aventuriers.get(joueurActif%4).getAssechementBonus() == false)
                aventuriers.get(joueurActif%4).setAssechementBonus(true);
                nbActions++;
            }
            else if (aventuriers.get(joueurActif%4).getAssechementBonus() == true) {
            aventuriers.get(joueurActif%4).setAssechementBonus(false);
            else {
               nbActions++;
            }
        }

        if (arg == Messages.DEPLACER) {
            vues.get(2).setAvailableTuile(aventuriers.get(joueurActif%4).getTuilesAccesibles(grille));
            openView(vues.get(2));}

        if (arg == Messages.VALIDERDEPLACEMENT) {
            System.out.println(nbActions);
            aventuriers.get(joueurActif%4).setPosition(getGrille().getTuile(((Vue) o).getTuileSelectionnee()));
            updatePos(aventuriers.get(joueurActif%4));
            closeView((Vue) o);
            nbActions++;
        }

        if (arg == Messages.AUTRE) {
            Utils.afficherInformation("Cette fonctionnalité n'est pas encore disponible !");
        }

        if (arg == Messages.FINTOUR) {
            tourPassé = true;
        }

        if (arg == Messages.RETOUR) {
            closeView((Vue) o);
        }


        // ---------------------------------- //
        // --------  GESTION DU TOUR -------- //
        // ---------------------------------- //

        if(aventuriers.get(joueurActif%4).getNombreAction() == nbActions){
                tourPassé = true;
        }
        // Si le nombre d'actions possibles de l'aventurier est égal au nombre d'actions effectuées pendant ce tour OU qu'il a appuyé sur "Fin Tour"
        if (tourPassé) {
            joueurActif++; // On passe au prochain joueur
            nbActions = 0; // On remet le nombre d'actions effectuées à 0
            tourPassé = false; // Le tour n'est plus passé
            // On met à jour le vue aventurier pour le prochain joueur
            ((Vue)o).updateVue(aventuriers.get(joueurActif%4).getPosition().toString(), aventuriers.get(joueurActif%4).getNomJoueur(), aventuriers.get(joueurActif%4).getNomRole(), aventuriers.get(joueurActif%4).getPion().getCouleur());
            }
    }

    public Grille getGrille(){
        return grille;
    }

    public ArrayList getVues(){
        return vues;
    }

    public void openView(Vue vue){
        vue.setVisible(true);
    }

    public void closeView(Vue vue){
        vue.setVisible(false);
    }
}
