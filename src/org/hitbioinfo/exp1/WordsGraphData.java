package org.hitbioinfo.exp1;

import java.util.*;

public class WordsGraphData {
      /* ----------------- Instance Filed ----------------- */
    private DirectedGraph<String> mwordsgraph;
    private int msize;  // Store the number of vertices.
    private Set<String> wordsSet = new HashSet<>(); // Store all the words present.
    private String presentWordInWalk;   // The present word in the random walk.
    private boolean flagOfWalk = false;
    // Flag to indicate whether an arc is just visited twice in the walk.
    private final int INF = 1000000007; // Init the distance;
    private HashMap<String, Integer> shortdistance = new HashMap<>();
    // Record the distance from a special word
    private HashMap<String, Boolean> verticesInqueue = new HashMap<>();
    // Record the distance from a special word
    private HashMap<String, Vector<String>> front = new HashMap<>();
    // Record the distance from a special word
    private List<String> recordpath = new ArrayList<>();
    // Record the distance from a special word
    private List<String> wordpath = new ArrayList<>();

    // Constructor
    public WordsGraphData(String str) {
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
        // This operation is needed because of the bad design of graph with adjacent matrix.
        // It should be solved.
        while (strScanner.hasNext()) {
            wordsSet.add(strScanner.next());
        }
        msize = wordsSet.size();
        mwordsgraph = new DirectedGraph<>(wordsSet);

        if (msize == 0 || msize == 1) { // If there is no word...
            return;
        }

        strScanner = new Scanner(builder.toString());
        String srcWord = strScanner.next();

        // The main part of the construction.
        while (strScanner.hasNext()) {
            // Read the next word.
            String tgtWord = strScanner.next();

            // Add an arc.
            if (mwordsgraph.isArc(srcWord, tgtWord)) {  // If the arc already exists...
                int tempWeight = mwordsgraph.getWeight(srcWord, tgtWord);
                mwordsgraph.setWeight(srcWord, tgtWord, tempWeight + 1);
            } else {
                mwordsgraph.addArc(srcWord, tgtWord, 1);
            }

            srcWord = tgtWord;
        }
        strScanner.close();
    }

    public DirectedGraph<String> getMwordsgraph() {
        return mwordsgraph;
    }

    public int getMsize() {
        return msize;
    }

    public Set<String> getWordsSet() {
        return wordsSet;
    }

    public int getINF() {
        return INF;
    }

    public HashMap<String, Boolean> getVerticesInqueue() {
        return verticesInqueue;
    }

    public boolean getFlagOfWalk() {
        return flagOfWalk;
    }

    public HashMap<String, Integer> getShortdistance() {
        return shortdistance;
    }

    public List<String> getRecordpath() {
        return recordpath;
    }

    public List<String> getWordpath() {
        return wordpath;
    }

    public HashMap<String, Vector<String>> getFront() {
        return front;
    }

    public String getPresentWordInWalk() {
        return presentWordInWalk;
    }

    public List<String> adjacentVertices(String src) {
        return mwordsgraph.adjacentVertices(src);
    }

    // Test whether a word is in the words graph.
    public boolean containsWords(String word) {
        return wordsSet.contains(word);
    }

    public int getWeight(String word1, String word2) {
        return mwordsgraph.getWeight(word1, word2);
    }

    public void setPresentWordInWalk(String presentWordInWalk) {
        this.presentWordInWalk = presentWordInWalk;
    }

    public void setFlagOfWalk(boolean flagOfWalk) {
        this.flagOfWalk = flagOfWalk;
    }

    public boolean isColored(String word1, String word2) {
        return mwordsgraph.isColored(word1, word2);
    }

    public void color(String word1, String word2) {
        mwordsgraph.color(word1, word2);
    }
}
