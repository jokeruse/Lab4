package org.hitbioinfo.exp1;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            run(args);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    // It is A utility function to run the main process of the program.
    // The purpose of this construction of the Main class is for the separation of the other codes from TRY BLOCK.
    private static void run(String[] args) {
        // Process different parameters.
        if (args.length == 0 || args[0].equals("-h") ) {
            // Print the help text.
            System.out.println("Usage: wordsToGraph [-h] <filename>");
            return;
        } else if (args.length == 1) {
            // Execute the primary function.

            // Read in the file.
            File file = new File(args[0]);
            if (file.isDirectory()) {       // Check whether the file is a directory.
                System.out.println("Attention: '" + args[0]
                        + "' is a directory. See help text with argument '-h'.");
                return;
            }

            // Read in the text.
            Scanner in;
            try {
                in = new Scanner(file, "UTF-8");
            }
            catch (FileNotFoundException e) {
                throw new RuntimeException("Attention: The file is not found!");
            }
            StringBuilder builder = new StringBuilder();
            while (in.hasNextLine()) {
                builder.append(in.nextLine());
            }
            String rawInput = builder.toString();

            // Generate a new graph.
            WordsGraph wordsGraph = new WordsGraph(rawInput);

            // Show the generated graph.
            System.out.println("The Generated graph is showed and saved.");
            wordsGraph.showDirectedGraph();

            in = new Scanner(System.in);

            // Find bridge words from word1 to word2.
            System.out.println("Please input two words to find their bridge words.");
            System.out.print("Word 1: ");
            String word1 = in.next();
            System.out.print("Word 2: ");
            String word2 = in.next();
            String[] bridgeWords = wordsGraph.queryBridgeWords(word1, word2);
            if (bridgeWords.length == 0) {
                System.out.println("No bridge words from word1 to word2!");
            } else if (bridgeWords.length == 1) {
                System.out.println("The bridge words from word1 to word2 is: "
                        + bridgeWords[0] + ".");
            } else {
                System.out.println("The bridge words from word1 to word2 are: ");

                // Print words except the last one in the FOR LOOP.
                for (int i = 0; i < bridgeWords.length - 1; ++i) {
                    System.out.println(bridgeWords[i] + ", ");
                }
                System.out.println("and "
                        + bridgeWords[bridgeWords.length - 1] + ".");
            }

            // Generate new text in terms of the newly input text and the graph.
            System.out.println("Please input a text in a single line:");
            String inputText = in.nextLine();
            System.out.println("The text is:");
            System.out.println(wordsGraph.generateNewText(inputText));

            // Get the shortest path(s) from word1 to word2 in the graph.
            System.out.println("Please input two words to find shortest path(s) from word1 to word2.");
            System.out.print("Word1: ");
            word1 = inputText;
            System.out.print("Word2: ");
            word2 = in.next();
            System.out.println("The shortest path(s) are:");
            String[] paths = wordsGraph.calcShortestPath(word1, word2);
            if (paths.length == 0) {
                System.out.println("There is no path from word1 to word2.");
            } else {
                for (String path : paths) {
                    System.out.println(path);
                }
            }

            // Get all shortest paths from one word to other words.
            System.out.println("Please input a word to find all shortest path(s) to other words if existed:");
            String word = in.next();
            paths = wordsGraph.calcShortestPath(word);
            if (paths.length == 0) {
                System.out.println("There is no path from this word to other words.");
            } else {
                for (String path : paths) {
                    System.out.println(path);
                }
            }

            // Traverse the graph from a random words as a start point.
            System.out.println("Please input a word as a start point:");
            word = in.next();
            System.out.println("The traversal routine is:");
            System.out.println(wordsGraph.randomWalk());

        } else {
            System.out.println("Attention: Bad parameter. See 'wordsToGraph -h'.");
        }
    }
}
