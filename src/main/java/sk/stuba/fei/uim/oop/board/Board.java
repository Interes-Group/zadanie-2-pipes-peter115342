package sk.stuba.fei.uim.oop.board;

import sk.stuba.fei.uim.oop.tiles.Tile;
import sk.stuba.fei.uim.oop.tiles.Type;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.MouseEvent;


public class Board extends JPanel {
    private Tile[][] board;
    private Random rand = new Random();

    public Board(int size) {
        this.initializeBoard(size);
        this.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        this.setBackground(new Color(150, 150, 150));

    }

    public void generatePath() {

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
        int startX = 0;
        int startY = rand.nextInt(dimension);
        int[][] tempArray = new int[dimension][dimension];
        board[startX][startY].setState(Type.START);
        int endX = dimension - 1;
        int endY = rand.nextInt(dimension);
        board[endX][endY].setState(Type.FINISH);

        Stack<Integer> stack = new Stack<>();
        stack.push(startX);
        stack.push(startY);

        // DFS algorithm to generate random path
        while (!stack.isEmpty()) {
            int currentY = stack.pop();
            int currentX = stack.pop();

            // check if we have reached the end point
            if (currentX == endX && currentY == endY) {
                break;
            }

            // check unvisited neighboring positions
            if (currentX > 0 && tempArray[currentX - 1][currentY] == 0) {
                // move up
                tempArray[currentX - 1][currentY] = 1;
                stack.push(currentX - 1);
                stack.push(currentY);
            }
            if (currentY < dimension - 1 && tempArray[currentX][currentY + 1] == 0) {
                // move right
                tempArray[currentX][currentY + 1] = 1;
                stack.push(currentX);
                stack.push(currentY + 1);
            }
            if (currentX < dimension - 1 && tempArray[currentX + 1][currentY] == 0) {
                // move down
                tempArray[currentX + 1][currentY] = 1;
                stack.push(currentX + 1);
                stack.push(currentY);
            }
            if (currentY > 0 && tempArray[currentX][currentY - 1] == 0) {
                // move left
                tempArray[currentX][currentY - 1] = 1;
                stack.push(currentX);
                stack.push(currentY - 1);
            }
        }

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (i == startX && j == startY) {
                    board[i][j].setState(Type.START);
                } else if (i == endX && j == endY) {
                    board[i][j].setState(Type.FINISH);
                } else if (tempArray[i][j] == 1) {
                    if((j > 1 && i  < dimension-1) &&(i > 1 && j  < dimension-1) ) {
                            if (tempArray[i + 1][j - 1] == 1 || tempArray[i-1][j + 1] == 1 ) {
                            board[i][j].setState(Type.L);
                        }

                    }
                    else if(j == 0 && i == dimension - 1 ){
                        if (tempArray[0][j + 1] == 1) {
                            board[i][j].setState(Type.L);
                        }
                    }
                    else if( i == 0 && j ==dimension-1){
                        if(tempArray[i+1][0] == 1){
                            board[i][j].setState(Type.L);

                        }
                    }
                    else {
                        board[i][j].setState(Type.I);
                    }
                }  else {
                    board[i][j].setState(Type.EMPTY);
                }
            }

        }


    }


}
