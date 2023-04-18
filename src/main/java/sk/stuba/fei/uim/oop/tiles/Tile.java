package sk.stuba.fei.uim.oop.tiles;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Random;


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

    @Getter
    private Random rand = new Random();
    @Getter
    private Boolean wasRotated;
    public Tile() {
        this.state = Type.EMPTY;
        this.wasRotated = false;
        this.setRotation(90);

      //  this.setBorder(BorderFactory.createLineBorder(Color.black));
        this.setBackground(new Color(150, 180, 160));
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        AffineTransform rotateTransform = AffineTransform.getRotateInstance(Math.toRadians(rotation), getWidth() / 2, getHeight() / 2);
        ((Graphics2D)g).transform(rotateTransform);
        if(!this.wasRotated){

            if( rand.nextInt(4)==1){
                rotation+=90;
            }
            if( rand.nextInt(4)==2){
                rotation+=180;

            }
            if( rand.nextInt(4)==3){
                rotation+=270;
            }
            wasRotated=true;
        }
        g.setColor(new Color(0, 120, 70));

        if (this.state.equals(Type.START)) {
            g.setColor(new Color(0, 255, 0));

            g.fillRect( (0), (int) (0),
                    this.getWidth(), this.getHeight());

            g.setColor(new Color(0, 120, 70));


        } else if (this.state.equals(Type.FINISH)) {
            int size = Math.min(this.getWidth()+14, (this.getHeight())+14) / 6;
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 6; j++) {
                    if ((i + j) % 2 == 0) {
                        g.setColor(Color.BLACK);
                    } else {
                        g.setColor(Color.WHITE);
                    }
                    g.fillRect(i * size, j * size, size, size);
                }
            }
        }
        else if (this.state.equals(Type.L)) {
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
            this.setBorder(BorderFactory.createLineBorder(Color.red,3));

            this.highlight = false;
        } else {
            g.setColor(new Color(128, 128, 128));
        }
        if(this.clicked){
            rotation +=90;
            this.setBorder(BorderFactory.createLineBorder(Color.red,3));

            this.clicked = false;
        }






    }



}
