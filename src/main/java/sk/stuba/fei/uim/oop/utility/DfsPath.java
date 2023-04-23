package sk.stuba.fei.uim.oop.utility;

import sk.stuba.fei.uim.oop.board.Direction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class DfsPath {
    private final boolean[][] visited;
    private final int startRow;
    private final int startCol;
    private final int endRow;
    private final int endCol;
    private final int numRows;
    private final int numCols;
    private final Random random;


    public DfsPath(int numRows, int numCols, int startRow, int startCol, int endRow, int endCol) {
        this.startRow = startRow;
        this.startCol = startCol;
        this.endRow = endRow;
        this.endCol = endCol;
        this.numRows = numRows;
        this.numCols = numCols;
        visited = new boolean[numRows][numCols];
        random = new Random();
    }

    public List<Node> findPath() {
        List<Node> path = new ArrayList<>();
        dfs(startRow, startCol, path);
        return path;
    }

    private boolean dfs(int row, int col, List<Node> path) {
        visited[row][col] = true;
        path.add(new Node(row, col));

        if (row == endRow && col == endCol) {
            return true;
        }

        List<Node> neighbors = getUnvisitedNeighbors(row, col);

        if (neighbors.isEmpty()) {
            path.remove(path.size() - 1);
            return false;
        }

        neighbors.sort((n1, n2) -> {
            int dist1 = Math.abs(n1.getX() - endRow) + Math.abs(n1.getY() - endCol);
            int dist2 = Math.abs(n2.getX() - endRow) + Math.abs(n2.getY() - endCol);
            return Integer.compare(dist1, dist2);
        });
        Collections.shuffle(neighbors, random);


        for (Node neighbor : neighbors) {
            boolean foundEnd = dfs(neighbor.getX(), neighbor.getY(), path);
            if (foundEnd) {
                return true;
            }
        }

        path.remove(path.size() - 1);
        return false;
    }

    private List<Node> getUnvisitedNeighbors(int x, int y) {
        List<Node> neighbors = new ArrayList<>();

        for (Direction dir : Direction.values()) {
            int neighborX = x + dir.getX();
            int neighborY = y + dir.getY();

            if (neighborX < 0 || neighborX >= numRows || neighborY < 0 || neighborY >= numCols) {
                continue;
            }

            if (!visited[neighborX][neighborY]) {
                neighbors.add(new Node(neighborX, neighborY));
            }
        }

        Collections.shuffle(neighbors, random);
        int numNeighbors = Math.min(3, neighbors.size());

        return neighbors.subList(0, numNeighbors);
    }
}