package Views;

import Controleur.Controleur;
import main.main.ETAT_TUILE;
import main.main.Messages;
import main.main.NOM_TUILE;
import main.main.PION;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.lang.reflect.Array;
import java.nio.Buffer;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class VueTest {
    private final JFrame window;
    private final JPanel mainPanel;

    public VueTest() {

        window = new JFrame();
        window.setTitle("Assèchement");
        window.setSize(1000, 1000);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        window.setLocation(0, 0);
        window.setResizable(false);


        // =================================================================================
        // Création du panel principal
        mainPanel = new JPanel(new GridBagLayout());
        window.add(mainPanel);
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;

        // =============
        // Test d'une carte

        TuilePanel[][] tableauTuile = new TuilePanel[6][6];
        Dimension size = new Dimension(150,150);
        int index = 1;
        for(int i = 0; i < 6; i++){
            for (int j = 0; j < 6; j++){
                if(((i == 0 || i == 5) && (j == 0 || j == 1 || j == 4 || j == 5)) || ((i == 1 || i == 4) && (j==0 || j == 5))) { // Si les coordonnées i,j correspondent, il s'agit d'une tuile eau
                    tableauTuile[i][j] = null;
                    mainPanel.add(new JLabel(),c);
                } else {
                    System.out.println("i : " + i + " - j : " + j);
                    TuilePanel test = new TuilePanel(NOM_TUILE.values()[index], ETAT_TUILE.SECHE, null);
                    mainPanel.add(test, c);
                    tableauTuile[i][j] = test;
                    index++;
                    test.setPreferredSize(size);
                    test.setSize(size);
                    test.setMinimumSize(size);
                }
                c.gridx++;
            }
            c.gridx = 0;
            c.gridy++;
        }

        ArrayList<PION>pions = new ArrayList<>();
        pions.add(PION.BLEU);
        pions.add(PION.ROUGE);

        tableauTuile[4][3].update(ETAT_TUILE.SECHE, pions);
        tableauTuile[4][3].update(ETAT_TUILE.INONDEE, pions);

        pions.clear();
        pions.add(PION.VIOLET);
        tableauTuile[4][3].update(ETAT_TUILE.INONDEE, pions);

        // ===
        window.setVisible(true);
    }



    public static void main(String[] args){
        VueTest vue = new VueTest();
    }

}
