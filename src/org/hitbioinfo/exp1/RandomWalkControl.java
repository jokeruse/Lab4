package org.hitbioinfo.exp1;

import java.util.List;
import java.util.Random;

public class RandomWalkControl {
    // Get a random word in words set
    private static String randWord(WordsGraphData wordsGraphData) {
        int randomIndex = new Random().nextInt(wordsGraphData.getMsize());
        int i = 0;
        String arandword = "";
        for (String tempStr : wordsGraphData.getWordsSet()) {
            if (i == randomIndex) {
                arandword = tempStr;
            }
            ++i;
        }

        if (arandword.equals("")) {
            throw new RuntimeException("Error: Failed to generate random word in randWord()!");
        }
        return arandword;
    }

    public static String randomWalk(WordsGraphData wordsGraphData) {
        // Entry and Exit.
        if (wordsGraphData.getPresentWordInWalk() == null) {  // Test whether it is the first word of walk.
            wordsGraphData.setPresentWordInWalk(randWord(wordsGraphData));
        } else if (wordsGraphData.getPresentWordInWalk().equals("")) { // Test whether it is the last word of walk.
            return "";
        }

        // Get all the adjacent words to the present word.
        List<String> adjVertices = wordsGraphData.adjacentVertices(wordsGraphData.getPresentWordInWalk());

        // Test whether there is no out-arcs from presentWordInWalk OR an arc is just visited twice.
        if (adjVertices.size() == 0 || wordsGraphData.getFlagOfWalk()) {
            String tempStr = wordsGraphData.getPresentWordInWalk();
            wordsGraphData.setPresentWordInWalk("");
            return tempStr;
        }

        // Select a random target and add it.
        int adjSize = adjVertices.size();
        Random rand = new Random();
        String tgtWord = adjVertices.get(rand.nextInt(adjSize));

        // Test whether the edge is already visited.
        if (wordsGraphData.isColored(wordsGraphData.getPresentWordInWalk(), tgtWord)) {
            wordsGraphData.setFlagOfWalk(true);
        } else {
            wordsGraphData.color(wordsGraphData.getPresentWordInWalk(), tgtWord);
        }

        // Update presentWordInWalk.
        String tempStr = wordsGraphData.getPresentWordInWalk();
        wordsGraphData.setPresentWordInWalk(tgtWord);
        return tempStr;
    }

    // Reset the random walk.
    public static void resetWalk(WordsGraphData wordsGraphData) {
        wordsGraphData.setPresentWordInWalk(null);
        wordsGraphData.setFlagOfWalk(false);
    }

}
