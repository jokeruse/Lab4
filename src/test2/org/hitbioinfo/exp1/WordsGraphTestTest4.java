package test2.org.hitbioinfo.exp1;
import org.hitbioinfo.exp1.WordsGraph;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class WordsGraphTestTest4 {

@Test
public void testCalcshortestpath() throws Exception { 
//TODO: Test goes here...
    WordsGraph testGraph = new WordsGraph("Every year, when the Spring Festival comes, \n" +
            "which is the biggest festival in China, \n" +
            "come back to their hometown and spend the day together");

    String[] result = testGraph.querybridgewords("the", "in");

    System.out.println(result);
    Assert.assertEquals(new String[]{}, result);
} 


} 
