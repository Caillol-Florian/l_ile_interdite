package Views;

import main.main.Messages;
import main.main.NOM_TUILE;
import main.main.Tuile;
import javax.swing.*;
import javax.swing.JComboBox;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class VueDeplacement extends Vue {
    private final JFrame window ;
    private final JPanel mainPanel;
    private final JPanel panelCentre;
    private final JComboBox<Object> listeTuilesAssechement;
    private final JPanel panelBoutons;

    public VueDeplacement(){
        // Création de la fenètre
        window = new JFrame();
        window.setTitle("Se Déplacer");
        window.setSize(350, 200);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        window.setLocation(dim.width / 2 - window.getSize().width / 2, dim.height / 2 - window.getSize().height / 2);
        window.setResizable(false);

        // =================================================================================
        // Création du panel principal
        mainPanel = new JPanel(new BorderLayout());
        window.add(mainPanel);

        // =================================================================================
        // Création du panel centre (sélection de la tuile à assécher)
        panelCentre = new JPanel(new GridLayout(2,1));
        mainPanel.add(panelCentre, BorderLayout.CENTER);
        listeTuilesAssechement = new JComboBox<>();

        panelCentre.add(new JLabel("Choisissez une destination :",SwingConstants.CENTER ));
        panelCentre.add(listeTuilesAssechement);

        // =================================================================================
        // Création du panel boutons (Retour/Accepter)
        this.panelBoutons = new JPanel(new GridLayout(1,2));
        this.panelBoutons.setOpaque(false);
        mainPanel.add(this.panelBoutons, BorderLayout.SOUTH);

        JButton retour = new JButton("Retour");
        // Lors d'un clic sur le bouton Retour
        retour.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setChanged();
                notifyObservers(Messages.RETOUR);
                clearChanged();
            }
        });

        JButton accepter = new JButton("Valider");
        // Lors d'un clic sur le bouton Accepter
        accepter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setChanged();
                notifyObservers(Messages.VALIDERASSECHEMENT);
                clearChanged();
            }
        });

        this.panelBoutons.add(retour);
        this.panelBoutons.add(accepter);
    }

    public Tuile getSelection(){
        // return possibilite.getSelectedItem();
        return null;
    }

    public void setAvailableTuile(ArrayList<Tuile> arTuile){
        listeTuilesAssechement.setModel(new DefaultComboBoxModel<>(arTuile.toArray()));
    }

    @Override
    public void setVisible(Boolean b){
        if (b == true) {
            window.setVisible(true);
        } else {
            window.setVisible(false);
        }
    }

    @Override
    public NOM_TUILE getTuileSelectionnee(){
        NOM_TUILE nom_tuile_trouvee = null;
        for(NOM_TUILE nom_tuile : NOM_TUILE.values()){
            if(nom_tuile.toString() == listeTuilesAssechement.getSelectedItem()){
                nom_tuile_trouvee = nom_tuile;
            }
        }
        return nom_tuile_trouvee;
    }

}
