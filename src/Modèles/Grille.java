/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modèles;


import Enums.ETAT_TUILE;
import Enums.Messages;
import Enums.NOM_TUILE;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author souliern
 */
public class Grille {

    private Tuile tuiles[][] = new Tuile[6][6];
    private ArrayList <NOM_TUILE> nomTuiles = new ArrayList<>();
    public Grille() {

        // ======================================
        // Génération aléatoire

        // Récupération des noms des tuiles
        for(NOM_TUILE nomTuile : NOM_TUILE.values()){
            this.getNomTuiles().add(nomTuile);
        }

        // On retire l'eau.
        nomTuiles.remove(NOM_TUILE.EAU);
        // On mélange aléatoirement
        Collections.shuffle(getNomTuiles());

        // ======================================
        // Création de la grille
         int indexEnum = 0;
         for(int j = 0; j < tuiles.length ; j++){ // Lignes
             for(int i = 0; i < tuiles.length; i++){ // Colonnes
                if(((j == 0 || j == 5) && (i == 0 || i == 1 || i == 4 || i == 5)) || ((j == 1 || j == 4) && (i==0 || i == 5))){ // Si les coordonnées i,j correspondent, il s'agit d'une tuile eau
                    tuiles[j][i] = new Tuile(NOM_TUILE.EAU, ETAT_TUILE.COULEE); // On créé une tuile d'eau qu'on ajoute au tableau des tuiles.
                 } else {
                    tuiles[j][i] = new Tuile(getNomTuiles().get(indexEnum), ETAT_TUILE.SECHE); // Sinon, on créé une tuile normale (le nom étant la valeur de indexEnum dans la liste de l'enum)
                    indexEnum++; // On augmente l'index pour la prochaine tuile à créer
                }
             }
         }
    }

    public Tuile[][] getTuiles() {
        return tuiles;
    }

    public ArrayList getTuilesAdjacentes(Tuile tuile, Messages messages){

        ArrayList<Integer> tuilesAdjacentes = new ArrayList<>();
        int[] coordonnes = this.getCordonneesTuiles(tuile);

        if (messages == Messages.DEPLACER) {
            if (coordonnes[0]!=0 && !tuiles[coordonnes[0]-1][coordonnes[1]].estCoulee()){
                tuilesAdjacentes.add(coordonnes[0]-1); tuilesAdjacentes.add(coordonnes[1]);
            }

            if (coordonnes[0]!=5 && !tuiles[coordonnes[0]+1][coordonnes[1]].estCoulee()){
                tuilesAdjacentes.add(coordonnes[0]+1); tuilesAdjacentes.add(coordonnes[1]);
            }
            if (coordonnes[1]!=0 && !tuiles[coordonnes[0]][coordonnes[1]-1].estCoulee()){
                tuilesAdjacentes.add(coordonnes[0]); tuilesAdjacentes.add(coordonnes[1]-1);
            }
            if (coordonnes[1]!=5 && !tuiles[coordonnes[0]][coordonnes[1]+1].estCoulee()){
                tuilesAdjacentes.add(coordonnes[0]); tuilesAdjacentes.add(coordonnes[1]+1);
            }
        }

        if (messages == Messages.ASSECHER) {
            if (coordonnes[0]!=0 && tuiles[coordonnes[0]-1][coordonnes[1]].estInondee()) {
                tuilesAdjacentes.add(coordonnes[0]-1); tuilesAdjacentes.add(coordonnes[1]);
            }
            if (coordonnes[0]!=5 && tuiles[coordonnes[0]+1][coordonnes[1]].estInondee()){
                tuilesAdjacentes.add(coordonnes[0]+1); tuilesAdjacentes.add(coordonnes[1]);
            }
            if (coordonnes[1]!=0 && tuiles[coordonnes[0]][coordonnes[1]-1].estInondee()){
                tuilesAdjacentes.add(coordonnes[0]); tuilesAdjacentes.add(coordonnes[1]-1);
            }
            if (coordonnes[1]!=5 && tuiles[coordonnes[0]][coordonnes[1]+1].estInondee()){
                tuilesAdjacentes.add(coordonnes[0]); tuilesAdjacentes.add(coordonnes[1]+1);
            }
            if(tuile.estInondee()){
                    tuilesAdjacentes.add(coordonnes[0]); tuilesAdjacentes.add(coordonnes[1]);
            }
        }

        return tuilesAdjacentes;
    }

    public ArrayList getTuilesDiagonales(Tuile tuile,Messages messages) {

        ArrayList<Integer> tuilesDiagonales = new ArrayList<>();
        int[] coordonnes = this.getCordonneesTuiles(tuile);

        if (messages == Messages.DEPLACER) {
            if (coordonnes[0] != 0 && coordonnes[1] != 0 && !tuiles[coordonnes[0] - 1][coordonnes[1] - 1].estCoulee()) {
                tuilesDiagonales.add(coordonnes[0]-1); tuilesDiagonales.add(coordonnes[1]-1);
            }
            if (coordonnes[0] != 0 && coordonnes[1] != 5 && !tuiles[coordonnes[0] - 1][coordonnes[1] + 1].estCoulee()) {
                tuilesDiagonales.add(coordonnes[0]-1); tuilesDiagonales.add(coordonnes[1]+1);
            }
            if (coordonnes[1] != 0 && coordonnes[0] != 5 && !tuiles[coordonnes[0] + 1][coordonnes[1] - 1].estCoulee()) {
                tuilesDiagonales.add(coordonnes[0]+1); tuilesDiagonales.add(coordonnes[1]-1);
            }
            if (coordonnes[1] != 5 && coordonnes[0] != 5 && !tuiles[coordonnes[0] + 1][coordonnes[1] + 1].estCoulee()) {
                tuilesDiagonales.add(coordonnes[0]+1); tuilesDiagonales.add(coordonnes[1]+1);
            }
        }

        if (messages == Messages.ASSECHER) {
            if (coordonnes[0] != 0 && coordonnes[1] != 0 && tuiles[coordonnes[0] - 1][coordonnes[1] - 1].estInondee()) {
                tuilesDiagonales.add(coordonnes[0]-1); tuilesDiagonales.add(coordonnes[1]-1);
            }
            if (coordonnes[0] != 0 && coordonnes[1] != 5 && tuiles[coordonnes[0] - 1][coordonnes[1] + 1].estInondee()) {
                tuilesDiagonales.add(coordonnes[0]-1); tuilesDiagonales.add(coordonnes[1]+1);

            }
            if (coordonnes[1]!= 0 && coordonnes[0] != 5 && tuiles[coordonnes[0] + 1][coordonnes[1]-1].estInondee()){
                tuilesDiagonales.add(coordonnes[0]+1); tuilesDiagonales.add(coordonnes[1]-1);
            }
            if (coordonnes[1]!= 5 && coordonnes[0] != 5 && tuiles[coordonnes[0] + 1][coordonnes[1]+1].estInondee()){
                tuilesDiagonales.add(coordonnes[0]+1); tuilesDiagonales.add(coordonnes[1]+1);
            }
        }

        return tuilesDiagonales;
    }

    public ArrayList getTuilesPlongeur(Tuile tuile){

        ArrayList<Tuile>tuilesPlongeur = new ArrayList<>();
        ArrayList<Tuile>tuilesPlongeurIntermediaire = new ArrayList<>();
        ArrayList<Tuile>tuilesPlongeurIntermediaire2 = new ArrayList<>();
        ArrayList<Integer>cordonneesFinales = new ArrayList<>();
        Boolean rechercheDeTuile = false;

        int[] coordonnes = this.getCordonneesTuiles(tuile);

        if (coordonnes[0]!=0 && !tuiles[coordonnes[0]-1][coordonnes[1]].estSeche() && !tuiles[coordonnes[0]-1][coordonnes[1]].estOcean()){
            tuilesPlongeurIntermediaire2.add(tuiles[coordonnes[0]-1][coordonnes[1]]);
        }
        if (coordonnes[0]!=5 && !tuiles[coordonnes[0]+1][coordonnes[1]].estSeche() && !tuiles[coordonnes[0]+1][coordonnes[1]].estOcean()){
            tuilesPlongeurIntermediaire2.add(tuiles[coordonnes[0]+1][coordonnes[1]]);
        }
        if (coordonnes[1]!=0 && !tuiles[coordonnes[0]][coordonnes[1]-1].estSeche() && !tuiles[coordonnes[0]][coordonnes[1]-1].estOcean()){
            tuilesPlongeurIntermediaire2.add(tuiles[coordonnes[0]][coordonnes[1]-1]);
        }
        if (coordonnes[1]!=5 && !tuiles[coordonnes[0]][coordonnes[1]+1].estSeche() && !tuiles[coordonnes[0]][coordonnes[1]+1].estOcean()){
            tuilesPlongeurIntermediaire2.add(tuiles[coordonnes[0]][coordonnes[1]+1]);
        }

        for (int i = 0; i< tuilesPlongeurIntermediaire2.size(); i++){
        }

        while (tuilesPlongeurIntermediaire.size() != tuilesPlongeurIntermediaire2.size()){

            rechercheDeTuile = true;

            for (Tuile tuileACopier : tuilesPlongeurIntermediaire2){
                if (!tuilesPlongeurIntermediaire.contains(tuileACopier)){
                    tuilesPlongeurIntermediaire.add(tuileACopier);
                }
            }

            for (int i = 0; i < tuilesPlongeurIntermediaire2.size(); i++){

                coordonnes = getCordonneesTuiles(tuilesPlongeurIntermediaire2.get(i));

                if (coordonnes[0]!=0 && !tuiles[coordonnes[0]-1][coordonnes[1]].estSeche() && !tuiles[coordonnes[0]-1][coordonnes[1]].estOcean()){
                    if (!tuilesPlongeurIntermediaire2.contains(tuiles[coordonnes[0]-1][coordonnes[1]])){
                        tuilesPlongeurIntermediaire2.add(tuiles[coordonnes[0]-1][coordonnes[1]]);
                    }
                }
                if (coordonnes[0]!=5 && !tuiles[coordonnes[0]+1][coordonnes[1]].estSeche() && !tuiles[coordonnes[0]+1][coordonnes[1]].estOcean()){
                    if (!tuilesPlongeurIntermediaire2.contains(tuiles[coordonnes[0]+1][coordonnes[1]])){
                        tuilesPlongeurIntermediaire2.add(tuiles[coordonnes[0]+1][coordonnes[1]]);
                    }
                }
                if (coordonnes[1]!=0 && !tuiles[coordonnes[0]][coordonnes[1]-1].estSeche() && !tuiles[coordonnes[0]][coordonnes[1]-1].estOcean()){
                    if (!tuilesPlongeurIntermediaire2.contains(tuiles[coordonnes[0]][coordonnes[1]-1])){
                        tuilesPlongeurIntermediaire2.add(tuiles[coordonnes[0]][coordonnes[1]-1]);
                    }
                }
                if (coordonnes[1]!=5 && !tuiles[coordonnes[0]][coordonnes[1]+1].estSeche() && !tuiles[coordonnes[0]][coordonnes[1]+1].estOcean()){
                    if (!tuilesPlongeurIntermediaire2.contains(tuiles[coordonnes[0]][coordonnes[1]+1])){
                        tuilesPlongeurIntermediaire2.add(tuiles[coordonnes[0]][coordonnes[1]+1]);
                    }
                }
            }
        }

        if (rechercheDeTuile){

            for (int i = 0; i < tuilesPlongeurIntermediaire.size(); i++){

                coordonnes = getCordonneesTuiles(tuilesPlongeurIntermediaire.get(i));

                if (coordonnes[0]!=0 && tuiles[coordonnes[0]-1][coordonnes[1]].estSeche()){
                    if (!tuilesPlongeur.contains(tuiles[coordonnes[0]-1][coordonnes[1]])){
                        tuilesPlongeur.add(tuiles[coordonnes[0]-1][coordonnes[1]]);
                    }
                }
                if (coordonnes[0]!=5 && tuiles[coordonnes[0]+1][coordonnes[1]].estSeche()){
                    if (!tuilesPlongeur.contains(tuiles[coordonnes[0]+1][coordonnes[1]])){
                        tuilesPlongeur.add(tuiles[coordonnes[0]+1][coordonnes[1]]);
                    }
                }
                if (coordonnes[1]!=0 && tuiles[coordonnes[0]][coordonnes[1]-1].estSeche()){
                    if (!tuilesPlongeur.contains(tuiles[coordonnes[0]][coordonnes[1]-1])){
                        tuilesPlongeur.add(tuiles[coordonnes[0]][coordonnes[1]-1]);
                    }
                }
                if (coordonnes[1]!=5 && tuiles[coordonnes[0]][coordonnes[1]+1].estSeche()){
                    if (!tuilesPlongeur.contains(tuiles[coordonnes[0]][coordonnes[1]+1])){
                        tuilesPlongeur.add(tuiles[coordonnes[0]][coordonnes[1]+1]);
                    }
                }
            }

            for (Tuile tuileInnondeAccessible : tuilesPlongeurIntermediaire){
                if (tuileInnondeAccessible.estInondee()){
                    tuilesPlongeur.add(tuileInnondeAccessible);
                }
            }
        }

        tuilesPlongeur.remove(tuile);

        for(Tuile tuileP : tuilesPlongeur){
            cordonneesFinales.add(getCordonneesTuiles(tuileP)[0]);
            cordonneesFinales.add(getCordonneesTuiles(tuileP)[1]);
        }

        return cordonneesFinales;


    }

    public ArrayList<Integer> getTuilesNonCoulee(Tuile tuile){
        ArrayList<Integer>tuilesSeches = new ArrayList<>();

        for(int i = 0; i < tuiles.length ; i++) { // Lignes
            for (int j = 0; j < tuiles.length; j++) { // Colonnes
                if (!tuiles[i][j].estCoulee() && tuiles[i][j] != tuile){
                    tuilesSeches.add(getCordonneesTuiles(tuiles[i][j])[0]);
                    tuilesSeches.add(getCordonneesTuiles(tuiles[i][j])[1]);
                }
            }
        }

        return tuilesSeches;
    }

    public Tuile getTuile(NOM_TUILE nomTuile){
        Tuile tuile = new Tuile();
        for(int i = 0; i < tuiles.length ; i++) { // Lignes
            for (int j = 0; j < tuiles.length; j++) { // Colonnes
                if (nomTuile.toString().equals(tuiles[i][j].toString())){
                    tuile = tuiles[i][j];
                }
            }
        }
        return tuile;
    }

    public int[] getCordonneesTuiles(Tuile tuile){
        int[] coordonnees = new int[2];
        for(int i = 0; i < tuiles.length ; i++) { // Lignes
            for (int j = 0; j < tuiles.length; j++) { // Colonnes
                if (tuile.toString().equals(tuiles[i][j].toString())){
                    coordonnees[0] = i;
                    coordonnees[1] = j;
                }
            }
        }
        return coordonnees;
    }

    public ArrayList<NOM_TUILE> getNomTuiles() {
        return nomTuiles;
    }
}
