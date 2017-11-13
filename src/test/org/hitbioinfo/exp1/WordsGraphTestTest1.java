package test.org.hitbioinfo.exp1;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.hitbioinfo.exp1.WordsGraph;

/**
* WordsGraphTest Tester.
*
* @author <Authors name>
* @since <pre>Ê®Ò»ÔÂ 13, 2017</pre>
* @version 1.0
*/
public class WordsGraphTestTest1 {

    @Before
    public void before() throws Exception {
    }


    @After
    public void after() throws Exception {
    }

/**
*
* Method: calcshortestpath()
*
*/
    @Test
    public void calcshortestpath() throws Exception {
        WordsGraph testGraph = new WordsGraph("Nowadays, more and more college students @@@ permanent and security and more want to look for college security more. now winter is coming.");

        String[] result = testGraph.calcshortestpath("coming", "more");
        System.out.println(result[0]);
        Assert.assertEquals("There is no path from word1 to word2.", result[0]);
    }


} 
