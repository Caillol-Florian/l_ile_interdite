package Views;

import Enums.*;
import Modèles.Parameters;
import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.util.ArrayList;
import java.awt.*;

import java.util.HashMap;

import static Modèles.Parameters.AFFICHERTRESOR;

/* Récap des imbrications des gridBagLayout, pour éviter les maux de têtes ;)

mainPanel (c) > panelAventuriers (cColonneAventurier) > panelMusique (cMusique) | panelTresor (cTresor) | panelAventurier (cAventurier) ** | panelBouton (cBouton)
              > panelGrille
              > panelInfo

** panelAventurier > cartePersonnage | panelNom | panelCarte

nb : tous les layouts sont indiqués dans le code

*/

public class VuePlateau extends Vue {
    private final JFrame window;
    private final JPanel mainPanel;

    private final ArrayList<JPanel>arrayPanelsAventurier = new ArrayList<>(); //Array contenant chaque panel de chaque aventurier
    private ArrayList<Color> couleurs;
    private ArrayList<JPanel> panelsNom = new ArrayList<>();
    private Color grey = new Color(122,122,122);

    private final TuilePanel[][] tableauTuile; // Grille des tuiles
    private final ArrayList<ArrayList<CartePanel>>cartesAventurier = new ArrayList<>(); // Array contenant l'Array des cartes de chaque aventurier.
    private String[] tresorsPath = {"images/tresors/calice.png", "images/tresors/pierre.png", "images/tresors/cristal.png", "images/tresors/zephyr.png"}; // Tableau path tresor
    private Integer niveau = 1; // Niveau de difficulté

    private ArrayList<CartePanel>tresors;

    // Parametre vueNiveau
    HashMap<Integer, JPanel> panelsGauches = new HashMap<>();
    Integer cellWidth = 50;
    Integer cellHeight = (Parameters.HAUTEUR_AUTRES_VUES - 25 - (Parameters.UNDECORATED ? 0 : Parameters.DECORATION_HEIGHT)) / 10;

    private JButton btnBouger, btnAssecher, btnRecuperer, btnDon, btnSpecial, btnFinir;


    public VuePlateau(ArrayList<String> pseudosJoueurs, ArrayList<Color> couleurs, ArrayList<String>nomRoles, ArrayList<NOM_TUILE> nomsTuiles, int niveauDifficulte) {

        this.couleurs = couleurs;

        window = new JFrame();
        window.setTitle("Ile Interdite");
        window.setSize(1920, 1080);
        window.setLocation(0, 0);
        window.setResizable(false);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //  MainPanel
        mainPanel = new ImagePanel(1920,1080,"images/backgrounds/bg_plateau.png");
        mainPanel.setLayout(new GridBagLayout());
        window.add(mainPanel);

        // Panel Aventuriers
        ImagePanel panelAventuriers =new ImagePanel(1920,1080,"images/backgrounds/bg_plateau.png");
        panelAventuriers.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.anchor = GridBagConstraints.PAGE_START;
        c.fill = GridBagConstraints.BOTH;

        c.weightx = 3;
        c.weighty = 1;

        c.gridx = 0;
        c.gridy = 0;

        mainPanel.add(panelAventuriers, c);

        //  PanelMusique
        GridBagConstraints cColonneAventurier = new GridBagConstraints();
        cColonneAventurier.gridy = 0;
        cColonneAventurier.gridx = 0;
        cColonneAventurier.fill = GridBagConstraints.BOTH;

        JPanel panelMusique = new JPanel(new GridBagLayout());
        panelMusique.setBorder(new MatteBorder(2,2,2,2, Color.BLACK));

        panelAventuriers.add(panelMusique,cColonneAventurier);
        GridBagConstraints cMusique = new GridBagConstraints();
        cMusique.weightx = 2;
        cMusique.weighty = 1;
        cMusique.gridx = 0;
        cMusique.gridy = 0;
        cMusique.anchor = GridBagConstraints.CENTER;
        cMusique.fill = GridBagConstraints.BOTH;
        cMusique.gridwidth = 2;

        JPanel textMusiquePanel = new JPanel();
        textMusiquePanel.setBackground(Color.BLACK);
        JLabel musiqueLabel = new JLabel("Lecteur de musique");
        musiqueLabel.setForeground(Color.WHITE);
        textMusiquePanel.add(musiqueLabel);
        panelMusique.add(textMusiquePanel, cMusique);

        JButton Lecture = btnIcone("playButton.png","Lecture");

        Lecture.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setChanged();
                notifyObservers(Messages.PLAY);
                clearChanged();
            }
        });

        JButton Pause = btnIcone("pause.png","Pause");

        Pause.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setChanged();
                notifyObservers(Messages.PAUSE);
                clearChanged();
            }
        });

        cMusique.gridy = 1;
        cMusique.gridwidth = 1;
        cMusique.fill = GridBagConstraints.NONE;
        cMusique.anchor = GridBagConstraints.FIRST_LINE_END;
        cMusique.insets = new Insets(0,0,0,8);

        panelMusique.add(Lecture,cMusique);

        cMusique.anchor = GridBagConstraints.FIRST_LINE_START;
        cMusique.gridx++;
        cMusique.insets = new Insets(0,8,0,0);
        panelMusique.add(Pause,cMusique);

        cColonneAventurier.gridy++;

        //  PanelTrésor

        cColonneAventurier.insets = new Insets(25,0,25,0);

        JPanel panelTresor = new JPanel(new GridBagLayout());
        panelAventuriers.add(panelTresor,cColonneAventurier);
        GridBagConstraints cTresor = new GridBagConstraints();

        cTresor.weightx = 4;
        cTresor.weighty = 2;
        cTresor.gridx = 0;
        cTresor.gridy = 0;
        cTresor.fill = GridBagConstraints.BOTH;
        cTresor.anchor = GridBagConstraints.CENTER;

        cTresor.gridwidth = 4;

        panelTresor.setBorder(new MatteBorder(2,2,2,2, Color.BLACK));

        JPanel textTresorPanel = new JPanel();
        textTresorPanel.setBackground(Color.BLACK);
        JLabel tresorLabel = new JLabel("Trésor(s) récupéré(s)");
        tresorLabel.setForeground(Color.WHITE);
        textTresorPanel.add(tresorLabel);
        panelTresor.add(textTresorPanel, cTresor);

        cTresor.gridy = 1;
        cTresor.gridwidth = 1;

        tresors = new ArrayList<>();
        for (int i =0; i<4; i++){
            CartePanel tresor = new CartePanel("images/tresors/blank.png",90,90);
            tresor.setPreferredSize(new Dimension(90,90));
            getTresors().add(tresor);
            panelTresor.add(tresor,cTresor);
            cTresor.gridx++;
        }
        cColonneAventurier.gridy++;

        //  PanelAventurier

        Dimension sizeCarte = new Dimension(60,  84);

        for(int i = 0; i < pseudosJoueurs.size(); i++){
            JPanel panelAventurier = new JPanel(new GridBagLayout());
            panelAventurier.setBorder(new MatteBorder(2,2,2,2, grey));

            GridBagConstraints cAventurier = new GridBagConstraints();
            cAventurier.gridy = 0; cAventurier.gridx = 0;
            cAventurier.fill = GridBagConstraints.HORIZONTAL;

            cAventurier.weightx = 2;
            cAventurier.weighty = 2;

            // CartePersonnage
            cAventurier.gridy = 0;
            cAventurier.gridx = 0;
            cAventurier.gridheight = 2;
            cAventurier.fill = GridBagConstraints.BOTH;

            CartePanel cartePersonnage = new CartePanel("images/personnages/"+nomRoles.get(i).toLowerCase()+".png",90,120);
            cartePersonnage.setPreferredSize(new Dimension(90,120));
            panelAventurier.add(cartePersonnage, cAventurier);

            //  PanelNom
            cAventurier.gridy = 0;
            cAventurier.gridx = 1;
            cAventurier.gridheight = 1;
            JPanel panelNom = new JPanel();
            panelNom.setBackground(grey);
            panelsNom.add(panelNom);
            JLabel labelNomAventurier = new JLabel(pseudosJoueurs.get(i),SwingConstants.CENTER);
            labelNomAventurier.setForeground(Color.WHITE);
            panelNom.add(labelNomAventurier);

            panelAventurier.add(panelNom, cAventurier);

            //  PanelCarte
            cAventurier.gridy = 1;
            JPanel panelCarte = new JPanel(new GridBagLayout());
            GridBagConstraints cCarte = new GridBagConstraints();
            cCarte.gridx = 0;
            cCarte.gridy = 1;
            cCarte.insets = new Insets(3,3,3,3);

            ArrayList<CartePanel>cartes = new ArrayList<>();
            for(int j = 0; j < 5; j++){
                CartePanel carte = new CartePanel("images/cartes/Fond rouge.png",60,84);
                carte.setPreferredSize(sizeCarte);
                panelCarte.add(carte, cCarte);
                cCarte.gridx++;

                cartes.add(carte);
            }

            cartesAventurier.add(cartes);
            panelAventurier.add(panelCarte, cAventurier);

            cColonneAventurier.insets = new Insets(5,0,0,0);
            panelAventuriers.add(panelAventurier, cColonneAventurier);
            arrayPanelsAventurier.add(panelAventurier);
            cColonneAventurier.gridy++;
        }


        //  PanelBouton
        ImagePanel panelBoutons = new ImagePanel(1920,1080,"images/backgrounds/bg_plateau.png");
        panelBoutons.setLayout(new GridBagLayout());
        GridBagConstraints cBouton = new GridBagConstraints();
        cBouton.weightx = 3;
        cBouton.weighty = 2;
        cBouton.gridx = 0;
        cBouton.gridy = 0;
        cBouton.anchor = GridBagConstraints.CENTER;
        cBouton.insets = new Insets(10,0,10,0);

        cColonneAventurier.insets = new Insets(55,0,35,0);
        panelAventuriers.add(panelBoutons, cColonneAventurier);

        Dimension iconSize = new Dimension(50,50);


        btnBouger = btnIcone("move.png","Se déplacer");
        panelBoutons.add(btnBouger,cBouton);

        btnBouger.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setChanged();
                notifyObservers(Messages.DEPLACER);
                clearChanged();
            }
        });

        cBouton.gridx++;

        btnAssecher = btnIcone("drought.png","Assécher");
        panelBoutons.add(btnAssecher,cBouton);

        btnAssecher.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setChanged();
                notifyObservers(Messages.ASSECHER);
                clearChanged();
            }
        });

        cBouton.gridx++;

        btnRecuperer = btnIcone("chest.png","Récupérer trésor");
        panelBoutons.add(btnRecuperer,cBouton);

        btnRecuperer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setChanged();
                notifyObservers(Messages.RECUPTRESOR);
                clearChanged();
            }
        });

        cBouton.gridx++;

        btnDon  = btnIcone("give-card.png","Donner une carte");
        panelBoutons.add(btnDon, cBouton);

        btnDon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setChanged();
                notifyObservers(Messages.DONCARTE);
                clearChanged();
            }
        });

        cBouton.gridx++;

        btnSpecial = btnIcone("star.png","Action spéciale");
        panelBoutons.add(btnSpecial,cBouton);

        btnSpecial.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setChanged();
                notifyObservers(Messages.ACTIONSPECIALE);
                clearChanged();
            }
        });

        cBouton.gridx++;

        btnFinir = btnIcone("next.png","Finir Tour");
        panelBoutons.add(btnFinir, cBouton);

        btnFinir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setChanged();
                notifyObservers(Messages.FINTOUR);
                clearChanged();
            }
        });


        // Panel Grille

        JPanel panelPlateau = new ImagePanel(1920,1920,"images/backgrounds/bg_plateau.png");
        panelPlateau.setLayout(new GridBagLayout());
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.CENTER;
        c.gridx = 1;
        c.gridy = 0;

        mainPanel.add(panelPlateau, c);

        GridBagConstraints cGrille = new GridBagConstraints();
        cGrille.fill = GridBagConstraints.BOTH;
        cGrille.weightx = 1;
        cGrille.gridx = 0;
        cGrille.gridy = 0;
        cGrille.insets = new Insets(5,5,5,5);

        //  Construction grille
        this.tableauTuile = new TuilePanel[6][6];
        Dimension size = new Dimension(150,150);
        int index = 0;
        int indexTresor = 0;
        for(int i = 0; i < 6; i++){
            for (int j = 0; j < 6; j++){
                // Tuiles vide
                if(((i == 0 || i == 5) && (j == 1 || j == 4)) || ((i == 1 || i == 4) && (j==0 || j == 5))) { // Si les coordonnées i,j correspondent, il s'agit d'une tuile eau
                    this.tableauTuile[i][j] = null;
                    panelPlateau.add(new JLabel(),cGrille);

                } else if((i == 0 || i == 5) && (j==0 || j == 5)){ // Si on décide d'afficher les trésors
                    if (AFFICHERTRESOR) {
                        TuilePanel tresor = new TuilePanel(tresorsPath[indexTresor]);
                        this.tableauTuile[i][j] = tresor;
                        panelPlateau.add(tresor, cGrille);
                        tresor.setPreferredSize(size);
                        tresor.setOpaque(false);
                        indexTresor++;
                    } else {
                        panelPlateau.add(new JLabel(), cGrille);
                    }

                }
                else{ // Tuiles
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

                    panelPlateau.add(tuile, cGrille);
                    this.tableauTuile[i][j] = tuile;
                    index++;
                    tuile.setOpaque(false);
                    tuile.setPreferredSize(size);
                }
                cGrille.gridx++;
            }
            cGrille.gridx = 0;
            cGrille.gridy++;
        }

        // PanelInfo (pioches et niveau d'eau)

        c.gridx = 2;
        c.gridy = 0;
        c.anchor = GridBagConstraints.PAGE_END;
        c.fill = GridBagConstraints.BOTH;


        JPanel panelInfo = new ImagePanel(1920,1920,"images/backgrounds/bg_plateau.png");
        panelInfo.setLayout(new GridBagLayout());
        mainPanel.add(panelInfo,c);

        GridBagConstraints cInfo = new GridBagConstraints();
        cInfo.weightx = 2;
        cInfo.weighty = 3;
        cInfo.anchor = GridBagConstraints.CENTER;

        //  Pioche / défausse carte trésor
        Dimension sizePioche = new Dimension(120,150);
        cInfo.gridx = 0;
        cInfo.gridy = 0;
        CartePanel tresorDos = new CartePanel("images/cartes/Fond rouge.png", 120, 150);
        tresorDos.setPreferredSize(sizePioche);
        panelInfo.add(tresorDos, cInfo);

        cInfo.gridx = 1;
        CartePanel tresorFace = new CartePanel("images/cartes/Calice.png",120, 150);
        tresorFace.setPreferredSize(sizePioche);
        panelInfo.add(tresorFace, cInfo);

        // PanelNiveau
        JPanel niveau = new JPanel();
        niveau.setLayout(new BorderLayout());
        niveau.setBackground(Color.WHITE);
        niveau.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, false));

        JLabel labelTitre = new JLabel("Niveau", JLabel.CENTER);
        niveau.add(labelTitre, BorderLayout.NORTH);
        labelTitre.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 14));

        JPanel panelNiveaux = new JPanel(new GridBagLayout());
        panelNiveaux.setOpaque(false);
        niveau.add(panelNiveaux, BorderLayout.CENTER);

        GridBagConstraints cNiveau = new GridBagConstraints();
        cNiveau.weightx = 2;
        cNiveau.weighty = 10;
        cNiveau.insets = new Insets(0, 0, 0, 0);
        cNiveau.fill = GridBagConstraints.VERTICAL;

        for (int i = 0; i < 10; i++) {
            cNiveau.gridx = 0;
            cNiveau.gridy = i;
            JPanel panelGauche = new JPanel();
            panelGauche.setLayout(new BoxLayout(panelGauche, BoxLayout.Y_AXIS));
            panelGauche.setBackground(getBgColor(10 - i));
            panelGauche.setPreferredSize(new Dimension(cellWidth, cellHeight));
            if (i < 9) {
                panelGauche.setBorder(new MatteBorder(0, 0, 1, 0, Color.WHITE));
            } else {
                panelGauche.setBorder(new MatteBorder(1, 0, 0, 0, Color.WHITE));
            }

            panelNiveaux.add(panelGauche, cNiveau);

            JLabel labelGauche = new JLabel("", JLabel.LEFT);
            labelGauche.setPreferredSize(new Dimension(cellWidth, cellHeight));
            labelGauche.setForeground(i == 0 ? new Color(223, 168, 169) : Color.BLACK);
            labelGauche.setFont(new Font(labelGauche.getFont().getFamily(), labelGauche.getFont().getStyle(), 8));
            labelGauche.setText(getLibelle(10 - i));
            panelGauche.add(labelGauche);
            panelsGauches.put((10 - i), panelGauche);
        }

        for (int iPanel = 0; iPanel < 4; iPanel++) {
            cNiveau.gridx = 1;
            cNiveau.gridy = (iPanel == 0 ? 0 : (iPanel == 1 ? 3 : (iPanel == 2 ? 5 : 8)));
            cNiveau.gridheight = (iPanel == 0 || iPanel == 2 ? 3 : 2);
            JPanel panelDroit = new JPanel();
            panelDroit.setPreferredSize(new Dimension(cellWidth, cellHeight));
            panelDroit.setLayout(new GridBagLayout());
            panelNiveaux.add(panelDroit, cNiveau);

            JLabel labelDroit;
            switch (iPanel) {
                case 0:
                    panelDroit.setBackground(getBgColor(10));
                    labelDroit = new JLabel("5", JLabel.CENTER);
                    break;
                case 1:
                    panelDroit.setBackground(getBgColor(7));
                    labelDroit = new JLabel("4", JLabel.CENTER);
                    break;
                case 2:
                    panelDroit.setBackground(getBgColor(5));
                    labelDroit = new JLabel("3", JLabel.CENTER);
                    break;
                default:
                    panelDroit.setBackground(getBgColor(1));
                    labelDroit = new JLabel("2", JLabel.CENTER);
                    break;
            }
            labelDroit.setPreferredSize(new Dimension(cellWidth, cellHeight));
            labelDroit.setForeground(Color.WHITE);
            labelDroit.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 40));
            GridBagConstraints gbc = new GridBagConstraints();
            panelDroit.add(labelDroit, gbc);
        }

        panelsGauches.get(getNiveau()).setBackground(Color.YELLOW);

        cInfo.gridx = 0;
        cInfo.gridy = 1;
        cInfo.gridwidth = 2;

        panelInfo.add(niveau,cInfo);

        // Pioche / défausse carte inondation

        cInfo.gridwidth = 1;
        cInfo.gridx = 0;
        cInfo.gridy = 2;


        CartePanel inondationDos = new CartePanel("images/cartes/Fond bleu.png",120, 150);
        inondationDos.setPreferredSize(sizePioche);
        panelInfo.add(inondationDos, cInfo);

        cInfo.gridx = 1;
        CartePanel inondationFace = new CartePanel("images/cartes/LeValDuCrecupuscule.png",120, 150);
        inondationFace.setPreferredSize(sizePioche);
        panelInfo.add(inondationFace, cInfo);

        setNiveau(niveauDifficulte);

        window.setVisible(true);
    }

    public JButton btnIcone(String path, String texte){
        String imgUrl="images/icones/"+path;
        ImageIcon icone = new ImageIcon(imgUrl);
        JButton btn = new JButton(icone);
        btn.setPreferredSize(new Dimension(60,60));
        btn.setToolTipText(texte);
        btn.setOpaque(false);
        return btn;
    }

    public void highlightAventurier(int joueurActif, int ancienJoueur){
        panelsNom.get(ancienJoueur).setBackground(grey);
        panelsNom.get(joueurActif).setBackground(couleurs.get(joueurActif));
        arrayPanelsAventurier.get(ancienJoueur).setBorder(new MatteBorder(2,2,2,2, grey));
        arrayPanelsAventurier.get(joueurActif).setBorder(new MatteBorder(2,2,2,2, couleurs.get(joueurActif)));
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
            getTableauTuile()[coordonnesTuiles.get(i)][coordonnesTuiles.get(i+1)].highlight(hightlightOn);
            i += 2;
        }
    }

    public JButton getBtnAssecher() {
        return btnAssecher;
    }

    public JButton getBtnDonCarte() {
        return btnDon;
    }

    public JButton getBtnFinir() {
        return btnFinir;
    }

    public JButton getBtnBouger() {
        return btnBouger;
    }

    public void setNiveau(Integer niveau) {
        panelsGauches.get(this.niveau).setBackground(getBgColor(this.niveau - 1));
        this.niveau = niveau;
        panelsGauches.get(this.niveau).setBackground(this.niveau == 10 ? Color.RED : Color.YELLOW);
        this.mainPanel.repaint();
    }

    public Integer getNiveau() {
        return this.niveau;
    }

    public Integer getColoredNiveau() {
        for (Integer coloredNiveau : panelsGauches.keySet()) {
            if (panelsGauches.get(coloredNiveau).getBackground() == Color.YELLOW) {
                return coloredNiveau;
            }
        }
        return -1;
    }

    private Color getBgColor(Integer niveau) {
        if (niveau <= 2)
            return new Color(169, 215, 226);

        if (niveau <= 5)
            return new Color(129, 194, 212);


        if (niveau <= 7)
            return new Color(67, 119, 204);

        return new Color(42, 76, 127);
    }

    private String getLibelle(int i) {
        switch (i) {
            case 1:
                return " novice";
            case 2:
                return " normal";
            case 3:
                return " élite";
            case 4:
                return " légendaire";
            case 10:
                return " mortel";
            default:
                return "";
        }
    }

    @Override
    public void setVisible(Boolean b) {
        window.setVisible(b);
    }

    public ArrayList<CartePanel> getTresors() {
        return tresors;
    }
}
