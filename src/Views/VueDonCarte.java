package Views;


import Enums.Message_Coche;
import Enums.Messages;
import Modèles.Parameters;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class VueDonCarte extends Vue {
    private final JFrame window;
    private final JPanel mainPanel;
    private ArrayList<CartePanel> cartes;
    private ArrayList<CartePanel> cartesA;
    private int[] selection = {-1, -1};
    private Integer lastCarteA;
    private Integer lastCarteT;

    public VueDonCarte(){
        this.window = new JFrame();
        window.setSize(520, 370);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        window.setLocation(700, 500);
        //le titre = nom du joueur
        window.setTitle("Donner une carte");
        window.setResizable(false);
        window.setUndecorated(Parameters.UNDECORATED);


        mainPanel = new ImagePanel(520, 370, "images/backgrounds/bg_inscriptionN.jpg");
        mainPanel.setLayout(new GridBagLayout());
        this.window.add(mainPanel);
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.FIRST_LINE_START;

        mainPanel.add(new JLabel("Choisir une carte à donner"));
        c.gridy= 1;
        cartes = new ArrayList<>();
        JPanel panelCarte = new JPanel(new GridBagLayout());
        GridBagConstraints cCarte = new GridBagConstraints();
        //cCarte.gridy = 0;
        cCarte.gridx = 0;
        cCarte.gridy = 1;
        cCarte.insets = new Insets(3,3,3,3);
        Dimension sizeCarte = new Dimension(90,  120);
        for(int i = 0; i < 5; i++){
            CartePanel carte = new CartePanel("images/cartes/Fond rouge.png", 90, 120);
            carte.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {}

                @Override
                public void mousePressed(MouseEvent e) {}

                @Override
                public void mouseReleased(MouseEvent e) {
                    selection[0] = getCartes().indexOf(carte);
                    highlightCarteTSelectionne(getCartes().indexOf(carte));
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

        c.gridy= 2;
        c.anchor = GridBagConstraints.CENTER;
        mainPanel.add(new JLabel("Choisir un aventurier"), c);
        JPanel panelCarteA = new JPanel(new GridBagLayout());
        cartesA = new ArrayList<>();
        //cCarte.gridy = 0;
        c.gridy = 3;
        cCarte.gridx = 0;
        cCarte.gridy = 1;
        cCarte.insets = new Insets(3,3,3,3);
        for(int i = 0; i < 4; i++){
            CartePanel carte = new CartePanel("images/cartes/Fond rouge.png", 90, 120);
            carte.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {}

                @Override
                public void mousePressed(MouseEvent e) {}

                @Override
                public void mouseReleased(MouseEvent e) {
                    selection[1] = getCartesA().indexOf(carte);
                    highlightCarteASelectionne(getCartesA().indexOf(carte));
                }
                @Override
                public void mouseEntered(MouseEvent e) {}

                @Override
                public void mouseExited(MouseEvent e) {}
            });

            carte.setPreferredSize(sizeCarte);
            panelCarteA.add(carte, cCarte);
            cCarte.gridx++;
            getCartesA().add(carte);
        }

        mainPanel.add(panelCarteA, c);

        c.gridx= 0;
        c.gridy = 5;
        c.anchor = GridBagConstraints.LINE_START;
        JButton btnQuitter = new JButton("Fermer");
        btnQuitter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setChanged();
                notifyObservers(Messages.RETOUR);
                clearChanged();
            }
        });

        Dimension d = new Dimension(200, 40);
        mainPanel.add(btnQuitter,c);

        c.anchor = GridBagConstraints.LINE_END;
        JButton btnValider = new JButton("Valider");
        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setChanged();
                notifyObservers(selection);
                clearChanged();
            }
        });
        mainPanel.add(btnValider,c);
        btnQuitter.setPreferredSize(d);
        btnValider.setPreferredSize(d);
    }

    @Override
    public void setVisible(Boolean b) {
        window.setVisible(b);
    }

    public static void main(String[] args) {
        VueDonCarte vue = new VueDonCarte(); vue.setVisible(true);
    }

    public ArrayList<CartePanel> getCartes() {
        return cartes;
    }

    public ArrayList<CartePanel> getCartesA(){
        return cartesA;
    }

    public void highlightCarteASelectionne(int carteAHighlight){
        if(lastCarteA != null) {
            getCartesA().get(lastCarteA).setBorder(null);
        }
        lastCarteA = carteAHighlight;
        getCartesA().get(carteAHighlight).setBorder(new MatteBorder(2,2,2,2, Color.RED));
    }

    public void highlightCarteTSelectionne(int carteAHighlight){
        if(lastCarteT != null) {
            getCartes().get(lastCarteT).setBorder(null);
        }
        lastCarteT = carteAHighlight;
        getCartes().get(carteAHighlight).setBorder(new MatteBorder(2,2,2,2, Color.RED));
    }
}


