package gnnt.MEBS.common.mgr.junit;

import java.io.PrintStream;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

public class GnntBeanFactoryJunit
  extends TestCase
{
  public GnntBeanFactoryJunit(String paramString)
  {
    super(paramString);
  }
  
  public void testGetConfig()
  {
    String str = GnntBeanFactory.getConfig("Test");
    System.out.println(str);
    assertEquals(str, "Test");
  }
  
  public void testGetErrorCode()
  {
    String str = GnntBeanFactory.getErrorInfo("999998");
    System.out.println(str);
    assertEquals(str, "出现未知错误，请联系管理员");
  }
  
  public static Test suite()
  {
    return new TestSuite(GnntBeanFactoryJunit.class);
  }
  
  public static void main(String[] paramArrayOfString)
  {
    TestRunner.run(suite());
  }
}
