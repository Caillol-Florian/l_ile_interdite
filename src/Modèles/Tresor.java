package Modèles;

import Enums.TYPE_TRESOR;

public class Tresor {
    private TYPE_TRESOR nom;

    public Tresor(TYPE_TRESOR nom){
        this.nom = nom;
    }

    public TYPE_TRESOR getNom() {
        return nom;
    }
}
