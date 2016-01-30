package gnnt.MEBS.common.broker.common;

import gnnt.MEBS.common.broker.statictools.ApplicationContextInit;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ReturnValue
{
  private Log log = LogFactory.getLog(ReturnValue.class);
  private int result = -1;
  private Map<Long, String> errorInfoMap = new HashMap();

  public int getResult()
  {
    return this.result;
  }

  public void setResult(int paramInt)
  {
    this.result = paramInt;
  }

  public Map<Long, String> getInfoMap()
  {
    return this.errorInfoMap;
  }

  public String getInfo()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    Iterator localIterator = this.errorInfoMap.entrySet().iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      if (this.result == 1)
        localStringBuffer.append((String)localEntry.getValue()).append("\\n");
      else
        localStringBuffer.append((String)localEntry.getValue()).append("\\n");
    }
    return localStringBuffer.toString();
  }

  public void addInfo(long paramLong)
  {
    this.errorInfoMap.put(Long.valueOf(paramLong), ApplicationContextInit.getErrorInfo(paramLong + ""));
  }

  public void addInfo(long paramLong, Object[] paramArrayOfObject)
  {
    String str1 = ApplicationContextInit.getErrorInfo(paramLong + "");
    String str2 = str1;
    try
    {
      str2 = String.format(str1, paramArrayOfObject);
    }
    catch (Exception localException)
    {
      this.log.debug("errorCode:" + paramLong + ";Format Exception!");
      this.log.debug("formatStr:" + str1);
      for (Object localObject : paramArrayOfObject)
        this.log.debug("Object:" + localObject.toString());
      this.log.debug(localException.toString());
    }
    this.errorInfoMap.put(Long.valueOf(paramLong), str2.replaceAll("\n", ""));
  }
}