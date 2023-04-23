package sk.stuba.fei.uim.oop.tiles;

import lombok.Getter;
import lombok.Setter;
import sk.stuba.fei.uim.oop.board.Direction;
import sk.stuba.fei.uim.oop.board.Node;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Tile extends JPanel {
    @Getter
    private List<Node> accessibleNeighbors = new ArrayList<>();

    @Setter
    @Getter
    private boolean highlight;
    @Setter
    private boolean clicked;
    @Getter
    @Setter
    private Type type;
    @Getter
    @Setter
    private boolean playable;
    @Setter
    private int rotate;
    @Getter
    private Rotation rotation;

    @Getter
    @Setter
    private  Boolean visited;
    @Getter
    private Random rand;
    @Getter
    private Boolean wasRotated;
    @Setter
    private Boolean checkActive;
    @Setter
    @Getter
    private Node coordinates;

    public Tile(int x,int y ) {

        this.type = Type.EMPTY;
        this.wasRotated = false;
        this.rotate = 0;
        rand = new Random();
        this.setBackground(new Color(150, 180, 160));
        coordinates = new Node(x,y);
        checkActive = false;
        visited = false;

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        checkRotation();
        AffineTransform rotateTransform = AffineTransform.getRotateInstance(Math.toRadians(rotate), getWidth() / 2, getHeight() / 2);
        ((Graphics2D) g).transform(rotateTransform);
        if (!this.wasRotated) {
            if (rand.nextInt(4) == 1) {
                rotate += 90;
                checkRotation();
            }
            if (rand.nextInt(4) == 2) {
                rotate += 180;
                checkRotation();
            }
            if (rand.nextInt(4) == 3) {
                rotate += 270;
                checkRotation();
            }
            wasRotated = true;
        }
        g.setColor(new Color(0, 120, 70));


            if (this.type.equals(Type.START)) {
                g.setColor(new Color(0, 255, 0));
                drawRectangle(g);

            } else if (this.type.equals(Type.END)) {
                if (checkActive){
                    g.setColor(new Color(0,0 , 255));
                }
                else{
                    g.setColor(new Color(200, 120, 70));

                }
                drawRectangle(g);
            } else if (this.type.equals(Type.L)) {
                if (checkActive){
                    g.setColor(new Color(0,0 , 255));
                }
                else{
                    g.setColor(new Color(0, 120, 70));

                }
                g.fillRect((int) (0 + this.getWidth() * 0.4), (0),
                        (int) (this.getWidth() * 0.2), (int) (this.getHeight() * 0.59));
                g.fillRect((0), (int) (0 + this.getHeight() * 0.4),
                        (int) (this.getWidth() * 0.59), (int) (this.getHeight() * 0.29));

            } else if (this.type.equals(Type.I)) {
                if (checkActive){
                    g.setColor(new Color(0,0 , 255));
                }
                else{
                    g.setColor(new Color(0, 120, 70));

                }
                g.fillRect((int) (this.getWidth() * 0.4), (0),
                        (int) (this.getWidth() * 0.2), (this.getHeight()));
            }
            checkActive = false;

        ((Graphics2D) g).setStroke(new BasicStroke(6));
        ((Graphics2D) g).setTransform(new AffineTransform());


        if (this.highlight) {
            this.setBorder(BorderFactory.createLineBorder(Color.red, 3));
            this.highlight = false;
        } else {
            g.setColor(new Color(128, 128, 128));
        }
        if (this.clicked) {
            rotate += 90;
            checkRotation();
            if (rotate > 359) {
                rotate = 0;
            }


            this.setBorder(BorderFactory.createLineBorder(Color.red, 3));

            this.clicked = false;
        }
        checkRotation();
        changeAccessibleNeighbors();

    }

    private void drawRectangle(Graphics g) {
        g.fillRect((0), (0), (this.getWidth()), (this.getHeight()));
        if (checkActive){
            g.setColor(new Color(0,0 , 255));
        }
        else {
            g.setColor(new Color(0, 120, 70));
        }

        g.fillRect((int) (this.getWidth() * 0.4), (int) (this.getWidth() * 0.4),
                (int) (this.getWidth() * 0.2), (this.getHeight()));
    }


    private void checkRotation() {
        if (this.rotate == 0) {
            if (this.type.equals(Type.I)) {
                this.rotation = Rotation.UP_DOWN;
            }
            if (this.type.equals(Type.L)) {
                this.rotation = Rotation.LEFT_UP;
            }
            if (this.type.equals(Type.START) || this.type.equals(Type.END)) {
                this.rotation = Rotation.DOWN;
            }
        } else if (this.rotate == 90) {
            if (this.type.equals(Type.I)) {
                this.rotation = Rotation.LEFT_RIGHT;
            }
            if (this.type.equals(Type.L)) {
                this.rotation = Rotation.RIGHT_UP;
            }
            if (this.type.equals(Type.START) || this.type.equals(Type.END)) {
                this.rotation = Rotation.LEFT;
            }
        } else if (this.rotate == 180) {
            if (this.type.equals(Type.I)) {
                this.rotation = Rotation.UP_DOWN;
            }
            if (this.type.equals(Type.L)) {
                this.rotation = Rotation.RIGHT_DOWN;
            }
            if (this.type.equals(Type.START) || this.type.equals(Type.END)) {
                this.rotation = Rotation.UP;
            }
        } else if (this.rotate == 270) {
            if (this.type.equals(Type.I)) {
                this.rotation = Rotation.LEFT_RIGHT;
            }
            if (this.type.equals(Type.L)) {
                this.rotation = Rotation.LEFT_DOWN;
            }
            if (this.type.equals(Type.START) || this.type.equals(Type.END)) {
                this.rotation = Rotation.RIGHT;
            }
        }

    }


    private void changeAccessibleNeighbors() {
        accessibleNeighbors.clear();
        if (this.type.equals(Type.I)) {
            if (this.rotation.equals(Rotation.LEFT_RIGHT)) {
                accessibleNeighbors.add(new Node(this.coordinates.getX()+ Direction.LEFT.getX(),this.coordinates.getY()+ Direction.LEFT.getY()));
                accessibleNeighbors.add(new Node(this.coordinates.getX()+ Direction.RIGHT.getX(),this.coordinates.getY()+ Direction.RIGHT.getY()));
            } else if (this.rotation.equals(Rotation.UP_DOWN)) {
                accessibleNeighbors.add(new Node(this.coordinates.getX()+ Direction.UP.getX(),this.coordinates.getY()+ Direction.UP.getY()));
                accessibleNeighbors.add(new Node(this.coordinates.getX()+ Direction.DOWN.getX(),this.coordinates.getY()+ Direction.DOWN.getY()));
            }
        }
        if (this.type.equals(Type.L)) {
            if (this.rotation.equals(Rotation.LEFT_DOWN)) {
                accessibleNeighbors.add(new Node(this.coordinates.getX()+ Direction.LEFT.getX(),this.coordinates.getY()+ Direction.LEFT.getY()));
                accessibleNeighbors.add(new Node(this.coordinates.getX()+ Direction.DOWN.getX(),this.coordinates.getY()+ Direction.DOWN.getY()));
            } else if (this.rotation.equals(Rotation.LEFT_UP)) {
                accessibleNeighbors.add(new Node(this.coordinates.getX()+ Direction.LEFT.getX(),this.coordinates.getY()+ Direction.LEFT.getY()));
                accessibleNeighbors.add(new Node(this.coordinates.getX()+ Direction.UP.getX(),this.coordinates.getY()+ Direction.UP.getY()));
            } else if (this.rotation.equals(Rotation.RIGHT_DOWN)) {
                accessibleNeighbors.add(new Node(this.coordinates.getX()+ Direction.RIGHT.getX(),this.coordinates.getY()+ Direction.RIGHT.getY()));
                accessibleNeighbors.add(new Node(this.coordinates.getX()+ Direction.DOWN.getX(),this.coordinates.getY()+ Direction.DOWN.getY()));
            } else if (this.rotation.equals(Rotation.RIGHT_UP)) {
                accessibleNeighbors.add(new Node(this.coordinates.getX()+ Direction.RIGHT.getX(),this.coordinates.getY()+ Direction.RIGHT.getY()));
                accessibleNeighbors.add(new Node(this.coordinates.getX()+ Direction.UP.getX(),this.coordinates.getY()+ Direction.UP.getY()));
            }
        }
        if (this.type.equals(Type.START) || this.type.equals(Type.END)) {
            if (this.rotation.equals(Rotation.LEFT)) {
                accessibleNeighbors.add(new Node(this.coordinates.getX()+ Direction.LEFT.getX(),this.coordinates.getY()+ Direction.LEFT.getY()));
            } else if (this.rotation.equals(Rotation.UP)) {
                accessibleNeighbors.add(new Node(this.coordinates.getX()+ Direction.UP.getX(),this.coordinates.getY()+ Direction.UP.getY()));
            } else if (this.rotation.equals(Rotation.RIGHT)) {
                accessibleNeighbors.add(new Node(this.coordinates.getX()+ Direction.RIGHT.getX(),this.coordinates.getY()+ Direction.RIGHT.getY()));
            } else if (this.rotation.equals(Rotation.DOWN)) {
                accessibleNeighbors.add(new Node(this.coordinates.getX()+ Direction.DOWN.getX(),this.coordinates.getY()+ Direction.DOWN.getY()));
            }

        }
    }

}
