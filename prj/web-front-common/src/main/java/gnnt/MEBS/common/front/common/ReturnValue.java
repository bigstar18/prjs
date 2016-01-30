package gnnt.MEBS.common.front.common;

import gnnt.MEBS.common.front.statictools.ApplicationContextInit;
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
  private int resultType = -1;
  private Map<Long, String> errorInfoMap = new HashMap();
  
  public int getResult()
  {
    return this.result;
  }
  
  public void setResult(int paramInt)
  {
    this.result = paramInt;
  }
  
  public int getResultType()
  {
    return this.resultType;
  }
  
  public void setResultType(int paramInt)
  {
    this.resultType = paramInt;
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
      localStringBuffer.append((String)localEntry.getValue());
    }
    return localStringBuffer.toString();
  }
  
  public String getDetailInfo()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    Iterator localIterator = this.errorInfoMap.entrySet().iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      if (this.result == 1) {
        localStringBuffer.append((String)localEntry.getValue()).append("\\n");
      } else {
        localStringBuffer.append("错误码【" + localEntry.getKey() + "】错误信息【" + (String)localEntry.getValue()).append("】\\n");
      }
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
      for (Object localObject : paramArrayOfObject) {
        this.log.debug("Object:" + localObject.toString());
      }
      this.log.debug(localException.toString());
    }
    this.errorInfoMap.put(Long.valueOf(paramLong), str2);
  }
}
