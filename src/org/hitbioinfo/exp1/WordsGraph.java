package org.hitbioinfo.exp1;

import java.util.Hashtable;
import java.util.List;
import java.util.Random;

public class WordsGraph {
    private DirectedGraph mWordsGraph;
    private Hashtable<Integer, String> indexToWords;
    private int mSize;

    public WordsGraph(String aString) {
        // TODO: Implementation
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
        Random rand = new Random();
        int startPoint = rand.nextInt(mSize);

        // Initialize the string builder.
        StringBuilder builder = new StringBuilder();

        int tempVertex = startPoint;
        while (true) {
            builder.append(indexToWords.get(tempVertex)).append(" ");

            // Get all the adjacent words to the present word.
            List<Integer> adjVertices = mWordsGraph.adjacentVertices(tempVertex);

            // Stop when there is no target from the source.
            if (adjVertices.size() == 0) {
                break;
            }

            // Select a random target and add it.
            int adjNumber = adjVertices.size();
            int randTarget = adjVertices.get(rand.nextInt(adjNumber));

            // Test whether the edge is already visited.
            if (mWordsGraph.isColored(tempVertex, randTarget)) {
                builder.append(indexToWords.get(randTarget)).append(" ");
                break;
            }

            // Color the visited edge and update the temporary vertex.
            mWordsGraph.color(tempVertex, randTarget);
            tempVertex = randTarget;
        }

        return builder.toString();
    }
}
