package gnnt.MEBS.bill.core.junit;

import gnnt.MEBS.bill.core.Server;
import gnnt.MEBS.bill.core.service.ITradeService;
import gnnt.MEBS.bill.core.util.GnntBeanFactory;
import gnnt.MEBS.bill.core.vo.ResultVO;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.textui.TestRunner;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class StockTradeJunit
  extends TestCase
{
  private Log log = LogFactory.getLog(StockTradeJunit.class);
  ITradeService tradeService;
  
  public StockTradeJunit(String paramString)
  {
    super(paramString);
    gnnt.MEBS.bill.core.ServerShell.OpenRMIService = false;
    gnnt.MEBS.bill.core.ServerShell.OpenTask = false;
    Server localServer = Server.getInstance();
    localServer.init();
    localServer.initServer();
    this.tradeService = ((ITradeService)GnntBeanFactory.getBean("tradeService"));
  }
  
  public void testRejectFinancing()
  {
    ResultVO localResultVO = this.tradeService.performRejectFinancing(2L);
    if (localResultVO.getResult() <= 0L)
    {
      this.log.debug(localResultVO.getErrorInfo());
      fail("发生错误:" + localResultVO.getErrorInfo());
    }
  }
  
  public static Test suite()
  {
    return new TestSuite(StockTradeJunit.class);
  }
  
  public static void main(String[] paramArrayOfString)
  {
    TestSuite localTestSuite = new TestSuite();
    localTestSuite.addTest(new StockTradeJunit("test")
    {
      protected void runTest()
      {
        testRejectFinancing();
      }
    });
    TestRunner.run(localTestSuite);
  }
}
