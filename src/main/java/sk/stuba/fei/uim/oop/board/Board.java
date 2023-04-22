package sk.stuba.fei.uim.oop.board;

import lombok.Getter;
import sk.stuba.fei.uim.oop.tiles.Rotation;
import sk.stuba.fei.uim.oop.tiles.Tile;
import sk.stuba.fei.uim.oop.tiles.Type;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;


public class Board extends JPanel {
    private Tile[][] board;
    @Getter
    private boolean winFlag;
    @Getter
    private Node start;
    @Getter
    private Node end;

    public Board(int size) {
        this.initializeBoard(size);
        this.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        this.setBackground(new Color(150, 150, 150));
        winFlag = false;

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
        int endX = dimension - 1;
        int endY = dimension - 1;
        if (board[0][0].getRand().nextInt(2) == 0) {
            startY = board[0][0].getRand().nextInt(dimension);
            endY = board[0][0].getRand().nextInt(dimension);
            board[endX][endY].setType(Type.END);
        } else {
            startX = board[0][0].getRand().nextInt(dimension);
            board[startX][startY].setType(Type.START);
            endX = board[0][0].getRand().nextInt(dimension);
        }
        this.start = new Node(startX, startY);
        this.end = new Node(endX, endY);
        DfsPath pathFinder = new DfsPath(dimension, dimension, startX, startY, endX, endY);
        List<Node> path = pathFinder.findPath();

        for (int i = 0; i < path.size(); i++) {
            Node current = path.get(i);
            Node next = path.get(Math.min(i + 1, path.size() - 1));
            Node prev = path.get(Math.max(0, i - 1));
            if ((prev.getX() == next.getX() || prev.getY() == next.getY())) {
                board[current.getX()][current.getY()].setType(Type.I);
            } else {
                board[current.getX()][current.getY()].setType(Type.L);
            }
        }
        board[start.getX()][start.getY()].setType(Type.START);
        board[end.getX()][end.getY()].setType(Type.END);
    }


    public boolean checkWin(Board board) {
        List<Tile> correctPath = winPath(board.start);
        if (correctPath.get(correctPath.size() - 1).getType().equals(Type.END)) {
            return winFlag;
        } else {
            return false;
        }
    }

    public List<Tile> winPath(Node startingNode) {
        List<Tile> correctPath = new ArrayList<>();
        Tile currentTile = board[startingNode.getX()][startingNode.getY()];
        correctPath.add(currentTile);
        Tile previousTile = new Tile();
        Tile nextTile = new Tile();
        System.out.println(currentTile.getAccessibleNeighbors().get(0).getX()+" "+ currentTile.getAccessibleNeighbors().get(0).getY()); ;
        while (true) {
            if (currentTile.getType().equals(Type.END)) {
                return correctPath;
            }
            if (currentTile.getType().equals(Type.START)) {
                if(getTileCoordinates(currentTile).getX()+ currentTile.getAccessibleNeighbors().get(0).getX() >=  0 )
                if (!board[getTileCoordinates(currentTile).getX()+ currentTile.getAccessibleNeighbors().get(0).getX()][getTileCoordinates(currentTile).getY()+currentTile.getAccessibleNeighbors().get(0).getY()].getType().equals(Type.EMPTY)) {
                        currentTile = board[currentTile.getAccessibleNeighbors().get(0).getX()][currentTile.getAccessibleNeighbors().get(0).getY()];
                        previousTile = currentTile;

                } else {
                    return correctPath;
                }
            }
            else if (board[currentTile.getAccessibleNeighbors().get(0).getX()][currentTile.getAccessibleNeighbors().get(0).getY()].getType().equals(Type.EMPTY)) {
                return correctPath;
            } else if (board[currentTile.getAccessibleNeighbors().get(1).getX()][currentTile.getAccessibleNeighbors().get(1).getY()].getType().equals(Type.EMPTY)) {
                return correctPath;
            } else {
                if (1==1) {
                    currentTile = board[currentTile.getAccessibleNeighbors().get(0).getX()][currentTile.getAccessibleNeighbors().get(0).getY()];
                    previousTile = currentTile;
                } else {
                    return correctPath;
                }
            }

        }

    }

    public Node getTileCoordinates(Tile tile) {

        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board[i].length; j++) {
                if (this.board[i][j] == tile) {
                    return new Node(i, j);
                }
            }
        }
        return null;
    }

}