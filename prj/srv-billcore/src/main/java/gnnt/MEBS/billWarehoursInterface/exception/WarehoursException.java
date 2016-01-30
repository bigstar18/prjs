package gnnt.MEBS.billWarehoursInterface.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

public class WarehoursException
  extends RuntimeException
{
  private static final long serialVersionUID = -5187137090446926964L;
  
  public WarehoursException(String string)
  {
    super(string);
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
}
