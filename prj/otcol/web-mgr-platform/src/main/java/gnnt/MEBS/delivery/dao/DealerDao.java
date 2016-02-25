package gnnt.MEBS.delivery.dao;

import gnnt.MEBS.base.copy.CommonRowMapper;
import gnnt.MEBS.delivery.model.Dealer;
import java.util.List;

public class DealerDao
  extends DaoHelperImpl
{
  public Dealer getDealerById(String paramString)
  {
    String str = "select t.firmid,t.name,t.contactman linkman,t.phone tel from m_firm t where firmId=? ";
    Object[] arrayOfObject = { paramString };
    Dealer localDealer = null;
    List localList = queryBySQL(str, arrayOfObject, null, new CommonRowMapper(new Dealer()));
    if (localList.size() > 0) {
      localDealer = (Dealer)localList.get(0);
    }
    return localDealer;
  }
}
