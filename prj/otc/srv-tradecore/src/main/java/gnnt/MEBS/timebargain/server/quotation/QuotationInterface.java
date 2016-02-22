package gnnt.MEBS.timebargain.server.quotation;

import gnnt.MEBS.timebargain.server.model.HQServerInfo;
import gnnt.MEBS.timebargain.server.model.Quotation;
import java.util.List;

public abstract interface QuotationInterface
{
  public abstract List<HQServerInfo> getHQServerInfoList();
  
  public abstract boolean getTraderOrderStatus();
  
  public abstract void setCurServerInfo(HQServerInfo paramHQServerInfo);
  
  public abstract void setQuotation(Quotation paramQuotation);
  
  public abstract void init();
  
  public abstract void dispose();
}
