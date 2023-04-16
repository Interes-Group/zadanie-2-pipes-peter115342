package sk.stuba.fei.uim.oop.board;

import sk.stuba.fei.uim.oop.tiles.Tile;
import sk.stuba.fei.uim.oop.tiles.Type;

import javax.swing.*;
import java.awt.*;
import java.util.*;


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

        int[][] tempArray = new int[dimension][dimension];
        int startX = 0;
        int startY = 0;
        int endX = dimension-1;
        int endY = dimension-1;
        if(rand.nextInt(2)==0){
            startY =rand.nextInt(dimension);
            board[startX][startY].setState(Type.START);
             endY = rand.nextInt(dimension);
            board[endX][endY].setState(Type.FINISH);
        }
        else{
            startX = rand.nextInt(dimension);
            board[startX][startY].setState(Type.START);
             endX =rand.nextInt(dimension);
            board[endX][endY].setState(Type.FINISH);
        }

        Stack<Integer> stack = new Stack<>();
        stack.push(startX);
        stack.push(startY);

        while (!stack.isEmpty()) {
            int currentY = stack.pop();
            int currentX = stack.pop();

            if (currentX == endX && currentY == endY) {
                break;
            }


            int[] neighbors = unvisitedNeighbors(tempArray,currentX, currentY,dimension );
            while (neighbors.length > 0) {
                int randomNeighbor = neighbors[rand.nextInt(neighbors.length)];
                int nextX = currentX + dx(randomNeighbor);
                int nextY = currentY + dy(randomNeighbor);

                tempArray[nextX][nextY] = 1;
                tempArray[currentX][currentY] = 2;
                stack.push(nextX);
                stack.push(nextY);

                if (nextX == endX && nextY == endY) {
                    break;
                }

                neighbors = unvisitedNeighbors(tempArray,currentX, currentY,dimension);
            }
        }
        tempArray[startX][startY] = 1;
        tempArray[endX][endY] = 1;

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (i == startX && j == startY) {
                    board[i][j].setState(Type.START);
                } else if (i == endX && j == endY) {
                    board[i][j].setState(Type.FINISH);
                } else if (tempArray[i][j] == 2) {/*
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
               */
                board[i][j].setState(Type.I);
                }  else {
                    board[i][j].setState(Type.EMPTY);
                }
            }

        }


    }
    private  int[] unvisitedNeighbors(int[][] array, int x, int y,int dimension) {
        int[] neighbors = new int[4];
        int count = 0;
        if (x > 1 && array[x-1][y] == 0) {
            neighbors[count++] = 0;
        }
        if (y < dimension-1 && array[x][y+1] == 0) {
            neighbors[count++] = 1;
        }
        if (x < dimension-1 && array[x+1][y] == 0) {
            neighbors[count++] = 2;
        }
        if (y > 1 && array[x][y-1] == 0) {
            neighbors[count++] = 3;
        }
        int[] result = new int[count];
        System.arraycopy(neighbors, 0, result, 0, count);
        return result;
    }
    private  int dx(int direction) {
        if (direction == 0) {
            return -1;
        } else if (direction == 2) {
            return 1;
        } else {
            return 0;
        }
    }

    private  int dy(int direction) {
        if (direction == 1) {
            return 1;
        } else if (direction == 3) {
            return -1;
        } else {
            return 0;
        }
    }
}
