package sk.stuba.fei.uim.oop.playability;

import lombok.Getter;
import sk.stuba.fei.uim.oop.board.Board;
import sk.stuba.fei.uim.oop.gui.Pipes;
import sk.stuba.fei.uim.oop.tiles.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import java.util.Stack;

public class Logic extends UniversalAdapter {
    public static final int STARTING_SIZE = 8;

    private int levelNumber;

    private JFrame game;

    private Board currBoard;
    @Getter
    private JLabel levelLabel;
    @Getter
    private  JLabel sizeLabel;

    private int currentSize;

    private Random rand = new Random();

    public Logic(JFrame game){
    this.game = game;
    this.currentSize = STARTING_SIZE;
    this.levelNumber = 1;
    this.createNewBoard(this.currentSize);
    this.game.add(this.currBoard);
    this.levelLabel = new JLabel();
    this.sizeLabel = new JLabel();
    this.updateBoardSizeLabel();
    this.updateLevelLabel();
    }

    private void createNewBoard(int dimension) {
        this.currBoard = new Board(dimension);
        this.currBoard.addMouseMotionListener(this);
        this.currBoard.addMouseListener(this);
    }

    private void updateBoardSizeLabel() {
        this.sizeLabel.setText("SIZE: " + this.currentSize);
        this.game.revalidate();
        this.game.repaint();
    }
    private void updateLevelLabel() {
        this.levelLabel.setText("LEVEL: " + this.levelNumber);
         this.game.revalidate();
         this.game.repaint();
    }

    private void restart(){
        this.game.remove(this.currBoard);
        this.createNewBoard(this.currentSize);
        this.game.add(this.currBoard);
        this.updateLevelLabel();
        this.currBoard.repaint();
        this.currBoard.revalidate();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println(e);
        switch (e.getKeyCode()) {
            case KeyEvent.VK_R:
                this.restart();
                break;
            case KeyEvent.VK_ESCAPE:
                this.game.dispose();
        }
    }
    @Override
    public void stateChanged(ChangeEvent e) {
        this.currentSize = ((JSlider) e.getSource()).getValue();
        this.updateBoardSizeLabel();
        this.restart();
        this.game.setFocusable(true);
        this.game.requestFocus();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case(Pipes.RESET_BUTTON_NAME):
                this.restart();
                break;
            case(Pipes.CHECK_BUTTON_NAME):
                //TODO
                break;

            default:
                break;
        }



    }

    @Override
    public void mousePressed(MouseEvent e) {
        Component current = this.currBoard.getComponentAt(e.getX(), e.getY());
        if (!(current instanceof Tile && !(((Tile) current).getState().equals(Type.EMPTY)))) {
            return;
        }
        ((Tile) current).setClicked(true);

        this.currBoard.repaint();
    }
    @Override
    public void mouseMoved(MouseEvent e) {
        Component current = this.currBoard.getComponentAt(e.getX(), e.getY());
        if (!(current instanceof Tile)) {
            return;
        }

            ((Tile) current).setHighlight(true);
       this.currBoard.repaint();
    }




}
