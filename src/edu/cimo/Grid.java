package edu.cimo;

public class Grid {
    private final char[] alphabet = {'H','D','L','P'};
    private final int Y, X;

    public Grid(int[] dim) {
        this.X = dim[0];
        this.Y = dim[1];
    }

    protected int getFitness(String subject) {
        int fitness = 0;
        int[] currPos = {0,0};
        int[][] grid = new int[this.Y][this.X];

        for (int i = 0; i < subject.length(); i++) { // iterate over subject
            if (subject.charAt(i) == 'H') { // move up if possible
                fitness += makeMove(currPos, grid, subject.charAt(i));
            }
        }

        return fitness;
    }

    /*
     * brief: Makes move if possible.
     * return 1 - if possible and not visisted tile. 0 - if not possible or visited
     */
    private int makeMove(int[] currPos, int[][] grid, char move) {
        if (move == 'H') {
            if (currPos[1] < Y-1) { // can move up
                currPos[1]++; // change position
                if (grid[currPos[1]][currPos[1]] == 0) { // tile was not visited
                    grid[currPos[1]][currPos[1]] = 1;
                    return 1;
                }
            }
        }
        else if (move == 'D') {
            if (currPos[1] > 0) { // can move up
                currPos[1]--; // change position
                if (grid[currPos[1]][currPos[1]] == 0) { // tile was not visited
                    grid[currPos[1]][currPos[1]] = 1;
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
}
