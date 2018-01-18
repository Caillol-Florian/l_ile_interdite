package Modèles;

import Enums.TYPE_TRESOR;

public class Tresor {
    private TYPE_TRESOR typeTresor;

    public Tresor(TYPE_TRESOR typeTresor){
        this.typeTresor = typeTresor;
    }

    public TYPE_TRESOR getTypeTresor() {
        return typeTresor;
    }
}
