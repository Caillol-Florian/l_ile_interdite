package main.main;

public class CarteTresor extends CarteStockable {

    private Tresor tresor;

    public CarteTresor(String nom, Tresor tresor){
        super(nom);
        this.tresor = tresor;
    }
}
