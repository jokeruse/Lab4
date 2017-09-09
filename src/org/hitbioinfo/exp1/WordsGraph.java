package org.hitbioinfo.exp1;

import java.util.*;

public class WordsGraph {
    private DirectedGraph<String> mWordsGraph;
    private int mSize;  // Store the number of vertices.
    private Set<String> wordsSet = new HashSet<String>(); // Store all the words present.
    private String presentWordInWalk;   // The present word in the random walk.
    private boolean flagOfWalk = false; // Flag to indicate whether an arc is just visited twice in the walk.

    // Utility function to get a random word in words set
    private String randWord() {
        int randomIndex = new Random().nextInt(mSize);
        int i = 0;
        String aRandWord = "";
        for (String tempStr : wordsSet) {
            if (i == randomIndex) {
                aRandWord = tempStr;
            }
            ++i;
        }

        if (aRandWord.equals("")) {
            throw new RuntimeException("Error: Failed to generate random word in randWord()!");
        }
        return aRandWord;
    }

    public WordsGraph(String str) {
        /* Modify specific character. */

        StringBuilder builder = new StringBuilder(str);
        for (int i = 0; i < builder.length(); ++i) {
            char temp = builder.charAt(i);
            if (Character.isLetter(temp)) {
                builder.setCharAt(i, Character.toLowerCase(temp));
            } else {
                builder.setCharAt(i, ' ');
            }
        }

        Scanner strScanner = new Scanner(builder.toString());

        /* Construct the words graph. */

        // Count how many words present and use the size to initialize the graph.
        // This operation is needed because of the bad design of graph.
        // It should be solved.
        while (strScanner.hasNext()) {
            wordsSet.add(strScanner.next());
        }
        mSize = wordsSet.size();
        mWordsGraph = new DirectedGraph<String>(wordsSet);

        if (mSize == 0 || mSize == 1) { // If there is no word...
            return;
        }

        strScanner = new Scanner(builder.toString());
        String srcWord = strScanner.next();

        // The main part of the construction.
        while (strScanner.hasNext()) {
            // Read the next word.
            String tgtWord = strScanner.next();

            // Add an arc.
            if (mWordsGraph.isArc(srcWord, tgtWord)) {  // If the arc already exists...
                int tempWeight = mWordsGraph.getWeight(srcWord, tgtWord);
                mWordsGraph.setWeight(srcWord, tgtWord, tempWeight + 1);
            } else {
                mWordsGraph.addArc(srcWord, tgtWord, 1);
            }

            srcWord = tgtWord;
        }
    }

    public void showDirectedGraph() {
        // TODO: Implementation
    }

    public String[] queryBridgeWords(String word1, String word2) {
        // TODO: Implementation
    }

    public String generateNewText(String aString) {
        // TODO: Implementation
    }

    public String[] calcShortestPath(String word) {
        // TODO: Implementation
    }

    public String[] calcShortestPath(String word1, String word2) {
        // TODO: Implementation
    }

    public void resetWalk() {
        presentWordInWalk = null;
        flagOfWalk = false;
    }

    public String randomWalk() {
        // Entry and Exit.
        if (presentWordInWalk == null) {    // Test whether it is the first word of walk.
            presentWordInWalk = randWord();
        } else if (presentWordInWalk.equals("")) { // Test whether it is the last word of walk.
            return "";
        }

        // Get all the adjacent words to the present word.
        List<String> adjVertices = mWordsGraph.adjacentVertices(presentWordInWalk);

        // Test whether there is no out-arcs from presentWordInWalk OR an arc is just visited twice.
        if (adjVertices.size() == 0 || flagOfWalk) {
            String tempStr = presentWordInWalk;
            presentWordInWalk = "";
            return tempStr;
        }

        // Select a random target and add it.
        int adjSize = adjVertices.size();
        Random rand = new Random();
        String tgtWord = adjVertices.get(rand.nextInt(adjSize));

        // Test whether the edge is already visited.
        if (mWordsGraph.isColored(presentWordInWalk, tgtWord)) {
            flagOfWalk = true;
        } else {
            mWordsGraph.color(presentWordInWalk, tgtWord);
        }

        // Update presentWordInWalk.
        String tempStr = presentWordInWalk;
        presentWordInWalk = tgtWord;
        return tempStr;
    }
}
