package Enums;

public enum TYPE_TRESOR {
    PIERRE("Pierre"),
    CALICE("Calice"),
    ZEPHYR("Zephyr"),
    CRISTAL("Cristal");

    String nom;

    TYPE_TRESOR(String nom){
        this.nom = nom;
    }

    @Override
    public String toString() {
        return this.nom;
    }
}
