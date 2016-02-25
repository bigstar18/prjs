package gnnt.MEBS.vendue.server.bus;

import gnnt.MEBS.vendue.server.beans.dtobeans.Commodity;
import gnnt.MEBS.vendue.server.beans.dtobeans.CurCommodity;
import gnnt.MEBS.vendue.server.beans.dtobeans.QuotationValue;
import gnnt.MEBS.vendue.server.dao.QuotationValueDao;
import java.io.PrintStream;
import java.util.List;

public class QuotationService
  implements Quotation
{
  private Long partitionId;
  
  public Long getPartitionId()
  {
    return this.partitionId;
  }
  
  public void setPartitionId(Long paramLong)
  {
    this.partitionId = paramLong;
  }
  
  public String getLastXML(long paramLong)
  {
    String str = null;
    if (this.partitionId == null)
    {
      System.out.println("*****Please input partion Id!*******");
    }
    else
    {
      QuotationCacheService localQuotationCacheService = new QuotationCacheService();
      str = localQuotationCacheService.getLastXML(this.partitionId.longValue(), paramLong);
    }
    return str;
  }
  
  public List getList()
  {
    QuotationValueDao localQuotationValueDao = new QuotationValueDao();
    List localList = null;
    try
    {
      localList = localQuotationValueDao.fetchTradePartitionQuotation(this.partitionId);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return localList;
  }
  
  public Commodity getDetail(String paramString)
  {
    QuotationValueDao localQuotationValueDao = new QuotationValueDao();
    Commodity localCommodity = null;
    try
    {
      localCommodity = localQuotationValueDao.fetchCommodityInTradePartitionQuotation(this.partitionId, paramString);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return localCommodity;
  }
  
  private void getListDemo(Long paramLong)
  {
    QuotationService localQuotationService = new QuotationService();
    localQuotationService.setPartitionId(new Long(3L));
    List localList = localQuotationService.getList();
    for (int i = 0; i < localList.size(); i++)
    {
      QuotationValue localQuotationValue = (QuotationValue)localList.get(i);
      System.out.print(localQuotationValue.getCode());
      System.out.print("  ");
      System.out.print(localQuotationValue.getCountDownStart());
      System.out.print("  ");
      System.out.print(localQuotationValue.getCountDownTime());
      System.out.print("  ");
      System.out.print(localQuotationValue.getId());
      System.out.print("  ");
      System.out.print(localQuotationValue.getLastTime());
      System.out.print("  ");
      System.out.print(localQuotationValue.getPrice());
      System.out.print("  ");
      System.out.print(localQuotationValue.getSection());
      System.out.print("  ");
      System.out.print(localQuotationValue.getSubmitId());
      System.out.print("  ");
      System.out.print(localQuotationValue.getTradePartition());
      System.out.print("  ");
      System.out.print(localQuotationValue.getUserID());
      System.out.print("  ");
      System.out.print(localQuotationValue.getValidAmount());
      System.out.print("  ");
      CurCommodity localCurCommodity = localQuotationValue.getCurCommodity();
      localCurCommodity.getBargainFlag();
      localCurCommodity.getCode();
      localCurCommodity.getCommodityID();
      localCurCommodity.getLpFlag();
      localCurCommodity.getModifyTime();
      localCurCommodity.getSection();
      localCurCommodity.getTradePartition();
      Commodity localCommodity = localQuotationValue.getCommodity();
      localCommodity.getAlertPrice();
      localCommodity.getAmount();
      localCommodity.getBeginPrice();
      localCommodity.getCode();
      localCommodity.getCreateTime();
      localCommodity.getFee();
      localCommodity.getFirstTime();
      localCommodity.getID();
      localCommodity.getMinAmount();
      localCommodity.getSecurity();
      localCommodity.getSplitID();
      localCommodity.getStatus();
      localCommodity.getStepPrice();
      localCommodity.getTradeUnit();
      System.out.println();
    }
    System.out.println(localList.size());
  }
  
  public static void main(String[] paramArrayOfString)
    throws Exception
  {
    gnnt.MEBS.vendue.server.dao.BaseDao.isRunWithServer = false;
    RefreshMemoryThreadManager.runRefreshThread();
    QuotationService localQuotationService = new QuotationService();
    new QuotationValueDao().changeCountStarttimeInDbForTest();
    BroadcastService localBroadcastService = new BroadcastService();
    for (int i = 0; i < 2000; i++)
    {
      localQuotationService.setPartitionId(new Long(3L));
      System.out.println(localQuotationService.getLastXML(0L));
      Thread.sleep(200L);
    }
  }
}
