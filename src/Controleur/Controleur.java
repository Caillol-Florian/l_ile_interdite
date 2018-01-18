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
    private ArrayList<CarteAction>défausseCarteAction = new ArrayList<>();
    private ArrayList<CarteInondation>pileCartesInondations = new ArrayList<>();
    private ArrayList<CarteInondation>défausseCarteInondations = new ArrayList<>();

    private ArrayList<NOM_AVENTURIER>roles = new ArrayList<>();

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

    // =============================
    // Vues
    private VueMenu vueMenu = new VueMenu();
    private VueInscription vueInscription = new VueInscription();
    private VuePlateau vuePlateau;
    private VueDefausse vueDefausse = new VueDefausse();

    public Controleur() throws LineUnavailableException, IOException, UnsupportedAudioFileException {

        //Ajout observer aux différentes vues
        vueMenu.abonner(this);
        vueInscription.abonner(this);
        vueDefausse.abonner(this);
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
            if(nom_tuile != NOM_TUILE.EAU) {
                CarteInondation carteInondation = new CarteInondation(getGrille().getTuile(nom_tuile));
                pileCartesInondations.add(carteInondation);
            }
        }

        // Initialisation de la pile de Carte Actions
        // Carte trésors (5 de chaque)
        for (int i=0; i<5; i++){
            CarteTresor carteTresorCalice = new CarteTresor("images/cartes/Calice.png");
            pileCartesAction.add(carteTresorCalice);
            CarteTresor carteTresorCristal = new CarteTresor("images/cartes/Cristal.png");
            pileCartesAction.add(carteTresorCristal);
            CarteTresor carteTresorPierre = new CarteTresor("images/cartes/Pierre.png");
            pileCartesAction.add(carteTresorPierre);
            CarteTresor carteTresorZephyr = new CarteTresor("images/cartes/Zephyr.png");
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

        // Tirage des 6 premières tuiles inondées
        tirageInondation(6);
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
            vuePlateau.highlightTuiles(true, aventuriers.get(getJActif()).getTuilesAssechables(grille));
            deplacementActif = false;
            assechementActif = true;
        }

            // Mise en évidence des tuiles pour se déplacer
        if (arg == Messages.DEPLACER) {
            // Afin de traiter le déplacement du pilote :
            // On vérifie que le joueur actif est un pilote
                // S'il est pilote, on affiche les tuiles disponibles en fonction de s'il a utilisé son déplacement spécial
            // SINON, on affiche les tuiles des autres aventuriers.
            if(aventuriers.get(joueurActif%aventuriers.size()) instanceof Pilote){
                vuePlateau.highlightTuiles(true, ((Pilote) aventuriers.get(getJActif())).getTuilesAccesibles(grille, piloteSpecial));
            } else {
                vuePlateau.highlightTuiles(true, aventuriers.get(getJActif()).getTuilesAccesibles(grille));
            }
            assechementActif = false;
            deplacementActif = true;
        }

        // ===============================================
        // VALIDATION DEPLACEMENT / ASSECHEMENT
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
                while(i < ((aventuriers.get(getJActif())).getTuilesAccesibles(grille).size())-1 && deplacementPossible == false){
                    if((aventuriers.get(getJActif())).getTuilesAccesibles(grille).get(i) == coordSelectionnee[0] && (aventuriers.get(getJActif())).getTuilesAccesibles(grille).get(i+1) == coordSelectionnee[1]){
                        deplacementPossible = true;
                    }
                    i+=2;
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
                    // AJOUTER LE PION SUR L'ANCIENNE TUILE
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
                            if ((aventuriers.get(getJActif())).getTuilesAccesibles(grille).get(i) == coordSelectionnee[0] && (aventuriers.get(getJActif())).getTuilesAccesibles(grille).get(i + 1) == coordSelectionnee[1]) {
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
            vuePlateau.getBtnBouger().setEnabled(false);
            vuePlateau.getBtnAutre().setEnabled(false);
        }

        // ---------------------------------- //
        // --------  GESTION DU TOUR -------- //
        // ---------------------------------- //
        if(jeuLance) {
            // Si le nombre d'actions possibles de l'aventurier est égal au nombre d'actions effectuées pendant ce tour (nbActions) on passe le tour
            if ((aventuriers.get(getJActif()).getNombreAction() == nbActions)) {
                tourPassé = true;
            }

            // tourPassé est true si on a atteint le nombre maximum d'actions possibles ou qu'on a appuyé sur le bouton Fin Tour
            if (tourPassé) {
                // Désactivation du highlight si le joueur a cliqué sur le bouton déplacer / assécher mais n'a pas validé l'assèchement
                if(aventuriers.get(getJActif()) instanceof Pilote){
                    vuePlateau.highlightTuiles(false, ((Pilote) aventuriers.get(getJActif())).getTuilesAccesibles(grille, piloteSpecial));
                } else {
                    vuePlateau.highlightTuiles(false, aventuriers.get(getJActif()).getTuilesAccesibles(grille));
                    vuePlateau.highlightTuiles(false, aventuriers.get(getJActif()).getTuilesAssechables(grille));
                }

                tourPassé = false; // Le tour n'est plus passé
                enableBoutons(true); // Réactivation des boutons (cas de l'ingénieur)

                // =================================
                // Tirage des cartes
                // Cartes inondations
                tirageInondation(nbCarteAPiocher[difficulte]);
                // Cartes actions
                tirageCarteActions();

                // =================================
                // Remise à 0 des paramètres du tour
                joueurActif++; // On passe au prochain joueur
                nbActions = 0; // On remet le nombre d'actions effectuées à 0
                tourPassé = false; // Le tour n'est plus passé
                aAsseché = false; // On reset l'assèchement bonus pour l'ingénieur
                piloteSpecial = false; // Reset de l'action spéciale du pilote

            }
        }

        // ---------------------------------- //
        // -----------  DEFAUSSE  ----------- //
        // ---------------------------------- //
        if(arg instanceof Integer){
            int carteChoisie = (Integer)arg;
            // On ajoute la carte chosie à la pile défausse
            défausseCarteAction.add(aventuriers.get(((joueurActif-1)%aventuriers.size())).getCartes().get(carteChoisie));
            // Et on la supprimme de la liste des carte de l'aventurier
            aventuriers.get(((joueurActif-1)%aventuriers.size())).getCartes().remove(carteChoisie);
            // On actualise la liste des carte dans la vue
            for(int i = 0; i < aventuriers.get(((joueurActif-1)%aventuriers.size())).getCartes().size(); i++){
                vuePlateau.getCartesAventurier().get(((joueurActif-1)%aventuriers.size())).get(i).setCarte(aventuriers.get(((joueurActif-1)%aventuriers.size())).getCartes().get(i).getPath());
            }
            // On ferme la vue permettant de défausser une carte
            closeView(vueDefausse);
            enableBoutons(true);
        }
    }


    public void updatePos(int[] coordonnesJoueur, PION pion) {
        ArrayList<PION>pions;
        pions = vuePlateau.getTableauTuile()[coordonnesJoueur[0]][coordonnesJoueur[1]].getPions();
        pions.add(pion);
        vuePlateau.getTableauTuile()[coordonnesJoueur[0]][coordonnesJoueur[1]].update(pions);
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
                // Si la tuile est sèche on l'inonde, sinon on la coule et on la carte inondation du jeu.
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
                    getGrille().getTuile(tuileInondée.getNom()).setEtat(ETAT_TUILE.COULEE);
                    vuePlateau.getTableauTuile()[coordonneeTuile[0]][coordonneeTuile[1]].setEtatTuile(ETAT_TUILE.COULEE);
                    vuePlateau.getTableauTuile()[coordonneeTuile[0]][coordonneeTuile[1]].update(vuePlateau.getTableauTuile()[coordonneeTuile[0]][coordonneeTuile[1]].getPions());
                    pileCartesInondations.remove(pileCartesInondations.get(0));
                }
            } else {
                pileCartesInondations.addAll(défausseCarteInondations);
                Collections.shuffle(pileCartesInondations);
            }
        }
    }

    public void tirageCarteActions(){
        CarteAction carteActionTirée;
        for(int i = 0; i < 2; i++){
            carteActionTirée = pileCartesAction.get(0);
            System.out.println(carteActionTirée.getPath());
            if (carteActionTirée instanceof CarteMonteeEaux){
                difficulte++;
                pileCartesInondations.addAll(défausseCarteInondations);
                défausseCarteInondations.clear();
                Collections.shuffle(pileCartesInondations);
                pileCartesAction.remove(carteActionTirée);
            } else {
                if(aventuriers.get(getJActif()).getCartes().size() != 5) {
                    aventuriers.get(getJActif()).addCarte((CarteStockable) carteActionTirée);
                    vuePlateau.getCartesAventurier().get(getJActif()).get(aventuriers.get(getJActif()).getCartes().size() - 1).setCarte(carteActionTirée.getPath());
                    défausseCarteAction.add(carteActionTirée);
                    pileCartesAction.remove(carteActionTirée);
                } else {
                    aventuriers.get(getJActif()).addCarte((CarteStockable) carteActionTirée);
                    enableBoutons(false);
                    for(int j = 0; j < aventuriers.get(getJActif()).getCartes().size(); j++){
                        vueDefausse.getCartes().get(j).setCarte(aventuriers.get(getJActif()).getCartes().get(j).getPath());
                        openView(vueDefausse);
                    }
                }
            }
        }
    }

    public int getJActif(){
        return joueurActif % aventuriers.size();
    }

    public void enableBoutons(boolean enable){
        vuePlateau.getBtnAssecher().setEnabled(enable);
        vuePlateau.getBtnFinir().setEnabled(enable);
        vuePlateau.getBtnAutre().setEnabled(enable);
        vuePlateau.getBtnBouger().setEnabled(enable);
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
