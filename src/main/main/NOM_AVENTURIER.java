package main.main;

public enum NOM_AVENTURIER {
    EXPLORATEUR("Explorateur"),
    INGENIEUR("Ingénieur"),
    PLONGEUR("Plongeur"),
    MESSAGER("Messager"),
    NAVIGATEUR("Navigateur"),
    PILOTE("Pilote");

    private String name;

    NOM_AVENTURIER(String name){
        this.name = name;
    }

    @Override
    public String toString(){ return name;}
}
