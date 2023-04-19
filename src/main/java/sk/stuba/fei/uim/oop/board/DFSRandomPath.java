package sk.stuba.fei.uim.oop.board;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class DFSRandomPath {
    private boolean[][] visited;
    private int startRow, startCol, endRow, endCol;
    private int numRows, numCols;
    private Random random;


    public DFSRandomPath(int numRows, int numCols, int startRow, int startCol, int endRow, int endCol) {
        this.startRow = startRow;
        this.startCol = startCol;
        this.endRow = endRow;
        this.endCol = endCol;
        this.numRows = numRows;
        this.numCols = numCols;
        visited = new boolean[numRows][numCols];
        random = new Random();
    }

    public List<int[]> findPath() {
        List<int[]> path = new ArrayList<>();
        dfs(startRow, startCol, path);
        return path;
    }

    private boolean dfs(int row, int col, List<int[]> path) {
        visited[row][col] = true;
        path.add(new int[]{row, col});

        if (row == endRow && col == endCol) {
            return true;
        }

        List<int[]> neighbors = getUnvisitedNeighbors(row, col);

        if (neighbors.isEmpty()) {
            path.remove(path.size() - 1);
            return false;
        }

        Collections.shuffle(neighbors, random);

        neighbors.sort((n1, n2) -> {
            int dist1 = Math.abs(n1[0] - endRow) + Math.abs(n1[1] - endCol);
            int dist2 = Math.abs(n2[0] - endRow) + Math.abs(n2[1] - endCol);
            Collections.shuffle(neighbors, random);
            return Integer.compare(dist1, dist2);
        });

        for (int[] neighbor : neighbors) {
            boolean foundEnd = dfs(neighbor[0], neighbor[1], path);
            if (foundEnd) {
                return true;
            }
        }

        path.remove(path.size() - 1);
        return false;
    }

    private List<int[]> getUnvisitedNeighbors(int row, int col) {
        List<int[]> neighbors = new ArrayList<>();


        for( Direction dir : Direction.values()){
            int neighborRow = row + dir.getX();
            int neighborCol = col + dir.getY();

            if (neighborRow < 0 || neighborRow >= numRows || neighborCol < 0 || neighborCol >= numCols) {
                continue;
            }

            if (!visited[neighborRow][neighborCol]) {
                neighbors.add(new int[]{neighborRow, neighborCol});
            }
        }

        Collections.shuffle(neighbors, random);
        int numNeighbors = Math.min(3, neighbors.size());
        return neighbors.subList(0, numNeighbors);
    }
}
