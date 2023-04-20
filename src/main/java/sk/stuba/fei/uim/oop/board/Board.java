package sk.stuba.fei.uim.oop.board;

import sk.stuba.fei.uim.oop.tiles.Tile;
import sk.stuba.fei.uim.oop.tiles.Type;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;


public class Board extends JPanel {
    private Tile[][] board;
    private final Random rand = new Random();

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
            board[endX][endY].setType(Type.END);
        }
        else{
            startX = rand.nextInt(dimension);
            board[startX][startY].setType(Type.START);
            endX =rand.nextInt(dimension);
            board[endX][endY].setType(Type.END);
        }
        DfsRandomPath pathFinder = new DfsRandomPath(dimension,dimension,startX,startY,endX,endY);
        List<int[]> path = pathFinder.findPath();
        path.forEach(pth -> System.out.println(Arrays.toString(pth)));


        for (int i = 0; i < path.size(); i++) {
            int x = path.get(i)[0];
            int y = path.get(i)[1];
              if ((i > 1  && i < path.size()-1)) {
                int nextX = path.get(i + 1)[0];
                int nextY = path.get(i + 1)[1];
                  int prevX = path.get(i - 1)[0];
                  int prevY = path.get(i -1)[1];
                  //System.out.println("Previous: "+prevX+", "+ prevY+" | Path: "+x +", "+ y+" | Next: "+ nextX+", "+ nextY);
                if ((prevX == nextX || prevY == nextY)) {
                    board[x][y].setType(Type.I);
                }
                else {
                    board[x][y].setType(Type.L);

                }
            } else {
                  board[x][y].setType(Type.L);
              }
        }
        board[startX][startY].setType(Type.START);
        board[endX][endY].setType(Type.END);

    }
}
