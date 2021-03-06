package Views;

import Enums.Messages;
import Enums.NIVEAU_DIFFICULTE;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static Modèles.Utils.afficherInformation;

public class VueInscription extends Vue {


    private JFrame window = new JFrame("Inscription");
    private ImagePanel mainPanel;
    private Font regular = new Font("Gill Sans",0,22);
    private Font bold = new Font("Gill Sans", 1, 25);
    private Font jouer = new Font("Gill Sans", 1,20);
    private Font btn = new Font("Gill Sans", 0,17);
    private Font btnBold = new Font("Gill Sans", 1,17);
    private boolean btn2P = false;
    private boolean btn3P = false;
    private boolean btn4P = false;

    private ArrayList<String> pseudos = new ArrayList<>();
    private ArrayList<JTextField> JTextFields = new ArrayList<>();

    private boolean btnD1 = true;
    private boolean btnD2 = false;
    private boolean btnD3 = false;
    private boolean btnD4 = false;

    private JButton jButtonD1;
    private JButton jButtonD2;
    private JButton jButtonD3;
    private JButton jButtonD4;

    public VueInscription(){

        window.setSize(1024, 720);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        window.setLocation(dim.width / 2 - window.getSize().width / 2, dim.height / 2 - window.getSize().height / 2);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        mainPanel = new ImagePanel(1024,720, "src/images/backgrounds/bg_inscriptionN.jpg") ;
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


        String imgUrl="src/images/icones/iconSettings.png";
        ImageIcon icone0 = new ImageIcon(imgUrl);

        JLabel titreLabel = new JLabel(icone0);
        titreLabel.setText("   Paramètres de la partie");
        titreLabel.setForeground(Color.BLACK);
        titreLabel.setFont(bold);
        mainPanel.add(titreLabel,gc);

        //label nombre de joueur

        gc.gridx = 1;
        gc.gridy = 2;
        gc.gridwidth = 4;
        gc.gridheight = 1;
        gc.anchor = GridBagConstraints.LAST_LINE_START;
        gc.insets = new Insets(0,25,0,0);

        JLabel titre = new JLabel("Veuillez séléctionner le nombre de joueur");
        titre.setForeground(Color.BLACK);
        titre.setFont(regular);
        mainPanel.add(titre,gc);

        //choix nombre de joueur

        gc.gridx = 1;
        gc.gridy = 3;
        gc.gridwidth = 1;
        gc.gridheight = 1;
        gc.anchor = GridBagConstraints.CENTER;
        gc.insets = new Insets(0,0,0,0);

        //URL de l'image
        String imgUrl1="src/images/icones/icon2PN.png";
        ImageIcon icone1 = new ImageIcon(imgUrl1);
        //création des boutons - ajout icones
        JButton jButton2 = new JButton(icone1);
        jButton2.setPreferredSize(new Dimension(200,60));
        //ajouter les deux JLabel a JFrame
        mainPanel.add(jButton2,gc);

        jButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btn2P = true; btn3P = false; btn4P = false;
                updateJTextField();
            }
        });

        gc.gridx = 2;
        gc.gridy = 3;

        String imgUrl2="src/images/icones/icon3PN.png";
        ImageIcon icone2 = new ImageIcon(imgUrl2);
        JButton jButton3 = new JButton(icone2);
        jButton3.setPreferredSize(new Dimension(200,60));
        mainPanel.add(jButton3,gc);

        jButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btn2P = false; btn3P = true; btn4P = false;
                updateJTextField();
            }
        });


        gc.gridx = 3;
        gc.gridy = 3;

        String imgUrl3="src/images/icones/icon4PN.png";
        ImageIcon icone3 = new ImageIcon(imgUrl3);
        JButton jButton4 = new JButton(icone3);
        jButton4.setPreferredSize(new Dimension(200,60));
        mainPanel.add(jButton4,gc);

        jButton4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btn2P = false; btn3P = false; btn4P = true;
                updateJTextField();
            }
        });

        mainPanel.validate();

        //label psuedo joueur

        gc.gridx = 1;
        gc.gridy = 4;
        gc.gridwidth = 4;
        gc.gridheight = 1;
        gc.anchor = GridBagConstraints.LAST_LINE_START;
        gc.insets = new Insets(0,25,0,0);

        JLabel pseudoLabel = new JLabel("Veuillez entrer vos pseudos");
        pseudoLabel.setForeground(Color.BLACK);
        pseudoLabel.setFont(regular);
        mainPanel.add(pseudoLabel,gc);

        //contruction des JTextField

        for (int i=1; i<getNombreJoueurs()+1; i++) {

            gc.gridx = i;
            gc.gridy = 5;
            gc.gridwidth = 1;
            gc.gridheight = 1;
            gc.anchor = GridBagConstraints.CENTER;
            gc.insets = new Insets(0,0,0,0);

            JTextField pseudo = new JTextField("Joueur " + i);
            JTextFields.add(pseudo);
            pseudo.setPreferredSize(new Dimension(200,40));
            mainPanel.add(pseudo, gc);
        }

        //difficultéLabel

        gc.gridx = 1;
        gc.gridy = 6;
        gc.gridwidth = 4;
        gc.gridheight = 1;
        gc.anchor = GridBagConstraints.LAST_LINE_START;
        gc.insets = new Insets(0,25,0,0);

        JLabel difficulteChoix = new JLabel("Veuillez séléctionner le niveau de difficulté");
        difficulteChoix.setForeground(Color.BLACK);
        difficulteChoix.setFont(regular);
        mainPanel.add(difficulteChoix,gc);

        //choix difficulté

        gc.gridx = 1;
        gc.gridy = 7;
        gc.gridwidth = 1;
        gc.gridheight = 1;
        gc.anchor = GridBagConstraints.CENTER;
        gc.insets = new Insets(0,0,0,0);

        jButtonD1 = new JButton();
        jButtonD1.setPreferredSize(new Dimension(200,60));
        jButtonD1.setText("Novice");
        mainPanel.add(jButtonD1,gc);

        jButtonD1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnD1 = true; btnD2 = false; btnD3 = false; btnD4 =  false;
                updateBtn(jButtonD1);
            }
        });

        gc.gridx = 2;
        gc.gridy = 7;

        jButtonD2 = new JButton();
        jButtonD2.setPreferredSize(new Dimension(200,60));
        jButtonD2.setText("Normal");
        mainPanel.add(jButtonD2,gc);

        jButtonD2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnD1 = false; btnD2 = true; btnD3 = false; btnD4 =  false;
                updateBtn(jButtonD2);
            }
        });

        gc.gridx = 3;
        gc.gridy = 7;

        jButtonD3 = new JButton();
        jButtonD3.setPreferredSize(new Dimension(200,60));
        jButtonD3.setText("Elite");
        jButtonD3.setFont(btnBold);
        mainPanel.add(jButtonD3,gc);

        jButtonD3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnD1 = false; btnD2 = false; btnD3 = true; btnD4 =  false;
                updateBtn(jButtonD3);
            }
        });

        gc.gridx = 4;
        gc.gridy = 7;

        jButtonD4 = new JButton();
        jButtonD4.setPreferredSize(new Dimension(200,60));
        jButtonD4.setText("Légendaire");
        mainPanel.add(jButtonD4,gc);

        jButtonD4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnD1 = false; btnD2 = false; btnD3 = false; btnD4 = true;
                updateBtn(jButtonD4);
            }
        });

        updateBtn(jButtonD1);

        gc.gridwidth = 4;
        gc.gridheight = 1;
        gc.gridx = 0;
        gc.gridy = 9;

        // Bouton retour
        JButton jButtonRetour = new JButton();
        jButtonRetour.setPreferredSize(new Dimension(250,60));
        jButtonRetour.setText("Retour");
        jButtonRetour.setFont(jouer);
        mainPanel.add(jButtonRetour,gc);
        jButtonRetour.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    setChanged();
                    notifyObservers(Messages.RETOUR);
                    clearChanged();
            }
        });

        // Bouton jouer
        gc.gridx = 2;
        JButton jButtonValider = new JButton();
        jButtonValider.setPreferredSize(new Dimension(250,60));
        jButtonValider.setText("Jouer");
        jButtonValider.setFont(jouer);
        mainPanel.add(jButtonValider,gc);

        jButtonValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Boolean pseudosRemplis = true;
                for(int i = 0; i < getPseudos().size(); i++){
                    if (getPseudos().get(i).contentEquals("")) pseudosRemplis = false;
                }
                if (!pseudosRemplis){
                    afficherInformation("Veuillez écrire vos pseudos pour commencer la partie");
                } else {
                    setChanged();
                    notifyObservers(Messages.VALIDERINSCRIPTION);
                    clearChanged();
                }
            }
        });
    }

    public void updateJTextField(){

        for (int i=0; i<4; i++){
            if (i<getNombreJoueurs()){
                JTextFields.get(i).setVisible(true);
            } else {
                JTextFields.get(i).setVisible(false);
            }
        }
    }

    public void updateBtn(JButton jButton){
        jButtonD1.setFont(btn);
        jButtonD2.setFont(btn);
        jButtonD3.setFont(btn);
        jButtonD4.setFont(btn);
        jButton.setFont(btnBold);
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
        pseudos.clear();
        for (int i = 0; i<getNombreJoueurs();i++){
            pseudos.add(JTextFields.get(i).getText());
        }
        return pseudos;
    }

    public int getNiveauDifficulte(){
        if (btnD1) {
            return 1;
        } else if (btnD2) {
            return 2;
        } else if (btnD3) {
            return 3;
        } else {
            return 4;
        }
    }

    @Override
    public void setVisible(Boolean b) {
        window.setVisible(b);
    }
}
