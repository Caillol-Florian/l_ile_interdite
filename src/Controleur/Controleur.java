/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controleur;

import java.awt.*;
import java.io.IOException;
import java.util.*;

import Enums.*;
import Modèles.*;
import Modèles.Aventurier.*;
import Modèles.Carte.*;
import Views.*;

import javax.sound.sampled.*;

import static Modèles.Parameters.ALEAS;

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

    private ArrayList<NOM_AVENTURIER>roles = new ArrayList<>();

    // ==============================
    // Paramètres
    private boolean jeuLance = false;
    private int nbActions = 0;
    private boolean tourPassé = false;
    private int joueurActif = 0;
    boolean aAsseché = false; //afin de traiter l'assechement supplementaire de l'ingenieur
    boolean piloteSpecial = false; // Déplacement spécial du pilote

    private int nbJoueurs;
    private int difficulte;
    private ArrayList<String> pseudos;

    // =============================
    // ArrayList des vuesAventuriers
    private ArrayList<VueAventurier> vueAventuriers = new ArrayList<>();


    // =============================
    // Vues
    private VueMenu vueMenu = new VueMenu();
    private VueInscription vueInscription = new VueInscription();
    private VueNiveau vueNiveau;
    private VuePlateau vuePlateau;

    public Controleur() throws LineUnavailableException, IOException, UnsupportedAudioFileException {

        //Ajout observer aux différentes vues
        vueMenu.abonner(this);
        vueInscription.abonner(this);

        //Lancement du jeu
        openView(vueMenu);

        // =========================
        // Musique

        //pour l'exécuter au moment ou la fenêtre s'ouvre
        //AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File(System.getProperty("user.dir") + "/src/Controleur/1055.wav"));
        //Get a sound clip resource.
        //Clip clip = AudioSystem.getClip();
        //Open audio clip and load samples from the audio input stream.
        //clip.open(audioIn);
        //clip.start();
        //clip.loop((int)clip.getMicrosecondLength());
    }


    public void startGame(){

        // ================================
        // Initialisation des modèles

        // Initialisation des trésors
        Tresor calice = new Tresor(TYPE_TRESOR.CALICE);
        Tresor cristal = new Tresor(TYPE_TRESOR.CRISTAL);
        Tresor pierre = new Tresor(TYPE_TRESOR.PIERRE);
        Tresor zephyr = new Tresor(TYPE_TRESOR.ZEPHYR);

        // Initialisation de la pile de Carte Inondations
        for (NOM_TUILE nom_tuile : NOM_TUILE.values()){
            CarteInondation carteInondation = new CarteInondation(getGrille().getTuile(nom_tuile));
            pileCartesInondations.add(carteInondation);
        }



        // Initialisation de la pile de Carte Actions
        // Carte trésors (5 de chaque)
        for (int i=0; i<5; i++){
            CarteTresor carteTresorCalice = new CarteTresor(TYPE_TRESOR.CALICE.toString());
            pileCartesAction.add(carteTresorCalice);
            CarteTresor carteTresorCristal = new CarteTresor(TYPE_TRESOR.CRISTAL.toString());
            pileCartesAction.add(carteTresorCristal);
            CarteTresor carteTresorPierre = new CarteTresor(TYPE_TRESOR.PIERRE.toString());
            pileCartesAction.add(carteTresorPierre);
            CarteTresor carteTresorZephyr = new CarteTresor(TYPE_TRESOR.ZEPHYR.toString());
            pileCartesAction.add(carteTresorZephyr);
        }

        // Cartes hélicoptère et montée des eaux (3 de chaque)
        for (int i=0; i<3; i++){
            CarteHelicoptere carteHelicoptere = new CarteHelicoptere("Helicoptere");
            pileCartesAction.add(carteHelicoptere);
            CarteMonteeEaux carteMonteeEaux = new CarteMonteeEaux();
            pileCartesAction.add(carteMonteeEaux);
        }

        // Cartes sac de sable (2)
        for (int i=0; i<2; i++){
            CarteSacDeSable carteSacDeSable = new CarteSacDeSable("Sac de sable");
            pileCartesAction.add(carteSacDeSable);
        }

        // Mélange des piles
        if (ALEAS){
            Collections.shuffle(pileCartesInondations);
            Collections.shuffle(pileCartesAction);

        }

        // =================================
        // Création Vue Plateau

        // ArrayList des couleurs et des roles nécessaires à la création de la vue
        ArrayList<Color>couleurs = new ArrayList<>();
        ArrayList<String>roles = new ArrayList<>();
        for(Aventurier aventurier : aventuriers){
            couleurs.add(aventurier.getPion().getCouleur());
            roles.add(aventurier.getNomRole().toString());
        }

        this.vuePlateau = new VuePlateau(pseudos, couleurs, roles, getGrille().getNomTuiles());
        vuePlateau.abonner(this); // Abonnement

        // Placement des pions
        for(int i = 0; i< aventuriers.size(); i++){
            updatePos(getGrille().getCordonneesTuiles(aventuriers.get(i).getPosition()), aventuriers.get(i).getPion());
        }

        // Ouverture de la vue
        closeView(vueInscription);
        openView(vuePlateau);
        jeuLance = true;
        //Création de la Vue niveau avec la difficulté
        vueNiveau = new VueNiveau(difficulte);
    }

    @Override
    public void update(Observable o, Object arg) {

        // ---------------------------------- //
        // -------- MENU PRINCIPAL --------- //
        // ---------------------------------- //

        if (arg == Messages.INSCRPTION){
            closeView((Vue)o);
            openView(vueInscription);
        }

        // Ajout Vue règles

        if (arg == Messages.QUITTER){
            closeView(((Vue)o));
        }

        if (arg == Messages.RETOUR){
            closeView(((Vue)o));
            openView(vueMenu);
        }

        // ---------------------------------- //
        // ----- INSCRIPTION DES JOUEURS ---- //
        // ---------------------------------- //

        if (arg == Messages.VALIDERINSCRIPTION) {
            // Récupération des paramètres de jeu
            nbJoueurs = ((VueInscription)o).getNombreJoueurs();
            difficulte = ((VueInscription)o).getNiveauDifficulte();
            pseudos = ((VueInscription)o).getPseudos();

            // On créer les aventuriers en leur attribbuant aléatoirement un role
            for (NOM_AVENTURIER role : NOM_AVENTURIER.values()){
                roles.add(role);
            }

            if (ALEAS){
                Collections.shuffle(roles);
            }

            int i=0;
            while (i<nbJoueurs){
                if (roles.get(i).equals(NOM_AVENTURIER.EXPLORATEUR)){
                    Explorateur aventurier = new Explorateur(getGrille().getTuile(NOM_TUILE.LA_PORTE_DE_CUIVRE), pseudos.get(i));
                    aventuriers.add(aventurier);
                }
                if (roles.get(i).equals(NOM_AVENTURIER.INGENIEUR)){
                    Ingenieur aventurier = new Ingenieur(getGrille().getTuile(NOM_TUILE.LA_PORTE_DE_BRONZE), pseudos.get(i));
                    aventuriers.add(aventurier);
                }
                if (roles.get(i).equals(NOM_AVENTURIER.MESSAGER)){
                    Messager aventurier = new Messager(getGrille().getTuile(NOM_TUILE.LA_PORTE_DE_FER), pseudos.get(i));
                    aventuriers.add(aventurier);
                }
                if (roles.get(i).equals(NOM_AVENTURIER.NAVIGATEUR)){
                    Navigateur aventurier = new Navigateur(getGrille().getTuile(NOM_TUILE.LA_PORTE_D_OR), pseudos.get(i));
                    aventuriers.add(aventurier);
                }
                if (roles.get(i).equals(NOM_AVENTURIER.PILOTE)){
                    Pilote aventurier = new Pilote(getGrille().getTuile(NOM_TUILE.HELIPORT), pseudos.get(i));
                    aventuriers.add(aventurier);
                }
                if (roles.get(i).equals(NOM_AVENTURIER.PLONGEUR)){
                    Plongeur aventurier = new Plongeur(getGrille().getTuile(NOM_TUILE.LA_PORTE_D_ARGENT), pseudos.get(i));
                    aventuriers.add(aventurier);
                }
                i++;
            }
            startGame();
        }


        // ---------------------------------- //
        // --------- VUE AVENTURIER  -------- //
        // ---------------------------------- //

            // Ouverture de la vue Assèchement
        if (arg == Messages.ASSECHER) {
            //openView(vues.get(1));
            //vues.get(1).setAvailableTuile(aventuriers.get(joueurActif%aventuriers.size()).getTuilesAssechables(grille));
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

            vuePlateau.setAvailableTuiles(aventuriers.get(joueurActif % aventuriers.size()).getTuilesAccesibles(grille));



            // Afin de traiter le déplacement du pilote :
            // On vérifie que le joueur actif est un pilote
                // S'il est pilote, on affiche les tuiles disponibles en fonction de s'il a utilisé son déplacement spécial
            // SINON, on affiche les tuiles des autres aventuriers.
            if(aventuriers.get(joueurActif%aventuriers.size()) instanceof Pilote){
               //vues.get(2).setAvailableTuile(((Pilote) aventuriers.get(joueurActif%aventuriers.size())).getTuilesAccesibles(grille, piloteSpecial));
            } else {
               // vues.get(2).setAvailableTuile(aventuriers.get(joueurActif % aventuriers.size()).getTuilesAccesibles(grille));
            }
            //openView(vues.get(2));
        }

        if (arg == Messages.VALIDERDEPLACEMENT) {
            if(((Vue) o).getTuileSelectionnee() != null){
                aventuriers.get(joueurActif % aventuriers.size()).setPosition(getGrille().getTuile(((Vue) o).getTuileSelectionnee()));

                // Vérification si le pilote a utilisé son déplacement spécial :
                // Si la tuile sélectionnée n'est pas une tuile adjacente au pilote alors il a utilisé son pouvoir
                if (aventuriers.get(joueurActif % aventuriers.size()) instanceof Pilote){
                   // if ( !((ArrayList<Tuile>) (aventuriers.get(joueurActif% aventuriers.size())).getTuilesAccesibles(grille)).contains(getGrille().getTuile(((Vue) o).getTuileSelectionnee()))){
                        piloteSpecial = true;

                }

                // Update visuel de la position
              //  updatePos(joueurActif % aventuriers.size());
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

    public void updatePos(int[] coordonnesJoueur, PION pion) {
        ArrayList<PION>pions;
        pions = vuePlateau.getTableauTuile()[coordonnesJoueur[0]][coordonnesJoueur[1]].getPions();
        pions.add(pion);
        vuePlateau.getTableauTuile()[coordonnesJoueur[0]][coordonnesJoueur[1]].update(ETAT_TUILE.SECHE, pions);
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
