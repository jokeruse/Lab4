package test1;

import org.hitbioinfo.exp1.QueryBridgeWordsControl;
import org.hitbioinfo.exp1.WordsGraphData;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class QueryBridgeWordsControlTest2 {
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void execute() throws Exception {
        WordsGraphData testGraph = new WordsGraphData("Every year, when the Spring Festival comes, \n" +
                            "which is the biggest festival in China, \n" +
                           "come back to their hometown and spend the day together");
        String[] result = QueryBridgeWordsControl.execute(testGraph, "the", "festival");
        System.out.println(result[0]);
        System.out.println(result[1]);
        Assert.assertEquals("spring", result[0]);
        Assert.assertEquals("biggest", result[1]);
    }

}