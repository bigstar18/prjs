package gnnt.bank.otc.util;

import gnnt.bank.platform.util.Configuration;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Properties;
import org.apache.log4j.Logger;

public class Util
{
  public static void log(String content)
  {
    Logger plog = Logger.getLogger("Processorlog");
    plog.debug(content);
  }
  
  public static String getExceptionTrace(Throwable e)
  {
    if (e != null)
    {
      StringWriter sw = new StringWriter();
      PrintWriter pw = new PrintWriter(sw);
      e.printStackTrace(pw);
      return sw.toString();
    }
    return "No Exception";
  }
  
  public static String getConfig(String key)
  {
    Properties props = new Configuration().getSection("BANK.Processor");
    return props.getProperty(key);
  }
}
