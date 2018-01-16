package Enums;

public enum NIVEAU_DIFFICULTE {
    NOVICE("Novice"),
    NORMAL("Normal"),
    ELITE("Elite"),
    LEGENDAIRE("Légendaire");

    private String niveau;

    NIVEAU_DIFFICULTE(String niveau){
        this.niveau = niveau;
    }

    @Override
    public String toString(){ return niveau;}
}

