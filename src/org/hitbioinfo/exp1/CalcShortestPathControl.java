package org.hitbioinfo.exp1;

import java.util.*;

public class CalcShortestPathControl {
    private static void shortpath(WordsGraphData wordsGraphData, String word) {
        for (String i : wordsGraphData.getWordsSet()) {
            wordsGraphData.getShortdistance().put(i, wordsGraphData.getINF());
            wordsGraphData.getVerticesInqueue().put(i, Boolean.FALSE);
            wordsGraphData.getFront().put(i, new Vector<>());
            wordsGraphData.getFront().get(i).add("");
        }
        wordsGraphData.getShortdistance().put(word, 0);
        Queue<String> q = new ArrayDeque<>();
        q.add(word);
        while (!q.isEmpty()) {
            String now = q.remove();
            List<String> adjVertices = wordsGraphData.adjacentVertices(now);
            for (String i : adjVertices) {
                if (wordsGraphData.getShortdistance().get(i) > wordsGraphData.getShortdistance().get(now) + wordsGraphData.getWeight(now, i)) {
                    wordsGraphData.getShortdistance().put(i, wordsGraphData.getShortdistance().get(now) + wordsGraphData.getWeight(now, i));
                    wordsGraphData.getFront().put(i, new Vector<>());
                    wordsGraphData.getFront().get(i).add(now);
                    if (!wordsGraphData.getVerticesInqueue().get(i)) {
                        q.add(i);
                        wordsGraphData.getVerticesInqueue().put(i, Boolean.TRUE);
                    }
                } else if (wordsGraphData.getShortdistance().get(i) == wordsGraphData.getShortdistance().get(now) + wordsGraphData.getWeight(now, i)) {
                    wordsGraphData.getFront().get(i).add(now);
                }
            }
            wordsGraphData.getVerticesInqueue().put(now, Boolean.TRUE);
        }
    }

    private static void getpath(WordsGraphData wordsGraphData, String word) {
        List<String> wordpath = wordsGraphData.getWordpath();

        if (word.equals("")) {
            StringBuilder temppath = new StringBuilder(wordpath.get(wordpath.size() - 1));
            for (int i = wordpath.size() - 2; i >= 0; i--) {
                temppath.append(" -> ").append(wordpath.get(i));
            }
            wordsGraphData.getRecordpath().add(temppath.toString());
            return;
        }
        wordpath.add(word);
        for (int i = 0; i < wordsGraphData.getFront().get(word).size(); i++) {
            getpath(wordsGraphData, wordsGraphData.getFront().get(word).elementAt(i));
        }
        wordpath.remove(wordpath.size() - 1);
    }

    public static String[] execute(WordsGraphData wordsGraphData, String word1, String word2) {
        String[] temp_result = new String[1];

        if (!wordsGraphData.containsWords(word1) || !wordsGraphData.containsWords(word2)) {
            if (!wordsGraphData.containsWords(word1) && ! wordsGraphData.containsWords(word2)) {
                temp_result[0] = "No '" + word1 + "' and '" + word2 + "' in the graph!";
            } else if (!wordsGraphData.containsWords(word1)) {
                temp_result[0] = "No '" + word1 + "' in the graph!";
            } else {
                temp_result[0] = "No '" + word2 + "' in the graph!";
            }
            return temp_result;
        }
        shortpath(wordsGraphData, word1);
        if (wordsGraphData.getShortdistance().get(word2).equals(wordsGraphData.getINF())) {
            return new String[]{"There is no path from word1 to word2."};
        }

        getpath(wordsGraphData, word2);
        String[] shortestpath = new String[wordsGraphData.getRecordpath().size()];
        for (int i = 0; i < wordsGraphData.getRecordpath().size(); i++) {
            shortestpath[i] = wordsGraphData.getRecordpath().get(i);
        }
        return shortestpath;
    }

}
