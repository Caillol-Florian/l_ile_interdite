package Views;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

class ImagePanel extends JPanel {

    private Image image;
    private final Integer width ;
    private final Integer height ;

    public ImagePanel(Integer width, Integer height, String imageFile) {
        this.width = width ;
        this.height = height ;
        try {
            this.image = ImageIO.read(new File(imageFile));
        } catch (IOException ex) {
            System.err.println("Erreur de lecture background");
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            g.drawImage(image, 0, 0, this.width, this.height, null, this);
        }
    }
}