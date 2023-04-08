package sk.stuba.fei.uim.oop.playability;

import lombok.Getter;
import sk.stuba.fei.uim.oop.board.Board;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

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
        this.restart();
        this.game.revalidate();
        this.game.repaint();
        this.game.setFocusable(true);
        this.game.requestFocus();
    }



}
