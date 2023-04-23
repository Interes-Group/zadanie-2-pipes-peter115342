package sk.stuba.fei.uim.oop.board;

import lombok.Getter;
import sk.stuba.fei.uim.oop.tiles.Tile;
import sk.stuba.fei.uim.oop.tiles.Type;
import sk.stuba.fei.uim.oop.utility.DfsPath;
import sk.stuba.fei.uim.oop.utility.Node;

import javax.swing.*;
import java.awt.*;
import java.util.List;


public class Board extends JPanel {
    @Getter
    private Tile[][] board;
    @Getter
    private boolean winFlag;
    @Getter
    private Node start;
    @Getter
    private Node end;
    @Getter
    private final int boardSize;

    public Board(int size) {
        this.initializeBoard(size);
        this.setBorder(BorderFactory.createEmptyBorder(88, 88, 88, 88));
        this.setBackground(new Color(150, 150, 150));
        winFlag = false;
        this.boardSize = size;

    }

    private void initializeBoard(int dimension) {
        this.board = new Tile[dimension][dimension];
        this.setLayout(new GridLayout(dimension, dimension));
        for (int j = 0; j < dimension; j++) {
            for (int i = 0; i < dimension; i++) {
                this.board[i][j] = new Tile(i, j);
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


}