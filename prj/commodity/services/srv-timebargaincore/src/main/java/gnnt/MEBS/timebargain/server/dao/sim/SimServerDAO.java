package gnnt.MEBS.timebargain.server.dao.sim;

import gnnt.MEBS.timebargain.server.model.Order;
import java.util.Date;

public abstract interface SimServerDAO
{
  public abstract long getMinOrderNo();
  
  public abstract Date getOrderTime(Order paramOrder);
  
  public abstract void insertOrder(Order paramOrder);
}
