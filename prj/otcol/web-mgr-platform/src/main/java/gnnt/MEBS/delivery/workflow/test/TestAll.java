package gnnt.MEBS.delivery.workflow.test;

import junit.framework.Test;
import junit.framework.TestSuite;

public class TestAll
{
  public static Test suite()
  {
    TestSuite localTestSuite = new TestSuite("All tests from part");
    localTestSuite.addTestSuite(EnterApplyServerTest.class);
    localTestSuite.addTestSuite(EnterApplyTest.class);
    localTestSuite.addTestSuite(EnterWareDaoTest.class);
    localTestSuite.addTestSuite(EnterWareServiceTest.class);
    localTestSuite.addTestSuite(OutWareServerTest.class);
    localTestSuite.addTestSuite(OutWareTest.class);
    localTestSuite.addTestSuite(RegStockApplyServerTest.class);
    localTestSuite.addTestSuite(RegStockApplyTest.class);
    localTestSuite.addTestSuite(RegStockToEnterWareServiceTest.class);
    localTestSuite.addTestSuite(RegStockToEnterWareTest.class);
    return localTestSuite;
  }
}
