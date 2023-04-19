package sk.stuba.fei.uim.oop.board;

import sk.stuba.fei.uim.oop.tiles.Tile;
import sk.stuba.fei.uim.oop.tiles.Type;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;


public class Board extends JPanel {
    private Tile[][] board;
    private Random rand = new Random();

    public Board(int size) {
        this.initializeBoard(size);
        this.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        this.setBackground(new Color(150, 150, 150));

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
        int startY = 0;
        int endX = dimension-1;
        int endY = dimension-1;
        if(rand.nextInt(2)==0){
            startY =rand.nextInt(dimension);
            board[startX][startY].setType(Type.START);
            endY = rand.nextInt(dimension);
            board[endX][endY].setType(Type.FINISH);
        }
        else{
            startX = rand.nextInt(dimension);
            board[startX][startY].setType(Type.START);
            endX =rand.nextInt(dimension);
            board[endX][endY].setType(Type.FINISH);
        }
        DFSRandomPath pathFinder = new DFSRandomPath(dimension,dimension,startX,startY,endX,endY);
        List<int[]> path = pathFinder.findPath();
        int[][] tempArray = new int[dimension][dimension];

        path.forEach(pth ->{
            int x = pth[0];
            int y = pth[1];
            tempArray[x][y] = 2;
          //  if (!board[x][y].getState().equals(Type.START) && !board[x][y].getState().equals(Type.FINISH) ){
               // board[x][y].setState(Type.I);// }
        } );

        for (int i = 0; i    < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (i == startX && j == startY) {
                    board[i][j].setType(Type.START);
                } else if (i == endX && j == endY) {
                    board[i][j].setType(Type.FINISH);
                } else if (tempArray[i][j] == 2 && !board[i][j].getType().equals(Type.START) && !board[i][j].getType().equals(Type.FINISH)) {

                    if ((!findNeighborsX(tempArray, i, j, dimension)) && (findNeighborsY(tempArray, i, j, dimension)) || (findNeighborsX(tempArray, i, j, dimension)) && (!findNeighborsY(tempArray, i, j, dimension))) {
                        board[i][j].setType(Type.I);

                    } else {
                        board[i][j].setType(Type.L);

                    }

                } else {
                    board[i][j].setType(Type.EMPTY);
                }
            }
        }
   }
    private boolean findNeighborsX(int[][] array,int x,int y,int dimension){
        if( x  > 1 && x < dimension-1) {
            return array[x + 1][y] == 2 && array[x - 1][y] == 2;
        }
        return  false;
    }
    private boolean findNeighborsY(int[][] array,int x,int y,int dimension){
        if( y  > 1 && y < dimension-1) {
            return array[x][y + 1] == 2 && array[x][y - 1] == 2;
        }
        return false;
    }

}
