package gnnt.MEBS.timebargain.server.dao.sim;

import gnnt.MEBS.timebargain.server.dao.DAO;
import java.util.Date;
import java.util.List;

public abstract interface SimHqDAO
  extends DAO
{
  public abstract List getQuotationList(String paramString);
  
  public abstract List getQuotationList();
  
  public abstract List getCommodityList(String paramString);
  
  public abstract List getCommodityList();
  
  public abstract Date getCurDbDate();
}
