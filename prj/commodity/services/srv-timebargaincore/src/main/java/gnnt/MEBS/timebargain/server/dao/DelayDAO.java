package gnnt.MEBS.timebargain.server.dao;

import gnnt.MEBS.timebargain.server.model.ApplyBill;
import gnnt.MEBS.timebargain.server.model.DelayOrder;
import gnnt.MEBS.timebargain.server.model.DelayQuotation;
import gnnt.MEBS.timebargain.server.model.DelayStatus;
import java.util.List;
import java.util.Map;

public abstract interface DelayDAO
  extends DAO
{
  public abstract List getDelayTradeTimes();
  
  public abstract DelayStatus getDelayStatus();
  
  public abstract void updateDelayStatus(DelayStatus paramDelayStatus);
  
  public abstract void updateDelayRecoverTime(String paramString);
  
  public abstract long buySettleOrder(DelayOrder paramDelayOrder);
  
  public abstract long sellSettleOrder(DelayOrder paramDelayOrder);
  
  public abstract int withdraw(DelayOrder paramDelayOrder);
  
  public abstract int settleMatch();
  
  public abstract int neutralMatch();
  
  public abstract void updateDelayQuotation();
  
  public abstract void updateDelaySettleQuotation();
  
  public abstract List getNotTradeOrderNos();
  
  public abstract Map getNeutralSideMap();
  
  public abstract void updateNeutralSide(DelayQuotation paramDelayQuotation);
  
  public abstract int delaySettleBill(ApplyBill paramApplyBill);
  
  public abstract int delaySettleBillCancel(ApplyBill paramApplyBill);
  
  public abstract long buyNeutralOrder(DelayOrder paramDelayOrder);
  
  public abstract long sellNeutralOrder(DelayOrder paramDelayOrder);
  
  public abstract void loadCommoditySettleProp(Map paramMap);
}
