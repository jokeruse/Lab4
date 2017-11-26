package org.hitbioinfo.exp1;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class ShowDirectedGraphControl {
    // Add an arc into graph-viz format file.
    private static String createArcFormat(String src, String tgt, int weight, boolean isEmphasized) {
        StringBuilder builder = new StringBuilder();
        builder.append(src).append("->").append(tgt).append("[label=").append(weight);
        if (isEmphasized) {
            builder.append(" color=red];");
        } else {
            builder.append("];");
        }

        return builder.toString();
    }

    private static void createDotGraph(String dotFormat, String fileName) {
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

    public static void execute(WordsGraphData wordsGraphData) {
        StringBuilder builder = new StringBuilder();

        // Create dot format string.
        for (String src : wordsGraphData.getWordsSet()) {
            List<String> adjVertices = wordsGraphData.getMwordsgraph().adjacentVertices(src);
            if (adjVertices.size() == 0) {
                continue;
            }
            for (String tgt : adjVertices) {
                String temp;
                int weight = wordsGraphData.getMwordsgraph().getWeight(src, tgt);
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
}
