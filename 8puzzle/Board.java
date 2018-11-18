/* *****************************************************************************
 *  Name: Rich Nakasato
 *  Date: 11/17/18
 *  Description:
 **************************************************************************** */

public class Board {

    private int[][] blocks;
    private final int dim;

    public Board(int[][] blocks)           // construct a board from an n-by-n array of blocks
    {
        this.blocks = blocks;
        dim = blocks.length;
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
        for (int r = 0; r < blocks.length; ++r) {
            for (int c = 0; c < blocks[0].length; ++c) {
                if (expected < max && blocks[r][c] != expected) {
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
        for (int r = 0; r < blocks.length; ++r) {
            for (int c = 0; c < blocks[0].length; ++c) {
                int cur = blocks[r][c];
                if (cur != 0) {
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
        for (int r = 0; r < blocks.length; ++r) {
            for (int c = 0; c < blocks[0].length; ++c) {
                if (expected < max && blocks[r][c] != expected) {
                    return false;
                }
                ++expected;
            }
        }
        return true;
    }

    public Board twin()                    // a board that is obtained by exchanging any pair of blocks
    {
        return null;
    }

    public boolean equals(Object y)        // does this board equal y?
    {
        return false;
    }

    public Iterable<Board> neighbors()     // all neighboring boards
    {
        return null;
    }

    public String toString()               // string representation of this board
    {
        return "";
    }

    public static void main(String[] args) { // unit tests (not graded)

    }
}
