/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controleur;

import java.awt.*;
import java.io.File;
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
    private ArrayList<NOM_AVENTURIER>roles = new ArrayList<>();
    private ArrayList<Aventurier>aventuriers = new ArrayList<>();
    private Grille grille = new Grille();
    private ArrayList<CarteAction>pileCartesAction = new ArrayList<>();
    private ArrayList<CarteAction>défausseCarteAction = new ArrayList<>();
    private ArrayList<CarteInondation>pileCartesInondations = new ArrayList<>();
    private ArrayList<CarteInondation>défausseCarteInondations = new ArrayList<>();
    private ArrayList<Tresor>tresorsRécupérés = new ArrayList<>();
    private ArrayList<Aventurier>aventuriersADeplacer = new ArrayList<>();

    private Tresor calice;
    private Tresor pierre;
    private Tresor cristal;
    private Tresor zephyr;

    // ==============================
    // Paramètres
    private int nbJoueurs;
    private int difficulte;
    private int[] nbCarteAPiocher = {2,2,3,3,3,4,4,5,5,0};
    private ArrayList<String> pseudos;

    private boolean jeuLance = false;
    private int nbActions = 0;
    private boolean tourPassé = false;
    private int joueurActif = 0;
    boolean aAsseché = false; //afin de traiter l'assechement supplementaire de l'ingenieur
    boolean piloteSpecial = false; // Déplacement spécial du pilote
    boolean deplacementActif = false;
    boolean assechementActif = false;
    boolean defausseEnCours = false;
    private int joueurADefausser = 0;
    boolean partiePerdue = false;

    // =============================
    // Vues
    private VueMenu vueMenu = new VueMenu();
    private VueInscription vueInscription = new VueInscription();
    private VuePlateau vuePlateau;
    private VueDefausse vueDefausse;
    private VueDonCarte vueDonCarte;

    //==============================
    // Sons
    boolean musiqueCharger = false;
    boolean alarmCharger = false;
    Clip musique = null;
    Clip alarm = null;

    public Controleur() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        //Ajout observer aux différentes vues
        vueMenu.abonner(this);
        vueInscription.abonner(this);

        //Lancement du jeu
        openView(vueMenu);

    }


    public void startGame(){
        // ================================
        // Initialisation des modèles
        vueDefausse = new VueDefausse();
        vueDonCarte = new VueDonCarte();
        vueDefausse.abonner(this);
        vueDonCarte.abonner(this);

        // Initialisation des trésors
        this.calice = new Tresor(TYPE_TRESOR.CALICE);
        this.cristal = new Tresor(TYPE_TRESOR.CRISTAL);
        this.pierre = new Tresor(TYPE_TRESOR.PIERRE);
        this.zephyr = new Tresor(TYPE_TRESOR.ZEPHYR);

        // Initialisation de la pile de Carte Inondations
        for (NOM_TUILE nom_tuile : NOM_TUILE.values()){
            if(nom_tuile != NOM_TUILE.EAU) {
                CarteInondation carteInondation = new CarteInondation(getGrille().getTuile(nom_tuile));
                pileCartesInondations.add(carteInondation);
            }
        }

        // Initialisation de la pile de Carte Actions
        // Carte trésors (5 de chaque)
        for (int i=0; i<5; i++){
            CarteTresor carteTresorCalice = new CarteTresor("images/cartes/Calice.png", calice);
            pileCartesAction.add(carteTresorCalice);
            CarteTresor carteTresorCristal = new CarteTresor("images/cartes/Cristal.png", cristal);
            pileCartesAction.add(carteTresorCristal);
            CarteTresor carteTresorPierre = new CarteTresor("images/cartes/Pierre.png", pierre);
            pileCartesAction.add(carteTresorPierre);
            CarteTresor carteTresorZephyr = new CarteTresor("images/cartes/Zephyr.png", zephyr);
            pileCartesAction.add(carteTresorZephyr);
        }

        // Cartes hélicoptère et montée des eaux (3 de chaque)
        for (int i=0; i<3; i++){
            CarteHelicoptere carteHelicoptere = new CarteHelicoptere("images/cartes/Helicoptere.png");
            pileCartesAction.add(carteHelicoptere);
            CarteMonteeEaux carteMonteeEaux = new CarteMonteeEaux();
            pileCartesAction.add(carteMonteeEaux);
        }

        // Cartes sac de sable (2)
        for (int i=0; i<2; i++){
            CarteSacDeSable carteSacDeSable = new CarteSacDeSable("images/cartes/SacsDeSable.png");
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

        this.vuePlateau = new VuePlateau(pseudos, couleurs, roles, getGrille().getNomTuiles(), difficulte);
        vuePlateau.abonner(this); // Abonnement

        // Placement des pions
        for(int i = 0; i< aventuriers.size(); i++){
            updatePos(getGrille().getCordonneesTuiles(aventuriers.get(i).getPosition()), aventuriers.get(i).getPion());
        }

        // Ouverture de la vue
        closeView(vueInscription);
        openView(vuePlateau);
        jeuLance = true;

        // Tirage des 6 premières tuiles inondées
        tirageInondation(6);

        // Highlight du premier jouer
        // Il n'y a pas d'ancien joueur mais on met ancienJoueur = 3 (celui avant 0 est 3).
        vuePlateau.highlightAventurier(0, 3);
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
        // ----------- AVENTURIER ----------- //
        // ---------------------------------- //
            // Mise en évidence des tuiles pour assécher
        if (arg == Messages.ASSECHER) {
            if(!assechementActif) { // Si l'assèchement n'est pas déjà actif, pas besoin d'highlight
                vuePlateau.highlightTuiles(true, aventuriers.get(getJActif()).getTuilesAssechables(grille));
                deplacementActif = false;
                assechementActif = true;
            }
        }

            // Mise en évidence des tuiles pour se déplacer
        if (arg == Messages.DEPLACER) {
            // Afin de traiter le déplacement du pilote :
            // On vérifie que le joueur actif est un pilote
                // S'il est pilote, on affiche les tuiles disponibles en fonction de s'il a utilisé son déplacement spécial
            // SINON, on affiche les tuiles des autres aventuriers.
            if (!deplacementActif) { // Si le déplacement n'est pas déjà actif, pas besoin d'highlight
                if (aventuriers.get(joueurActif % aventuriers.size()) instanceof Pilote) {
                    vuePlateau.highlightTuiles(true, ((Pilote) aventuriers.get(getJActif())).getTuilesAccesibles(grille, piloteSpecial));
                } else {
                    vuePlateau.highlightTuiles(true, aventuriers.get(getJActif()).getTuilesAccesibles(grille));
                }
                assechementActif = false;
                deplacementActif = true;
            }
        }

        // ---------------------------------- //
        // -- VALID DEPLACEMENT/ASSECHEMENT-- //
        // ---------------------------------- //
        if(arg instanceof Message_Coche){
            // Récupération du nom de la tuile sélectionnée
            NOM_TUILE tuileSelectionnee = ((Message_Coche)arg).getNomTuile();
            // Coordonnée sélectionnee
            int[] coordSelectionnee = new int[2];
            coordSelectionnee[0] = getGrille().getCordonneesTuiles(getGrille().getTuile(tuileSelectionnee))[0];
            coordSelectionnee[1] = getGrille().getCordonneesTuiles(getGrille().getTuile(tuileSelectionnee))[1];

            // ==========================================
            // DEPLACEMENT
            if (deplacementActif){
                // ============================
                // Vérification que la tuile sélectionnée soit correcte
                boolean deplacementPossible = false;
                int i = 0;
                // Pour le pilote
                if (aventuriers.get(joueurActif % aventuriers.size()) instanceof Pilote) {
                    while(i < ((Pilote) aventuriers.get(getJActif())).getTuilesAccesibles(grille, piloteSpecial).size()-1 && deplacementPossible == false) {
                        if (((Pilote) aventuriers.get(getJActif())).getTuilesAccesibles(grille, piloteSpecial).get(i) == coordSelectionnee[0] && ((Pilote) aventuriers.get(getJActif())).getTuilesAccesibles(grille, piloteSpecial).get(i + 1) == coordSelectionnee[1]) {
                            deplacementPossible = true;
                        }
                        i += 2;
                    }
                } else { // Pour les autres aventuriers.
                    while(i < ((aventuriers.get(getJActif())).getTuilesAccesibles(grille).size())-1 && deplacementPossible == false) {
                        if ((aventuriers.get(getJActif())).getTuilesAccesibles(grille).get(i) == coordSelectionnee[0] && (aventuriers.get(getJActif())).getTuilesAccesibles(grille).get(i + 1) == coordSelectionnee[1]) {
                            deplacementPossible = true;
                        }
                        i += 2;
                    }
                }

                if(deplacementPossible) {
                    // Désactivation du highlight
                    if (aventuriers.get(joueurActif % aventuriers.size()) instanceof Pilote) {
                        vuePlateau.highlightTuiles(false, ((Pilote) aventuriers.get(getJActif())).getTuilesAccesibles(grille, piloteSpecial));
                    } else {
                        vuePlateau.highlightTuiles(false, aventuriers.get(getJActif()).getTuilesAccesibles(grille));
                    }

                    // ==========================================
                    // UPDATE MODELE / VUE :
                    // ===================================
                    // RETIRER LE PION DE L'ANCIENNE TUILE
                    // Ancienne coordonnées
                    int[] coordAncienne = new int[2];
                    coordAncienne[0] = getGrille().getCordonneesTuiles(aventuriers.get(getJActif()).getPosition())[0];
                    coordAncienne[1] = getGrille().getCordonneesTuiles(aventuriers.get(getJActif()).getPosition())[1];
                    // Update visuelle
                    ArrayList<PION> pionsTuile = vuePlateau.getTableauTuile()[coordAncienne[0]][coordAncienne[1]].getPions();
                    pionsTuile.remove(aventuriers.get(joueurActif % aventuriers.size()).getPion());
                    vuePlateau.getTableauTuile()[coordAncienne[0]][coordAncienne[1]].update(pionsTuile);

                    // =====================================
                    // AJOUTER LE PION SUR LA NOUVELLE TUILE
                    // Update visuelle
                    ArrayList<PION> newPions = vuePlateau.getTableauTuile()[coordSelectionnee[0]][coordSelectionnee[1]].getPions();
                    newPions.add(aventuriers.get(joueurActif % aventuriers.size()).getPion());
                    vuePlateau.getTableauTuile()[coordSelectionnee[0]][coordSelectionnee[1]].update(newPions);


                    // ====================================
                    // Vérification si le pilote a utilisé son déplacement spécial :
                    // Si la tuile sélectionnée n'est pas une tuile adjacente au pilote alors il a utilisé son pouvoir
                    if (aventuriers.get(getJActif()) instanceof Pilote) {
                        int j = 0;
                        boolean dedans = false;
                        while (j < ((aventuriers.get(getJActif())).getTuilesAccesibles(grille).size()) - 1 && dedans == false) {
                            if ((aventuriers.get(getJActif())).getTuilesAccesibles(grille).get(j) == coordSelectionnee[0] && (aventuriers.get(getJActif())).getTuilesAccesibles(grille).get(j + 1) == coordSelectionnee[1]) {
                                dedans = true;
                            }
                            j += 2;
                        }
                        if (!dedans) {
                            piloteSpecial = true;
                        }
                    }

                    // ====================================
                    // ACTUALISATION POSITION MODELE
                    aventuriers.get(getJActif()).setPosition(getGrille().getTuile(tuileSelectionnee));
                    deplacementActif = false;
                    nbActions++;


                    // ======================================
                    // TEST DE LA CONDITION SI PARTIE GAGNÉE
                    // Une partie est gagnée lorsque tout les trésors sont récupérés ainsi que tout les joueurs sont sur la case Heliport.
                    // Ainsi la partie est potentiellement gagnée après un déplacement uniquement !
                    if(tresorsRécupérés.size() == 4) {
                        boolean aventurierSurHeliport = true;
                        for (Aventurier aventurier : aventuriers) {
                            if (aventurier.getPosition().getNom() != NOM_TUILE.HELIPORT) {
                                aventurierSurHeliport = false;
                            }
                        }
                        if(aventurierSurHeliport) {
                            Utils.afficherInformation("Partie gagnée !");
                            enableBoutons(false);
                        }
                    }
                }

                // ==========================================
                // ASSECHEMENT
            } else if (assechementActif) {
                boolean assechementPossible = false;
                int i = 0;
                while (i < ((aventuriers.get(getJActif())).getTuilesAssechables(grille).size()) - 1 && assechementPossible == false) {
                    if ((aventuriers.get(getJActif())).getTuilesAssechables(grille).get(i) == coordSelectionnee[0] && (aventuriers.get(getJActif())).getTuilesAssechables(grille).get(i + 1) == coordSelectionnee[1]) {
                        assechementPossible = true;
                    }
                    i += 2;
                }

                if (assechementPossible) {
                    // Désactivation du highlight
                    vuePlateau.highlightTuiles(false, aventuriers.get(getJActif()).getTuilesAssechables(grille));

                    getGrille().getTuiles()[coordSelectionnee[0]][coordSelectionnee[1]].setEtat(ETAT_TUILE.SECHE);
                    vuePlateau.getTableauTuile()[coordSelectionnee[0]][coordSelectionnee[1]].setEtatTuile(ETAT_TUILE.SECHE);
                    vuePlateau.getTableauTuile()[coordSelectionnee[0]][coordSelectionnee[1]].update(vuePlateau.getTableauTuile()[coordSelectionnee[0]][coordSelectionnee[1]].getPions());
                    assechementActif = false;

                    // Assèchement bonus de l'ingénieur
                    // Si l'aventurier qui assèche est un ingénieur et qu'il n'a pas déjà asseché
                    if (aventuriers.get(getJActif()).getNomRole() == NOM_AVENTURIER.INGENIEUR && !aAsseché) {
                        aAsseché = true; // On change la valeur aAsseché en true
                    } else if (aAsseché) { // S'il a déjà asseché
                        nbActions++; // On augmente le nombre d'action effectué
                        aAsseché = false; // On remet la valeur aAsseché en false
                    } else {
                        nbActions++; // Sinon s'il s'agit pas d'un ingénieur on augmente le nombre d'action
                    }
                }
            }
        }

        // ==========================
        // OUVERTURE VUE DON CARTE
        if (arg == Messages.DONCARTE) {
            // Remise à 0 des cartes affichées dans la vue don carte.
            for(int i = 0; i < vueDonCarte.getCartes().size(); i++){
                vueDonCarte.getCartes().get(i).setCarte("images/cartes/Fond rouge.png");
            }
            for(int i = 0; i < vueDonCarte.getCartesA().size(); i++){
                vueDonCarte.getCartesA().get(i).setCarte("images/cartes/Fond rouge.png");
            }

            // On récupère les cartes que possède l'aventurier courant et on les affiche dans le vue vueDonCarte
            for(int i = 0; i < aventuriers.get(getJActif()).getCartes().size(); i++){
                vueDonCarte.getCartes().get(i).setCarte(aventuriers.get(getJActif()).getCartes().get(i).getPath());
            }

            // On récupère les aventuriers qui sont sur la même case que l'aventurier courant et on les affiche dans la vue vueDonCarte
            int index = 0;
            for(int j = 0; j < aventuriers.size(); j++){
                if(aventuriers.get(j).getPosition() == aventuriers.get(getJActif()).getPosition() && aventuriers.get(j) != aventuriers.get(getJActif())) {
                    vueDonCarte.getCartesA().get(index).setCarte("images/personnages/"+aventuriers.get(j).getNomRole().toString().toLowerCase()+".png");
                    index++;
                }
            }

            openView(vueDonCarte);
        }

        // ---------------------------------- //
        // --------- DON D'UNE CARTE -------- //
        // ---------------------------------- //
        if(arg instanceof int[]){
            // On récupère la sélection de l'aventurier
            int[] selection = (int[])arg; // selection[0] = carte que l'aventurier veut donner & selection[1] = aventurier choisi
            if(selection[0] != -1){ // La valeur de base est à -1 si rien n'est sélectionné.
                if(selection[1] != -1){
                    // On parcourt la liste des aventuriers pour trouver l'aventurier qui correspond à celui sélectionner grâce à son nom de role
                    int indexAventurierSelectionne = -1;
                    for(int i = 0; i < aventuriers.size(); i++) {
                        if (Objects.equals(aventuriers.get(i).getNomRole().toString(), vueDonCarte.getCartesA().get(selection[1]).getNomAventurier())) {
                            indexAventurierSelectionne = i;
                        }
                    }

                    if(indexAventurierSelectionne != -1) {
                        // Update Modèle
                        aventuriers.get(indexAventurierSelectionne).getCartes().add(aventuriers.get(getJActif()).getCartes().get(selection[0]));
                        aventuriers.get(getJActif()).getCartes().remove(aventuriers.get(getJActif()).getCartes().get(selection[0]));

                        // Update Visuelle de l'aventurier qui a donné
                        // Remise à 0 graphique de la liste des cartes des aventuriers
                        for (int j = 0; j < 5; j++) {
                            // Aventurier qui a donné
                            vuePlateau.getCartesAventurier().get(getJActif()).get(j).setCarte("images/cartes/Fond rouge.png");
                            // Aventurier qui reçoit
                            vuePlateau.getCartesAventurier().get(indexAventurierSelectionne).get(j).setCarte("images/cartes/Fond rouge.png");
                        }
                        // Et on remet les nouvelles cartes dans l'ordre...
                        for (int k = 0; k < vuePlateau.getCartesAventurier().size(); k++) {
                            if(k < aventuriers.get(getJActif()).getCartes().size()) {
                                vuePlateau.getCartesAventurier().get(getJActif()).get(k).setCarte(aventuriers.get(getJActif()).getCartes().get(k).getPath());
                            }
                        }

                        for(int q = 0; q < vuePlateau.getCartesAventurier().size(); q++){
                            // Aventurier qui reçoit
                            if(q < aventuriers.get(indexAventurierSelectionne).getCartes().size()){
                                vuePlateau.getCartesAventurier().get(indexAventurierSelectionne).get(q).setCarte(aventuriers.get(indexAventurierSelectionne).getCartes().get(q).getPath());
                            }
                            // Affichage de la dernière carte si l'aventurier a 5 cartes ou plus... (je suis pas arrivé à le glisser dans le if du dessus)
                            if(aventuriers.get(indexAventurierSelectionne).getCartes().size() >= 5) {
                                vuePlateau.getCartesAventurier().get(indexAventurierSelectionne).get(4).setCarte(aventuriers.get(indexAventurierSelectionne).getCartes().get(4).getPath());
                            }
                        }

                        // Si après avoir donné une carte l'aventurier sélectionné a trop de carte il doit en défausser une !
                        if (aventuriers.get(indexAventurierSelectionne).getCartes().size() > 5){
                            for(int j = 0; j < vueDefausse.getCartes().size(); j++) { // On ouvre la vue défausse avec les cartes qu'il possède (6 premières).
                                vueDefausse.getCartes().get(j).setCarte(aventuriers.get(indexAventurierSelectionne).getCartes().get(j).getPath());
                                vueDefausse.setNomJoueur(aventuriers.get(indexAventurierSelectionne).getNomJoueur());
                                openView(vueDefausse);
                                joueurADefausser = indexAventurierSelectionne;
                            }
                        }

                        nbActions++;
                        closeView((Vue)o);

                    } else {
                        Utils.afficherInformation("Vous devez sélectionner une carte et un aventurier !");
                    }
                } else {
                    Utils.afficherInformation("Vous devez sélectionner un aventurier !");
                }
            } else {
                Utils.afficherInformation("Vous devez sélectionner une carte !");
            }
        }

        // ---------------------------------- //
        // ---------- RECUP TRESOR ---------- //
        // ---------------------------------- //
        if(arg == Messages.RECUPTRESOR){
            Tresor tresorDeLaTuile = aventuriers.get(getJActif()).getPosition().getNom().getTresor();

            // Vérification que la tuile possède un trésor
            if(tresorDeLaTuile != null){
                // Vérification que le trésor n'a pas déjà été récupéré.
                if (!tresorsRécupérés.contains(tresorDeLaTuile)) {
                    // Vérification que le joueur possède assez de carte trésor pour récupérer le trésor.
                    int nbCarteTresorCorrespondante = 0;
                    ArrayList<Integer>cartesASupprimer = new ArrayList<>();

                    for (int i = 0; i < aventuriers.get(getJActif()).getCartes().size(); i++) {
                        if (aventuriers.get(getJActif()).getCartes().get(i) instanceof CarteTresor) {
                            if (tresorDeLaTuile.getTypeTresor() == ((CarteTresor) aventuriers.get(getJActif()).getCartes().get(i)).getTresor().getTypeTresor()) {
                                nbCarteTresorCorrespondante++;
                                cartesASupprimer.add(i);
                            }
                        }
                    }

                    if (nbCarteTresorCorrespondante >= 4) {
                        // Ajout du trésor à la liste des trésors récupérés
                        tresorsRécupérés.add(tresorDeLaTuile);

                        // Suppression des cartes trésors de l'aventurier dans le modèle
                        for(int i = 0; i < 4; i++){
                            aventuriers.get(getJActif()).getCartes().remove(cartesASupprimer.get(i));
                        }

                        // Remise à 0 graphique de la liste des cartes de l'aventurier
                        for (int j = 0; j < 5; j++) {
                            // Aventurier qui a donné
                            vuePlateau.getCartesAventurier().get(getJActif()).get(j).setCarte("images/cartes/Fond rouge.png");
                        }
                        // Et on remet les nouvelles cartes dans l'ordre...
                        for (int k = 0; k < aventuriers.get(getJActif()).getCartes().size(); k++) {
                            // Aventurier qui a donné
                            vuePlateau.getCartesAventurier().get(getJActif()).get(k).setCarte(aventuriers.get(getJActif()).getCartes().get(k).getPath());
                        }

                        // Affichage dans la vue du trésor gagné
                        vuePlateau.getTresors().get(tresorsRécupérés.size()-1).setCarte(tresorDeLaTuile.getTypeTresor().getPath());

                    } else {
                        Utils.afficherInformation("Vous n'avez pas assez de carte trésor " + tresorDeLaTuile.getTypeTresor().toString() + " !");
                    }
                } else {
                    Utils.afficherInformation("Ce trésor à déja été récupéré !");
                }
            } else {
                Utils.afficherInformation("Cette tuile ne possède pas de trésor !");
            }
        }

        // ---------------------------------- //
        // -----------  DEFAUSSE  ----------- //
        // ---------------------------------- //
        if(arg instanceof Integer){
            int carteChoisie = (Integer)arg;

            defausser(carteChoisie, joueurADefausser);

            if(defausseEnCours){
                prochainTour();
                defausseEnCours = false;
            }
        }

        // ---------------------------------- //
        // --------  GESTION DU TOUR -------- //
        // ---------------------------------- //

        // Si on appuie sur le bouton Fin Tour pendant un tour, tourPassé = true (ce qui aura pour effet de passer le tour)
        if (arg == Messages.FINTOUR) {
            tourPassé = true;
        }
        // On gère l'assèchement bonus de l'Ingénieur
        // Si l'ingénieur a effectué 2 actions et qu'il a déjà asséché une fois, il n'a plus le choix que de profiter de son
        // assèchement bonus ou de passer le tour.
        if (nbActions == 2 & aAsseché){
            vuePlateau.getBtnBouger().setEnabled(false);
            vuePlateau.getBtnDonCarte().setEnabled(false);
        }

        // =================
        // TOUR SUIVANT
        if(jeuLance) {
            // Si le nombre d'actions possibles de l'aventurier est égal au nombre d'actions effectuées pendant ce tour (nbActions) on passe le tour
            if ((aventuriers.get(getJActif()).getNombreAction() == nbActions)) {
                tourPassé = true;
            }

            // tourPassé est true si on a atteint le nombre maximum d'actions possibles ou qu'on a appuyé sur le bouton Fin Tour
            if (tourPassé) {
                // =================================
                // Tirage des cartes
                // Cartes inondations
                tirageInondation(nbCarteAPiocher[difficulte]);
                // Cartes actions
                tirageCarteActions();

                if (!defausseEnCours) {
                    prochainTour();
                }

                //=================================
                // TEST CONDITION PARTIE PERDUE
                // Une partie est perdue :
                // 1.Si les 2 tuiles « Temple », « Caverne », « Palais » ou « Jardin » (sur lesquelles sont placés les symboles des trésors) sombrent avant que vous n’ayez pris leurs trésors respectifs
                // 2.Si « l’héliport » sombre
                // 3.Si un joueur est sur une tuile Île qui sombre et qu’il n’y a pas de tuile adjacente où nager
                // 4.Si le Marqueur de niveau atteint la tête de mort (niveau d’eau à 10).
                // Ici on peut vérifier à la fin d'un tour (après avoir pioché des cartes inondations) les conditions 1, 2 et 3
                if ((getGrille().getTuile(NOM_TUILE.LE_PALAIS_DE_CORAIL).estCoulee() && (getGrille().getTuile(NOM_TUILE.LE_PALAIS_DES_MAREES).estCoulee()) && !tresorsRécupérés.contains(calice)) ||
                        (getGrille().getTuile(NOM_TUILE.LA_CAVERNE_DES_OMBRES).estCoulee() && (getGrille().getTuile(NOM_TUILE.LA_CAVERNE_DU_BRASIER).estCoulee()) && !tresorsRécupérés.contains(cristal)) ||
                        (getGrille().getTuile(NOM_TUILE.LE_JARDIN_DES_HURLEMENTS).estCoulee() && (getGrille().getTuile(NOM_TUILE.LE_JARDIN_DES_MURMURES).estCoulee()) && !tresorsRécupérés.contains(zephyr)) ||
                        (getGrille().getTuile(NOM_TUILE.LE_TEMPLE_DE_LA_LUNE).estCoulee() && (getGrille().getTuile(NOM_TUILE.LE_TEMPLE_DU_SOLEIL).estCoulee()) && !tresorsRécupérés.contains(pierre)) ||
                        (getGrille().getTuile(NOM_TUILE.HELIPORT).estCoulee()) ||
                        difficulte == 10){
                    partiePerdue = true;
                }
            }
        }

        if(partiePerdue){
            Utils.afficherInformation("Partie perdue !");
            enableBoutons(false);
        }

        // ---------------------------------- //
        // --------  GESTION DU SON --------- //
        // ---------------------------------- //

        if (arg == Messages.PLAY){
            if (!musiqueCharger){
                musique = chargerSon(musique,"percussions.wav");
                FloatControl f = (FloatControl) musique.getControl(FloatControl.Type.MASTER_GAIN);
                f.setValue(-20f);
                musique.start();
                musique.loop((int)musique.getMicrosecondLength());
                musiqueCharger = true;
            } else {
                if (!musique.isRunning()){
                    musique.start();
                    musique.loop((int) musique.getMicrosecondLength());
                }
            }
        }

        if (arg == Messages.PAUSE) {
            musique.stop();
        }
    }


    public void prochainTour(){
        // Désactivation du highlight si le joueur a cliqué sur le bouton déplacer / assécher mais n'a pas validé
        if(aventuriers.get(getJActif()) instanceof Pilote){
            vuePlateau.highlightTuiles(false, ((Pilote) aventuriers.get(getJActif())).getTuilesAccesibles(grille, piloteSpecial));
        } else {
            vuePlateau.highlightTuiles(false, aventuriers.get(getJActif()).getTuilesAccesibles(grille));
            vuePlateau.highlightTuiles(false, aventuriers.get(getJActif()).getTuilesAssechables(grille));
        }

        tourPassé = false; // Le tour n'est plus passé
        enableBoutons(true); // Réactivation des boutons (cas de l'ingénieur)


        // =================================
        // Remise à 0 des paramètres du tour
            joueurActif++; // On passe au prochain joueur
            nbActions = 0; // On remet le nombre d'actions effectuées à 0
            tourPassé = false; // Le tour n'est plus passé
            aAsseché = false; // On reset l'assèchement bonus pour l'ingénieur
            piloteSpecial = false; // Reset de l'action spéciale du pilote
            aventuriersADeplacer.clear(); // Il n'y a plus d'aventurier à déplacer
            vuePlateau.highlightAventurier(getJActif(), (joueurActif - 1) % aventuriers.size());

    }

    public void tirageInondation(int nbCarteAPiocher){
        Tuile tuileInondée;
        int[] coordonneeTuile = new int[2];
        // Pioche du nombre de carte à piocher (6 pour le début de la partie, niveau de l'eau à chaque fin de tour)
        for(int i = 0; i < nbCarteAPiocher; i++){
            if(!pileCartesInondations.isEmpty()) {
                // Récupération de la tuile correspondante et ses coordonnées
                tuileInondée = pileCartesInondations.get(0).getTuile();
                coordonneeTuile[0] = getGrille().getCordonneesTuiles(tuileInondée)[0];
                coordonneeTuile[1] = getGrille().getCordonneesTuiles(tuileInondée)[1];
                // Si la tuile est sèche on l'inonde, sinon on la coule et on retire la carte inondation du jeu.
                if (getGrille().getTuile(tuileInondée.getNom()).getEtat() == ETAT_TUILE.SECHE) {
                    // Actualisation modèle
                    getGrille().getTuile(tuileInondée.getNom()).setEtat(ETAT_TUILE.INONDEE);
                    // Actualisation visuelle
                    vuePlateau.getTableauTuile()[coordonneeTuile[0]][coordonneeTuile[1]].setEtatTuile(ETAT_TUILE.INONDEE);
                    vuePlateau.getTableauTuile()[coordonneeTuile[0]][coordonneeTuile[1]].update(vuePlateau.getTableauTuile()[coordonneeTuile[0]][coordonneeTuile[1]].getPions());
                    // Ajout à la défausse
                    défausseCarteInondations.add(pileCartesInondations.get(0));
                    pileCartesInondations.remove(pileCartesInondations.get(0));
                } else {
                    // Actualisation modèle
                    getGrille().getTuile(tuileInondée.getNom()).setEtat(ETAT_TUILE.COULEE);

                    // Suppression de la carte
                    pileCartesInondations.remove(pileCartesInondations.get(0));

                    // Vérification qu'un joueur n'est pas déjà sur la tuile qui vient d'être coulée
                    if (!vuePlateau.getTableauTuile()[coordonneeTuile[0]][coordonneeTuile[1]].getPions().isEmpty()) {
                        // Récupération des aventuriers présents sur la tuile
                        for (Aventurier aventurier : aventuriers) {
                            if (aventurier.getPosition().getNom() == tuileInondée.getNom()){
                                aventuriersADeplacer.add(aventurier);
                            }
                        }

                        int j;
                        boolean tuileTrouvée;
                        for (Aventurier aventurier : aventuriersADeplacer){
                            j = 0;
                            tuileTrouvée = false;
                            while(j < aventurier.getTuilesAccesibles(getGrille()).size() && !tuileTrouvée){
                                int[] nouvelleCoo = {aventurier.getTuilesAccesibles(getGrille()).get(j), aventurier.getTuilesAccesibles(getGrille()).get(j+1)};
                                if(!getGrille().getTuiles()[nouvelleCoo[0]][nouvelleCoo[1]].estCoulee()){

                                    aventurier.setPosition(getGrille().getTuiles()[nouvelleCoo[0]][nouvelleCoo[1]]);
                                    tuileTrouvée = true;

                                    // =====================================
                                    // AJOUTER LE PION SUR LA NOUVELLE TUILE
                                    // Update visuelle
                                    System.out.println(vuePlateau.getTableauTuile());
                                    ArrayList<PION> newPions = vuePlateau.getTableauTuile()[nouvelleCoo[0]][nouvelleCoo[1]].getPions();
                                    newPions.add(aventurier.getPion());
                                    vuePlateau.getTableauTuile()[nouvelleCoo[0]][nouvelleCoo[1]].update(newPions);
                                }
                                j+=2;
                            }
                            if (!tuileTrouvée){
                                partiePerdue = true;
                            }
                        }
                    }

                    // Actualisation visuelle
                    vuePlateau.getTableauTuile()[coordonneeTuile[0]][coordonneeTuile[1]].setEtatTuile(ETAT_TUILE.COULEE);
                    vuePlateau.getTableauTuile()[coordonneeTuile[0]][coordonneeTuile[1]].update(vuePlateau.getTableauTuile()[coordonneeTuile[0]][coordonneeTuile[1]].getPions());
                }

            }else {
                pileCartesInondations.addAll(défausseCarteInondations);
                défausseCarteInondations.clear();
                Collections.shuffle(pileCartesInondations);
            }
        }
    }

    public void tirageCarteActions(){

        if(pileCartesAction.isEmpty()) {
            pileCartesAction.addAll(défausseCarteAction);
            défausseCarteAction.clear();
            Collections.shuffle(pileCartesAction);
        }

        CarteAction carteActionTirée;
        for(int i = 0; i < 2; i++){
            carteActionTirée = pileCartesAction.get(0);
            // S'il s'agit d'une carte montée des eaux.
            if (carteActionTirée instanceof CarteMonteeEaux){
                difficulte++; // Augmentation de la difficultée
                pileCartesInondations.addAll(défausseCarteInondations); // On ajoute à la pile des cartes inondations la défausse inondation
                défausseCarteInondations.clear(); // On remet à 0 la défausse inondations
                Collections.shuffle(pileCartesInondations); // On remélange
                pileCartesAction.remove(carteActionTirée); // On supprimme la carte montée des eaux de la pile des cartes actions
                défausseCarteAction.add(carteActionTirée); // On l'ajoute à la défausse
                vuePlateau.setNiveau(difficulte); // Et on actualise le niveau de l'eau sur la vue.
                jouerSonMonteeDesEaux();
            } else {
                aventuriers.get(getJActif()).addCarte((CarteStockable) carteActionTirée); // On ajoute la carte à la liste des cartes que le joueur possède
                pileCartesAction.remove(carteActionTirée); // Et on la retire de la pile
                if(aventuriers.get(getJActif()).getCartes().size() < 6) {
                    vuePlateau.getCartesAventurier().get(getJActif()).get(aventuriers.get(getJActif()).getCartes().size() - 1).setCarte(carteActionTirée.getPath()); // On l'affiche sur le plateau
                }
            }
        }

        // Si malgré l'ajout de la carte il a moins de 6 cartes
        if (aventuriers.get(getJActif()).getCartes().size() >= 6) { // SINON il doit défausser !
            enableBoutons(false); // On désactive les boutons (il n'a plus le choix que de défausser)
            for(int j = 0; j < vueDefausse.getCartes().size(); j++){ // On ouvre la vue défausse avec les cartes qu'il possède (6 premières).
                vueDefausse.getCartes().get(j).setCarte(aventuriers.get(getJActif()).getCartes().get(j).getPath());
                vueDefausse.setNomJoueur(aventuriers.get(getJActif()).getNomJoueur());
                openView(vueDefausse);
                defausseEnCours = true;
                joueurADefausser = getJActif();
            }
        }

    }

    public Clip chargerSon(Clip clip,String path){
        AudioInputStream audioIn = null;
        try {
            audioIn = AudioSystem.getAudioInputStream(new File(System.getProperty("user.dir") + "/src/sons/"+path));
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            clip = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }

        try {
            clip.open(audioIn);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return clip;
    }

    public void jouerSonMonteeDesEaux(){

        if (!alarmCharger){
            alarm = chargerSon(alarm, "wave.wav");
            FloatControl f = (FloatControl) alarm.getControl(FloatControl.Type.MASTER_GAIN);
            f.setValue(-40f);
            alarm.start();
        } else {
            alarm.start();
        }

    }

    public void defausser(int carteChoisie, int joueurADefausser){
        // On ajoute la carte chosie à la pile défausse
        défausseCarteAction.add(aventuriers.get(joueurADefausser).getCartes().get(carteChoisie));

        // Et on la supprimme de la liste des carte de l'aventurier
        aventuriers.get(joueurADefausser).getCartes().remove(carteChoisie);

        // On actualise la liste des carte dans la vue
        for(int i = 0; i <  vuePlateau.getCartesAventurier().get(joueurADefausser).size() ; i++){
            vuePlateau.getCartesAventurier().get(joueurADefausser).get(i).setCarte(aventuriers.get(joueurADefausser).getCartes().get(i).getPath());
        }

        // On vérifie une seconde fois si l'aventurier n'a plus que 5 cartes (cas où il a 5 cartes et en pioche 2 en plus).
        if(aventuriers.get(joueurADefausser).getCartes().size() < 6) { // S'il a 5 ou moins
            closeView(vueDefausse); // On ferme la vue permettant de défausser une carte
            enableBoutons(true); // On ractive les boutons
        } else { // Sinon il doit encore défausser, on actualise la vue défausse avec les 6 cartes qu'il lui reste.
            for(int j = 0; j < vueDefausse.getCartes().size(); j++){
                vueDefausse.getCartes().get(j).setCarte(aventuriers.get(joueurADefausser).getCartes().get(j).getPath());
                vueDefausse.setNomJoueur(aventuriers.get(joueurADefausser).getNomJoueur());
            }
        }
    }
    public int getJActif(){
        return joueurActif % aventuriers.size();
    }

    public void enableBoutons(boolean enable){
        vuePlateau.getBtnAssecher().setEnabled(enable);
        vuePlateau.getBtnFinir().setEnabled(enable);
        vuePlateau.getBtnDonCarte().setEnabled(enable);
        vuePlateau.getBtnBouger().setEnabled(enable);
    }

    public void updatePos(int[] coordonnesJoueur, PION pion) {
        ArrayList<PION>pions;
        pions = vuePlateau.getTableauTuile()[coordonnesJoueur[0]][coordonnesJoueur[1]].getPions();
        pions.add(pion);
        vuePlateau.getTableauTuile()[coordonnesJoueur[0]][coordonnesJoueur[1]].update(pions);
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
