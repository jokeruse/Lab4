package org.hitbioinfo.exp1;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class WordsGraph {
    /* ----------------- Instance Filed ----------------- */

    private DirectedGraph<String> mWordsGraph;
    private int mSize;  // Store the number of vertices.
    private Set<String> wordsSet = new HashSet<>(); // Store all the words present.
    private String presentWordInWalk;   // The present word in the random walk.
    private boolean flagOfWalk = false; // Flag to indicate whether an arc is just visited twice in the walk.

    /* ----------------- Utility Function ----------------- */

    // Get a random word in words set
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

    // Add an arc into graph-viz format file.
    private String createArcFormat(String src, String tgt, int weight, boolean isEmphasized) {
        StringBuilder builder = new StringBuilder();
        builder.append(src).append("->").append(tgt).append("[label=").append(weight);
        if (isEmphasized) {
            builder.append(" color=red];");
        } else {
            builder.append("];");
        }

        return builder.toString();
    }

    // Create dot graph.
    private void createDotGraph(String dotFormat,String fileName) {
        GraphViz gv=new GraphViz();
        gv.addln(gv.start_graph());
        gv.add(dotFormat);
        gv.addln(gv.end_graph());
        String type = "gif";
        gv.increaseDpi();
        gv.increaseDpi();
        gv.increaseDpi();
        File out = new File(fileName+"."+ type);
        gv.writeGraphToFile( gv.getGraph( gv.getDotSource(), type ), out);
    }

    // Display picture file.
    private void displayPic(String fileName) {
        try {
            // This is a new frame, where the picture should be shown.
            final JFrame showPictureFrame = new JFrame(fileName);
            JLabel pictureLabel = new JLabel();

            /* Read the image */

            URL url = new File(fileName).toURI().toURL();
            BufferedImage img = ImageIO.read(url);

             /* until here */

            // Add the image as ImageIcon to the label.
            pictureLabel.setIcon(new ImageIcon(img));
            // Add the label to the frame.
            showPictureFrame.add(pictureLabel);
            // Pack everything (does many stuff. e.g. resize the frame to fit the image)
            showPictureFrame.pack();

            // This is how you should open a new Frame or Dialog
            // but only using showPictureFrame.setVisible(true); would also work.
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    showPictureFrame.setVisible(true);
                }
            });
        } catch (IOException ex) {
            System.err.println("Some IOException occurred (did you set the right path?): ");
            System.err.println(ex.getMessage());
        }
    }

    /* ----------------- Constructors ----------------- */

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
        // This operation is needed because of the bad design of graph with adjacent matrix.
        // It should be solved.
        while (strScanner.hasNext()) {
            wordsSet.add(strScanner.next());
        }
        mSize = wordsSet.size();
        mWordsGraph = new DirectedGraph<>(wordsSet);

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

    /* ----------------- Primary Methods ----------------- */

    public void showDirectedGraph() {
        StringBuilder builder = new StringBuilder();

        // Create dot format string.
        for (String src : wordsSet) {
            List<String> adjVertices = mWordsGraph.adjacentVertices(src);
            if (adjVertices.size() == 0) {
                continue;
            }
            for (String tgt : adjVertices) {
                String temp;
                int weight = mWordsGraph.getWeight(src, tgt);
                temp = createArcFormat(src, tgt, weight, false);
                builder.append(temp);
            }
        }

        createDotGraph(builder.toString(), "wordsGraph");
        displayPic("wordsGraph.gif");
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

    /* ----------------- Extra Methods ----------------- */

    // Reset the random walk.
    public void resetWalk() {
        presentWordInWalk = null;
        flagOfWalk = false;
    }

    // Test whether a word is in the words graph.
    public boolean containsWords(String word) {
        return wordsSet.contains(word);
    }
}
