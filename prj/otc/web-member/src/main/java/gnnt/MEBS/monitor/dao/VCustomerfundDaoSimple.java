package gnnt.MEBS.monitor.dao;

import gnnt.MEBS.monitor.model.VCustomerfundSimple;
import org.springframework.stereotype.Repository;

@Repository("vCustomerfundDaoSimple")
public class VCustomerfundDaoSimple
  extends BaseDao
{
  public Class getEntityClass()
  {
    return VCustomerfundSimple.class;
  }
}
