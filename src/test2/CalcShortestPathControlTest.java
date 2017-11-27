package test2;

import com.sun.org.apache.bcel.internal.generic.RET;
import org.hitbioinfo.exp1.CalcShortestPathControl;
import org.hitbioinfo.exp1.WordsGraphData;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.hitbioinfo.exp1.QueryBridgeWordsControl;


public class CalcShortestPathControlTest {
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void execute() throws Exception {
        WordsGraphData testGraph = new WordsGraphData("Nowadays, more and more college students @@@ permanent and security and more want to look for college security more. now winter is coming.");
        String[] result = CalcShortestPathControl.execute(testGraph, "more", "college");
        System.out.println(result[0]);
        Assert.assertEquals("more -> college", result[0]);
    }

}