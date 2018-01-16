package Views;

import main.main.Messages;
import main.main.NIVEAU_DIFFICULTE;
import sun.plugin2.message.Message;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class VueInscription extends Vue {


    private Window window = new JFrame("Inscription");
    private PanelAvecImage mainPanel;
    private Font regular = new Font("Gill Sans",0,25);
    private Font bold = new Font("Gill Sans", 1, 50);
    private boolean btn2P = false;
    private boolean btn3P = false;
    private boolean btn4P = false;
    private JButton jButton2;
    private JButton jButton3;
    private JButton jButton4;
    private ArrayList<String> pseudos = new ArrayList<>();
    private ArrayList<JTextField> JTextFields = new ArrayList<>();

    private boolean btnD1 = false;
    private boolean btnD2 = false;
    private boolean btnD3 = false;
    private boolean btnD4 = false;

    public VueInscription(){

        window.setSize(1920, 1280);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        window.setLocation(dim.width / 2 - window.getSize().width / 2, dim.height / 2 - window.getSize().height / 2);
        //window.setResizable(false);
        //window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        mainPanel = new PanelAvecImage(1400,1000, "images/backgrounds/bg_options.jpeg") ;
        mainPanel.setLayout(new GridBagLayout());
        window.add(mainPanel);

        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.NONE;

        //définition grille

        gc.weightx = 6;
        gc.weighty = 12;

        //remplissage

        for (int i=0; i<12; i++){
            gc.gridx = 0;
            gc.gridy = i;
            mainPanel.add(new JLabel(), gc);

            gc.gridx = 5;
            gc.gridy = i;
            mainPanel.add(new JLabel(), gc);
        }

        gc.gridx = 3;
        gc.gridy = 4;
        mainPanel.add(new JLabel(), gc);

        //titre panel

        gc.gridx = 1;
        gc.gridy = 0;
        gc.gridwidth = 4;
        gc.gridheight= 2;
        gc.anchor = GridBagConstraints.CENTER;

        String imgUrl="images/icones/iconSettings.png";
        ImageIcon icone0 = new ImageIcon(imgUrl);

        JLabel titreLabel = new JLabel(icone0);
        titreLabel.setText("   Paramètres de la partie");
        titreLabel.setFont(bold);
        mainPanel.add(titreLabel,gc);

        //label nombre de joueur

        gc.gridx = 1;
        gc.gridy = 2;
        gc.gridwidth = 4;
        gc.gridheight = 1;

        JLabel titre = new JLabel("Veuillez séléctionner le nombre de joueur");
        titre.setFont(regular);
        mainPanel.add(titre,gc);

        //choix nombre de joueur

        gc.gridx = 1;
        gc.gridy = 3;
        gc.gridwidth = 1;
        gc.gridheight = 1;

        //URL de l'image
        String imgUrl1="images/icones/icon2PN.png";
        ImageIcon icone1 = new ImageIcon(imgUrl1);
        //création des boutons - ajout icones
        jButton2 = new JButton(icone1);
        jButton2.setPreferredSize(new Dimension(200,60));
        //ajouter les deux JLabel a JFrame
        mainPanel.add(jButton2,gc);

        jButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setChanged();
                btn2P = true; btn3P = false; btn4P = false;
                update();
                clearChanged();
            }
        });

        gc.gridx = 2;
        gc.gridy = 3;

        String imgUrl2="images/icones/icon3PN.png";
        ImageIcon icone2 = new ImageIcon(imgUrl2);
        jButton3 = new JButton(icone2);
        jButton3.setPreferredSize(new Dimension(200,60));
        mainPanel.add(jButton3,gc);

        jButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setChanged();
                btn2P = false; btn3P = true; btn4P = false;
                update();
                clearChanged();
            }
        });


        gc.gridx = 3;
        gc.gridy = 3;

        String imgUrl3="images/icones/icon4PN.png";
        ImageIcon icone3 = new ImageIcon(imgUrl3);
        jButton4 = new JButton(icone3);
        jButton4.setPreferredSize(new Dimension(200,60));
        mainPanel.add(jButton4,gc);

        jButton4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setChanged();
                btn2P = false; btn3P = false; btn4P = true;
                update();
                clearChanged();
            }
        });

        mainPanel.validate();

        //label psuedo joueur

        gc.gridx = 1;
        gc.gridy = 4;
        gc.gridwidth = 4;
        gc.gridheight = 1;

        JLabel pseudoLabel = new JLabel("Veuillez entrer vos pseudos");
        pseudoLabel.setFont(regular);
        mainPanel.add(pseudoLabel,gc);

        //contruction des JTextField

        for (int i=1; i<getNombreJoueurs()+1; i++) {

            gc.gridx = i;
            gc.gridy = 5;
            gc.gridwidth = 1;
            gc.gridheight = 1;
            gc.fill = GridBagConstraints.NONE;

            JTextField pseudo = new JTextField();
            JTextFields.add(pseudo);
            pseudo.setPreferredSize(new Dimension(200,40));
            mainPanel.add(pseudo, gc);
        }

        //difficultéLabel

        gc.gridx = 1;
        gc.gridy = 6;
        gc.gridwidth = 4;
        gc.gridheight = 1;

        JLabel difficulteChoix = new JLabel("Veuillez séléctionner le niveau de difficulté");
        difficulteChoix.setFont(regular);
        mainPanel.add(difficulteChoix,gc);

        //choix difficulté

        gc.gridx = 1;
        gc.gridy = 7;
        gc.gridwidth = 1;
        gc.gridheight = 1;

        JButton jButtonD1 = new JButton();
        jButtonD1.setPreferredSize(new Dimension(200,60));
        jButtonD1.setText("Novice");
        mainPanel.add(jButtonD1,gc);

        jButtonD1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setChanged();
                btnD1 = true; btnD2 = false; btnD3 = false; btnD4 =  false;
                clearChanged();
            }
        });

        gc.gridx = 2;
        gc.gridy = 7;

        JButton jButtonD2 = new JButton();
        jButtonD2.setPreferredSize(new Dimension(200,60));
        jButtonD2.setText("Normal");
        mainPanel.add(jButtonD2,gc);

        jButtonD2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setChanged();
                btnD1 = false; btnD2 = true; btnD3 = false; btnD4 =  false;
                clearChanged();
            }
        });

        gc.gridx = 3;
        gc.gridy = 7;

        JButton jButtonD3 = new JButton();
        jButtonD3.setPreferredSize(new Dimension(200,60));
        jButtonD3.setText("Elite");
        mainPanel.add(jButtonD3,gc);

        jButtonD3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setChanged();
                btnD1 = false; btnD2 = false; btnD3 = true; btnD4 =  false;
                clearChanged();
            }
        });

        gc.gridx = 4;
        gc.gridy = 7;

        JButton jButtonD4 = new JButton();
        jButtonD4.setPreferredSize(new Dimension(200,60));
        jButtonD4.setText("Légendaire");
        mainPanel.add(jButtonD4,gc);

        jButtonD4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setChanged();
                btnD1 = false; btnD2 = false; btnD3 = false; btnD4 = true;
                clearChanged();
            }
        });

        gc.gridwidth = 4;
        gc.gridheight = 1;
        gc.gridx = 1;
        gc.gridy = 9;
        gc.anchor = GridBagConstraints.CENTER;

        JButton jButtonSuivant = new JButton();
        jButtonSuivant.setPreferredSize(new Dimension(250,60));
        jButtonSuivant.setText("Jouer");
        mainPanel.add(jButtonSuivant,gc);

        jButtonSuivant.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setChanged();
                notifyObservers(Messages.SUIVANT);
                btnD1 = false; btnD2 = false; btnD3 = false; btnD4 = true;
                clearChanged();
            }
        });

    }



    private class PanelAvecImage extends JPanel {

        private Image image;
        private final Integer width;
        private final Integer height;

        public PanelAvecImage(Integer width, Integer height, String imageFile) {
            this.width = width;
            this.height = height;
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

    public void update(){

        for (int i=0; i<4; i++){
            if (i<getNombreJoueurs()){
                JTextFields.get(i).setVisible(true);
            } else {
                JTextFields.get(i).setVisible(false);
            }

        }

    }

    public int getNombreJoueurs(){

        if (btn2P) {
            return 2;
        } else if (btn3P){
            return 3;
        } else {
            return 4;
        }

    }

    public ArrayList<String> getPseudos() {
        for (JTextField jTextField : JTextFields){
            pseudos.add(jTextField.getText());
        }
        return pseudos;
    }

    public NIVEAU_DIFFICULTE getNiveauDifficulte(){
        if (btnD1) {
            return NIVEAU_DIFFICULTE.NOVICE;
        } else if (btnD2) {
            return NIVEAU_DIFFICULTE.NORMAL;
        } else if (btnD3) {
            return NIVEAU_DIFFICULTE.ELITE;
        } else {
            return NIVEAU_DIFFICULTE.LEGENDAIRE;
        }
    }

    @Override
    public void setVisible(Boolean b) {
        window.setVisible(b);
    }
}
