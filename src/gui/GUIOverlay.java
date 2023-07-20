package src.gui;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class GUIOverlay extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Set the fill color and transparency
        Color fillColor = new Color(93, 84, 71, 87);
        g2d.setColor(fillColor);
        g2d.fillRect(10, 10, 400, 100);

        // Set the border color and transparency
        Color borderColor = new Color(82, 77, 60, 200);
        // set border width
        g2d.setStroke(new BasicStroke(2));
        g2d.setColor(borderColor);
        // Draw the rounded rectangle
        g2d.drawRect(10, 10, 400, 100);

        // Load image from /resources/rs-asset.svg
        Image img = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/resources/rs-asset.svg"))).getImage();
        // Draw the image
        g2d.drawImage(img, 20, 20, 80, 80, null);

    }
}
