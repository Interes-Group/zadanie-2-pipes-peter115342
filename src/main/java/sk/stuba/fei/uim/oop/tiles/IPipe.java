package sk.stuba.fei.uim.oop.tiles;

import java.awt.*;
import  javax.swing.*;

public class IPipe extends  JPanel {

    public IPipe( ){
    paintComponent(getGraphics());


    }
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        ((Graphics2D) g).setStroke( new BasicStroke(2));
        g.drawRect(0,0,5000,15);
    }

}
