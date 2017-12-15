/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.main;


import java.util.ArrayList;

/**
 *
 * @author souliernLaVictime
 */
public class Grille {

    private Tuile tuiles[][] = new Tuile[6][6];
    
    public Grille() {
         int indexEnum = 1; // L'index commence à 1 et non 0 car l'ordinal 0 de l'enum est la tuile eau
         for(int j = 0; j < tuiles.length ; j++){ // Lignes
             for(int i = 0; i < tuiles.length; i++){ // Colonnes
                if(((j == 0 || j == 5) && (i == 0 || i == 1 || i == 4 || i == 5)) || ((j == 1 || j == 4) && (i==0 || i == 5))){ // Si les coordonnées i,j correspondent, il s'agit d'une tuile eau
                    tuiles[j][i] = new Tuile(NOM_TUILE.EAU, ETAT_TUILE.COULEE); // On créé une tuile d'eau qu'on ajoute au tableau des tuiles.
                 } else {
                    tuiles[j][i] = new Tuile(NOM_TUILE.values()[indexEnum], ETAT_TUILE.SECHE); // Sinon, on créé une tuile normale (le nom étant la valeur de indexEnum dans la liste de l'enum)
                    indexEnum++;
                }
             }
         }
    }

    public Tuile[][] getTuiles() {
        return tuiles;
    }

    public ArrayList getTuilesAdjacentes(Tuile tuile,Messages messages){

        ArrayList<Tuile> tuilesAdjacentes = new ArrayList<>();
        int[] coordonnes = this.getCordonneesTuiles(tuile);

        if (messages == Messages.DEPLACER) {
            if (coordonnes[0]!=0 && !tuiles[coordonnes[0]-1][coordonnes[1]].estCoulee()){
                tuilesAdjacentes.add(tuiles[coordonnes[0]-1][coordonnes[1]]);
            }
            if (coordonnes[0]!=5 && !tuiles[coordonnes[0]+1][coordonnes[1]].estCoulee()){
                tuilesAdjacentes.add(tuiles[coordonnes[0]+1][coordonnes[1]]);
            }
            if (coordonnes[1]!=0 && !tuiles[coordonnes[0]][coordonnes[1]-1].estCoulee()){
                tuilesAdjacentes.add(tuiles[coordonnes[0]][coordonnes[1]-1]);
            }
            if (coordonnes[1]!=5 && !tuiles[coordonnes[0]][coordonnes[1]+1].estCoulee()){
                tuilesAdjacentes.add(tuiles[coordonnes[0]-1][coordonnes[1]=1]);
            }

        }

        if (messages == Messages.DEPLACER) {
            if (coordonnes[0]!=0 && !tuiles[coordonnes[0]-1][coordonnes[1]].estCoulee()){
                tuilesAdjacentes.add(tuiles[coordonnes[0]-1][coordonnes[1]]);
            }
            if (coordonnes[0]!=5 && !tuiles[coordonnes[0]+1][coordonnes[1]].estCoulee()){
                tuilesAdjacentes.add(tuiles[coordonnes[0]+1][coordonnes[1]]);
            }
            if (coordonnes[1]!=0 && !tuiles[coordonnes[0]][coordonnes[1]-1].estCoulee()){
                tuilesAdjacentes.add(tuiles[coordonnes[0]][coordonnes[1]-1]);
            }
            if (coordonnes[1]!=5 && !tuiles[coordonnes[0]][coordonnes[1]+1].estCoulee()){
                tuilesAdjacentes.add(tuiles[coordonnes[0]-1][coordonnes[1]=1]);
            }

        }

       /** if (coordonnes[0] **/



        return null;
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


}
