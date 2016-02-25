package gnnt.MEBS.vendue.server.bus;

import gnnt.MEBS.vendue.server.beans.busbeans.BroadcastHighBuffer;
import gnnt.MEBS.vendue.server.beans.busbeans.BroadcastPack;
import gnnt.MEBS.vendue.server.beans.busbeans.ShareObjectOfBroadcast;
import gnnt.MEBS.vendue.server.beans.dtobeans.Broadcast;
import gnnt.MEBS.vendue.server.dao.BroadcastDao;
import gnnt.MEBS.vendue.util.Configuration;
import java.io.PrintStream;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class BroadcastCacheService
{
  private static final String CacheXmlConstant_R = "<R>";
  private static final String CacheXmlConstant_R_End = "</R>";
  private static final String CacheXmlConstant_I = "<I>";
  private static final String CacheXmlConstant_I_End = "</I>";
  private static final String CacheXmlConstant_ID = "<ID>";
  private static final String CacheXmlConstant_ID_End = "</ID>";
  private static final String CacheXmlConstant_TITLE = "<Title>";
  private static final String CacheXmlConstant_TITLE_End = "</Title>";
  private static final String CacheXmlConstant_AUTHOR = "<Author>";
  private static final String CacheXmlConstant_AUTHOR_End = "</Author>";
  private static final String CacheXmlConstant_TYPE = "<Type>";
  private static final String CacheXmlConstant_TYPE_End = "</Type>";
  private static final String CacheXmlConstant_TIME = "<Time>";
  private static final String CacheXmlConstant_TIME_End = "</Time>";
  private static final String CacheXmlConstant_XmlFileHead = "<?xml version=\"1.0\" encoding=\"GB2312\"?>";
  private static final String CacheXmlConstant_CHECKTIME = "<LT>";
  private static final String CacheXmlConstant_CHECKTIME_End = "</LT>";
  private static Long broadcastHighBufferNumber;
  private Timestamp lastUpdatetime;
  
  public String getLastXML(Timestamp paramTimestamp)
  {
    BroadcastPack localBroadcastPack = ShareObjectOfBroadcast.getSingleInstance().getBroadcastPack();
    if (paramTimestamp == null) {
      return buildReturnXml(localBroadcastPack, paramTimestamp);
    }
    List localList = localBroadcastPack.getBroadcastHighBuffer().getHighBufferCheckTimeList();
    if ((localList != null) && (localList.size() > 0))
    {
      Timestamp localTimestamp = (Timestamp)localList.get(0);
      if (paramTimestamp.compareTo(localTimestamp) < 0) {
        return buildReturnXml(localBroadcastPack, paramTimestamp);
      }
      Map localMap = localBroadcastPack.getBroadcastHighBuffer().getHighBufferBroadcastMap();
      return (String)localMap.get(paramTimestamp);
    }
    return buildReturnXml(localBroadcastPack, paramTimestamp);
  }
  
  public void buildSharedData()
  {
    try
    {
      BroadcastDao localBroadcastDao = new BroadcastDao();
      ShareObjectOfBroadcast localShareObjectOfBroadcast = ShareObjectOfBroadcast.getSingleInstance();
      BroadcastPack localBroadcastPack = null;
      localBroadcastDao.fetchBroadcast(this.lastUpdatetime);
      Timestamp localTimestamp1 = localBroadcastDao.getNewestUpdateTime();
      Timestamp localTimestamp2 = localBroadcastDao.getCurrentDbTime();
      if (localTimestamp1 == null)
      {
        localBroadcastPack = (BroadcastPack)localShareObjectOfBroadcast.getBroadcastPack().clone();
      }
      else
      {
        if (this.lastUpdatetime == null) {
          localBroadcastPack = new BroadcastPack();
        } else {
          localBroadcastPack = (BroadcastPack)localShareObjectOfBroadcast.getBroadcastPack().clone();
        }
        List localList = localBroadcastDao.getNewestBroadcastTitleList();
        processModifiedBroadcast(localList, localBroadcastPack.getBroadCastList());
        this.lastUpdatetime = localTimestamp1;
      }
      clearMemoryIdWhichNotExistInDB(localBroadcastPack.getBroadCastList(), localBroadcastDao.fetchAllBroadcastId());
      checkDataInMemory(localTimestamp2, localBroadcastPack.getBroadCastList());
      localBroadcastPack.setNewestCheckTime(localTimestamp2);
      buildBroadcastHighBuffer(localBroadcastPack, localTimestamp2);
      localShareObjectOfBroadcast.setBroadcastPack(localBroadcastPack);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
  
  private void clearMemoryIdWhichNotExistInDB(List paramList1, List paramList2)
  {
    for (int i = paramList1.size() - 1; i >= 0; i--)
    {
      Broadcast localBroadcast = (Broadcast)paramList1.get(i);
      Long localLong = localBroadcast.getId();
      if (!paramList2.contains(localLong)) {
        paramList1.remove(i);
      }
    }
  }
  
  private void buildBroadcastHighBuffer(BroadcastPack paramBroadcastPack, Timestamp paramTimestamp)
  {
    List localList = paramBroadcastPack.getBroadcastHighBuffer().getHighBufferCheckTimeList();
    if (broadcastHighBufferNumber.longValue() <= 0L) {
      return;
    }
    if (localList.size() >= broadcastHighBufferNumber.longValue()) {
      localList.remove(0);
    }
    localList.add(paramTimestamp);
    HashMap localHashMap = new HashMap();
    for (int i = 0; i < localList.size(); i++)
    {
      Timestamp localTimestamp = (Timestamp)localList.get(i);
      String str = buildReturnXml(paramBroadcastPack, localTimestamp);
      localHashMap.put(localTimestamp, str);
    }
    paramBroadcastPack.getBroadcastHighBuffer().setHighBufferBroadcastMap(localHashMap);
  }
  
  private void checkDataInMemory(Timestamp paramTimestamp, List paramList)
  {
    clearOverdueBroadcastList(paramTimestamp, paramList);
    for (int i = 0; i < paramList.size(); i++)
    {
      Broadcast localBroadcast = (Broadcast)paramList.get(i);
      if ((localBroadcast.getActiveTime() == null) && (paramTimestamp.compareTo(localBroadcast.getSendtime()) >= 1)) {
        localBroadcast.setActiveTime(paramTimestamp);
      }
    }
  }
  
  private void clearOverdueBroadcastList(Timestamp paramTimestamp, List paramList)
  {
    for (int i = paramList.size() - 1; i >= 0; i--)
    {
      Broadcast localBroadcast = (Broadcast)paramList.get(i);
      if (localBroadcast.getEndtime().compareTo(paramTimestamp) < 0) {
        paramList.remove(i);
      }
    }
  }
  
  private void processModifiedBroadcast(List paramList1, List paramList2)
  {
    makeXmlStringBufferOfBroadcast(paramList1);
    for (int i = paramList1.size() - 1; i >= 0; i--)
    {
      Broadcast localBroadcast1 = (Broadcast)paramList1.get(i);
      for (int k = paramList2.size() - 1; k >= 0; k--) {
        if (((Broadcast)paramList2.get(k)).getId().equals(localBroadcast1.getId()))
        {
          paramList2.remove(k);
          break;
        }
      }
      paramList2.add(localBroadcast1);
    }
    for (i = 0; i < paramList2.size() - 1; i++) {
      for (int j = 0; j < paramList2.size() - i - 1; j++) {
        if (((Broadcast)paramList2.get(j)).getTitle().compareTo(((Broadcast)paramList2.get(j + 1)).getTitle()) < 0)
        {
          Broadcast localBroadcast2 = (Broadcast)paramList2.get(j);
          paramList2.set(j, (Broadcast)paramList2.get(j + 1));
          paramList2.set(j + 1, localBroadcast2);
        }
      }
    }
  }
  
  private void makeXmlStringBufferOfBroadcast(List paramList)
  {
    for (int i = 0; i < paramList.size(); i++)
    {
      Broadcast localBroadcast = (Broadcast)paramList.get(i);
      localBroadcast.setXmlStringBuffer(buildBroadcastXmlString(localBroadcast));
    }
  }
  
  private StringBuffer buildBroadcastXmlString(Broadcast paramBroadcast)
  {
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Timestamp localTimestamp = paramBroadcast.getUpdatetime();
    String str = null;
    if (localTimestamp == null) {
      str = "";
    } else {
      str = localSimpleDateFormat.format(localTimestamp);
    }
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("<I>").append("<ID>").append(paramBroadcast.getId()).append("</ID>").append("<Title>").append(paramBroadcast.getTitle()).append("</Title>").append("<Author>").append(paramBroadcast.getAuthor()).append("</Author>").append("<Type>").append(paramBroadcast.getType()).append("</Type>").append("</I>");
    return localStringBuffer;
  }
  
  private String buildReturnXml(BroadcastPack paramBroadcastPack, Timestamp paramTimestamp)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("<?xml version=\"1.0\" encoding=\"GB2312\"?>").append("<R>");
    List localList = paramBroadcastPack.getBroadCastList();
    for (int i = localList.size() - 1; i >= 0; i--)
    {
      localObject = (Broadcast)localList.get(i);
      if (((Broadcast)localObject).getActiveTime() != null) {
        if (paramTimestamp == null) {
          localStringBuffer.append(((Broadcast)localObject).getXmlStringBuffer());
        } else if (((Broadcast)localObject).getActiveTime().compareTo(paramTimestamp) > 0) {
          localStringBuffer.append(((Broadcast)localObject).getXmlStringBuffer());
        }
      }
    }
    Timestamp localTimestamp = paramBroadcastPack.getNewestCheckTime();
    Object localObject = null;
    if (localTimestamp == null) {
      localObject = "";
    } else {
      localObject = localTimestamp.toString();
    }
    localStringBuffer.append("<LT>").append((String)localObject).append("</LT>");
    localStringBuffer.append("</R>");
    return localStringBuffer.toString();
  }
  
  static
  {
    try
    {
      Properties localProperties = new Configuration().getSection("MEBS.Partition");
      String str = localProperties.getProperty("HighBufferNumber");
      if ((str == null) || (str.equals(""))) {
        broadcastHighBufferNumber = new Long(20L);
      } else {
        broadcastHighBufferNumber = new Long(str);
      }
      System.out.println("广播缓存区大小: " + broadcastHighBufferNumber);
    }
    catch (Exception localException)
    {
      broadcastHighBufferNumber = new Long(20L);
    }
  }
}
