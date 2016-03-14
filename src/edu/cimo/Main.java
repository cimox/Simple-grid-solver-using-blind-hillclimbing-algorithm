package edu.cimo;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Random;

public class Main {
    private static final int[] dim = {4,5}; // dimension of grid
    private static final char[] alphabet = {'H','D','L','P'};
    private static final int SIZE = 20; // size of grid (aka math searched space)
    private static final int LIMIT = 50000; // max iterations
    private static int D = 1; // searched distance

    /*
    Takze algoritmus vyzera nasledovne:
    1. Inicializujete nahodneho jedinca, vypocitate mu fitness (iba na toto sluzi mriezka)
    2. Vygenerujte nahodne kandidata z okolia jedinca tym ze ho skopirujete a kopiu zmutujete.
       Pocet povolenych mutacii - nahodnych zmien v retazci je velkost prehladavaneho okolia. Kandidatovi vypocitajte fitness.
    3. Ak ma kandidat vecsiu alebo rovnaku fitness ako original tak kandidat nahradi original.
    4. Ak su splnene podmienky ukoncenia tak algoritmus zastavte (dosiahnuta maximalna fitness,
       pocet iteracii stagnacie algoritmu - kolko iteracii sa fitness nezvysila).
     */

    public static void main(String[] args) {
        Grid grid = new Grid(dim);
        String subject = genRanSubj();
        System.out.println("[INFO] 1st subject: " + subject);
        int currFitness = grid.getFitness(subject, true);
        System.out.println("[INFO] 1st fitness: " + currFitness + "\n-------------------\n");

        // do some magic
        for (int i = 0; i < LIMIT; i+= 100) {
            String winner = hillClimb(i, SIZE, subject, true);
            System.out.println("[FINAL + " + i + "] fitness: " + grid.getFitness(winner, true));
        }
    }


    private static String hillClimb(int max, int size, String subject, boolean debugPrinting) {
        System.out.println("[INFO] running hillclimbing");
        String candidate;
        Grid grid = new Grid(dim);
        PrintWriter writer = null;
        if (debugPrinting) {
            try {
                writer = new PrintWriter("max-"+max+"_size-"+size+"_D-"+D+".out", "UTF-8");
                writer.println("iter;fitness");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        int currFitness = grid.getFitness(subject, false);
        int candFitness = 0;

        for (int i = 0; i < max; i++) {
            if (currFitness >= SIZE || i >= max) { // stop condition
                return subject;
            }

            candidate = getRandCandidate(subject);
            candFitness = grid.getFitness(candidate, false);
            if (candFitness >= currFitness) {
                subject = candidate;
                currFitness = candFitness;
            }
            if (debugPrinting) {
                writer.println(i + ";" + currFitness);
            }
        }

        if (writer != null) writer.close();
        return subject;
    }

    private static String getRandCandidate(String subject) {
        StringBuilder tmp = new StringBuilder(subject);
        for (int i = 0; i <= D; i++) { // do some mutation
            tmp.setCharAt(randInt(0,SIZE-1), alphabet[randInt(0,alphabet.length-1)]);
        }

        return tmp.toString();
    }
    /*
     * generate random subject (aka chromosome) with limit length
     */
    private static String genRanSubj() {
        StringBuilder str = new StringBuilder();

        for (int i = 0; i < SIZE; i++) {
            str.append(alphabet[randInt(0,alphabet.length-1)]);
        }

        return str.toString();
    }

    /*
     * brief: generate random integer in min-max bounds
     */
    public static int randInt(int min, int max) {
        Random rand = new Random();

        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }
}
