package org.hitbioinfo.exp1;

import java.util.ArrayList;
import java.util.List;

public class QueryBridgeWordsControl {
    public static String[] execute(WordsGraphData mwordsgraph, String word1, String word2) {
        List<String> tempbridgewords = new ArrayList<>();
        if (mwordsgraph.adjacentVertices(word1).size() == 0) {
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
}
