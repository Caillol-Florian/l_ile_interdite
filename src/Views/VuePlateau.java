package Views;

import Enums.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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

    public VuePlateau(ArrayList<String> pseudosJoueurs, ArrayList<Color> couleurs, ArrayList<String>nomRoles, ArrayList<NOM_TUILE> nomsTuiles) {

        window = new JFrame();
        window.setTitle("Ile Interdite");
        window.setSize(1450, 1000);
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

            cAventurier.weightx = 2;
            cAventurier.weighty = 2;

            // Role
            cAventurier.gridy = 0;
            cAventurier.gridx = 0;
            cAventurier.gridheight = 2;
            cAventurier.fill = GridBagConstraints.BOTH;

            CartePanel cartePersonnage = new CartePanel("images/personnages/"+nomRoles.get(i).toLowerCase()+".png");
            System.out.println("images/personnages/"+nomRoles.get(i).toLowerCase()+".png");
            cartePersonnage.setPreferredSize(new Dimension(90,120));
            panelAventurier.add(cartePersonnage, cAventurier);

            // Nom de l'aventurier
            cAventurier.gridy = 0;
            cAventurier.gridx = 1;
            cAventurier.gridheight = 1;
            JPanel panelNom = new JPanel();
            panelNom.setBackground(couleurs.get(i));
            JLabel labelNomAventurier = new JLabel(pseudosJoueurs.get(i),SwingConstants.CENTER);
            labelNomAventurier.setForeground(Color.WHITE);
            panelNom.add(labelNomAventurier);

            panelAventurier.add(panelNom, cAventurier);

            // Cartes
            cAventurier.gridy = 1;
            JPanel panelCarte = new JPanel(new GridBagLayout());
            GridBagConstraints cCarte = new GridBagConstraints();
            //cCarte.gridy = 0;
            cCarte.gridx = 0;
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
        JButton btnBouger = new JButton("Se déplacer");
        btnBouger.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setChanged();
                notifyObservers(Messages.DEPLACER);
                clearChanged();
            }
        });
        panelBoutons.add(btnBouger);

        JButton btnAssecher = new JButton("Se Assécher");
        btnAssecher.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setChanged();
                notifyObservers(Messages.ASSECHER);
                clearChanged();
            }
        });
        panelBoutons.add(btnAssecher);

        panelBoutons.add(new JButton("Autre action"));

        JButton btnFinir = new JButton("Finir Tour");
        btnFinir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setChanged();
                notifyObservers(Messages.FINTOUR);
                clearChanged();
            }
        });

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
        int index = 0;
        int indexTresor = 0;
        for(int i = 0; i < 6; i++){
            for (int j = 0; j < 6; j++){
                // Tuiles vide
                if(((i == 0 || i == 5) && (j == 1 || j == 4)) || ((i == 1 || i == 4) && (j==0 || j == 5))) { // Si les coordonnées i,j correspondent, il s'agit d'une tuile eau
                    this.tableauTuile[i][j] = null;
                    panelPlateau.add(new JLabel(),c);

                } else if((i == 0 || i == 5) && (j==0 || j == 5)){ // Trésors
                    TuilePanel tresor = new TuilePanel(tresorsPath[indexTresor]);
                    this.tableauTuile[i][j] = tresor;
                    panelPlateau.add(tresor, c);
                    tresor.setPreferredSize(size);
                    tresor.setOpaque(false);
                    indexTresor++;
                }
                else{ // Tuiles
                    System.out.println("i : " + i + " - j : " + j);
                    TuilePanel tuile = new TuilePanel(nomsTuiles.get(index), ETAT_TUILE.SECHE, null);
                    tuile.addMouseListener(new MouseListener() {
                        @Override
                        public void mouseClicked(MouseEvent e) {}

                        @Override
                        public void mousePressed(MouseEvent e) {}

                        @Override
                        public void mouseReleased(MouseEvent e) {
                            setChanged();
                            Message_Coche m = new Message_Coche(tuile.getNomTuile());
                            notifyObservers(m);
                            clearChanged();
                        }

                        @Override
                        public void mouseEntered(MouseEvent e) {}

                        @Override
                        public void mouseExited(MouseEvent e) {}
                    });

                    panelPlateau.add(tuile, c);
                    this.tableauTuile[i][j] = tuile;
                    index++;
                    tuile.setOpaque(false);
                    tuile.setPreferredSize(size);
                }
                c.gridx++;
            }
            c.gridx = 0;
            c.gridy++;
        }

        ArrayList<PION>pions = new ArrayList<>();
        pions.add(PION.BLEU);
        tableauTuile[1][1].update(ETAT_TUILE.SECHE, pions);
        pions.clear();
        tableauTuile[1][1].update(ETAT_TUILE.SECHE, pions);

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

    public void highlightTuiles (boolean hightlightOn, ArrayList<Integer> coordonnesTuiles){
        int i = 0;

        while (i < coordonnesTuiles.size()-1){
            System.out.print(coordonnesTuiles.get(i));System.out.println(coordonnesTuiles.get(i+1) + " -");
            getTableauTuile()[coordonnesTuiles.get(i)][coordonnesTuiles.get(i+1)].highlight(hightlightOn);
            i += 2;
        }
    }

    public void highlightOff(){
        for(int i = 0; i < getTableauTuile().length; i++){
            for(int j = 0; j < getTableauTuile().length; j++){
                if((!((i == 0 || i == 5) && (j == 1 || j == 4 || j == 5) || j == 0) || ((i == 1 || i == 4) && (j==0 || j == 5))) && getTableauTuile()[i][j] != null) { // Si les coordonnées i,j correspondent, il s'agit d'une tuile eau
                    getTableauTuile()[i][j].highlight(false);
                }
            }
        }
    }
    @Override
    public void setVisible(Boolean b) {
        window.setVisible(b);
    }

}
