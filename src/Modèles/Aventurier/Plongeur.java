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
        setPion(PION.GRIS);
    }

    @Override
    public ArrayList getTuilesAccesibles(Grille g) {

        ArrayList<Tuile> tuilesAccessibleDirect = g.getTuilesAdjacentes(getPosition(), Messages.DEPLACER);
        ArrayList<Tuile> tuilesAccessiblePlongeur = g.getTuilesPlongeur(getPosition());

        for (Tuile tuileAccessibleDirect : tuilesAccessibleDirect) {
            if (!tuilesAccessiblePlongeur.contains(tuileAccessibleDirect)){
                tuilesAccessiblePlongeur.add(tuileAccessibleDirect);
            }
        }

        return tuilesAccessiblePlongeur;
    }

}
