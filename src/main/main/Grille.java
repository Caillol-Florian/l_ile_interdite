/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.main;


/**
 *
 * @author souliern
 */
public class Grille {
    private Tuile tuiles[][] = new Tuile[6][6];
    
    public Grille() {

        /*for(int i = 0; i<36; i++){
            // Cases d'eau pour ces valeurs de i
            if (i ==0 || i == 1 || i ==4 || i ==5 || i == 6 || i ==11 || i ==24 || i ==29 || i ==30 || i == 31 || i ==34 || i ==35){
                tuiles[i%6][j] = new Tuile (NOM_TUILE.EAU); // On créé une case d'eau
                // On ajoute la tuile au tableau de tuiles i étant la ligne et y étant la colonne
            } else {
                if(j == 1 && i == 0) {
                    System.out.println("aa");
                }
                Tuile tuile = new Tuile(NOM_TUILE.values()[indexEnum]); // On créé une tuile qui a comme nom une valeur de l'enum dans l'ordre
                indexEnum++; // On incrémente l'index de l'enum
                tuiles[i%6][j] = tuile; // On l'ajoute
            }
            
            if(i%6==0 && i != 0){ // A chaque fois qu'on passe à une nouvelle ligne (c'est à dire que i modulo 6 = 0)
                j++; // On incrémente le numéro de colonne de 1
            }  
            
        }*/
        
        for (int i=0; i<6; i++){
            for (int j=0; j<6; j++){
               /* if (i ==0 && j || i == 1 || i ==4 || i ==5 || i == 6 || i ==11 || i ==24 || i ==29 || i ==30 || i == 31 || i ==34 || i ==35){
                    case i
                            
                    default:
                        */
                                
                }
            }
        }


    /**
     * @return the tuiles
     */
    public Tuile[][] getTuiles() {
        return tuiles;
    }
}
