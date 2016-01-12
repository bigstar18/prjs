package gnnt.MEBS.timebargain.mgr.dao.systemMana;

import gnnt.MEBS.common.mgr.dao.StandardDao;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository("systemStatusDao")
public class SystemStatusDao extends StandardDao
{
  public String getSystemStatus()
  {
    String sql = "select status from t_systemstatus";
    List list = queryBySql(sql);
    String status = "";
    if (list.get(0) != null) {
      Map map = (Map)list.get(0);
      status = map.get("STATUS").toString();
    }
    return status;
  }
}