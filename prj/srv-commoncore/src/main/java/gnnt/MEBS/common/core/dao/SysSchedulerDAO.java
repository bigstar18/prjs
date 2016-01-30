package gnnt.MEBS.common.core.dao;

import gnnt.MEBS.common.core.po.MarketInfoPO;
import gnnt.MEBS.common.core.po.TradeModelPO;
import java.util.Date;

public abstract interface SysSchedulerDAO
{
  public abstract TradeModelPO getTradeModel(int paramInt);
  
  public abstract Date getCurDbDate();
  
  public abstract void moveHistory(Date paramDate);
  
  public abstract MarketInfoPO getMarketInfo(String paramString);
}
