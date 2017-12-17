package Views;
import main.main.Messages;
import main.main.NOM_AVENTURIER;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VueInscription extends Vue{
    private final JFrame window;
    private final JComboBox<Object> roles;
    private final JTextField nom;
    public VueInscription(){
        window = new JFrame();
        window.setTitle("Inscription");
        window.setSize(350, 200);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        window.setLocation(dim.width / 2 - window.getSize().width / 2, dim.height / 2 - window.getSize().height / 2);
        window.setResizable(false);

        JPanel mainPanel = new JPanel(new GridLayout(4,1));
        window.add(mainPanel);

        // Joueur + indice
        int i = 1;
        JLabel joueurIndice = new JLabel("Joueur " + i, SwingConstants.CENTER);
        mainPanel.add(joueurIndice);

        // Nom à entrer
        nom = new JTextField("Nom du joueur");
        mainPanel.add(nom);

        // Rôles disponibles à sélectionner
        roles = new JComboBox<>();
        for(NOM_AVENTURIER nomAventurier : NOM_AVENTURIER.values()){
            roles.addItem(nomAventurier);
        }
        mainPanel.add(roles);

        // Bouton Valider
        JButton btnValider = new JButton("Valider");
        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setChanged();
                notifyObservers(Messages.VALIDERINSCRIPTION);
                clearChanged();
            }
        });
        mainPanel.add(btnValider);
    }

    public void nextAventurier(){

    }

    public NOM_AVENTURIER getRoleSelectionne(){
    return null;
    }

    public String getNom(){
        return nom.getText();
    }
    @Override
    public void setVisible(Boolean b) {
        if(b){
            window.setVisible(true);
        } else {
            window.setVisible(false);
        }
    }
}


