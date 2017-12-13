package main.main;
        import Views.VueAssechement;

        import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        // write your code here
        VueAssechement vueDep = new VueAssechement();
        vueDep.setVisible();
        Grille gri = new Grille();
        ArrayList<Tuile> a = new ArrayList<Tuile>();

        Grille grille = new Grille();
        for (int i = 0; i < 6 ; i++){
            for(int j = 0; j < 6 ; j++){
                a.add(grille.getTuiles()[i][j]);
            }
            System.out.println();
        }
         ;
        vueDep.setAvailableTuile(a);

    }
}
