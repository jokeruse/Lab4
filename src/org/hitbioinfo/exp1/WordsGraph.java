package org.hitbioinfo.exp1;

import java.util.*;

public class WordsGraph {
    private DirectedGraph<String> mWordsGraph;
    private int mSize;  // Store the number of vertices.
    Set<String> wordsSet = new HashSet<String>(); // Store all the words present.

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
        mWordsGraph = new DirectedGraph<String>(mSize);

        if (mSize == 0 || mSize == 1) { // If there is no word...
            return;
        }

        strScanner = new Scanner(builder.toString());
        String srcWord = strScanner.next();
        String tgtWord = strScanner.next();
        mWordsGraph.addArc(srcWord, tgtWord, 1);
        srcWord = tgtWord;

        // The main part of the construction.
        while (strScanner.hasNext()) {
            // Read the next word.
            tgtWord = strScanner.next();

            // Add an arc.
            if (mWordsGraph.containsVertex(srcWord) && mWordsGraph.containsVertex(tgtWord)
                    && mWordsGraph.isArc(srcWord, tgtWord)) {  // If the arc already exists...
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

    public String randomWalk() {
        // Select a random start point.
        String startWord = randWord();

        // Initialize the string builder.
        StringBuilder builder = new StringBuilder();

        String tempWord = startWord;
        while (true) {
            builder.append(tempWord).append(" ");

            // Get all the adjacent words to the present word.
            List<String> adjVertices = mWordsGraph.adjacentVertices(tempWord);

            // Stop when there is no target from the source.
            if (adjVertices.size() == 0) {
                break;
            }

            // Select a random target and add it.
            int adjSize = adjVertices.size();
            Random rand = new Random();
            String randTgtWord = adjVertices.get(rand.nextInt(adjSize));

            // Test whether the edge is already visited.
            if (mWordsGraph.isColored(tempWord, randTgtWord)) {
                builder.append(randTgtWord).append(" ");
                break;
            }

            // Color the visited edge and update the temporary vertex.
            mWordsGraph.color(tempWord, randTgtWord);
            tempWord = randTgtWord;
        }

        return builder.toString();
    }
}
