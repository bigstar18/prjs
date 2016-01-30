package gnnt.MEBS.billWarehoursInterface.util;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Properties;

public class GnntProperties
  extends Properties
{
  private static final long serialVersionUID = -6912591098520979084L;
  private static GnntProperties instance;
  private static String filePath = "gnnt.properties";
  
  public static GnntProperties getInstance()
  {
    if (instance != null) {
      return instance;
    }
    makeInstance();
    return instance;
  }
  
  private static synchronized void makeInstance()
  {
    if (instance == null) {
      instance = new GnntProperties();
    }
  }
  
  private GnntProperties()
  {
    ClassLoader loader = Thread.currentThread().getContextClassLoader();
    InputStream is = loader.getResourceAsStream(filePath);
    try
    {
      load(is);
    }
    catch (Exception e)
    {
      System.err.println("错误：没有读取属性文件，请确认db.property文件是否存在。");
      return;
    }
  }
}
