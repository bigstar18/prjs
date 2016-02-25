package gnnt.MEBS.zcjs.services;

import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.zcjs.dao.TradeTimeDao;
import gnnt.MEBS.zcjs.model.TradeTime;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("z_tradeTimeService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class TradeTimeService
{
  @Autowired
  @Qualifier("z_tradeTimeDao")
  private TradeTimeDao tradeTimeDao;
  
  @Transactional(propagation=Propagation.REQUIRED, readOnly=true)
  public List<Map<String, Object>> getTableList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    return this.tradeTimeDao.getTableList(paramQueryConditions, paramPageInfo);
  }
  
  public List<TradeTime> getObjectList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    return this.tradeTimeDao.getObjectList(paramQueryConditions, paramPageInfo);
  }
  
  public TradeTime getObject(int paramInt)
  {
    return this.tradeTimeDao.getObject(paramInt);
  }
  
  public void update(TradeTime paramTradeTime)
  {
    this.tradeTimeDao.update(paramTradeTime);
  }
  
  public void add(TradeTime paramTradeTime)
  {
    paramTradeTime.setSerialNumber(this.tradeTimeDao.getId() + 1);
    this.tradeTimeDao.add(paramTradeTime);
  }
  
  public void delete(int[] paramArrayOfInt)
  {
    TradeTime localTradeTime = new TradeTime();
    for (int i = 0; i < paramArrayOfInt.length; i++)
    {
      localTradeTime.setSerialNumber(paramArrayOfInt[i]);
      this.tradeTimeDao.delete(localTradeTime);
    }
  }
  
  public boolean isCross(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    boolean bool = false;
    String[] arrayOfString1 = paramString1.split(":");
    String[] arrayOfString2 = paramString2.split(":");
    String[] arrayOfString3 = paramString3.split(":");
    String[] arrayOfString4 = paramString4.split(":");
    int i = Integer.parseInt(arrayOfString1[0]);
    int j = Integer.parseInt(arrayOfString1[1]);
    int k = Integer.parseInt(arrayOfString2[0]);
    int m = Integer.parseInt(arrayOfString2[1]);
    int n = Integer.parseInt(arrayOfString3[0]);
    int i1 = Integer.parseInt(arrayOfString3[1]);
    int i2 = Integer.parseInt(arrayOfString4[0]);
    int i3 = Integer.parseInt(arrayOfString4[1]);
    if ((k < n) || (i2 < i)) {
      bool = false;
    } else if ((k == n) && (m < i1)) {
      bool = false;
    } else if ((i2 == i) && (i3 < j)) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
}
