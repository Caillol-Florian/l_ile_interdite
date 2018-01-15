package Views;

import main.main.ETAT_TUILE;
import main.main.NOM_TUILE;
import main.main.PION;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.Buffer;
import java.util.ArrayList;

public class TuilePanel extends JPanel {
    private BufferedImage imageTuile;
    private NOM_TUILE nomTuile;
    private ArrayList<BufferedImage>imagesPions = new ArrayList<>();

    public TuilePanel(NOM_TUILE nomTuile, ETAT_TUILE etatTuile, ArrayList<PION>pions) {

        this.nomTuile = nomTuile;

        try {
            if (etatTuile == ETAT_TUILE.SECHE){
                this.imageTuile = ImageIO.read(new File(nomTuile.getPath()));
            } else {
                this.imageTuile = ImageIO.read(new File(nomTuile.getPathInonde()));
            }
            if (pions != null) {
                for (PION pion : pions) {
                    this.imagesPions.add(ImageIO.read(new File(pion.getPath())));
                }
            }
        } catch (IOException e) {
            System.out.println("Impossible de récupérer l'image.");
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int index = 15;
        g.drawImage(resize(imageTuile, 150, 150), 0, 0, null);
        drawPions(this.imagesPions);
    }

    public static BufferedImage resize(BufferedImage img, int newW, int newH){
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return dimg;
    }

    public void update(ETAT_TUILE etatTuile, ArrayList<PION> pions){
        try {

            // Actualisation de l'état de la tuile
            // On récupère l'image de la tuile correspondant à son état
            if (etatTuile == ETAT_TUILE.SECHE){
                this.imageTuile = ImageIO.read(new File(nomTuile.getPath()));
            } else {
                this.imageTuile = ImageIO.read(new File(nomTuile.getPathInonde()));
            }

            // Actualisation des pions
            this.imagesPions.clear();
            for(PION pion : pions) {
                this.imagesPions.add(ImageIO.read(new File(pion.getPath())));
            }

            // Affichage du nouvel état de la tuile
            imageTuile.getGraphics().drawImage(imageTuile, 0, 0, null);

            // Affichage des pions
            drawPions(this.imagesPions);

        } catch (IOException e) {
            System.out.println("Impossible de récupérer l'image.");
        }
    }

    public void drawPions(ArrayList<BufferedImage> pions){
        if(!pions.isEmpty()) {
            int index = 15;
            for (BufferedImage pion : this.imagesPions) {
                imageTuile.getGraphics().drawImage(pion, index, pion.getHeight(this) / 4, null);
                index += pion.getWidth(null);
            }
        }
    }
}
