package Modèles.Aventurier;
import Enums.NOM_AVENTURIER;
import Enums.PION;
import Modèles.Grille;
import Enums.Messages;
import Modèles.Tuile;
import java.util.ArrayList;

public class Plongeur extends Aventurier {

    public Plongeur(Tuile tuile, String nom) {
        super(tuile, nom);
        setNomRole(NOM_AVENTURIER.PLONGEUR);
        setPion(PION.NOIR);
    }

    @Override
    public ArrayList getTuilesAccesibles(Grille g) {

        ArrayList<Integer> tuilesAccessibleAdjacentes = g.getTuilesAdjacentes(getPosition(), Messages.DEPLACER);
        ArrayList<Integer> tuilesAccessiblePlongeur = g.getTuilesPlongeur(getPosition());

        tuilesAccessiblePlongeur.addAll(tuilesAccessibleAdjacentes);

        return tuilesAccessiblePlongeur;
    }

}
