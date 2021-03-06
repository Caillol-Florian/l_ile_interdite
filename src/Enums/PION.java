package Enums;

import java.awt.*;

public enum PION {

    ROUGE("Ingénieur", new Color(255, 0, 0), "src/images/pions/pionRouge"),
    BLEU ("Pilote", new Color(55, 194, 198), "src/images/pions/pionBleu"),
    VERT ("Explorateur", new Color(0, 195, 0), "src/images/pions/pionVert"),
    VIOLET ("Messager", new Color(204, 94, 255), "src/images/pions/pionViolet"),
    JAUNE("Navigateur", new Color(255, 255, 0), "src/images/pions/pionJaune"),
    NOIR("Plongeur", new Color(50, 50, 50), "src/images/pions/pionNoir");

    private final String name ;
    private final Color couleur;
    private final String path ;


    PION(String name, Color couleur, String path) {
        this.name = name;
        this.couleur = couleur;
        this.path = path;
    }

    @Override
    public String toString() {
        return this.name ;
    }

    public Color getCouleur() {
        return this.couleur ;
    }

    public String getPath(){
        return path + ".png";
    }
}
