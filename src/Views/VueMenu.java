package Views;

import Enums.Messages;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class VueMenu extends Vue {

    private JFrame window = new JFrame("Menu Principal");
    private ImagePanel mainPanel;
    private Font regular = new Font("Gill Sans",0,16);
    private Font bold = new Font("Gill Sans", 0, 30);

    //Monospaced Futura Copperplate Geneva

    public VueMenu(){

        window.setSize(1024, 720);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        window.setLocation(dim.width / 2 - window.getSize().width / 2, dim.height / 2 - window.getSize().height / 2);
        window.setResizable(false);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        mainPanel = new ImagePanel(1024,720, "src/images/backgrounds/bg_menu.png") ;
        mainPanel.setLayout(new GridBagLayout());
        window.add(mainPanel);

        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.NONE;

        gc.weightx = 4;
        gc.weighty = 15;

        //on remplis le gridBagLayout

        for (int i=0; i<15; i++){
            gc.gridx = 0;
            gc.gridy = i;
            mainPanel.add(new JLabel(), gc);

            gc.gridx = 3;
            gc.gridy = i;
            mainPanel.add(new JLabel(), gc);

            if(i<10){
                gc.gridx = 1;
                gc.gridy = i;
                mainPanel.add(new JLabel(), gc);

                gc.gridx = 2;
                gc.gridy = i;
                mainPanel.add(new JLabel(), gc);
            }

            if(i>11){
                gc.gridx = 1;
                gc.gridy = i;
                mainPanel.add(new JLabel(), gc);

                gc.gridx = 2;
                gc.gridy = i;
                mainPanel.add(new JLabel(), gc);
            }
        }

        //bouton start

        gc.gridx = 1;
        gc.gridy = 10;
        gc.gridheight = 1;
        gc.gridwidth = 2;
        //gc.fill = GridBagConstraints.VERTICAL;
        //gc.fill = GridBagConstraints.HORIZONTAL;
        //gc.anchor = GridBagConstraints.PAGE_END;

        JButton btnStart= new JButton("Débuter l'aventure");
        btnStart.setFont(bold);
        btnStart.setPreferredSize( new Dimension(360,60));
        mainPanel.add(btnStart,gc);
        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setChanged();
                notifyObservers(Messages.INSCRPTION);
                clearChanged();
            }
        });

        //btn regle

        gc.gridx = 1;
        gc.gridy = 11;
        gc.gridheight = 1;
        gc.gridwidth = 1;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = new Insets(-20, 0, 0, 10);

        JButton btnRegle= new JButton("Règle");
        btnRegle.setFont(regular);
        btnRegle.setPreferredSize( new Dimension(170,40));
        mainPanel.add(btnRegle,gc);


        //btn quitter

        gc.gridx = 2;
        gc.gridy = 11;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = new Insets(-20, 10, 0, 0);


        JButton btnQuitter= new JButton("Quitter");
        btnQuitter.setFont(regular);
        btnQuitter.setPreferredSize( new Dimension(170,40));
        mainPanel.add(btnQuitter,gc);

        btnQuitter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setChanged();
                notifyObservers(Messages.QUITTER);
                clearChanged();
            }
        });
    }

    @Override
    public void setVisible(Boolean b) {
        window.setVisible(b);
    }
}
