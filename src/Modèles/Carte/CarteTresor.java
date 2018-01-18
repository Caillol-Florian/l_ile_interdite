package Modèles.Carte;

import Modèles.Tresor;

public class CarteTresor extends CarteStockable {

    Tresor tresor;
    public CarteTresor(String nom, Tresor tresor){
        super(nom);
        this.tresor = tresor;
    }

    public Tresor getTresor() {
        return tresor;
    }
}
