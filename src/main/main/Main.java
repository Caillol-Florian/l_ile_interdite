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

        controleur.getGrille().getTuiles()[0][3].setEtat(ETAT_TUILE.INONDEE);
        controleur.getGrille().getTuiles()[1][2].setEtat(ETAT_TUILE.INONDEE);
        controleur.getGrille().getTuiles()[2][2].setEtat(ETAT_TUILE.COULEE);
        controleur.getGrille().getTuiles()[3][1].setEtat(ETAT_TUILE.INONDEE);
        controleur.getGrille().getTuiles()[3][3].setEtat(ETAT_TUILE.INONDEE);
        controleur.getGrille().getTuiles()[3][4].setEtat(ETAT_TUILE.COULEE);
        controleur.getGrille().getTuiles()[4][2].setEtat(ETAT_TUILE.COULEE);
        controleur.getGrille().getTuiles()[5][3].setEtat(ETAT_TUILE.INONDEE);

        controleur.getGrille().getTuiles()[3][2].setEtat(ETAT_TUILE.COULEE);

        controleur.startInscription();
    }



        }

