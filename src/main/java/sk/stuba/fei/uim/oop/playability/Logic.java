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

public class Logic extends UniversalAdapter {
    public static final int STARTING_SIZE = 8;

    private int levelNumber;

    private final JFrame game;

    private Board currentBoard;
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
    this.game.add(this.currentBoard);
    this.levelLabel = new JLabel();
    this.sizeLabel = new JLabel();
    this.updateBoardSizeLabel();
    this.updateLevelLabel();
    }

    private void createNewBoard(int dimension) {
        this.currentBoard = new Board(dimension);
        this.currentBoard.addMouseMotionListener(this);
        this.currentBoard.addMouseListener(this);
        this.currentBoard.revalidate();
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
        this.levelNumber = 1;
        this.game.remove(this.currentBoard);
        this.createNewBoard(this.currentSize);
        this.game.add(this.currentBoard);
        this.updateLevelLabel();
        this.currentBoard.repaint();
        this.currentBoard.revalidate();
    }

    private void  win(){
        if(this.currentBoard.checkWin(currentBoard)){
            this.levelNumber++;
            this.game.remove(this.currentBoard);
            this.createNewBoard(this.currentSize);
            this.game.add(this.currentBoard);
            this.updateLevelLabel();
            this.currentBoard.repaint();
            this.currentBoard.revalidate();
        }


    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_R:
                this.restart();
                break;
            case KeyEvent.VK_ESCAPE:
                this.game.dispose();
                break;
            case KeyEvent.VK_ENTER:
                this.win();

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
                this.win();
                break;
            default:
                break;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Component current = this.currentBoard.getComponentAt(e.getX(), e.getY());
        if (!(current instanceof Tile && !(((Tile) current).getType().equals(Type.EMPTY)))) {
            return;
        }
        ((Tile) current).setClicked(true);

        this.currentBoard.repaint();
    }
    @Override
    public void mouseMoved(MouseEvent e) {
        Component current = this.currentBoard.getComponentAt(e.getX(), e.getY());
        if (!(current instanceof Tile)) {
            this.currentBoard.repaint();
            return;
        }
            ((Tile) current).setHighlight(true);
       this.currentBoard.repaint();
    }
}
