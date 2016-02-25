package gnnt.MEBS.vendue.server.bus;

import gnnt.MEBS.vendue.server.beans.busbeans.PartitionCurCommodity;
import gnnt.MEBS.vendue.server.beans.dtobeans.SysCurStatus;
import gnnt.MEBS.vendue.server.dao.CurCommodityDao;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.apache.log4j.Logger;

public class PartitonAndSectionMonitorService
{
  private static Logger logger = Logger.getLogger("QuotationCachelog");
  private CurCommodityDao dao = new CurCommodityDao();
  private CurCommodityCacheService curCommodityCacheService = null;
  private long lastNumOfCurCommodity = 0L;
  
  public void setCurCommodityCacheService(CurCommodityCacheService paramCurCommodityCacheService)
  {
    this.curCommodityCacheService = paramCurCommodityCacheService;
  }
  
  public void monitor()
  {
    logger.debug("into monitor....");
    try
    {
      if (needReFetchAllParationAndAllCurCommodity())
      {
        logger.debug("需要强制调用进行刷新!");
        this.curCommodityCacheService.buildSharedData();
      }
    }
    catch (Exception localException)
    {
      localException = localException;
      localException.printStackTrace();
      logger.error(localException);
    }
    finally {}
    logger.debug("out monitor...");
  }
  
  private boolean needReFetchAllParationAndAllCurCommodity()
    throws Exception
  {
    if (numCurCommodityIsReduce()) {
      return true;
    }
    Map localMap1 = this.dao.getAllPartion();
    Map localMap2 = CurCommodityCacheService.getSharedMap();
    if (localMap2.size() != localMap1.size()) {
      return true;
    }
    Iterator localIterator = localMap1.entrySet().iterator();
    logger.debug(localMap2.size() + "");
    for (int i = 0; i < localMap1.size(); i++)
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      PartitionCurCommodity localPartitionCurCommodity1 = (PartitionCurCommodity)localEntry.getValue();
      Long localLong1 = localPartitionCurCommodity1.getSysCurStatus().getSection();
      Long localLong2 = localPartitionCurCommodity1.getSysCurStatus().getStatus();
      PartitionCurCommodity localPartitionCurCommodity2 = (PartitionCurCommodity)localMap2.get(localEntry.getKey());
      if (localPartitionCurCommodity2 == null) {
        return true;
      }
      Long localLong3 = localPartitionCurCommodity2.getSysCurStatus().getStatus();
      Long localLong4 = localPartitionCurCommodity2.getSysCurStatus().getSection();
      if ((localLong2.longValue() != localLong3.longValue()) || (localLong1.longValue() != localLong4.longValue()))
      {
        logger.debug("here");
        return true;
      }
    }
    return false;
  }
  
  private boolean numCurCommodityIsReduce()
    throws Exception
  {
    long l = this.dao.getNumOfAllCommodity();
    boolean bool;
    if (this.lastNumOfCurCommodity != l) {
      bool = true;
    } else {
      bool = false;
    }
    this.lastNumOfCurCommodity = l;
    return bool;
  }
  
  public static void main(String[] paramArrayOfString)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("1", "1");
    Iterator localIterator = localHashMap.entrySet().iterator();
    for (int i = 0; i < localHashMap.size(); i++)
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      System.out.println(localEntry.getKey());
      System.out.println(localEntry.getValue());
    }
  }
}
