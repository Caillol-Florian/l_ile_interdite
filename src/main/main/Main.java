package main.main;
        import Views.VueAssechement;

        import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        Controleur controleur = new Controleur();
        VueAssechement vueAssechement = new VueAssechement();
        controleur.addView(vueAssechement);

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
    }
}
