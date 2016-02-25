package gnnt.MEBS.vendue.server.bus;

import gnnt.MEBS.vendue.server.beans.dtobeans.Commodity;
import java.util.List;

public abstract interface Quotation
{
  public abstract void setPartitionId(Long paramLong);
  
  public abstract String getLastXML(long paramLong);
  
  public abstract List getList();
  
  public abstract Commodity getDetail(String paramString);
}
