package gnnt.MEBS.transformhq.server.quotation;

import gnnt.MEBS.transformhq.server.model.HQBean;
import gnnt.MEBS.transformhq.server.model.IPConfig;
import gnnt.MEBS.transformhq.server.model.InCommodity;
import java.util.List;
import java.util.Map;

public abstract interface QuotationInterFace
{
  public abstract List<IPConfig> getIPConfigList();
  
  public abstract IPConfig getUseIPConfig();
  
  public abstract void setHQBean(HQBean paramHQBean);
  
  public abstract Map<String, InCommodity> getInCommodity();
  
  public abstract void setClientVersion(String paramString);
  
  public abstract String getClientVersion();
  
  public abstract void start();
}
