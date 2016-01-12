package gnnt.MEBS.timebargain.mgr.dao.impl;

import gnnt.MEBS.common.mgr.dao.StandardDao;
import gnnt.MEBS.timebargain.mgr.dao.FirmSpecialDao;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository("firmSpecialDao")
public class FirmSpecialDaoImpl extends StandardDao
  implements FirmSpecialDao
{
  public List getSystemStatus()
  {
    String sql = "select * from T_SystemStatus";

    return queryBySql(sql);
  }
}