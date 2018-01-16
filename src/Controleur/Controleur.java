/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controleur;

import java.io.IOException;
import java.util.ArrayList;

import Enums.ETAT_TUILE;
import Enums.Messages;
import Enums.NOM_AVENTURIER;
import Enums.NOM_TUILE;
import Modèles.*;
import Modèles.Aventurier.*;
import Modèles.Carte.CarteAction;
import Modèles.Carte.CarteInondation;
import Views.*;
import java.util.Observable;
import java.util.Observer;

import javax.sound.sampled.*;

/**
 *
 * @author souliern
 */

public class Controleur implements Observer {

    // ==============================
    // Modèle
    private ArrayList<Aventurier>aventuriers = new ArrayList<>();
    private Grille grille = new Grille();
    private ArrayList<CarteAction>pileCartesAction = new ArrayList<>();
    private ArrayList<CarteInondation>pileCartesInondations = new ArrayList<>();

    // ==============================
    // Paramètres
    private boolean jeuLance = false;
    private int nbActions = 0;
    private boolean tourPassé = false;
    private int joueurActif = 0;
    boolean aAsseché = false; //afin de traiter l'assechement supplementaire de l'ingenieur
    boolean piloteSpecial = false; // Déplacement spécial du pilote


    // ==============================
    // Les vues autres que aventuriers seront rangées dans cet ordre :
    // 0 : VueInscription
    // 1 : VueAssechement
    // 2 : VueDeplacement
    // 3 : VueMenu
    private ArrayList<Vue>vues = new ArrayList<>();

    // =============================
    // ArrayList des vuesAventuriers
    private ArrayList<VueAventurier> vueAventuriers = new ArrayList<>();

    // =============================

    public Controleur() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
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

        // Création des différentes vues
        VueInscription vueInscription = new VueInscription();
        VueAssechement vueAssechement = new VueAssechement();
        VueDeplacement vueDeplacement = new VueDeplacement();
        VueMenu vueMenu = new VueMenu();


        // Abonnement
        addView(vueInscription);
        addView(vueAssechement);
        addView(vueDeplacement);
        addView(vueMenu);

        // On commence par l'inscription des joueurs
        openView(vues.get(3));

        //pour l'exécuter au moment ou la fenêtre s'ouvre
        //AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File(System.getProperty("user.dir") + "/src/Controleur/1055.wav"));
        //Get a sound clip resource.
        //Clip clip = AudioSystem.getClip();
        //Open audio clip and load samples from the audio input stream.
        //clip.open(audioIn);
        //clip.start();
        //clip.loop((int)clip.getMicrosecondLength());
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
            jeuLance = true;
        }
    }

    @Override
    public void update(Observable o, Object arg) {

        // =====================================
        // Ouverture vue Inscrition

        if (arg == Messages.INSCRPTION){
            startInscription();
            closeView((Vue)o);
        }

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

                // Si l'aventurier qui assèche est un ingénieur et qu'il n'a pas déjà asseché
                if (aventuriers.get(joueurActif % aventuriers.size()).getNomRole() == NOM_AVENTURIER.INGENIEUR && !aAsseché) {
                aAsseché = true; // On change la valeur aAsseché en true
                } else if (aAsseché) { // S'il a déjà asseché
                    nbActions++; // On augmente le nombre d'action effectué
                    aAsseché = false; // On remet la valeur aAsseché en false
                } else {
                    nbActions++; // Sinon s'il s'agit pas d'un ingénieur on augmente le nombre d'action
                }
            } else {

                Utils.afficherInformation("Aucune tuile sélectionnée !");
            }
        }

            // Ouverture de la vue Déplacement

        if (arg == Messages.DEPLACER) {
            // Afin de traiter le déplacement du pilote :
            // On vérifie que le joueur actif est un pilote
                // S'il est pilote, on affiche les tuiles disponibles en fonction de s'il a utilisé son déplacement spécial
            // SINON, on affiche les tuiles des autres aventuriers.
            if(aventuriers.get(joueurActif%aventuriers.size()) instanceof Pilote){
               vues.get(2).setAvailableTuile(((Pilote) aventuriers.get(joueurActif%aventuriers.size())).getTuilesAccesibles(grille, piloteSpecial));
            } else {
                vues.get(2).setAvailableTuile(aventuriers.get(joueurActif % aventuriers.size()).getTuilesAccesibles(grille));
            }
            openView(vues.get(2));
        }

        if (arg == Messages.VALIDERDEPLACEMENT) {
            if(((Vue) o).getTuileSelectionnee() != null){
                aventuriers.get(joueurActif % aventuriers.size()).setPosition(getGrille().getTuile(((Vue) o).getTuileSelectionnee()));

                // Vérification si le pilote a utilisé son déplacement spécial :
                // Si la tuile sélectionnée n'est pas une tuile adjacente au pilote alors il a utilisé son pouvoir
                if (aventuriers.get(joueurActif % aventuriers.size()) instanceof Pilote){
                    if ( !((ArrayList<Tuile>) (aventuriers.get(joueurActif% aventuriers.size())).getTuilesAccesibles(grille)).contains(getGrille().getTuile(((Vue) o).getTuileSelectionnee()))){
                        piloteSpecial = true;
                    }
                }

                // Update visuel de la position
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

        // On gère l'assèchement bonus de l'Ingénieur
        // Si l'ingénieur a effectué 2 actions et qu'il a déjà asséché une fois, il n'a plus le choix que de profiter de son
        // assèchement bonus ou de passer le tour.
        if (nbActions == 2 & aAsseché){
            vueAventuriers.get(joueurActif%aventuriers.size()).getBtnBouger().setEnabled(false);
        }

        // ---------------------------------- //
        // --------  GESTION DU TOUR -------- //
        // ---------------------------------- //

        if(jeuLance) {
            // Si le nombre d'actions possibles de l'aventurier est égal au nombre d'actions effectuées pendant ce tour (nbActions) on passe le tour
            if ((aventuriers.get(joueurActif % aventuriers.size()).getNombreAction() == nbActions)) {
                tourPassé = true;
            }

            // tourPassé est true si on a atteint le nombre maximum d'actions possibles ou qu'on a appuyé sur le bouton Fin Tour
            if (tourPassé) {
                enableBouton(false, joueurActif % aventuriers.size()); // On désactive les boutons de la vue Aventurier correspondant au joueur qui est actif
                joueurActif++; // On passe au prochain joueur
                nbActions = 0; // On remet le nombre d'actions effectuées à 0
                tourPassé = false; // Le tour n'est plus passé
                aAsseché = false; // On reset l'assèchement bonus pour l'ingénieur
                piloteSpecial = false; // Reset de l'action spéciale du pilote
                // On active les boutons de la vue Aventurier pour le prochain joueur
                enableBouton(true, joueurActif % aventuriers.size());
            }
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
