package Views;

import Enums.ETAT_TUILE;
import Enums.NOM_TUILE;
import Enums.PION;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class TuilePanel extends JPanel {

    private BufferedImage imageTuile;
    private NOM_TUILE nomTuile;
    private ETAT_TUILE etatTuile;
    private ArrayList<BufferedImage>imagesPions = new ArrayList<>();
    private ArrayList<PION> pions;
    BufferedImage highlight;


    public TuilePanel(NOM_TUILE nomTuile, ETAT_TUILE etatTuile, ArrayList<PION>pions) {
        if(pions == null){
            this.setPions(new ArrayList<>());
        } else {
            this.setPions(pions);
        }

        setEtatTuile(etatTuile);
        setNomTuile(nomTuile);
        setNomTuile(nomTuile);

        try {
            // Récupération de l'image highlight
            this.highlight = ImageIO.read(new File("images/tuiles/highlight.png"));

            // Récupération de l'image de la tuile en fonction de son état.
            if (getEtatTuile() == ETAT_TUILE.SECHE){
                this.setImageTuile(ImageIO.read(new File(nomTuile.getPath())));
            } else {
                this.setImageTuile(ImageIO.read(new File(nomTuile.getPathInonde())));
            }
            if (pions != null) {
                for (PION pion : pions) {
                    this.getImagesPions().add(ImageIO.read(new File(pion.getPath())));
                }
            }
        } catch (IOException e) {
            System.out.println("Impossible de récupérer l'image.");
        }
    }

    public TuilePanel(String path){
        try {
            this.setImageTuile(ImageIO.read(new File(path)));
        } catch (IOException e) {
            System.out.println("Impossible de récupérer l'image.");
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(resize(getImageTuile(), 150, 150), 0, 0, null);
        drawPions(this.getImagesPions());
    }

    public static BufferedImage resize(BufferedImage img, int newW, int newH){
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return dimg;
    }

    public void update(ArrayList<PION> pions){
        try {
            // Actualisation de l'état de la tuile
            // On récupère l'image de la tuile correspondant à son état
            if (getEtatTuile() == ETAT_TUILE.SECHE){
                this.setImageTuile(ImageIO.read(new File(getNomTuile().getPath())));
            } else if (getEtatTuile() == ETAT_TUILE.INONDEE) {
                this.setImageTuile(ImageIO.read(new File(getNomTuile().getPathInonde())));
            }

            // Affichage des pions
                // Actualisation des pions
                this.getImagesPions().clear();
                if(!pions.isEmpty()) {
                    for (PION pion : pions) {
                        this.getImagesPions().add(ImageIO.read(new File(pion.getPath())));
                    }
                }

            Image image = new BufferedImage(150, 150, BufferedImage.TYPE_INT_ARGB);
            paintComponent(image.getGraphics());
            repaint();

        } catch (IOException e) {
            System.out.println("Impossible de récupérer l'image.");
        }
    }

    public void drawPions(ArrayList<BufferedImage> pions){
        if(!pions.isEmpty()) {
            int index = 15;
            for (BufferedImage pion : this.getImagesPions()) {
                getImageTuile().getGraphics().drawImage(pion, index, pion.getHeight(this) / 4, null);
                index += pion.getWidth(null);
            }
        }
    }

    public void highlight(Boolean hightlightOn){
        // Affichage du nouvel état de la tuile
        getImageTuile().getGraphics().drawImage(getImageTuile(), 0, 0, null);

        // Highlight
        if (hightlightOn) {
            getImageTuile().getGraphics().drawImage(resize(highlight, 503, 502), 0, 0, null);
        } else {
            update(getPions());
        }

        repaint();
    }

    public NOM_TUILE getNomTuile() {
        return nomTuile;
    }

    public ArrayList<PION> getPions(){
            return pions;
    }

    public void setEtatTuile(ETAT_TUILE etatTuile) {
        this.etatTuile = etatTuile;
    }

    public ETAT_TUILE getEtatTuile() {
        return etatTuile;
    }

    public BufferedImage getImageTuile() {
        return imageTuile;
    }

    public void setImageTuile(BufferedImage imageTuile) {
        this.imageTuile = imageTuile;
    }

    public void setNomTuile(NOM_TUILE nomTuile) {
        this.nomTuile = nomTuile;
    }

    public ArrayList<BufferedImage> getImagesPions() {
        return imagesPions;
    }

    public void setPions(ArrayList<PION> pions) {
        this.pions = pions;
    }
}
