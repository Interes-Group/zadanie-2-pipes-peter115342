package sk.stuba.fei.uim.oop.pipesandtiles;
import lombok.Generated;
import lombok.Getter;
import  lombok.Setter;
import javax.swing.*;
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
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK,1,true));
        this.setBackground(new Color(150,180,160));
    }
}
