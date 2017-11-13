package test2.org.hitbioinfo.exp1;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.hitbioinfo.exp1.WordsGraph;

public class WordsGraphTestTest3 {

@Test
public void testCalcshortestpath() throws Exception {
    WordsGraph testGraph = new WordsGraph("Every year, when the Spring Festival comes, \n" +
            "which is the biggest festival in China, \n" +
            "come back to their hometown and spend the day together");

    String[] result = testGraph.querybridgewords("the", "festival");

    System.out.println(result[0]);
    System.out.println(result[1]);
    Assert.assertEquals("spring", result[0]);
    Assert.assertEquals("biggest", result[1]);
}


} 
