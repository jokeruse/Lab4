package org.hitbioinfo.exp1;

import java.util.*;

public class CalcShortestPathFromOneControl {
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

    public static String[] execute(WordsGraphData wordsGraphData, String word) {
        shortpath(wordsGraphData, word);
        String[] path = new String[wordsGraphData.getMsize() - 1];
        int cnt = 0;
        for (String i : wordsGraphData.getWordsSet()) {
            if (i.equals(word)) {
                continue;
            }
            if (wordsGraphData.getShortdistance().get(i).equals(wordsGraphData.getINF())) {
                path[cnt++] = word + " -> " + i + ": Unreachable";
                continue;
            }
            StringBuilder p = new StringBuilder("");
            Stack<String> q = new Stack<>();
            String temp = i;
            while (!wordsGraphData.getFront().get(temp).elementAt(0).equals("")) {
                q.push(temp);
                temp = wordsGraphData.getFront().get(temp).elementAt(0);

            }
            p.append(word);
            while (!q.empty()) {
                p.append(" -> ").append(q.pop());
            }
            path[cnt++] = p.toString();
        }
        return path;
    }
}
