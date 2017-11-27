package test2;

import org.hitbioinfo.exp1.CalcShortestPathControl;
import org.hitbioinfo.exp1.WordsGraphData;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class    CalcShortestPathControlTest2 {
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void execute() throws Exception {
        WordsGraphData testGraph = new WordsGraphData("Nowadays, more and more college students @@@ permanent and security and more want to look for college security more. now winter is coming.");
        String[] result = CalcShortestPathControl.execute(testGraph, "and", "more");
        System.out.println(result[0]);
        Assert.assertEquals("and -> more", result[0]);
        System.out.println(result[1]);
        Assert.assertEquals("and -> security -> more", result[1]);
    }

}