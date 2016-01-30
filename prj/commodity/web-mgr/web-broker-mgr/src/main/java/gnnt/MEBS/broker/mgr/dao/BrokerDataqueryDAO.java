package gnnt.MEBS.broker.mgr.dao;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

public abstract interface BrokerDataqueryDAO
{
  public abstract List getBrokerTradeFee(HttpServletRequest paramHttpServletRequest);

  public abstract List getBreedTradeFee(HttpServletRequest paramHttpServletRequest);

  public abstract List getFirmTradeFee(HttpServletRequest paramHttpServletRequest);
}