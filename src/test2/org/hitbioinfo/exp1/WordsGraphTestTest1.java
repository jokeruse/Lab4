package test2.org.hitbioinfo.exp1;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.hitbioinfo.exp1.WordsGraph;
public class WordsGraphTestTest1 {

    @Test
    public void calcshortestpath() throws Exception {
        WordsGraph testGraph = new WordsGraph("Every year, when the Spring Festival comes, \n" +
                "which is the biggest festival in China, \n" +
                "come back to their hometown and spend the day together");

        String[] result = testGraph.querybridgewords("together", "spend");
        System.out.println(result);
        Assert.assertEquals(new String[]{}, result);
    }


} 
