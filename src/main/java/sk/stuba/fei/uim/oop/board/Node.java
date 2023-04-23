package sk.stuba.fei.uim.oop.board;

import lombok.Getter;
import lombok.Setter;

public class Node {
    @Setter
    @Getter
    private int x;
    @Setter
    @Getter
    private int y;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return x == node.x && y == node.y;
    }

}