/* *****************************************************************************
 *  Name:    Rich Nakasato
 *  NetID:   rnakasato
 *  Precept: P00
 *
 *  Description:  Prints 'Hello, World' to the terminal window.
 *                By tradition, this is everyone's first program.
 *                Prof. Brian Kernighan initiated this tradition in 1974.
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private WeightedQuickUnionUF connections;   // connected "sites"
    private boolean[][] grid;                   // grid of "sites"
    private int size;                           // cache grid size
    private int openSites;                      // cache open sites
    private int virtualTopNode;                 // top virtual connection
    private int virtualBottomNode;              // bottom virtual connection

    // creates grid of booleans of nxn size, initialized to false
    public Percolation(int n) {
        validateConstruction(n);
        int numVirtualSites = 2;
        size = n;
        virtualTopNode = 0;
        virtualBottomNode = n * n + 1;
        connections = new WeightedQuickUnionUF(size * size + numVirtualSites);
        grid = new boolean[size][size];
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                grid[row][col] = false;
                /*
                if (row == 0) {
                    int currNode = convertRowColToNode(row, col);
                    connections.union(virtualTopNode, currNode);
                }
                */
                /*
                else if (row == size - 1) {
                    int currNode = convertRowColToNode(row, col);
                    connections.union(virtualBottomNode, currNode);
                }
                */
            }
        }
    }

    // opens a site if not already open
    public void open(int row, int col) {
        validateRowCol(row, col);
        int trueRow = row - 1;
        int trueCol = col - 1;
        if (!grid[trueRow][trueCol]) {
            grid[trueRow][trueCol] = true;
            openSites += 1;
            int currNode = convertRowColToNode(trueRow, trueCol);
            if (doConnectUp(trueRow, trueCol)) {
                int upNode = convertRowColToNode(trueRow - 1, trueCol);
                connections.union(upNode, currNode);
            }
            if (doConnectLeft(trueRow, trueCol)) {
                int leftNode = convertRowColToNode(trueRow, trueCol - 1);
                connections.union(leftNode, currNode);
            }
            if (doConnectDown(trueRow, trueCol)) {
                int downNode = convertRowColToNode(trueRow + 1, trueCol);
                connections.union(downNode, currNode);
            }
            if (doConnectRight(trueRow, trueCol)) {
                int rightNode = convertRowColToNode(trueRow, trueCol + 1);
                connections.union(rightNode, currNode);
            }
        }
    }

    // returns true if a site is open
    public boolean isOpen(int row, int col) {
        validateRowCol(row, col);
        int trueRow = row - 1;
        int trueCol = col - 1;
        return grid[trueRow][trueCol];
    }

    // returns true if this site is connect to a top row node (virtual)
    public boolean isFull(int row, int col) {
        validateRowCol(row, col);
        int trueRow = row - 1;
        int trueCol = col - 1;
        int currNode = convertRowColToNode(trueRow, trueCol);
        return connections.connected(virtualTopNode, currNode);
    }

    // return the count of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // return true if the top is connected to the bottom (vitual)
    public boolean percolates() {
        return connections.connected(virtualTopNode, virtualBottomNode);
    }

    // testing
    public static void main(String[] args) {
        Percolation p = new Percolation(3);
        p.open(1, 1);
        p.open(1, 2);
        p.open(2, 2);
        p.open(2, 3);
        p.open(3, 3);
        System.out.println(p.isFull(3, 3));
        System.out.println(p.isFull(1, 3));
        int start = p.convertRowColToNode(0, 0);
        int end = p.convertRowColToNode(2, 2);
        boolean is = p.connections.connected(start, end);
        System.out.println("is connected: " + is);
    }

    // validate construction parameters
    private void validateConstruction(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("invalid size");
        }
    }

    // validate row and column indicies
    private void validateRowCol(int row, int col) {
        if (row <= 0 || row > size || col <= 0 || col > size) {
            throw new IllegalArgumentException("invalid row or column");
        }
    }

    // converts "true" row and "true" col to wuqf node
    public int convertRowColToNode(int row, int col) {
        int nodeOffset = 1;
        return size * row + col + nodeOffset;
    }

    // returns true if "upwards" position exists and is open
    private boolean doConnectUp(int row, int col) {
        return row - 1 >= 0 && grid[row - 1][col];
    }

    // return true if "leftward" position exists and is open
    private boolean doConnectLeft(int row, int col) {
        return col - 1 >= 0 && grid[row][col - 1];
    }

    // return true if "downward" position exists and is open
    private boolean doConnectDown(int row, int col) {
        return row + 1 < size && grid[row + 1][col];
    }

    // return true if "rightward" position exists and is open
    private boolean doConnectRight(int row, int col) {
        return col + 1 < size && grid[row][col + 1];
    }
}
