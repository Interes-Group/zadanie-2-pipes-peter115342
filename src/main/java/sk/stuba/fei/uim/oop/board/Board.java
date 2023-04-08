package sk.stuba.fei.uim.oop.board;

import sk.stuba.fei.uim.oop.tiles.Tile;

import javax.swing.*;
import java.awt.*;

public class Board extends JPanel {
    private Tile[][] board;

    public Board(int size){
        this.initializeBoard(size);
        this.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        this.setBackground(new Color(150,150,150));



    }

    private void initializeBoard(int dimension) {
        this.board = new Tile[dimension][dimension];
        this.setLayout(new GridLayout(dimension, dimension));
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                this.board[i][j] = new Tile();
                this.add(this.board[i][j]);
            }
        }

    }

}
