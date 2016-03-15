package edu.cimo;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.Random;

public class Main {
    private static final int[] dim = {4,5}; // dimension of grid
    private static final char[] alphabet = {'H','D','L','P'};
    private static final int SIZE = dim[0]*dim[1]; // size of grid (aka math searched space)
    private static final int LIMIT = 50000; // max iterations
    private static final int INCREMENT = 100;
    private static int DISTANCE = 1; // searched distance
    private static int LIMIT_STAGNATE = LIMIT/10;
    private static final boolean ENABLE_DISTANCE = true;


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
        int soFarBestFitness = 0;
        int soFarBestIncr = 0;
        Grid grid = new Grid(dim);
        String subject = genRanSubj();
        System.out.println("[INFO] 1st subject: " + subject);
        int currFitness = grid.getFitness(subject, true);
        System.out.println("[INFO] 1st fitness: " + currFitness + "\n-------------------\n");

        // do some magic
        for (int i = INCREMENT; i <= LIMIT; i+= INCREMENT) {
            DISTANCE=1;
            String winner = hillClimb(i, SIZE, subject, true);
            if (grid.getFitness(winner, false) == SIZE) {
                System.err.println("[FINAL + " + i + "] fitness: " + grid.getFitness(winner, true));
                System.out.println(winner);
                System.out.println("--------------");
                break;
            }
            if (grid.getFitness(winner, false) > soFarBestFitness) {
                soFarBestFitness = grid.getFitness(winner, false);
                soFarBestIncr = i;
            }
        }
        System.err.println("[INFO] so far best: " + soFarBestFitness + ", incr: " + soFarBestIncr);
    }


    private static String hillClimb(int max, int size, String subject, boolean debugPrinting) {
//        System.out.println("[INFO] running hillclimbing");
        LinkedList<String> candidates;
        Grid grid = new Grid(dim);
        PrintWriter writer = null;
        if (debugPrinting) {
            try {
                writer = new PrintWriter("max-"+max+"_size-"+size+"_D-"+ DISTANCE +".out", "UTF-8");
                writer.println("\"iter\";\"fitness\";\"distance\"");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        int currFitness = grid.getFitness(subject, false);
        int candFitness = 0;
        int stagnate = 0;

        for (int i = 0; i < max; i++) {
            if (currFitness >= SIZE || i >= max || stagnate >= LIMIT_STAGNATE) { // stop condition: stop or solution was found
                if (debugPrinting) {
                    writer.println("\"" + i + "\";\"" + currFitness + "\";\"" + DISTANCE + "\"");
                }
                if (writer != null) writer.close();
                return subject;
            }

            candidates = getRandCandidate(subject);
            for (String candidate : candidates) {
                candFitness = grid.getFitness(candidate, false);
                if (candFitness >= currFitness) {
                    subject = candidate;
                    currFitness = candFitness;
                    if (currFitness == candFitness) stagnate++; // stagnation - same position
                    else stagnate = 0;
                } else { // stagnation - local extrem
                    stagnate++;
                }
            }

            if (stagnate >= LIMIT_STAGNATE/2 && ENABLE_DISTANCE) DISTANCE = (DISTANCE < SIZE/4) ? DISTANCE+1 : DISTANCE;

            if (debugPrinting) {
                writer.println("\"" + i + "\";\"" + currFitness + "\";\"" + DISTANCE + "\"");
            }
        }

        if (writer != null) writer.close();
        return subject;
    }

    private static LinkedList<String> getRandCandidate(String subject) {
        StringBuilder tmp = new StringBuilder(subject);
        LinkedList<String> candidates = new LinkedList<String>();

        for (int i = 0; i < DISTANCE; i++) { // do some mutation
            tmp.setCharAt(randInt(0,SIZE-1), alphabet[randInt(0,alphabet.length-1)]);
            candidates.add(tmp.toString());
        }

        return candidates;
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
