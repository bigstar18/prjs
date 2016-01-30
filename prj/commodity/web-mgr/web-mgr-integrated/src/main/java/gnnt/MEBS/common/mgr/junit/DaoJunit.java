package gnnt.MEBS.common.mgr.junit;

import gnnt.MEBS.common.mgr.dao.StandardDao;
import gnnt.MEBS.integrated.mgr.model.usermanage.MFirm;
import gnnt.MEBS.integrated.mgr.service.MFirmService;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

public class DaoJunit
  extends TestCase
{
  public DaoJunit()
  {
    super("test");
  }
  
  public DaoJunit(String paramString)
  {
    super(paramString);
  }
  
  public void testQueryBySql()
  {
    StandardDao localStandardDao = (StandardDao)GnntBeanFactory.getBean("standardDao");
    int i = 0;
    try
    {
      HashMap localHashMap = new HashMap();
      localHashMap.put("p_FirmID", "ss");
      localStandardDao.executeProcedure("{call SP_X_FirmAdd(?)}", localHashMap);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    System.out.println(i);
  }
  
  public void testMfirm()
  {
    MFirmService localMFirmService = (MFirmService)GnntBeanFactory.getBean("mfirmService");
    MFirm localMFirm1 = new MFirm();
    localMFirm1.setFirmId("888");
    MFirm localMFirm2 = (MFirm)localMFirmService.get(localMFirm1);
    System.out.println(localMFirm2);
    localMFirm2 = (MFirm)localMFirmService.get(localMFirm1);
    System.out.println(localMFirm2);
  }
  
  public static Test suite()
  {
    return new TestSuite(DaoJunit.class);
  }
  
  public static void main(String[] paramArrayOfString)
  {
    TestSuite localTestSuite = new TestSuite();
    localTestSuite.addTest(new DaoJunit("test")
    {
      protected void runTest()
      {
        testMfirm();
      }
    });
    TestRunner.run(localTestSuite);
  }
}
