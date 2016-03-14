package edu.cimo;

public class Grid {
    private final char[] alphabet = {'H','D','L','P'};
    private final int X, Y;

    public Grid(int[] dim) {
        this.X = dim[0];
        this.Y = dim[1];
    }

    protected int getFitness(String subject, boolean printGrid) {
        int fitness = 0;
        int[] currPos = {0,0};
        int[][] grid = new int[this.X][this.Y];

        for (int i = 0; i < subject.length(); i++) { // iterate over subject
            fitness += makeMove(currPos, grid, subject.charAt(i));
        }

        if (printGrid) {
            System.out.println("[INFO] pos: [" + currPos[0] + "," + currPos[1] + "]");
            printGridHumanFormat(grid);
        }

        return fitness;
    }

    /*
     * brief: Makes move if possible.
     * return 1 - if possible and not visisted tile. 0 - if not possible or visited
     */
    private int makeMove(int[] currPos, int[][] grid, char move) {
        if (currPos[0] >= X || currPos[1] >= Y) {
            return 0;
        }
        if (move == 'H') {
            if (currPos[1] < Y-1) { // can move up
                currPos[1]++; // change position
                if (grid[currPos[0]][currPos[1]] == 0) { // tile was not visited
                    grid[currPos[0]][currPos[1]] = 1;
                    return 1;
                }
            }
        }
        else if (move == 'D') {
            if (currPos[1] > 0) { // can move up
                currPos[1]--; // change position
                if (grid[currPos[0]][currPos[1]] == 0) { // tile was not visited
                    grid[currPos[0]][currPos[1]] = 1;
                    return 1;
                }
            }
        }
        else if (move == 'L') {
            if (currPos[0] > 0) { // can move up
                currPos[0]--; // change position
                if (grid[currPos[0]][currPos[1]] == 0) { // tile was not visited
                    grid[currPos[0]][currPos[1]] = 1;
                    return 1;
                }
            }
        }
        else if (move == 'P') {
            if (currPos[0] < X-1) { // can move up
                currPos[0]++; // change position
                if (grid[currPos[0]][currPos[1]] == 0) { // tile was not visited
                    grid[currPos[0]][currPos[1]] = 1;
                    return 1;
                }
            }
        }

        return 0;
    }

    /*
     * brief: print grid in human readable format to console
     */
    protected void printGridHumanFormat(int[][] grid) {
        System.out.println("[INFO] grid:");
        for (int i = Y-1; i >= 0; i--) {
            for (int j = 0; j < X; j++) {
                System.out.print(grid[j][i] + " ");
            }
            System.out.println();
        }
    }
}
