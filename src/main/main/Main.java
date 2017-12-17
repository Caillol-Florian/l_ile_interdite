package main.main;
        import Aventurier.Pilote;
        import Views.*;
        import main.main.Utils.Pion;
        import Controleur.*;

public class Main {

    public static void main(String[] args) {
        Controleur controleur = new Controleur();

        VueInscription vueInscription = new VueInscription();
        VueAssechement vueAssechement = new VueAssechement();
        VueDeplacement vueDeplacement = new VueDeplacement();

        controleur.addView(vueInscription);
        controleur.addView(vueAssechement);
        controleur.addView(vueDeplacement);

        controleur.startInscription();


        for(int i = 0; i < controleur.getGrille().getTuiles().length ; i++) { // Lignes
            for (int j = 0; j < controleur.getGrille().getTuiles().length; j++) { // Colonnes
                System.out.print(i); System.out.print(" - " + j);System.out.print(controleur.getGrille().getTuiles()[i][j]);System.out.print("|");
                }
                System.out.println();
            }
        }
        }

