package sk.stuba.fei.uim.oop.board;

import lombok.Getter;
import sk.stuba.fei.uim.oop.tiles.Tile;
import sk.stuba.fei.uim.oop.tiles.Type;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
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

    private int size;

    public Board(int size) {
        this.initializeBoard(size);
        this.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        this.setBackground(new Color(150, 150, 150));
        winFlag = false;
        this.size = size;

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


    public List<Tile> winPath() {
        List<Tile> correctPath = new ArrayList<>();
        Tile currentTile = board[start.getX()][start.getY()];
        correctPath.add(currentTile);
        Tile previousTile = null;
        Tile nextTile = null;
        int neighborIndex = 0;
        while (true) {
            neighborIndex = 0;
            if (currentTile.getType().equals(Type.END)) {
                correctPath.add(currentTile);
                return correctPath;
            }
            if (checkOutOfBounds(currentTile, this.size)) {
                if (currentTile.getType().equals(Type.START)) {
                    if (checkEmpty(currentTile)) {
                        previousTile = currentTile;
                        board[currentTile.getCoordinates().getX()][currentTile.getCoordinates().getY()].setVisited(true);
                        currentTile = board[previousTile.getAccessibleNeighbors().get(neighborIndex).getX()][previousTile.getAccessibleNeighbors().get(neighborIndex).getY()];
                        if (checkOutOfBounds(currentTile, this.size)){
                            if (checkEmpty(currentTile)) {
                                board[currentTile.getCoordinates().getX()][currentTile.getCoordinates().getY()].setVisited(true);
                                if ( board[currentTile.getAccessibleNeighbors().get(0).getX()][currentTile.getAccessibleNeighbors().get(0).getY()].getVisited() ) {
                                    nextTile = board[currentTile.getAccessibleNeighbors().get(1).getX()][currentTile.getAccessibleNeighbors().get(1).getY()];
                                }
                                else {
                                    nextTile = board[currentTile.getAccessibleNeighbors().get(0).getX()][currentTile.getAccessibleNeighbors().get(0).getY()];
                                }
                                if (checkNeighbors(previousTile, currentTile, nextTile)) {
                                    correctPath.add(currentTile);
                                } else {
                                    resetVisited(correctPath);

                                    return correctPath;
                                }
                            } else {
                                resetVisited(correctPath);

                                return correctPath;
                            }
                        }
                        else{
                            resetVisited(correctPath);

                            return correctPath;
                        }

                    } else {
                        resetVisited(correctPath);

                        return correctPath;
                    }
                }  else {
                    if (checkEmpty(currentTile)) {
                        previousTile = currentTile;
                        board[currentTile.getCoordinates().getX()][currentTile.getCoordinates().getY()].setVisited(true);
                        if ( board[previousTile.getAccessibleNeighbors().get(neighborIndex).getX()][previousTile.getAccessibleNeighbors().get(neighborIndex).getY()].getVisited() ) {
                            neighborIndex = 1;
                        }
                        currentTile = board[currentTile.getAccessibleNeighbors().get(neighborIndex).getX()][currentTile.getAccessibleNeighbors().get(neighborIndex).getY()];
                        if (checkOutOfBounds(currentTile, this.size)){
                            if (checkEmpty(currentTile)) {
                                board[currentTile.getCoordinates().getX()][currentTile.getCoordinates().getY()].setVisited(true);
                                if ( board[currentTile.getAccessibleNeighbors().get(0).getX()][currentTile.getAccessibleNeighbors().get(0).getY()].getVisited() ) {
                                    if ( board[currentTile.getAccessibleNeighbors().get(1).getX()][currentTile.getAccessibleNeighbors().get(1).getY()].getType().equals(Type.END)){
                                        correctPath.add(board[currentTile.getAccessibleNeighbors().get(1).getX()][currentTile.getAccessibleNeighbors().get(1).getY()]);
                                        resetVisited(correctPath);
                                        return correctPath;
                                    }
                                    else {
                                        nextTile = board[currentTile.getAccessibleNeighbors().get(1).getX()][currentTile.getAccessibleNeighbors().get(1).getY()];
                                    }
                                }
                                else {
                                    nextTile = board[currentTile.getAccessibleNeighbors().get(0).getX()][currentTile.getAccessibleNeighbors().get(0).getY()];
                                }

                                if (checkNeighbors(previousTile, currentTile, nextTile)) {
                                    correctPath.add(currentTile);
                                } else {
                                    resetVisited(correctPath);
                                    return correctPath;
                                }
                            } else {
                                resetVisited(correctPath);

                                return correctPath;
                            }
                        }
                        else{
                            resetVisited(correctPath);

                            return correctPath;
                        }

                    }
                }

            } else {
                resetVisited(correctPath);
                return correctPath;
            }
        }

    }


    private boolean checkOutOfBounds(Tile currentTile, int dimension) {
        int counter = 0;
        for (int i = 0; i < currentTile.getAccessibleNeighbors().size(); i++) {
            if (currentTile.getAccessibleNeighbors().get(i).getX() >= 0 &&
                    currentTile.getAccessibleNeighbors().get(i).getX() < dimension &&
                    currentTile.getAccessibleNeighbors().get(i).getY() >= 0 &&
                    currentTile.getAccessibleNeighbors().get(i).getY() < dimension) {
                counter++;
            }

        }
        return counter == currentTile.getAccessibleNeighbors().size();
    }

    private boolean checkEmpty(Tile currentTile) {
        int counter = 0;

        for (int i = 0; i < currentTile.getAccessibleNeighbors().size(); i++) {
            if (!board[currentTile.getAccessibleNeighbors().get(i).getX()][currentTile.getAccessibleNeighbors().get(i).getY()].getType().equals(Type.EMPTY)) {
                counter++;
            }
        }
        return counter == currentTile.getAccessibleNeighbors().size();
    }

    public boolean checkNeighbors(Tile previous, Tile current, Tile next) {
        List<Node> previousAccessNeighbors = previous.getAccessibleNeighbors();
        List<Node> currentAccessibleNeighbors = current.getAccessibleNeighbors();
        List<Node> nextAccessNeighbors = next.getAccessibleNeighbors();
        Node currentCoordinates = current.getCoordinates();
        Node  previousCoordinates = previous.getCoordinates();
        Node nextCoordinates = next.getCoordinates();


        if (previousAccessNeighbors.contains(currentCoordinates) ) {
            if (currentAccessibleNeighbors.contains(previousCoordinates) && currentAccessibleNeighbors.contains(nextCoordinates)) {
                return previousAccessNeighbors.contains(nextAccessNeighbors.get(0)) || previousAccessNeighbors.contains(nextAccessNeighbors.get(1));
            }
        }
        return false;
    }


    private void resetVisited(List<Tile> correctPath){
        for (Tile tile : correctPath){
            board[tile.getCoordinates().getX()][tile.getCoordinates().getY()].setVisited(false);
        }
    }

}