package Views;

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
    private boolean btn4P = true;
    private JButton jButton2;
    private JButton jButton3;
    private JButton jButton4;
    private ArrayList<String> pseudos = new ArrayList<>();

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

        JLabel titreLabel = new JLabel("Paramètres de la partie");
        titreLabel.setFont(bold);
        mainPanel.add(titreLabel,gc);

        //label nombre de joueur

        gc.gridx = 1;
        gc.gridy = 2;
        gc.gridwidth = 4;
        gc.gridheight = 1;
        gc.anchor = GridBagConstraints.LINE_START;

        JLabel titre = new JLabel("Veuillez séléctionner le nombre de joueur");
        titre.setFont(regular);
        mainPanel.add(titre,gc);

        //choix nombre de joueur

        gc.gridx = 1;
        gc.gridy = 3;
        gc.gridwidth = 1;
        gc.gridheight = 1;

        //URL de l'image
        String imgUrl1="images/icones/icon2P.png";
        ImageIcon icone1 = new ImageIcon(imgUrl1);
        //création des boutons - ajout icones
        jButton2 = new JButton(icone1);
        jButton2.setPreferredSize(new Dimension(200,80));
        //ajouter les deux JLabel a JFrame
        mainPanel.add(jButton2,gc);

        jButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setChanged();
                update(btn2P, jButton2);
                clearChanged();
            }
        });

        gc.gridx = 2;
        gc.gridy = 3;

        String imgUrl2="images/icones/icon3P.png";
        ImageIcon icone2 = new ImageIcon(imgUrl2);
        jButton3 = new JButton(icone2);
        jButton3.setPreferredSize(new Dimension(200,80));
        mainPanel.add(jButton3,gc);

        jButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update(btn3P,jButton3);
                setChanged();
                clearChanged();
            }
        });


        gc.gridx = 3;
        gc.gridy = 3;

        String imgUrl3="images/icones/icon4P.png";
        ImageIcon icone3 = new ImageIcon(imgUrl3);
        jButton4 = new JButton(icone3);
        jButton4.setPreferredSize(new Dimension(200,80));
        mainPanel.add(jButton4,gc);

        jButton4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update(btn4P,jButton4);
                setChanged();
                clearChanged();
            }
        });

        mainPanel.validate();

        //label psuedo joueur

        gc.gridx = 1;
        gc.gridy = 4;
        gc.gridwidth = 4;
        gc.gridheight = 1;
        gc.anchor = GridBagConstraints.LINE_START;

        JLabel pseudoLabel = new JLabel("Veuillez entrer vos pseudos");
        pseudoLabel.setFont(regular);
        mainPanel.add(pseudoLabel,gc);

        //

        for (int i=1; i<getNombreJoueurs()+1; i++) {

            gc.gridx = i;
            gc.gridy = 5;
            gc.gridwidth = 1;
            gc.gridheight = 1;
            gc.fill = GridBagConstraints.NONE;

            JTextField pseudo = new JTextField();
            pseudo.setPreferredSize(new Dimension(200,80));
            mainPanel.add(pseudo, gc);
        }








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

    public void update(Boolean btnB, JButton btn){

        btnB = true;
        btn.setPreferredSize(new Dimension(210,90));


        if (btnB != btn2P){
            btn2P = false;
            jButton2.setPreferredSize(new Dimension(200,80));
        } else if (btnB != btn3P) {
            System.out.println("lol");
            btn3P = false;
            jButton3.setPreferredSize(new Dimension(200,80));
        } else if (btnB != btn4P) {
            btn4P = false;
            jButton4.setPreferredSize(new Dimension(200,80));
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

    @Override
    public void setVisible(Boolean b) {
        window.setVisible(b);
    }
}
