/* *****************************************************************************
 *  Name: Rich Nakasato
 *  Date: 11/17/18
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Queue;

import java.util.Arrays;

public class Board {

    private static final int EMPTY_BLOCK = 0;

    private int[][] block;
    private int[][] twin;
    private int openRow;
    private int openCol;
    private final int dim;

    public Board(int[][] blocks)           // construct a board from an n-by-n array of blocks
    {
        dim = blocks.length;
        block = deepCopy(blocks);
        findOpen();
        twin = deepCopy(blocks);
        buildTwin();
    }

    private int[][] deepCopy(int[][] original) { // deep copy 2d array
        if (original == null) {
            return null;
        }
        int[][] result = new int[original.length][];
        for (int i = 0; i < original.length; ++i) {
            result[i] = Arrays.copyOf(original[i], original[i].length);
        }
        return result;
    }

    private void findOpen() {              // sets openRow and openCol
        for (int r = 0; r < dim; ++r) {
            for (int c = 0; c < dim; ++c) {
                if (block[r][c] == EMPTY_BLOCK) {
                    openRow = r;
                    openCol = c;
                    return;
                }
            }
        }
    }

    public int dimension()                 // board dimension n
    {
        return dim;
    }

    public int hamming()                   // number of blocks out of place
    {
        int expected = 1;
        int ham = 0;
        int max = dim * dim;
        for (int r = 0; r < dim; ++r) {
            for (int c = 0; c < dim; ++c) {
                if (expected < max && block[r][c] != expected) {
                    ++ham;
                }
                ++expected;
            }
        }
        return ham;
    }

    public int manhattan()                 // sum of Manhattan distances between blocks and goal
    {
        int man = 0;
        for (int r = 0; r < dim; ++r) {
            for (int c = 0; c < dim; ++c) {
                int cur = block[r][c];
                if (cur != EMPTY_BLOCK) {
                    --cur;
                    int expectedRow = cur / dim;
                    int expectedCol = cur % dim;
                    man += Math.abs(expectedRow - r) + Math.abs(expectedCol - c);
                }
            }
        }
        return man;
    }

    public boolean isGoal()                // is this board the goal board?
    {
        int expected = 1;
        int max = dim * dim;
        for (int r = 0; r < dim; ++r) {
            for (int c = 0; c < dim; ++c) {
                if (expected < max && block[r][c] != expected) {
                    return false;
                }
                ++expected;
            }
        }
        return true;
    }

    private void buildTwin() {              // does the "twin" work
        int cacheRow = -1;
        int cacheCol = -1;
        for (int r = 0; r < dim; ++r) {
            for (int c = 0; c < dim; ++c) {
                if (twin[r][c] != EMPTY_BLOCK) {
                    if (cacheRow < EMPTY_BLOCK) {
                        cacheRow = r;
                        cacheCol = c;
                    }
                    else {
                        int temp = twin[r][c];
                        twin[r][c] = twin[cacheRow][cacheCol];
                        twin[cacheRow][cacheCol] = temp;
                        return;
                    }
                }
            }
        }
    }

    public Board twin()                    // a board that is obtained by exchanging any pair of blocks
    {
        return new Board(twin);
    }

    public boolean equals(Object y)        // does this board equal y?
    {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        if (that.block.length != this.block.length) return false;
        if (that.block[0].length != this.block[0].length) return false;
        for (int r = 0; r < dim; ++r) {
            for (int c = 0; c < dim; ++c) {
                if (that.block[r][c] != this.block[r][c]) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isValid(int row, int col) {
        if (row < 0) return false;
        if (col < 0) return false;
        if (row >= dim) return false;
        if (col >= dim) return false;
        return true;
    }

    public Iterable<Board> neighbors()     // all neighboring boards
    {
        Queue<Board> nQueue = new Queue<>();
        if (isValid(openRow - 1, openCol)) {
            int[][] moveDown = deepCopy(block);
            moveDown[openRow][openCol] = moveDown[openRow - 1][openCol];
            moveDown[openRow - 1][openCol] = EMPTY_BLOCK;
            nQueue.enqueue(new Board(moveDown));
        }
        if (isValid(openRow, openCol + 1)) {
            int[][] moveLeft = deepCopy(block);
            moveLeft[openRow][openCol] = moveLeft[openRow][openCol + 1];
            moveLeft[openRow][openCol + 1] = EMPTY_BLOCK;
            nQueue.enqueue(new Board(moveLeft));
        }
        if (isValid(openRow + 1, openCol)) {
            int[][] moveUp = deepCopy(block);
            moveUp[openRow][openCol] = moveUp[openRow + 1][openCol];
            moveUp[openRow + 1][openCol] = EMPTY_BLOCK;
            nQueue.enqueue(new Board(moveUp));
        }
        if (isValid(openRow, openCol - 1)) {
            int[][] moveRight = deepCopy(block);
            moveRight[openRow][openCol] = moveRight[openRow][openCol - 1];
            moveRight[openRow][openCol - 1] = EMPTY_BLOCK;
            nQueue.enqueue(new Board(moveRight));
        }
        return nQueue;
    }

    public String toString()               // string representation of this board
    {
        StringBuilder s = new StringBuilder();
        s.append(dim + "\n");
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                s.append(String.format("%2d ", block[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    public static void main(String[] args) { // unit tests (not graded)

    }
}
