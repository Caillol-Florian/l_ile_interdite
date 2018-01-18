package Enums;

public enum TYPE_TRESOR {
    PIERRE("Pierre", "src/images/tresors/pierre.png"),
    CALICE("Calice", "src/images/tresors/calice.png"),
    ZEPHYR("Zephyr", "src/images/tresors/zephyr.png"),
    CRISTAL("Cristal", "src/images/tresors/cristal.png");

    String nom;
    String path;

    TYPE_TRESOR(String nom, String path){
        this.nom = nom;
        this.path = path;
    }

    @Override
    public String toString() {
        return this.nom;
    }

    public String getPath() {
        return path;
    }
}
