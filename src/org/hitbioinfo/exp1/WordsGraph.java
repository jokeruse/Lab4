package org.hitbioinfo.exp1;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;
import java.util.Vector;

public class WordsGraph {
  /* ----------------- Instance Filed ----------------- */

  private DirectedGraph<String> mwordsgraph;
  private int msize;  // Store the number of vertices.
  private Set<String> wordsSet = new HashSet<>(); // Store all the words present.
  private String presentWordInWalk;   // The present word in the random walk.
  private boolean flagOfWalk = false; 
  // Flag to indicate whether an arc is just visited twice in the walk.
  private static final int INF = 1000000007; // Init the distance;
  private HashMap<String, Integer> shortdistance = new HashMap<>(); 
  // Record the distance from a special word
  private HashMap<String, Boolean> verticesInqueue = new HashMap<>(); 
  // Record the distance from a special word
  private HashMap<String, Vector<String>> front = new HashMap<>(); 
  // Record the distance from a special word
  private List<String> recordpath = new ArrayList<>(); 
  // Record the distance from a special word
  private List<String> wordpath = new ArrayList<>();

  /* ----------------- Utility Function ----------------- */

  // Get a random word in words set
  private String randWord() {
    int randomIndex = new Random().nextInt(msize);
    int i = 0;
    String arandword = "";
    for (String tempStr : wordsSet) {
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
  private void createDotGraph(String dotFormat, String fileName) {
    GraphViz gv = new GraphViz();
    gv.addln(gv.start_graph());
    gv.add(dotFormat);
    gv.addln(gv.end_graph());
    String type = "gif";
    gv.increaseDpi();
    gv.increaseDpi();
    gv.increaseDpi();
    File out = new File(fileName + "." + type);
    gv.writeGraphToFile(gv.getGraph(gv.getDotSource(), type), out);
  }

  private void shortpath(String word) {
    for (String i : wordsSet) {
      shortdistance.put(i, INF);
      verticesInqueue.put(i, Boolean.FALSE);
      front.put(i, new Vector<>());
      front.get(i).add("");
    }
    shortdistance.put(word, 0);
    Queue<String> q = new ArrayDeque<>();
    q.add(word);
    while (!q.isEmpty()) {
      String now = q.remove();
      List<String> adjVertices = mwordsgraph.adjacentVertices(now);
      for (String i : adjVertices) {
        if (shortdistance.get(i) > shortdistance.get(now) + mwordsgraph.getWeight(now, i)) {
          shortdistance.put(i, shortdistance.get(now) + mwordsgraph.getWeight(now, i));
          front.put(i, new Vector<>());
          front.get(i).add(now);
          if (!verticesInqueue.get(i)) {
            q.add(i);
            verticesInqueue.put(i, Boolean.TRUE);
          }
        } else if (shortdistance.get(i) == shortdistance.get(now) + mwordsgraph.getWeight(now, i)) {
          front.get(i).add(now);
        }
      }
      verticesInqueue.put(now, Boolean.TRUE);
    }

  }

  private void getpath(String word) {

    if (word.equals("")) {
      StringBuilder temppath = new StringBuilder(wordpath.get(wordpath.size() - 1));
      for (int i = wordpath.size() - 2; i >= 0; i --) {
        temppath.append(" -> ").append(wordpath.get(i));
      }
      recordpath.add(temppath.toString());
      return;
    }
    wordpath.add(word);
    for (int i = 0; i < front.get(word).size(); i ++) {
      getpath(front.get(word).elementAt(i));
    }
    wordpath.remove(wordpath.size() - 1);
  }



  /* ----------------- Constructors ----------------- */
  /**
   * a.
   * @param str.
   */
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

  /* ----------------- Primary Methods ----------------- */
  /**
   * a.
   */
  public void showDirectedGraph() {
    StringBuilder builder = new StringBuilder();

    // Create dot format string.
    for (String src : wordsSet) {
      List<String> adjVertices = mwordsgraph.adjacentVertices(src);
      if (adjVertices.size() == 0) {
        continue;
      }
      for (String tgt : adjVertices) {
        String temp;
        int weight = mwordsgraph.getWeight(src, tgt);
        temp = createArcFormat(src, tgt, weight, false);
        builder.append(temp);
      }
    }

    createDotGraph(builder.toString(), "wordsGraph");
    try {
      Desktop.getDesktop().open(new File("wordsGraph.gif"));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
  /**
   * a.
   * @param word1.
   * @param word2.
   * @return.
   */
  
  public String[] querybridgewords(String word1, String word2) {
    List<String> tempbridgewords = new ArrayList<>();
    if (!wordsSet.contains(word1)) {
      return new String[]{};
    }
    List<String> adjVertices = mwordsgraph.adjacentVertices(word1);
    for (String tempWords : adjVertices) {
      List<String> tempAdjVertices = mwordsgraph.adjacentVertices(tempWords);
      for (String nextTempWords : tempAdjVertices) {
        if (nextTempWords.equals(word2)) {
          tempbridgewords.add(tempWords);
          break;
        }
      }
    }
    String[] bridgewords = new String[tempbridgewords.size()];
    for (int i = 0; i < tempbridgewords.size(); i++) {
      bridgewords[i] = tempbridgewords.get(i);
    }
    return bridgewords;
  }
  /**
   * a.
   * @param aString.
   * @return.
   */
  
  public String generateNewText(String astring) {
    List<String> wordlist = new ArrayList<>();
    StringBuilder builder = new StringBuilder(astring.replaceAll("[^a-zA-Z]+[^a-zA-Z]*", " "));
    for (int i = 0; i < builder.length(); i ++) {
      if ((builder.charAt(i) >= 'a' && builder.charAt(i) <= 'z')
          || (builder.charAt(i) >= 'A' && builder.charAt(i) <= 'Z')) {
        StringBuilder temp = new StringBuilder("");
        int j;
        for (j = i; j < builder.length() && !Character.isWhitespace(builder.charAt(j)); j ++) {
          temp.append(builder.charAt(j));
        }
        wordlist.add(temp.toString());
        i = j;
      }
    }
    if (wordlist.size() < 1) {
      return astring;
    }
    StringBuilder newText = new StringBuilder("");
    newText.append(wordlist.get(0));
    for (int i = 1; i < wordlist.size(); i++) {
      String[] bridgewords = querybridgewords(
        wordlist.get(i - 1).toLowerCase(), wordlist.get(i).toLowerCase());
      if (bridgewords.length > 0) {
        int p = new Random().nextInt(bridgewords.length);
        newText.append(" ").append(bridgewords[p]);
      }
      newText.append(" ").append(wordlist.get(i));
    }
    return newText.toString();
  }
  /**
   * a.
   * @param word.
   * @return.
   */
  
  public String[] calcshortestpath(String word) {
    shortpath(word);
    String[] path = new String[msize - 1];
    int cnt = 0;
    for (String i : wordsSet) {
      if (i.equals(word)) {
        continue;
      }
      if (shortdistance.get(i).equals(INF)) {
        path[cnt++] = word + " -> " + i + ": Unreachable";
        continue;
      }
      StringBuilder p = new StringBuilder("");
      Stack<String> q = new Stack<>();
      String temp = i;
      while (!front.get(temp).elementAt(0).equals("")) {
        q.push(temp);
        temp = front.get(temp).elementAt(0);
     
      }
      p.append(word);
      while (!q.empty()) {
        p.append(" -> ").append(q.pop());
      }
      path[cnt++] = p.toString();
    }
    return path;
  }
  /**
   * a.
   * @param word1.
   * @param word2.
   * @return.
   */
  
  public String[] calcshortestpath(String word1, String word2) {
    shortpath(word1);
    if (shortdistance.get(word2).equals(INF)) {
      return new String[]{};
    }
    recordpath = new ArrayList<>();
    getpath(word2);
    String[] shortestpath = new String[recordpath.size()];
    for (int i = 0; i < recordpath.size(); i++) {
      shortestpath[i] = recordpath.get(i);
    }
    return shortestpath;

  }
  /**
   * a.
   * @return.
   */
  
  public String randomWalk() {
    // Entry and Exit.
    if (presentWordInWalk == null) {  // Test whether it is the first word of walk.
      presentWordInWalk = randWord();
    } else if (presentWordInWalk.equals("")) { // Test whether it is the last word of walk.
      return "";
    }

    // Get all the adjacent words to the present word.
    List<String> adjVertices = mwordsgraph.adjacentVertices(presentWordInWalk);

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
    if (mwordsgraph.isColored(presentWordInWalk, tgtWord)) {
      flagOfWalk = true;
    } else {
      mwordsgraph.color(presentWordInWalk, tgtWord);
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
