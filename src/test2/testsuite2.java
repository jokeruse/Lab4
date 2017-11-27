package test2;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import test1.QueryBridgeWordsControlTest;
import test1.QueryBridgeWordsControlTest2;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        CalcShortestPathControlTest.class,
        CalcShortestPathControlTest2.class,

})
public class testsuite2 {

}