package main.main;
        import Aventurier.Pilote;
        import Views.*;
        import main.main.Utils.Pion;
        import Controleur.*;

public class Main {

    public static void main(String[] args) {
        Controleur controleur = new Controleur();
        Pilote greg = new Pilote(controleur.getGrille().getTuile(NOM_TUILE.LA_PORTE_DE_CUIVRE), "Greg");

        VueInscription vueInscription = new VueInscription();
        VueAventurier vueAventurier = new VueAventurier(greg.getNomJoueur(), greg.getNomRole(), greg.getPion().getCouleur());
        VueAssechement vueAssechement = new VueAssechement();
        VueDeplacement vueDeplacement = new VueDeplacement();

        controleur.addView(vueInscription);
        controleur.addView(vueAventurier);
        controleur.addView(vueAssechement);
        controleur.addView(vueDeplacement);

        controleur.startGame();
        }
}
