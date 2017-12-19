/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controleur;

import java.util.ArrayList;
import Aventurier.*;
import Views.*;
import java.util.Observable;
import java.util.Observer;
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

    // ==============================
    // Les vues autres que aventuriers seront rangées dans cet ordre :
    // 0 : VueInscription
    // 1 : VueAssechement
    // 2 : VueDeplacement
    private ArrayList<Vue>vues = new ArrayList<>();

    // =============================
    // ArrayList des vuesAventuriers
    private ArrayList<VueAventurier> vueAventuriers = new ArrayList<>();

    // =============================

    public Controleur(){
        // On change les état des tuiles pour qu'elles correspondent au plateau fourni
        getGrille().getTuiles()[0][3].setEtat(ETAT_TUILE.INONDEE);
        getGrille().getTuiles()[2][2].setEtat(ETAT_TUILE.COULEE);
        getGrille().getTuiles()[3][1].setEtat(ETAT_TUILE.INONDEE);
        getGrille().getTuiles()[3][3].setEtat(ETAT_TUILE.INONDEE);
        getGrille().getTuiles()[3][4].setEtat(ETAT_TUILE.COULEE);
        getGrille().getTuiles()[3][2].setEtat(ETAT_TUILE.COULEE);
        getGrille().getTuiles()[3][5].setEtat(ETAT_TUILE.INONDEE);
        getGrille().getTuiles()[4][2].setEtat(ETAT_TUILE.COULEE);
        getGrille().getTuiles()[5][3].setEtat(ETAT_TUILE.INONDEE);
        getGrille().getTuiles()[5][3].setEtat(ETAT_TUILE.INONDEE);
        getGrille().getTuiles()[0][2].setEtat(ETAT_TUILE.INONDEE);
        getGrille().getTuiles()[1][3].setEtat(ETAT_TUILE.INONDEE);


        // Création des différentes vues
        VueInscription vueInscription = new VueInscription();
        VueAssechement vueAssechement = new VueAssechement();
        VueDeplacement vueDeplacement = new VueDeplacement();

        // Abonnement
        addView(vueInscription);
        addView(vueAssechement);
        addView(vueDeplacement);

        // On commence par l'inscription des joueurs
        startInscription();
    }

    public void addView(Vue vue){
        vue.abonner(this);
        vues.add(vue);
    }

    public void addViewAventurier(VueAventurier vue){
        vue.abonner(this);
        vueAventuriers.add(vue);
    }

    public void startInscription(){
        // Ouverture de la vue Inscription
        openView(vues.get(0));
    }

    public void startGame(){
        // Pour chaque aventurier on ouvre sa vue correspondante en mettant à jour la position affichée
        // On active/désactive les boutons de la vue s'il s'agit du joueurActif (Dans ce cas là joueurActif = 0 donc la seule vue avec les boutons activés est celui du Joueur n°1
        for(int i = 0; i < vueAventuriers.size() ; i++){
            openView(vueAventuriers.get(i));
            updatePos(i);
            enableBouton(i == joueurActif, i);
        }
    }

    @Override
    public void update(Observable o, Object arg) {

        // ---------------------------------- //
        // ----- INSCRIPTION DES JOUEURS ---- //
        // ---------------------------------- //

        if (arg == Messages.VALIDERINSCRIPTION) {
            // Si le rôle sélectionné est l'explorateur, on créé un Explorateur avec les infos rentrées par le joueur
            if (((Vue) o).getRoleSelectionne() == NOM_AVENTURIER.EXPLORATEUR) {
                Explorateur aventurier = new Explorateur(getGrille().getTuile(NOM_TUILE.LA_PORTE_DE_CUIVRE), ((Vue) o).getNom());
                aventuriers.add(aventurier);

                // On créé aussi la vue associé à cet aventurier en récupérant ses informations. A savoir : l'index de la vue dans l'ArrayList des VueAventurier est le même que celui de l'aventurier dans l'ArrayList aventuriers
                VueAventurier vueAventurier = new VueAventurier(aventurier.getNomJoueur(), aventurier.getNomRole(), aventurier.getPion().getCouleur(), aventuriers.size()-1);
                addViewAventurier(vueAventurier);
            }

            if (((Vue) o).getRoleSelectionne() == NOM_AVENTURIER.INGENIEUR) {
                Ingenieur aventurier = new Ingenieur(getGrille().getTuile(NOM_TUILE.LA_PORTE_DE_BRONZE), ((Vue) o).getNom());
                aventuriers.add(aventurier);

                VueAventurier vueAventurier = new VueAventurier(aventurier.getNomJoueur(), aventurier.getNomRole(), aventurier.getPion().getCouleur(), aventuriers.size()-1);
                addViewAventurier(vueAventurier);
            }

            if (((Vue) o).getRoleSelectionne() == NOM_AVENTURIER.MESSAGER) {
                Messager aventurier = new Messager(getGrille().getTuile(NOM_TUILE.LA_PORTE_DE_FER), ((Vue) o).getNom());
                aventuriers.add(aventurier);

                VueAventurier vueAventurier = new VueAventurier(aventurier.getNomJoueur(), aventurier.getNomRole(), aventurier.getPion().getCouleur(), aventuriers.size()-1);
                addViewAventurier(vueAventurier);
            }

            if (((Vue) o).getRoleSelectionne() == NOM_AVENTURIER.NAVIGATEUR) {
                Navigateur aventurier = new Navigateur(getGrille().getTuile(NOM_TUILE.LA_PORTE_D_OR), ((Vue) o).getNom());
                aventuriers.add(aventurier);

                VueAventurier vueAventurier = new VueAventurier(aventurier.getNomJoueur(), aventurier.getNomRole(), aventurier.getPion().getCouleur(), aventuriers.size()-1);
                addViewAventurier(vueAventurier);
            }

            if (((Vue) o).getRoleSelectionne() == NOM_AVENTURIER.PILOTE) {
                Pilote aventurier = new Pilote(getGrille().getTuile(NOM_TUILE.HELIPORT), ((Vue) o).getNom());
                aventuriers.add(aventurier);

                VueAventurier vueAventurier = new VueAventurier(aventurier.getNomJoueur(), aventurier.getNomRole(), aventurier.getPion().getCouleur(), aventuriers.size()-1);
                addViewAventurier(vueAventurier);
            }

            if (((Vue) o).getRoleSelectionne() == NOM_AVENTURIER.PLONGEUR) {
                Plongeur aventurier = new Plongeur(getGrille().getTuile(NOM_TUILE.LA_PORTE_D_ARGENT), ((Vue) o).getNom());
                aventuriers.add(aventurier);

                VueAventurier vueAventurier = new VueAventurier(aventurier.getNomJoueur(), aventurier.getNomRole(), aventurier.getPion().getCouleur(), aventuriers.size()-1);
                addViewAventurier(vueAventurier);
            }

            // Dès lors qu'on a inscrit 2 joueurs, on active le bouton Finir ce qui rend la fin de l'inscription possible (On a donc 2 à 4 joueurs)
            if(aventuriers.size() == 2){
                ((Vue)o).getBtnFinir().setEnabled(true);
            }

            if(aventuriers.size()==4){ // Si après avoir inscrit un aventurier il s'agissait du 4ème joueur (aventuriers.size() est désormais = à 4), on commence la partie
                closeView((Vue) o);
                startGame();
            }

            // On remet à 0 la vue après avoir inscrit un joueur.
            ((Vue) o).resetInscription(((Vue) o).getRoleSelectionne());
        }

        // Si on clique sur le bouton Finir pendant l'inscription, on lance directement la partie.
        if (arg == Messages.FINIRINSCRIPTION) {
             closeView((Vue)o);
             startGame();
        }

        // ---------------------------------- //
        // --------- VUE AVENTURIER  -------- //
        // ---------------------------------- //

            // Ouverture de la vue Assèchement
        if (arg == Messages.ASSECHER) {
            openView(vues.get(1));
            vues.get(1).setAvailableTuile(aventuriers.get(joueurActif%aventuriers.size()).getTuilesAssechables(grille));
        }

            // Validation de la case à assécher
        if (arg == Messages.VALIDERASSECHEMENT) {
            if(((Vue) o).getTuileSelectionnee() != null) {
                getGrille().getTuile(((Vue) o).getTuileSelectionnee()).setEtat(ETAT_TUILE.SECHE);
                closeView((Vue) o);

                if (aventuriers.get(joueurActif % aventuriers.size()).getNomRole() == NOM_AVENTURIER.INGENIEUR && aventuriers.get(joueurActif % aventuriers.size()).getAssechementBonus() == false) {
                    aventuriers.get(joueurActif % aventuriers.size()).setAssechementBonus(true);
                } else if (aventuriers.get(joueurActif % aventuriers.size()).getAssechementBonus() == true) {
                    aventuriers.get(joueurActif % aventuriers.size()).setAssechementBonus(false);
                    nbActions++;
                } else {
                    nbActions++;
                }

            } else {
                Utils.afficherInformation("Aucune tuile sélectionnée !");
            }
        }

            // Ouverture de la vue Déplacement
        if (arg == Messages.DEPLACER) {
            vues.get(2).setAvailableTuile(aventuriers.get(joueurActif%aventuriers.size()).getTuilesAccesibles(grille));
            openView(vues.get(2));}

        if (arg == Messages.VALIDERDEPLACEMENT) {
            if(((Vue) o).getTuileSelectionnee() != null){
                aventuriers.get(joueurActif % aventuriers.size()).setPosition(getGrille().getTuile(((Vue) o).getTuileSelectionnee()));
                updatePos(joueurActif % aventuriers.size());
                closeView((Vue) o);
                nbActions++;
            } else {
                Utils.afficherInformation("Aucune tuile sélectionnée !");
            }
        }

        if (arg == Messages.AUTRE) {
            Utils.afficherInformation("Cette fonctionnalité n'est pas encore disponible !");
        }

        // Si on appuie sur le bouton Fin Tour pendant un tour, tourPassé = true (ce qui aura pour effet de passer le tour)
        if (arg == Messages.FINTOUR) {
            tourPassé = true;
        }

        // Quand on clique sur un bouton Retour, on ferme la présente vue.
        if (arg == Messages.RETOUR) {
            closeView((Vue) o);
        }

        // ---------------------------------- //
        // --------  GESTION DU TOUR -------- //
        // ---------------------------------- //


        // Si le nombre d'actions possibles de l'aventurier est égal au nombre d'actions effectuées pendant ce tour (nbActions) on passe le tour
        if(aventuriers.get(joueurActif%aventuriers.size()).getNombreAction() == nbActions){
                tourPassé = true;
        }

        // tourPassé est true si on a atteint le nombre maximum d'actions possibles ou qu'on a appuyé sur le bouton Fin Tour
        if (tourPassé) {
            enableBouton(false, joueurActif%aventuriers.size()); // On désactive les boutons de la vue Aventurier correspondant au joueur qui est actif
            joueurActif++; // On passe au prochain joueur
            nbActions = 0; // On remet le nombre d'actions effectuées à 0
            tourPassé = false; // Le tour n'est plus passé
            // On active les boutons de la vue Aventurier pour le prochain joueur
            enableBouton(true, joueurActif%aventuriers.size());
        }
    }

    public void enableBouton(boolean b, int vueActive){
        vueAventuriers.get(vueActive%aventuriers.size()).getBtnAssecher().setEnabled(b);
        vueAventuriers.get(vueActive%aventuriers.size()).getBtnAutreAction().setEnabled(b);
        vueAventuriers.get(vueActive%aventuriers.size()).getBtnBouger().setEnabled(b);
        vueAventuriers.get(vueActive%aventuriers.size()).getBtnTerminerTour().setEnabled(b);
    }

    public void updatePos(int joueur){
        vueAventuriers.get(joueur).setPosition(aventuriers.get(joueur).getPosition().toString());
    }

    public Grille getGrille(){
        return grille;
    }

    public void openView(Vue vue){
        vue.setVisible(true);
    }

    public void closeView(Vue vue){
        vue.setVisible(false);
    }
}
