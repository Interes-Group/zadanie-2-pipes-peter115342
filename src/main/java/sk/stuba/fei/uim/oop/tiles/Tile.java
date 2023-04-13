package sk.stuba.fei.uim.oop.tiles;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Random;
import java.awt.event.MouseEvent;


public class Tile extends JPanel {
    @Setter
    private boolean highlight;
    @Setter
    private boolean clicked;
    @Getter
    @Setter
    private Type state;
    @Getter
    @Setter
    private boolean playable;
    @Setter
    private int rotation;

    Random rand = new Random();


    public Tile() {
        this.state = Type.EMPTY;
        if (rand.nextInt(4) == 0) {
            this.state = Type.EMPTY;
        } else if ((rand.nextInt(4) == 1)) {
            this.state = Type.END;
        } else if ((rand.nextInt(4) == 2)) {
            this.state = Type.L;
        } else if ((rand.nextInt(4) == 3)) {
            this.state = Type.I;
        }
        this.setRotation(90);

        //this.setBorder(BorderFactory.createLineBorder(Color.black));
        this.setBackground(new Color(150, 180, 160));
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        AffineTransform rotateTransform = AffineTransform.getRotateInstance(Math.toRadians(rotation), getWidth() / 2, getHeight() / 2);
        ((Graphics2D)g).transform(rotateTransform);

        g.setColor(new Color(0, 120, 70));

        if (this.state.equals(Type.END)) {
            g.fillRect( (0), (int) (this.getHeight() * 0.2),
                    (int) (this.getWidth() * 0.55), (int) (this.getHeight() * 0.6));

        } else if (this.state.equals(Type.L)) {
            g.fillRect((int) (0 + this.getWidth() * 0.4),  (0),
                    (int) (this.getWidth() * 0.2), (int) (this.getHeight() * 0.59));
            g.fillRect( (0), (int) (0 + this.getHeight() * 0.4),
                    (int) (this.getWidth() * 0.59), (int) (this.getHeight() * 0.29));

        } else if (this.state.equals(Type.I)) {
            g.fillRect((int) (this.getWidth() * 0.4),  (0),
                    (int) (this.getWidth() * 0.2),  (this.getHeight()));
        }
        ((Graphics2D) g).setStroke(new BasicStroke(6));
        ((Graphics2D)g).setTransform(new AffineTransform());

        if (this.highlight) {
            g.setColor(new Color(255, 0, 0));
            this.setBorder(BorderFactory.createLineBorder(Color.red));

            this.highlight = false;
        } else {
            g.setColor(new Color(128, 128, 128));
        }
        if(this.clicked){
            rotation +=90;
            g.setColor(new Color(255, 0, 0));
            this.setBorder(BorderFactory.createLineBorder(Color.red));

            this.highlight = false;
            this.clicked = false;
        }






    }

}
