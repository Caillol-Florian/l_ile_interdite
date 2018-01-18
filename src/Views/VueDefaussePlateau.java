package Views;


import Enums.Messages;
import Modèles.Parameters;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.util.ArrayList;

public class VueDefaussePlateau extends Vue {
    private final JFrame window;
    private final JPanel mainPanel;
    private ArrayList<CartePanel> cartes;
    private JLabel labelNomJoueur = new JLabel();
    private Font titreFont = new Font("Gill Sans", 1,22);

    public VueDefaussePlateau(){
        this.window = new JFrame();
        window.setSize(1080, 720);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        window.setLocation(dim.width / 2 - window.getSize().width / 2, dim.height / 2 - window.getSize().height / 2);
        //le titre = nom du joueur
        window.setTitle("Défausse plateau");
        window.setResizable(false);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setUndecorated(Parameters.UNDECORATED);


        mainPanel = new ImagePanel(1080, 1080, "images/backgrounds/bg_inscriptionN.jpg");
        mainPanel.setLayout(new GridBagLayout());
        this.window.add(mainPanel);
        GridBagConstraints c = new GridBagConstraints();

        c.gridx = 0;
        c.gridy = 0;
        mainPanel.add(labelNomJoueur,c);

        c.insets = new Insets(0,0,20,0);
        c.anchor = GridBagConstraints.CENTER;
        c.gridy= 1;

        JLabel titre = new JLabel("Cartes défaussées");
        titre.setFont(titreFont);
        mainPanel.add(titre,c);

        c.gridy= 2;
        cartes = new ArrayList<>();

        JPanel panelCarte = new JPanel(new GridBagLayout());
        GridBagConstraints cCarte = new GridBagConstraints();
        cCarte.gridx = 0;
        cCarte.gridy = 1;
        cCarte.insets = new Insets(10,10,10,10);
        Dimension sizeCarte = new Dimension(90,  120);

        for(int i = 0; i < 4; i++){
            for (int j = 0; j<6; j++){
                CartePanel carte = new CartePanel("images/cartes/Fond rouge.png", 90, 120);
                carte.setPreferredSize(sizeCarte);
                panelCarte.add(carte, cCarte);
                cCarte.gridx++;
                getCartesDefaussees().add(carte);

            }
            cCarte.gridx = 0;
            cCarte.gridy++;
        }

        c.insets = new Insets(0,0,0,0);
        mainPanel.add(panelCarte, c);
        panelCarte.setOpaque(false);

        c.gridy= 3;
        c.insets = new Insets(15,0,0,0);
        JButton btnQuitter = new JButton("Quitter");
        btnQuitter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setChanged();
                notifyObservers(Messages.QUITTER);
                clearChanged();
            }
        });
        btnQuitter.setPreferredSize(new Dimension(200,40));
        mainPanel.add(btnQuitter,c);



    }

    @Override
    public void setVisible(Boolean b) {
        window.setVisible(b);
    }

    public static void main(String[] args) {
        VueDefaussePlateau vue = new VueDefaussePlateau();
        vue.setVisible(true);
    }

    public ArrayList<CartePanel> getCartesDefaussees() {
        return cartes;
    }
}