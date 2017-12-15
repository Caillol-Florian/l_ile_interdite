package main.main;
        import Aventurier.Explorateur;
        import Views.*;
        import main.main.Utils.Pion;
        import java.util.ArrayList;
        import Controleur.*;

public class Main {

    public static void main(String[] args) {
        Controleur controleur = new Controleur();
        Explorateur greg = new Explorateur(controleur.getGrille().getTuile(NOM_TUILE.LA_PORTE_DE_CUIVRE), "Greg");

        VueAventurier vueAventurier = new VueAventurier(greg.getNom(), "Explorateur", Pion.VERT.getCouleur() );
        VueAssechement vueAssechement = new VueAssechement();
        VueDeplacement vueDeplacement = new VueDeplacement();

        controleur.addView(vueAventurier);
        controleur.addView(vueAssechement);
        controleur.addView(vueDeplacement);

        controleur.addAventurier(greg);

        controleur.startGame(greg);
    }
}
