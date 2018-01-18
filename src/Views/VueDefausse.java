package Views;


import Modèles.Parameters;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.util.ArrayList;

public class VueDefausse extends Vue {
    private final JFrame window;
    private final JPanel mainPanel;
    private ArrayList<CartePanel> cartes;
    private JLabel labelNomJoueur = new JLabel();
    public VueDefausse(){
        this.window = new JFrame();
        window.setSize(720, 180);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        window.setLocation(700, 500);
        //le titre = nom du joueur
        window.setTitle("Défausser une carte !");
        window.setResizable(false);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setUndecorated(Parameters.UNDECORATED);


        mainPanel = new ImagePanel(720, 180, "images/backgrounds/bg_inscriptionN.jpg");
        mainPanel.setLayout(new GridBagLayout());
        this.window.add(mainPanel);
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        mainPanel.add(labelNomJoueur,c);
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.gridy= 1;
        mainPanel.add(new JLabel("Choisir une carte à défausser"),c);
        c.gridy= 2;
        cartes = new ArrayList<>();
        JPanel panelCarte = new JPanel(new GridBagLayout());
        GridBagConstraints cCarte = new GridBagConstraints();
        //cCarte.gridy = 0;
        cCarte.gridx = 0;
        cCarte.gridy = 1;
        cCarte.insets = new Insets(3,3,3,3);
        Dimension sizeCarte = new Dimension(90,  120);
        for(int i = 0; i < 6; i++){
            CartePanel carte = new CartePanel("images/cartes/Fond rouge.png", 90, 120);
            carte.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {}

                @Override
                public void mousePressed(MouseEvent e) {}

                @Override
                public void mouseReleased(MouseEvent e) {
                    setChanged();
                    notifyObservers(getCartes().indexOf(carte));
                    clearChanged();
                }
                @Override
                public void mouseEntered(MouseEvent e) {}

                @Override
                public void mouseExited(MouseEvent e) {}
            });

            carte.setPreferredSize(sizeCarte);
            panelCarte.add(carte, cCarte);
            cCarte.gridx++;
            getCartes().add(carte);
        }
        mainPanel.add(panelCarte, c);
        panelCarte.setOpaque(false);
    }

    @Override
    public void setVisible(Boolean b) {
        window.setVisible(b);
    }

    public void setNomJoueur(String nomJoueur){
        this.labelNomJoueur.setText(nomJoueur);
    }
    public static void main(String[] args) {
        VueDefausse vue = new VueDefausse();
        vue.setVisible(true);
    }

    public ArrayList<CartePanel> getCartes() {
        return cartes;
    }
}


