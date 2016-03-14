package edu.cimo;

import java.util.Random;

public class Main {
    private static final int[] dim = {4,5};
    private static final char[] alphabet = {'H','D','L','P'};
    private static final int LIMIT = 20;

    public static void main(String[] args) {
        Grid grid = new Grid(dim);
        String subject = genRanSubj();
        System.out.println("[INFO] 1st subject: " + subject);
        int currFitness = grid.getFitness(subject);

        System.out.println("[INFO] 1st fitness: " + currFitness);
    }

    private static String genRanSubj() {
        StringBuilder str = new StringBuilder();

        for (int i = 0; i < LIMIT; i++) {
            str.append(alphabet[randInt(0,alphabet.length-1)]);
        }

        return str.toString();
    }

    public static int randInt(int min, int max) {
        Random rand = new Random();

        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }
}
