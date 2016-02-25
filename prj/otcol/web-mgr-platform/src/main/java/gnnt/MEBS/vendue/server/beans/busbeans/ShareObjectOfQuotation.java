package gnnt.MEBS.vendue.server.beans.busbeans;

import java.io.PrintStream;
import java.util.Map;

public class ShareObjectOfQuotation
{
  private Map sharedData = null;
  private String xmlForNullPartition = null;
  private static ShareObjectOfQuotation singleInstance = new ShareObjectOfQuotation();
  
  private ShareObjectOfQuotation()
  {
    System.out.println("===============hello, 共享区单例行情对象初始化===========");
  }
  
  public static ShareObjectOfQuotation getSingleInstance()
  {
    return singleInstance;
  }
  
  public synchronized Map getSharedData()
  {
    return this.sharedData;
  }
  
  public synchronized void setSharedData(Map paramMap, String paramString)
  {
    this.sharedData = paramMap;
    this.xmlForNullPartition = paramString;
  }
  
  public synchronized String getXmlForNullPartition()
  {
    return this.xmlForNullPartition;
  }
}
