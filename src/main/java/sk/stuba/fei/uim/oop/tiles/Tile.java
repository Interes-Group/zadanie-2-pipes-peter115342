package sk.stuba.fei.uim.oop.tiles;
import lombok.Getter;
import  lombok.Setter;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.util.Random;

public class Tile extends JPanel {
    @Setter
    private boolean highlight;
    @Getter @Setter
    private Type state;
    @Getter @Setter
    private boolean playable;
     Random rand  = new Random();


    public Tile(){
        this.state = Type.EMPTY;
        if (rand.nextInt(4)==0){
            this.state = Type.EMPTY;
        }
        else if ((rand.nextInt(4)==1)){
            this.state = Type.END;
        }else if ((rand.nextInt(4)==2)){
            this.state = Type.L;
        }
        else if ((rand.nextInt(4)==3)){
            this.state = Type.I;
        }

        this.setBorder(new BevelBorder(BevelBorder.LOWERED));
        this.setBackground(new Color(150,180,160));
    }

    public void paintComponent( Graphics g){
        super.paintComponent(g);
        ((Graphics2D) g).setStroke( new BasicStroke(2));
        g.setColor(new Color(0,120,70));
        if (this.state.equals(Type.EMPTY)){
            if (this.highlight){
                g.setColor(new Color(0,255,0));
                this.highlight = false;
            } else{
                g.setColor(new Color(128,128,128));
            }
        }
        else if(this.state.equals(Type.END)){
            g.fillRect((int) (0+this.getWidth() * 0.4), (int) ( 0+ this.getHeight() * 0.2),
                    (int) (this.getWidth() * 0.2), (int) (this.getHeight() * 0.6));
            g.fillRect((int) (0), (int) (0+ this.getHeight() * 0.4),
                    (int) (this.getWidth() * 0.6), (int) (this.getHeight() * 0.2));
        }

        else if(this.state.equals(Type.L)){
            g.fillRect((int) (0+this.getWidth() * 0.4), (int) (0),
                    (int) (this.getWidth() * 0.2), (int) (this.getHeight() * 0.59));
            g.fillRect((int) (0), (int) (0+ this.getHeight() * 0.4),
                    (int) (this.getWidth() * 0.55), (int) (this.getHeight() * 0.2));

        }
        else if(this.state.equals(Type.I)){
            g.fillRect((int) (this.getWidth()*0.4), (int) (0),
                    (int) (this.getWidth() * 0.2), (int) (this.getHeight()));
        }

    }
}
