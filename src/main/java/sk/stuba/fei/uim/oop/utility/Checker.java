package sk.stuba.fei.uim.oop.utility;

import sk.stuba.fei.uim.oop.board.Board;
import sk.stuba.fei.uim.oop.tiles.Tile;
import sk.stuba.fei.uim.oop.tiles.Type;

import java.util.ArrayList;
import java.util.List;


public class Checker {
    private final Board board;

    public Checker(Board board) {
        this.board = board;
    }

    /* The check  rarely doesn't complete the whole path, and you need to rotate a few pipes around the point where it stopped to get it to
    work properly. Or we can consider this as a feature as the pipes might be clogged, and you need to wiggle them around for a bit to get the water flowing ;)
     */

    public List<Tile> winPath() {
        List<Tile> correctPath = new ArrayList<>();
        Tile currentTile = board.getBoard()[board.getStart().getX()][board.getStart().getY()];
        correctPath.add(currentTile);
        Tile previousTile;
        Tile nextTile;
        for (int i = 0; i < (board.getBoardSize() * board.getBoardSize()); i++) {
            if (currentTile.getType().equals(Type.END)) {
                correctPath.add(currentTile);
                return correctPath;
            }
            if (checkOutOfBounds(currentTile, board.getBoardSize())) {
                if (currentTile.getType().equals(Type.START)) {
                    if (checkEmpty(currentTile)) {
                        previousTile = currentTile;
                        board.getBoard()[currentTile.getCoordinates().getX()][currentTile.getCoordinates().getY()].setVisited(true);
                        currentTile = board.getBoard()[previousTile.getAccessibleNeighbors().get(0).getX()][previousTile.getAccessibleNeighbors().get(0).getY()];
                        if (checkOutOfBounds(currentTile, board.getBoardSize())) {
                            if (checkEmpty(currentTile)) {
                                board.getBoard()[currentTile.getCoordinates().getX()][currentTile.getCoordinates().getY()].setVisited(true);
                                nextTile = checkNext(currentTile);
                                if (checkNeighbors(previousTile, currentTile, nextTile)) {
                                    if (currentTile.getType().equals(Type.END)) {
                                        correctPath.add(currentTile);
                                        resetVisited(board.getBoardSize());
                                        return correctPath;
                                    }
                                    correctPath.add(currentTile);
                                } else {
                                    resetVisited(board.getBoardSize());
                                    return correctPath;
                                }
                            } else {
                                resetVisited(board.getBoardSize());

                                return correctPath;
                            }
                        } else {
                            resetVisited(board.getBoardSize());

                            return correctPath;
                        }
                    } else {
                        resetVisited(board.getBoardSize());

                        return correctPath;
                    }
                } else {
                    if (checkEmpty(currentTile)) {
                        previousTile = currentTile;
                        board.getBoard()[currentTile.getCoordinates().getX()][currentTile.getCoordinates().getY()].setVisited(true);
                        currentTile = checkVisited(previousTile, currentTile.getAccessibleNeighbors());
                        if (currentTile.getType().equals(Type.END)) {
                            if (checkNeighborsEnd(previousTile, currentTile)) {
                                correctPath.add(currentTile);
                                resetVisited(board.getBoardSize());
                                return correctPath;
                            }
                            resetVisited(board.getBoardSize());
                            return correctPath;
                        }
                        if (checkOutOfBounds(currentTile, board.getBoardSize())) {
                            if (checkEmpty(currentTile)) {
                                board.getBoard()[currentTile.getCoordinates().getX()][currentTile.getCoordinates().getY()].setVisited(true);
                                if (!board.getBoard()[currentTile.getAccessibleNeighbors().get(0).getX()][currentTile.getAccessibleNeighbors().get(0).getY()].getType().equals(Type.END) && !board.getBoard()[currentTile.getAccessibleNeighbors().get(1).getX()][currentTile.getAccessibleNeighbors().get(1).getY()].getType().equals(Type.END)) {
                                    nextTile = checkNext(currentTile);
                                } else {
                                    nextTile = board.getBoard()[currentTile.getAccessibleNeighbors().get(0).getX()][currentTile.getAccessibleNeighbors().get(0).getY()];
                                }
                                if (checkNeighbors(previousTile, currentTile, nextTile)) {
                                    correctPath.add(currentTile);
                                } else {
                                    resetVisited(board.getBoardSize());
                                    return correctPath;
                                }
                            } else {
                                resetVisited(board.getBoardSize());
                                return correctPath;
                            }
                        } else {
                            resetVisited(board.getBoardSize());
                            return correctPath;
                        }
                    }
                }
            } else {
                resetVisited(board.getBoardSize());
                return correctPath;
            }
        }
        resetVisited(board.getBoardSize());
        return correctPath;
    }

    private Tile checkNext(Tile currentTile) {
        Tile nextTile;
        nextTile = checkVisited(currentTile, currentTile.getAccessibleNeighbors());
        return nextTile;
    }

    private Tile checkVisited(Tile currentTile, List<Node> accessibleNeighbors) {
        Tile nextTile;
        if (board.getBoard()[currentTile.getAccessibleNeighbors().get(0).getX()][currentTile.getAccessibleNeighbors().get(0).getY()].getVisited()) {
            nextTile = board.getBoard()[accessibleNeighbors.get(1).getX()][accessibleNeighbors.get(1).getY()];
        } else {
            nextTile = board.getBoard()[accessibleNeighbors.get(0).getX()][accessibleNeighbors.get(0).getY()];
        }
        return nextTile;
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
            if (!board.getBoard()[currentTile.getAccessibleNeighbors().get(i).getX()][currentTile.getAccessibleNeighbors().get(i).getY()].getType().equals(Type.EMPTY)) {
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
        Node previousCoordinates = previous.getCoordinates();
        Node nextCoordinates = next.getCoordinates();


        if (previousAccessNeighbors.contains(currentCoordinates)) {
            if (currentAccessibleNeighbors.contains(previousCoordinates) && currentAccessibleNeighbors.contains(nextCoordinates)) {
                if (next.getType().equals(Type.END)) {
                    return previousAccessNeighbors.contains(nextAccessNeighbors.get(0));
                }
                return previousAccessNeighbors.contains(nextAccessNeighbors.get(0)) || previousAccessNeighbors.contains(nextAccessNeighbors.get(1));
            }
        }
        return false;
    }

    public boolean checkNeighborsEnd(Tile previous, Tile current) {
        List<Node> previousAccessNeighbors = previous.getAccessibleNeighbors();
        List<Node> currentAccessibleNeighbors = current.getAccessibleNeighbors();
        Node currentCoordinates = current.getCoordinates();
        Node previousCoordinates = previous.getCoordinates();


        if (previousAccessNeighbors.contains(currentCoordinates)) {
            return currentAccessibleNeighbors.contains(previousCoordinates);
        }
        return false;
    }


    private void resetVisited(int dimension) {
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                board.getBoard()[i][j].setVisited(false);
            }
        }
    }
}
