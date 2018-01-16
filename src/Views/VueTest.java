package Views;

import Controleur.Controleur;
import main.main.ETAT_TUILE;
import main.main.Messages;
import main.main.NOM_TUILE;
import main.main.PION;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.lang.reflect.Array;
import java.nio.Buffer;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static javax.swing.SwingConstants.CENTER;

public class VueTest {
    private final JFrame window;
    private final JPanel mainPanel;
    private final ArrayList<JPanel>arrayPanelsAventurier = new ArrayList<>();
    public VueTest(ArrayList<String> pseudosJoueurs, ArrayList<Color> couleurs) {

        window = new JFrame();
        window.setTitle("Ile Interdite");
        window.setSize(1350, 1000);
        window.setLocation(0, 0);
        window.setResizable(false);

        // =============================================================
        // Création du panel principal
        mainPanel = new JPanel(new GridBagLayout());
        window.add(mainPanel);

        // =============================================================
        // Panel Aventuriers
        JPanel panelAventuriers = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.FIRST_LINE_START;

        mainPanel.add(panelAventuriers, c);

        GridBagConstraints cColonneAventurier = new GridBagConstraints();
        cColonneAventurier.gridy = 0;
        cColonneAventurier.fill = GridBagConstraints.BOTH;

        Dimension sizeCarte = new Dimension(60,  84);
        for(int i = 0; i < pseudosJoueurs.size(); i++){
            JPanel panelAventurier = new JPanel(new GridBagLayout());
            panelAventurier.setBorder(new MatteBorder(2,2,2,2, couleurs.get(i)));

            GridBagConstraints cAventurier = new GridBagConstraints();
            cAventurier.gridy = 0; cAventurier.gridx = 0;
            cAventurier.fill = GridBagConstraints.HORIZONTAL;

            // Nom de l'aventurier
            JPanel panelNom = new JPanel();
            panelNom.setBackground(couleurs.get(i));
            JLabel labelNomAventurier = new JLabel(pseudosJoueurs.get(i),SwingConstants.CENTER);
            labelNomAventurier.setForeground(Color.WHITE);
            panelNom.add(labelNomAventurier);

            panelAventurier.add(panelNom, cAventurier);

            // Nom du Role
            cAventurier.gridy = 1;
            JPanel panelRole = new JPanel();
            panelRole.setBackground(couleurs.get(i));
            JLabel labelNomRole = new JLabel("Explorateur",SwingConstants.CENTER);
            labelNomRole.setForeground(Color.WHITE);
            panelRole.add(labelNomRole);

            panelAventurier.add(panelRole, cAventurier);

            // Position
            cAventurier.gridy = 2;
            JPanel panelPosition = new JPanel(new GridLayout(2, 1));
            panelPosition.setOpaque(false);

            panelPosition.add(new JLabel ("Position", SwingConstants.CENTER));
            JTextField position = new  JTextField(30);
            position.setHorizontalAlignment(CENTER);
            position.setEditable(false);
            panelPosition.add(position);

            panelAventurier.add(panelPosition, cAventurier);

            // Cartes
            cAventurier.gridy = 3;
            JPanel panelCarte = new JPanel(new GridBagLayout());
            GridBagConstraints cCarte = new GridBagConstraints();
            cCarte.gridy = 0;
            cCarte.gridx = 0;
            panelCarte.add(new JLabel("Cartes"), cCarte);
            cCarte.gridy = 1;
            CartePanel carte = new CartePanel("images/cartes/Calice.png");
            carte.setPreferredSize(sizeCarte);
            panelCarte.add(carte, cCarte);
            panelAventurier.add(panelCarte, cAventurier);

            panelAventuriers.add(panelAventurier, cColonneAventurier);
            arrayPanelsAventurier.add(panelAventurier);
            cColonneAventurier.gridy++;
        }

        // =============================================================
        // Panel Grille
        JPanel panelPlateau = new PanelAvecImage(1500,1500,"images/backgrounds/bg_plateau.png");
        panelPlateau.setLayout(new GridBagLayout());
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.LINE_END;
        c.gridy = 0;
        c.gridx = 1;
        c.weightx = 6;
        mainPanel.add(panelPlateau, c);

        // Contrainte pour le panel grille
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(5,5,5,5);
        // ===========================================
        // Grille
        TuilePanel[][] tableauTuile = new TuilePanel[6][6];
        Dimension size = new Dimension(150,150);
        int index = 1;
        for(int i = 0; i < 6; i++){
            for (int j = 0; j < 6; j++){
                if(((i == 0 || i == 5) && (j == 0 || j == 1 || j == 4 || j == 5)) || ((i == 1 || i == 4) && (j==0 || j == 5))) { // Si les coordonnées i,j correspondent, il s'agit d'une tuile eau
                    tableauTuile[i][j] = null;
                    panelPlateau.add(new JLabel(),c);
                } else {
                    System.out.println("i : " + i + " - j : " + j);
                    TuilePanel test = new TuilePanel(NOM_TUILE.values()[index], ETAT_TUILE.SECHE, null);
                    panelPlateau.add(test, c);
                    tableauTuile[i][j] = test;
                    index++;
                    test.setPreferredSize(size);
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
        tableauTuile[4][4].update(ETAT_TUILE.INONDEE, pions);

        pions.clear();
        pions.add(PION.VIOLET);
        tableauTuile[4][3].update(ETAT_TUILE.INONDEE, pions);

        // ===
        window.setVisible(true);
    }


    private class PanelAvecImage extends JPanel {

        private Image image ;
        private final Integer width ;
        private final Integer height ;

        public PanelAvecImage(Integer width, Integer height, String imageFile) {
            this.width = width ;
            this.height = height ;
            try {
                this.image = ImageIO.read(new File(imageFile));
            } catch (IOException ex) {
                System.err.println("Erreur de lecture background");
            }
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (image != null) {
                g.drawImage(image, 0, 0, this.width, this.height, null, this);
            }
        }
    }


    public static void main(String[] args){
        ArrayList<String>pseudos = new ArrayList<>();
        ArrayList<Color>couleurs = new ArrayList<>();
        pseudos.add("Jean"); pseudos.add("jacques"); pseudos.add("françois"); pseudos.add("théophile fdp");
        couleurs.add(Color.BLUE);couleurs.add(Color.RED);couleurs.add(Color.ORANGE);couleurs.add(Color.GREEN);
        VueTest vue = new VueTest(pseudos, couleurs);
    }

}
