package Views;

import Enums.ETAT_TUILE;
import Enums.NOM_TUILE;
import Enums.PION;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.awt.*;
import java.io.File;
import java.io.IOException;

import static javax.swing.SwingConstants.CENTER;

public class VuePlateau extends Vue {
    private final JFrame window;
    private final JPanel mainPanel;
    // Array contenant chaque panel de chaque aventurier
    private final ArrayList<JPanel>arrayPanelsAventurier = new ArrayList<>();
    // Grille des tuiles
    private final TuilePanel[][] tableauTuile;
    // Array contenant l'Array des cartes de chaque aventurier.
    private final ArrayList<ArrayList<CartePanel>>cartesAventurier = new ArrayList<>();

    public VuePlateau(ArrayList<String> pseudosJoueurs, ArrayList<Color> couleurs, ArrayList<String>nomRoles) {

        window = new JFrame();
        window.setTitle("Ile Interdite");
        window.setSize(1350, 1000);
        window.setLocation(0, 0);
        window.setResizable(false);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // =============================================================
        // Création du panel principal
        mainPanel = new PanelAvecImage(1500,1500,"images/backgrounds/bg_plateau.png");
        mainPanel.setLayout(new GridBagLayout());
        window.add(mainPanel);

        // =============================================================
        // Panel Aventuriers
        PanelAvecImage panelAventuriers =new PanelAvecImage(1500,1500,"images/backgrounds/bg_plateau.png");
        panelAventuriers.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.fill = GridBagConstraints.BOTH;

        mainPanel.add(panelAventuriers, c);

        GridBagConstraints cColonneAventurier = new GridBagConstraints();
        cColonneAventurier.gridy = 0;
        cColonneAventurier.fill = GridBagConstraints.BOTH;
        cColonneAventurier.insets = new Insets(10,0,0,0);

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
            JLabel labelNomRole = new JLabel(nomRoles.get(i),SwingConstants.CENTER);
            labelNomRole.setForeground(Color.WHITE);
            panelRole.add(labelNomRole);

            panelAventurier.add(panelRole, cAventurier);

            // Position
            cAventurier.gridy = 2;
            JPanel panelPosition = new JPanel(new GridLayout(2, 1));
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
            cCarte.insets = new Insets(3,3,3,3);

            ArrayList<CartePanel>cartes = new ArrayList<>();
            for(int j = 0; j < 5; j++){
                CartePanel carte = new CartePanel("images/cartes/Fond rouge.png");

                carte.setPreferredSize(sizeCarte);
                panelCarte.add(carte, cCarte);
                cCarte.gridx++;

                cartes.add(carte);
            }
            cartesAventurier.add(cartes);

            //
            panelAventurier.add(panelCarte, cAventurier);

            panelAventuriers.add(panelAventurier, cColonneAventurier);
            arrayPanelsAventurier.add(panelAventurier);
            cColonneAventurier.gridy++;
        }

        JPanel panelBoutons = new JPanel(new GridLayout(2,2));
        cColonneAventurier.anchor = GridBagConstraints.PAGE_END;
        panelAventuriers.add(panelBoutons, cColonneAventurier);
        panelBoutons.add(new JButton("Se déplacer"));
        panelBoutons.add(new JButton("Assécher"));
        panelBoutons.add(new JButton("Autre action"));
        panelBoutons.add(new JButton("Passer son tour"));

        // =============================================================
        // Panel Grille
        JPanel panelPlateau = new PanelAvecImage(1500,1500,"images/backgrounds/bg_plateau.png");
        panelPlateau.setLayout(new GridBagLayout());
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.LINE_END;
        c.gridy = 0;
        c.gridx = 1;
        c.weightx = 6;
        mainPanel.add(panelPlateau, c);
        c.fill = GridBagConstraints.NONE;
        // Contrainte pour le panel grille
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(5,5,5,5);
        // ===========================================
        // Grille
        this.tableauTuile = new TuilePanel[6][6];
        Dimension size = new Dimension(150,150);
        String[] tresorsPath = {"images/tresors/calice.png", "images/tresors/pierre.png", "images/tresors/cristal.png", "images/tresors/zephyr.png"};
        int index = 1;
        int indexTresor = 0;
        for(int i = 0; i < 6; i++){
            for (int j = 0; j < 6; j++){
                if(((i == 0 || i == 5) && (j == 1 || j == 4)) || ((i == 1 || i == 4) && (j==0 || j == 5))) { // Si les coordonnées i,j correspondent, il s'agit d'une tuile eau
                    tableauTuile[i][j] = null;
                    panelPlateau.add(new JLabel(),c);

                } else if((i == 0 || i == 5) && (j==0 || j == 5)){

                    TuilePanel tresor = new TuilePanel(tresorsPath[indexTresor]);
                    tableauTuile[i][j] = tresor;
                    panelPlateau.add(tresor, c);
                    tresor.setPreferredSize(size);
                    tresor.setOpaque(false);
                    indexTresor++;
                }
                else{
                    System.out.println("i : " + i + " - j : " + j);
                    TuilePanel test = new TuilePanel(NOM_TUILE.values()[index], ETAT_TUILE.SECHE, null);
                    panelPlateau.add(test, c);
                    tableauTuile[i][j] = test;
                    index++;
                    test.setOpaque(false);
                    test.setPreferredSize(size);
                }
                c.gridx++;
            }
            c.gridx = 0;
            c.gridy++;
        }

        // ===
        window.setVisible(true);
    }


    private class PanelAvecImage extends JPanel {

        private Image image;
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

    public TuilePanel[][] getTableauTuile() {
        return tableauTuile;
    }

    public ArrayList<ArrayList<CartePanel>> getCartesAventurier() {
        return cartesAventurier;
    }

    public static void main(String[] args){
        ArrayList<String>pseudos = new ArrayList<>();
        ArrayList<Color>couleurs = new ArrayList<>();
        pseudos.add("Jean"); pseudos.add("jacques"); pseudos.add("françois"); pseudos.add("théophile fdp");
        couleurs.add(Color.BLUE);couleurs.add(Color.RED);couleurs.add(Color.ORANGE);couleurs.add(Color.GREEN);
        ArrayList<String>roles = new ArrayList<>();
        roles.add("Explorateur"); roles.add("Ingénieur"); roles.add("ntm"); roles.add("Pilote");
        VuePlateau vue = new VuePlateau(pseudos, couleurs, roles);
    }

    @Override
    public void setVisible(Boolean b) {
        window.setVisible(b);
    }

}
