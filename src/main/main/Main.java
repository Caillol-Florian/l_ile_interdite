package main.main;
        import Views.VueDeplacement ;
public class Main {

    public static void main(String[] args) {
        // write your code here
        VueDeplacement vueDep = new VueDeplacement();
     //   vueDep.setVisible();

        Grille grille = new Grille();
        for (int i = 0; i < 6 ; i++){
            for(int j = 0; j < 6 ; j++){
                System.out.print(" " + i + "-" + j + " ");System.out.print(grille.getTuiles()[i][j].nom); System.out.print("|");
            }
            System.out.println();
        }

    }
}
