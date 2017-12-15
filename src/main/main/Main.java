package main.main;
        import Views.*;
        import main.main.Utils.Pion;
        import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        Controleur controleur = new Controleur();
        VueAventurier vueAventurier = new VueAventurier("Marion", "Explorateur", Pion.ROUGE.getCouleur() );
        VueAssechement vueAssechement = new VueAssechement();
        VueDeplacement vueDeplacement = new VueDeplacement();
        controleur.addView(vueAventurier);
        controleur.addView(vueAssechement);
        controleur.addView(vueDeplacement);

        // Test de l'affichage des tuiles dans un ComboBox
        ArrayList<Tuile>a = new ArrayList<>();

        for (int i = 0; i < 6 ; i++){
            for(int j = 0; j < 6 ; j++){
                if (controleur.getGrille().getTuiles()[i][j].toString()!="Eau"){
                    a.add(controleur.getGrille().getTuiles()[i][j]);
                }
            }
        }

        vueAssechement.setAvailableTuile(a);
        // Fin du test

        controleur.openView(vueAventurier);
    }
}
