package Views;

import main.main.Messages;
import main.main.Tuile;
import javax.swing.*;
import javax.swing.JComboBox;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class VueAssechement extends View {
        private final JFrame window ;
        private final JPanel mainPanel;
        private final JPanel accepterPanel;
        private final JPanel retourPanel;
        private  JComboBox possibilite;
        private final JLabel Assechement = new JLabel("Assechement");
        private final JButton accepter;
        private final JButton retour;

        public VueAssechement(){
            // Création de la fenètre
            window = new JFrame();
            window.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
            window.setSize(480, 240);
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            window.setLocation(dim.width / 2 - window.getSize().width / 2, dim.height / 2 - window.getSize().height / 2);

            // Création du panel principal
            mainPanel = new JPanel(new GridLayout(3, 3));
            window.add(mainPanel);
            possibilite = new JComboBox();
            //Initialisation de boutton accepter et de son listner
            accepter = new JButton("Accepter");
            accepter.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setChanged();
                    notifyObservers(Messages.VALIDERASSECHEMENT);
                    clearChanged();
                }
            });

            // Initialisation du boutton retour et de son listner
            retour  = new JButton("Retour");
            accepter.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setChanged();
                    notifyObservers(Messages.RETOUR);
                    clearChanged();
                }
            });

            // Création de panels secondaires
            accepterPanel = new JPanel(new GridLayout(3,3));
            for(int i = 0; i < 9 ; i++){
                if (i==8){
                    accepterPanel.add(accepter);
                }
                else{
                    accepterPanel.add(new JLabel());
                }
            }

            retourPanel = new JPanel(new GridLayout(3,3));

            for(int i = 0; i < 9 ; i++){
                if (i==6){
                    retourPanel.add(retour);
                }
                else{
                    retourPanel.add(new JLabel());
                }
            }
            for (int i = 0; i < 9; i++) {
                switch (i) {
                    case 1 :
                        mainPanel.add(Assechement);
                        break;
                    case 4 :
                        mainPanel.add(possibilite);
                        break;
                    case 6 :
                        mainPanel.add(retourPanel);
                        break;
                    case 8 :
                        mainPanel.add(accepterPanel);
                        break;
                    default:
                        mainPanel.add(new JLabel());
                }
            }
        }

        public Tuile getSelection(){
           // return possibilite.getSelectedItem();
            return null;
        }

        public void setAvailableTuile(ArrayList<Tuile> arTuile){
            possibilite.setModel(new DefaultComboBoxModel(arTuile.toArray()));
        }

        @Override
        public void setVisible(){
        window.setVisible(true);
    }

}

