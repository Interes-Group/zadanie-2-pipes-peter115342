package sk.stuba.fei.uim.oop.tiles;
import lombok.Getter;
import  lombok.Setter;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

public class Tile extends JPanel {
    @Setter
    private boolean highlight;
    @Getter @Setter
    private boolean state;
    @Getter @Setter
    private boolean playable;


    public Tile(){
        this.state = false;
        this.setBorder(new BevelBorder(BevelBorder.LOWERED));
        this.setBackground(new Color(150,180,160));
    }
}
