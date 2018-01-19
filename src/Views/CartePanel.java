package Views;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CartePanel extends JPanel {
    private BufferedImage imageCarte;
    private int W;
    private int H;
    private String path;
    public CartePanel(String path, int W, int H){
        setPath(path);
        setH(H);
        setW(W);
        try {
            this.imageCarte = ImageIO.read(new File(path));
        } catch (IOException e) {
            System.out.println("Impossible de récupérer l'image.");
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(resize(imageCarte, getW(), getH()), 0, 0, null);
    }


    public static BufferedImage resize(BufferedImage img, int newW, int newH){
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return dimg;
    }

    public void setCarte(String path){
        try {
            this.imageCarte = ImageIO.read(new File(path));
            setPath(path);
            repaint();
        } catch (IOException e) {
            System.out.println("Impossible de récupérer l'image.");
        }
    }

    public int getW() {
        return W;
    }

    public void setW(int w) {
        W = w;
    }

    public int getH() {
        return H;
    }

    public void setH(int h) {
        H = h;
    }

    public String getPath() {
        return path;
    }

    // Cas où on veut récupérer le rôle de l'aventurier de la carte
    // On admet que la carte est une carte aventurier.
    // Le 19 ème caractère est celui après le dernier / dans "images/personnages/---.png" soit le début du nom de l'aventurier
    public String getNomAventurier(){
        String pathC = "";
        for(int i = 23; i < path.toCharArray().length; i++){
            if(i == 23){
                pathC += getPath().toUpperCase().charAt(i);
            } else {
                pathC += getPath().charAt(i);
            }
        }

        String nomAventurier = "";
        int j = 0;
        while(pathC.charAt(j) != '.'){
            nomAventurier += pathC.charAt(j);
            j++;
        }

        return nomAventurier;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
